/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * A {@link CliModeMixException} is thrown if two {@link CliOption options} are
 * used together that have incompatible {@link CliOption#mode() modes}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public class CliModeMixException extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1233209808038797353L;

  /**
   * Key for the {@link net.sf.mmm.util.nls.api.NlsMessage#getArgument(String)
   * argument} {@value}.
   */
  public static final String KEY_OPTION2 = "option2";

  /**
   * The constructor.
   * 
   * @param option1 is the first option.
   * @param option2 is the second option.
   */
  public CliModeMixException(String option1, String option2) {

    super(NlsBundleUtilCore.ERR_CLI_MODE_MIX, toMap(KEY_OPTION, option1, KEY_OPTION2, option2));
  }

}