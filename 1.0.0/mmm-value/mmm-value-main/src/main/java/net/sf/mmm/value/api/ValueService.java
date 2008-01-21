/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.api;

import java.util.Collection;

import javax.annotation.Resource;

/**
 * This is the interface for the component that manages the values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@Resource
public interface ValueService {

  /**
   * This method gets a generic manager that is capable to (de-)serialize all
   * value-types {@link #getManager(Class) available} in this service.<br>
   * <b>ATTENTION:</b><br>
   * The method {@link ValueManager#fromString(String)} of the returned manager
   * will NOT be efficient.
   * 
   * @return the generic XML serializer.
   */
  ValueManager<Object> getGenericManager();

  /**
   * This method gets the {@link ValueManager value manager} for the given
   * {@link ValueManager#getValueClass() valueType}.
   * 
   * @param <V> is the templated type of the value.
   * @param valueType is the {@link ValueManager#getValueClass() value type} of
   *        the requested manager.
   * @return the manager for the given <code>valueClass</code> or
   *         <code>null</code> if NO such manager is registered.
   */
  <V> ValueManager<V> getManager(Class<V> valueType);

  /**
   * This method gets the {@link ValueManager value manager} for the given
   * <code>valueSubType</code>. Use this method instead of
   * {@link #getManager(Class)} if you do NOT know the precise value type.<br>
   * E.g. if you have {@link ValueManager value-managers} with an abstract
   * {@link ValueManager#getValueClass() value-type} (such as
   * {@link org.w3c.dom.Document}) and you have an instance of a value you can
   * use this method to find the manager for the {@link Object#getClass() class}
   * of the value.
   * 
   * @param <V> is the templated type of the value.
   * @param valueSubType is a sup-type of the requested manager's
   *        {@link ValueManager#getValueClass() value type}.
   * @return the manager for the given <code>valueClass</code> or
   *         <code>null</code> if NO such manager could be found. Be aware
   *         that the {@link ValueManager#getValueClass() value-type} of the
   *         result may return a super-type of the given
   *         <code>valueSubType</code>.
   */
  <V> ValueManager<? super V> findManager(Class<V> valueSubType);

  /**
   * This method gets the value manager for the given name.
   * 
   * @see ValueManager#getName()
   * 
   * @param name is the {@link ValueManager#getName() name} of the value for
   *        which the manager is requested.
   * @return the manager for the given name or <code>null</code> if no such
   *         manager is registered.
   */
  ValueManager<?> getManager(String name);

  /**
   * This method gets all value {@link ValueManager value-managers} registered
   * by this service.
   * 
   * @return a collection containing all registered value managers.
   */
  Collection<ValueManager> getManagers();

}
