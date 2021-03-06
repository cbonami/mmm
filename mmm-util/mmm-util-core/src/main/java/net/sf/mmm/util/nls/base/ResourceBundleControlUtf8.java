/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.ResourceBundle.Control;

import net.sf.mmm.util.io.api.EncodingUtil;

/**
 * Adds UTF-8 support for {@link ResourceBundle} <code>*.properties</code> files.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public class ResourceBundleControlUtf8 extends Control {

  /** The singleton instance. */
  static final ResourceBundleControlUtf8 INSTANCE = new ResourceBundleControlUtf8();

  /**
   * The constructor.
   */
  public ResourceBundleControlUtf8() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
      throws IllegalAccessException, InstantiationException, IOException {

    // The below is a copy of the default implementation.
    String bundleName = toBundleName(baseName, locale);
    String resourceName = toResourceName(bundleName, "properties");
    ResourceBundle bundle = null;
    InputStream stream = null;
    if (reload) {
      URL url = loader.getResource(resourceName);
      if (url != null) {
        URLConnection connection = url.openConnection();
        if (connection != null) {
          connection.setUseCaches(false);
          stream = connection.getInputStream();
        }
      }
    } else {
      stream = loader.getResourceAsStream(resourceName);
    }
    if (stream != null) {
      try {
        bundle = new PropertyResourceBundle(new InputStreamReader(stream, EncodingUtil.ENCODING_UTF_8));
      } finally {
        stream.close();
      }
    }
    return bundle;
  }
}
