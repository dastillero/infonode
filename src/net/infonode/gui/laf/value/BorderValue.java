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


// $Id: BorderValue.java,v 1.3 2004/09/22 14:35:04 jesper Exp $
package net.infonode.gui.laf.value;

import javax.swing.border.Border;
import javax.swing.plaf.BorderUIResource;

/**
 * <p>BorderValue class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.3 $
 */
public class BorderValue {
  private BorderUIResource defaultBorder;
  private BorderUIResource border;

  /**
   * <p>Constructor for BorderValue.</p>
   */
  public BorderValue() {
  }

  /**
   * <p>Constructor for BorderValue.</p>
   *
   * @param defaultBorder a {@link javax.swing.border.Border} object.
   */
  public BorderValue(Border defaultBorder) {
    this.defaultBorder = new BorderUIResource(defaultBorder);
  }

  /**
   * <p>Getter for the field <code>defaultBorder</code>.</p>
   *
   * @return a {@link javax.swing.plaf.BorderUIResource} object.
   */
  public BorderUIResource getDefaultBorder() {
    return defaultBorder;
  }

  /**
   * <p>Setter for the field <code>defaultBorder</code>.</p>
   *
   * @param defaultBorder a {@link javax.swing.border.Border} object.
   */
  public void setDefaultBorder(Border defaultBorder) {
    setDefaultBorder(new BorderUIResource(defaultBorder));
  }

  /**
   * <p>Setter for the field <code>defaultBorder</code>.</p>
   *
   * @param defaultBorder a {@link javax.swing.plaf.BorderUIResource} object.
   */
  public void setDefaultBorder(BorderUIResource defaultBorder) {
    this.defaultBorder = defaultBorder;
  }

  /**
   * <p>Getter for the field <code>border</code>.</p>
   *
   * @return a {@link javax.swing.plaf.BorderUIResource} object.
   */
  public BorderUIResource getBorder() {
    return border == null ? defaultBorder : border;
  }

  /**
   * <p>Setter for the field <code>border</code>.</p>
   *
   * @param border a {@link javax.swing.plaf.BorderUIResource} object.
   */
  public void setBorder(BorderUIResource border) {
    this.border = border;
  }
}
