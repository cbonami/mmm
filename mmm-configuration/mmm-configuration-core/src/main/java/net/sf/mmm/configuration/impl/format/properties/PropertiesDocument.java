/* $Id$ */
package net.sf.mmm.configuration.impl.format.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.AbstractConfiguration;
import net.sf.mmm.configuration.base.AbstractConfigurationDocument;
import net.sf.mmm.context.api.MutableContextIF;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.ConfigurationDocumentIF} for
 * {@link java.util.Properties}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PropertiesDocument extends AbstractConfigurationDocument {

  /** @see #getProperties() */
  private Properties properties;

  /**
   * @see #getSeparator()
   */
  private final String separator;

  /** @see #isFlat() */
  private final boolean flat;

  /**
   * The constructor for a toplevel document.
   * 
   * @param configurationAccess
   *        is the access to the raw configuration data.
   * @param context
   *        is the context.
   * @param keySeparator
   *        is the {@link #getSeparator() separator}.
   * @param isFlat
   *        is the {@link #isFlat() flat} flag.
   */
  public PropertiesDocument(ConfigurationAccessIF configurationAccess, MutableContextIF context,
      String keySeparator, boolean isFlat) {

    super(configurationAccess, context);
    this.properties = null;
    this.separator = keySeparator;
    this.flat = isFlat;
  }

  /**
   * The constructor for a child document.
   * 
   * @param configurationAccess
   *        is the access to the raw configuration data.
   * @param parentConfiguration
   *        is the parent configuration.
   * @param keySeparator
   *        is the {@link #getSeparator() separator}.
   * @param isFlat
   *        is the {@link #isFlat() flat} flag.
   */
  public PropertiesDocument(ConfigurationAccessIF configurationAccess,
      AbstractConfiguration parentConfiguration, String keySeparator, boolean isFlat) {

    super(configurationAccess, parentConfiguration);
    this.properties = null;
    this.separator = keySeparator;
    this.flat = isFlat;
  }

  /**
   * This method gets the properties.
   * 
   * @return the properties.
   */
  Properties getProperties() {

    return this.properties;
  }

  /**
   * This method determines if the properties are treated flat. In this case all
   * properties are direct children of the root-node.<br>
   * Else the properties are treated as a tree. Then the
   * {@link #getSeparator() sperator} splits a property key into according
   * configuration nodes. E.g. if the {@link #getSeparator() sperator} is '.'
   * and a property has the key 'foo.bar.key' then it represents a
   * {@link net.sf.mmm.configuration.api.ConfigurationIF configuration-node}
   * with the
   * {@link net.sf.mmm.configuration.api.ConfigurationIF#getName() name} 'key'
   * and can be retrieved via
   * 
   * <pre>
   * propertiesRootNode.{@link net.sf.mmm.configuration.api.ConfigurationIF#getDescendant(String) getDescendant}("foo/bar/key").
   * </pre>
   * 
   * In a flat layout, the property would represent a direct child of the
   * <code>propertiesRootNode</code> accessable via
   * 
   * <pre>
   * propertiesRootNode.{@link net.sf.mmm.configuration.api.ConfigurationIF#getDescendant(String) getDescendant}("foo.bar.key").
   * </pre>
   * 
   * Be aware that
   * 
   * <pre>
   * propertiesRootNode.{@link net.sf.mmm.configuration.api.ConfigurationIF#getDescendant(String) getDescendant}("foo").{@link net.sf.mmm.configuration.api.ConfigurationIF#getDescendants(String) getDescendants}("*").
   * </pre>
   * 
   * would be empty in flat mode but in tree mode it has 'bar' as child.
   * 
   * @return <code>true</code> if flat, <code>false</code> if tree.
   */
  boolean isFlat() {

    return this.flat;
  }

  /**
   * This method gets the key separator (typically '.' or '/'). If the
   * properties are treated as tree (NOT {@link #isFlat() flat}) is used analog
   * to {@link net.sf.mmm.configuration.api.ConfigurationIF#PATH_SEPARATOR} but
   * to separate the internal keys of the properties.
   * 
   * 
   * @return the properties key separator.
   */
  String getSeparator() {

    return this.separator;
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfigurationDocument#load(java.io.InputStream)
   *      
   */
  @Override
  protected AbstractConfiguration load(InputStream inputStream) throws ConfigurationException {

    try {
      this.properties = new Properties();
      this.properties.load(inputStream);

      String name = this.properties.getProperty("cfg:name");
      if (name == null) {
        name = "properties";
      }
      String namespace = this.properties.getProperty("cfg:namespace");
      PropertiesElement root = new PropertiesElement(getParentConfiguration(), this, "", name,
          namespace);
      // TODO: namespace support...
      for (Object key : this.properties.keySet()) {
        String keyString = key.toString();
        if (this.flat) {
          PropertiesElement child = new PropertiesElement(root, this, keyString, keyString,
              namespace);
          root.addChild(child);
        } else {
          root.getDescendant(keyString);
        }
      }
      return root;
    } catch (IOException e) {
      throw new ConfigurationException("I/O Error reading properties for " + this, e);
    }
  }

  /**
   * @see net.sf.mmm.configuration.base.AbstractConfigurationDocument#save(java.io.OutputStream)
   *      
   */
  @Override
  protected void save(OutputStream outputStream) throws ConfigurationException {

    try {
      // TODO
      this.properties.store(outputStream, "");
    } catch (IOException e) {
      throw new ConfigurationException("I/O Error writing properties for " + this, e);
    }

  }

}
