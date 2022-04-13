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


// $Id: ShapedUtil.java,v 1.5 2005/02/16 11:28:13 jesper Exp $
package net.infonode.gui.shaped;

import net.infonode.gui.InsetsUtil;
import net.infonode.gui.RectangleUtil;
import net.infonode.gui.shaped.panel.ShapedPanel;
import net.infonode.util.Direction;

import java.awt.*;

/**
 * <p>ShapedUtil class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.5 $
 */
public class ShapedUtil {
  private ShapedUtil() {
  }

  /**
   * <p>getDirection.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a {@link net.infonode.util.Direction} object.
   */
  public static Direction getDirection(Component c) {
    return c instanceof ShapedPanel ? ((ShapedPanel) c).getDirection() : Direction.RIGHT;
  }

  /**
   * <p>transformInsets.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param insets a {@link java.awt.Insets} object.
   * @return a {@link java.awt.Insets} object.
   */
  public static Insets transformInsets(Component c, Insets insets) {
    return InsetsUtil.rotate(getDirection(c), flipInsets(c, insets));
  }

  /**
   * <p>flipInsets.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param i a {@link java.awt.Insets} object.
   * @return a {@link java.awt.Insets} object.
   */
  public static Insets flipInsets(Component c, Insets i) {
    if (c instanceof ShapedPanel) {
      if (((ShapedPanel) c).isHorizontalFlip())
        i = InsetsUtil.flipHorizontal(i);
      if (((ShapedPanel) c).isVerticalFlip())
        i = InsetsUtil.flipVertical(i);
    }

    return i;
  }

  /**
   * <p>rotateCW.</p>
   *
   * @param polygon a {@link java.awt.Polygon} object.
   * @param height a int.
   */
  public static void rotateCW(Polygon polygon, int height) {
    for (int i = 0; i < polygon.npoints; i++) {
      int tmp = polygon.ypoints[i];
      polygon.ypoints[i] = polygon.xpoints[i];
      polygon.xpoints[i] = height - 1 - tmp;
    }
  }

  /**
   * <p>rotate.</p>
   *
   * @param polygon a {@link java.awt.Polygon} object.
   * @param d a {@link net.infonode.util.Direction} object.
   * @param width a int.
   * @param height a int.
   */
  public static void rotate(Polygon polygon, Direction d, int width, int height) {
    if (d == Direction.UP) {
      rotateCW(polygon, height);
      rotateCW(polygon, width);
      rotateCW(polygon, height);
    }
    else if (d == Direction.LEFT) {
      rotateCW(polygon, height);
      rotateCW(polygon, width);
    }
    else if (d == Direction.DOWN) {
      rotateCW(polygon, height);
    }
  }

  /**
   * <p>transform.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param rect a {@link java.awt.Rectangle} object.
   * @return a {@link java.awt.Rectangle} object.
   */
  public static Rectangle transform(Component c, Rectangle rect) {
    if (c instanceof ShapedPanel) {
      ShapedPanel sp = (ShapedPanel) c;
      return RectangleUtil.transform(rect,
                                     sp.getDirection(),
                                     sp.isHorizontalFlip(),
                                     sp.isVerticalFlip(),
                                     c.getWidth(),
                                     c.getHeight());
    }
    else
      return rect;
  }

  /**
   * <p>transform.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param dim a {@link java.awt.Dimension} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension transform(Component c, Dimension dim) {
    return getDirection(c).isHorizontal() ? dim : new Dimension(dim.height, dim.width);
  }

  /**
   * <p>getWidth.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param width a int.
   * @param height a int.
   * @return a int.
   */
  public static int getWidth(Component c, int width, int height) {
    return getDirection(c).isHorizontal() ? width : height;
  }

  /**
   * <p>getHeight.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param width a int.
   * @param height a int.
   * @return a int.
   */
  public static int getHeight(Component c, int width, int height) {
    return getDirection(c).isHorizontal() ? height : width;
  }

}
