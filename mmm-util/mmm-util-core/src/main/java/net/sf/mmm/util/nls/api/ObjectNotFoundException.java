/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsBundleUtilCore;

/**
 * An {@link ObjectNotFoundException} is thrown if an object was requested but
 * does NOT exist or could NOT be found.<br>
 * This typically happens in situations where required objects are requested by
 * a key (e.g. in a registry- {@link java.util.Map}) but an expected object was
 * NOT registered or the key is wrong for some reason.<br>
 * If you design your API please always consider if you should return
 * <code>null</code> or throw an {@link ObjectNotFoundException}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ObjectNotFoundException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1008016155549441562L;

  /**
   * The constructor.
   * 
   * @param object is a description (e.g. the classname) of the object that was
   *        required but could NOT be found.
   */
  public ObjectNotFoundException(Object object) {

    super(NlsBundleUtilCore.ERR_OBJECT_NOT_FOUND, object);
  }

  /**
   * The constructor.
   * 
   * @param object is a description (e.g. the classname) of the object that was
   *        required but could NOT be found.
   * @param key is the key to the required object.
   */
  public ObjectNotFoundException(Object object, Object key) {

    super(NlsBundleUtilCore.ERR_OBJECT_NOT_FOUND_WITH_KEY, object, key);
  }

}
