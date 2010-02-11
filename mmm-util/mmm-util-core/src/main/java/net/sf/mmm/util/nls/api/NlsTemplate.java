/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

/**
 * This interface represents the template for an internationalized text that can
 * be {@link #translate(Locale) translated} for a given {@link Locale}.<br>
 * 
 * @see NlsMessage
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsTemplate {

  /**
   * This method translates the represented string for the given
   * <code>locale</code>.<br>
   * This typically happens via a lookup in a {@link java.util.ResourceBundle}).
   * 
   * @param locale is the locale to translate to.
   * @return the resolved string (closest translation for the given
   *         <code>locale</code>).
   */
  String translate(Locale locale);

  /**
   * This method behaves like {@link #translate(Locale)} but additionally fills
   * the given <code>arguments</code> into the translated message writing into
   * the given <code>buffer</code>.
   * 
   * @param locale is the locale to translate to.
   * @param arguments are the variable arguments to fill in the message.
   * @param buffer is the buffer where the translation will be appended to.
   * @param resolver is the {@link NlsTemplateResolver}.
   * @return <code>true</code> if the (translated) message has been appended to
   *         the given <code>messageBuffer</code> or <code>false</code> if the
   *         translation failed.
   * @throws IOException if the given {@link Appendable} caused such exception.
   */
  boolean translate(Locale locale, Map<String, Object> arguments, Appendable buffer,
      NlsTemplateResolver resolver) throws IOException;

}
