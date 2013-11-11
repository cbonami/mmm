/*
 * Copyright (c) 2007-2012, Stephen Colebourne & Michael Nascimento Santos
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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertSame;
import static org.testng.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Duration;

import org.testng.annotations.Test;

/**
 * Test Duration.
 */
@Test
public class TestDuration extends AbstractTest {

  // -----------------------------------------------------------------------
  @Test(groups = { "implementation" })
  public void test_interfaces() {

    assertTrue(Serializable.class.isAssignableFrom(Duration.class));
    assertTrue(Comparable.class.isAssignableFrom(Duration.class));
  }

  // -----------------------------------------------------------------------
  // serialization
  // -----------------------------------------------------------------------
  @Test(groups = { "implementation" })
  public void test_deserializationSingleton() throws Exception {

    Duration orginal = Duration.ZERO;
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(baos);
    out.writeObject(orginal);
    out.close();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    ObjectInputStream in = new ObjectInputStream(bais);
    Duration ser = (Duration) in.readObject();
    assertSame(ser, Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void plus_zeroReturnsThis() {

    Duration t = Duration.ofSeconds(-1);
    assertSame(t.plus(Duration.ZERO), t);
  }

  @Test(groups = { "implementation" })
  public void plus_zeroSingleton() {

    Duration t = Duration.ofSeconds(-1);
    assertSame(t.plus(Duration.ofSeconds(1)), Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void plusSeconds_zeroReturnsThis() {

    Duration t = Duration.ofSeconds(-1);
    assertSame(t.plusSeconds(0), t);
  }

  @Test(groups = { "implementation" })
  public void plusSeconds_zeroSingleton() {

    Duration t = Duration.ofSeconds(-1);
    assertSame(t.plusSeconds(1), Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void plusMillis_zeroReturnsThis() {

    Duration t = Duration.ofSeconds(-1, 2000000);
    assertSame(t.plusMillis(0), t);
  }

  @Test(groups = { "implementation" })
  public void plusMillis_zeroSingleton() {

    Duration t = Duration.ofSeconds(-1, 2000000);
    assertSame(t.plusMillis(998), Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void plusNanos_zeroReturnsThis() {

    Duration t = Duration.ofSeconds(-1, 2000000);
    assertSame(t.plusNanos(0), t);
  }

  @Test(groups = { "implementation" })
  public void plusNanos_zeroSingleton() {

    Duration t = Duration.ofSeconds(-1, 2000000);
    assertSame(t.plusNanos(998000000), Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void minus_zeroReturnsThis() {

    Duration t = Duration.ofSeconds(1);
    assertSame(t.minus(Duration.ZERO), t);
  }

  @Test(groups = { "implementation" })
  public void minus_zeroSingleton() {

    Duration t = Duration.ofSeconds(1);
    assertSame(t.minus(Duration.ofSeconds(1)), Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void minusSeconds_zeroReturnsThis() {

    Duration t = Duration.ofSeconds(1);
    assertSame(t.minusSeconds(0), t);
  }

  @Test(groups = { "implementation" })
  public void minusSeconds_zeroSingleton() {

    Duration t = Duration.ofSeconds(1);
    assertSame(t.minusSeconds(1), Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void minusMillis_zeroReturnsThis() {

    Duration t = Duration.ofSeconds(1, 2000000);
    assertSame(t.minusMillis(0), t);
  }

  @Test(groups = { "implementation" })
  public void minusMillis_zeroSingleton() {

    Duration t = Duration.ofSeconds(1, 2000000);
    assertSame(t.minusMillis(1002), Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void minusNanos_zeroReturnsThis() {

    Duration t = Duration.ofSeconds(1, 2000000);
    assertSame(t.minusNanos(0), t);
  }

  @Test(groups = { "implementation" })
  public void minusNanos_zeroSingleton() {

    Duration t = Duration.ofSeconds(1, 2000000);
    assertSame(t.minusNanos(1002000000), Duration.ZERO);
  }

  @Test(groups = { "implementation" })
  public void test_abs_same() {

    Duration base = Duration.ofSeconds(12);
    assertSame(base.abs(), base);
  }

  void doTest_comparisons_Duration(Duration... durations) {

    for (int i = 0; i < durations.length; i++) {
      Duration a = durations[i];
      for (int j = 0; j < durations.length; j++) {
        Duration b = durations[j];
        if (i < j) {
          assertEquals(a.compareTo(b) < 0, true, a + " <=> " + b);
          assertEquals(a.isLessThan(b), true, a + " <=> " + b);
          assertEquals(a.isGreaterThan(b), false, a + " <=> " + b);
          assertEquals(a.equals(b), false, a + " <=> " + b);
        } else if (i > j) {
          assertEquals(a.compareTo(b) > 0, true, a + " <=> " + b);
          assertEquals(a.isLessThan(b), false, a + " <=> " + b);
          assertEquals(a.isGreaterThan(b), true, a + " <=> " + b);
          assertEquals(a.equals(b), false, a + " <=> " + b);
        } else {
          assertEquals(a.compareTo(b), 0, a + " <=> " + b);
          assertEquals(a.isLessThan(b), false, a + " <=> " + b);
          assertEquals(a.isGreaterThan(b), false, a + " <=> " + b);
          assertEquals(a.equals(b), true, a + " <=> " + b);
        }
      }
    }
  }

  // -----------------------------------------------------------------------
  @Test(groups = { "tck" })
  public void test_serialization_format() throws ClassNotFoundException, IOException {

    assertEqualsSerialisedForm(Duration.ofMillis(130));
  }

  @Test(groups = { "tck" })
  public void test_serialization() throws ClassNotFoundException, IOException {

    assertSerializable(Duration.ofHours(5));
  }

}
