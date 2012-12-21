/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.field;

import net.sf.mmm.client.ui.api.feature.UiFeatureValidation;
import net.sf.mmm.client.ui.api.feature.UiFeatureValue;
import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetActive;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegularComposite;
import net.sf.mmm.client.ui.api.widget.UiWidgetWithValue;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetLabel;

/**
 * This is the abstract interface for a {@link UiWidgetRegularComposite regular composite widget} that
 * represents a editable field (text field, combobox, text area, radio-buttons, checkbox, etc.). It appears
 * confusing in the first place that this is a composite rather than an atomic widget, but a field widget may
 * also be composed out of multiple native input widgets (e.g. to edit a composite
 * {@link net.sf.mmm.util.lang.api.Datatype} - see
 * {@link net.sf.mmm.client.ui.api.UiContext#createForDatatype(Class)}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public abstract interface UiWidgetField<VALUE> extends UiWidgetRegularComposite<UiWidget>, UiWidgetWithValue<VALUE>,
    UiWidgetActive, UiFeatureValue<VALUE>, UiFeatureValidation<VALUE> {

  /**
   * This method is logically equivalent to
   * <code>{@link #getFieldLabelWidget()}.{@link UiWidgetLabel#getLabel() getLabel()}</code> without actually
   * creating the {@link #getFieldLabelWidget() field label widget} if it does NOT yet exist.
   * 
   * @return the {@link UiWidgetLabel#getLabel() label} of the {@link #getFieldLabelWidget() field label
   *         widget} widget} or <code>null</code> if NOT set.
   */
  String getFieldLabel();

  /**
   * This method is logically equivalent to
   * <code>{@link #getFieldLabelWidget()}.{@link UiWidgetLabel#setLabel(String) setLabel(String)}</code>
   * without actually creating the {@link #getFieldLabelWidget() field label widget} if it does NOT yet exist.
   * In the latter case the given label is stored and will automatically be assigned if
   * {@link #getFieldLabelWidget()} will create the label widget.
   * 
   * @param label is the new {@link UiWidgetLabel#getLabel() label} for the {@link #getFieldLabelWidget()
   *        field label widget} widget}.
   */
  void setFieldLabel(String label);

  /**
   * This method gets the {@link UiWidgetLabel label widget} associated with this field.<br/>
   * <b>ATTENTION:</b><br/>
   * Depending on the underlying toolkit the label widget is lazily created on the first call of this method.
   * Additionally users of this API (unlike implementors of the API) may only use this for very specific cases
   * (e.g. setting the style of the label). Therefore you should avoid calling this method unless you are
   * aware of what you are doing. To access the label-text simply use {@link #getFieldLabel()} and
   * {@link #setFieldLabel(String)}. Be aware that some native toolkits (e.g. SmartGWT) already implement
   * field widgets including the label. In this case this method will return a different view on the same
   * native widget.
   * 
   * @return the associated label widget.
   */
  UiWidgetLabel getFieldLabelWidget();

}
