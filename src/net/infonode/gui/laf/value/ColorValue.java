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


// $Id: ColorValue.java,v 1.3 2005/02/16 11:28:11 jesper Exp $
package net.infonode.gui.laf.value;

import javax.swing.plaf.ColorUIResource;
import java.awt.*;

/**
 * <p>ColorValue class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.3 $
 */
public class ColorValue {
  private ColorUIResource color;
  private ColorUIResource defaultColor;

  /**
   * <p>Constructor for ColorValue.</p>
   */
  public ColorValue() {
    this(Color.BLACK);
  }

  /**
   * <p>Constructor for ColorValue.</p>
   *
   * @param r a int.
   * @param g a int.
   * @param b a int.
   */
  public ColorValue(int r, int g, int b) {
    this(new ColorUIResource(r, g, b));
  }

  /**
   * <p>Constructor for ColorValue.</p>
   *
   * @param defaultColor a {@link java.awt.Color} object.
   */
  public ColorValue(Color defaultColor) {
    this(new ColorUIResource(defaultColor));
  }

  /**
   * <p>Constructor for ColorValue.</p>
   *
   * @param defaultColor a {@link javax.swing.plaf.ColorUIResource} object.
   */
  public ColorValue(ColorUIResource defaultColor) {
    this.defaultColor = defaultColor;
  }

  /**
   * <p>Getter for the field <code>color</code>.</p>
   *
   * @return a {@link javax.swing.plaf.ColorUIResource} object.
   */
  public ColorUIResource getColor() {
    return color == null ? defaultColor : color;
  }

  /**
   * <p>Setter for the field <code>color</code>.</p>
   *
   * @param color a {@link java.awt.Color} object.
   */
  public void setColor(Color color) {
    setColor(new ColorUIResource(color));
  }

  /**
   * <p>Setter for the field <code>color</code>.</p>
   *
   * @param color a {@link javax.swing.plaf.ColorUIResource} object.
   */
  public void setColor(ColorUIResource color) {
    this.color = color;
  }

  /**
   * <p>Setter for the field <code>defaultColor</code>.</p>
   *
   * @param defaultColor a {@link java.awt.Color} object.
   */
  public void setDefaultColor(Color defaultColor) {
    setDefaultColor(new ColorUIResource(defaultColor));
  }

  /**
   * <p>Setter for the field <code>defaultColor</code>.</p>
   *
   * @param defaultColor a {@link javax.swing.plaf.ColorUIResource} object.
   */
  public void setDefaultColor(ColorUIResource defaultColor) {
    this.defaultColor = defaultColor;
  }

  /**
   * <p>Setter for the field <code>defaultColor</code>.</p>
   *
   * @param defaultColor a {@link net.infonode.gui.laf.value.ColorValue} object.
   */
  public void setDefaultColor(ColorValue defaultColor) {
    setDefaultColor(defaultColor.getColor());
  }
}
