/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.cli.base;

import net.sf.mmm.util.collection.api.CollectionFactoryManager;
import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.nls.api.NlsMessageFactory;
import net.sf.mmm.util.reflect.api.CollectionReflectionUtil;
import net.sf.mmm.util.reflect.api.ReflectionUtil;
import net.sf.mmm.util.value.api.GenericValueConverter;

/**
 * This interface bundles the configuration for the
 * {@link net.sf.mmm.util.cli.api.CliParser}. It contains components required by
 * the parser.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 2.0.0
 */
public interface CliParserConfiguration {

  /**
   * @return the {@link StringUtil} instance to use.
   */
  StringUtil getStringUtil();

  /**
   * @return the {@link ReflectionUtil} instance to use.
   */
  ReflectionUtil getReflectionUtil();

  /**
   * @return the {@link CollectionReflectionUtil} instance to use.
   */
  CollectionReflectionUtil getCollectionReflectionUtil();

  /**
   * @return the {@link CollectionFactoryManager} instance to use.
   */
  CollectionFactoryManager getCollectionFactoryManager();

  /**
   * @return the {@link NlsMessageFactory} instance to use.
   */
  NlsMessageFactory getNlsMessageFactory();

  /**
   * @return the {@link GenericValueConverter} instance used to convert CLI
   *         parameters to typed values.
   */
  GenericValueConverter<Object> getConverter();

  // /**
  // * @return the {@link PojoDescriptorBuilderFactory} instance to use.
  // */
  // PojoDescriptorBuilderFactory getDescriptorBuilderFactory();

}
