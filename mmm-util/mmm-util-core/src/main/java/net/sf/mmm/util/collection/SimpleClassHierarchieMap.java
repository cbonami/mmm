/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.Map;

/**
 * This is a simple implementation of {@link AbstractClassHierarchieMap}.
 * 
 * @param <E> is the generic type of the elements contained in this map.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleClassHierarchieMap<E> extends AbstractClassHierarchieMap<E> {

  /**
   * The constructor.
   */
  public SimpleClassHierarchieMap() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param mapFactory is the factory used to create the internal {@link Map}.
   */
  @SuppressWarnings("unchecked")
  public SimpleClassHierarchieMap(MapFactory<Map> mapFactory) {

    super(mapFactory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public E put(Class<?> type, E element) {

    return super.put(type, element);
  }

}
