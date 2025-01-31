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


// $Id: AbstractColorProvider.java,v 1.6 2005/02/16 11:28:10 jesper Exp $
package net.infonode.gui.colorprovider;

import java.awt.*;

/**
 * Base class for color providers. It returns the color black.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.6 $
 */
abstract public class AbstractColorProvider implements ColorProvider {
  /**
   * <p>getColor.</p>
   *
   * @return a {@link java.awt.Color} object.
   */
  public Color getColor() {
    return Color.BLACK;
  }

  /** {@inheritDoc} */
  public Color getColor(Component component) {
    return getColor();
  }
}
