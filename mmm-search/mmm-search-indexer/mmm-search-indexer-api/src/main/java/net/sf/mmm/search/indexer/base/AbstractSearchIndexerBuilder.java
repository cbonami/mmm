/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.api.config.SearchIndexConfiguration;
import net.sf.mmm.search.base.config.SearchIndexConfigurationBean;
import net.sf.mmm.search.indexer.api.SearchIndexer;
import net.sf.mmm.search.indexer.api.SearchIndexerBuilder;
import net.sf.mmm.search.indexer.api.config.SearchIndexerOptions;
import net.sf.mmm.search.indexer.base.config.SearchIndexerOptionsBean;
import net.sf.mmm.util.component.base.AbstractLoggable;

/**
 * This is the abstract base-implementation for the {@link SearchIndexerBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractSearchIndexerBuilder extends AbstractLoggable implements
    SearchIndexerBuilder {

  /**
   * The constructor.
   */
  public AbstractSearchIndexerBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public final SearchIndexer createIndexer(String dataSource, SearchIndexerOptions options)
      throws SearchException {

    SearchIndexConfigurationBean configuration = new SearchIndexConfigurationBean();
    configuration.setLocation(dataSource);
    return createIndexer(configuration, options);
  }

  /**
   * {@inheritDoc}
   */
  public SearchIndexer createIndexer(SearchIndexConfiguration searchIndexConfiguration)
      throws SearchException {

    return createIndexer(searchIndexConfiguration, new SearchIndexerOptionsBean());
  }

}
