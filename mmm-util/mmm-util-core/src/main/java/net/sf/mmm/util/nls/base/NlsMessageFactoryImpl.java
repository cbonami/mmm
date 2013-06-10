/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.nls.api.NlsAccess;
import net.sf.mmm.util.nls.api.NlsMessage;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.nls.api.NlsTemplate;
import net.sf.mmm.util.nls.impl.NlsMessageImpl;
import net.sf.mmm.util.nls.impl.formatter.NlsFormatterManagerImpl;

/**
 * This is the implementation of {@link NlsMessageFactory}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named(NlsMessageFactory.CDI_NAME)
@Singleton
public class NlsMessageFactoryImpl extends AbstractNlsMessageFactory {

  /** @see #getNlsDependencies() */
  private NlsDependencies nlsDependencies;

  /**
   * The constructor.
   */
  public NlsMessageFactoryImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessage create(NlsTemplate template, Map<String, Object> messageArguments) {

    return new NlsMessageImpl(template, messageArguments, this.nlsDependencies);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessage create(String internationalizedMessage, Map<String, Object> messageArguments) {

    return new NlsMessageImpl(internationalizedMessage, messageArguments, this.nlsDependencies);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NlsMessage createDirect(String bundleName, String key, Map<String, Object> messageArguments) {

    NlsTemplate template = null;
    // template = new NlsTemplateImplWithMessage(bundleName, key, key);
    return create(template, messageArguments);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.nlsDependencies == null) {
      NlsFormatterManagerImpl formatterManager = new NlsFormatterManagerImpl();
      formatterManager.initialize();
      this.nlsDependencies = formatterManager.getNlsDependencies();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialized() {

    super.doInitialized();
    NlsAccess.setFactory(this);
  }

  /**
   * @return the {@link NlsDependencies}.
   */
  public NlsDependencies getNlsDependencies() {

    return this.nlsDependencies;
  }

  /**
   * @param nlsDependencies are the {@link NlsDependencies} to set.
   */
  @Inject
  public void setNlsDependencies(NlsDependencies nlsDependencies) {

    getInitializationState().requireNotInitilized();
    this.nlsDependencies = nlsDependencies;
  }

}
