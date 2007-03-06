/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.binding.api;

import net.sf.mmm.configuration.api.ConfigurationException;


/**
 * TODO: this class ...
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationBindingException extends ConfigurationException {

  /**
   * The constructor
   *
   * @param internaitionalizedMessage
   * @param arguments
   */
  public ConfigurationBindingException(String internaitionalizedMessage, Object... arguments) {

    super(internaitionalizedMessage, arguments);
  }

  /**
   * The constructor
   *
   * @param nested
   * @param internaitionalizedMessage
   * @param arguments
   */
  public ConfigurationBindingException(Throwable nested, String internaitionalizedMessage,
      Object... arguments) {

    super(nested, internaitionalizedMessage, arguments);
  }

}
