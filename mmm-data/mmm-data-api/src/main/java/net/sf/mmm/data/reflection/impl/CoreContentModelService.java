/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.reflection.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import net.sf.mmm.data.api.DataException;
import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataClassLoader;
import net.sf.mmm.data.api.reflection.DataReflectionEvent;
import net.sf.mmm.data.api.reflection.DataReflectionException;
import net.sf.mmm.data.base.reflection.AbstractContentClass;
import net.sf.mmm.data.base.reflection.AbstractContentField;
import net.sf.mmm.data.base.reflection.AbstractMutableContentModelService;
import net.sf.mmm.data.base.reflection.ContentClassLoaderStAX;
import net.sf.mmm.data.reflection.impl.statically.ContentClassImpl;
import net.sf.mmm.data.reflection.impl.statically.ContentFieldImpl;

/**
 * This is an abstract base implementation of the
 * {@link net.sf.mmm.data.api.reflection.ContentReflectionService} interface that
 * assumes that {@link DataId}s are used as well as specific implementations
 * for class and field.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class CoreContentModelService extends AbstractMutableContentModelService {

  /** @see #getClassLoader() */
  private DataClassLoader classLoader;

  /** @see #isEditable() */
  private boolean editable;

  /**
   * The constructor.
   */
  public CoreContentModelService() {

    super();
    this.editable = false;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isEditable() {

    return this.editable;
  }

  /**
   * This method sets the {@link #isEditable() editable} flag.
   * 
   * @param editable the editable to set
   */
  public void setEditable(boolean editable) {

    this.editable = editable;
  }

  /**
   * @return the classLoader
   */
  public DataClassLoader getClassLoader() {

    return this.classLoader;
  }

  /**
   * @param classLoader the classLoader to set
   */
  public void setClassLoader(DataClassLoader classLoader) {

    this.classLoader = classLoader;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   * 
   * @throws Exception if an I/O error was caused by the class-loader.
   */
  @Override
  @PostConstruct
  public void initialize() throws Exception {

    super.initialize();
    if (getIdManager() == null) {
      setIdManager(new StaticSmartIdManager());
    }
    if (this.classLoader == null) {
      this.classLoader = new ContentClassLoaderStAX(this);
    }
    loadClasses();
  }

  /**
   * This method loads the content-model.
   * 
   * @throws IOException if an I/O error was caused by the class-loader.
   * @throws DataException if the content-model is invalid.
   */
  protected void loadClasses() throws IOException, DataException {

    AbstractContentClass rootClass = this.classLoader.loadClasses();
    setRootClass(rootClass);
    addClassRecursive(rootClass);
    AbstractContentClass classClass = getContentClass(getIdManager().getClassClassId());
    if (classClass == null) {
      // TODO:
      throw new DataReflectionException("Missing class for ContentClass!");
    }
    // ContentClassImpl.setContentClass(classClass);
    AbstractContentClass fieldClass = getContentClass(getIdManager().getFieldClassId());
    if (fieldClass == null) {
      // TODO:
      throw new DataReflectionException("Missing class for ContentField!");
    }
    // ContentFieldImpl.setContentClass(fieldClass);
  }

  /**
   * This method reloads the content-model. If the external representation of
   * the model has been modified, this method updates the model to import these
   * changes.
   * 
   * @throws IOException if an I/O error was caused by the class-loader.
   * @throws DataException if the content-model is invalid.
   */
  public void reaload() throws IOException, DataException {

    loadClasses();
    fireEvent(new DataReflectionEvent(getRootContentClass(), ChangeEventType.UPDATE));
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass createNewClass(DataId id, String name) {

    AbstractContentClass contentClass = new ContentClassImpl(name, id);
    setContentObjectId(contentClass, id);
    return contentClass;
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentField createNewField(DataId id, String name) {

    AbstractContentField contentField = new ContentFieldImpl(name, id);
    setContentObjectId(contentField, id);
    return contentField;
  }

}
