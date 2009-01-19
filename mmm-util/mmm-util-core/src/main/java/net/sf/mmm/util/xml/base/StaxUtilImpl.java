/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.xml.base;

import java.io.OutputStream;
import java.io.Writer;

import javax.annotation.Resource;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.value.api.StringValueConverter;
import net.sf.mmm.util.value.api.ValueException;
import net.sf.mmm.util.value.base.StringValueConverterImpl;
import net.sf.mmm.util.xml.api.StaxUtil;

/**
 * This utility class contains methods that help to work with the StAX API (JSR
 * 173).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class StaxUtilImpl extends AbstractLoggable implements StaxUtil {

  /** @see #getInstance() */
  private static StaxUtil instance;

  /** the StAX output factory */
  private XMLOutputFactory xmlOutputFactory;

  /** @see #getValueConverter() */
  private StringValueConverter valueConverter;

  /**
   * The constructor.
   */
  public StaxUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of this {@link StaxUtilImpl}.<br>
   * This design is the best compromise between easy access (via this
   * indirection you have direct, static access to all offered functionality)
   * and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and
   * construct new instances via the container-framework of your choice (like
   * plexus, pico, springframework, etc.). To wire up the dependent components
   * everything is properly annotated using common-annotations (JSR-250). If
   * your container does NOT support this, you should consider using a better
   * one.
   * 
   * @return the singleton instance.
   */
  public static StaxUtil getInstance() {

    if (instance == null) {
      synchronized (StaxUtilImpl.class) {
        if (instance == null) {
          StaxUtilImpl util = new StaxUtilImpl();
          util.initialize();
          instance = util;
        }
      }
    }
    return instance;
  }

  /**
   * @return the valueConverter
   */
  protected StringValueConverter getValueConverter() {

    return this.valueConverter;
  }

  /**
   * @param valueConverter the valueConverter to set
   */
  @Resource
  public void setValueConverter(StringValueConverter valueConverter) {

    getInitializationState().requireNotInitilized();
    this.valueConverter = valueConverter;
  }

  /**
   * This method gets the {@link XMLOutputFactory}.
   * 
   * @return the xmlOutputFactory
   */
  protected XMLOutputFactory getXmlOutputFactory() {

    return this.xmlOutputFactory;
  }

  /**
   * This method sets the {@link #getXmlOutputFactory() XML-output-factory}.
   * 
   * @param xmlOutputFactory is the xmlOutputFactory to set.
   */
  public void setXmlOutputFactory(XMLOutputFactory xmlOutputFactory) {

    getInitializationState().requireNotInitilized();
    this.xmlOutputFactory = xmlOutputFactory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.valueConverter == null) {
      this.valueConverter = StringValueConverterImpl.getInstance();
    }
    if (this.xmlOutputFactory == null) {
      this.xmlOutputFactory = XMLOutputFactory.newInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  public XMLStreamWriter createXmlStreamWriter(OutputStream out) throws XMLStreamException {

    return getXmlOutputFactory().createXMLStreamWriter(out);
  }

  /**
   * {@inheritDoc}
   */
  public XMLStreamWriter createXmlStreamWriter(Writer writer) throws XMLStreamException {

    return getXmlOutputFactory().createXMLStreamWriter(writer);
  }

  /**
   * {@inheritDoc}
   */
  public <V> V parseAttribute(XMLStreamReader xmlReader, String namespaceUri,
      String localAttributeName, Class<V> type) throws ValueException {

    String value = xmlReader.getAttributeValue(namespaceUri, localAttributeName);
    String valueSource = xmlReader.getLocalName() + "/@" + localAttributeName;
    return getValueConverter().convertValue(value, valueSource, type);
  }

  /**
   * {@inheritDoc}
   */
  public <V> V parseAttribute(XMLStreamReader xmlReader, String namespaceUri,
      String localAttributeName, Class<V> type, V defaultValue) throws ValueException {

    String value = xmlReader.getAttributeValue(namespaceUri, localAttributeName);
    return getValueConverter().convertValue(value, localAttributeName, type, type, defaultValue);
  }

  /**
   * {@inheritDoc}
   */
  public String readText(XMLStreamReader xmlReader) throws XMLStreamException {

    int eventType = xmlReader.getEventType();
    if (eventType == XMLStreamConstants.START_ELEMENT) {
      eventType = xmlReader.next();
    }
    while (eventType == XMLStreamConstants.ATTRIBUTE) {
      eventType = xmlReader.next();
    }
    if (eventType == XMLStreamConstants.END_ELEMENT) {
      return "";
    }
    if ((eventType == XMLStreamConstants.CHARACTERS) || (eventType == XMLStreamConstants.CDATA)) {
      return xmlReader.getText();
    }
    throw new IllegalStateException("Not implemented!");
  }

  /**
   * {@inheritDoc}
   */
  public void skipOpenElement(XMLStreamReader xmlReader) throws XMLStreamException {

    int depth = 1;
    do {
      int eventType = xmlReader.nextTag();
      if (eventType == XMLStreamConstants.START_ELEMENT) {
        depth++;
        // } else if (eventType == XMLStreamConstants.END_ELEMENT) {
      } else {
        depth--;
        if (depth == 0) {
          return;
        }
      }
    } while (true);
  }

  /**
   * {@inheritDoc}
   */
  public String getEventTypeName(int eventType) {

    switch (eventType) {
      case XMLStreamConstants.ATTRIBUTE:
        return "ATTRIBUTE";
      case XMLStreamConstants.CDATA:
        return "CDATA";
      case XMLStreamConstants.CHARACTERS:
        return "CHARACTERS";
      case XMLStreamConstants.COMMENT:
        return "COMMENT";
      case XMLStreamConstants.DTD:
        return "DTD";
      case XMLStreamConstants.END_DOCUMENT:
        return "END_DOCUMENT";
      case XMLStreamConstants.END_ELEMENT:
        return "END_ELEMENT";
      case XMLStreamConstants.ENTITY_DECLARATION:
        return "ENTITY_DECLARATION";
      case XMLStreamConstants.ENTITY_REFERENCE:
        return "ENTITY_REFERENCE";
      case XMLStreamConstants.NAMESPACE:
        return "NAMESPACE";
      case XMLStreamConstants.NOTATION_DECLARATION:
        return "NOTATION_DECLARATION";
      case XMLStreamConstants.PROCESSING_INSTRUCTION:
        return "PROCESSING_INSTRUCTION";
      case XMLStreamConstants.SPACE:
        return "SPACE";
      case XMLStreamConstants.START_DOCUMENT:
        return "START_DOCUMENT";
      case XMLStreamConstants.START_ELEMENT:
        return "START_ELEMENT";
      default :
        return "UNKNOWN_EVENT_TYPE (" + String.valueOf(eventType) + ")";
    }
  }

  /*
   * public void writeToDom(XMLStreamReader xmlReader, Node node) throws
   * XMLStreamException {
   * 
   * int nodeType = node.getNodeType(); int eventType =
   * xmlReader.getEventType();
   * 
   * }
   * 
   * public void readFromDom(Node node, XMLStreamWriter xmlWriter) throws
   * XMLStreamException {
   * 
   * }
   */

}
