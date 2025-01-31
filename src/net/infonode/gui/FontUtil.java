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


// $Id: FontUtil.java,v 1.5 2005/02/16 11:28:13 jesper Exp $
package net.infonode.gui;

import java.awt.*;

/**
 * <p>FontUtil class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class FontUtil {
  private FontUtil() {
  }

  /**
   * <p>copy.</p>
   *
   * @param font a {@link java.awt.Font} object.
   * @return a {@link java.awt.Font} object.
   */
  public static Font copy(Font font) {
    return font == null ? null : new Font(font.getName(), font.getStyle(), font.getSize());
  }
}
