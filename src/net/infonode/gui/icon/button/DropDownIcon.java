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


// $Id: DropDownIcon.java,v 1.6 2005/02/16 11:28:11 jesper Exp $
package net.infonode.gui.icon.button;

import net.infonode.util.Direction;

import java.awt.*;

/**
 * <p>DropDownIcon class.</p>
 *
 * @author johan
 * @version $Id: $Id
 */
public class DropDownIcon extends ArrowIcon {
  /**
   * <p>Constructor for DropDownIcon.</p>
   *
   * @param size a int.
   * @param direction a {@link net.infonode.util.Direction} object.
   */
  public DropDownIcon(int size, Direction direction) {
    this(null, size, direction);
  }

  /**
   * <p>Constructor for DropDownIcon.</p>
   *
   * @param color a {@link java.awt.Color} object.
   * @param size a int.
   * @param direction a {@link net.infonode.util.Direction} object.
   */
  public DropDownIcon(Color color, int size, Direction direction) {
    super(color, size, direction);
  }

  /** {@inheritDoc} */
  protected void paintIcon(Component c, Graphics g, int x1, int y1, int x2, int y2) {
    if (getDirection() == Direction.DOWN) {
      int offset = (getIconWidth() / 4);
      g.fillRect(x1, y1, x2 - x1 + 1, 2);
      super.paintIcon(c, g, x1, y1 + offset, x2, y2 + offset);
    }
  }
}
