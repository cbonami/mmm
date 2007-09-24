/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import net.sf.mmm.content.api.ContentObject;

/**
 * This is the interface for an object reflecting the content model. It can be
 * either a {@link ContentClass} or a {@link ContentField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract interface ContentReflectionObject extends ContentObject {

  /** the name of the {@link #getContentClass() class} reflecting this type. */
  String CLASS_NAME = "ContentReflectionObject";

  /** the id of the {@link #getContentClass() class} reflecting this type. */
  short CLASS_ID = 1;

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getModifiers() modifiers} for generic access via
   * {@link #getValue(String)}.
   */
  String FIELD_NAME_MODIFIERS = "modifiers";

  /**
   * This method gets the modifiers of this object. The modifiers are a set of
   * flags.
   * 
   * @see ContentClass#getModifiers()
   * @see ContentField#getModifiers()
   * 
   * @return the objects modifiers.
   */
  Modifiers getModifiers();

  /**
   * This method determines if this is a {@link ContentClass content-class} or a
   * {@link ContentField content-field}. It is allowed to cast this object
   * according to the result of this method.
   * 
   * @return <code>true</code> if this is a {@link ContentClass content-class},
   *         <code>false</code> if this is a
   *         {@link ContentField content-field}.
   */
  boolean isClass();

}