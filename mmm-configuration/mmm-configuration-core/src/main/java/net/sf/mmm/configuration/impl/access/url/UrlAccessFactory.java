/* $Id$ */
package net.sf.mmm.configuration.impl.access.url;

import java.net.MalformedURLException;
import java.net.URL;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.Configuration;
import net.sf.mmm.configuration.api.access.ConfigurationAccess;
import net.sf.mmm.configuration.base.ConfigurationReadException;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccess;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccessFactory;
import net.sf.mmm.context.api.Context;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessFactory}
 * interface for {@link java.net.URL URL} access.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class UrlAccessFactory extends AbstractConfigurationAccessFactory {

  /**
   * this is the default
   * {@link net.sf.mmm.configuration.api.ConfigurationDocument#NAME_INCLUDE_ACCESS access}
   * name for this implementation.
   * 
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactory#CONTEXT_VARIABLE_PREFIX
   */
  public static final String CONTEXT_DEFAULT_NAME = "url";

  /**
   * The constructor.
   */
  public UrlAccessFactory() {

    super();
  }

  /**
   * @see net.sf.mmm.configuration.base.access.AbstractConfigurationAccessFactory#configure(java.lang.String,
   *      net.sf.mmm.context.api.Context,
   *      net.sf.mmm.configuration.api.Configuration,
   *      net.sf.mmm.configuration.api.access.ConfigurationAccess,
   *      java.lang.String) 
   */
  @Override
  public AbstractConfigurationAccess[] configure(String prefix, Context context,
      Configuration include, ConfigurationAccess parent, String href)
      throws ConfigurationException {

    try {
      URL url;
      if (parent != null) {
        UrlAccess parentAccess = (UrlAccess) parent;
        url = new URL(parentAccess.getUrl(), href);
      } else {
        url = new URL(href);
      }
      UrlAccess access = new UrlAccess(url);
      return new UrlAccess[] {access};
    } catch (MalformedURLException e) {
      throw new ConfigurationReadException(href, e);
    }
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactory#isSingleAccess()
   */
  public boolean isSingleAccess() {

    return true;
  }

}
