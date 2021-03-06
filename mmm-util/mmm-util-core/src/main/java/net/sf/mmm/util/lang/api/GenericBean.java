/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This class is a simple java bean that has one generic property named {@link #getValue() value}. It can be
 * used for various purposes - e.g. to receive a value by passing it as argument to a method that already
 * returns something else.
 * 
 * @param <TYPE> is the generic type of the {@link #getValue() value}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public class GenericBean<TYPE> implements AttributeWriteValue<TYPE> {

  /** @see #getValue() */
  private TYPE value;

  /**
   * The constructor.
   * 
   */
  public GenericBean() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param value is the initial {@link #getValue() value}.
   */
  public GenericBean(TYPE value) {

    super();
    this.value = value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public TYPE getValue() {

    return this.value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(TYPE value) {

    this.value = value;
  }

}
