/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls;

import net.sf.mmm.util.nls.api.AbstractNlsException;

/**
 * This is an abstract base implementation of a checked exception with real
 * <em>native language support</em> (NLS). <br>
 * <b>ATTENTION:</b><br>
 * Checked exceptions should be used for business errors and should only occur
 * in unexpected situations.
 * 
 * @see net.sf.mmm.util.nls.api.NlsThrowable
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class NlsException extends AbstractNlsException {

  /**
   * The constructor.<br>
   * 
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsException(String internaitionalizedMessage, Object... arguments) {

    super(new NlsMessageImpl(internaitionalizedMessage, arguments));
  }

  /**
   * The constructor.<br>
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param internaitionalizedMessage is a short description of the problem. It
   *        is used for internationalization and should be in English language.
   * @param arguments are the arguments filled into the
   *        <code>internaitionalizedMessage</code> after nationalization.
   */
  public NlsException(Throwable nested, String internaitionalizedMessage, Object... arguments) {

    super(nested, new NlsMessageImpl(internaitionalizedMessage, arguments));
  }

}
