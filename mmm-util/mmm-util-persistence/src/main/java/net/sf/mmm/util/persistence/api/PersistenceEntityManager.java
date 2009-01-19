/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.api;

import net.sf.mmm.util.nls.api.ObjectNotFoundException;
import net.sf.mmm.util.persistence.api.search.PersistenceSearcher;

/**
 * This is the interface for a manager of a {@link #getEntityClass() specific
 * type} of {@link PersistenceEntity}. In other contexts this is often called a
 * DAO (Data Access Object). It is responsible for {@link #load(Object) loading}
 * , {@link #save(PersistenceEntity) saving} and {@link PersistenceSearcher
 * searching} all {@link PersistenceEntity entities} of the
 * {@link #getEntityClass() according type}.<br>
 * For each (non-abstract) implementation of {@link PersistenceEntity} there
 * should exists one instance of this interface. Typically when you create a
 * custom {@link PersistenceEntity entity} you will also create a custom
 * interface and implementation of an according {@link PersistenceEntityManager}
 * . If there is no custom implementation of a {@link PersistenceEntityManager}
 * for some type of {@link PersistenceEntity}, a generic implementation is used
 * as fallback.<br>
 * The {@link PersistenceManager} can be seen as the manager of all
 * {@link PersistenceEntityManager} and acts as front-end to them.
 * 
 * @see PersistenceEntity
 * @see PersistenceManager
 * 
 * @param <ENTITY> is the {@link #getEntityClass() type} of the managed entity.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PersistenceEntityManager<ENTITY extends PersistenceEntity> {

  /**
   * This method gets the class reflecting the {@link PersistenceEntity} managed
   * by this {@link PersistenceEntityManager}.
   * 
   * @return the according entity-class.
   */
  Class<ENTITY> getEntityClass();

  /**
   * This method loads the {@link PersistenceEntity} with the given
   * <code>id</code> from the persistent store.
   * 
   * @param id is the {@link PersistenceEntity#getId() primary key} of the
   *        requested {@link PersistenceEntity entity}.
   * @return the requested {@link PersistenceEntity entity} or <code>null</code>
   *         if no {@link PersistenceEntity} of the type
   *         {@link #getEntityClass() ENTITY} exists with the given ID.
   * @throws ObjectNotFoundException if the requested {@link PersistenceEntity
   *         entity} could NOT be found.
   */
  ENTITY load(Object id) throws ObjectNotFoundException;

  /**
   * This method saves the given <code>entity</code> in the persistent store. If
   * the <code>entity</code> is {@link PersistenceEntity#isTransient()
   * transient} it is initially created in the persistent store. If the
   * <code>entity</code> is configured to have a generated
   * {@link PersistenceEntity#getId() primary key} a unique ID is generated and
   * assigned. Otherwise, if the <code>entity</code> is already persistent, the
   * <code>entity</code> is updated in the persistent store.
   * 
   * @param entity is the {@link PersistenceEntity} to save.
   */
  void save(ENTITY entity);

}
