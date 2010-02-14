/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsFormatterManager;
import net.sf.mmm.util.nls.base.SimpleNlsFormatter;

/**
 * This is an implementation of {@link net.sf.mmm.util.nls.api.NlsFormatter}
 * using {@link SimpleDateFormat}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class NlsFormatterDatePattern extends SimpleNlsFormatter<Object> {

  /** @see #createFormat(Locale) */
  private final String pattern;

  /**
   * The constructor.
   * 
   * @param pattern is the pattern for the {@link SimpleDateFormat}.
   */
  public NlsFormatterDatePattern(String pattern) {

    super();
    this.pattern = pattern;
    // trigger pattern validation if assertions are one
    assert (new SimpleDateFormat(this.pattern, Locale.ROOT) != null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Format createFormat(Locale locale) {

    // reimplement thread-safe SimpleDateFormat?
    return new SimpleDateFormat(this.pattern, locale);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getType() {

    return NlsFormatterManager.TYPE_DATE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getStyle() {

    return this.pattern;
  }

}
