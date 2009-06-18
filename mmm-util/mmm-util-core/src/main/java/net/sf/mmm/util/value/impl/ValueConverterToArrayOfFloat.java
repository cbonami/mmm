/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to <code>float[]</code>.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.3
 */
public class ValueConverterToArrayOfFloat extends AbstractConverterToArray<float[]> {

  /**
   * The constructor.
   */
  public ValueConverterToArrayOfFloat() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  public Class<float[]> getTargetType() {

    return float[].class;
  }

}
