/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import java.util.Locale;

/**
 * This is the interface for an internationalized message. It stores an
 * {@link #getInternationalizedMessage() internationalized-message} separated
 * from language independent {@link #getArgument(String) arguments}. This
 * approach ensures that the message is always available in the
 * internationalized language (should be English) while it still allows to
 * {@link #getLocalizedMessage(Locale, NlsTemplateResolver) translate} the
 * message to a native language.<br>
 * The format of the {@link #getInternationalizedMessage()
 * internationalized-message} is compatible to {@link java.text.MessageFormat}.
 * This allows to migrate existing code from {@link java.text.MessageFormat} to
 * {@link NlsMessage} easily. However there are some advanced features
 * available. While using numbers to identify the {@link #getArgument(int)
 * argument} is a maintenance-hell for large messages, it is also possible to
 * use {@link #getArgument(String) named arguments}. Further there is also
 * support for additional styles as well as
 * {@link net.sf.mmm.util.text.api.Justification}. The format specification for
 * parameter-syntax is as following:
 * 
 * <pre>'{' ArgumentKey [ ',' FormatType [ ',' FormatStyle ] ] [ '{' Justification '}' ] '}'</pre>
 * 
 * The literals are explained in this table.
 * <table border="1">
 * <tr>
 * <th>literal</th>
 * <th>explanation</th>
 * </tr>
 * <tr>
 * <td>ArgumentKey</td>
 * <td>is the key of the parameter (may be numeric for legacy support)</td>
 * </tr>
 * <tr>
 * <td>FormatType</td>
 * <td>a type specifying how to format the value (see below). Should fit to the
 * {@link Object#getClass() type} of the value.
 * </tr>
 * <tr>
 * <td>FormatStyle</td>
 * <td>a style according to <code>FormatType</code> (see below).</td>
 * </tr>
 * <tr>
 * <td>Justification</td>
 * <td>a {@link net.sf.mmm.util.text.api.JustificationBuilder justification}</td>
 * </tr>
 * </table>
 * 
 * Here are the supported variants:
 * <table border="1">
 * <tr>
 * <th>FormatType</th>
 * <th>FormatStyle</th>
 * <th>Example</th>
 * <th>Comment</th>
 * </tr>
 * <tr>
 * <td>-</td>
 * <td>-</td>
 * <td>value as string</td>
 * <td>default string representation of the value</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>-</td>
 * <td>12,345</td>
 * <td>Numeric representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>{@link NlsFormatterManager#STYLE_PERCENT percent}</td>
 * <td>12%</td>
 * <td>Number in percent</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_NUMBER number}</td>
 * <td>{@link NlsFormatterManager#STYLE_CURRENCY currency}</td>
 * <td>123,45�</td>
 * <td>Number as currency</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>-</td>
 * <td>12,345</td>
 * <td>Numeric representation</td>
 * </tr>
 * <tr>
 * <td>{@link NlsFormatterManager#TYPE_DATE date}</td>
 * <td>yyyy.MM.dd-HH:mm:ssZ</td>
 * <td>12,345</td>
 * <td>Any other style is treated pattern for {@link java.text.SimpleDateFormat}
 * </td>
 * </tr>
 * </table>
 * 
 * For the term <em>internationalization</em> usually the shortcut <em>i18n</em>
 * is used.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface NlsMessage extends NlsObject {

  /**
   * The prefix appended to the {@link #getInternationalizedMessage() message}
   * if the localization (translation) failed.
   */
  String LOCALIZATION_FAILURE_PREFIX = "#";

  /**
   * This method gets the internationalized message that can be
   * {@link NlsTemplateResolver#resolveTemplate(String) translated} to a native
   * language. The language independent arguments are filled into the message
   * after the translation process. <br>
   * E.g. the i18n message may be <code>"Welcome {name}!"</code> and there is
   * one argument that is the string <code>"Joelle"</code>. The final result
   * will then be <code>"Welcome Joelle!"</code>. If the message is translated
   * to German as <code>"Willkommen {name}!"</code> the final result will be
   * <code>"Willkommen Joelle!"</code>.
   * 
   * @see NlsMessage#getArgument(String)
   * @see java.text.MessageFormat
   * 
   * @return the message for internationalization.
   */
  String getInternationalizedMessage();

  /**
   * This method gets the number of language independent arguments of this
   * exception.
   * 
   * @return the argument count.
   * @deprecated this method does NOT make sense anymore.
   */
  @Deprecated
  int getArgumentCount();

  /**
   * This method gets the language independent argument for the given
   * <code>key</code>.
   * 
   * @param key is the name of the requested argument.
   * @return the argument for the given key.
   */
  Object getArgument(String key);

  /**
   * This method gets the language independent argument at the given
   * <code>index</code>.
   * 
   * @param index is the index of the requested argument.
   * @return the argument for the given key.
   * @deprecated use {@link #getArgument(String)} instead (and use named keys
   *             instead of numbers where possible).
   */
  @Deprecated
  Object getArgument(int index);

  /**
   * This method gets the untranslated message (default language should be
   * English) with arguments filled in.
   * 
   * @see NlsMessage#getLocalizedMessage(Locale, NlsTemplateResolver)
   * 
   * @return the i18n message with arguments filled in.
   */
  String getMessage();

  /**
   * This method tries to get the localized message as string. Since no
   * {@link NlsTemplateResolver translator} is specified, the implementation may
   * try its best to do the translation on its own according to the callers
   * {@link java.util.Locale locale}. Therefore this method has to do some magic
   * behind the scenes. This may only work if you follow specific rules of the
   * implementation of this interface. If this fails or is NOT supported, the
   * {@link #getMessage() untranslated} message should be used.<br>
   * <b>ATTENTION:</b><br>
   * If possible try to avoid using this method and use
   * {@link #getLocalizedMessage(Locale, NlsTemplateResolver)} instead.
   * 
   * @return the localized message.
   */
  String getLocalizedMessage();

  /**
   * This method tries to get the localized message as string. Since no
   * {@link NlsTemplateResolver translator} is specified, the implementation may
   * try its best to do the translation on its own according to the callers
   * {@link java.util.Locale locale}. Therefore this method has to do some magic
   * behind the scenes. This may only work if you follow specific rules of the
   * implementation of this interface. If this fails or is NOT supported, the
   * {@link #getMessage() untranslated} message should be used.<br>
   * <b>ATTENTION:</b><br>
   * If possible try to avoid using this method and use
   * {@link #getLocalizedMessage(Locale, NlsTemplateResolver)} instead.
   * 
   * @param locale is the locale to translate to.
   * @return the localized message.
   */
  String getLocalizedMessage(Locale locale);

  /**
   * This method gets the localized message as string.
   * 
   * @see NlsMessage#getLocalizedMessage(Locale, NlsTemplateResolver,
   *      Appendable)
   * 
   * @param locale is the locale to translate to.
   * @param resolver is used to translate the message.
   * @return the localized message.
   */
  String getLocalizedMessage(Locale locale, NlsTemplateResolver resolver);

  /**
   * This method writes the localized message to the given <code>buffer</code>. <br>
   * The actual localization is done by a {@link NlsTemplate template} that is
   * provided via the given <code>resolver</code>. If this fails, the
   * {@link #getInternationalizedMessage() original message} will be used. After
   * translation is done, the language independent arguments will be filled in
   * the translated message string according to the given <code>locale</code>.
   * 
   * @param locale is the locale to translate to.
   * @param resolver is used to resolve the template required to translate the
   *        {@link #getInternationalizedMessage() internationalized message}.
   * @param buffer is the buffer where to write the message to.
   * @throws IllegalStateException if the given <code>buffer</code> produced an
   *         {@link java.io.IOException}.
   */
  void getLocalizedMessage(Locale locale, NlsTemplateResolver resolver, Appendable buffer)
      throws IllegalStateException;

}
