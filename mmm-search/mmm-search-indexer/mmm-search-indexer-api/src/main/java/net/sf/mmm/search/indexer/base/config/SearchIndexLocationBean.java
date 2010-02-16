/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base.config;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlRootElement;

import net.sf.mmm.search.api.config.SearchSource;
import net.sf.mmm.search.base.config.SearchSourceBean;
import net.sf.mmm.search.indexer.api.config.SearchIndexLocation;
import net.sf.mmm.util.filter.base.FilterRuleChain;
import net.sf.mmm.util.transformer.base.StringTransformerChain;

/**
 * This is the implementation of {@link SearchIndexLocation} as JAXB-ready
 * Java-Bean.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@XmlRootElement(name = "location")
@XmlAccessorType(XmlAccessType.FIELD)
public class SearchIndexLocationBean implements SearchIndexLocation {

  /** @see #getLocation() */
  @XmlAttribute(name = "location")
  private String localtion;

  /** @see #getSource() */
  @XmlIDREF
  @XmlAttribute(name = "source")
  private SearchSourceBean source;

  /** @see #getEncoding() */
  @XmlAttribute(name = "encoding")
  private String encoding;

  /** @see #getFilter() */
  @XmlIDREF
  @XmlAttribute(name = "filter")
  private FilterRuleChain<String> filter;

  /** @see #getUriTransformer() */
  @XmlIDREF
  @XmlAttribute(name = "uri-transformer")
  private StringTransformerChain uriTransformer;

  /**
   * The constructor.
   */
  public SearchIndexLocationBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public String getLocation() {

    return this.localtion;
  }

  /**
   * @param path is the path to set
   */
  public void setLocaltion(String path) {

    this.localtion = path;
  }

  /**
   * {@inheritDoc}
   */
  public SearchSource getSource() {

    return this.source;
  }

  /**
   * @param source is the source to set
   */
  public void setSource(SearchSourceBean source) {

    this.source = source;
  }

  //
  // /**
  // * This method gets the {@link SearchSource#getId() ID} of the
  // * {@link SearchSource Source} associated with this directory.
  // *
  // * @return the {@link SearchSource#getId() ID} of the {@link SearchSource}.
  // */
  // public String getSourceId() {
  //
  // return this.sourceId;
  // }
  //
  // /**
  // * @param source is the source to set
  // */
  // public void setSourceId(String source) {
  //
  // this.sourceId = source;
  // }

  /**
   * {@inheritDoc}
   */
  public String getEncoding() {

    return this.encoding;
  }

  /**
   * @param encoding is the encoding to set
   */
  public void setEncoding(String encoding) {

    this.encoding = encoding;
  }

  /**
   * @return the filter
   */
  public FilterRuleChain<String> getFilter() {

    return this.filter;
  }

  /**
   * @param filter is the filter to set
   */
  public void setFilter(FilterRuleChain<String> filter) {

    this.filter = filter;
  }

  /**
   * {@inheritDoc}
   */
  public StringTransformerChain getUriTransformer() {

    return this.uriTransformer;
  }

  /**
   * @param uriTransformer is the uriTransformer to set
   */
  public void setUriTransformer(StringTransformerChain uriTransformer) {

    this.uriTransformer = uriTransformer;
  }

}