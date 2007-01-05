/* $Id$ */
package net.sf.mmm.search.engine.api;

import net.sf.mmm.search.api.SearchEntry;

/**
 * This is the interface for a single hit of a search. It represents any content
 * (web-page, document, etc.) that is represented by a single
 * {@link #getUri() URI}.<br>
 * ATTENTION: Depending on the implementation it might be expensive to call
 * methods multiple times.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SearchHit extends SearchEntry {

  /**
   * This method gets the ID of the entry in the search index represented by
   * this hit.
   * 
   * @return the ID of this hit.
   */
  String getEntryId();

  /**
   * This method gets the score of this hit. It is a positiv value in the range
   * from <code>0</code> to <code>1</code>, where <code>1</code>
   * represents a perfect hit and <code>0</code> represents a hit that has
   * nothing in common with the search query. A hit will typically NOT have a
   * {@link #getScore() score} of <code>0</code> but the score may be close to
   * <code>0</code>.
   * 
   * @return the score of this hit.
   */
  double getScore();

  /**
   * This method gets the score of this hit as an integer value in the range
   * from <code>0</code> to <code>maximum</code>. The higher the score the
   * better the hit. This method is useful to view the score as a percentage
   * value (with a <code>maximum</code> of <code>100</code>) or a number of
   * stars (e.g. with a <code>maximum</code> of <code>5</code>).
   * 
   * @see #getScore()
   * 
   * @param maximum
   *        is the maximum score
   * @return the score of this hit.
   */
  int getScore(int maximum);

  /**
   * This method gets an excerpt of the plain text of the content that contains
   * terms of the search query as a highlighted html fragment.
   * 
   * @see #getText()
   * 
   * @return the highlighted text of this hit.
   */
  String getHighlightedText();

}