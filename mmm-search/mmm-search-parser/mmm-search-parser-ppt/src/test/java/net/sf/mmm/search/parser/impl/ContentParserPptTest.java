/* $Id$ */
package net.sf.mmm.search.parser.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;

import net.sf.mmm.search.parser.api.ContentParser;
import net.sf.mmm.util.reflect.ClasspathUtil;
import net.sf.mmm.util.reflect.ReflectionUtil;

import junit.framework.TestCase;

/**
 * This is the test-case for {@link ContentParserPpt}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ContentParserPptTest extends TestCase {

  /**
   * The constructor
   */
  public ContentParserPptTest() {

    super();
  }

  protected Properties parse(ContentParser parser, String resourceName, long filesize) throws Exception {

    URL resourceURL = ClasspathUtil.getClasspathResource(ContentParserPptTest.class.getPackage(),
        resourceName);
    InputStream inputStream = resourceURL.openStream();
    try {
      return parser.parse(inputStream, filesize);
    } finally {
      inputStream.close();
    }
  }

  public void checkParser(ContentParser parser, long filesize) throws Exception {
    Properties properties = parse(parser, "test.ppt", filesize);
    String title = properties.getProperty(ContentParser.PROPERTY_KEY_TITLE);
    assertEquals("Title of Testslide", title);
    String author = properties.getProperty(ContentParser.PROPERTY_KEY_AUTHOR);
    assertEquals("J�rg Hohwiller", author);
  }
  
  @Test
  public void testParser() throws Exception {

    ContentParser parser = new ContentParserPpt();
    checkParser(parser, 0);
    checkParser(parser, 10752);
  }

}
