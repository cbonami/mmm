/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.model.api;

import java.util.List;

import net.sf.mmm.util.collection.SizedIterable;

/**
 * This is the interface of a content-class. It reflects the structure of the
 * {@link net.sf.mmm.content.api.ContentObject content-object} types in an
 * object-oriented way. <br>
 * A content-class is the analogy to a {@link java.lang.Class} that reflects a
 * {@link java.lang.Object}. <br>
 * A content-class may be used to render a generic UI editor, synchronize the
 * schema of the persistence store (e.g. a DB), etc. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClass extends ContentReflectionObject {

  /** the name of the {@link #getContentClass() class} reflecting this type. */
  String CLASS_NAME = "Class";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getSuperClass() superClass} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_SUPER_CLASS = "superClass";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getSubClasses() subClasses} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_SUB_CLASSES = "subClasses";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getFields() fields} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_FIELDS = "fields";

  /**
   * The name of the {@link net.sf.mmm.content.model.api.ContentField field}
   * {@link #getDeclaredFields() declaredFields} for generic access via
   * {@link #getFieldValue(String)}.
   */
  String FIELD_NAME_DECLARED_FIELDS = "declaredFields";

  /**
   * This method gets an iterator of all fields declared by this class. This
   * does NOT include fields inherited from the
   * {@link #getSuperClass() super-class} except they are overridden by this
   * class. An inherited field can be overridden (if supported by the
   * {@link ContentModelService content-model}) in order to declare it more
   * specific meaning. Then the type of the field is a subtype of the field that
   * is overridden or the validator is more restrictive.<br>
   * 
   * @return a (read-only) iterable of all declared fields.
   */
  SizedIterable<? extends ContentField> getDeclaredFields();

  /**
   * This method gets the declared field with the given
   * {@link ContentField#getName() name}. Declared means that the field is
   * {@link ContentField#getInitiallyDefiningClass() initially defined} or
   * overridden in this class.<br>
   * An inherited field can be overridden (if supported by the
   * {@link ContentModelService content-model}) in order to declare it more
   * specific (typically the {@link ContentField#getFieldType() field-type} is
   * specialized). Such field can be identified via
   * {@link ContentField#getInitiallyDefiningClass()}.
   * 
   * @see #getField(String)
   * 
   * @param name is the name of the requested field of this class.
   * @return the field with the given name or <code>null</code> if no such
   *         field is declared by this class.
   */
  ContentField getDeclaredField(String name);

  /**
   * This method gets the field with the given
   * {@link ContentField#getName() name}. A field is either
   * {@link #getDeclaredFields() declared} in this class or inherited from a
   * {@link #getSuperClass() super-class}.
   * 
   * @param name is the name of the requested field of this class.
   * @return the field with the given name or <code>null</code> if no such
   *         field exists for this class.
   */
  ContentField getField(String name);

  /**
   * This method gets all fields defined in this class or inherited by the
   * super-class(es). An inherited field can be identified via
   * {@link ContentField#getDeclaringClass()}.
   * 
   * @return a (read-only) iterable of fields of this class.
   */
  SizedIterable<? extends ContentField> getFields();

  /**
   * This method gets the super-class of this class.
   * 
   * @return the super-class that is extended by this class.
   */
  ContentClass getSuperClass();

  /**
   * This method gets the list of all sub-classes.
   * 
   * @return an un-modifiable list of all sub-class.
   */
  List<? extends ContentClass> getSubClasses();

  /**
   * {@inheritDoc}
   */
  ClassModifiers getModifiers();

  /**
   * This method determines is this class is a super class of the given class.
   * <br>
   * 
   * @param contentClass is the class to compare with.
   * @return <code>true</code> if this class is a super-class of the given
   *         class.
   */
  boolean isSuperClassOf(ContentClass contentClass);

  /**
   * This is the opposite of the method
   * {@link ContentClass#isSuperClassOf(ContentClass)}. This means that
   * <code>class1.isSubClassOf(class2)</code> is equal to
   * <code>class2.isSuperClassOf(class1)</code>.
   * 
   * @param contentClass is the class to compare with.
   * @return <code>true</code> if this class is a sub-class of the given
   *         class.
   */
  boolean isSubClassOf(ContentClass contentClass);

}
