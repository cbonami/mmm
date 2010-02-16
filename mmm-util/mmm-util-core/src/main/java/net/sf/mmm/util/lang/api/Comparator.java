/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.lang.api;

import java.util.Calendar;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * A {@link Comparator} represents a function that compares two given values.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.1.2
 */
public enum Comparator {

  /** {@link Comparator} to check if some value is greater than another. */
  GREATER_THAN(">", false, Boolean.FALSE) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 > arg2;
    }
  },

  /** {@link Comparator} to check if some value is greater or equal to another. */
  GREATER_OR_EQUAL(">=", true, Boolean.FALSE) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 >= arg2;
    }
  },

  /** {@link Comparator} to check if some value is less than another. */
  LESS_THAN("<", false, Boolean.TRUE) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 < arg2;
    }
  },

  /** {@link Comparator} to check if some value is less or equal than another. */
  LESS_OR_EQUAL("<=", true, Boolean.TRUE) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 <= arg2;
    }
  },

  /**
   * {@link Comparator} to check if objects are {@link Object#equals(Object)
   * equal}.
   */
  EQUAL("==", true, null) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 == arg2;
    }
  },

  /**
   * {@link Comparator} to check if objects are NOT
   * {@link Object#equals(Object) equal}.
   */
  NOT_EQUAL("!=", false, null) {

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean eval(double arg1, double arg2) {

      return arg1 != arg2;
    }
  };

  /** @see #getSymbol() */
  private final String symbol;

  /** @see #eval(Object, Object) */
  private final boolean evalTrueIfEquals;

  /** @see #evalComparable(Comparable, Comparable) */
  private final Boolean less;

  /**
   * The constructor.
   * 
   * @param expression is the {@link #getSymbol() symbol}.
   * @param evalTrueIfEquals - <code>true</code> if {@link Comparator}
   *        {@link #eval(Object, Object) evaluates} to <code>true</code> if
   *        arguments are equal, <code>false</code> otherwise.
   * @param less - {@link Boolean#TRUE} if {@link Comparator}
   *        {@link #eval(Object, Object) evaluates} to <code>true</code> if
   *        first argument is less than second, {@link Boolean#FALSE} on
   *        greater, <code>null</code> otherwise.
   */
  private Comparator(String expression, boolean evalTrueIfEquals, Boolean less) {

    this.symbol = expression;
    this.evalTrueIfEquals = evalTrueIfEquals;
    this.less = less;
  }

  /**
   * This method gets the symbol of the {@link Comparator}. E.g. "==", ">",
   * ">=", etc.
   * 
   * @return the comparator symbol.
   */
  public String getSymbol() {

    return this.symbol;
  }

  /**
   * This method evaluates this {@link Comparator} for the given arguments.
   * 
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link Comparator} applied to the given
   *         arguments.
   */
  public abstract boolean eval(double arg1, double arg2);

  /**
   * This method converts the given value to a more common type. E.g. instances
   * of {@link Calendar} or {@link XMLGregorianCalendar} will be converted to
   * {@link java.util.Date}.
   * 
   * @param value is the value to convert.
   * @param otherType the type of the value to compare that differs from the
   *        type
   * @return a simpler representation of <code>value</code> or the same
   *         <code>value</code> if on simpler type is known.
   */
  private Object convert(Object value, Class<?> otherType) {

    if (value instanceof Calendar) {
      return ((Calendar) value).getTime();
    }
    if (value instanceof XMLGregorianCalendar) {
      return ((XMLGregorianCalendar) value).toGregorianCalendar().getTime();
    }
    // if (value instanceof String) {
    // if (Number.class.isAssignableFrom(otherType)) {
    // return Double.valueOf((String) value);
    // } else if (Date.class.isAssignableFrom(otherType)) {
    //        
    // }
    // }
    return value;
  }

  /**
   * This method handles {@link #eval(Object, Object)} for two
   * {@link Comparable}s.
   * 
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link Comparator} applied to the given
   *         arguments.
   */
  @SuppressWarnings("unchecked")
  private boolean evalComparable(Comparable arg1, Comparable arg2) {

    Class<?> type1 = arg1.getClass();
    Class<?> type2 = arg2.getClass();
    int delta;
    if (type1.equals(type2) || type1.isAssignableFrom(type2)) {
      delta = arg1.compareTo(arg2);
    } else if (type2.isAssignableFrom(type1)) {
      delta = -arg2.compareTo(arg1);
    } else {
      // incompatible comparables
      return (arg1.equals(arg2) == this.evalTrueIfEquals);
    }
    if (delta == 0) {
      return this.evalTrueIfEquals;
    } else {
      if (this.less == null) {
        return !this.evalTrueIfEquals;
      } else {
        if (delta < 0) {
          // arg1 < arg2
          return (this.less.booleanValue());
        } else {
          // arg1 > arg2
          return (!this.less.booleanValue());
        }
      }
    }
  }

  /**
   * This method evaluates this {@link Comparator} for the given arguments.
   * 
   * @param arg1 is the first argument.
   * @param arg2 is the second argument.
   * @return the result of the {@link Comparator} applied to the given
   *         arguments.
   */
  @SuppressWarnings("unchecked")
  public boolean eval(Object arg1, Object arg2) {

    if (arg1 == arg2) {
      return this.evalTrueIfEquals;
    } else if ((arg1 == null) || (arg2 == null)) {
      return !this.evalTrueIfEquals;
    } else {
      Object v1 = arg1;
      Object v2 = arg2;
      Class<?> type1 = v1.getClass();
      Class<?> type2 = v2.getClass();
      if (!type1.equals(type2)) {
        v1 = convert(v1, type2);
        v2 = convert(v2, type1);
      }
      if ((v1 instanceof Number) && (v2 instanceof Number)) {
        return eval(((Number) v1).doubleValue(), ((Number) v2).doubleValue());
      } else if ((v1 instanceof Comparable) && (v2 instanceof Comparable)) {
        return evalComparable((Comparable) v1, (Comparable) v2);
      } else if (v1.equals(v2)) {
        return this.evalTrueIfEquals;
      } else {
        return !this.evalTrueIfEquals;
      }
    }

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    return this.symbol;
  }

  /**
   * This method gets the {@link Comparator} for the given <code>symbol</code>.
   * 
   * @param symbol is the {@link #getSymbol() symbol} of the requested
   *        {@link Comparator}.
   * @return the requested {@link Comparator} or <code>null</code> if no such
   *         {@link Comparator} exists.
   */
  public static Comparator fromSymbol(String symbol) {

    for (Comparator comparator : values()) {
      if (comparator.symbol.equals(symbol)) {
        return comparator;
      }
    }
    return null;
  }
}