/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.reflection.base;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import net.sf.mmm.content.api.ContentModelSystemModifyException;
import net.sf.mmm.content.api.ContentObject;
import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.content.reflection.api.ContentClass;
import net.sf.mmm.content.reflection.api.ContentClassModifiers;
import net.sf.mmm.content.reflection.api.ContentField;
import net.sf.mmm.content.reflection.api.ContentFieldModifiers;
import net.sf.mmm.content.reflection.api.ContentModifiers;
import net.sf.mmm.content.reflection.api.ContentReflectionEvent;
import net.sf.mmm.content.reflection.api.ContentReflectionException;
import net.sf.mmm.content.reflection.api.ContentReflectionNotEditableException;
import net.sf.mmm.content.reflection.api.ContentReflectionObject;
import net.sf.mmm.content.reflection.api.MutableContentReflectionService;
import net.sf.mmm.content.reflection.base.statically.AbstractContentReflectionObject;
import net.sf.mmm.content.trash.MutableMetaData;
import net.sf.mmm.util.event.api.ChangeType;
import net.sf.mmm.util.reflect.api.ClassResolver;
import net.sf.mmm.util.reflect.base.MappedClassResolver;

/**
 * This an the abstract base implementation of the
 * {@link net.sf.mmm.content.reflection.api.ContentReflectionService
 * ContentModelService} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractMutableContentModelService extends AbstractContentModelService
    implements MutableContentReflectionService, ClassResolver {

  /** @see #getClassResolver() */
  private ClassResolver classResolver;

  /** @see #getName2JavaClassMap() */
  private Map<String, Class> name2JavaClassMap;

  /**
   * The constructor.
   */
  public AbstractMutableContentModelService() {

    super();
  }

  /**
   * @return the classResolver
   */
  public ClassResolver getClassResolver() {

    return this.classResolver;
  }

  /**
   * @param classResolver the classResolver to set
   */
  @Resource
  public void setClassResolver(ClassResolver classResolver) {

    this.classResolver = classResolver;
  }

  /**
   * @return the name2JavaClassMap
   */
  public Map<String, Class> getName2JavaClassMap() {

    return this.name2JavaClassMap;
  }

  /**
   * @param name2JavaClassMap the name2JavaClassMap to set
   */
  public void setName2JavaClassMap(Map<String, Class> name2JavaClassMap) {

    this.name2JavaClassMap = name2JavaClassMap;
  }

  /**
   * This method initializes this class. It has to be called after construction
   * and injection is completed.
   * 
   * @throws Exception if an exception was caused during initialization.
   */
  @PostConstruct
  public void initialize() throws Exception {

    if (this.name2JavaClassMap == null) {
      Map<String, Class> name2javaClass = new HashMap<String, Class>();
      initializeJavaClassMap(name2javaClass);
      this.name2JavaClassMap = name2javaClass;
    }
    if (this.classResolver == null) {
      MappedClassResolver mappedClassResolver = new MappedClassResolver();
      initializeClassResolver(mappedClassResolver);
      this.classResolver = mappedClassResolver;
    }
  }

  /**
   * This method initializes the default {@link #getName2JavaClassMap()
   * name2JavaClassMap}.
   * 
   * @param name2javaClass is the map to initialize.
   */
  protected void initializeJavaClassMap(Map<String, Class> name2javaClass) {

    name2javaClass.put(ContentObject.CLASS_NAME, ContentObject.class);
    name2javaClass.put(ContentReflectionObject.CLASS_NAME, ContentReflectionObject.class);
    name2javaClass.put(ContentClass.CLASS_NAME, ContentClass.class);
    name2javaClass.put(ContentField.CLASS_NAME, ContentField.class);
  }

  /**
   * This method initializes the default {@link #getClassResolver()
   * class-resolver}.
   * 
   * @param mappedClassResolver is the resolver to initialize.
   */
  protected void initializeClassResolver(MappedClassResolver mappedClassResolver) {

    mappedClassResolver.addClassMapping(String.class);
    mappedClassResolver.addClassMapping(Boolean.class);
    mappedClassResolver.addClassMapping(Integer.class);
    mappedClassResolver.addClassMapping(Long.class);
    mappedClassResolver.addClassMapping(Double.class);
    mappedClassResolver.addClassMapping(Date.class);
    mappedClassResolver.addClassMapping(Float.class);
    mappedClassResolver.addClassMapping(Short.class);
    mappedClassResolver.addClassMapping(Byte.class);
    // collections
    mappedClassResolver.addClassMapping(List.class);
    mappedClassResolver.addClassMapping(Set.class);
    mappedClassResolver.addClassMapping(Collection.class);
    mappedClassResolver.addClassMapping(Map.class);
    // mmm values
    mappedClassResolver.addClassMapping(ContentModifiers.class);
    mappedClassResolver.addClassMapping(ContentFieldModifiers.class);
    mappedClassResolver.addClassMapping(ContentClassModifiers.class);
    mappedClassResolver.addClassMapping(ContentId.VALUE_NAME, ContentId.class);
    mappedClassResolver.addClassMapping(MutableMetaData.VALUE_NAME, MutableMetaData.class);
  }

  /**
   * {@inheritDoc}
   */
  public Class resolveClass(String name) {

    Class result;
    ContentClass contentClass = getContentClass(name);
    if (contentClass == null) {
      result = this.classResolver.resolveClass(name);
    } else {
      //
      result = contentClass.getJavaClass();
    }
    assert (result != null);
    return result;
  }

  /**
   * This method verifies that this model is {@link #isEditable() editable}.
   * 
   * @throws ContentReflectionNotEditableException if this model is NOT
   *         {@link #isEditable() editable}.
   */
  protected void requireEditableModel() throws ContentReflectionNotEditableException {

    if (!isEditable()) {
      throw new ContentReflectionNotEditableException();
    }
  }

  /**
   * {@inheritDoc}
   */
  public AbstractContentClass createContentClass(ContentClass superClass, String name,
      ContentClassModifiers modifiers) throws ContentReflectionException {

    requireEditableModel();
    assert (superClass == getContentClass(superClass.getContentId()));
    ContentId id = getIdManager().createUniqueClassId();
    AbstractContentClass parentClass = (AbstractContentClass) superClass;
    AbstractContentClass newClass = createNewClass(id, name, parentClass, modifiers,
        superClass.getJavaClass(), false);
    // TODO
    syncClassRecursive(newClass, parentClass);
    // fire update event on super-class?
    // fireEvent(new ContentModelEvent(newClass, ChangeEvent.Type.ADD));
    return newClass;
  }

  /**
   * {@inheritDoc}
   */
  public ContentField createContentField(ContentClass declaringClass, String name, Type type,
      ContentFieldModifiers modifiers) throws ContentReflectionException {

    requireEditableModel();
    assert (declaringClass == getContentClass(declaringClass.getContentId()));
    ContentId id = getIdManager().createUniqueFieldId();
    AbstractContentClass contentClass = (AbstractContentClass) declaringClass;
    AbstractContentField field = createNewField(id, name, contentClass, type, modifiers, false);
    syncField(contentClass, field);
    // TODO: persist here...
    // contentClass.addField(field);
    // fireEvent(new ContentModelEvent(field, ChangeEvent.Type.ADD));
    return field;
  }

  /**
   * {@inheritDoc}
   */
  public void setDeleted(ContentReflectionObject classOrField, boolean newDeletedFlag)
      throws ContentReflectionException {

    requireEditableModel();
    if (classOrField.getContentModifiers().isSystem()) {
      throw new ContentModelSystemModifyException(classOrField);
    }
    // TODO: persist
    AbstractContentReflectionObject reflectionObject = (AbstractContentReflectionObject) classOrField;
    reflectionObject.setDeletedFlag(newDeletedFlag);
    if (classOrField.isContentClass()) {
      fireEvent(new ContentReflectionEvent((ContentClass) classOrField, ChangeType.UPDATE));
    } else {
      fireEvent(new ContentReflectionEvent((ContentField) classOrField, ChangeType.UPDATE));
    }
  }

}
