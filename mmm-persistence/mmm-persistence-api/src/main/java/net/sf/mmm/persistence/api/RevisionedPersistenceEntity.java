/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

/**
 * This is the interface for a {@link PersistenceEntity} that is
 * revision-controlled.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionedPersistenceEntity extends PersistenceEntity {

  /**
   * The {@link PersistenceEntityManager#load(Object) latest}
   * {@link #getRevision() revision} of a {@link PersistenceEntity}. It can also
   * be used for generic
   * {@link RevisionedPersistenceEntityManager#load(Object, int) requests} of
   * the {@link PersistenceEntityManager#load(Object) latest} revision.<br>
   * Value is: {@value}
   */
  int LATEST_REVISION = -1;

  /**
   * This method gets the revision of this entity. The latest revision of an
   * entity will always return
   * {@link RevisionedPersistenceEntity#LATEST_REVISION}. Otherwise this object
   * is a <em>historic entity</em> and it will be read-only so modifications are
   * NOT permitted.
   * 
   * @return the revision.
   */
  int getRevision();

}
