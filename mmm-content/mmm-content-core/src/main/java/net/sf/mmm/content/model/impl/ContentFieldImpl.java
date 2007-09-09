/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.lang.reflect.Type;

import net.sf.mmm.content.base.ClassAnnotation;
import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.model.api.ContentField;
import net.sf.mmm.content.model.base.AbstractContentField;
import net.sf.mmm.content.value.base.SmartId;

/**
 * This is the implementation of the
 * {@link net.sf.mmm.content.model.api.ContentField} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@ClassAnnotation(id = ContentField.CLASS_ID, name = ContentField.CLASS_NAME)
public final class ContentFieldImpl extends AbstractContentField {

  /** UID for serialization. */
  private static final long serialVersionUID = -2021919603941862430L;

  /** @see #getContentClass() */
  private static ContentClass fieldClass;

  /**
   * The constructor.
   */
  public ContentFieldImpl() {

    super();
  }

  /**
   * The constructor.
   *
   * @param id is the {@link #getId() id}.
   */
  public ContentFieldImpl(SmartId id) {

    super(id);
  }

  /**
   * {@inheritDoc}
   */
  public ContentClass getContentClass() {

    return fieldClass;
  }

  /**
   * This method sets the {@link #getContentClass() content-class} reflecting
   * the type content-field.
   * 
   * @param contentClass is the content-class reflecting this type.
   */
  static void setContentClass(ContentClass contentClass) {

    assert ((fieldClass == null) || (fieldClass == contentClass));
    assert (contentClass != null);
    fieldClass = contentClass;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setDeclaringClass(ContentClass declaringClass) {

    // make package visible...
    super.setDeclaringClass(declaringClass);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setFieldTypeAndClass(Type type) {

    // make package visible...
    super.setFieldTypeAndClass(type);
  }

}
