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
package javax.time;

import static java.time.calendrical.ChronoUnit.DAYS;
import static java.time.calendrical.ChronoUnit.FOREVER;
import static java.time.calendrical.ChronoUnit.SECONDS;

import java.time.DateTimeException;
import java.time.calendrical.DateTime;
import java.time.calendrical.DateTime.MinusAdjuster;
import java.time.calendrical.DateTime.PlusAdjuster;
import java.time.calendrical.PeriodUnit;
import java.util.Objects;

/**
 * Mock period of time measured using a single unit, such as {@code 3 Days}.
 */
public final class MockSimplePeriod implements PlusAdjuster, MinusAdjuster, Comparable<MockSimplePeriod> {

  /**
   * A constant for a period of zero, measured in days.
   */
  public static final MockSimplePeriod ZERO_DAYS = new MockSimplePeriod(0, DAYS);

  /**
   * A constant for a period of zero, measured in seconds.
   */
  public static final MockSimplePeriod ZERO_SECONDS = new MockSimplePeriod(0, SECONDS);

  /**
   * The amount of the period.
   */
  private final long amount;

  /**
   * The unit the period is measured in.
   */
  private final PeriodUnit unit;

  /**
   * Obtains a {@code MockSimplePeriod} from an amount and unit.
   * <p>
   * The parameters represent the two parts of a phrase like '6 Days'.
   * 
   * @param amount the amount of the period, measured in terms of the unit, positive or negative
   * @param unit the unit that the period is measured in, must not be the 'Forever' unit, not null
   * @return the {@code MockSimplePeriod} instance, not null
   * @throws DateTimeException if the period unit is {@link java.time.calendrical.ChronoUnit#FOREVER}.
   */
  public static MockSimplePeriod of(long amount, PeriodUnit unit) {

    return new MockSimplePeriod(amount, unit);
  }

  private MockSimplePeriod(long amount, PeriodUnit unit) {

    Objects.requireNonNull(unit, "unit");
    if (unit == FOREVER) {
      throw new DateTimeException("Cannot create a period of the Forever unit");
    }
    this.amount = amount;
    this.unit = unit;
  }

  // -----------------------------------------------------------------------
  public long getAmount() {

    return this.amount;
  }

  public PeriodUnit getUnit() {

    return this.unit;
  }

  // -------------------------------------------------------------------------
  @Override
  public DateTime doPlusAdjustment(DateTime dateTime) {

    return dateTime.plus(this.amount, this.unit);
  }

  @Override
  public DateTime doMinusAdjustment(DateTime dateTime) {

    return dateTime.minus(this.amount, this.unit);
  }

  // -----------------------------------------------------------------------
  @Override
  public int compareTo(MockSimplePeriod otherPeriod) {

    if (this.unit.equals(otherPeriod.getUnit()) == false) {
      throw new IllegalArgumentException("Units cannot be compared: " + this.unit + " and " + otherPeriod.getUnit());
    }
    return Long.compare(this.amount, otherPeriod.amount);
  }

  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (obj instanceof MockSimplePeriod) {
      MockSimplePeriod other = (MockSimplePeriod) obj;
      return this.amount == other.amount && this.unit.equals(other.unit);
    }
    return false;
  }

  @Override
  public int hashCode() {

    return this.unit.hashCode() ^ (int) (this.amount ^ (this.amount >>> 32));
  }

  @Override
  public String toString() {

    return this.amount + " " + this.unit.getName();
  }

}
