/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.value.impl;

import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Date;

import net.sf.mmm.util.date.Iso8601Util;
import net.sf.mmm.util.value.api.ValueConverter;

/**
 * This is an implementation of the {@link ValueConverter} interface that
 * converts an {@link Object} to a {@link String}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class ValueConverterToDate implements ValueConverter<Object, Date> {

  /**
   * The constructor.
   */
  public ValueConverterToDate() {

    super();
  }

  /**
   * This method gets the {@link Iso8601Util} to use.
   * 
   * @return the {@link Iso8601Util} instance.
   */
  protected Iso8601Util getIso8601Util() {

    return Iso8601Util.getInstance();
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
  public Class<Date> getTargetType() {

    return Date.class;
  }

  /**
   * {@inheritDoc}
   */
  public Date convert(Object value, Object valueSource, Class<? extends Date> targetClass,
      Type targetType) {

    if (value == null) {
      return null;
    }
    if (value instanceof Calendar) {
      return ((Calendar) value).getTime();
    }
    if (value instanceof String) {
      return getIso8601Util().parseDate((String) value);
    }
    if (value instanceof Long) {
      return new Date(((Long) value).longValue());
    }
    return null;
  }

}
