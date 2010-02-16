/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import net.sf.mmm.util.NlsBundleUtilCore;
import net.sf.mmm.util.nls.api.NlsRuntimeException;

/**
 * The {@link NlsFormatterChoiceOnlyElseConditionException} is thrown if a
 * {@link NlsFormatterChoice choice-format} starts with an (else)-condition.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class NlsFormatterChoiceOnlyElseConditionException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 8608373296346083118L;

  /**
   * The constructor.
   */
  public NlsFormatterChoiceOnlyElseConditionException() {

    super(NlsBundleUtilCore.ERR_NLS_CHOICE_ONLY_ELSE);
  }

}
