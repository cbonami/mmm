/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.persistence.impl.hibernate;

import org.junit.Test;

import net.sf.mmm.framework.base.SpringContainerPool;
import net.sf.mmm.persistence.api.RevisionedPersistenceManager;
import net.sf.mmm.transaction.api.TransactionExecutor;

/**
 * This is the test-case for the persistence.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class PersistenceTest {

  /** The classpath to the spring application context XML configuration. */
  private static final String SPRING_XML = "net/sf/mmm/persistence/impl/hibernate/beans-test-persistence-hibernate.xml";

  protected RevisionedPersistenceManager getPersistenceManager() {

    return SpringContainerPool.getContainer(SPRING_XML).getComponent(
        RevisionedPersistenceManager.class);
  }

  @Test
  public void testPersistence() throws Exception {

    TransactionExecutor transactionExecutor = SpringContainerPool.getContainer(SPRING_XML)
        .getComponent(TransactionExecutor.class);

  }

}
