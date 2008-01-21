/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.mmm.util.nls.base.NlsIllegalArgumentException;

/**
 * This class is a collection of utility functions for reflectively dealing with
 * {@link Collections}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class CollectionUtil {

  /**
   * This is the singleton instance of this {@link CollectionUtil}. Instead of
   * declaring the methods static, we declare this static instance what gives
   * the same way of access while still allowing a design for extension by
   * inheriting from this class.
   */
  public static final CollectionUtil INSTANCE = new CollectionUtil();

  /** @see #create(Class) */
  @SuppressWarnings("unchecked")
  private static final Map<Class<? extends Collection>, Class<? extends Collection>> COLLECTION_IMPLEMENTATIONS = new HashMap<Class<? extends Collection>, Class<? extends Collection>>();

  static {
    COLLECTION_IMPLEMENTATIONS.put(Collection.class, ArrayList.class);
    COLLECTION_IMPLEMENTATIONS.put(List.class, ArrayList.class);
    COLLECTION_IMPLEMENTATIONS.put(Set.class, HashSet.class);
    COLLECTION_IMPLEMENTATIONS.put(SortedSet.class, TreeSet.class);
    COLLECTION_IMPLEMENTATIONS.put(Queue.class, LinkedList.class);
    COLLECTION_IMPLEMENTATIONS.put(Deque.class, LinkedList.class);
  }

  /**
   * The constructor.
   */
  protected CollectionUtil() {

    super();
  }

  /**
   * This method gets the implementation to use for the given
   * <code>collectionInterface</code>.
   * 
   * @param collectionInterface is the interface of a specific collection.
   * @return the implementation to used for the given
   *         <code>collectionInterface</code>.
   */
  @SuppressWarnings("unchecked")
  protected Class<? extends Collection> getCollectionImplementation(
      Class<? extends Collection> collectionInterface) {

    return COLLECTION_IMPLEMENTATIONS.get(collectionInterface);
  }

  /**
   * This method creates a collection implementing the given collection
   * <code>type</code>.
   * 
   * @param <C> is the templated type of the collection.
   * @param type is the type of collection to create. This is either an
   *        interface ({@link List}, {@link Set}, {@link Queue}, etc.) or a
   *        non-abstract implementation of a {@link Collection}.
   * @return the new instance
   * @throws InstantiationException if the given <code>type</code> is a class
   *         that can NOT be instantiated via {@link Class#newInstance()}.
   * @throws IllegalAccessException if you do NOT have access to create an
   *         instance of <code>type</code>.
   */
  @SuppressWarnings("unchecked")
  public <C extends Collection> C create(Class<C> type) throws InstantiationException,
      IllegalAccessException {

    Class<C> implementation;
    if (type.isInterface()) {
      implementation = (Class<C>) getCollectionImplementation(type);
      if (implementation == null) {
        throw new IllegalArgumentException("Unknown collection interface: " + type);
      }
    } else {
      implementation = type;
      if (Modifier.isAbstract(implementation.getModifiers())) {
        throw new IllegalArgumentException("Can NOT instantiate abstract class: " + type);
      }
    }
    return implementation.newInstance();
  }

  /**
   * This method gets the size of the given <code>arrayMapOrCollection</code>.
   * If <code>arrayMapOrCollection</code> is an array, then its
   * {@link Array#getLength(Object) length} is returned. If it is a {@link Map}
   * or {@link Collection}, its {@link Collection#size() size} is returned.
   * 
   * @param arrayMapOrCollection the array, {@link Map} or {@link Collection}.
   * @return the length or size of <code>arrayMapOrCollection</code>.
   * @throws NlsIllegalArgumentException if the given
   *         <code>arrayMapOrCollection</code> is invalid (<code>null</code>
   *         or neither array nor {@link Map} or {@link Collection}).
   */
  public int getSize(Object arrayMapOrCollection) throws NlsIllegalArgumentException {

    if (arrayMapOrCollection != null) {
      Class<?> type = arrayMapOrCollection.getClass();
      if (type.isArray()) {
        return Array.getLength(arrayMapOrCollection);
      } else if (Collection.class.isAssignableFrom(type)) {
        return ((Collection<?>) arrayMapOrCollection).size();
      } else if (Map.class.isAssignableFrom(type)) {
        return ((Map<?, ?>) arrayMapOrCollection).size();
      }
    }
    throw new NlsIllegalArgumentException(arrayMapOrCollection);
  }

  /**
   * This method gets the item at the given <code>index</code> from
   * <code>arrayOrCollection</code>.
   * 
   * @see List#get(int)
   * 
   * @param arrayOrList is the array or {@link List}.
   * @param index is the position of the requested item.
   * @return the item at the given <code>index</code>.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code>
   *         is invalid (<code>null</code> or neither array nor {@link List}).
   */
  public Object get(Object arrayOrList, int index) throws NlsIllegalArgumentException {

    if (arrayOrList != null) {
      Class<?> type = arrayOrList.getClass();
      if (type.isArray()) {
        return Array.get(arrayOrList, index);
      } else if (List.class.isAssignableFrom(type)) {
        return ((List<?>) arrayOrList).get(index);
      }
    }
    throw new NlsIllegalArgumentException(arrayOrList);
  }

  /**
   * This method sets the given <code>item</code> at the given
   * <code>index</code> in <code>arrayOrCollection</code>.
   * 
   * @see List#set(int, Object)
   * 
   * @param arrayOrList is the array or {@link List}.
   * @param index is the position where to set the item.
   * @param item is the item to set.
   * @throws NlsIllegalArgumentException if the given <code>arrayOrList</code>
   *         is invalid (<code>null</code> or neither array nor {@link List}).
   */
  @SuppressWarnings("unchecked")
  public void set(Object arrayOrList, int index, Object item) throws NlsIllegalArgumentException {

    if (arrayOrList != null) {
      Class<?> type = arrayOrList.getClass();
      if (type.isArray()) {
        Array.set(arrayOrList, index, item);
        return;
      } else if (List.class.isAssignableFrom(type)) {
        ((List) arrayOrList).set(index, item);
        return;
      }
    }
    throw new NlsIllegalArgumentException(arrayOrList);
  }

}
