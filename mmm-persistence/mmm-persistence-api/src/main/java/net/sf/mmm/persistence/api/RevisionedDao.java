/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.api;

import java.util.List;

import net.sf.mmm.util.entity.api.RevisionedEntity;
import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the interface for a {@link GenericDao} with the ability of revision-control. It organizes a
 * revision-history (journal) of the {@link #getEntityClassImplementation() managed entities}.
 * 
 * @see RevisionedEntity
 * 
 * @param <ID> is the type of the {@link RevisionedEntity#getId() primary key}.
 * @param <ENTITY> is the {@link #getEntityClassImplementation() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface RevisionedDao<ID, ENTITY extends RevisionedEntity<ID>> extends GenericDao<ID, ENTITY> {

  /**
   * This method will get the {@link List} of historic {@link RevisionedEntity#getRevision() revisions} before
   * the given <code>entity</code>. If the given <code>entity</code> is the
   * {@link RevisionedEntity#LATEST_REVISION latest revision} ...<br>
   * If the <code>entity</code> is NOT revision controlled, an {@link java.util.Collections#emptyList() empty
   * list} is returned.
   * 
   * @param entity is the according {@link RevisionedEntity}.
   * @return the {@link List} of historic {@link RevisionedEntity#getRevision() revisions}.
   */
  List<Number> getRevisionHistory(ENTITY entity);

  /**
   * This method will get the {@link List} of {@link RevisionMetadata} from the
   * {@link RevisionedEntity#getRevision() revision}-history of the {@link #getEntityClassImplementation()
   * entity} with the given <code>id</code>.
   * 
   * @param id is the {@link RevisionedEntity#getId() primary key} of the entity for which the
   *        history-metadata is requested.
   * @return the requested {@link List} of {@link RevisionMetadata}.
   */
  List<RevisionMetadata> getRevisionHistoryMetadata(Object id);

  /**
   * This method loads a historic {@link RevisionedEntity#getRevision() revision} of the
   * {@link RevisionedEntity} with the given <code>id</code> from the persistent store.<br>
   * However if the given <code>revision</code> is {@link RevisionedEntity#LATEST_REVISION} the
   * {@link #load(Object) latest revision will be loaded}.<br>
   * 
   * @param id is the {@link RevisionedEntity#getId() primary key} of the requested {@link RevisionedEntity
   *        entity}.
   * @param revision is the {@link RevisionedEntity#getRevision() revision} of the requested entity or
   *        {@link RevisionedEntity#LATEST_REVISION} to get the {@link #load(Object) latest} revision.
   * @return the requested {@link RevisionedEntity entity}.
   * @throws ObjectNotFoundException if the requested {@link RevisionedEntity entity} could NOT be found.
   */
  ENTITY load(ID id, Number revision) throws ObjectNotFoundException;

  /**
   * This method creates a new {@link RevisionedEntity#getRevision() revision} of the given entity. The given
   * entity is saved and a copy is written to the revision-history
   * 
   * @param entity is the entity to create a new revision of.
   * @return the {@link RevisionedEntity#getRevision() revision} of the created history-entry.
   */
  Number createRevision(ENTITY entity);

  /**
   * {@inheritDoc}
   * 
   * The behaviour of this method depends on the revision-control strategy of the implementation.<br>
   * <ul>
   * <li>In case of an <em>audit-proof revision-history</em> the deletion of the
   * {@link RevisionedEntity#LATEST_REVISION latest revision} of an entity will only move it to the history
   * while the deletion of a {@link RevisionedEntity#getRevision() historic entity} is NOT permitted and will
   * cause a {@link PersistenceException}.</li>
   * <li>In case of an <em>on-demand revision-history</em> the deletion of the
   * {@link RevisionedEntity#LATEST_REVISION latest revision} of an entity will either move it to the history
   * or</li>
   * </ul>
   * If the given <code>entity</code> is a {@link RevisionedEntity#getRevision() historic entity} the
   * according historic
   */
  @Override
  void delete(ENTITY entity);

}
