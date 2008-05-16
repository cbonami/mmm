/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.pojo.path.impl;

import javax.annotation.Resource;

import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptor;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorNonArgMode;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArg;
import net.sf.mmm.util.pojo.descriptor.api.accessor.PojoPropertyAccessorOneArgMode;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderImpl;
import net.sf.mmm.util.pojo.path.api.PojoPathContext;
import net.sf.mmm.util.pojo.path.api.PojoPathMode;
import net.sf.mmm.util.pojo.path.base.AbstractPojoPathNavigator;
import net.sf.mmm.util.reflect.api.GenericType;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.util.pojo.path.api.PojoPathNavigator} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathNavigatorImpl extends AbstractPojoPathNavigator {

  /** @see #getDescriptorBuilder() */
  private PojoDescriptorBuilder descriptorBuilder;

  /**
   * The constructor.<br>
   * <b>ATTENTION:</b><br>
   * You need to {@link #initialize()} this component before it can be used.
   */
  public PojoPathNavigatorImpl() {

    super();
  }

  /**
   * This method gets the {@link PojoDescriptorBuilder} used for the underlying
   * reflectional property access.
   * 
   * @return the descriptorBuilder
   */
  protected PojoDescriptorBuilder getDescriptorBuilder() {

    return this.descriptorBuilder;
  }

  /**
   * This method sets the {@link #getDescriptorBuilder() descriptor-builder} to
   * use.
   * 
   * @param descriptorBuilder is the descriptorBuilder to use.
   */
  @Resource
  public void setDescriptorBuilder(PojoDescriptorBuilder descriptorBuilder) {

    getInitializationState().requireNotInitilized();
    this.descriptorBuilder = descriptorBuilder;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.descriptorBuilder == null) {
      PojoDescriptorBuilderImpl builder = new PojoDescriptorBuilderImpl();
      builder.initialize();
      this.descriptorBuilder = builder;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  protected Object getFromPojo(CachingPojoPath currentPath, PojoPathContext context,
      PojoPathState state) {

    CachingPojoPath parentPath = currentPath.getParent();
    PojoDescriptor<?> descriptor = getDescriptorBuilder().getDescriptor(parentPath.getPojoType());
    PojoPropertyAccessorNonArg getter = descriptor.getAccessor(currentPath.getSegment(),
        PojoPropertyAccessorNonArgMode.GET, !state.isGetType());
    if (getter == null) {
      // TODO
      // throw new PojoPathAccessException();
      return null;
    }
    GenericType pojoType = getter.getPropertyType();
    currentPath.setPojoType(pojoType);
    Class pojoClass = getter.getPropertyClass();
    currentPath.setPojoClass(pojoClass);
    Object result = null;
    if (!state.isGetType()) {
      Object parentPojo = parentPath.getPojo();
      result = getter.invoke(parentPojo);
      if ((result == null) && (state.getMode() == PojoPathMode.CREATE_IF_NULL)) {
        result = context.getPojoFactory().newInstance(pojoClass);
        setInPojo(currentPath, context, state, parentPojo, result);
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @SuppressWarnings("unchecked")
  protected Object setInPojo(CachingPojoPath currentPath, PojoPathContext context,
      PojoPathState state, Object parentPojo, Object value) {

    PojoDescriptor<?> descriptor = getDescriptorBuilder().getDescriptor(parentPojo.getClass());
    PojoPropertyAccessorOneArg setAccessor = descriptor.getAccessor(currentPath.getSegment(),
        PojoPropertyAccessorOneArgMode.SET, true);
    GenericType targetType = setAccessor.getPropertyType();
    currentPath.setPojoType(targetType);
    Object convertedValue = convert(currentPath, context, value, setAccessor.getPropertyClass(),
        targetType.getType());
    return setAccessor.invoke(parentPojo, convertedValue);
  }

}
