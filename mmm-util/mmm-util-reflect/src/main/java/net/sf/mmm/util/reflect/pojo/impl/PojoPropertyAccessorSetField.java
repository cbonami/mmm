/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.reflect.pojo.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.reflect.pojo.base.AbstractPojoPropertyAccessorField;

/**
 * This is the implementation of the {@link PojoPropertyAccessorOneArg}
 * interface for {@link PojoPropertyAccessorOneArgMode#SET setting} a
 * {@link Field}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPropertyAccessorSetField extends AbstractPojoPropertyAccessorField implements
    PojoPropertyAccessorOneArg {

  /**
   * The constructor.
   * 
   * @param field is the {@link #getField() field} to access.
   */
  public PojoPropertyAccessorSetField(Field field) {

    super(field);
  }

  /**
   * {@inheritDoc}
   */
  public Object invoke(Object pojoInstance, Object argument) throws IllegalAccessException,
      InvocationTargetException {

    getField().set(pojoInstance, argument);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  public PojoPropertyAccessorOneArgMode getMode() {

    return PojoPropertyAccessorOneArgMode.SET;
  }

}
