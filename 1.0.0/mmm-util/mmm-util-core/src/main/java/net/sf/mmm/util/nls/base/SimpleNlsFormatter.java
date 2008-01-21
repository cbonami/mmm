/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.text.Format;
import java.util.Locale;

import net.sf.mmm.util.nls.api.NlsFormatter;

/**
 * This is an abstract base implementation of {@link NlsFormatter} that adapts a
 * {@link Format}.
 * 
 * @param <O> is the generic type of the object to
 *        {@link #format(Object, Locale)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class SimpleNlsFormatter<O> implements NlsFormatter<O> {

  /**
   * The constructor.
   */
  public SimpleNlsFormatter() {

    super();
  }

  /**
   * This method creates the underlying {@link Format} to delegate to.
   * 
   * @param locale is the locale of the {@link Format} to create.
   * @return the according format.
   */
  protected abstract Format createFormat(Locale locale);

  /**
   * {@inheritDoc}
   */
  public String format(O object, Locale locale) {

    return createFormat(locale).format(object);
  }

  /**
   * {@inheritDoc}
   */
  public void format(O object, Locale locale, Appendable buffer) {

    try {
      buffer.append(format(object, locale));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

}
