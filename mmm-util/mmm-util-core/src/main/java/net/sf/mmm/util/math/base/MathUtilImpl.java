/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.math.base;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.util.math.api.MathUtil;
import net.sf.mmm.util.math.api.NumberType;

/**
 * This class is a collection of utility functions for dealing with numbers.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named(MathUtil.CDI_NAME)
public class MathUtilImpl extends MathUtilLimitedImpl implements MathUtil {

  /** @see #getInstance() */
  private static MathUtil instance;

  /**
   * The constructor.
   */
  public MathUtilImpl() {

    super();
  }

  /**
   * This method gets the singleton instance of {@link MathUtil}.<br>
   * This design is the best compromise between easy access (via this indirection you have direct, static
   * access to all offered functionality) and IoC-style design which allows extension and customization.<br>
   * For IoC usage, simply ignore all static {@link #getInstance()} methods and construct new instances via
   * the container-framework of your choice (like plexus, pico, springframework, etc.). To wire up the
   * dependent components everything is properly annotated using common-annotations (JSR-250). If your
   * container does NOT support this, you should consider using a better one.
   * 
   * @return the singleton instance.
   */
  public static MathUtil getInstance() {

    if (instance == null) {
      synchronized (MathUtilImpl.class) {
        if (instance == null) {
          MathUtilImpl impl = new MathUtilImpl();
          impl.initialize();
          instance = impl;
        }
      }
    }
    return instance;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public NumberType<? extends Number> getNumberType(Class<?> numericType) {

    if ((AtomicInteger.class.isAssignableFrom(numericType))) {
      return ATOMIC_INTEGER;
    } else if ((AtomicLong.class.isAssignableFrom(numericType))) {
      return ATOMIC_LONG;
    } else {
      return super.getNumberType(numericType);
    }
  }

  /** The {@link NumberTypeImpl} for {@link AtomicLong}. */
  public static final NumberTypeImpl<AtomicLong> ATOMIC_LONG = new NumberTypeImpl<AtomicLong>(4) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<AtomicLong> getNumberClass() {

      return AtomicLong.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AtomicLong convert(Number number) {

      return new AtomicLong(number.longValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AtomicLong parse(String number) throws NumberFormatException {

      return new AtomicLong(Long.parseLong(number));
    }

  };

  /** The {@link NumberTypeImpl} for {@link AtomicInteger}. */
  public static final NumberTypeImpl<AtomicInteger> ATOMIC_INTEGER = new NumberTypeImpl<AtomicInteger>(3) {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<AtomicInteger> getNumberClass() {

      return AtomicInteger.class;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDecimal() {

      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AtomicInteger convert(Number number) {

      return new AtomicInteger(number.intValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AtomicInteger parse(String number) throws NumberFormatException {

      return new AtomicInteger(Integer.parseInt(number));
    }

  };

}
