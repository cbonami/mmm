/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.reflect.base;

import net.sf.mmm.util.reflect.api.ReflectionUtilLimited;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.0.0
 */
public class ReflectionUtilLimitedImpl implements ReflectionUtilLimited {

  /** @see #getInstance() */
  private static final ReflectionUtilLimited INSTANCE = new ReflectionUtilLimitedImpl();

  /**
   * The constructor.
   */
  public ReflectionUtilLimitedImpl() {

    super();
  }

  /**
   * @return the instance
   */
  public static ReflectionUtilLimited getInstance() {

    return INSTANCE;
  }

  /**
   * {@inheritDoc}
   */
  public Class<?> getNonPrimitiveType(Class<?> type) {

    Class<?> result = type;
    if (type.isPrimitive()) {
      if (int.class == type) {
        return Integer.class;
      } else if (long.class == type) {
        return Long.class;
      } else if (double.class == type) {
        return Double.class;
      } else if (boolean.class == type) {
        return Boolean.class;
      } else if (float.class == type) {
        return Float.class;
      } else if (char.class == type) {
        return Character.class;
      } else if (byte.class == type) {
        return Byte.class;
      } else if (short.class == type) {
        return Short.class;
      } else if (void.class == type) {
        return Void.class;
      } else {
        throw new IllegalStateException("Class-loader isolation trap!");
      }
    }
    return result;
  }

}
