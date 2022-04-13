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


// $Id: SimplePanel.java,v 1.11 2005/12/04 13:46:04 jesper Exp $
package net.infonode.gui.panel;

import javax.swing.border.Border;
import java.awt.*;

/**
 * <p>SimplePanel class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class SimplePanel extends BaseContainer {
  private Component comp;

  /**
   * <p>Constructor for SimplePanel.</p>
   */
  public SimplePanel() {
    this(new BorderLayout());
  }

  /**
   * <p>Constructor for SimplePanel.</p>
   *
   * @param border a {@link javax.swing.border.Border} object.
   */
  public SimplePanel(Border border) {
    this();
    setBorder(border);
  }

  /**
   * <p>Constructor for SimplePanel.</p>
   *
   * @param border a {@link javax.swing.border.Border} object.
   * @param comp a {@link java.awt.Component} object.
   */
  public SimplePanel(Border border, Component comp) {
    this(comp);
    setBorder(border);
  }

  /**
   * <p>Constructor for SimplePanel.</p>
   *
   * @param layoutManager a {@link java.awt.LayoutManager} object.
   */
  public SimplePanel(LayoutManager layoutManager) {
    super(false, layoutManager);
  }

  /**
   * <p>Constructor for SimplePanel.</p>
   *
   * @param c a {@link java.awt.Component} object.
   */
  public SimplePanel(Component c) {
    this();
    setComponent(c);
  }

  /**
   * <p>Constructor for SimplePanel.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param northComponent a {@link java.awt.Component} object.
   */
  public SimplePanel(Component c, Component northComponent) {
    this(c);
    add(northComponent, BorderLayout.NORTH);
  }

  /**
   * <p>Constructor for SimplePanel.</p>
   *
   * @param border a {@link javax.swing.border.Border} object.
   * @param c a {@link java.awt.Component} object.
   * @param northComponent a {@link java.awt.Component} object.
   */
  public SimplePanel(Border border, Component c, Component northComponent) {
    this(border, c);
    add(northComponent, BorderLayout.NORTH);
  }

  /**
   * <p>setComponent.</p>
   *
   * @param c a {@link java.awt.Component} object.
   */
  public void setComponent(Component c) {
    if (comp != null)
      remove(comp);

    if (c != null) {
      add(c, BorderLayout.CENTER);
      // c.repaint();
      revalidate();
    }

    comp = c;
  }

  /**
   * <p>setSouthComponent.</p>
   *
   * @param c a {@link java.awt.Component} object.
   */
  public void setSouthComponent(Component c) {
    add(c, BorderLayout.SOUTH);
    revalidate();
  }
}
