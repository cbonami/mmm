/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Provides the API for a persistence layer.
 * <a name="documentation"/><h2>Persistence API</h2> 
 * This package contains the API for a persistence layer.<br/>
 * ATTENTION: This is not yet another O/R mapper or java persistence API (JPA) 
 * wrapper! <br/>
 * The purpose of this API and its implementations is to help you writing a 
 * structured and well designed persistence layer. The JPA is a perfect API for 
 * the O/R mapping of your entities to a database. However, for any reasonable
 * business application you need to write a custom persistence layer on top of 
 * the JPA with your entities, queries, data access objects (DAOs), etc.<br/>
 * To support you with the design of your persistence layer this API and its 
 * basic implementations will help you. If you do not like dependencies on this 
 * code feel free to copy the ideas of this design into your own code.<br/>
 * <br/>
 * A major principle of this API is to establish a uniform structure as well as
 * generic access for your entities and DAOs. All your entities shall therefore
 * implement the interface {@link net.sf.mmm.persistence.api.PersistenceEntity}
 * and all your DAOs the interface 
 * {@link net.sf.mmm.persistence.api.PersistenceEntityManager}.
 * The overall entry point is {@link net.sf.mmm.persistence.api.PersistenceManager}.<br/>
 * For revisioning/auditing support there are also <code>Revisioned*</code>
 * extensions of the interfaces like {@link net.sf.mmm.persistence.api.RevisionedPersistenceManager}.  
 */
package net.sf.mmm.persistence.api;

