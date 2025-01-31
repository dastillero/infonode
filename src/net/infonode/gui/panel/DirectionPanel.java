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


// $Id: DirectionPanel.java,v 1.10 2005/12/04 13:46:03 jesper Exp $
package net.infonode.gui.panel;

import net.infonode.gui.layout.DirectionLayout;
import net.infonode.util.Direction;

import java.awt.*;

/**
 * <p>DirectionPanel class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.10 $
 */
public class DirectionPanel extends BaseContainer {
  /**
   * <p>Constructor for DirectionPanel.</p>
   */
  public DirectionPanel() {
    this(Direction.RIGHT, 0);
  }

  /**
   * <p>Constructor for DirectionPanel.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   * @param spacing a int.
   */
  public DirectionPanel(Direction direction, int spacing) {
    super(new DirectionLayout(direction, spacing));
    setForcedOpaque(false);
  }

  /**
   * <p>Constructor for DirectionPanel.</p>
   *
   * @param components an array of {@link java.awt.Component} objects.
   */
  public DirectionPanel(Component[] components) {
    this(Direction.RIGHT, components);
  }

  /**
   * <p>Constructor for DirectionPanel.</p>
   *
   * @param spacing a int.
   * @param components an array of {@link java.awt.Component} objects.
   */
  public DirectionPanel(int spacing, Component[] components) {
    this(Direction.RIGHT, spacing, components);
  }

  /**
   * <p>Constructor for DirectionPanel.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   * @param components an array of {@link java.awt.Component} objects.
   */
  public DirectionPanel(Direction direction, Component[] components) {
    this(direction, 0, components);
  }

  /**
   * <p>Constructor for DirectionPanel.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   * @param spacing a int.
   * @param components an array of {@link java.awt.Component} objects.
   */
  public DirectionPanel(Direction direction, int spacing, Component[] components) {
    this(direction, spacing);

    for (int i = 0; i < components.length; i++)
      add(components[i]);
  }

  /**
   * <p>setDirection.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   */
  public void setDirection(Direction direction) {
    ((DirectionLayout) getLayout()).setDirection(direction);
  }
}
