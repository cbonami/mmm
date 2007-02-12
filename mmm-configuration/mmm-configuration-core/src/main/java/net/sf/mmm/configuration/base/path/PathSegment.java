/* $Id$ */
package net.sf.mmm.configuration.base.path;

/**
 * This class represents a single segment of a
 * {@link net.sf.mmm.configuration.api.Configuration#getDescendants(String, String) descendant-path}.
 * A segment is a substring of the descendant-path that represents a query for a
 * {@link net.sf.mmm.configuration.base.AbstractConfiguration#getChild(String) child}
 * configuration. It is build out of a name (-pattern) and an optional
 * condition.<br>
 * Examples are "childName", "*", "@a?tr*", "item[@name==foo*]"
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PathSegment extends SimplePathSegment {

  /**
   * the condition or <code>null</code> if any child is acceptable.
   */
  private Condition condition;

  /**
   * The constructor.
   * 
   * @param name
   *        is the {@link #getString() string} to match.
   * @param filter
   *        is the additional {@link #getCondition() condition} or
   *        <code>null</code>.
   */
  public PathSegment(String name, Condition filter) {

    super(name);
    this.condition = filter;
  }

  /**
   * This method gets the condition.
   * 
   * @return the condition.
   */
  public Condition getCondition() {

    return this.condition;
  }

}
