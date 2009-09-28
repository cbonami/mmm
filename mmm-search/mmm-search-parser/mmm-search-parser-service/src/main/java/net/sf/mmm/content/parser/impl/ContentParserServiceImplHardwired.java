/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.parser.impl;

import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.content.parser.api.ContentParser;
import net.sf.mmm.content.parser.base.AbstractContentParser;

/**
 * This is a variant of {@link ContentParserServiceImpl} that can be used easier
 * without a IoC-framework such as spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ContentParserServiceImplHardwired extends ContentParserServiceImpl {

  /**
   * The constructor.
   * 
   */
  public ContentParserServiceImplHardwired() {

    super();
  }

  /**
   * @see #addParser(ContentParser, String...)
   * 
   * @param parser is the parser to register.
   */
  public void addParser(AbstractContentParser parser) {

    parser.initialize();
    super.addParser(parser, parser.getRegistryKeys());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();

    addParser(new ContentParserPdf());
    addParser(new ContentParserHtml());
    addParser(new ContentParserDoc());
    addParser(new ContentParserXls());
    addParser(new ContentParserPpt());

    addParser(new ContentParserOdb());
    addParser(new ContentParserOdc());
    addParser(new ContentParserOdf());
    addParser(new ContentParserOdg());
    addParser(new ContentParserOdi());
    addParser(new ContentParserOdm());
    addParser(new ContentParserOds());
    addParser(new ContentParserOdt());
    addParser(new ContentParserOtg());
    addParser(new ContentParserOth());
    addParser(new ContentParserOtp());
    addParser(new ContentParserOts());
    addParser(new ContentParserOtt());

    addParser(new ContentParserXml());
    // even.txt files may contain markup (e.g. twiki data files).
    addParser(new ContentParserTextMarkupAware());
    addParser(new ContentParserText());
    addParser(new ContentParserJava());

    Map<String, String> alias2keyMap = new HashMap<String, String>();
    alias2keyMap.put("htm", "html");
    alias2keyMap.put("php", "html");
    alias2keyMap.put("jsp", "html");
    alias2keyMap.put("jinc", "text-with-markup");
    alias2keyMap.put("asc", "txt");
    alias2keyMap.put("apt", "txt");
    alias2keyMap.put("csv", "txt");
    alias2keyMap.put("sql", "txt");
    alias2keyMap.put("bat", "txt");
    alias2keyMap.put("sh", "txt");
    alias2keyMap.put("c", "txt");
    alias2keyMap.put("h", "txt");
    alias2keyMap.put("cpp", "txt");
    alias2keyMap.put("c++", "txt");
    alias2keyMap.put("cs", "txt");
    alias2keyMap.put("net", "txt");
    alias2keyMap.put("pl", "txt");
    alias2keyMap.put("py", "txt");
    alias2keyMap.put("js", "txt");
    alias2keyMap.put("dot", "doc");
    setAlias2keyMap(alias2keyMap);

  }
}
