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


// $Id: GraphicsUtil.java,v 1.4 2005/12/04 13:46:04 jesper Exp $

package net.infonode.gui;

import javax.swing.*;
import java.awt.*;

/**
 * <p>GraphicsUtil class.</p>
 *
 * @author johan
 * @version $Id: $Id
 */
public class GraphicsUtil {
  /**
   * <p>drawOptimizedLine.</p>
   *
   * @param g a {@link java.awt.Graphics} object.
   * @param x1 a int.
   * @param y1 a int.
   * @param x2 a int.
   * @param y2 a int.
   */
  public static void drawOptimizedLine(Graphics g, int x1, int y1, int x2, int y2) {
    if (g.getColor().getAlpha() < 255 && (x1 == x2 || y1 == y2))
      g.fillRect(x1 < x2 ? x1 : x2, y1 < y2 ? y1 : y2, Math.abs(x2 - x1) + 1, Math.abs(y2 - y1) + 1);
    else
      g.drawLine(x1, y1, x2, y2);
  }

  /**
   * <p>calculateIntersectionClip.</p>
   *
   * @param x a int.
   * @param y a int.
   * @param width a int.
   * @param height a int.
   * @param originalClip a {@link java.awt.Shape} object.
   * @return a {@link java.awt.Rectangle} object.
   */
  public static Rectangle calculateIntersectionClip(int x, int y, int width, int height, Shape originalClip) {
    Rectangle bounds = originalClip.getBounds();
    SwingUtilities.computeIntersection(x, y, width, height, bounds);
    return bounds;
  }
}
