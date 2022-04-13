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


// $Id: DraggableComponentEvent.java,v 1.3 2004/11/25 13:06:42 jesper Exp $
package net.infonode.gui.draggable;

import java.awt.event.MouseEvent;

/**
 * <p>DraggableComponentEvent class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class DraggableComponentEvent {
  /** Constant <code>TYPE_UNDEFINED=-1</code> */
  public static final int TYPE_UNDEFINED = -1;
  /** Constant <code>TYPE_MOVED=0</code> */
  public static final int TYPE_MOVED = 0;
  /** Constant <code>TYPE_PRESSED=1</code> */
  public static final int TYPE_PRESSED = 1;
  /** Constant <code>TYPE_RELEASED=2</code> */
  public static final int TYPE_RELEASED = 2;
  /** Constant <code>TYPE_ENABLED=3</code> */
  public static final int TYPE_ENABLED = 3;
  /** Constant <code>TYPE_DISABLED=4</code> */
  public static final int TYPE_DISABLED = 4;

  private DraggableComponent source;
  private int type = TYPE_UNDEFINED;
  private MouseEvent mouseEvent;

  /**
   * <p>Constructor for DraggableComponentEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponent} object.
   */
  public DraggableComponentEvent(DraggableComponent source) {
    this(source, null);
  }

  /**
   * <p>Constructor for DraggableComponentEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponent} object.
   * @param mouseEvent a {@link java.awt.event.MouseEvent} object.
   */
  public DraggableComponentEvent(DraggableComponent source, MouseEvent mouseEvent) {
    this(source, TYPE_UNDEFINED, mouseEvent);
  }

  /**
   * <p>Constructor for DraggableComponentEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponent} object.
   * @param type a int.
   */
  public DraggableComponentEvent(DraggableComponent source, int type) {
    this(source, type, null);
  }

  /**
   * <p>Constructor for DraggableComponentEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponent} object.
   * @param type a int.
   * @param mouseEvent a {@link java.awt.event.MouseEvent} object.
   */
  public DraggableComponentEvent(DraggableComponent source, int type, MouseEvent mouseEvent) {
    this.source = source;
    this.type = type;
    this.mouseEvent = mouseEvent;
  }

  /**
   * <p>Getter for the field <code>source</code>.</p>
   *
   * @return a {@link net.infonode.gui.draggable.DraggableComponent} object.
   */
  public DraggableComponent getSource() {
    return source;
  }

  /**
   * <p>Getter for the field <code>type</code>.</p>
   *
   * @return a int.
   */
  public int getType() {
    return type;
  }

  /**
   * <p>Getter for the field <code>mouseEvent</code>.</p>
   *
   * @return a {@link java.awt.event.MouseEvent} object.
   */
  public MouseEvent getMouseEvent() {
    return mouseEvent;
  }

}
