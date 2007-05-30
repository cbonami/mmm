/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml;

import java.io.OutputStream;
import java.io.Writer;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

/**
 * This utility class contains methods that help to work with the StAX API (JSR
 * 173).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class StaxUtil {

  /** the StAX output factory */
  private static final XMLOutputFactory OUTPUT_FACTORY = XMLOutputFactory.newInstance();

  /**
   * Forbidden constructor.
   */
  private StaxUtil() {

    super();
  }

  /**
   * This method creates a stream writer.
   * 
   * @param out
   *        is the output stream where the XML will be written to.
   * @return the XML stream writer.
   * @throws XMLStreamException
   *         if the creation of the stream writer failed (StAX not available or
   *         misconfigured).
   */
  public static XMLStreamWriter createXmlStreamWriter(OutputStream out) throws XMLStreamException {

    return OUTPUT_FACTORY.createXMLStreamWriter(out);
  }

  /**
   * This method creates a stream writer.
   * 
   * @param writer
   *        is the writer where the XML will be written to.
   * @return the XML stream writer.
   * @throws XMLStreamException
   *         if the creation of the stream writer failed (StAX not available or
   *         misconfigured).
   */
  public static XMLStreamWriter createXmlStreamWriter(Writer writer) throws XMLStreamException {

    return OUTPUT_FACTORY.createXMLStreamWriter(writer);
  }

}
