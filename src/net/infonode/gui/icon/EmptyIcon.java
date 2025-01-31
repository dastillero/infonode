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


// $Id: EmptyIcon.java,v 1.4 2005/02/16 11:28:11 jesper Exp $
package net.infonode.gui.icon;

import javax.swing.*;
import java.awt.*;

/**
 * <p>EmptyIcon class.</p>
 *
 * @author Jesper Nordenberg
 * @version $Revision: 1.4 $ $Date: 2005/02/16 11:28:11 $
 */
public class EmptyIcon implements Icon {
  /** Constant <code>INSTANCE</code> */
  public static final EmptyIcon INSTANCE = new EmptyIcon();

  private EmptyIcon() {
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
    return 0;
  }

  /**
   * <p>getIconHeight.</p>
   *
   * @return a int.
   */
  public int getIconHeight() {
    return 0;
  }
}
