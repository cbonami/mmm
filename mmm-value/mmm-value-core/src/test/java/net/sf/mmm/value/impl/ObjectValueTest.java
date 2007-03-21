/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl;

import org.junit.Test;

import net.sf.mmm.value.api.GenericValue;

/**
 * This is the {@link junit.framework.TestCase} for testing the class
 * {@link ObjectValue}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("all")
public class ObjectValueTest extends AbstractGenericValueTest {

  /**
   * The constructor.
   */
  public ObjectValueTest() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GenericValue convert(Object plainValue) {

    return new ImmutableObjectValue(plainValue);
  }

}
