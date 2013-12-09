/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

/**
 * This enum contains the possible values for the sort order of a list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public enum SortOrder implements SimpleDatatype<String> {

  /** Indicating that values are in increasing order (e.g. "1, 2, 3"). */
  ASCENDING("asc", "ascending"),

  /** Indicating that values are in decreasing order (e.g. "3, 2, 1"). */
  DESCENDING("desc", "descending");

  /** @see #getValue() */
  private final String value;

  /** @see #getTitle() */
  private final String title;

  /**
   * The constructor.
   * 
   * @param value is the {@link #getValue() raw value} (symbol).
   * @param title is the {@link #getTitle() title}.
   */
  private SortOrder(String value, String title) {

    this.value = value;
    this.title = title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getTitle() {

    return this.title;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getTitle();
  }

}
