/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * This is the interface for a {@link CollectionFactory} that
 * {@link #create() creates} instances of {@link Set}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SortedSetFactory extends CollectionFactory<SortedSet> {

  /**
   * {@inheritDoc}
   */
  <E> SortedSet<E> create();

  /**
   * {@inheritDoc}
   */
  <E> SortedSet<E> create(int capacity);

  /** The default instance creating a {@link TreeSet}. */
  SortedSetFactory INSTANCE_TREE_SET = new AbstractSortedSetFactory() {

    /**
     * {@inheritDoc}
     */
    public Class<? extends SortedSet> getCollectionImplementation() {

      return TreeSet.class;
    }

    /**
     * {@inheritDoc}
     */
    public <E> SortedSet<E> create() {

      return new TreeSet<E>();
    }

    /**
     * {@inheritDoc}
     */
    public <E> SortedSet<E> create(int capacity) {

      // capacity does NOT make sense here...
      return new TreeSet<E>();
    }
  };

}
