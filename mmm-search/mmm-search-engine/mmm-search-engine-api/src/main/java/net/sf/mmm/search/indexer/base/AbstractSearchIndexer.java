/* $Id$ */
package net.sf.mmm.search.indexer.base;

import net.sf.mmm.search.api.SearchException;
import net.sf.mmm.search.base.SearchUpdateMissingIdException;
import net.sf.mmm.search.indexer.api.MutableSearchEntry;
import net.sf.mmm.search.indexer.api.SearchIndexer;

/**
 * This is the abstract base implementation of the {@link SearchIndexer}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSearchIndexer implements SearchIndexer {

  /**
   * The constructor
   */
  public AbstractSearchIndexer() {

    super();
  }

  /**
   * @see net.sf.mmm.search.indexer.api.SearchIndexer#update(net.sf.mmm.search.indexer.api.MutableSearchEntry)
   */
  public int update(MutableSearchEntry entry) throws SearchException {

    int removeCount;
    String identifier = entry.getUid();
    if (identifier == null) {
      identifier = entry.getUri();
      if (identifier == null) {
        throw new SearchUpdateMissingIdException();
      }
      removeCount = removeByUri(identifier);
    } else {
      removeCount = removeByUid(identifier);
    }
    add(entry);
    return removeCount;
  }

}
