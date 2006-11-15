/* $Id$ */
package net.sf.mmm.content.api;

import net.sf.mmm.content.model.api.ContentClass;
import net.sf.mmm.content.security.api.PermissionDeniedException;
import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.util.xml.api.XmlSerializable;

/**
 * This is the abstract interface for any content object. This can be a
 * {@link net.sf.mmm.content.model.api.ContentReflectionObject reflection-object}
 * (class or field), or a resource (file, folder, user, group, etc.).
 * 
 * TODO: add generic methods "ContentObject getParent()", "String getPath()" 
 *       "Collection<ContentObject> getChildren()" instead of content-folder?
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface ContentObject extends XmlSerializable {

  /**
   * the variable-name of the current object in the
   * {@link net.sf.mmm.context.api.Context context} for
   * link net.sf.mmm.content.model.api.ContentField#calculate(ContentObject) calculation.
   */
  String ENV_VARIABLE_THIS = "this";

  /**
   * the attribute for the {@link #getName() name}.
   */
  String XML_ATR_ROOT_NAME = "name";

  /**
   * the attribute for the {@link #getName() name}.
   */
  String XML_ATR_ROOT_ID = "id";

  /**
   * the attribute for the {@link #isDeleted() deleted-flag}.
   */
  String XML_ATR_ROOT_DELETED = "deleted";

  /**
   * This method gets the unique identifier of this content-object.
   * 
   * @return the unique ID.
   */
  Id getId();

  /**
   * This method gets the name of this content-object. The name must be unique
   * for all content-objects in the same folder (that have the same parent
   * folder). <br>
   * The root-folder has the empty string as name while any other content-object
   * must have a name with a length greater zero.
   * 
   * @return the name of the resource.
   */
  String getName();

  /**
   * This method gets the content-class used to reflect this content-object. The
   * content-class represents the exact type of this content-object.
   * 
   * @return the content-class of this resource.
   */
  ContentClass getContentClass();

  /**
   * This method gets the meta-data of this object.
   * 
   * @return the meta-data.
   * @throws PermissionDeniedException
   *         if you (the current user) does not have permission to perform the
   *         operation.
   */
  // MutableMetaDataSet getMetaData() throws PermissionDeniedException;
  /**
   * This method determines if this content-object is marked as deleted. <br>
   * A deleted class or field can NOT be modified. No instances or sub-classes
   * can be created of a deleted class. Deleted fields are NOT visible in the
   * editor. If a class or field is deleted it can eighter be undeleted or
   * destroyed (then all instances will be removed from the persistence store).
   * <br>
   * If the delted flag is set, the class or field can be seen as deprecated.
   * 
   * @return <code>true</code> if this object is marked as deleted.
   */
  boolean isDeleted();

  /**
   * This method gets the value of the specified
   * {@link net.sf.mmm.content.model.api.ContentField field}. It is the
   * generic getter for all fields of this object. <br>
   * E.g. <code>getFieldValue("id")</code> will produce the same result as
   * {@link ContentObject#getId()}. Additionally all fields that are defined
   * in sub-types are accessible.
   * 
   * @param fieldName
   *        is the {@link ContentObject#getName() name} of the
   *        {@link net.sf.mmm.content.model.api.ContentField field} to get.
   * @return the value of the specified field or <code>null</code> if not set.
   * @throws NoSuchFieldException
   *         if the objects {@link #getContentClass() content-class} does not
   *         have a {@link net.sf.mmm.content.model.api.ContentField field}
   *         with the given {@link ContentObject#getName() name}.
   * @throws PermissionDeniedException
   *         if you (the current user) does not have permission to perform the
   *         operation.
   * @throws ContentException
   *         TODO: if the specified field is
   *         {@link net.sf.mmm.content.model.api.FieldModifiers#isTransient() transient}
   *         but an error occured calculating its value.
   */
  Object getFieldValue(String fieldName) throws NoSuchFieldException, PermissionDeniedException,
      ContentException;

  /**
   * This method sets the value of the specified
   * {@link net.sf.mmm.content.model.api.ContentField field}. It is the
   * generic setter for all fields of this resource. The field must NOT be
   * {@link net.sf.mmm.content.model.api.FieldModifiers#isReadOnly() read-only}.
   * A {@link net.sf.mmm.content.model.api.FieldModifiers#isStatic() static}
   * field can only be set on a {@link ContentClass content-class}. Other
   * fields only on a resource.
   * 
   * @see #getFieldValue(String)
   * 
   * @param fieldName
   *        is the {@link ContentObject#getName() name} of the
   *        {@link net.sf.mmm.content.model.api.ContentField field} to set.
   *        The field must be defined in the object's
   *        {@link #getContentClass() content-class}.
   * @param value
   *        is the new value for the field. It must be an instance of the
   *        {@link net.sf.mmm.content.model.api.ContentField#getFieldType() type}
   *        declared by the
   *        {@link net.sf.mmm.content.model.api.ContentField field}.
   * @throws NoSuchFieldException
   *         if the objects {@link #getContentClass() content-class} does not
   *         have a {@link net.sf.mmm.content.model.api.ContentField field}
   *         with the given {@link ContentObject#getName() name}.
   * @throws PermissionDeniedException
   *         if you (the current user) does not have permission to perform the
   *         operation.
   * @throws ContentException
   *         TODO: if the field does not exist for this resource or has an
   *         incompatible type for the given value.
   */
  void setFieldValue(String fieldName, Object value) throws NoSuchFieldException,
      PermissionDeniedException, ContentException;

  /**
   * This method test if the given action is allowed for the current user.
   * 
   * @param action
   *        is the action to be checked.
   * @return <code>true</code> if the current user is allowed to perform the
   *         specified action of this object.
   */
  // boolean checkPermission(ContentActionIF action);
   

  /**
   * This method validates the given object. This is done by
   * {@link net.sf.mmm.content.api.model.ContentFieldIF#validate(Object) validating}
   * the {@link #getFieldValue(String) values} of all
   * {@link net.sf.mmm.content.api.model.ContentFieldIF fields} as defined by
   * the objects {@link #getContentClass() content-class}.
   * 
   * @return the result of the validation.
   * @throws PermissionDeniedException
   *         if you (the current user) does not have permission to perform the
   *         operation.
   */
  // ValidationResultIF validate() throws PermissionDeniedException;
}
