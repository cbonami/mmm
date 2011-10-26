/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.jpa;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import net.sf.mmm.persistence.api.RevisionedPersistenceEntity;

/**
 * This is the abstract base-implementation of a
 * {@link net.sf.mmm.persistence.api.RevisionedPersistenceEntity}.<br>
 * <b>ATTENTION:</b><br>
 * JPA does not support <em>auditing</em> (tracking the revision history) of
 * entities. The recommended implementation is Hibernate-Envers (
 * <code>org.hibernate.envers</code>). In this case your entities need to be
 * annotated with <code>org.hibernate.envers.Audited</code> if you want auditing
 * support.
 * 
 * @param <ID> is the type of the {@link #getId() primary key}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@MappedSuperclass
public abstract class JpaRevisionedPersistenceEntity<ID> extends JpaPersistenceEntity<ID> implements
    RevisionedPersistenceEntity<ID> {

  /** @see #getRevision() */
  private Number revision;

  /**
   * The constructor.
   */
  public JpaRevisionedPersistenceEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Transient
  public Number getRevision() {

    return this.revision;
  }

  /**
   * @param revision is the revision to set
   */
  void setRevision(Number revision) {

    this.revision = revision;
  }

}
