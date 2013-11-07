/*
 * Copyright (c) 2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package java.time.chrono.global;

import static java.time.calendrical.ChronoField.DAY_OF_MONTH;
import static java.time.calendrical.ChronoField.MONTH_OF_YEAR;
import static java.time.calendrical.ChronoField.YEAR;
import static java.time.chrono.global.ThaiBuddhistChrono.YEARS_DIFFERENCE;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.calendrical.ChronoField;
import java.time.calendrical.DateTimeField;
import java.time.calendrical.DateTimeValueRange;
import java.time.chrono.ChronoLocalDate;
import java.util.Objects;

/**
 * A date in the Thai Buddhist calendar system.
 * <p>
 * This implements {@code ChronoLocalDate} for the {@link ThaiBuddhistChrono Thai Buddhist calendar}.
 * 
 * <h4>Implementation notes</h4> This class is immutable and thread-safe.
 */
final class ThaiBuddhistDate extends ChronoDateImpl<ThaiBuddhistChrono> implements Serializable {

  // this class is package-scoped so that future conversion to public
  // would not change serialization

  /**
   * Serialization version.
   */
  private static final long serialVersionUID = -8722293800195731463L;

  /**
   * The underlying date.
   */
  private final LocalDate isoDate;

  /**
   * Creates an instance from an ISO date.
   * 
   * @param isoDate the standard local date, validated not null
   */
  ThaiBuddhistDate(LocalDate date) {

    Objects.requireNonNull(date, "date");
    this.isoDate = date;
  }

  // -----------------------------------------------------------------------
  @Override
  public ThaiBuddhistChrono getChrono() {

    return ThaiBuddhistChrono.INSTANCE;
  }

  @Override
  public int lengthOfMonth() {

    return this.isoDate.lengthOfMonth();
  }

  @Override
  public DateTimeValueRange range(DateTimeField field) {

    if (field instanceof ChronoField) {
      if (isSupported(field)) {
        ChronoField f = (ChronoField) field;
        switch (f) {
          case DAY_OF_MONTH:
          case DAY_OF_YEAR:
          case ALIGNED_WEEK_OF_MONTH:
            return this.isoDate.range(field);
          case YEAR_OF_ERA: {
            DateTimeValueRange range = YEAR.range();
            long max = (getProlepticYear() <= 0 ? -(range.getMinimum() + YEARS_DIFFERENCE) + 1 : range.getMaximum()
                + YEARS_DIFFERENCE);
            return DateTimeValueRange.of(1, max);
          }
        }
        return getChrono().range(f);
      }
      throw new DateTimeException("Unsupported field: " + field.getName());
    }
    return field.doRange(this);
  }

  @Override
  public long getLong(DateTimeField field) {

    if (field instanceof ChronoField) {
      switch ((ChronoField) field) {
        case YEAR_OF_ERA: {
          int prolepticYear = getProlepticYear();
          return (prolepticYear >= 1 ? prolepticYear : 1 - prolepticYear);
        }
        case YEAR:
          return getProlepticYear();
        case ERA:
          return (getProlepticYear() >= 1 ? 1 : 0);
      }
      return this.isoDate.getLong(field);
    }
    return field.doGet(this);
  }

  private int getProlepticYear() {

    return this.isoDate.getYear() + YEARS_DIFFERENCE;
  }

  // -----------------------------------------------------------------------
  @Override
  public ThaiBuddhistDate with(DateTimeField field, long newValue) {

    if (field instanceof ChronoField) {
      ChronoField f = (ChronoField) field;
      if (getLong(f) == newValue) {
        return this;
      }
      switch (f) {
        case YEAR_OF_ERA:
        case YEAR:
        case ERA: {
          f.checkValidValue(newValue);
          int nvalue = (int) newValue;
          switch (f) {
            case YEAR_OF_ERA:
              return with(this.isoDate.withYear((getProlepticYear() >= 1 ? nvalue : 1 - nvalue) - YEARS_DIFFERENCE));
            case YEAR:
              return with(this.isoDate.withYear(nvalue - YEARS_DIFFERENCE));
            case ERA:
              return with(this.isoDate.withYear((1 - getProlepticYear()) - YEARS_DIFFERENCE));
          }
        }
      }
      return with(this.isoDate.with(field, newValue));
    }
    return field.doWith(this, newValue);
  }

  // -----------------------------------------------------------------------
  @Override
  ThaiBuddhistDate plusYears(long years) {

    return with(this.isoDate.plusYears(years));
  }

  @Override
  ThaiBuddhistDate plusMonths(long months) {

    return with(this.isoDate.plusMonths(months));
  }

  @Override
  ThaiBuddhistDate plusDays(long days) {

    return with(this.isoDate.plusDays(days));
  }

  private ThaiBuddhistDate with(LocalDate newDate) {

    return (newDate.equals(this.isoDate) ? this : new ThaiBuddhistDate(newDate));
  }

  @Override
  // override for performance
  public long toEpochDay() {

    return this.isoDate.toEpochDay();
  }

  // -------------------------------------------------------------------------
  @Override
  // override for performance
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj instanceof ThaiBuddhistDate) {
      ThaiBuddhistDate otherDate = (ThaiBuddhistDate) obj;
      return this.isoDate.equals(otherDate.isoDate);
    }
    return false;
  }

  @Override
  // override for performance
  public int hashCode() {

    return getChrono().getId().hashCode() ^ this.isoDate.hashCode();
  }

  // -----------------------------------------------------------------------
  private Object writeReplace() {

    return new Ser(Ser.THAIBUDDHIST_DATE_TYPE, this);
  }

  void writeExternal(DataOutput out) throws IOException {

    // MinguoChrono is implicit in the THAIBUDDHIST_DATE_TYPE
    out.writeInt(get(YEAR));
    out.writeByte(get(MONTH_OF_YEAR));
    out.writeByte(get(DAY_OF_MONTH));
  }

  static ChronoLocalDate<ThaiBuddhistChrono> readExternal(DataInput in) throws IOException {

    int year = in.readInt();
    int month = in.readByte();
    int dayOfMonth = in.readByte();
    return ThaiBuddhistChrono.INSTANCE.date(year, month, dayOfMonth);
  }

}
