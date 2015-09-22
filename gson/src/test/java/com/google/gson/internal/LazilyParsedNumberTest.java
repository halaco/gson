/*
 * Copyright (C) 2015 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.gson.internal;

import com.google.gson.common.MoreAsserts;
import junit.framework.TestCase;

/**
 * Unit test for the {@link LazilyParsedNumber} class.
 *
 * @author Sadayuki Kato
 */
public class LazilyParsedNumberTest extends TestCase {

  public void testIntValue() {
    // by Integer#parseInt()
    assertEquals(100, new LazilyParsedNumber("100").intValue());
    assertEquals(-100, new LazilyParsedNumber("-100").intValue());
    // by Long#parseLong()
    assertEquals(-2147483644, new LazilyParsedNumber("2147483652").intValue()); // 2^31 + 5
    assertEquals(2147483644, new LazilyParsedNumber("-2147483652").intValue()); // -(2^31 + 5)
    // by BigDecimal#intValue()
    assertEquals(5, new LazilyParsedNumber("18446744073709551621").intValue()); // 2^64 + 5
    assertEquals(-5, new LazilyParsedNumber("-18446744073709551621").intValue()); // -(2^64 + 5)
  }

  public void testLongValue() {
    // by Long#parseLong()
    assertEquals(2147483652L, new LazilyParsedNumber("2147483652").longValue());
    assertEquals(-2147483652L, new LazilyParsedNumber("-2147483652").longValue());
    // by BigDecimal#intValue()
    assertEquals(5L, new LazilyParsedNumber("18446744073709551621").longValue()); // 2^64 + 5
    assertEquals(-5L, new LazilyParsedNumber("-18446744073709551621").longValue()); // -(2^64 + 5)
  }

  public void testDoubleValue() {
    assertEquals(1.2, new LazilyParsedNumber("1.2").doubleValue(), 0.0001);
    assertEquals(-1.2, new LazilyParsedNumber("-1.2").doubleValue(), 0.0001);
  }

  public void testFloatValue() {
    assertEquals(1.2f, new LazilyParsedNumber("1.2").floatValue(), 0.0001);
    assertEquals(-1.2f, new LazilyParsedNumber("-1.2").floatValue(), 0.0001);
  }

  public void testEqualsAndHashCode() {
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("100"), new LazilyParsedNumber("100"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("2147483652"), new LazilyParsedNumber("2147483652"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("18446744073709551621"),
        new LazilyParsedNumber("18446744073709551621"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("-18446744073709551621"),
        new LazilyParsedNumber("-18446744073709551621"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("1.2"), new LazilyParsedNumber("1.2"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("120"), new LazilyParsedNumber("120.0"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("1.2e2"), new LazilyParsedNumber("120"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("120"), new LazilyParsedNumber("120.00"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("1.201e2"), new LazilyParsedNumber("120.1"));
    MoreAsserts.assertEqualsAndHashCode(new LazilyParsedNumber("-1.2e2"), new LazilyParsedNumber("-120"));
  }
}
