/*
 * Copyright (c) 2008-2012, Stephen Colebourne & Michael Nascimento Santos
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
package java.time.calendrical;

import static java.time.calendrical.ChronoField.ALIGNED_DAY_OF_WEEK_IN_MONTH;
import static java.time.calendrical.ChronoField.ALIGNED_DAY_OF_WEEK_IN_YEAR;
import static java.time.calendrical.ChronoField.ALIGNED_WEEK_OF_MONTH;
import static java.time.calendrical.ChronoField.ALIGNED_WEEK_OF_YEAR;
import static java.time.calendrical.ChronoField.DAY_OF_MONTH;
import static java.time.calendrical.ChronoField.DAY_OF_WEEK;
import static java.time.calendrical.ChronoField.DAY_OF_YEAR;
import static java.time.calendrical.ChronoField.EPOCH_DAY;
import static java.time.calendrical.ChronoField.EPOCH_MONTH;
import static java.time.calendrical.ChronoField.MONTH_OF_YEAR;
import static java.time.calendrical.ChronoField.YEAR;
import static org.testng.Assert.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDate;
import java.time.OffsetTime;
import java.time.ZoneOffset;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test.
 */
public class TestDateTimeBuilderCombinations {

  @DataProvider(name = "combine")
  Object[][] data_combine() {

    return new Object[][] {
        { YEAR, 2012, MONTH_OF_YEAR, 6, DAY_OF_MONTH, 3, null, null, LocalDate.class, LocalDate.of(2012, 6, 3) },
        { EPOCH_MONTH, (2012 - 1970) * 12 + 6 - 1, DAY_OF_MONTH, 3, null, null, null, null, LocalDate.class,
            LocalDate.of(2012, 6, 3) },
        { YEAR, 2012, ALIGNED_WEEK_OF_YEAR, 6, DAY_OF_WEEK, 3, null, null, LocalDate.class, LocalDate.of(2012, 2, 8) },
        { YEAR, 2012, DAY_OF_YEAR, 155, null, null, null, null, LocalDate.class, LocalDate.of(2012, 6, 3) },
        // {ERA, 1, YEAR_OF_ERA, 2012, DAY_OF_YEAR, 155, null, null, LocalDate.class, LocalDate.of(2012, 6,
        // 3)},
        { YEAR, 2012, MONTH_OF_YEAR, 6, null, null, null, null, LocalDate.class, null },
        { EPOCH_DAY, 12, null, null, null, null, null, null, LocalDate.class, LocalDate.of(1970, 1, 13) }, };
  }

  @Test(dataProvider = "combine")
  public void test_derive(DateTimeField field1, Number value1, DateTimeField field2, Number value2,
      DateTimeField field3, Number value3, DateTimeField field4, Number value4, Class<?> query, Object expectedVal) {

    DateTimeBuilder builder = new DateTimeBuilder(field1, value1.longValue());
    if (field2 != null) {
      builder.addFieldValue(field2, value2.longValue());
    }
    if (field3 != null) {
      builder.addFieldValue(field3, value3.longValue());
    }
    if (field4 != null) {
      builder.addFieldValue(field4, value4.longValue());
    }
    builder.resolve();
    assertEquals(builder.extract((Class<?>) query), expectedVal);
  }

  // -----------------------------------------------------------------------
  @DataProvider(name = "normalized")
  Object[][] data_normalized() {

    return new Object[][] { { YEAR, 2127, null, null, null, null, YEAR, 2127 },
        { MONTH_OF_YEAR, 12, null, null, null, null, MONTH_OF_YEAR, 12 },
        { DAY_OF_YEAR, 127, null, null, null, null, DAY_OF_YEAR, 127 },
        { DAY_OF_MONTH, 23, null, null, null, null, DAY_OF_MONTH, 23 },
        { DAY_OF_WEEK, 127, null, null, null, null, DAY_OF_WEEK, 127L },
        { ALIGNED_WEEK_OF_YEAR, 23, null, null, null, null, ALIGNED_WEEK_OF_YEAR, 23 },
        { ALIGNED_DAY_OF_WEEK_IN_YEAR, 4, null, null, null, null, ALIGNED_DAY_OF_WEEK_IN_YEAR, 4L },
        { ALIGNED_WEEK_OF_MONTH, 4, null, null, null, null, ALIGNED_WEEK_OF_MONTH, 4 },
        { ALIGNED_DAY_OF_WEEK_IN_MONTH, 3, null, null, null, null, ALIGNED_DAY_OF_WEEK_IN_MONTH, 3 },
        { EPOCH_MONTH, 15, null, null, null, null, EPOCH_MONTH, null },
        { EPOCH_MONTH, 15, null, null, null, null, YEAR, 1971 },
        { EPOCH_MONTH, 15, null, null, null, null, MONTH_OF_YEAR, 4 }, };
  }

  @Test(dataProvider = "normalized")
  public void test_normalized(DateTimeField field1, Number value1, DateTimeField field2, Number value2,
      DateTimeField field3, Number value3, DateTimeField query, Number expectedVal) {

    DateTimeBuilder builder = new DateTimeBuilder(field1, value1.longValue());
    if (field2 != null) {
      builder.addFieldValue(field2, value2.longValue());
    }
    if (field3 != null) {
      builder.addFieldValue(field3, value3.longValue());
    }
    builder.resolve();
    if (expectedVal != null) {
      assertEquals(builder.getLong(query), expectedVal.longValue());
    } else {
      assertEquals(builder.containsFieldValue(query), false);
    }
  }

  // -----------------------------------------------------------------------
  public void test_split() {

    DateTimeBuilder builder = new DateTimeBuilder();
    builder.addCalendrical(LocalDateTime.of(2012, 6, 30, 12, 30));
    builder.addCalendrical(ZoneOffset.ofHours(2));
    builder.resolve();
    assertEquals(builder.build(LocalDate.class), LocalDate.of(2012, 6, 30));
    assertEquals(builder.build(LocalTime.class), LocalTime.of(12, 30));
    assertEquals(builder.build(ZoneOffset.class), ZoneOffset.ofHours(2));

    assertEquals(builder.build(LocalDateTime.class), LocalDateTime.of(2012, 6, 30, 12, 30));
    assertEquals(builder.build(OffsetDate.class), OffsetDate.of(2012, 6, 30, ZoneOffset.ofHours(2)));
    assertEquals(builder.build(OffsetTime.class), OffsetTime.of(12, 30, ZoneOffset.ofHours(2)));
    // assertEquals(builder.build(OffsetDateTime.class), OffsetDateTime.of(2012, 6, 30, 12, 30,
    // ZoneOffset.ofHours(2)));
  }

}
