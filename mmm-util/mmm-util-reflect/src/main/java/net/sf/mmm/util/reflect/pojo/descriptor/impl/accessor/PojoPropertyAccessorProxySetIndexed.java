/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl.accessor;

import java.lang.reflect.Type;

import net.sf.mmm.util.GenericBean;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorIndexedOneArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapterComponentType;
import net.sf.mmm.util.reflect.pojo.descriptor.impl.PojoDescriptorConfiguration;

/**
 * This is the implementation of the {@link PojoPropertyAccessorIndexedOneArg}
 * interface for
 * {@link PojoPropertyAccessorIndexedOneArgMode#SET_INDEXED setting} an indexed
 * property using the getter from another accessor returning an array or
 * {@link java.util.List}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxySetIndexed extends
    AbstractPojoPropertyAccessorProxyAdapterComponentType implements
    PojoPropertyAccessorIndexedOneArg {

  /** The according setter to use if array has to be resized. */
  private final PojoPropertyAccessorOneArg containerSetAccessor;

  /**
   * The constructor.
   * 
   * @param configuration is the configuration to use.
   * @param containerGetAccessor is the accessor delegate that gets an array, or
   *        {@link java.util.List} property.
   * @param containerSetAccessor is the accessor that sets the array, or
   *        {@link java.util.List} property. May be <code>null</code> if NOT
   *        available.
   */
  public PojoPropertyAccessorProxySetIndexed(PojoDescriptorConfiguration configuration,
      PojoPropertyAccessorNonArg containerGetAccessor,
      PojoPropertyAccessorOneArg containerSetAccessor) {

    super(configuration, containerGetAccessor);
    this.containerSetAccessor = containerSetAccessor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorIndexedOneArgMode getMode() {

    return PojoPropertyAccessorIndexedOneArgMode.SET_INDEXED;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Type getReturnType() {

    return getReturnClass();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return void.class;
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, int index, Object item) {

    Object arrayOrList = getDelegate().invoke(pojoInstance);
    GenericBean<Object> arrayReceiver = new GenericBean<Object>();
    Object result = getConfiguration().getCollectionUtil().set(arrayOrList, index, item,
        arrayReceiver);
    if ((arrayReceiver.getValue() != null) && (this.containerSetAccessor != null)) {
      this.containerSetAccessor.invoke(pojoInstance, arrayReceiver.getValue());
    }
    return result;
  }

}
