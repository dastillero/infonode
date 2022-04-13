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


// $Id: DimensionUtil.java,v 1.11 2005/12/04 13:46:04 jesper Exp $
package net.infonode.gui;

import net.infonode.util.Direction;

import java.awt.*;

/**
 * <p>DimensionUtil class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.11 $
 */
public class DimensionUtil {
  /** Constant <code>ZERO</code> */
  public static final Dimension ZERO = new Dimension(0, 0);

  /**
   * <p>min.</p>
   *
   * @param d1 a {@link java.awt.Dimension} object.
   * @param d2 a {@link java.awt.Dimension} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension min(Dimension d1, Dimension d2) {
    return new Dimension(Math.min((int) d1.getWidth(), (int) d2.getWidth()),
                         Math.min((int) d1.getHeight(), (int) d2.getHeight()));
  }

  /**
   * <p>max.</p>
   *
   * @param d1 a {@link java.awt.Dimension} object.
   * @param d2 a {@link java.awt.Dimension} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension max(Dimension d1, Dimension d2) {
    return new Dimension(Math.max((int) d1.getWidth(), (int) d2.getWidth()),
                         Math.max((int) d1.getHeight(), (int) d2.getHeight()));
  }

  /**
   * <p>getInnerDimension.</p>
   *
   * @param d a {@link java.awt.Dimension} object.
   * @param i a {@link java.awt.Insets} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension getInnerDimension(Dimension d, Insets i) {
    return new Dimension((int) (d.getWidth() - i.left - i.right),
                         (int) (d.getHeight() - i.top - i.bottom));
  }

  /**
   * <p>add.</p>
   *
   * @param dim a {@link java.awt.Dimension} object.
   * @param insets a {@link java.awt.Insets} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension add(Dimension dim, Insets insets) {
    return new Dimension(dim.width + insets.left + insets.right, dim.height + insets.top + insets.bottom);
  }

  /**
   * <p>add.</p>
   *
   * @param d1 a {@link java.awt.Dimension} object.
   * @param d2 a {@link java.awt.Dimension} object.
   * @param isHorizontalAdd a boolean.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension add(Dimension d1, Dimension d2, boolean isHorizontalAdd) {
    return new Dimension(isHorizontalAdd ? (d1.width + d2.width) : Math.max(d1.width, d2.width),
                         isHorizontalAdd ? Math.max(d1.height, d2.height) : d1.height + d2.height);
  }

  /**
   * <p>rotate.</p>
   *
   * @param source a {@link net.infonode.util.Direction} object.
   * @param d a {@link java.awt.Dimension} object.
   * @param destination a {@link net.infonode.util.Direction} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension rotate(Direction source, Dimension d, Direction destination) {
    if (source.isHorizontal() && destination.isHorizontal())
      return d;

    return new Dimension(d.height, d.width);
  }
}
