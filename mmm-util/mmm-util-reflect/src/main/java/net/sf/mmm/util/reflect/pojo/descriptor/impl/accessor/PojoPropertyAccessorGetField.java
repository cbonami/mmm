/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.descriptor.impl.accessor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.reflect.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.reflect.pojo.descriptor.base.accessor.AbstractPojoPropertyAccessorField;

/**
 * This is the implementation of the {@link PojoPropertyAccessorNonArg}
 * interface for {@link PojoPropertyAccessorNonArgMode#GET getting} a
 * {@link Field}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorGetField extends AbstractPojoPropertyAccessorField implements
    PojoPropertyAccessorNonArg {

  /**
   * The constructor.
   * 
   * @param field is the {@link #getField() field} to access.
   */
  public PojoPropertyAccessorGetField(Field field) {

    super(field);
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance) throws IllegalAccessException,
      InvocationTargetException {

    return getField().get(pojoInstance);
  }

  /**
   * {@inheritDoc}
   */
  public Type[] getArgumentTypes() {

    return ReflectionUtil.NO_TYPES;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorNonArgMode getMode() {

    return PojoPropertyAccessorNonArgMode.GET;
  }

}
