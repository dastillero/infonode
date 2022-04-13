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


// $Id: SizeIcon.java,v 1.3 2005/12/04 13:46:05 jesper Exp $
package net.infonode.tabbedpanel.theme.internal.laftheme;

import javax.swing.*;
import java.awt.*;

/**
 * <p>SizeIcon class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class SizeIcon implements Icon {
  /** Constant <code>EMPTY</code> */
  public static SizeIcon EMPTY = new SizeIcon(0, 0);

  private int width;
  private int height;
  private boolean swap;

  /**
   * <p>Constructor for SizeIcon.</p>
   *
   * @param width a int.
   * @param height a int.
   */
  public SizeIcon(int width, int height) {
    this(width, height, false);
  }

  /**
   * <p>Constructor for SizeIcon.</p>
   *
   * @param width a int.
   * @param height a int.
   * @param swap a boolean.
   */
  public SizeIcon(int width, int height, boolean swap) {
    this.width = width;
    this.height = height;
    this.swap = swap;
  }

  /** {@inheritDoc} */
  public void paintIcon(Component c, Graphics g, int x, int y) {
  }

  /**
   * <p>getIconWidth.</p>
   *
   * @return a int.
   */
  public int getIconWidth() {
    return swap ? height : width;
  }

  /**
   * <p>getIconHeight.</p>
   *
   * @return a int.
   */
  public int getIconHeight() {
    return swap ? width : height;
  }
}
