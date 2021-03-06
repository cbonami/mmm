/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.term.api;

import net.sf.mmm.term.NlsBundleTermCore;

/**
 * This exception represents an error that occured during the cast (conversion
 * of a value to another type) of a function. <br>
 * E.g. If you want to 'cast' the string "12noInt" to integer this exception
 * will be thrown.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IllegalCastException extends CalculationException {

  /** uid for serialization */
  private static final long serialVersionUID = -8880790425494345197L;

  /**
   * The constructor.
   * 
   * @param argument is the argument that could not be casted.
   * @param type is the type the argument could not be casted to.
   */
  public IllegalCastException(Object argument, Class<?> type) {

    super(NlsBundleTermCore.ERR_ILLEGAL_CAST, argument, type);
  }

  /**
   * The constructor.
   * 
   * @param argument is the argument that could not be casted.
   * @param type is the type the argument could not be casted to.
   * @param nested is the throwable that caused this exception.
   */
  public IllegalCastException(Object argument, Class<?> type, Throwable nested) {

    super(nested, NlsBundleTermCore.ERR_ILLEGAL_CAST, argument, type);
  }

}
