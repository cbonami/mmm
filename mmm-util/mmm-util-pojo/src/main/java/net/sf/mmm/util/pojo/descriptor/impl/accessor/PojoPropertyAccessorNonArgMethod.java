/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorMethod;
import net.sf.mmm.util.reflect.AccessFailedException;
import net.sf.mmm.util.reflect.InvocationFailedException;
import net.sf.mmm.util.reflect.ReflectionUtil;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArg}
 * interface for accessing a {@link Method}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorNonArgMethod extends AbstractPojoPropertyAccessorMethod implements
    PojoPropertyAccessorNonArg {

  /** @see #getMode() */
  private final PojoPropertyAccessorNonArgMode mode;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param mode is the {@link #getMode() mode} of access.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param configuration is the {@link PojoDescriptorConfiguration} to use.
   * @param method is the {@link #getMethod() method} to access.
   */
  public PojoPropertyAccessorNonArgMethod(String propertyName, Type propertyType,
      PojoPropertyAccessorNonArgMode mode, PojoDescriptor<?> descriptor,
      PojoDescriptorConfiguration configuration, Method method) {

    super(propertyName, propertyType, mode, descriptor, configuration, method);
    this.mode = mode;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance) {

    try {
      return getMethod().invoke(pojoInstance, ReflectionUtil.NO_ARGUMENTS);
    } catch (IllegalAccessException e) {
      throw new AccessFailedException(e, getMethod());
    } catch (InvocationTargetException e) {
      throw new InvocationFailedException(e, getMethod(), pojoInstance);
    }
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return this.mode;
  }

}
