/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.SortedSet;

/**
 * This is the abstract base implementation of the {@link SortedSetFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSortedSetFactory implements SortedSetFactory {

  /**
   * The constructor.
   */
  public AbstractSortedSetFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<SortedSet> getCollectionInterface() {

    return SortedSet.class;
  }

  /**
   * {@inheritDoc}
   */
  public SortedSet createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public SortedSet createGeneric(int capacity) {

    return create(capacity);
  }

}
