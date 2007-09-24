/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.value.impl.type;

import net.sf.mmm.util.StringUtil;
import net.sf.mmm.value.api.type.Duration;
import net.sf.mmm.value.api.type.Time;

/**
 * This is the implementation of a duration value.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class DurationImpl implements Duration {

  /** uid for serialization */
  private static final long serialVersionUID = -2996008315537598480L;

  /** the actual duration */
  private final long duration;

  /** the days */
  private final long days;

  /** the hours (0-23) */
  private final int hours;

  /** the minutes (0-59) */
  private final int minutes;

  /** the seconds (0-59) */
  private final int seconds;

  /** the milliseconds (0-999) */
  private final int milliseconds;

  /**
   * The constructor.
   * 
   * @param durationInMillis is the time difference in milliseconds.
   */
  public DurationImpl(long durationInMillis) {

    super();
    this.duration = durationInMillis;
    this.milliseconds = (int) (this.duration % Time.MILLISECONDS_PER_SECOND);
    long rest = this.duration / Time.MILLISECONDS_PER_SECOND;
    this.seconds = (int) (rest % Time.SECONDS_PER_MINUTE);
    rest = rest / Time.SECONDS_PER_MINUTE;
    this.minutes = (int) (rest % Time.MINUTES_PER_HOUR);
    rest = rest / Time.MINUTES_PER_HOUR;
    this.hours = (int) (rest % Time.HOURS_PER_DAY);
    rest = rest / Time.HOURS_PER_DAY;
    this.days = rest;
  }

  /**
   * The constructor.
   * 
   * @param durationAsString is the duration given as String in the format as
   *        produced by {@link DurationImpl#toString()}.
   */
  public DurationImpl(String durationAsString) {

    // TODO:
    this(0);
  }

  /**
   * {@inheritDoc}
   */
  public long getDays() {

    return this.days;
  }

  /**
   * {@inheritDoc}
   */
  public int getHours() {

    return this.hours;
  }

  /**
   * {@inheritDoc}
   */
  public int getMinutes() {

    return this.minutes;
  }

  /**
   * {@inheritDoc}
   */
  public int getSeconds() {

    return this.seconds;
  }

  /**
   * {@inheritDoc}
   */
  public int getMilliseconds() {

    return this.milliseconds;
  }

  /**
   * {@inheritDoc}
   */
  public long asMilliseconds() {

    return this.duration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuffer result = new StringBuffer();
    if (this.days > 0) {
      result.append(Long.toString(this.days));
      result.append("#");
    }
    result.append(StringUtil.padNumber(this.hours, 2));
    result.append(":");
    result.append(StringUtil.padNumber(this.minutes, 2));
    result.append(":");
    result.append(StringUtil.padNumber(this.seconds, 2));
    if (this.milliseconds == 0) {
      result.append(".");
      result.append(StringUtil.padNumber(this.milliseconds, 4));
    }
    return result.toString();
  }
  
}