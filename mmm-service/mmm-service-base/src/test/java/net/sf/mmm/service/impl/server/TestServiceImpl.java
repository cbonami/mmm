/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.service.impl.server;

import javax.inject.Named;

import net.sf.mmm.service.base.server.AbstractRemoteInvocationService;

/**
 * This is the implementation of {@link TestService}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Named
public class TestServiceImpl extends AbstractRemoteInvocationService implements TestService {

  /**
   * {@inheritDoc}
   */
  @Override
  public String getMagicValue() {

    return MAGIC_VALUE;
  }

}
