package com.seven.discs.helper;

public final class A1Conversion {

  /**
   * param index The X axis index
   * return The index on the X axis in A1 Notation
   */
  public static String getA1XConversion(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException("[Error] Found X index < 0: " + index);
    }
    String result = "";
    int resultInt = index;
    int remainder;
    int net;
    while (resultInt >= 0) {
      remainder = resultInt % 26;
      result = getNumber2Char(remainder) + result;
      net = resultInt - remainder;
      resultInt = net / 26 - 1;
    }
    return result;
  }

  /**
   * param i Integer between 0 and 25 inclusive
   * return Letter associated with the given Integer
   */
  private static String getNumber2Char(int i) {
      return i > -1 && i < 26 ? String.valueOf((char)(i + 65)) : null;
  }

  /**
  * param index The Y axis index
  * return The index on the Y axis in A1 Notation (ie index + 1)
  */
  public static int getA1YConversion(int index) {
    if (index < 0) {
      throw new IndexOutOfBoundsException("[Error] Found Y index < 0: " + index);
    }
    return index + 1;
  }
}
