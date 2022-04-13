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


// $Id: DraggableComponentBoxEvent.java,v 1.4 2005/02/16 11:28:11 jesper Exp $
package net.infonode.gui.draggable;

import java.awt.*;

/**
 * <p>DraggableComponentBoxEvent class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class DraggableComponentBoxEvent {
  private DraggableComponentBox source;
  private DraggableComponent draggableComponent;
  private DraggableComponent oldDraggableComponent;
  private DraggableComponentEvent draggableComponentEvent;
  private Point draggableComponentBoxPoint;

  /**
   * <p>Constructor for DraggableComponentBoxEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponentBox} object.
   */
  public DraggableComponentBoxEvent(DraggableComponentBox source) {
    this(source, null);
  }

  /**
   * <p>Constructor for DraggableComponentBoxEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponentBox} object.
   * @param component a {@link net.infonode.gui.draggable.DraggableComponent} object.
   */
  public DraggableComponentBoxEvent(DraggableComponentBox source, DraggableComponent component) {
    this(source, component, null, null);
  }

  /**
   * <p>Constructor for DraggableComponentBoxEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponentBox} object.
   * @param component a {@link net.infonode.gui.draggable.DraggableComponent} object.
   * @param event a {@link net.infonode.gui.draggable.DraggableComponentEvent} object.
   */
  public DraggableComponentBoxEvent(DraggableComponentBox source,
                                    DraggableComponent component,
                                    DraggableComponentEvent event) {
    this(source, component, event, null);
  }

  /**
   * <p>Constructor for DraggableComponentBoxEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponentBox} object.
   * @param component a {@link net.infonode.gui.draggable.DraggableComponent} object.
   * @param event a {@link net.infonode.gui.draggable.DraggableComponentEvent} object.
   * @param point a {@link java.awt.Point} object.
   */
  public DraggableComponentBoxEvent(DraggableComponentBox source,
                                    DraggableComponent component,
                                    DraggableComponentEvent event,
                                    Point point) {
    this.source = source;
    this.draggableComponent = component;
    this.draggableComponentEvent = event;
    this.draggableComponentBoxPoint = point;
  }

  /**
   * <p>Constructor for DraggableComponentBoxEvent.</p>
   *
   * @param source a {@link net.infonode.gui.draggable.DraggableComponentBox} object.
   * @param component a {@link net.infonode.gui.draggable.DraggableComponent} object.
   * @param oldDraggableComponent a {@link net.infonode.gui.draggable.DraggableComponent} object.
   */
  public DraggableComponentBoxEvent(DraggableComponentBox source,
                                    DraggableComponent component,
                                    DraggableComponent oldDraggableComponent) {
    this(source, component);
    this.oldDraggableComponent = oldDraggableComponent;
  }

  /**
   * <p>Getter for the field <code>source</code>.</p>
   *
   * @return a {@link net.infonode.gui.draggable.DraggableComponentBox} object.
   */
  public DraggableComponentBox getSource() {
    return source;
  }

  /**
   * <p>Getter for the field <code>draggableComponent</code>.</p>
   *
   * @return a {@link net.infonode.gui.draggable.DraggableComponent} object.
   */
  public DraggableComponent getDraggableComponent() {
    return draggableComponent;
  }

  /**
   * <p>Getter for the field <code>oldDraggableComponent</code>.</p>
   *
   * @return a {@link net.infonode.gui.draggable.DraggableComponent} object.
   */
  public DraggableComponent getOldDraggableComponent() {
    return oldDraggableComponent;
  }

  /**
   * <p>Getter for the field <code>draggableComponentBoxPoint</code>.</p>
   *
   * @return a {@link java.awt.Point} object.
   */
  public Point getDraggableComponentBoxPoint() {
    return draggableComponentBoxPoint;
  }

  /**
   * <p>Getter for the field <code>draggableComponentEvent</code>.</p>
   *
   * @return a {@link net.infonode.gui.draggable.DraggableComponentEvent} object.
   */
  public DraggableComponentEvent getDraggableComponentEvent() {
    return draggableComponentEvent;
  }
}
