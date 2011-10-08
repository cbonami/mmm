/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import net.sf.mmm.util.nls.api.NlsUnsupportedOperationException;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the interface for a {@link PersistenceManager} with the ability of
 * revision-control (auditing), e.g. provided by hibernate-envers.
 * 
 * @see RevisionedPersistenceEntity
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionedPersistenceManager extends PersistenceManager {

  /**
   * This method gets the individual {@link RevisionedPersistenceEntityManager}
   * {@link PersistenceEntityManager#getEntityClass() responsible} for the given
   * <code>entityClass</code>.
   * 
   * @param <ENTITY> is the generic entity-type.
   * @param <ID> is the type of the {@link PersistenceEntity#getId() primary
   *        key}.
   * @param entityClass is the type of the {@link PersistenceEntity} for which
   *        the according {@link PersistenceEntityManager} is requested.
   * @return the {@link PersistenceEntityManager} responsible for the given
   *         <code>entityClass</code>.
   * @throws ObjectNotFoundException if the requested
   *         {@link PersistenceEntityManager manager} could NOT be found.
   */
  <ID, ENTITY extends RevisionedPersistenceEntity<ID>> RevisionedPersistenceEntityManager<ID, ENTITY> getRevisionedManager(
      Class<ENTITY> entityClass) throws ObjectNotFoundException;

  /**
   * This method loads the specified
   * {@link RevisionedPersistenceEntity#getRevision() revision} of the
   * {@link PersistenceEntity} with the given <code>entityClass</code> and
   * <code>id</code> from the persistent store.
   * 
   * @see RevisionedPersistenceEntityManager#load(Object, Number)
   * 
   * @param <ENTITY> is the generic type of the <code>entityClass</code>.
   * @param <ID> is the type of the {@link PersistenceEntity#getId() primary
   *        key}.
   * @param entityClass is the class reflecting the type of the requested
   *        entity.
   * @param id is the {@link PersistenceEntity#getId() primary key} of the
   *        requested entity.
   * @param revision is the {@link RevisionedPersistenceEntity#getRevision()
   *        revision} of the requested entity or
   *        {@link RevisionedPersistenceEntity#LATEST_REVISION} to get the
   *        {@link #load(Class, Object) latest} revision.
   * @return the requested entity. It is immutable if NOT the
   *         {@link #load(Class, Object) latest} revision.
   * @throws ObjectNotFoundException if the requested {@link PersistenceEntity
   *         entity} could NOT be found.
   * @throws NlsUnsupportedOperationException if the given
   *         <code>entityClass</code> reflects a {@link PersistenceEntity} that
   *         is NOT revision-controlled.
   */
  <ID, ENTITY extends RevisionedPersistenceEntity<ID>> ENTITY load(Class<ENTITY> entityClass,
      ID id, Number revision) throws ObjectNotFoundException, NlsUnsupportedOperationException;

}
