/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

/**
 * This is the interface of a "lightweight" and
 * {@link java.util.Collections#unmodifiableCollection(java.util.Collection) unmodifiable}
 * {@link java.util.Collection Collection}.
 * 
 * @param <E>
 *        is the templated element type that can be {@link #iterator() iterated}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SizedIterable<E> extends Iterable<E> {

  /**
   * This method determines the size of this iterable. The
   * {@link #iterator() iterator} will iterate this number of elements (if NOT
   * concurrently modified).
   * 
   * @see java.util.Collection#size()
   * 
   * @return the number of elements in this iterable.
   */
  int getSize();

}
