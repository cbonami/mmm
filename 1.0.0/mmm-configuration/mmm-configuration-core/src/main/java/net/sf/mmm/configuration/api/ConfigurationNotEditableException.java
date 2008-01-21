/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.api;

import net.sf.mmm.configuration.NlsBundleConfigCore;

/**
 * This is the exception thrown if a
 * {@link net.sf.mmm.configuration.api.MutableConfiguration configuration-node}
 * was edited without being {@link MutableConfiguration#isEditable() editable}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ConfigurationNotEditableException extends ConfigurationException {

  /** uid for serialization */
  private static final long serialVersionUID = 8537295080260761963L;

  /**
   * The constructor.
   * 
   * @param node is the node that is NOT
   *        {@link MutableConfiguration#isEditable() editable} .
   */
  public ConfigurationNotEditableException(MutableConfiguration node) {

    super(NlsBundleConfigCore.ERR_NODE_NOT_EDITABLE, node);
  }

  /**
   * The constructor.
   * 
   * @param node is the node that is NOT
   *        {@link MutableConfiguration#isEditable() editable} .
   * @param nested is the throwable that caused this exception.
   */
  public ConfigurationNotEditableException(MutableConfiguration node, Throwable nested) {

    super(nested, NlsBundleConfigCore.ERR_NODE_NOT_EDITABLE, node);
  }

  /**
   * This method gets the node that was edited without being
   * {@link MutableConfiguration#isEditable() editable}.
   * 
   * @return the associated configuration node.
   */
  public MutableConfiguration getConfiguration() {

    return (MutableConfiguration) getNlsMessage().getArgument(0);
  }

}
