/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.framework.base;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.sf.mmm.framework.api.ComponentDescriptor;
import net.sf.mmm.framework.api.ComponentManager;
import net.sf.mmm.framework.api.ExtendedComponentDescriptor;

/**
 * This is the implementation of the {@link ComponentInstantiationManager}
 * interface for multiple singletons with different
 * {@link net.sf.mmm.framework.api.ComponentManager#requestComponent(Class, String) instance-IDs}.
 * 
 * @param <S> is the
 *        {@link ComponentDescriptor#getSpecification() specification} of the
 *        component.
 * @param <I> is the
 *        {@link ExtendedComponentDescriptor#getImplementation() implementation}
 *        of the component.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SingletonComponentInstantiationManager<S, I extends S> extends
    AbstractComponentInstantiationManager<S, I> {

  /** @see #getNewInstanceContainer(String) */
  private final Map<String, ExtendedComponentInstanceContainerImpl<S, I>> instanceContainers;

  /**
   * The constructor.
   */
  public SingletonComponentInstantiationManager() {

    super();
    this.instanceContainers = new HashMap<String, ExtendedComponentInstanceContainerImpl<S, I>>();
  }

  /**
   * @see net.sf.mmm.framework.base.AbstractComponentInstantiationManager#createInstanceContainer(String)
   * 
   * @param instanceId is the
   *        {@link ComponentManager#requestComponent(Class, String) instance-ID}
   *        of the requested component.
   * @return the new instance container for the requested component.
   */
  protected ExtendedComponentInstanceContainerImpl<S, I> getNewInstanceContainer(String instanceId) {

    ExtendedComponentInstanceContainerImpl<S, I> instanceContainer = createInstanceContainer(instanceId);
    this.instanceContainers.put(instanceId, instanceContainer);
    return instanceContainer;
  }

  /**
   * {@inheritDoc}
   */
  public boolean release(ExtendedComponentInstanceContainerImpl<S, I> instanceContainer) {

    return false;
  }

  /**
   * {@inheritDoc}
   */
  public ExtendedComponentInstanceContainerImpl<S, I> request(String instanceId) {

    ExtendedComponentInstanceContainerImpl<S, I> instanceContainer = this.instanceContainers
        .get(instanceId);
    if (instanceContainer == null) {
      instanceContainer = getNewInstanceContainer(instanceId);
    }
    return instanceContainer;
  }

  /**
   * {@inheritDoc}
   */
  public ExtendedComponentInstanceContainerImpl<S, I>[] dispose() {

    Collection<ExtendedComponentInstanceContainerImpl<S, I>> values = this.instanceContainers
        .values();
    ExtendedComponentInstanceContainerImpl[] containers = new ExtendedComponentInstanceContainerImpl[values
        .size()];
    return this.instanceContainers.values().toArray(containers);
  }
}
