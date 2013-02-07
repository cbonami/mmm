/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import net.sf.mmm.search.NlsBundleSearchApi;
import net.sf.mmm.search.api.SearchException;

/**
 * This is the exception thrown if a given {@link net.sf.mmm.search.engine.api.SearchHit#getId() entry ID} is
 * invalid.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SearchEntryIdInvalidException extends SearchException {

  /** UID for serialization. */
  private static final long serialVersionUID = 3613794146407350907L;

  /** @see #getCode() */
  public static final String MESSAGE_CODE = "SearchEntryInvalid";

  /**
   * The constructor.
   * 
   * @param entryId is the invalid entry ID.
   */
  public SearchEntryIdInvalidException(String entryId) {

    super(NlsBundleSearchApi.ERR_ID_INVALID, toMap(KEY_ID, entryId));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param entryId is the invalid entry ID.
   */
  public SearchEntryIdInvalidException(Throwable nested, String entryId) {

    super(nested, NlsBundleSearchApi.ERR_ID_INVALID, toMap(KEY_ID, entryId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCode() {

    return MESSAGE_CODE;
  }

}
