/* $Id$ */
package net.sf.mmm.configuration.impl.format.properties;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.configuration.base.access.AbstractConfigurationFactory;
import net.sf.mmm.context.api.Context;
import net.sf.mmm.context.api.MutableContext;
import net.sf.mmm.value.api.ValueException;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.base.access.ConfigurationFactory} interface
 * using {@link java.util.Properties} as representation for the configuration.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PropertiesFactory extends AbstractConfigurationFactory {

  /**
   * this is the default
   * {@link ConfigurationDocumentIF#NAME_INCLUDE_FORMAT format} name for this
   * implementation.
   * 
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#CONTEXT_VARIABLE_PREFIX
   */
  public static final String CONTEXT_DEFAULT_NAME = "properties";

  /** the suffix for the {@link PropertiesDocument#getSeparator() separator} */
  public static final String CONTEXT_VARIABLE_SUFFIX_SEPARATOR = ".separator";

  /** the suffix for the {@link PropertiesDocument#isFlat() flat} flag */
  public static final String CONTEXT_VARIABLE_SUFFIX_FLAT = ".flat";

  /** the default {@link PropertiesDocument#getSeparator() separator} */
  public static final String DEFAULT_SEPARATOR = ".";

  /** the default {@link PropertiesDocument#getSeparator() separator} */
  public static final boolean DEFAULT_FLAT = false;

  /** the {@link PropertiesDocument#isFlat() flat} flag */
  private boolean flat;

  /** the {@link PropertiesDocument#getSeparator() separator} */
  private String propertyKeySeparator;

  /**
   * The constructor.
   */
  public PropertiesFactory() {

    super();
    this.propertyKeySeparator = DEFAULT_SEPARATOR;
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#configure(java.lang.String,
   *      net.sf.mmm.context.api.Context,
   *      net.sf.mmm.configuration.api.Configuration)
   */
  public void configure(String prefix, Context context, Configuration include)
      throws ConfigurationException, ValueException {

    this.propertyKeySeparator = context.getValue(prefix + CONTEXT_VARIABLE_SUFFIX_SEPARATOR)
        .getString(DEFAULT_SEPARATOR);
    this.flat = context.getValue(prefix + CONTEXT_VARIABLE_SUFFIX_FLAT).getBoolean(
        Boolean.valueOf(DEFAULT_FLAT)).booleanValue();
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#create(net.sf.mmm.configuration.api.access.ConfigurationAccess,
   *      net.sf.mmm.context.api.MutableContext) 
   */
  public AbstractConfigurationDocument create(ConfigurationAccess access, MutableContext context)
      throws ConfigurationException {

    return new PropertiesDocument(access, context, this.propertyKeySeparator, this.flat);
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#create(net.sf.mmm.configuration.api.access.ConfigurationAccess,
   *      net.sf.mmm.configuration.base.AbstractConfiguration) 
   */
  public AbstractConfigurationDocument create(ConfigurationAccess access,
      AbstractConfiguration parentConfiguration) throws ConfigurationException {

    return new PropertiesDocument(access, parentConfiguration, this.propertyKeySeparator, this.flat);
  }

}
