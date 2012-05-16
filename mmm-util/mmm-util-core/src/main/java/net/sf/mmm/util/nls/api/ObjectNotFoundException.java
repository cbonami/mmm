/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.nls.api;

import net.sf.mmm.util.NlsMessagesBundleUtilCore;

/**
 * An {@link ObjectNotFoundException} is thrown if an object was requested but does NOT exist or could NOT be
 * found.<br>
 * This typically happens in situations where required objects are requested by a key (e.g. in a registry-
 * {@link java.util.Map}) but an expected object was NOT registered or the key is wrong for some reason.<br>
 * If you design your API please always consider if you should return <code>null</code> or throw an
 * {@link ObjectNotFoundException}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class ObjectNotFoundException extends NlsRuntimeException {

  /** UID for serialization. */
  private static final long serialVersionUID = 1008016155549441562L;

  /**
   * The constructor.
   * 
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be
   *        found.
   */
  public ObjectNotFoundException(Object object) {

    this((Throwable) null, object);
  }

  /**
   * The constructor.
   * 
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be
   *        found.
   * @param key is the key to the required object.
   */
  public ObjectNotFoundException(Object object, Object key) {

    this((Throwable) null, object, key);
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be
   *        found.
   * @since 2.0.2
   */
  public ObjectNotFoundException(Throwable nested, Object object) {

    super(nested, createBundle(NlsMessagesBundleUtilCore.class).errorObjectNotFound(object));
  }

  /**
   * The constructor.
   * 
   * @param nested is the {@link #getCause() cause} of this exception.
   * @param object is a description (e.g. the classname) of the object that was required but could NOT be
   *        found.
   * @param key is the key to the required object.
   * @since 2.0.2
   */
  public ObjectNotFoundException(Throwable nested, Object object, Object key) {

    super(nested, createBundle(NlsMessagesBundleUtilCore.class).errorObjectNotFoundWithKey(object, key));
  }

}
