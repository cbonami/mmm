/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.base.AbstractContentClass;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.model.api.ContentClass} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class ContentClassImpl extends AbstractContentClass {

  /** UID for serialization. */
  private static final long serialVersionUID = -6926223109885122995L;

  /** @see #getContentClass() */
  private static ContentClass classClass;

  /**
   * The constructor.
   */
  public ContentClassImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getContentClass() {

    return classClass;
  }

  /**
   * This method sets the {@link #getContentClass() content-class} reflecting
   * the type content-class (itself).
   * 
   * @param contentClass is the content-class reflecting itself.
   */
  static void setContentClass(ContentClass contentClass) {

    assert ((classClass == null) || (classClass == contentClass));
    assert (contentClass != null);
    classClass = contentClass;
  }

}
