/* $ Id: $ */
package net.sf.mmm.xml.test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import junit.framework.TestCase;

import net.sf.mmm.value.api.ValueManagerIF;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.impl.type.XmlValueManager;
import net.sf.mmm.xml.DomUtil;
import net.sf.mmm.xml.XmlUtil;
import net.sf.mmm.xml.api.XmlException;
import net.sf.mmm.xml.api.XmlWriterIF;
import net.sf.mmm.xml.impl.DomXmlWriter;
import net.sf.mmm.xml.impl.OutputXmlWriter;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class XmlWriterTest extends TestCase {

  /**
   * The constructor.
   * 
   */
  public XmlWriterTest() {

    super();
  }

  /**
   * This method produces some strange xml for testing.
   * 
   * @param xmlWriter
   *        is the serializer where to create the xml.
   * @throws XmlException
   *         if the xml is illegal or general error.
   */
  public static void toXml(XmlWriterIF xmlWriter) throws XmlException {

    String ns3 = "http://URI3";
    // xmlWriter.writeStartElement(ValueManagerIF.XML_TAG_VALUE);
    // xmlWriter.writeAttribute(ValueManagerIF.XML_ATR_VALUE_NAME,
    // XmlValueManager.VALUE_NAME);
    xmlWriter.writeStartElement("tag1");
    xmlWriter.writeNamespaceDeclaration("ns3", ns3);
    xmlWriter.writeAttribute("atr", "val");
    xmlWriter.writeCharacters("Hello World!&<>�������");
    xmlWriter.writeStartElement("tag2", "ns2", "http://URI2");
    xmlWriter.writeAttribute("atr", "val&<>\"");
    xmlWriter.writeCharacters("Hello World!");
    xmlWriter.writeEndElement("tag2", "ns2");
    xmlWriter.writeStartElement("tag3", "ns3");
    xmlWriter.writeAttribute("atr", "val");
    xmlWriter.writeEndElement("tag3", "ns3");
    xmlWriter.writeEndElement("tag1");
    // xmlWriter.writeEndElement(ValueManagerIF.XML_TAG_VALUE);
  }

  /**
   * This method does the test.
   * 
   * @throws XmlException
   *         on xml problem.
   * @throws ValueParseException
   *         on parsing problem.
   */
  @Test
  public void testXml() throws XmlException, ValueParseException {

    /**
     * XMLStreamWriter xmlSerializer =
     * StaxUtil.createXmlStreamWriter(System.out);
     * xmlSerializer.writeStartDocument(); toXml(xmlSerializer);
     * xmlSerializer.writeEndDocument(); xmlSerializer.close();
     */
    System.out.println(manager.toString());
    Document doc = DomUtil.createDocument();
    XmlWriterIF domWriter = new DomXmlWriter(doc);
    toXml(domWriter);
    Element rootElement = doc.getDocumentElement();
    String xmlString = manager.toString(rootElement);
    System.out.println(xmlString);
    System.out.println("--");

    Element root2Element = manager.parse(xmlString);
    String xmlString2 = manager.toString(root2Element);
    System.out.println(xmlString2);
    System.out.println("--");
    // assertEquals(xmlString, xmlString2);
    StringWriter sw = new StringWriter();
    XmlWriterIF outSerializer = new OutputXmlWriter(sw, null, "UTF-8");
    toXml(outSerializer);
    System.out.println(sw.toString());
    System.out.println("--");
  }

  /**
   * @param args
   */
  public static void main(String[] args) throws IOException {

    Writer w = XmlUtil.createXmlAttributeWriter(new OutputStreamWriter(System.out));
    w.write("<&>abc\"def<ghi>");
    w.flush();
  }

}
