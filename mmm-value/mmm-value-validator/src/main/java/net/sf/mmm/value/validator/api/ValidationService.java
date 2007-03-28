/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.validator.api;

/**
 * This is the interface for a service that provides validation.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ValidationService {

  /**
   * This method creates a new validation-result that
   * {@link ValidationResult#getDetail(int) contains} the given
   * validation-results.
   * 
   * @param message
   *        is the message for the composed validation-result.
   * @param validationResults
   *        are the results to compose.
   * @return a new validation-result object containing the given
   *         validation-results.
   */
  ValidationResult compose(String message, ValidationResult... validationResults);

}
