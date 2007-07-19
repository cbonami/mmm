/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * This exception is thrown if a something goes wrong about values. This can be
 * an invalid "casting", a parse error, etc.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = -8445209659250789499L;

  /**
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   */
  public ValueException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
   */
  public ValueException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
