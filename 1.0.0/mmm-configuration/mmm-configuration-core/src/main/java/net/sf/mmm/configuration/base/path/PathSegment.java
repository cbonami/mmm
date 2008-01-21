/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base.path;

import net.sf.mmm.configuration.base.path.condition.Condition;

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
   * @param name is the {@link #getString() string} to match.
   * @param filter is the {@link #getCondition() condition}. Use
   *        {@link Condition#EMPTY_CONDITION} for no condition.
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

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return super.toString() + this.condition.toString();
  }

}
