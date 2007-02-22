/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.configuration.api;

/**
 * This is the exception thrown if a descendant path is illegal.
 * 
 * @see net.sf.mmm.configuration.api.Configuration#getDescendant(String,
 *      String)
 * @see net.sf.mmm.configuration.api.Configuration#getDescendants(String,
 *      String)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class IllegalDescendantPathException extends ConfigurationException {

  /** uid for serialization */
  private static final long serialVersionUID = -4616110040377899931L;

  /**
   * The constructor.
   * 
   * @param path
   *        is the descendant path that is illegal.
   */
  public IllegalDescendantPathException(String path) {

    this(path, null);
  }

  /**
   * The constructor.
   * 
   * @param path
   *        is the descendant path that is illegal.
   * @param nested
   *        is the throwable that caused this exception.
   */
  public IllegalDescendantPathException(String path, Throwable nested) {

    super(nested, "Illegal descendant path \"{0}\"!", path);
  }

}
