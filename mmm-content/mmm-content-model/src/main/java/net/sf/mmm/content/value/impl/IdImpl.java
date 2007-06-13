/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.value.impl;

import net.sf.mmm.content.value.api.Id;
import net.sf.mmm.value.api.ValueParseException;
import net.sf.mmm.value.api.ValueParseStringException;
import net.sf.mmm.value.base.AbstractValueManager;

/**
 * This is the implementation of the {@link Id} interface.<br>
 * Since the revision and
 * {@link net.sf.mmm.content.api.ContentObject#getContentClass() content-class}
 * of a {@link net.sf.mmm.content.api.ContentObject content-object} does not
 * change, their primary keys are stored in this ID implementation. This allows
 * to determine the content-class and revision of the resource without any cost
 * (e.g. DB lookup). Especially a content-object-instance can be created from
 * the ID using lazy loading.<br>
 * This {@link Id} implementation builds the ID out of four parts:
 * <ul>
 * <li>{@link #getObjectId() object-id} - unique object/resource counter but
 * <code>0</code> for id of a content-class.</li>
 * <li>{@link #getStoreId() store-id} - if multiple backends are used to store
 * objects this identifies the actual store. In that case two totally different
 * objects may share the same object-id. Unique identification is only possible
 * in combination with this store-id.</li>
 * <li>{@link #getRevision() revision} - a resource can have multiple revisions
 * (in the version history). All revisions of a resource (in the same branch)
 * share the same {@link #getObjectId() object-id}. An {@link Id} uniquely
 * identifies the specific resource-revision.</li>
 * <li>{@link #getClassId() class-id} - is the id of the content-class that
 * reflects the {@link net.sf.mmm.content.api.ContentObject content-object}
 * identified by this {@link Id}. See also {@link #getContentClassId()}.</li>
 * </ul>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public final class IdImpl implements Id {

  /** UID for serialization. */
  private static final long serialVersionUID = 4050487802653521717L;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * {@link net.sf.mmm.content.api.ContentObject content-object}.
   */
  public static final int CLASS_ID_ROOT = 0;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * {@link net.sf.mmm.content.model.api.ContentReflectionObject content-relection-object}.
   */
  public static final int CLASS_ID_RELECTION = 1;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * {@link net.sf.mmm.content.model.api.ContentClass content-class}.
   */
  public static final int CLASS_ID_CLASS = 2;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * {@link net.sf.mmm.content.model.api.ContentField content-field}.
   */
  public static final int CLASS_ID_FIELD = 3;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-resource.
   */
  public static final int CLASS_ID_RESOURCE = 10;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-folder.
   */
  public static final int CLASS_ID_FOLDER = 11;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-file.
   */
  public static final int CLASS_ID_FILE = 12;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-principal.
   */
  public static final int CLASS_ID_PRINCIPAL = 7;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-user.
   */
  public static final int CLASS_ID_USER = 8;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-group.
   */
  public static final int CLASS_ID_GROUP = 9;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-group.
   */
  public static final int CLASS_ID_ACTION = 10;

  /**
   * the id number of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of a
   * content-group.
   */
  public static final int CLASS_ID_PERMISSION = 11;

  /**
   * the first {@link #getClassId() class-id} that can be used for custom
   * classes. All class-IDs lower than this are reserved for system classes.
   */
  public static final int MINIMUM_CUSTOM_CLASS_ID = 4096;

  /**
   * the first {@link #getObjectId() object-id} that can be used for custom
   * fields. All field-IDs lower than this are reserved for system fields.
   */
  public static final int MINIMUM_CUSTOM_FIELD_ID = 4096;

  /** the {@link #getObjectId() object-ID} of a class. */
  public static final long OID_CLASS = 0;

  /** the {@link #getObjectId() object-ID} of a field. */
  public static final long OID_FIELD = 1;

  /** the id of the root-folder. */
  public static final long FOLDER_ID_ROOT = 2;

  /** the id of the reflection-folder. */
  public static final long FOLDER_ID_REFLECTION = 3;

  /** the id of the classes-folder. */
  public static final long FOLDER_ID_CLASSES = 4;

  /** the id of the fields-folder. */
  public static final long FOLDER_ID_FIELDS = 5;

  /** the id of the principals-folder. */
  public static final long FOLDER_ID_PRINCIPALS = 6;

  /** the id of the users-folder. */
  public static final long FOLDER_ID_USERS = 7;

  /** the id of the groups-folder. */
  public static final long FOLDER_ID_GROUPS = 8;

  /** the id of the IDs-folder. */
  public static final long FOLDER_ID_IDS = 9;

  /** the id of the resources-folder. */
  public static final long FOLDER_ID_RESOURCES = 10;

  /**
   * the first {@link #getObjectId() object-id} that can be used for custom
   * resources. All object-IDs lower than this are reserved for system resources
   * (e.g. root-folder, etc.).
   */
  public static final long MINIMUM_CUSTOM_RESOURCE_ID = 4096;

  /** the delimiter used in the string representations of an id */
  private static final char SEPARATOR_CHAR = '.';

  /** the delimiter used in the string representations of an id */
  private static final String SEPARATOR = String.valueOf(SEPARATOR_CHAR);

  /**
   * the id of the root
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} (the class
   * that all other classes are derived from).
   */
  public static final IdImpl ID_CLASS_ROOT = new IdImpl(CLASS_ID_ROOT);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects
   * {@link net.sf.mmm.content.model.api.ContentReflectionObject content-reflection-object}.
   */
  public static final IdImpl ID_CLASS_REFELCTION = new IdImpl(CLASS_ID_RELECTION);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects itself (Like {@link Class} in java).
   */
  public static final IdImpl ID_CLASS_CLASS = new IdImpl(CLASS_ID_CLASS);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects {@link net.sf.mmm.content.model.api.ContentField content-field}.
   */
  public static final IdImpl ID_CLASS_FIELD = new IdImpl(CLASS_ID_FIELD);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects content-resource.
   */
  public static final IdImpl ID_CLASS_RESOURCE = new IdImpl(CLASS_ID_RESOURCE);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects content-folder.
   */
  public static final IdImpl ID_CLASS_FOLDER = new IdImpl(CLASS_ID_FOLDER);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects content-file.
   */
  public static final IdImpl ID_CLASS_FILE = new IdImpl(CLASS_ID_FILE);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects content-principal.
   */
  public static final IdImpl ID_CLASS_PRINCIPAL = new IdImpl(CLASS_ID_PRINCIPAL);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects a content-user.
   */
  public static final IdImpl ID_CLASS_USER = new IdImpl(CLASS_ID_USER);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects a content-group.
   */
  public static final IdImpl ID_CLASS_GROUP = new IdImpl(CLASS_ID_GROUP);

  /**
   * the id of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} that
   * reflects a content-action.
   */
  public static final IdImpl ID_CLASS_ACTION = new IdImpl(CLASS_ID_ACTION);

  /**
   * the id of the root-folder.
   */
  public static final IdImpl ID_FOLDER_ROOT = new IdImpl(FOLDER_ID_ROOT, CLASS_ID_FOLDER);

  /**
   * the id of the resources-folder.
   */
  public static final IdImpl ID_FOLDER_RESOURCES = new IdImpl(FOLDER_ID_RESOURCES, CLASS_ID_FOLDER);

  /**
   * the id of the IDs-folder
   */
  public static final IdImpl ID_FOLDER_IDS = new IdImpl(FOLDER_ID_IDS, CLASS_ID_FOLDER);

  /**
   * the UID of the object as complete entity with all its revisions or
   * <code>0</code> if the resource is a content class.
   */
  private final long objectId;

  /**
   * the UID of the content class of the resource associated with this ID or in
   * case of a content class the UID of that class.
   */
  private final int classId;

  /**
   * the revision counter of the identified resource version, <code>0</code>
   * for latest version.
   */
  private final int revision;

  /**
   * the id of the content store where the identified resource is persisted. The
   * default store has the id <code>0</code>.
   */
  private final int storeId;

  /**
   * The constructor for the ID of a
   * {@link net.sf.mmm.content.model.api.ContentClass content-class}.
   * 
   * @see IdImpl
   * 
   * @param classUid is the class id.
   */
  public IdImpl(int classUid) {

    this(OID_CLASS, classUid);
  }

  /**
   * The constructor for the ID of a
   * {@link net.sf.mmm.content.api.ContentObject content-object} or the latest
   * revision of a content-resource in the default store.
   * 
   * @see IdImpl
   * 
   * @param objectUid is the object id.
   * @param classUid is the class id.
   */
  public IdImpl(long objectUid, int classUid) {

    this(objectUid, classUid, 0);
  }

  /**
   * The constructor for the ID of a content-resource in the default store.
   * 
   * @see IdImpl
   * 
   * @param objectUid is the object id.
   * @param classUid is the class id.
   * @param revisionNumber is the revision number
   */
  public IdImpl(long objectUid, int classUid, int revisionNumber) {

    this(objectUid, classUid, revisionNumber, 0);
  }

  /**
   * The constructor for the ID of a content-resource in the specified store.
   * 
   * @see IdImpl
   * 
   * @param objectUid is the object id.
   * @param classUid is the class id.
   * @param revisionNumber is the revision number
   * @param storeUid is the id of the store.
   */
  public IdImpl(long objectUid, int classUid, int revisionNumber, int storeUid) {

    super();
    this.objectId = objectUid;
    this.classId = classUid;
    this.revision = revisionNumber;
    this.storeId = storeUid;
  }

  /**
   * The constructor.
   * 
   * @param uid is the ID to create in the format produced by the
   *        {@link #toString()} method.
   * @throws ValueParseException if the given string representation is invalid
   *         and can not be parsed.
   */
  public IdImpl(String uid) throws ValueParseException {

    super();
    long rid = 0;
    int cid = 0;
    int rev = 0;
    int store = 0;

    StringBuffer token = new StringBuffer();
    int end = uid.length() - 1;
    int pos = 0;
    for (int i = 0; i <= end; i++) {
      char c = uid.charAt(i);
      if (i == end) {
        token.append(c);
        c = SEPARATOR_CHAR;
      }
      if (c == SEPARATOR_CHAR) {
        String s = token.toString();
        token.setLength(0);
        try {
          if (pos == 0) {
            rid = Long.parseLong(s);
          } else if (pos == 1) {
            cid = Integer.parseInt(s);
          } else if (pos == 2) {
            rev = Integer.parseInt(s);
          } else if (pos == 3) {
            store = Integer.parseInt(s);
          }
        } catch (NumberFormatException e) {
          throw new ValueParseStringException(uid, IdImpl.class, "id");
        }
        pos++;
      } else {
        token.append(c);
      }
    }
    if ((pos < 2) || (pos > 4)) {
      throw new ValueParseStringException(uid, IdImpl.class, "id");
    }
    this.objectId = rid;
    this.classId = cid;
    this.revision = rev;
    this.storeId = store;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object other) {

    if ((other != null) && (other instanceof Id)) {
      return (toString().equals(other.toString()));
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    return (int) (this.objectId ^ (this.objectId >>> 32) ^ this.revision ^ this.storeId);
  }

  /**
   * This method gets UID of the
   * {@link net.sf.mmm.content.model.api.ContentClass content-class} of the
   * {@link net.sf.mmm.content.api.ContentObject content-object} associated with
   * this ID.
   * 
   * @return the classId.
   */
  public int getClassId() {

    return this.classId;
  }

  /**
   * This method gets the object-id.
   * 
   * @return the object-id.
   */
  public long getObjectId() {

    return this.objectId;
  }

  /**
   * This method gets the revision number of the specific version associated
   * with this id.
   * 
   * @return the revision.
   */
  public int getRevision() {

    return this.revision;
  }

  /**
   * This method gets the id of the store where the identified content object is
   * persisted. The default store has the id <code>0</code>.
   * 
   * @return the storeId.
   */
  public int getStoreId() {

    return this.storeId;
  }

  /**
   * This method gets the {@link Id ID} of the associated
   * {@link net.sf.mmm.content.model.api.ContentClass content-class}
   * 
   * @return the ID of the associated content-class.
   */
  public IdImpl getContentClassId() {

    if (this.objectId == 0) {
      // identified object is a class
      return ID_CLASS_CLASS;
    } else if (this.objectId == 1) {
      // identified object is a class
      return ID_CLASS_FIELD;
    } else {
      // identified object is no class, get the class id
      // TOOD: use pool!?
      return new IdImpl(0, this.classId);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    result.append(Long.toString(this.objectId, 16).toUpperCase());
    result.append(SEPARATOR);
    result.append(Long.toString(this.classId, 16).toUpperCase());
    if ((this.revision != 0) && (this.storeId != 0)) {
      result.append(SEPARATOR);
      result.append(Long.toString(this.revision));
      if (this.storeId != 0) {
        result.append(SEPARATOR);
        result.append(Long.toString(this.storeId));
      }
    }
    return result.toString();
  }

  /**
   * This inner class is the manager for the value.
   */
  public static class Manager extends AbstractValueManager<IdImpl> {

    /**
     * The constructor.
     */
    public Manager() {

      super();
    }

    /**
     * {@inheritDoc}
     */
    public String getName() {

      return VALUE_NAME;
    }

    /**
     * {@inheritDoc}
     */
    public IdImpl parse(String valueAsString) throws ValueParseException {

      return new IdImpl(valueAsString);
    }

    /**
     * {@inheritDoc}
     */
    public Class<IdImpl> getValueClass() {

      return IdImpl.class;
    }

  }

}
