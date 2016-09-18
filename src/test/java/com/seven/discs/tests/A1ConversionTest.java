package com.seven.discs.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.seven.discs.helper.A1Conversion;

public class A1ConversionTest {


  /**
   * X-Axis A1Conversion Tests
   */
  @Test (expected = IndexOutOfBoundsException.class)
  public void xAxisNegative100() {
      A1Conversion.getA1XConversion(-100);
  }

  @Test (expected = IndexOutOfBoundsException.class)
  public void xAxisNegative1() {
      A1Conversion.getA1XConversion(-1);
  }

  @Test
  public void xAxis0() {
    String expected = "A";
    String actual = A1Conversion.getA1XConversion(0);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis1() {
    String expected = "B";
    String actual = A1Conversion.getA1XConversion(1);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis2() {
    String expected = "C";
    String actual = A1Conversion.getA1XConversion(2);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis3() {
    String expected = "D";
    String actual = A1Conversion.getA1XConversion(3);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis4() {
    String expected = "E";
    String actual = A1Conversion.getA1XConversion(4);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis10() {
    String expected = "K";
    String actual = A1Conversion.getA1XConversion(10);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis15() {
    String expected = "P";
    String actual = A1Conversion.getA1XConversion(15);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis20() {
    String expected = "U";
    String actual = A1Conversion.getA1XConversion(20);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis25() {
    String expected = "Z";
    String actual = A1Conversion.getA1XConversion(25);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis26() {
    String expected = "AA";
    String actual = A1Conversion.getA1XConversion(26);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis701() {
    String expected = "ZZ";
    String actual = A1Conversion.getA1XConversion(701);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis702() {
    String expected = "AAA";
    String actual = A1Conversion.getA1XConversion(702);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis18277() {
    String expected = "ZZZ";
    String actual = A1Conversion.getA1XConversion(18277);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

  @Test
  public void xAxis18278() {
    String expected = "AAAA";
    String actual = A1Conversion.getA1XConversion(18278);
    assertEquals("[Error] Int to A1 X-Axis", expected, actual);
  }

    /**
     * Y-Axis A1Conversion Tests
     */
  @Test (expected = IndexOutOfBoundsException.class)
  public void yAxisNegative100() {
      A1Conversion.getA1YConversion(-100);
  }

  @Test (expected = IndexOutOfBoundsException.class)
  public void yAxisNeg1() {
    A1Conversion.getA1YConversion(-1);
  }

  @Test
  public void yAxis0() {
    int expected = 1;
    int actual = A1Conversion.getA1YConversion(0);
    assertEquals("[Error] Int to A1 Y-Axis", expected, actual);
  }

  @Test
  public void yAxis1() {
    int expected = 2;
    int actual = A1Conversion.getA1YConversion(1);
    assertEquals("[Error] Int to A1 Y-Axis", expected, actual);
  }

  @Test
  public void yAxis41() {
    int expected = 42;
    int actual = A1Conversion.getA1YConversion(41);
    assertEquals("[Error] Int to A1 Y-Axis", expected, actual);
  }

}
