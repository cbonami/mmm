/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.datatype;

import net.sf.mmm.data.api.datatype.DataId;
import net.sf.mmm.data.api.reflection.DataField;
import net.sf.mmm.data.base.datatype.AbstractContentId;

/**
 * This is the implementation of the {@link DataId} interface for the ID of a
 * {@link net.sf.mmm.data.api.reflection.DataField ContentField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class ContentFieldId extends AbstractContentId {

  /** UID for serialization. */
  private static final long serialVersionUID = 2291237026928351257L;

  /** @see #POOL */
  private static final int POOL_SIZE = 256;

  /** @see #valueOf(int) */
  private static final ContentFieldId[] POOL = new ContentFieldId[POOL_SIZE];

  /**
   * The constructor.
   * 
   * <b>ATTENTION:</b><br>
   * Please use {@link net.sf.mmm.data.api.DataIdManager} to create
   * instances.
   * 
   * @param objectId is the {@link #getObjectId() object-ID} identifying the
   *        {@link DataField}.
   */
  public ContentFieldId(int objectId) {

    super(objectId);
  }

  /**
   * {@inheritDoc}
   */
  public int getClassId() {

    return DataField.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  public DataId getContentClassId() {

    return ContentClassId.FIELD;
  }

  /**
   * {@inheritDoc}
   */
  public int getRevision() {

    return 0;
  }

  /**
   * {@inheritDoc}
   */
  public int getStoreId() {

    return 0;
  }

  /**
   * This method gets the {@link ContentFieldId} instance for the given
   * <code>fieldUid</code>. A pool is used to store the ID instances for the
   * first N <code>field-IDs</code>. For those this method will always return
   * the same instance.
   * 
   * @param fieldUid is the {@link #getObjectId() object-ID} of the requested
   *        {@link ContentFieldId} instance.
   * @return the requested {@link ContentClassId} instance.
   */
  public static ContentFieldId valueOf(int fieldUid) {

    ContentFieldId id;
    if (fieldUid < POOL_SIZE) {
      id = POOL[fieldUid];
      if (id == null) {
        id = new ContentFieldId(fieldUid);
        POOL[fieldUid] = id;
      }
    } else {
      id = new ContentFieldId(fieldUid);
    }
    return id;
  }

}
