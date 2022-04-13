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


// $Id: Int4.java,v 1.2 2004/11/05 17:53:08 johan Exp $
package net.infonode.util.math;

/**
 * <p>Int4 class.</p>
 *
 * @author $Author: johan $
 * @version $Revision: 1.2 $
 */
final public class Int4 {
  private int a;
  private int b;
  private int c;
  private int d;

  /**
   * <p>Constructor for Int4.</p>
   */
  public Int4() {
    this(0, 0, 0, 0);
  }

  /**
   * <p>Constructor for Int4.</p>
   *
   * @param i a {@link net.infonode.util.math.Int4} object.
   */
  public Int4(Int4 i) {
    this(i.a, i.b, i.c, i.d);
  }

  /**
   * <p>Constructor for Int4.</p>
   *
   * @param a a int.
   * @param b a int.
   * @param c a int.
   * @param d a int.
   */
  public Int4(int a, int b, int c, int d) {
    this.a = a;
    this.b = b;
    this.c = c;
    this.d = d;
  }

  /**
   * <p>Getter for the field <code>a</code>.</p>
   *
   * @return a int.
   */
  public int getA() {
    return a;
  }

  /**
   * <p>Setter for the field <code>a</code>.</p>
   *
   * @param a a int.
   */
  public void setA(int a) {
    this.a = a;
  }

  /**
   * <p>Getter for the field <code>b</code>.</p>
   *
   * @return a int.
   */
  public int getB() {
    return b;
  }

  /**
   * <p>Setter for the field <code>b</code>.</p>
   *
   * @param b a int.
   */
  public void setB(int b) {
    this.b = b;
  }

  /**
   * <p>Getter for the field <code>c</code>.</p>
   *
   * @return a int.
   */
  public int getC() {
    return c;
  }

  /**
   * <p>Setter for the field <code>c</code>.</p>
   *
   * @param c a int.
   */
  public void setC(int c) {
    this.c = c;
  }

  /**
   * <p>Getter for the field <code>d</code>.</p>
   *
   * @return a int.
   */
  public int getD() {
    return d;
  }

  /**
   * <p>Setter for the field <code>d</code>.</p>
   *
   * @param d a int.
   */
  public void setD(int d) {
    this.d = d;
  }

  /**
   * <p>set.</p>
   *
   * @param i a {@link net.infonode.util.math.Int4} object.
   * @return a {@link net.infonode.util.math.Int4} object.
   */
  public Int4 set(Int4 i) {
    a = i.a;
    b = i.b;
    c = i.c;
    d = i.d;
    return this;
  }

  /**
   * <p>add.</p>
   *
   * @param i a {@link net.infonode.util.math.Int4} object.
   * @return a {@link net.infonode.util.math.Int4} object.
   */
  public Int4 add(Int4 i) {
    a += i.a;
    b += i.b;
    c += i.c;
    d += i.d;
    return this;
  }

  /**
   * <p>sub.</p>
   *
   * @param i a {@link net.infonode.util.math.Int4} object.
   * @return a {@link net.infonode.util.math.Int4} object.
   */
  public Int4 sub(Int4 i) {
    a -= i.a;
    b -= i.b;
    c -= i.c;
    d -= i.d;
    return this;
  }

  /**
   * <p>div.</p>
   *
   * @param value a long.
   * @return a {@link net.infonode.util.math.Int4} object.
   */
  public Int4 div(long value) {
    a /= value;
    b /= value;
    c /= value;
    d /= value;
    return this;
  }

  /**
   * <p>mul.</p>
   *
   * @param value a long.
   * @return a {@link net.infonode.util.math.Int4} object.
   */
  public Int4 mul(long value) {
    a *= value;
    b *= value;
    c *= value;
    d *= value;
    return this;
  }

  /**
   * <p>toString.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return a + ", " + b + ", " + c + ", " + d;
  }

}
