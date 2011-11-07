/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.reflection;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.sf.mmm.data.api.datatype.DataId;

/**
 * This annotation is used to mark a
 * {@link java.lang.reflect.Modifier#isPublic(int) public} getter
 * {@link java.lang.reflect.Method method} of
 * {@link net.sf.mmm.data.api.DataObject} and its sub-types as a
 * {@link net.sf.mmm.data.api.reflection.DataField}.
 * 
 * @see DataClassAnnotation
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataFieldAnnotation {

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getId() id} of the field. If
   * omitted the ID will be generated automatically. If an update is performed
   * and the {@link #title() title} has NOT changed then the existing ID will be
   * reassigned. However, for statically typed data classes it is suggested to
   * supply fixed IDs to prevent problems on renaming.
   */
  long id() default DataId.OBJECT_ID_ILLEGAL;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataField#getTitle() name} of the
   * field. If omitted the bean-property-name of the annotated getter is used
   * (e.g. <code>fooBar</code> if the annotated method is named
   * <code>getFooBar()</code>).
   */
  String title() default "";

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isTransient()
   * transient-flag} of the field.
   */
  boolean isTransient() default false;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isStatic()
   * static-flag} of the field. Will be ignored if the annotated getter is
   * {@link java.lang.reflect.Modifier#isStatic(int) static}.
   */
  boolean isStatic() default false;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isReadOnly()
   * readonly-flag} of the field. Will be ignored if the annotated getter has no
   * according public setter.
   */
  boolean isReadOnly() default false;

  /**
   * The {@link net.sf.mmm.data.api.reflection.DataFieldModifiers#isFinal()
   * final-flag} of the field. Will be ignored if the annotated getter is
   * {@link java.lang.reflect.Modifier#isFinal(int) final}.
   */
  boolean isFinal() default false;

  /**
   * The inherited-flag of the field.
   * 
   * @see net.sf.mmm.data.api.reflection.DataFieldModifiers#isInheritedFromParent()
   */
  boolean isInheritedFromParent() default false;

  /**
   * The {@link #id()} of a field of some {@link net.sf.mmm.data.api.DataObject}
   * <code>X</code> that represents a relation on the annotated type. The
   * annotated field is expected to be a collection of all <code>X</code> that
   * link to this object in the {@link net.sf.mmm.data.api.reflection.DataField}
   * specified by the given
   * {@link net.sf.mmm.data.api.reflection.DataField#getId() ID}.<br/>
   * The default is <code>-1</code> indicating that this is not an inverse
   * relation.
   */
  int inverseRelationFieldId() default -1;

}
