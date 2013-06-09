/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.validation.base;

import java.util.Collection;
import java.util.Map;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;
import net.sf.mmm.util.nls.api.NlsAccess;

/**
 * This is a {@link net.sf.mmm.util.validation.api.ValueValidator} that validates that a mandatory value is
 * filled. It will produce a {@link net.sf.mmm.util.validation.api.ValidationFailure} if the value is not
 * provided (empty, blank, <code>null</code>).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ValidatorMandatory extends AbstractValueValidator<Object> {

  /** @see #getInstance() */
  private static final ValidatorMandatory INSTANCE = new ValidatorMandatory();

  /**
   * The constructor.
   */
  public ValidatorMandatory() {

    super();
  }

  /**
   * @return the singleton instance of this class.
   */
  public static ValidatorMandatory getInstance() {

    return INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String validateNull() {

    return getFailureMessage();
  }

  /**
   * @return the failure message.
   */
  private String getFailureMessage() {

    return NlsAccess.getBundleFactory().createBundle(NlsBundleUtilCoreRoot.class).failureMandatory()
        .getLocalizedMessage();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String validateNotNull(Object value) {

    if (value instanceof Collection) {
      Collection<?> collection = (Collection<?>) value;
      if (collection.isEmpty()) {
        return getFailureMessage();
      }
    } else if (value instanceof Map) {
      Map<?, ?> map = (Map<?, ?>) value;
      if (map.isEmpty()) {
        return getFailureMessage();
      }
    } else if (value instanceof CharSequence) {
      CharSequence sequence = (CharSequence) value;
      if (sequence.length() == 0) {
        return getFailureMessage();
      }
    }
    return null;
  }

}
