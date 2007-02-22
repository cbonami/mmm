/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * TODO This type ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SingleConfigurationIterator implements Iterator<AbstractConfiguration> {

  /** the next configuration */
  private AbstractConfiguration next;

  /**
   * The constructor.
   * 
   * @param configuration
   */
  public SingleConfigurationIterator(AbstractConfiguration configuration) {

    super();
    this.next = configuration;
  }

  /**
   * @see java.util.Iterator#hasNext() 
   */
  public boolean hasNext() {

    return (this.next != null);
  }

  /**
   * @see java.util.Iterator#next() 
   */
  public AbstractConfiguration next() {

    if (this.next == null) {
      throw new NoSuchElementException();
    }
    AbstractConfiguration result = this.next;
    this.next = null;
    return result;
  }

  /**
   * @see java.util.Iterator#remove() 
   */
  public void remove() {

    throw new UnsupportedOperationException();
  }

}
