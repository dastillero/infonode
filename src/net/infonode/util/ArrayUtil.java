/*
 * Copyright (C) 2004 NNL Technology AB
 * Visit www.infonode.net for information about InfoNode(R) 
 * products and how to contact NNL Technology AB.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, 
 * MA 02111-1307, USA.
 */


// $Id: ArrayUtil.java,v 1.7 2005/01/28 13:50:29 jesper Exp $
package net.infonode.util;

import java.util.ArrayList;

/**
 * <p>ArrayUtil class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class ArrayUtil {
  private ArrayUtil() {
  }

  /**
   * <p>add.</p>
   *
   * @param objects an array of {@link java.lang.Object} objects.
   * @param object a {@link java.lang.Object} object.
   * @param newObjects an array of {@link java.lang.Object} objects.
   * @return an array of {@link java.lang.Object} objects.
   */
  static final public Object[] add(Object[] objects, Object object, Object[] newObjects) {
    System.arraycopy(objects, 0, newObjects, 0, objects.length);
    newObjects[objects.length] = object;
    return newObjects;
  }

  /**
   * <p>part.</p>
   *
   * @param array an array of byte.
   * @param offset a int.
   * @param length a int.
   * @return an array of byte.
   */
  static final public byte[] part(byte[] array, int offset, int length) {
    byte[] b = new byte[length];
    System.arraycopy(array, offset, b, 0, length);
    return b;
  }

  /**
   * <p>countNotNull.</p>
   *
   * @param objects an array of {@link java.lang.Object} objects.
   * @return a int.
   */
  static final public int countNotNull(Object[] objects) {
    int count = 0;

    for (int i = 0; i < objects.length; i++)
      if (objects[i] != null)
        count++;

    return count;
  }

  /**
   * <p>findSmallest.</p>
   *
   * @param items an array of double.
   * @return a int.
   */
  static final public int findSmallest(double[] items) {
    int index = 0;

    for (int i = 1; i < items.length; i++) {
      if (items[i] < items[index]) {
        index = i;
      }
    }

    return index;
  }

  /**
   * <p>findSmallest.</p>
   *
   * @param items an array of int.
   * @return a int.
   */
  static final public int findSmallest(int[] items) {
    int index = 0;

    for (int i = 1; i < items.length; i++) {
      if (items[i] < items[index]) {
        index = i;
      }
    }

    return index;
  }

  /**
   * <p>findLargest.</p>
   *
   * @param items an array of float.
   * @return a int.
   */
  static final public int findLargest(float[] items) {
    int index = 0;

    for (int i = 1; i < items.length; i++) {
      if (items[i] > items[index]) {
        index = i;
      }
    }

    return index;
  }

  /**
   * <p>toFloatArray.</p>
   *
   * @param values an array of int.
   * @return an array of float.
   */
  public static float[] toFloatArray(int[] values) {
    float[] floatValues = new float[values.length];

    for (int i = 0; i < values.length; i++)
      floatValues[i] = values[i];

    return floatValues;
  }

  /**
   * <p>indexOf.</p>
   *
   * @param array an array of int.
   * @param value a int.
   * @return a int.
   */
  static final public int indexOf(int[] array, int value) {
    for (int i = 0; i < array.length; i++)
      if (array[i] == value)
        return i;

    return -1;
  }

  /**
   * <p>indexOf.</p>
   *
   * @param array an array of byte.
   * @param value a byte.
   * @return a int.
   */
  static final public int indexOf(byte[] array, byte value) {
    for (int i = 0; i < array.length; i++)
      if (array[i] == value)
        return i;

    return -1;
  }

  /**
   * <p>append.</p>
   *
   * @param a1 an array of {@link java.lang.String} objects.
   * @param a2 an array of {@link java.lang.String} objects.
   * @return an array of {@link java.lang.String} objects.
   */
  static final public String[] append(String[] a1, String[] a2) {
    String[] n = new String[a1.length + a2.length];
    System.arraycopy(a1, 0, n, 0, a1.length);
    System.arraycopy(a2, 0, n, a1.length, a2.length);
    return n;
  }

  /**
   * <p>append.</p>
   *
   * @param a1 an array of {@link java.lang.Object} objects.
   * @param a2 an array of {@link java.lang.Object} objects.
   * @param out an array of {@link java.lang.Object} objects.
   * @return an array of {@link java.lang.Object} objects.
   */
  static final public Object[] append(Object[] a1, Object[] a2, Object[] out) {
    System.arraycopy(a1, 0, out, 0, a1.length);
    System.arraycopy(a2, 0, out, a1.length, a2.length);
    return out;
  }

  /**
   * <p>equal.</p>
   *
   * @param a an array of int.
   * @param aOffset a int.
   * @param b an array of int.
   * @param bOffset a int.
   * @param length a int.
   * @return a boolean.
   */
  public static boolean equal(int[] a, int aOffset, int[] b, int bOffset, int length) {
    for (int i = 0; i < length; i++)
      if (a[aOffset + i] != b[bOffset + i])
        return false;

    return true;
  }

  /**
   * <p>equal.</p>
   *
   * @param a an array of byte.
   * @param aOffset a int.
   * @param b an array of byte.
   * @param bOffset a int.
   * @param length a int.
   * @return a boolean.
   */
  public static boolean equal(byte[] a, int aOffset, byte[] b, int bOffset, int length) {
    for (int i = 0; i < length; i++)
      if (a[aOffset + i] != b[bOffset + i])
        return false;

    return true;
  }

  /**
   * <p>contains.</p>
   *
   * @param a an array of short.
   * @param v a short.
   * @return a boolean.
   */
  public static boolean contains(short[] a, short v) {
    for (int i = 0; i < a.length; i++)
      if (a[i] == v)
        return true;

    return false;
  }

  /**
   * <p>range.</p>
   *
   * @param start a int.
   * @param length a int.
   * @param step a int.
   * @return an array of int.
   */
  public static int[] range(int start, int length, int step) {
    int[] a = new int[length];

    for (int i = 0; i < length; i++)
      a[i] = start + step * i;

    return a;
  }

  /**
   * <p>containsEqual.</p>
   *
   * @param values an array of {@link java.lang.Object} objects.
   * @param value a {@link java.lang.Object} object.
   * @return a boolean.
   */
  public static boolean containsEqual(Object[] values, Object value) {
    return indexOfEqual(values, value) != -1;
  }

  /**
   * <p>contains.</p>
   *
   * @param values an array of {@link java.lang.Object} objects.
   * @param value a {@link java.lang.Object} object.
   * @return a boolean.
   */
  public static boolean contains(Object[] values, Object value) {
    return indexOf(values, value) != -1;
  }

  /**
   * <p>indexOf.</p>
   *
   * @param values an array of {@link java.lang.Object} objects.
   * @param value a {@link java.lang.Object} object.
   * @return a int.
   */
  public static int indexOf(Object[] values, Object value) {
    for (int i = 0; i < values.length; i++)
      if (values[i] == value)
        return i;

    return -1;
  }

  /**
   * <p>indexOf.</p>
   *
   * @param values an array of {@link java.lang.Object} objects.
   * @param value a {@link java.lang.Object} object.
   * @param startIndex a int.
   * @param length a int.
   * @return a int.
   */
  public static int indexOf(Object[] values, Object value, int startIndex, int length) {
    for (int i = startIndex; i < length; i++)
      if (values[i] == value)
        return i;

    return -1;
  }

  /**
   * <p>indexOfEqual.</p>
   *
   * @param values an array of {@link java.lang.Object} objects.
   * @param value a {@link java.lang.Object} object.
   * @return a int.
   */
  public static int indexOfEqual(Object[] values, Object value) {
    for (int i = 0; i < values.length; i++)
      if (values[i].equals(value))
        return i;

    return -1;
  }

  /**
   * <p>remove.</p>
   *
   * @param values an array of {@link java.lang.Object} objects.
   * @param value a {@link java.lang.Object} object.
   * @param newValues an array of {@link java.lang.Object} objects.
   * @return an array of {@link java.lang.Object} objects.
   */
  public static Object[] remove(Object[] values, Object value, Object[] newValues) {
    int index = indexOf(values, value);

    if (index == -1)
      index = values.length;

    System.arraycopy(values, 0, newValues, 0, index);
    System.arraycopy(values, index + 1, newValues, index, newValues.length - index);
    return newValues;
  }

  /**
   * <p>toString.</p>
   *
   * @param a an array of int.
   * @return a {@link java.lang.String} object.
   */
  public static String toString(int[] a) {
    StringBuffer b = new StringBuffer(a.length * 4);

    for (int i = 0; i < a.length; i++) {
      if (i != 0)
        b.append(", ");

      b.append(a[i]);
    }

    return b.toString();
  }

  /**
   * <p>part.</p>
   *
   * @param values an array of int.
   * @param start a int.
   * @param length a int.
   * @return an array of int.
   */
  public static int[] part(int[] values, int start, int length) {
    int[] a = new int[length];
    System.arraycopy(values, start, a, 0, length);
    return a;
  }

  /**
   * <p>sum.</p>
   *
   * @param values an array of int.
   * @return a int.
   */
  public static int sum(int[] values) {
    int sum = 0;

    for (int i = 0; i < values.length; i++)
      sum += values[i];

    return sum;
  }

  /**
   * <p>count.</p>
   *
   * @param values an array of int.
   * @param value a int.
   * @return a int.
   */
  public static int count(int[] values, int value) {
    int count = 0;

    for (int i = 0; i < values.length; i++)
      if (values[i] == value)
        count++;

    return count;
  }

  /**
   * <p>count.</p>
   *
   * @param values an array of boolean.
   * @param value a boolean.
   * @return a int.
   */
  public static int count(boolean[] values, boolean value) {
    int count = 0;

    for (int i = 0; i < values.length; i++)
      if (values[i] == value)
        count++;

    return count;
  }

  /**
   * <p>findLargest.</p>
   *
   * @param items an array of int.
   * @return a int.
   */
  public static int findLargest(int[] items) {
    int index = 0;

    for (int i = 1; i < items.length; i++) {
      if (items[i] > items[index]) {
        index = i;
      }
    }

    return index;
  }

  /**
   * <p>toIntArray.</p>
   *
   * @param items a {@link java.util.ArrayList} object.
   * @return an array of int.
   */
  public static int[] toIntArray(ArrayList items) {
    int[] result = new int[items.size()];

    for (int i = 0; i < items.size(); i++)
      result[i] = ((Number) items.get(i)).intValue();

    return result;
  }

  /**
   * <p>toBooleanArray.</p>
   *
   * @param items a {@link java.util.ArrayList} object.
   * @return an array of boolean.
   */
  public static boolean[] toBooleanArray(ArrayList items) {
    boolean[] result = new boolean[items.size()];

    for (int i = 0; i < items.size(); i++)
      result[i] = ((Boolean) items.get(i)).booleanValue();

    return result;
  }
}
