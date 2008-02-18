/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.base.accessor;

import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessor;

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
  private final Type type;

  /** @see #getPropertyClass() */
  private final Class<?> clazz;

  /**
   * The constructor.
   * 
   * @param propertyName is the {@link #getName() name} of the property.
   * @param propertyType is the {@link #getPropertyType() generic type} of the
   *        property.
   * @param propertyClass is the {@link #getPropertyClass() raw type} of the
   *        property.
   */
  public AbstractPojoPropertyAccessor(String propertyName, Type propertyType, Class<?> propertyClass) {

    super();
    this.name = propertyName;
    this.clazz = propertyClass;
    this.type = propertyType;
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
  public Type getPropertyType() {

    return this.type;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getPropertyClass() {

    return this.clazz;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return getMode() + "-accessor of property '" + this.name + "' with type " + this.type + "("
        + getClass().getSimpleName() + ")";
  }

}
