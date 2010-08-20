/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.indexer.impl;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import net.sf.mmm.search.api.SearchEntry;
import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchEntryIdInvalidException;
import net.sf.mmm.search.base.SearchPropertyValueInvalidException;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.base.AbstractSearchIndexer;
import net.sf.mmm.search.indexer.base.SearchAddFailedException;
import net.sf.mmm.search.indexer.base.SearchRemoveFailedException;
import net.sf.mmm.util.component.api.ResourceMissingException;
import net.sf.mmm.util.io.api.IoMode;
import net.sf.mmm.util.io.api.RuntimeIoException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexModifier;
import org.apache.lucene.index.Term;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.search.indexer.api.SearchIndexer} interface using lucene as
 * underlying search-engine.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class LuceneSearchIndexer extends AbstractSearchIndexer {

  /** the index modifier */
  private IndexModifier indexModifier;

  /** @see #setAnalyzer(Analyzer) */
  private Analyzer analyzer;

  /** @see #setIndexPath(String) */
  private String indexPath;

  /** @see #setUpdate(boolean) */
  private boolean update;

  /**
   * The constructor.
   */
  public LuceneSearchIndexer() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param indexModifier is the index modifier to use.
   */
  public LuceneSearchIndexer(IndexModifier indexModifier) {

    super();
    this.indexModifier = indexModifier;
    initialize();
  }

  /**
   * This method sets the lucene analyzer used by this search engine.
   * 
   * @param luceneAnalyzer the analyzer to set
   */
  @Inject
  public void setAnalyzer(Analyzer luceneAnalyzer) {

    getInitializationState().requireNotInitilized();
    this.analyzer = luceneAnalyzer;
  }

  /**
   * @param modifier the modifier to set
   */
  public void setIndexModifier(IndexModifier modifier) {

    getInitializationState().requireNotInitilized();
    this.indexModifier = modifier;
  }

  /**
   * This method sets the path in the local filesystem where the search-index is
   * located. You can also use {@link #setIndexModifier(IndexModifier)} instead.
   * 
   * @param searchIndexPath the indexPath to set
   */
  public void setIndexPath(String searchIndexPath) {

    getInitializationState().requireNotInitilized();
    this.indexPath = searchIndexPath;
  }

  /**
   * This method sets the update flag. If set to <code>true</code>, the index is
   * updated if it already exists. Else if <code>false</code>, a new index will
   * be created when the indexing is started. The default is <code>false</code>.
   * 
   * @param update the update flag to set.
   */
  public void setUpdate(boolean update) {

    getInitializationState().requireNotInitilized();
    this.update = update;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.indexModifier == null) {
      if (this.indexPath == null) {
        throw new ResourceMissingException("indexPath");
      }
      if (this.analyzer == null) {
        this.analyzer = new StandardAnalyzer();
      }
      File indexDirectory = new File(this.indexPath);
      boolean create;
      if (this.update) {
        if (indexDirectory.isDirectory()) {
          create = false;
        } else {
          indexDirectory.mkdirs();
          create = true;
        }
      } else {
        indexDirectory.mkdirs();
        create = true;
      }
      try {
        this.indexModifier = new IndexModifier(indexDirectory, this.analyzer, create);
      } catch (IOException e) {
        throw new RuntimeIoException(e, IoMode.READ);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void add(MutableSearchEntry entry) throws SearchException {

    try {
      LuceneMutableSearchEntry luceneEntry = (LuceneMutableSearchEntry) entry;
      Document luceneDocument = luceneEntry.getLuceneDocument();
      this.indexModifier.addDocument(luceneDocument);
    } catch (IOException e) {
      throw new SearchAddFailedException(e, entry);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void close() {

    try {
      this.indexModifier.close();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.CLOSE);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void flush() throws SearchException {

    try {
      this.indexModifier.flush();
    } catch (IOException e) {
      throw new RuntimeIoException(e, IoMode.WRITE);
    }
  }

  /**
   * {@inheritDoc}
   */
  public MutableSearchEntry createEntry() {

    return new LuceneMutableSearchEntry(new Document());
  }

  /**
   * {@inheritDoc}
   */
  public void optimize() throws SearchException {

    try {
      this.indexModifier.optimize();
    } catch (IOException e) {
      throw new RuntimeIoException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeById(String entryId) throws SearchException {

    try {
      int docId = Integer.parseInt(entryId);
      // lucene does NOT seem to give use feedback here...
      this.indexModifier.deleteDocument(docId);
      return true;
    } catch (NumberFormatException e) {
      throw new SearchEntryIdInvalidException(e, entryId);
    } catch (IOException e) {
      throw new SearchRemoveFailedException("ID", entryId);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int removeByUid(String uid) throws SearchException {

    return remove(SearchEntry.PROPERTY_UID, uid);
  }

  /**
   * {@inheritDoc}
   */
  public int removeByUri(String uri) throws SearchException {

    return remove(SearchEntry.PROPERTY_URI, uri);
  }

  /**
   * {@inheritDoc}
   */
  public int remove(String property, String value) throws SearchException {

    if ((value == null) || (value.length() == 0)) {
      throw new SearchPropertyValueInvalidException(property, value);
    }
    try {
      return this.indexModifier.deleteDocuments(new Term(property, value));
    } catch (IOException e) {
      throw new SearchRemoveFailedException(property, value);
    }
  }

}
