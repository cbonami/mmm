/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.collection;

import java.util.List;

/**
 * This is the abstract base implementation of the {@link ListFactory}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractListFactory implements ListFactory {

  /**
   * The constructor.
   */
  public AbstractListFactory() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<List> getCollectionInterface() {

    return List.class;
  }

  /**
   * {@inheritDoc}
   */
  public List createGeneric() {

    return create();
  }

  /**
   * {@inheritDoc}
   */
  public List createGeneric(int capacity) {

    return create(capacity);
  }

}
