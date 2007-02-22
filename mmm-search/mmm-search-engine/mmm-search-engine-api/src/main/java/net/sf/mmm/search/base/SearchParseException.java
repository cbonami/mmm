/* $Id$ */
package net.sf.mmm.search.base;

import net.sf.mmm.search.NlsResourceBundleSearchEngineApi;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown from
 * {@link net.sf.mmm.search.engine.api.SearchQueryBuilder} if a query could NOT
 * be parsed.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchParseException extends SearchException {

  /** UID for serialization */
  private static final long serialVersionUID = -8896253123083824658L;

  /**
   * The constructor.<br>
   * Please use {@link #SearchParseException(Throwable, String)} to supply the
   * cause of the error.
   * 
   * @param query
   *        is the query that could NOT be parsed.
   */
  public SearchParseException(String query) {

    super(NlsResourceBundleSearchEngineApi.ERR_PARSE, query);
  }

  /**
   * The constructor
   * 
   * @param nested
   *        is the {@link #getCause() cause} of this exception.
   * @param query
   *        is the query that could NOT be parsed.
   */
  public SearchParseException(Throwable nested, String query) {

    super(nested, NlsResourceBundleSearchEngineApi.ERR_PARSE, query);
  }

}
