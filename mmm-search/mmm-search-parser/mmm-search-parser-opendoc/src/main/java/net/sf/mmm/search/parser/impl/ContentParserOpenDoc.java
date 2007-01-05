/* $Id$ */
package net.sf.mmm.search.parser.impl;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import net.sf.mmm.search.parser.api.ContentParser;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.parser.api.ContentParser} interface for content of
 * the open-document format (content with the mimetypes
 * "application/vnd.oasis.opendocument.*").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserOpenDoc implements ContentParser {

  /** the content.xml entry of the document */
  private static final String ENTRY_CONTENT_XML = "content.xml";

  /** the meta.xml entry of the document */
  private static final String ENTRY_META_XML = "meta.xml";

  /**
   * The constructor
   */
  public ContentParserOpenDoc() {

    super();
  }

  /**
   * @see net.sf.mmm.search.parser.api.ContentParser#parse(java.io.InputStream,
   *      long)
   */
  public Properties parse(InputStream inputStream, long filesize) throws Exception {

    Properties properties = new Properties();
    ZipInputStream zipInputStream = new ZipInputStream(inputStream);
    // new BufferedInputStream(zipInputStream);
    ZipEntry zipEntry = zipInputStream.getNextEntry();
    while (zipEntry != null) {
      if (zipEntry.getName().equals(ENTRY_CONTENT_XML)) {
        XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(zipInputStream);
        streamReader.getLocalName();
      } else if (zipEntry.getName().equals(ENTRY_META_XML)) {
        XMLStreamReader streamReader = XMLInputFactory.newInstance().createXMLStreamReader(zipInputStream);
      }
      zipInputStream.closeEntry();
      zipEntry = zipInputStream.getNextEntry();
    }
    zipInputStream.close();

    return properties;
  }

}
