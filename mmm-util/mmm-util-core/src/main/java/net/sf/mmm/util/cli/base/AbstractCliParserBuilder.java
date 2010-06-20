/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import javax.annotation.Resource;

import net.sf.mmm.util.cli.api.CliParser;
import net.sf.mmm.util.cli.api.CliParserBuilder;
import net.sf.mmm.util.cli.api.CliParserExcepiton;
import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsNullPointerException;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.descriptor.api.PojoDescriptorBuilderFactory;
import net.sf.mmm.util.pojo.descriptor.impl.PojoDescriptorBuilderFactoryImpl;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.reflect.base.ReflectionUtilImpl;
import net.sf.mmm.util.value.api.GenericValueConverter;
import net.sf.mmm.util.value.impl.DefaultComposedValueConverter;

/**
 * This is the abstract base implementation of the {@link CliParserBuilder}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public abstract class AbstractCliParserBuilder extends AbstractLoggable implements
    CliParserBuilder, CliParserConfiguration {

  /** @see #getDescriptorBuilderFactory() */
  private PojoDescriptorBuilderFactory descriptorBuilderFactory;

  /** @see #getDescriptorBuilder() */
  private PojoDescriptorBuilder descriptorBuilder;

  /** @see #getNlsMessageFactory() */
  private NlsMessageFactory nlsMessageFactory;

  /** @see #getStringUtil() */
  private StringUtil stringUtil;

  /** @see #getReflectionUtil() */
  private ReflectionUtil reflectionUtil;

  /** @see #getCollectionReflectionUtil() */
  private CollectionReflectionUtil collectionReflectionUtil;

  /** @see #getCollectionFactoryManager() */
  private CollectionFactoryManager collectionFactoryManager;

  /** @see #getConverter() */
  private GenericValueConverter<Object> converter;

  /**
   * The constructor.
   */
  public AbstractCliParserBuilder() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.descriptorBuilderFactory == null) {
      PojoDescriptorBuilderFactoryImpl factory = new PojoDescriptorBuilderFactoryImpl();
      factory.initialize();
      this.descriptorBuilderFactory = factory;
    }
    if (this.nlsMessageFactory == null) {
      this.nlsMessageFactory = NlsAccess.getFactory();
    }
    if (this.converter == null) {
      DefaultComposedValueConverter valueConverter = new DefaultComposedValueConverter();
      valueConverter.initialize();
      this.converter = valueConverter;
    }
    if (this.stringUtil == null) {
      this.stringUtil = StringUtilImpl.getInstance();
    }
    if (this.reflectionUtil == null) {
      this.reflectionUtil = ReflectionUtilImpl.getInstance();
    }
  }

  /**
   * {@inheritDoc}
   */
  public CliParser build(Object pojo) {

    if (pojo == null) {
      throw new NlsNullPointerException("pojo");
    }
    try {
      CliState state = new CliState(pojo.getClass(), this.descriptorBuilderFactory, getLogger());
      CliParser parser = buildInternal(pojo, state);
      return parser;
    } catch (Exception e) {
      throw new CliParserExcepiton(e, pojo.getClass());
    }
  }

  /**
   * This method implements {@link #build(Object)} internally.
   * 
   * @param state is the {@link AbstractCliParser#getState() state object}.
   * @param cliState is the according {@link CliState}.
   * 
   * @return the new {@link CliParser}.
   */
  protected abstract CliParser buildInternal(Object state, CliState cliState);

  /**
   * @return the descriptorBuilderFactory
   */
  protected PojoDescriptorBuilderFactory getDescriptorBuilderFactory() {

    return this.descriptorBuilderFactory;
  }

  /**
   * @param descriptorBuilderFactory is the descriptorBuilderFactory to set
   */
  @Resource
  public void setDescriptorBuilderFactory(PojoDescriptorBuilderFactory descriptorBuilderFactory) {

    getInitializationState().requireNotInitilized();
    this.descriptorBuilderFactory = descriptorBuilderFactory;
  }

  /**
   * @return the descriptorBuilder
   */
  protected PojoDescriptorBuilder getDescriptorBuilder() {

    return this.descriptorBuilder;
  }

  /**
   * @param descriptorBuilder is the descriptorBuilder to set
   */
  public void setDescriptorBuilder(PojoDescriptorBuilder descriptorBuilder) {

    getInitializationState().requireNotInitilized();
    this.descriptorBuilder = descriptorBuilder;
  }

  /**
   * {@inheritDoc}
   */
  public CollectionFactoryManager getCollectionFactoryManager() {

    return this.collectionFactoryManager;
  }

  /**
   * @param collectionFactoryManager is the collectionFactoryManager to set
   */
  @Resource
  public void setCollectionFactoryManager(CollectionFactoryManager collectionFactoryManager) {

    getInitializationState().requireNotInitilized();
    this.collectionFactoryManager = collectionFactoryManager;
  }

  /**
   * {@inheritDoc}
   */
  public StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * @param stringUtil is the stringUtil to set
   */
  @Resource
  public void setStringUtil(StringUtil stringUtil) {

    getInitializationState().requireNotInitilized();
    this.stringUtil = stringUtil;
  }

  /**
   * {@inheritDoc}
   */
  public CollectionReflectionUtil getCollectionReflectionUtil() {

    return this.collectionReflectionUtil;
  }

  /**
   * @param collectionReflectionUtil is the collectionReflectionUtil to set
   */
  @Resource
  public void setCollectionReflectionUtil(CollectionReflectionUtil collectionReflectionUtil) {

    getInitializationState().requireNotInitilized();
    this.collectionReflectionUtil = collectionReflectionUtil;
  }

  /**
   * @return the reflectionUtil
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
  public NlsMessageFactory getNlsMessageFactory() {

    return this.nlsMessageFactory;
  }

  /**
   * @param nlsMessageFactory is the nlsMessageFactory to set
   */
  public void setNlsMessageFactory(NlsMessageFactory nlsMessageFactory) {

    getInitializationState().requireNotInitilized();
    this.nlsMessageFactory = nlsMessageFactory;
  }

  /**
   * {@inheritDoc}
   */
  public GenericValueConverter<Object> getConverter() {

    return this.converter;
  }

  /**
   * @param converter is the converter to set
   */
  @Resource
  public void setConverter(GenericValueConverter<Object> converter) {

    getInitializationState().requireNotInitilized();
    this.converter = converter;
  }

}
