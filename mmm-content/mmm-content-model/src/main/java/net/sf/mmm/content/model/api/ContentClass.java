/* $Id$ */
package net.sf.mmm.content.model.api;

import java.util.Iterator;
import java.util.List;

/**
 * This is the interface of a content-class. It reflects the structure of the
 * {@link net.sf.mmm.content.api.ContentObject content-object} types in an
 * object-oriented way. <br>
 * A content-class is the analogy to a java {@link java.lang.Class} that
 * reflects a type. <br>
 * A content-class may be used to render a genric UI editor, synchronize the
 * schema of the persistence store (e.g. a DB), etc. <br>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentClass extends ContentReflectionObject {

  /**
   * the name of the root class reflecting
   * {@link net.sf.mmm.content.api.ContentObject}
   */
  String NAME_ROOT = "Object";

  /**
   * the name of the class reflecting {@link ContentReflectionObject}
   */
  String NAME_REFLECTION = "ReflectionObject";

  /**
   * the name of the class reflecting {@link ContentClass} (itself).
   */
  String NAME_CLASS = "Class";

  /**
   * the name of the class reflecting {@link ContentField}.
   */
  String NAME_FIELD = "Field";

  /**
   * the name of the class reflecting
   * {@link net.sf.mmm.content.api.security.ContentSecurityObject}.
   */
  String NAME_SECURITY = "SecurityObject";

  /**
   * the name of the class reflecting
   * {@link net.sf.mmm.content.api.security.ContentPrincipal}.
   */
  String NAME_PRINCIPAL = "Principal";

  /**
   * the name of the class reflecting
   * {@link net.sf.mmm.content.api.security.ContentUser}.
   */
  String NAME_USER = "User";

  /**
   * the name of the class reflecting
   * {@link net.sf.mmm.content.api.security.ContentGroup}.
   */
  String NAME_GROUP = "Group";

  /**
   * the name of the class reflecting
   * {@link net.sf.mmm.content.api.security.ContentPermission}.
   */
  String NAME_PERMISSION = "Permission";

  /**
   * the name of the class reflecting
   * {@link net.sf.mmm.content.api.security.ContentAction}.
   */
  String NAME_ACTION = "Action";

  /**
   * the name of the class reflecting content-resource.
   */
  String NAME_RESOURCE = "Resource";

  /**
   * the name of the class reflecting
   * {@link net.sf.mmm.content.resource.api.ContentFile}.
   */
  String NAME_FILE = "File";

  /**
   * the name of the class reflecting {@link ContentFolder}.
   */
  String NAME_FOLDER = "Folder";

  /**
   * This method gets the number of fields declared by this class.
   * 
   * @see #getDeclatedFields()
   * 
   * @return the number of fields declared by this class.
   */
  int getDeclaredFieldCount();

  /**
   * This method gets an iterator of all fields declared by this class. This
   * does NOT include fields inherited from the
   * {@link #getSuperClass() super-class} except they are overriden by this
   * class. An inherited field can be overridden (if supported by the
   * {@link ContentModelService content-model}) in order to declare it more
   * specific meaning. Then the type of the field is a subtype of the field that
   * is overriden or the validator is more restrictive.<br>
   * 
   * @return an (read-only) iterator of all declared fields.
   */
  Iterator<ContentField> getDeclatedFields();

  /**
   * This method gets the declared field with the given
   * {@link ContentField#getName() name}. Declared means that the field is
   * {@link ContentField#getInitiallyDefiningClass() initially defined} or
   * overriden in this class.<br>
   * An inherited field can be overridden (if supported by the
   * {@link ContentModelService content-model}) in order to declare it more
   * specific (typically the {@link ContentField#getFieldType() field-type} is
   * specialized). Such field can be identified via
   * {@link ContentField#getInitiallyDefiningClass()}.
   * 
   * @see #getField(String)
   * 
   * @param name
   *        is the name of the requested field of this class.
   * @return the field with the given name or <code>null</code> if no such
   *         field is declared by this class.
   */
  ContentField getDeclaredField(String name);

  /**
   * This method gets the field with the given
   * {@link ContentField#getName() name}. A field is eigther
   * {@link #getDeclaredFieldCount() declared} in this class or inherited
   * from a {@link #getSuperClass() super-class}.
   * 
   * @param name
   *        is the name of the requested field of this class.
   * @return the field with the given name or <code>null</code> if no such
   *         field exists for this class.
   */
  ContentField getField(String name);

  /**
   * This method gets the number of fields defined in this class or inherited by
   * the super-classe(s).
   * 
   * @return the number of fields.
   */
  int getFieldCount();

  /**
   * This method gets all fields defined in this class or inherited by the
   * super-classe(s). An inherited field can be identified via
   * {@link ContentField#getDeclaringClass()}.
   * 
   * @return an (read-only) iterator of fields of this class.
   */
  Iterator<ContentField> getFields();

  /**
   * This method gets the super-class of this class.
   * 
   * @return the super-class that is extended by this class.
   */
  ContentClass getSuperClass();

  /**
   * This method gets the list of all sub-classes.
   * 
   * @return an unmodifyable list of all sub-class.
   */
  List<ContentClass> getSubClasses();

  /**
   * @see net.sf.mmm.content.model.api.ContentReflectionObject#getModifiers()
   */
  ClassModifiers getModifiers();

  /**
   * This method determines is this class is a super class of the given class.
   * <br>
   * 
   * @param contentClass
   *        is the class to compare with.
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
   * @param contentClass
   *        is the class to compare with.
   * @return <code>true</code> if this class is a sub-class of the given
   *         class.
   */
  boolean isSubClassOf(ContentClass contentClass);

}
