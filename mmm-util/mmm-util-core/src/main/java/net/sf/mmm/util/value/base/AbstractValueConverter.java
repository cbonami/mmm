/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.base;

import javax.annotation.Resource;

import net.sf.mmm.util.component.AbstractLoggable;
import net.sf.mmm.util.reflect.ReflectionUtil;
import net.sf.mmm.util.value.api.ValueConverter;

/**
 * This is the abstract base-implementation of the {@link ValueConverter}
 * interface.
 * 
 * @param <SOURCE> is the generic {@link #getSourceType() source-type}.
 * @param <TARGET> is the generic {@link #getTargetType() target-type}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractValueConverter<SOURCE, TARGET> extends AbstractLoggable implements
    ValueConverter<SOURCE, TARGET> {

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /**
   * The constructor.
   */
  public AbstractValueConverter() {

    super();
  }

  /**
   * This method gets the {@link ReflectionUtil} instance to use.
   * 
   * @return the {@link ReflectionUtil} to use.
   */
  public ReflectionUtil getReflectionUtil() {

    return this.reflectionUtil;
  }

  /**
   * @param reflectionUtil is the reflectionUtil to set
   */
  @Resource
  public void setReflectionUtil(ReflectionUtil reflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.reflectionUtil = reflectionUtil;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtil.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  public final TARGET convert(SOURCE value, Object valueSource, Class<? extends TARGET> targetClass) {

    return convert(value, valueSource, getReflectionUtil().createGenericType(targetClass));
  }

}
