/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import net.sf.mmm.util.lang.api.StringUtil;
import net.sf.mmm.util.lang.base.StringUtilImpl;
import net.sf.mmm.util.value.base.AbstractSimpleValueConverter;

/**
 * This is an implementation of the
 * {@link net.sf.mmm.util.value.api.ValueConverter} interface that converts an
 * {@link Object} to a {@link Number}. It supports objects given as
 * {@link CharSequence} (e.g. {@link String}) or {@link Number}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
@SuppressWarnings("unchecked")
public class ValueConverterToEnum extends AbstractSimpleValueConverter<Object, Enum> {

  /** @see #getStringUtil() */
  private final StringUtil stringUtil;

  /**
   * The constructor.
   */
  public ValueConverterToEnum() {

    this(StringUtilImpl.getInstance());
  }

  /**
   * The constructor.
   * 
   * @param stringUtil is the {@link StringUtilImpl} to use.
   */
  public ValueConverterToEnum(StringUtil stringUtil) {

    super();
    this.stringUtil = stringUtil;
  }

  /**
   * This method gets the {@link StringUtilImpl} to use.
   * 
   * @return the {@link StringUtilImpl} instance.
   */
  protected StringUtil getStringUtil() {

    return this.stringUtil;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Object> getSourceType() {

    return Object.class;
  }

  /**
   * {@inheritDoc}
   */
  public Class<Enum> getTargetType() {

    return Enum.class;
  }

  /**
   * {@inheritDoc}
   */
  public Enum convert(Object value, Object valueSource, Class<? extends Enum> targetClass) {

    if (value == null) {
      return null;
    }
    if (value instanceof CharSequence) {
      String name = this.stringUtil.fromCamlCase(value.toString(), '_').toUpperCase();
      return Enum.valueOf(targetClass, name);
    }
    return null;
  }

}
