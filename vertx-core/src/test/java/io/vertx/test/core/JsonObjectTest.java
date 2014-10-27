/*
 * Copyright (c) 2011-2014 The original author or authors
 * ------------------------------------------------------
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Apache License v2.0 which accompanies this distribution.
 *
 *     The Eclipse Public License is available at
 *     http://www.eclipse.org/legal/epl-v10.html
 *
 *     The Apache License v2.0 is available at
 *     http://www.opensource.org/licenses/apache2.0.php
 *
 * You may elect to redistribute this code under either of these licenses.
 */

package io.vertx.test.core;

import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author <a href="http://tfox.org">Tim Fox</a>
 */
public class JsonObjectTest {

  private JsonObject jsonObject;

  @Before
  public void setUp() {
    jsonObject = new JsonObject();
  }

  @Test
  public void testGetInteger() {
    jsonObject.put("foo", 123);
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
    jsonObject.put("bar", "hello");
    try {
      jsonObject.getInteger("bar");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }
    // Put as different Number types
    jsonObject.put("foo", 123L);
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
    jsonObject.put("foo", 123d);
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
    jsonObject.put("foo", 123f);
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
    jsonObject.put("foo", Long.MAX_VALUE);
    assertEquals(Integer.valueOf(-1), jsonObject.getInteger("foo"));

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getInteger("foo"));
    assertNull(jsonObject.getInteger("absent"));

    try {
      jsonObject.getInteger(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetIntegerDefault() {
    jsonObject.put("foo", 123);
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", 321));
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", null));
    jsonObject.put("bar", "hello");
    try {
      jsonObject.getInteger("bar", 123);
      fail();
    } catch (ClassCastException e) {
      // Ok
    }
    // Put as different Number types
    jsonObject.put("foo", 123l);
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", 321));
    jsonObject.put("foo", 123d);
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", 321));
    jsonObject.put("foo", 123f);
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo", 321));
    jsonObject.put("foo", Long.MAX_VALUE);
    assertEquals(Integer.valueOf(-1), jsonObject.getInteger("foo", 321));

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getInteger("foo", 321));
    assertEquals(Integer.valueOf(321), jsonObject.getInteger("absent", 321));
    assertNull(jsonObject.getInteger("foo", null));
    assertNull(jsonObject.getInteger("absent", null));

    try {
      jsonObject.getInteger(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetLong() {
    jsonObject.put("foo", 123l);
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
    jsonObject.put("bar", "hello");
    try {
      jsonObject.getLong("bar");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }
    // Put as different Number types
    jsonObject.put("foo", 123);
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
    jsonObject.put("foo", 123d);
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
    jsonObject.put("foo", 123f);
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
    jsonObject.put("foo", Long.MAX_VALUE);
    assertEquals(Long.valueOf(Long.MAX_VALUE), jsonObject.getLong("foo"));

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getLong("foo"));
    assertNull(jsonObject.getLong("absent"));

    try {
      jsonObject.getLong(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetLongDefault() {
    jsonObject.put("foo", 123l);
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo", 321l));
    assertEquals(Long.valueOf(123), jsonObject.getLong("foo", null));
    jsonObject.put("bar", "hello");
    try {
      jsonObject.getLong("bar", 123l);
      fail();
    } catch (ClassCastException e) {
      // Ok
    }
    // Put as different Number types
    jsonObject.put("foo", 123);
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo", 321l));
    jsonObject.put("foo", 123d);
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo", 321l));
    jsonObject.put("foo", 123f);
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo", 321l));
    jsonObject.put("foo", Long.MAX_VALUE);
    assertEquals(Long.valueOf(Long.MAX_VALUE), jsonObject.getLong("foo", 321l));

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getLong("foo", 321l));
    assertEquals(Long.valueOf(321l), jsonObject.getLong("absent", 321l));
    assertNull(jsonObject.getLong("foo", null));
    assertNull(jsonObject.getLong("absent", null));

    try {
      jsonObject.getLong(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetFloat() {
    jsonObject.put("foo", 123f);
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
    jsonObject.put("bar", "hello");
    try {
      jsonObject.getFloat("bar");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }
    // Put as different Number types
    jsonObject.put("foo", 123);
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
    jsonObject.put("foo", 123d);
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
    jsonObject.put("foo", 123f);
    assertEquals(Float.valueOf(123l), jsonObject.getFloat("foo"));

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getFloat("foo"));
    assertNull(jsonObject.getFloat("absent"));

    try {
      jsonObject.getFloat(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetFloatDefault() {
    jsonObject.put("foo", 123f);
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo", 321f));
    assertEquals(Float.valueOf(123), jsonObject.getFloat("foo", null));
    jsonObject.put("bar", "hello");
    try {
      jsonObject.getFloat("bar", 123f);
      fail();
    } catch (ClassCastException e) {
      // Ok
    }
    // Put as different Number types
    jsonObject.put("foo", 123);
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo", 321f));
    jsonObject.put("foo", 123d);
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo", 321f));
    jsonObject.put("foo", 123l);
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo", 321f));

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getFloat("foo", 321f));
    assertEquals(Float.valueOf(321f), jsonObject.getFloat("absent", 321f));
    assertNull(jsonObject.getFloat("foo", null));
    assertNull(jsonObject.getFloat("absent", null));

    try {
      jsonObject.getFloat(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetDouble() {
    jsonObject.put("foo", 123d);
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
    jsonObject.put("bar", "hello");
    try {
      jsonObject.getDouble("bar");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }
    // Put as different Number types
    jsonObject.put("foo", 123);
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
    jsonObject.put("foo", 123l);
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
    jsonObject.put("foo", 123f);
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getDouble("foo"));
    assertNull(jsonObject.getDouble("absent"));

    try {
      jsonObject.getDouble(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetDoubleDefault() {
    jsonObject.put("foo", 123d);
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo", 321d));
    assertEquals(Double.valueOf(123), jsonObject.getDouble("foo", null));
    jsonObject.put("bar", "hello");
    try {
      jsonObject.getDouble("bar", 123d);
      fail();
    } catch (ClassCastException e) {
      // Ok
    }
    // Put as different Number types
    jsonObject.put("foo", 123);
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo", 321d));
    jsonObject.put("foo", 123f);
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo", 321d));
    jsonObject.put("foo", 123l);
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo", 321d));

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getDouble("foo", 321d));
    assertEquals(Double.valueOf(321d), jsonObject.getDouble("absent", 321d));
    assertNull(jsonObject.getDouble("foo", null));
    assertNull(jsonObject.getDouble("absent", null));

    try {
      jsonObject.getDouble(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetString() {
    jsonObject.put("foo", "bar");
    assertEquals("bar", jsonObject.getString("foo"));
    jsonObject.put("bar", 123);
    try {
      jsonObject.getString("bar");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getString("foo"));
    assertNull(jsonObject.getString("absent"));

    try {
      jsonObject.getString(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetStringDefault() {
    jsonObject.put("foo", "bar");
    assertEquals("bar", jsonObject.getString("foo", "wibble"));
    assertEquals("bar", jsonObject.getString("foo", null));
    jsonObject.put("bar", 123);
    try {
      jsonObject.getString("bar", "wibble");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getString("foo", "wibble"));
    assertEquals("wibble", jsonObject.getString("absent", "wibble"));
    assertNull(jsonObject.getString("foo", null));
    assertNull(jsonObject.getString("absent", null));

    try {
      jsonObject.getString(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetBoolean() {
    jsonObject.put("foo", true);
    assertEquals(true, jsonObject.getBoolean("foo"));
    jsonObject.put("foo", false);
    assertEquals(false, jsonObject.getBoolean("foo"));
    jsonObject.put("bar", 123);
    try {
      jsonObject.getBoolean("bar");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getBoolean("foo"));
    assertNull(jsonObject.getBoolean("absent"));

    try {
      jsonObject.getBoolean(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }

  @Test
  public void testGetBooleanDefault() {
    jsonObject.put("foo", true);
    assertEquals(true, jsonObject.getBoolean("foo", false));
    assertEquals(true, jsonObject.getBoolean("foo", null));
    jsonObject.put("foo", false);
    assertEquals(false, jsonObject.getBoolean("foo", true));
    assertEquals(false, jsonObject.getBoolean("foo", null));
    jsonObject.put("bar", 123);
    try {
      jsonObject.getBoolean("bar", true);
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    // Null and absent values
    jsonObject.putNull("foo");
    assertNull(jsonObject.getBoolean("foo", true));
    assertNull(jsonObject.getBoolean("foo", false));
    assertEquals(true, jsonObject.getBoolean("absent", true));
    assertEquals(false, jsonObject.getBoolean("absent", false));

    try {
      jsonObject.getBoolean(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }

  }


  @Test
  public void testGetBinary() {
    byte[] bytes = TestUtils.randomByteArray(100);
    jsonObject.put("foo", bytes);
    assertTrue(TestUtils.byteArraysEqual(bytes, jsonObject.getBinary("foo")));

    // Can also get as string:
    String val = jsonObject.getString("foo");
    assertNotNull(val);
    byte[] retrieved = Base64.getDecoder().decode(val);
    assertTrue(TestUtils.byteArraysEqual(bytes, retrieved));

    jsonObject.put("foo", 123);
    try {
      jsonObject.getBinary("foo");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    jsonObject.putNull("foo");
    assertNull(jsonObject.getBinary("foo"));
    assertNull(jsonObject.getBinary("absent"));
    try {
      jsonObject.getBinary(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.getBinary(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testGetBinaryDefault() {
    byte[] bytes = TestUtils.randomByteArray(100);
    byte[] defBytes = TestUtils.randomByteArray(100);
    jsonObject.put("foo", bytes);
    assertTrue(TestUtils.byteArraysEqual(bytes, jsonObject.getBinary("foo", defBytes)));
    assertTrue(TestUtils.byteArraysEqual(bytes, jsonObject.getBinary("foo", null)));

    jsonObject.put("foo", 123);
    try {
      jsonObject.getBinary("foo", defBytes);
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    jsonObject.putNull("foo");
    assertNull(jsonObject.getBinary("foo", defBytes));
    assertTrue(TestUtils.byteArraysEqual(defBytes, jsonObject.getBinary("absent", defBytes)));
    assertNull(jsonObject.getBinary("foo", null));
    assertNull(jsonObject.getBinary("absent", null));
    try {
      jsonObject.getBinary(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testGetJsonObject() {
    JsonObject obj = new JsonObject().put("blah", "wibble");
    jsonObject.put("foo", obj);
    assertEquals(obj, jsonObject.getJsonObject("foo"));

    jsonObject.put("foo", "hello");
    try {
      jsonObject.getJsonObject("foo");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    jsonObject.putNull("foo");
    assertNull(jsonObject.getJsonObject("foo"));
    assertNull(jsonObject.getJsonObject("absent"));
    try {
      jsonObject.getJsonObject(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testGetJsonObjectDefault() {
    JsonObject obj = new JsonObject().put("blah", "wibble");
    JsonObject def = new JsonObject().put("eek", "quuz");
    jsonObject.put("foo", obj);
    assertEquals(obj, jsonObject.getJsonObject("foo", def));
    assertEquals(obj, jsonObject.getJsonObject("foo", null));

    jsonObject.put("foo", "hello");
    try {
      jsonObject.getJsonObject("foo", def);
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    jsonObject.putNull("foo");
    assertNull(jsonObject.getJsonObject("foo", def));
    assertEquals(def, jsonObject.getJsonObject("absent", def));
    assertNull(jsonObject.getJsonObject("foo", null));
    assertNull(jsonObject.getJsonObject("absent", null));
    try {
      jsonObject.getJsonObject(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testGetJsonArray() {
    JsonArray arr = new JsonArray().add("blah").add("wibble");
    jsonObject.put("foo", arr);
    assertEquals(arr, jsonObject.getJsonArray("foo"));

    jsonObject.put("foo", "hello");
    try {
      jsonObject.getJsonArray("foo");
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    jsonObject.putNull("foo");
    assertNull(jsonObject.getJsonArray("foo"));
    assertNull(jsonObject.getJsonArray("absent"));
    try {
      jsonObject.getJsonArray(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testGetJsonArrayDefault() {
    JsonArray arr = new JsonArray().add("blah").add("wibble");
    JsonArray def = new JsonArray().add("quux").add("eek");
    jsonObject.put("foo", arr);
    assertEquals(arr, jsonObject.getJsonArray("foo", def));
    assertEquals(arr, jsonObject.getJsonArray("foo", null));

    jsonObject.put("foo", "hello");
    try {
      jsonObject.getJsonArray("foo", def);
      fail();
    } catch (ClassCastException e) {
      // Ok
    }

    jsonObject.putNull("foo");
    assertNull(jsonObject.getJsonArray("foo", def));
    assertEquals(def, jsonObject.getJsonArray("absent", def));
    assertNull(jsonObject.getJsonArray("foo", null));
    assertNull(jsonObject.getJsonArray("absent", null));
    try {
      jsonObject.getJsonArray(null, null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testGetValue() {
    jsonObject.put("foo", 123);
    assertEquals(123, jsonObject.getValue("foo"));
    jsonObject.put("foo", 123l);
    assertEquals(123l, jsonObject.getValue("foo"));
    jsonObject.put("foo", 123f);
    assertEquals(123f, jsonObject.getValue("foo"));
    jsonObject.put("foo", 123d);
    assertEquals(123d, jsonObject.getValue("foo"));
    jsonObject.put("foo", false);
    assertEquals(false, jsonObject.getValue("foo"));
    jsonObject.put("foo", true);
    assertEquals(true, jsonObject.getValue("foo"));
    jsonObject.put("foo", "bar");
    assertEquals("bar", jsonObject.getValue("foo"));
    JsonObject obj = new JsonObject().put("blah", "wibble");
    jsonObject.put("foo", obj);
    assertEquals(obj, jsonObject.getValue("foo"));
    JsonArray arr = new JsonArray().add("blah").add("wibble");
    jsonObject.put("foo", arr);
    assertEquals(arr, jsonObject.getValue("foo"));
    byte[] bytes = TestUtils.randomByteArray(100);
    jsonObject.put("foo", bytes);
    assertTrue(TestUtils.byteArraysEqual(bytes, Base64.getDecoder().decode((String) jsonObject.getValue("foo"))));
    jsonObject.putNull("foo");
    assertNull(jsonObject.getValue("foo"));
    assertNull(jsonObject.getValue("absent"));
  }

  @Test
  public void testGetValueDefault() {
    jsonObject.put("foo", 123);
    assertEquals(123, jsonObject.getValue("foo", "blah"));
    assertEquals(123, jsonObject.getValue("foo", null));
    jsonObject.put("foo", 123l);
    assertEquals(123l, jsonObject.getValue("foo", "blah"));
    assertEquals(123l, jsonObject.getValue("foo", null));
    jsonObject.put("foo", 123f);
    assertEquals(123f, jsonObject.getValue("foo", "blah"));
    assertEquals(123f, jsonObject.getValue("foo", null));
    jsonObject.put("foo", 123d);
    assertEquals(123d, jsonObject.getValue("foo", "blah"));
    assertEquals(123d, jsonObject.getValue("foo", null));
    jsonObject.put("foo", false);
    assertEquals(false, jsonObject.getValue("foo", "blah"));
    assertEquals(false, jsonObject.getValue("foo", null));
    jsonObject.put("foo", true);
    assertEquals(true, jsonObject.getValue("foo", "blah"));
    assertEquals(true, jsonObject.getValue("foo", null));
    jsonObject.put("foo", "bar");
    assertEquals("bar", jsonObject.getValue("foo", "blah"));
    assertEquals("bar", jsonObject.getValue("foo", null));
    JsonObject obj = new JsonObject().put("blah", "wibble");
    jsonObject.put("foo", obj);
    assertEquals(obj, jsonObject.getValue("foo", "blah"));
    assertEquals(obj, jsonObject.getValue("foo", null));
    JsonArray arr = new JsonArray().add("blah").add("wibble");
    jsonObject.put("foo", arr);
    assertEquals(arr, jsonObject.getValue("foo", "blah"));
    assertEquals(arr, jsonObject.getValue("foo", null));
    byte[] bytes = TestUtils.randomByteArray(100);
    jsonObject.put("foo", bytes);
    assertTrue(TestUtils.byteArraysEqual(bytes, Base64.getDecoder().decode((String) jsonObject.getValue("foo", "blah"))));
    assertTrue(TestUtils.byteArraysEqual(bytes, Base64.getDecoder().decode((String)jsonObject.getValue("foo", null))));
    jsonObject.putNull("foo");
    assertNull(jsonObject.getValue("foo", "blah"));
    assertNull(jsonObject.getValue("foo", null));
    assertEquals("blah", jsonObject.getValue("absent", "blah"));
    assertNull(jsonObject.getValue("absent", null));
  }

  @Test
  public void testContainsKey() {
    jsonObject.put("foo", "bar");
    assertTrue(jsonObject.containsKey("foo"));
    jsonObject.putNull("foo");
    assertTrue(jsonObject.containsKey("foo"));
    assertFalse(jsonObject.containsKey("absent"));
  }

  @Test
  public void testSize() {
    assertEquals(0, jsonObject.size());
    jsonObject.put("foo", "bar");
    assertEquals(1, jsonObject.size());
    jsonObject.put("bar", 123);
    assertEquals(2, jsonObject.size());
    jsonObject.putNull("wibble");
    assertEquals(3, jsonObject.size());
    jsonObject.remove("wibble");
    assertEquals(2, jsonObject.size());
    jsonObject.clear();
    assertEquals(0, jsonObject.size());
  }

  @Test
  public void testPutString() {
    assertSame(jsonObject, jsonObject.put("foo", "bar"));
    assertEquals("bar", jsonObject.getString("foo"));
    jsonObject.put("quux", "wibble");
    assertEquals("wibble", jsonObject.getString("quux"));
    assertEquals("bar", jsonObject.getString("foo"));
    jsonObject.put("foo", "blah");
    assertEquals("blah", jsonObject.getString("foo"));
    try {
      jsonObject.put("foo", (String) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, "blah");
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutInteger() {
    assertSame(jsonObject, jsonObject.put("foo", 123));
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
    jsonObject.put("quux", 321);
    assertEquals(Integer.valueOf(321), jsonObject.getInteger("quux"));
    assertEquals(Integer.valueOf(123), jsonObject.getInteger("foo"));
    jsonObject.put("foo", 456);
    assertEquals(Integer.valueOf(456), jsonObject.getInteger("foo"));
    try {
      jsonObject.put("foo", (Integer) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, 123);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutLong() {
    assertSame(jsonObject, jsonObject.put("foo", 123l));
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
    jsonObject.put("quux", 321l);
    assertEquals(Long.valueOf(321l), jsonObject.getLong("quux"));
    assertEquals(Long.valueOf(123l), jsonObject.getLong("foo"));
    jsonObject.put("foo", 456l);
    assertEquals(Long.valueOf(456l), jsonObject.getLong("foo"));
    try {
      jsonObject.put("foo", (Long) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, 123l);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutFloat() {
    assertSame(jsonObject, jsonObject.put("foo", 123f));
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
    jsonObject.put("quux", 321f);
    assertEquals(Float.valueOf(321f), jsonObject.getFloat("quux"));
    assertEquals(Float.valueOf(123f), jsonObject.getFloat("foo"));
    jsonObject.put("foo", 456f);
    assertEquals(Float.valueOf(456f), jsonObject.getFloat("foo"));
    try {
      jsonObject.put("foo", (Float) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, 1.2f);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutDouble() {
    assertSame(jsonObject, jsonObject.put("foo", 123d));
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
    jsonObject.put("quux", 321d);
    assertEquals(Double.valueOf(321d), jsonObject.getDouble("quux"));
    assertEquals(Double.valueOf(123d), jsonObject.getDouble("foo"));
    jsonObject.put("foo", 456d);
    assertEquals(Double.valueOf(456d), jsonObject.getDouble("foo"));
    try {
      jsonObject.put("foo", (Double) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, 1.23d);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutBoolean() {
    assertSame(jsonObject, jsonObject.put("foo", true));
    assertEquals(true, jsonObject.getBoolean("foo"));
    jsonObject.put("quux", true);
    assertEquals(true, jsonObject.getBoolean("quux"));
    assertEquals(true, jsonObject.getBoolean("foo"));
    jsonObject.put("foo", true);
    assertEquals(true, jsonObject.getBoolean("foo"));
    try {
      jsonObject.put("foo", (Boolean) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, false);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutJsonObject() {
    JsonObject obj1 = new JsonObject().put("blah", "wibble");
    JsonObject obj2 = new JsonObject().put("eeek", "flibb");
    JsonObject obj3 = new JsonObject().put("floob", "plarp");
    assertSame(jsonObject, jsonObject.put("foo", obj1));
    assertEquals(obj1, jsonObject.getJsonObject("foo"));
    jsonObject.put("quux", obj2);
    assertEquals(obj2, jsonObject.getJsonObject("quux"));
    assertEquals(obj1, jsonObject.getJsonObject("foo"));
    jsonObject.put("foo", obj3);
    assertEquals(obj3, jsonObject.getJsonObject("foo"));
    try {
      jsonObject.put("foo", (JsonObject) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, new JsonObject());
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutJsonArray() {
    JsonArray obj1 = new JsonArray().add("parp");
    JsonArray obj2 = new JsonArray().add("fleep");
    JsonArray obj3 = new JsonArray().add("woob");

    assertSame(jsonObject, jsonObject.put("foo", obj1));
    assertEquals(obj1, jsonObject.getJsonArray("foo"));
    jsonObject.put("quux", obj2);
    assertEquals(obj2, jsonObject.getJsonArray("quux"));
    assertEquals(obj1, jsonObject.getJsonArray("foo"));
    jsonObject.put("foo", obj3);
    assertEquals(obj3, jsonObject.getJsonArray("foo"));
    try {
      jsonObject.put("foo", (JsonArray) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, new JsonArray());
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutBinary() {
    byte[] bin1 = TestUtils.randomByteArray(100);
    byte[] bin2 = TestUtils.randomByteArray(100);
    byte[] bin3 = TestUtils.randomByteArray(100);

    assertSame(jsonObject, jsonObject.put("foo", bin1));
    assertTrue(TestUtils.byteArraysEqual(bin1, jsonObject.getBinary("foo")));
    jsonObject.put("quux", bin2);
    assertTrue(TestUtils.byteArraysEqual(bin2, jsonObject.getBinary("quux")));
    assertTrue(TestUtils.byteArraysEqual(bin1, jsonObject.getBinary("foo")));
    jsonObject.put("foo", bin3);
    assertTrue(TestUtils.byteArraysEqual(bin3, jsonObject.getBinary("foo")));
    try {
      jsonObject.put("foo", (byte[]) null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
    try {
      jsonObject.put(null, bin1);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testPutNull() {
    assertSame(jsonObject, jsonObject.putNull("foo"));
    assertTrue(jsonObject.containsKey("foo"));
    assertSame(jsonObject, jsonObject.putNull("bar"));
    assertTrue(jsonObject.containsKey("bar"));
    try {
      jsonObject.putNull(null);
      fail();
    } catch (NullPointerException e) {
      // OK
    }
  }

  @Test
  public void testMergeIn1() {
    JsonObject obj1 = new JsonObject().put("foo", "bar");
    JsonObject obj2 = new JsonObject().put("eek", "flurb");
    obj1.mergeIn(obj2);
    assertEquals(2, obj1.size());
    assertEquals("bar", obj1.getString("foo"));
    assertEquals("flurb", obj1.getString("eek"));
    assertEquals(1, obj2.size());
    assertEquals("flurb", obj2.getString("eek"));
  }

  @Test
  public void testMergeIn2() {
    JsonObject obj1 = new JsonObject().put("foo", "bar");
    JsonObject obj2 = new JsonObject().put("foo", "flurb");
    obj1.mergeIn(obj2);
    assertEquals(1, obj1.size());
    assertEquals("flurb", obj1.getString("foo"));
    assertEquals(1, obj2.size());
    assertEquals("flurb", obj2.getString("foo"));
  }

  @Test
  public void testEncode() throws Exception {
    jsonObject.put("mystr", "foo");
    jsonObject.put("myint", 123);
    jsonObject.put("mylong", 1234l);
    jsonObject.put("myfloat", 1.23f);
    jsonObject.put("mydouble", 2.34d);
    jsonObject.put("myboolean", true);
    byte[] bytes = TestUtils.randomByteArray(10);
    jsonObject.put("mybinary", bytes);
    jsonObject.putNull("mynull");
    jsonObject.put("myobj", new JsonObject().put("foo", "bar"));
    jsonObject.put("myarr", new JsonArray().add("foo").add(123));
    String strBytes = Base64.getEncoder().encodeToString(bytes);
    String expected = "{\"mystr\":\"foo\",\"myint\":123,\"mylong\":1234,\"myfloat\":1.23,\"mydouble\":2.34,\"" +
      "myboolean\":true,\"mybinary\":\"" + strBytes + "\",\"mynull\":null,\"myobj\":{\"foo\":\"bar\"},\"myarr\":[\"foo\",123]}";
    String json = jsonObject.encode();
    assertEquals(expected, json);
  }

  @Test
  public void testDecode() throws Exception {
    byte[] bytes = TestUtils.randomByteArray(10);
    String strBytes = Base64.getEncoder().encodeToString(bytes);
    String json = "{\"mystr\":\"foo\",\"myint\":123,\"mylong\":1234,\"myfloat\":1.23,\"mydouble\":2.34,\"" +
      "myboolean\":true,\"mybinary\":\"" + strBytes + "\",\"mynull\":null,\"myobj\":{\"foo\":\"bar\"},\"myarr\":[\"foo\",123]}";
    JsonObject obj = new JsonObject(json);
    assertEquals(json, obj.encode());
    assertEquals("foo", obj.getString("mystr"));
    assertEquals(Integer.valueOf(123), obj.getInteger("myint"));
    assertEquals(Long.valueOf(1234), obj.getLong("mylong"));
    assertEquals(Float.valueOf(1.23f), obj.getFloat("myfloat"));
    assertEquals(Double.valueOf(2.34d), obj.getDouble("mydouble"));
    assertTrue(obj.getBoolean("myboolean"));
    assertTrue(TestUtils.byteArraysEqual(bytes, obj.getBinary("mybinary")));
    assertTrue(obj.containsKey("mynull"));
    JsonObject nestedObj = obj.getJsonObject("myobj");
    assertEquals("bar", nestedObj.getString("foo"));
    JsonArray nestedArr = obj.getJsonArray("myarr");
    assertEquals("foo", nestedArr.getString(0));
    assertEquals(Integer.valueOf(123), Integer.valueOf(nestedArr.getInteger(1)));
  }

  @Test
  public void testToString() {
    jsonObject.put("foo", "bar");
    assertEquals(jsonObject.encode(), jsonObject.toString());
  }

  @Test
  public void testEncodePrettily() throws Exception {
    jsonObject.put("mystr", "foo");
    jsonObject.put("myint", 123);
    jsonObject.put("mylong", 1234l);
    jsonObject.put("myfloat", 1.23f);
    jsonObject.put("mydouble", 2.34d);
    jsonObject.put("myboolean", true);
    byte[] bytes = TestUtils.randomByteArray(10);
    jsonObject.put("mybinary", bytes);
    jsonObject.put("myobj", new JsonObject().put("foo", "bar"));
    jsonObject.put("myarr", new JsonArray().add("foo").add(123));
    String strBytes = Base64.getEncoder().encodeToString(bytes);
    String expected = "{\n" +
      "  \"mystr\" : \"foo\",\n" +
      "  \"myint\" : 123,\n" +
      "  \"mylong\" : 1234,\n" +
      "  \"myfloat\" : 1.23,\n" +
      "  \"mydouble\" : 2.34,\n" +
      "  \"myboolean\" : true,\n" +
      "  \"mybinary\" : \"" + strBytes + "\",\n" +
      "  \"myobj\" : {\n" +
      "    \"foo\" : \"bar\"\n" +
      "  },\n" +
      "  \"myarr\" : [ \"foo\", 123 ]\n" +
      "}";
    String json = jsonObject.encodePrettily();
    assertEquals(expected, json);
  }

  // Strict JSON doesn't allow comments but we do so users can add comments to config files etc
  @Test
  public void testCommentsInJson() {
    String jsonWithComments =
      "// single line comment\n" +
      "/*\n" +
      "  This is a multi \n" +
      "  line comment\n" +
      "*/\n" +
      "{\n" +
      "// another single line comment this time inside the JSON object itself\n" +
      "  \"foo\": \"bar\" // and a single line comment at end of line \n" +
      "/*\n" +
      "  This is a another multi \n" +
      "  line comment this time inside the JSON object itself\n" +
      "*/\n" +
      "}";
    JsonObject json = new JsonObject(jsonWithComments);
    assertEquals("{\"foo\":\"bar\"}", json.encode());
  }

  @Test
  public void testInvalidJson() {
    String invalid = "qiwjdoiqwjdiqwjd";
    try {
      new JsonObject(invalid);
      fail();
    } catch (DecodeException e) {
      // OK
    }
  }

  @Test
  public void testClear() {
    jsonObject.put("foo", "bar");
    jsonObject.put("quux", 123);
    assertEquals(2, jsonObject.size());
    jsonObject.clear();
    assertEquals(0, jsonObject.size());
    assertNull(jsonObject.getValue("foo"));
    assertNull(jsonObject.getValue("quux"));
  }

  @Test
  public void testIsEmpty() {
    assertTrue(jsonObject.isEmpty());
    jsonObject.put("foo", "bar");
    jsonObject.put("quux", 123);
    assertFalse(jsonObject.isEmpty());
    jsonObject.clear();
    assertTrue(jsonObject.isEmpty());
  }

  @Test
  public void testRemove() {
    jsonObject.put("mystr", "bar");
    jsonObject.put("myint", 123);
    assertEquals("bar", jsonObject.remove("mystr"));
    assertNull(jsonObject.getValue("mystr"));
    assertEquals(123, jsonObject.remove("myint"));
    assertNull(jsonObject.getValue("myint"));
    assertTrue(jsonObject.isEmpty());
  }

  @Test
  public void testIterator() {
    jsonObject.put("foo", "bar");
    jsonObject.put("quux", 123);
    JsonObject obj = createJsonObject();
    jsonObject.put("wibble", obj);
    Iterator<Map.Entry<String, Object>> iter = jsonObject.iterator();
    assertTrue(iter.hasNext());
    Map.Entry<String, Object> entry = iter.next();
    assertEquals("foo", entry.getKey());
    assertEquals("bar", entry.getValue());
    assertTrue(iter.hasNext());
    entry = iter.next();
    assertEquals("quux", entry.getKey());
    assertEquals(123, entry.getValue());
    assertTrue(iter.hasNext());
    entry = iter.next();
    assertEquals("wibble", entry.getKey());
    assertEquals(obj, entry.getValue());
    assertFalse(iter.hasNext());
  }

  @Test
  public void testStream() {
    jsonObject.put("foo", "bar");
    jsonObject.put("quux", 123);
    JsonObject obj = createJsonObject();
    jsonObject.put("wibble", obj);
    List<Map.Entry<String, Object>> list = jsonObject.stream().collect(Collectors.toList());
    Iterator<Map.Entry<String, Object>> iter = list.iterator();
    assertTrue(iter.hasNext());
    Map.Entry<String, Object> entry = iter.next();
    assertEquals("foo", entry.getKey());
    assertEquals("bar", entry.getValue());
    assertTrue(iter.hasNext());
    entry = iter.next();
    assertEquals("quux", entry.getKey());
    assertEquals(123, entry.getValue());
    assertTrue(iter.hasNext());
    entry = iter.next();
    assertEquals("wibble", entry.getKey());
    assertEquals(obj, entry.getValue());
    assertFalse(iter.hasNext());
  }

  @Test
  public void testCopy() {
    jsonObject.put("foo", "bar");
    jsonObject.put("quux", 123);
    JsonObject obj = createJsonObject();
    jsonObject.put("wibble", obj);
    JsonObject copy = jsonObject.copy();
    assertNotSame(jsonObject, copy);
    assertEquals(jsonObject, copy);
    copy.put("blah", "flib");
    assertFalse(jsonObject.containsKey("blah"));
    copy.remove("foo");
    assertFalse(copy.containsKey("foo"));
    assertTrue(jsonObject.containsKey("foo"));
    jsonObject.put("oob", "flarb");
    assertFalse(copy.containsKey("oob"));
    jsonObject.remove("quux");
    assertFalse(jsonObject.containsKey("quux"));
    assertTrue(copy.containsKey("quux"));
    JsonObject nested = jsonObject.getJsonObject("wibble");
    JsonObject nestedCopied = copy.getJsonObject("wibble");
    assertNotSame(nested, nestedCopied);
    assertEquals(nested, nestedCopied);
  }

  @Test
  public void testInvalidValsOnCopy() {
    Map<String, Object> invalid = new HashMap<>();
    invalid.put("foo", new SomeClass());
    JsonObject object = new JsonObject(invalid);
    try {
      object.copy();
      fail();
    } catch (IllegalStateException e) {
      // OK
    }
  }

  class SomeClass {
  }

  @Test
  public void testGetMap() {
    jsonObject.put("foo", "bar");
    jsonObject.put("quux", 123);
    JsonObject obj = createJsonObject();
    jsonObject.put("wibble", obj);
    Map<String, Object> map = jsonObject.getMap();
    map.remove("foo");
    assertFalse(jsonObject.containsKey("foo"));
    map.put("bleep", "flarp");
    assertTrue(jsonObject.containsKey("bleep"));
    jsonObject.remove("quux");
    assertFalse(map.containsKey("quux"));
    jsonObject.put("wooble", "plink");
    assertTrue(map.containsKey("wooble"));
  }

  @Test
  public void testCreateFromMap() {
    Map<String, Object> map = new HashMap<>();
    map.put("foo", "bar");
    map.put("quux", 123);
    JsonObject obj = new JsonObject(map);
    assertEquals("bar", obj.getString("foo"));
    assertEquals(Integer.valueOf(123), obj.getInteger("quux"));
    assertSame(map, obj.getMap());
  }

  @Test
  public void testCreateFromMapNestedJsonObject() {
    Map<String, Object> map = new HashMap<>();
    JsonObject nestedObj = new JsonObject().put("foo", "bar");
    map.put("nested", nestedObj);
    JsonObject obj = new JsonObject(map);
    JsonObject nestedRetrieved = obj.getJsonObject("nested");
    assertEquals("bar", nestedRetrieved.getString("foo"));
  }

  @Test
  public void testCreateFromMapNestedMap() {
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> nestedMap = new HashMap<>();
    nestedMap.put("foo", "bar");
    map.put("nested", nestedMap);
    JsonObject obj = new JsonObject(map);
    JsonObject nestedRetrieved = obj.getJsonObject("nested");
    assertEquals("bar", nestedRetrieved.getString("foo"));
  }

  @Test
  public void testCreateFromMapNestedJsonArray() {
    Map<String, Object> map = new HashMap<>();
    JsonArray nestedArr = new JsonArray().add("foo");
    map.put("nested", nestedArr);
    JsonObject obj = new JsonObject(map);
    JsonArray nestedRetrieved = obj.getJsonArray("nested");
    assertEquals("foo", nestedRetrieved.getString(0));
  }

  @Test
  public void testCreateFromMapNestedList() {
    Map<String, Object> map = new HashMap<>();
    List<String> nestedArr = Arrays.asList("foo");
    map.put("nested", nestedArr);
    JsonObject obj = new JsonObject(map);
    JsonArray nestedRetrieved = obj.getJsonArray("nested");
    assertEquals("foo", nestedRetrieved.getString(0));
  }

  private JsonObject createJsonObject() {
    JsonObject obj = new JsonObject();
    obj.put("mystr", "bar");
    obj.put("myint", Integer.MAX_VALUE);
    obj.put("mylong", Long.MAX_VALUE);
    obj.put("myfloat", Float.MAX_VALUE);
    obj.put("mydouble", Double.MAX_VALUE);
    obj.put("myboolean", true);
    obj.put("mybinary", TestUtils.randomByteArray(100));
    return obj;
  }

}


