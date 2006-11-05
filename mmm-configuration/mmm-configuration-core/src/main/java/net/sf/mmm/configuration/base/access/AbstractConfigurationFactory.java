/* $Id$ */
package net.sf.mmm.configuration.base.access;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.configuration.base.ConfigurationUtil;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.configuration.base.access.ConfigurationFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractConfigurationFactory implements ConfigurationFactory {

  /**
   * The constructor.
   */
  public AbstractConfigurationFactory() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.base.access.ConfigurationFactory#create(net.sf.mmm.configuration.api.access.ConfigurationAccess)
   */
  public AbstractConfigurationDocument create(ConfigurationAccess access)
      throws ConfigurationException {

    return create(access, ConfigurationUtil.createDefaultContext());
  }

}
