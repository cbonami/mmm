/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.api;

import net.sf.mmm.nls.base.NlsRuntimeException;

/**
 * A {@link ContainerException} is thrown the
 * {@link IocContainer IoC container} itself is in an exceptional state.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContainerException extends IocException {

  /** UID for serialization */
  private static final long serialVersionUID = -596276084369784042L;

  /**
   * @see NlsRuntimeException#NlsRuntimeException(String, Object[])
   */
  public ContainerException(String newInternaitionalizedMessage, Object... newArguments) {

    super(newInternaitionalizedMessage, newArguments);
  }

  /**
   * @see NlsRuntimeException#NlsRuntimeException(Throwable, String, Object[])
   */
  public ContainerException(Throwable newNested, String newInternaitionalizedMessage,
      Object... newArguments) {

    super(newNested, newInternaitionalizedMessage, newArguments);
  }

}
