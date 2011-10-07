/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.content.api;

import java.io.Serializable;

import net.sf.mmm.content.datatype.api.ContentId;
import net.sf.mmm.persistence.api.RevisionedPersistenceEntity;
import net.sf.mmm.util.lang.api.attribute.AttributeReadTitle;

/**
 * This is the abstract interface for an object of the content-repository. An
 * instance of {@link ContentObject this} interface represents the
 * {@link #getRevision() revision} of an <em>entity</em> stored in the content
 * repository. There are two cases to distinguish:
 * <ul>
 * <li>The {@link #LATEST_REVISION latest revision}.<br/>
 * A {@link ContentObject} pointing to {@link #LATEST_REVISION} represents the
 * latest state of the entity and can be modified.</li>
 * <li>A previous {@link #getRevision() revision}.<br/>
 * If the entity is
 * {@link net.sf.mmm.content.reflection.api.ContentClass#isRevisionControlled()
 * revision-controlled}, it has a history of modifications. A
 * {@link ContentObject} can represent a historic {@link #getRevision()
 * revision} out of this history. It therefore is immutable so operations to
 * modify the {@link ContentObject} will fail.</li>
 * </ul>
 * The core Java OO-world is rewritten here as meta-model inside Java. The
 * following table shows the mmm types with its corresponding Java constructs:<br>
 * <table border="1">
 * <tr>
 * <th>Java</th>
 * <th>mmm</th>
 * </tr>
 * <tr>
 * <td>{@link Object}</td>
 * <td>{@link ContentObject}</td>
 * </tr>
 * <tr>
 * <td>{@link Class}</td>
 * <td>{@link net.sf.mmm.content.reflection.api.ContentClass}</td>
 * </tr>
 * <tr>
 * <td>{@link java.lang.reflect.Field Field}/{@link java.lang.reflect.Method
 * Method}</td>
 * <td>{@link net.sf.mmm.content.reflection.api.ContentField ContentField}</td>
 * </tr>
 * <tr>
 * <td>{@link ClassLoader}</td>
 * <td>{@link net.sf.mmm.content.reflection.api.ContentClassLoader
 * ContentClassLoader}</td>
 * </tr>
 * </table>
 * <br>
 * The tree spanned by the hierarchy of the
 * {@link net.sf.mmm.content.reflection.api.ContentClass}es is called
 * <em>content-model</em>.<br>
 * A sub-type of this interface has to follow specific rules in order to be an
 * <em>entity-type</em> that will have an according
 * {@link net.sf.mmm.content.reflection.api.ContentClass}. For more details see
 * {@link net.sf.mmm.content.base.AbstractContentObject
 * AbstractContentObject}.
 * 
 * @see ContentId
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@ContentClassAnnotation(id = ContentObject.CLASS_ID, name = ContentObject.CLASS_NAME, isExtendable = false)
public abstract interface ContentObject extends RevisionedPersistenceEntity,
    AttributeReadTitle<String>, Serializable {

  /**
   * The {@link ContentObject#getTitle() name} of the
   * {@link net.sf.mmm.content.reflection.api.ContentClass} reflecting this
   * type.
   */
  String CLASS_NAME = "ContentObject";

  /**
   * The {@link net.sf.mmm.content.datatype.api.ContentId#getClassId() class-ID}
   * of the {@link net.sf.mmm.content.reflection.api.ContentClass} reflecting
   * this type.
   */
  int CLASS_ID = 0;

  /**
   * The name of the {@link net.sf.mmm.content.reflection.api.ContentField
   * field} {@link #getId() ID}.
   */
  String FIELD_NAME_ID = "id";

  /**
   * The name of the {@link net.sf.mmm.content.reflection.api.ContentField
   * field} {@link #getTitle() name} for generic access.
   */
  String FIELD_NAME_TITLE = "title";

  /**
   * {@inheritDoc}
   */
  @ContentFieldAnnotation(id = 0, isFinal = true)
  Long getId();

  /**
   * This method gets the ID of the type (
   * {@link net.sf.mmm.content.reflection.api.ContentClass}) of this object.
   * 
   * @see ContentId#getClassId()
   * 
   * @return the class ID.
   */
  @ContentFieldAnnotation(id = 1, isStatic = true, isFinal = true)
  int getContentClassId();

  /**
   * This method gets the title of this content-object. For objects of
   * particular {@link net.sf.mmm.content.reflection.api.ContentClass types}
   * (e.g. {@link net.sf.mmm.content.reflection.api.ContentClass} or
   * {@link net.sf.mmm.content.reflection.api.ContentField}) title has to be
   * unique. <br>
   * 
   * @return the title of this object.
   */
  @ContentFieldAnnotation(id = 2, name = FIELD_NAME_TITLE, isFinal = true)
  String getTitle();

  /**
   * This method gets the unique ID of this content-object.
   * 
   * @return the unique ID or <code>null</code> if the entity has been
   *         instantiated directly and NOT yet been persisted in the
   *         content-repository.
   */
  @ContentFieldAnnotation(id = 8, isTransient = true, isFinal = true, isReadOnly = true)
  ContentId getContentId();

  // /**
  // * This method gets the history of this specific resource revision
  // (version).
  // *
  // * @return the history of this resource revision.
  // */
  // RevisionHistory getRevisionHistory();

}
