/*
 * Copyright (c) 2009-2012, Stephen Colebourne & Michael Nascimento Santos
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
package javax.time.format;

import static java.time.calendrical.ChronoField.DAY_OF_MONTH;
import static java.time.calendrical.ChronoField.DAY_OF_WEEK;
import static java.time.calendrical.ChronoField.MONTH_OF_YEAR;
import static org.testng.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.calendrical.DateTimeField;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test text printing.
 */
@Test
public class TCKDateTimeTextPrinting {

  private DateTimeFormatterBuilder builder;

  @BeforeMethod(groups = { "tck" })
  public void setUp() {

    this.builder = new DateTimeFormatterBuilder();
  }

  // -----------------------------------------------------------------------
  @DataProvider(name = "printText")
  Object[][] data_text() {

    return new Object[][] { { DAY_OF_WEEK, TextStyle.FULL, 1, "Monday" },
        { DAY_OF_WEEK, TextStyle.FULL, 2, "Tuesday" }, { DAY_OF_WEEK, TextStyle.FULL, 3, "Wednesday" },
        { DAY_OF_WEEK, TextStyle.FULL, 4, "Thursday" }, { DAY_OF_WEEK, TextStyle.FULL, 5, "Friday" },
        { DAY_OF_WEEK, TextStyle.FULL, 6, "Saturday" }, { DAY_OF_WEEK, TextStyle.FULL, 7, "Sunday" },

        { DAY_OF_WEEK, TextStyle.SHORT, 1, "Mon" }, { DAY_OF_WEEK, TextStyle.SHORT, 2, "Tue" },
        { DAY_OF_WEEK, TextStyle.SHORT, 3, "Wed" }, { DAY_OF_WEEK, TextStyle.SHORT, 4, "Thu" },
        { DAY_OF_WEEK, TextStyle.SHORT, 5, "Fri" }, { DAY_OF_WEEK, TextStyle.SHORT, 6, "Sat" },
        { DAY_OF_WEEK, TextStyle.SHORT, 7, "Sun" },

        { DAY_OF_MONTH, TextStyle.FULL, 1, "1" }, { DAY_OF_MONTH, TextStyle.FULL, 2, "2" },
        { DAY_OF_MONTH, TextStyle.FULL, 3, "3" }, { DAY_OF_MONTH, TextStyle.FULL, 28, "28" },
        { DAY_OF_MONTH, TextStyle.FULL, 29, "29" }, { DAY_OF_MONTH, TextStyle.FULL, 30, "30" },
        { DAY_OF_MONTH, TextStyle.FULL, 31, "31" },

        { DAY_OF_MONTH, TextStyle.SHORT, 1, "1" }, { DAY_OF_MONTH, TextStyle.SHORT, 2, "2" },
        { DAY_OF_MONTH, TextStyle.SHORT, 3, "3" }, { DAY_OF_MONTH, TextStyle.SHORT, 28, "28" },
        { DAY_OF_MONTH, TextStyle.SHORT, 29, "29" }, { DAY_OF_MONTH, TextStyle.SHORT, 30, "30" },
        { DAY_OF_MONTH, TextStyle.SHORT, 31, "31" },

        { MONTH_OF_YEAR, TextStyle.FULL, 1, "January" }, { MONTH_OF_YEAR, TextStyle.FULL, 12, "December" },

        { MONTH_OF_YEAR, TextStyle.SHORT, 1, "Jan" }, { MONTH_OF_YEAR, TextStyle.SHORT, 12, "Dec" }, };
  }

  @Test(dataProvider = "printText", groups = { "tck" })
  public void test_appendText2arg_print(DateTimeField field, TextStyle style, int value, String expected)
      throws Exception {

    DateTimeFormatter f = this.builder.appendText(field, style).toFormatter(Locale.ENGLISH);
    LocalDateTime dt = LocalDateTime.of(2010, 1, 1, 0, 0);
    dt = dt.with(field, value);
    String text = f.print(dt);
    assertEquals(text, expected);
  }

  @Test(dataProvider = "printText", groups = { "tck" })
  public void test_appendText1arg_print(DateTimeField field, TextStyle style, int value, String expected)
      throws Exception {

    if (style == TextStyle.FULL) {
      DateTimeFormatter f = this.builder.appendText(field).toFormatter(Locale.ENGLISH);
      LocalDateTime dt = LocalDateTime.of(2010, 1, 1, 0, 0);
      dt = dt.with(field, value);
      String text = f.print(dt);
      assertEquals(text, expected);
    }
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_print_appendText2arg_french_long() throws Exception {

    DateTimeFormatter f = this.builder.appendText(MONTH_OF_YEAR, TextStyle.FULL).toFormatter(Locale.FRENCH);
    LocalDateTime dt = LocalDateTime.of(2010, 1, 1, 0, 0);
    String text = f.print(dt);
    assertEquals(text, "janvier");
  }

  @Test(groups = { "tck" })
  public void test_print_appendText2arg_french_short() throws Exception {

    DateTimeFormatter f = this.builder.appendText(MONTH_OF_YEAR, TextStyle.SHORT).toFormatter(Locale.FRENCH);
    LocalDateTime dt = LocalDateTime.of(2010, 1, 1, 0, 0);
    String text = f.print(dt);
    assertEquals(text, "janv.");
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_appendTextMap() throws Exception {

    Map<Long, String> map = new HashMap<Long, String>();
    map.put(1L, "JNY");
    map.put(2L, "FBY");
    map.put(3L, "MCH");
    map.put(4L, "APL");
    map.put(5L, "MAY");
    map.put(6L, "JUN");
    map.put(7L, "JLY");
    map.put(8L, "AGT");
    map.put(9L, "SPT");
    map.put(10L, "OBR");
    map.put(11L, "NVR");
    map.put(12L, "DBR");
    this.builder.appendText(MONTH_OF_YEAR, map);
    DateTimeFormatter f = this.builder.toFormatter();
    LocalDateTime dt = LocalDateTime.of(2010, 1, 1, 0, 0);
    for (Month month : Month.values()) {
      assertEquals(f.print(dt.with(month)), map.get((long) month.getValue()));
    }
  }

  @Test(groups = { "tck" })
  public void test_appendTextMap_DOM() throws Exception {

    Map<Long, String> map = new HashMap<Long, String>();
    map.put(1L, "1st");
    map.put(2L, "2nd");
    map.put(3L, "3rd");
    this.builder.appendText(DAY_OF_MONTH, map);
    DateTimeFormatter f = this.builder.toFormatter();
    LocalDateTime dt = LocalDateTime.of(2010, 1, 1, 0, 0);
    assertEquals(f.print(dt.withDayOfMonth(1)), "1st");
    assertEquals(f.print(dt.withDayOfMonth(2)), "2nd");
    assertEquals(f.print(dt.withDayOfMonth(3)), "3rd");
  }

  @Test(groups = { "tck" })
  public void test_appendTextMapIncomplete() throws Exception {

    Map<Long, String> map = new HashMap<Long, String>();
    map.put(1L, "JNY");
    this.builder.appendText(MONTH_OF_YEAR, map);
    DateTimeFormatter f = this.builder.toFormatter();
    LocalDateTime dt = LocalDateTime.of(2010, 2, 1, 0, 0);
    assertEquals(f.print(dt), "2");
  }

}
