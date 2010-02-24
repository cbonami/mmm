/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliOptionDuplicateException} is thrown if the same option occurred
 * multiple times as commandline-argument.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliOptionDuplicateException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 5934504531477162386L;

  /**
   * The constructor.
   * 
   * @param option is the according {@link CliOption option}.
   */
  public CliOptionDuplicateException(String option) {

    super(NlsBundleUtilCore.ERR_CLI_OPTION_DUPLICATE, toMap(KEY_OPTION, option));
  }

}
