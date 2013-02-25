/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.feature;

import java.util.LinkedList;
import java.util.List;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteModified;
import net.sf.mmm.client.ui.api.feature.UiFeatureValueAndValidation;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.base.handler.event.ChangeEventSender;
import net.sf.mmm.util.component.base.AbstractLoggableObject;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.validation.api.ValidationFailure;
import net.sf.mmm.util.validation.api.ValidationState;
import net.sf.mmm.util.validation.api.ValueValidator;
import net.sf.mmm.util.validation.base.SimpleValidationFailure;
import net.sf.mmm.util.validation.base.ValidationStateImpl;
import net.sf.mmm.util.validation.base.ValidatorMandatory;

/**
 * This is a base implementation of {@link UiFeatureValueAndValidation} and related aspects such as
 * {@link #isModified() modified}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiFeatureValue<VALUE> extends AbstractLoggableObject implements
    UiFeatureValueAndValidation<VALUE>, AttributeWriteModified {

  /** @see #addValidator(ValueValidator) */
  private final List<ValueValidator<? super VALUE>> validatorList;

  /** @see #addChangeHandler(UiHandlerEventValueChange) */
  private ChangeEventSender<VALUE> changeEventSender;

  /** @see #getOriginalValue() */
  private VALUE originalValue;

  /** @see #isModified() */
  private boolean modified;

  /** @see #isMandatory() */
  private boolean mandatory;

  /** @see #isValidated() */
  private boolean validated;

  /**
   * The constructor.
   */
  public AbstractUiFeatureValue() {

    super();
    this.validatorList = new LinkedList<ValueValidator<? super VALUE>>();
    this.modified = false;
    this.originalValue = null;
    this.changeEventSender = null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValue() {

    try {
      return getValueOrException(null);
    } catch (RuntimeException e) {
      handleGetValueError(e, null);
      return null;
    }
  }

  /**
   * This method handles a {@link RuntimeException} that occurred in
   * {@link #getValueInternal(Object, ValidationState)}.
   * 
   * @param e is the {@link RuntimeException} to handle.
   * @param state is the {@link ValidationState} or <code>null</code> if no validation is performed.
   */
  private void handleGetValueError(RuntimeException e, ValidationState state) {

    if (state == null) {
      // ATTENTION: This is one of the very rare cases where we intentionally consume an exception.
      getLogger().debug("Error in getValue() at " + toString(), e);
    } else {
      ValidationFailure failure = createValidationFailure(e);
      if (failure != null) {
        state.onFailure(failure);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValue(VALUE value) {

    setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValueAndValidate(ValidationState state) {

    clearMessages();
    ValidationState validationState = state;
    if (validationState == null) {
      validationState = new ValidationStateImpl();
    }
    VALUE result = getValueInternal(null, validationState);
    if (validationState.isValid()) {
      if ((result == null) && (state == null)) {
        getLogger().error("null has been returned as valid value at " + toString());
      }
      return result;
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValueOrException(VALUE template) throws RuntimeException {

    return getValueInternal(template, null);
  }

  /**
   * @return <code>true</code> if this object has been {@link #validate(ValidationState) validated}
   *         (technically via {@link #getValueInternal(Object, ValidationState)}) since
   *         {@link #clearMessages() messages have been cleared}.
   */
  protected boolean isValidated() {

    return this.validated;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clearMessages() {

    this.validated = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValueInternal(VALUE template, ValidationState state) throws RuntimeException {

    // clear validations (and flag)...?
    try {
      VALUE value = doGetValue(template, state);
      if (state != null) {
        doValidate(state, value);
      }
      return value;
    } catch (RuntimeException e) {
      if (state == null) {
        throw e;
      }
      handleGetValueError(e, state);
      return null;
    }
  }

  /**
   * This method is called from {@link #getValueOrException(Object)}. It has to be implemented with the custom
   * logic to get the value from the view. The implementation of this method has to correspond with the
   * implementation of {@link #doSetValue(Object)}. A typical implementation of this method for a composite
   * widget should look like this:
   * 
   * <pre>
   * protected MyBean doGetValue(MyBean template, {@link ValidationState} state) {
   *   MyBean result = template;
   *   if (result == null) {
   *     result = new MyBean();
   *   }
   *   MyBean original = {@link #getOriginalValue()};
   *   if (original != null) {
   *     // result.copyValues(original);
   *     result.setId(original.getId());
   *   }
   *
   *   // if foo is a datatype we could supply null instead of result.getFoo()
   *   result.setFoo(this.widgetFoo.{@link #getValueInternal(Object, ValidationState) getValueInternal}(result.getFoo(), state));
   *
   *   Bar bar = this.widgetBar.{@link #getValueInternal(Object, ValidationState) getValueInternal}(result.getBar());
   *   this.widgetBar2.{@link #getValueOrException(Object) getValueOrException}(bar);
   *   result.setBar(bar);
   *   ...
   *
   *   return result;
   * }
   * </pre>
   * 
   * @see #doSetValue(Object)
   * 
   * @param template is the object where the data is filled in. May also be <code>null</code> - then this
   *        method will create a new instance.
   * @param state is the {@link ValidationState}. If <code>null</code> the validation will be omitted,
   *        otherwise an implicit validation is performed.(see
   *        {@link #getValueInternal(Object, ValidationState)}).
   * @return the current value of this widget. May be <code>null</code> if empty. If &lt;VALUE&gt; is
   *         {@link String} the empty {@link String} has to be returned if no value has been entered. In case
   *         &lt;VALUE&gt; is a mutable object (java bean) and <code>template</code> is NOT <code>null</code>,
   *         this method is supposed to return <code>template</code> after the value(s) of this object have
   *         been assigned. If <code>template</code> is <code>null</code> this method has to create a new
   *         instance of &lt;VALUE&gt;. It is forbidden and an explicit bug-pattern to modify the
   *         {@link #getOriginalValue() original value}. This is required to support operations such as
   *         {@link #resetValue()}.
   */
  protected abstract VALUE doGetValue(VALUE template, ValidationState state);

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getOriginalValue() {

    return this.originalValue;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValueForUser(VALUE value) {

    setValue(value, true);
  }

  /**
   * Implementation of {@link #setValue(Object)} and {@link #setValueForUser(Object)}.
   * 
   * @param newValue is the new {@link #getValue() value}.
   * @param forUser <code>true</code> if called from {@link #setValueForUser(Object)}, <code>false</code> if
   *        set from {@link #setValue(Object)}.
   */
  private void setValue(VALUE newValue, boolean forUser) {

    setModified(forUser);
    if (!forUser) {
      this.originalValue = newValue;
    }
    doSetValue(newValue);
    if (this.changeEventSender != null) {
      this.changeEventSender.onValueChange(this, true);
    }
  }

  /**
   * This method is called from {@link #setValue(Object)} and {@link #setValueForUser(Object)}. It has to be
   * implemented with the custom logic to set the value in the view. The implementation of this method has to
   * correspond with the implementation of {@link #doGetValue(Object, ValidationState)}.
   * 
   * @see #doGetValue(Object, ValidationState)
   * 
   * @param value is the value to set. Typically a composite object (e.g. java bean) so its attributes are set
   *        to {@link net.sf.mmm.client.ui.api.widget.field.UiWidgetField atomic fields}.
   */
  protected abstract void doSetValue(VALUE value);

  /**
   * {@inheritDoc}
   */
  @Override
  public final void resetValue() {

    setValue(this.originalValue);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean isModified() {

    if (isModifiedLocal()) {
      return true;
    }
    return isModifiedRecursive();
  }

  /**
   * @return {@link #isModified()} for this object itself (without {@link #isModifiedRecursive() recursion}).
   */
  protected boolean isModifiedLocal() {

    return this.modified;
  }

  /**
   * @return <code>true</code> if a child or descendant of this object is {@link #isModified() modified},
   *         <code>false</code> otherwise.
   */
  protected boolean isModifiedRecursive() {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setModified(boolean modified) {

    this.modified = modified;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    requireChangeEventSender().addHandler(handler);
  }

  /**
   * @return the {@link ChangeEventSender}. Will be {@link #createChangeEventSender() created} on the first
   *         call.
   */
  protected final ChangeEventSender<VALUE> requireChangeEventSender() {

    if (this.changeEventSender == null) {
      this.changeEventSender = createChangeEventSender();
    }
    return this.changeEventSender;
  }

  /**
   * @return a new instance of {@link ChangeEventSender}. Should be connected with the underlying native
   *         widget where applicable.
   */
  protected abstract ChangeEventSender<VALUE> createChangeEventSender();

  /**
   * @return the {@link ChangeEventSender} or <code>null</code> if NOT yet created.
   */
  protected final ChangeEventSender<VALUE> getChangeEventSender() {

    return this.changeEventSender;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    if (this.changeEventSender != null) {
      return this.changeEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isMandatory() {

    return this.mandatory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addValidator(ValueValidator<? super VALUE> validator) {

    NlsNullPointerException.checkNotNull(ValueValidator.class, validator);
    this.validatorList.add(validator);
    if (validator instanceof ValidatorMandatory) {
      setMandatory(true);
      // ensure that super call was not omitted if overridden...
      assert this.mandatory;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addValidatorMandatory() {

    if (isMandatory()) {
      return;
    }
    addValidator(new ValidatorMandatory());
  }

  /**
   * This method updates the {@link #isMandatory()} flag. You may override it to update the UI to reflect this
   * change.
   * 
   * @param mandatory is the new value of {@link #isMandatory()}.
   */
  protected void setMandatory(boolean mandatory) {

    this.mandatory = mandatory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeValidator(ValueValidator<? super VALUE> validator) {

    NlsNullPointerException.checkNotNull(ValueValidator.class, validator);
    boolean removed = this.validatorList.remove(validator);
    if (removed) {
      boolean newMandatory = false;
      for (ValueValidator<? super VALUE> currentValidator : this.validatorList) {
        if (currentValidator instanceof ValidatorMandatory) {
          newMandatory = true;
          break;
        }
      }
      if (newMandatory != this.mandatory) {
        setMandatory(newMandatory);
        // if mandatory has changed due to removal of validator, it can only change to false
        assert !this.mandatory;
      }
    }
    return removed;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean validate(ValidationState state) {

    ValidationState validationState = state;
    if (validationState == null) {
      validationState = new ValidationStateImpl();
    }
    getValueInternal(null, state);
    return validationState.isValid();
  }

  /**
   * This method is called from {@link #validate(ValidationState)} and performs the actual validation of this
   * object.
   * 
   * @param state is the {@link ValidationState}. Never <code>null</code>.
   * @param value is the {@link #getValue() current value} of this object that has already be determined.
   */
  protected void doValidate(ValidationState state, VALUE value) {

    validateLocal(state, value);
  }

  /**
   * This method performs the local validation of this object excluding potential child objects.
   * 
   * @param state is the {@link ValidationState}. Not <code>null</code>.
   * @param value is the {@link #getValue() current value} of this object that has already be determined.
   */
  protected void validateLocal(ValidationState state, VALUE value) {

    if (this.validatorList != null) {
      for (ValueValidator<? super VALUE> validator : this.validatorList) {
        try {
          ValidationFailure failure = validator.validate(value, getSource());
          if (failure != null) {
            state.onFailure(failure);
          }
        } catch (RuntimeException e) {
          getLogger().error("Error in validator '" + validator + "' at " + toString(), e);
          handleGetValueError(e, state);
        }
      }
    }
  }

  /**
   * This method converts an exception from {@link #getValueOrException(Object)} to a
   * {@link ValidationFailure}.
   * 
   * @param error is the exception.
   * @return the {@link ValidationFailure}.
   */
  protected ValidationFailure createValidationFailure(Throwable error) {

    return new SimpleValidationFailure(error.getClass().getSimpleName(), getSource(), error.getLocalizedMessage());
  }

  /**
   * @return the source for validation failures.
   */
  protected abstract String getSource();

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    // default implementation
    return getSource() + "[" + getClass().getSimpleName() + "]";
  }

}
