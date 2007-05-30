/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.io.Writer;

import net.sf.mmm.util.io.EscapeWriter;

/**
 * This utility class contains methods that held to deal with XML.
 * 
 * @see net.sf.mmm.util.xml.DomUtil
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class XmlUtil {

  /** the xml entity for an ampersand sign (&) */
  public static final String ENTITY_AMPERSAND = "&amp;";

  /** the xml entity for a less sign (<) */
  public static final String ENTITY_LESS = "&lt;";

  /** the xml entity for a greater sign (>) */
  public static final String ENTITY_GREATER = "&gt;";

  /** the xml entity for a quote sign (") */
  public static final String ENTITY_QUOTE = "&quot;";

  /** table with escapes for xml text */
  private static final String[] CHAR_ESCAPE_TABLE_TEXT;

  /** table with escapes for xml attributes */
  private static final String[] CHAR_ESCAPE_TABLE_ATTRIBUTE;

  static {
    int arraySize = StrictMath.max(StrictMath.max(StrictMath.max('<', '>'), '&'), '"') + 1;
    CHAR_ESCAPE_TABLE_TEXT = new String[arraySize];
    CHAR_ESCAPE_TABLE_ATTRIBUTE = new String[arraySize];
    // would not be necessary for sun JVM, but you never know...
    for (int i = 0; i < arraySize; i++) {
      CHAR_ESCAPE_TABLE_TEXT[i] = null;
      CHAR_ESCAPE_TABLE_ATTRIBUTE[i] = null;
    }
    CHAR_ESCAPE_TABLE_TEXT['&'] = ENTITY_AMPERSAND;
    CHAR_ESCAPE_TABLE_ATTRIBUTE['&'] = ENTITY_AMPERSAND;
    CHAR_ESCAPE_TABLE_TEXT['<'] = ENTITY_LESS;
    CHAR_ESCAPE_TABLE_ATTRIBUTE['<'] = ENTITY_LESS;
    CHAR_ESCAPE_TABLE_TEXT['>'] = ENTITY_GREATER;
    CHAR_ESCAPE_TABLE_ATTRIBUTE['>'] = ENTITY_GREATER;
    CHAR_ESCAPE_TABLE_ATTRIBUTE['"'] = ENTITY_QUOTE;
  }

  /** the URI of the "xmlns" namespace */
  public static final String NAMESPACE_URI_XMLNS = "http://www.w3.org/2000/xmlns/".intern();

  /** the reserved "xmlns" namespace prefix */
  public static final String NAMESPACE_PREFIX_XMLNS = "xmlns".intern();

  /**
   * Forbidden constructor.
   */
  private XmlUtil() {

    super();
  }

  /**
   * This method creates a wrapper (facade) on the given writer that escapes the
   * characters reserved in XML attributes (CDATA).<br>
   * E.g. the character '"' will automatically written as {@link #ENTITY_QUOTE}
   * by the returned writer.
   * 
   * @param plainWriter
   *        is the writer to wrap.
   * @return a new writer that behaves like the given writer except that
   *         reserved XML characters are replaced by their equivalent entity.
   */
  public static Writer createXmlAttributeWriter(Writer plainWriter) {

    return new EscapeWriter(CHAR_ESCAPE_TABLE_ATTRIBUTE, plainWriter);
  }

  /**
   * This method creates a wrapper (facade) on the given writer that escapes the
   * characters reserved in XML text (PCDATA).<br>
   * E.g. the character '&' will automatically written as
   * {@link #ENTITY_AMPERSAND} by the returned writer.
   * 
   * @param plainWriter
   *        is the writer to wrap.
   * @return a new writer that behaves like the given writer except that
   *         reserved XML characters are replaced by their equivalent entity.
   */
  public static Writer createXmlTextWriter(Writer plainWriter) {

    return new EscapeWriter(CHAR_ESCAPE_TABLE_TEXT, plainWriter);
  }

}
