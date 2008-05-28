/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.base.accessor;

import java.lang.reflect.Type;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the abstract base-implementation of the {@link PojoPropertyAccessor}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractPojoPropertyAccessor implements PojoPropertyAccessor {

  /** @see #getName() */
  private final String name;

  /** @see #getPropertyType() */
  private final GenericType<?> propertyType;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param descriptor is the descriptor this accessor is intended for.
   * @param configuration is the {@link PojoDescriptorConfiguration} to use.
   */
  public AbstractPojoPropertyAccessor(String propertyName, Type propertyType,
      PojoDescriptor<?> descriptor, PojoDescriptorConfiguration configuration) {

    super();
    this.name = propertyName;
    ReflectionUtil util = configuration.getReflectionUtil();
    this.propertyType = util.createGenericType(propertyType, descriptor.getPojoType());
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {

    return this.name;
  }

  /**
   * {@inheritDoc}
   */
  public GenericType<?> getPropertyType() {

    return this.propertyType;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getPropertyClass() {

    if (getMode().isReading()) {
      return this.propertyType.getUpperBound();
    } else {
      return this.propertyType.getLowerBound();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getMode() + "-accessor of property '" + this.name + "' with type " + this.propertyType
        + "(" + getClass().getSimpleName() + ")";
  }

}
