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


// $Id: HoverableShapedPanel.java,v 1.15 2005/12/04 13:46:03 jesper Exp $
package net.infonode.gui.hover.panel;

import net.infonode.gui.hover.HoverEvent;
import net.infonode.gui.hover.HoverListener;
import net.infonode.gui.hover.hoverable.HoverManager;
import net.infonode.gui.hover.hoverable.Hoverable;
import net.infonode.gui.shaped.panel.ShapedPanel;

import java.awt.*;
import java.util.ArrayList;

/**
 * <p>HoverableShapedPanel class.</p>
 *
 * @author johan
 * @version $Id: $Id
 */
public class HoverableShapedPanel extends ShapedPanel implements Hoverable {
  private HoverListener hoverListener;

  private Component hoveredComponent;

  private boolean hovered = false;

  /**
   * <p>Constructor for HoverableShapedPanel.</p>
   *
   * @param listener a {@link net.infonode.gui.hover.HoverListener} object.
   */
  public HoverableShapedPanel(HoverListener listener) {
    this(new BorderLayout(), listener, null);
  }

  /**
   * <p>Constructor for HoverableShapedPanel.</p>
   *
   * @param l a {@link java.awt.LayoutManager} object.
   * @param listener a {@link net.infonode.gui.hover.HoverListener} object.
   */
  public HoverableShapedPanel(LayoutManager l, HoverListener listener) {
    this(l, listener, null);
  }

  /**
   * <p>Constructor for HoverableShapedPanel.</p>
   *
   * @param l a {@link java.awt.LayoutManager} object.
   * @param listener a {@link net.infonode.gui.hover.HoverListener} object.
   * @param hoveredComponent a {@link java.awt.Component} object.
   */
  public HoverableShapedPanel(LayoutManager l, HoverListener listener,
                              final Component hoveredComponent) {
    super(l);
    this.hoveredComponent = hoveredComponent != null ? hoveredComponent : this;
    HoverManager.getInstance().addHoverable(this);
    setHoverListener(listener);
  }

  /**
   * <p>Getter for the field <code>hoverListener</code>.</p>
   *
   * @return a {@link net.infonode.gui.hover.HoverListener} object.
   */
  public HoverListener getHoverListener() {
    return hoverListener;
  }

  /**
   * <p>Setter for the field <code>hoverListener</code>.</p>
   *
   * @param newHoverListener a {@link net.infonode.gui.hover.HoverListener} object.
   */
  public void setHoverListener(HoverListener newHoverListener) {
    if (hoverListener != newHoverListener) {
      HoverListener oldHoverListener = hoverListener;

      // if (oldHoverListener != null && newHoverListener == null)
      // HoverManager.getInstance().removeHoverable(this);

      hoverListener = newHoverListener;

      // if (oldHoverListener == null && newHoverListener != null)
      // HoverManager.getInstance().addHoverable(this);

      if (oldHoverListener != null && newHoverListener != null && hovered) {
        HoverEvent event = new HoverEvent(hoveredComponent);
        oldHoverListener.mouseExited(event);
        newHoverListener.mouseEntered(event);
      }
    }
  }

  /**
   * <p>hoverEnter.</p>
   */
  public void hoverEnter() {
    if (hoverListener != null) {
      hovered = true;
      hoverListener.mouseEntered(new HoverEvent(HoverableShapedPanel.this.hoveredComponent));
    }
  }

  /**
   * <p>hoverExit.</p>
   */
  public void hoverExit() {
    if (hoverListener != null) {
      hovered = false;
      hoverListener.mouseExited(new HoverEvent(HoverableShapedPanel.this.hoveredComponent));
    }
  }

  /**
   * <p>Getter for the field <code>hoveredComponent</code>.</p>
   *
   * @return a {@link java.awt.Component} object.
   */
  public Component getHoveredComponent() {
    return hoveredComponent;
  }

  /**
   * <p>isHovered.</p>
   *
   * @return a boolean.
   */
  public boolean isHovered() {
    return hovered;
  }

  /** {@inheritDoc} */
  public boolean acceptHover(ArrayList enterableHoverables) {
    return true;
  }
}
