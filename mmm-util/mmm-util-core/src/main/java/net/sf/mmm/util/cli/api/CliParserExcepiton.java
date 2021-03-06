/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.api;

import net.sf.mmm.util.NlsBundleUtilCoreRoot;

/**
 * A {@link CliParserExcepiton} is thrown if a {@link net.sf.mmm.util.cli.api.CliClass state class} could NOT
 * be parsed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public class CliParserExcepiton extends CliException {

  /** UID for serialization. */
  private static final long serialVersionUID = 7576087965627428527L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "CliParser";

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param type is the {@link net.sf.mmm.util.cli.base.CliState#getStateClass() state-class}.
   */
  public CliParserExcepiton(Exception nested, Class<?> type) {

    super(nested, createBundle(NlsBundleUtilCoreRoot.class).errorCliParser(type));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isTechnical() {

    return true;
  }

}
