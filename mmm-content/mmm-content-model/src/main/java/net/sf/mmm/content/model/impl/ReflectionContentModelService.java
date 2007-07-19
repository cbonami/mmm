/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.impl;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.model.api.ClassModifiers;
import net.sf.mmm.content.model.base.ClassModifiersImpl;
import net.sf.mmm.util.pojo.api.PojoDescriptor;
import net.sf.mmm.util.pojo.api.PojoDescriptorBuilder;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessMode;
import net.sf.mmm.util.pojo.api.PojoPropertyAccessor;
import net.sf.mmm.util.pojo.api.PojoPropertyDescriptor;
import net.sf.mmm.util.pojo.impl.FieldPojoDescriptorBuilder;
import net.sf.mmm.util.pojo.impl.PublicMethodPojoDescriptorBuilder;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ReflectionContentModelService extends BasicContentModelService {

  /** The "class-loader" used to read the custom content-model. */
  private ContentModelClassReader classReader;

  /**
   * The constructor.
   */
  public ReflectionContentModelService() {

    super();
  }

  /**
   * @param classReader the classReader to set
   */
  @Resource
  public void setClassReader(ContentModelClassReader classReader) {

    this.classReader = classReader;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void initialize() {

    super.initialize();
    if (this.classReader == null) {
      ContentModelClassReader reader = new ContentModelClassReader();
      // reader.initialize();
      this.classReader = reader;
    }
  }

  public void initialize(Class<? extends ContentObject>[] entityTypes) {

    // TODO:
    for (Class<? extends ContentObject> type: entityTypes) {
      this.classReader.readClass(type);      
    }
    // setRootClass(rootClass);
    // addClassRecursive(rootClass);
  }
}
