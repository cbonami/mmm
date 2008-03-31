/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.pojo.path.base;

import net.sf.mmm.util.reflect.pojo.NlsBundlePojo;
import net.sf.mmm.util.reflect.pojo.path.api.PojoPathException;

/**
 * A {@link PojoPathCachingDisabledException} is thrown if caching is required
 * to access a specific {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath}
 * but caching was disabled at this point.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class PojoPathCachingDisabledException extends PojoPathException {

  /** UID for serialization. */
  private static final long serialVersionUID = -3185296481410849119L;

  /**
   * The constructor.
   * 
   * @param pojoPath is the
   *        {@link net.sf.mmm.util.reflect.pojo.path.api.PojoPath} for which
   *        caching is disabled but was required.
   */
  public PojoPathCachingDisabledException(String pojoPath) {

    super(NlsBundlePojo.ERR_PATH_CACHING_DISABLED, pojoPath);
  }

}