/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.descriptor.impl.accessor;

import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.base.PojoDescriptorConfiguration;
import net.sf.mmm.util.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorProxyAdapter;
import net.sf.mmm.util.reflect.GenericType;
import net.sf.mmm.util.reflect.SimpleGenericType;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArg}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getting} the size of
 * an array, {@link java.util.List} or {@link java.util.Map} from another
 * accessor.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorProxyGetSize extends AbstractPojoPropertyAccessorProxyAdapter
    implements PojoPropertyAccessorNonArg {

  /**
   * The constructor.
   * 
   * @param configuration is the configuration to use.
   * @param containerGetAccessor is the accessor delegate that gets an array,
   *        map or collection property.
   */
  public PojoPropertyAccessorProxyGetSize(PojoDescriptorConfiguration configuration,
      PojoPropertyAccessorNonArg containerGetAccessor) {

    super(configuration, containerGetAccessor);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET_SIZE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType getPropertyType() {

    return SimpleGenericType.TYPE_INT;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getPropertyClass() {

    return int.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public GenericType getReturnType() {

    return getPropertyType();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Class<?> getReturnClass() {

    return getPropertyClass();
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance) {

    Object arrayMapOrCollection = getDelegate().invoke(pojoInstance);
    int size;
    if (arrayMapOrCollection == null) {
      size = 0;
    } else {
      size = getConfiguration().getCollectionUtil().getSize(arrayMapOrCollection);
    }
    return Integer.valueOf(size);
  }

}
