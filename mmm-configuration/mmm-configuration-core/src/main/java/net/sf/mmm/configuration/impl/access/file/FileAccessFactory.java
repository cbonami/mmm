/* $Id$ */
package net.sf.mmm.configuration.impl.access.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.configuration.api.ConfigurationDocumentIF;
import net.sf.mmm.configuration.api.ConfigurationException;
import net.sf.mmm.configuration.api.ConfigurationIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF;
import net.sf.mmm.configuration.api.access.ConfigurationAccessIF;
import net.sf.mmm.configuration.base.ConfigurationReadException;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccess;
import net.sf.mmm.configuration.base.access.AbstractConfigurationAccessFactory;
import net.sf.mmm.context.api.ContextIF;
import net.sf.mmm.util.io.FileType;
import net.sf.mmm.util.io.FileUtil;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF}
 * interface for simple {@link java.io.File file} access.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class FileAccessFactory extends AbstractConfigurationAccessFactory {

  /**
   * this is the default
   * {@link ConfigurationDocumentIF#NAME_INCLUDE_ACCESS access} name for this
   * implementation.
   * 
   * @see ConfigurationAccessFactoryIF#CONTEXT_VARIABLE_PREFIX
   */
  public static final String CONTEXT_DEFAULT_NAME = "file";

  /**
   * <code>true</code> if exact file is specified, <code>false</code> if
   * wildcard
   */
  private boolean singleAccess;

  /**
   * The constructor.
   */
  public FileAccessFactory() {

    super();
    this.singleAccess = false;
  }

  /**
   * @see net.sf.mmm.configuration.base.access.AbstractConfigurationAccessFactory#configure(java.lang.String,
   *      net.sf.mmm.context.api.ContextIF,
   *      net.sf.mmm.configuration.api.ConfigurationIF,
   *      net.sf.mmm.configuration.api.access.ConfigurationAccessIF,
   *      java.lang.String) 
   */
  @Override
  public AbstractConfigurationAccess[] configure(String prefix, ContextIF context,
      ConfigurationIF include, ConfigurationAccessIF parent, String href)
      throws ConfigurationException {

    // determine current working directory
    File cwd = null;
    if (parent != null) {
      FileAccess parentAccess = (FileAccess) parent;
      cwd = parentAccess.getFile().getParentFile();
    }
    if (cwd == null) {
      cwd = new File("");
    }
    List<File> fileList = new ArrayList<File>();
    boolean hasPattern = FileUtil.collectMatchingFiles(cwd, href, FileType.FILE, fileList);
    this.singleAccess = !hasPattern;
    int count = fileList.size();
    if (this.singleAccess && (count != 1)) {
      throw new ConfigurationReadException(href);
    }
    FileAccess[] accessors = new FileAccess[count];
    for (int i = 0; i < count; i++) {
      accessors[i] = new FileAccess(fileList.get(i));
    }
    return accessors;
  }

  /**
   * @see net.sf.mmm.configuration.api.access.ConfigurationAccessFactoryIF#isSingleAccess()
   *      
   */
  public boolean isSingleAccess() {

    return this.singleAccess;
  }

}
