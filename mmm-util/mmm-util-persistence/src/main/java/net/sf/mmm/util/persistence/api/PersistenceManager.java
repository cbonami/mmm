/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.persistence.api;

import net.sf.mmm.util.nls.api.ObjectNotFoundException;

/**
 * This is the interface for a manager of the persistence layer. It is the
 * entrance point to the persistence component. In a common environment there is
 * typically only one single instance of this interface active.<br>
 * The {@link PersistenceManager} acts as delegation to the
 * {@link PersistenceEntityManager}
 * {@link PersistenceEntityManager#getEntityClass() responsible} for the
 * according {@link PersistenceEntity} in the invoked methods. This guarantees
 * that your individual custom logic is also invoked in case of generic access.
 * 
 * @see javax.persistence.EntityManager
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface PersistenceManager {

  /**
   * This method gets the individual {@link PersistenceEntityManager}
   * {@link PersistenceEntityManager#getEntityClass() responsible} for the given
   * <code>entityClass</code>.
   * 
   * @param <ENTITY> is the generic entity-type.
   * @param entityClass is the type of the {@link PersistenceEntity} for which
   *        the according {@link PersistenceEntityManager} is requested.
   * @return the {@link PersistenceEntityManager} responsible for the given
   *         <code>entityClass</code>.
   * @throws ObjectNotFoundException if the requested
   *         {@link PersistenceEntityManager manager} could NOT be found.
   */
  <ENTITY extends PersistenceEntity> PersistenceEntityManager<ENTITY> getManager(
      Class<ENTITY> entityClass) throws ObjectNotFoundException;

  /**
   * This method loads the {@link PersistenceEntity} with the given
   * <code>entityClass</code> and <code>id</code> from the persistent store.
   * 
   * @see PersistenceEntityManager#load(Object)
   * 
   * @param <ENTITY> is the generic type of the <code>entityClass</code>.
   * @param entityClass is the class reflecting the type of the requested
   *        entity.
   * @param id is the {@link PersistenceEntity#getId() primary key} of the
   *        requested entity.
   * @return the requested entity.
   * @throws ObjectNotFoundException if the requested {@link PersistenceEntity
   *         entity} could NOT be found.
   */
  <ENTITY extends PersistenceEntity> ENTITY load(Class<ENTITY> entityClass, Object id)
      throws ObjectNotFoundException;

  /**
   * This method saves the given <code>entity</code>.<br>
   * 
   * @see PersistenceEntityManager#save(PersistenceEntity)
   * 
   * @param entity is the entity to save.
   */
  void save(PersistenceEntity entity);

  /**
   * This method gets the {@link Class} reflecting the given
   * <code>{@link PersistenceEntity entity}</code>. Unlike
   * <code>entity.{@link #getClass()}</code> this method will always return the
   * real {@link PersistenceEntityManager#getEntityClass() class defining the
   * entity}.<br>
   * E.g. if the underlying implementation may create a dynamic proxy that
   * extends the entity-class and <code>entity.{@link #getClass()}</code> will return the {@link Class} of
   * the proxy instead. In such case this method will return the real
   * entity-class adapted by the proxy.
   * 
   * @param entity is the {@link PersistenceEntity} for which the class is
   *        requested.
   * @return the {@link Class} reflecting the given <code>entity</code>.
   */
  Class<? extends PersistenceEntity> getEntityClass(PersistenceEntity entity);

}
