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


// $Id: LayoutUtil.java,v 1.11 2005/02/16 11:28:12 jesper Exp $
package net.infonode.gui.layout;

import net.infonode.util.Direction;

import java.awt.*;

/**
 * <p>LayoutUtil class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class LayoutUtil {
  private LayoutUtil() {
  }

  /**
   * <p>getVisibleChildren.</p>
   *
   * @param parent a {@link java.awt.Container} object.
   * @return an array of {@link java.awt.Component} objects.
   */
  public static Component[] getVisibleChildren(Container parent) {
    return getVisibleChildren(parent.getComponents());
  }

  /**
   * <p>getVisibleChildren.</p>
   *
   * @param components an array of {@link java.awt.Component} objects.
   * @return an array of {@link java.awt.Component} objects.
   */
  public static Component[] getVisibleChildren(Component[] components) {
    int count = 0;

    for (int i = 0; i < components.length; i++)
      if (components[i].isVisible())
        count++;

    Component[] c = new Component[count];
    int index = 0;

    for (int i = 0; i < components.length; i++)
      if (components[i].isVisible())
        c[index++] = components[i];

    return c;
  }

  /**
   * <p>getInteriorArea.</p>
   *
   * @param container a {@link java.awt.Container} object.
   * @return a {@link java.awt.Rectangle} object.
   */
  public static Rectangle getInteriorArea(Container container) {
    Insets insets = container.getInsets();
    return new Rectangle(insets.left,
                         insets.top,
                         container.getWidth() - insets.left - insets.right,
                         container.getHeight() - insets.top - insets.bottom);
  }

  /**
   * <p>getInteriorSize.</p>
   *
   * @param container a {@link java.awt.Container} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension getInteriorSize(Container container) {
    Insets insets = container.getInsets();
    return new Dimension(container.getWidth() - insets.left - insets.right,
                         container.getHeight() - insets.top - insets.bottom);
  }

  /**
   * <p>rotate.</p>
   *
   * @param dim a {@link java.awt.Dimension} object.
   * @param dir a {@link net.infonode.util.Direction} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension rotate(Dimension dim, Direction dir) {
    return rotate(dim, dir.isHorizontal());
  }

  /**
   * <p>rotate.</p>
   *
   * @param dim a {@link java.awt.Dimension} object.
   * @param horizontal a boolean.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension rotate(Dimension dim, boolean horizontal) {
    return dim == null ? null : horizontal ? dim : new Dimension(dim.height, dim.width);
  }

  /**
   * <p>isDescendingFrom.</p>
   *
   * @param component a {@link java.awt.Component} object.
   * @param parent a {@link java.awt.Component} object.
   * @return a boolean.
   */
  public static boolean isDescendingFrom(Component component, Component parent) {
    return component == parent || (component != null && isDescendingFrom(component.getParent(), parent));
  }

  /**
   * <p>getMaxMinimumSize.</p>
   *
   * @param components an array of {@link java.awt.Component} objects.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension getMaxMinimumSize(Component[] components) {
    int maxWidth = 0;
    int maxHeight = 0;

    for (int i = 0; i < components.length; i++) {
      if (components[i] != null) {
        Dimension min = components[i].getMinimumSize();
        int w = min.width;
        int h = min.height;

        if (maxHeight < h)
          maxHeight = h;

        if (maxWidth < w)
          maxWidth = w;
      }
    }

    return new Dimension(maxWidth, maxHeight);
  }

  /**
   * <p>getMaxPreferredSize.</p>
   *
   * @param components an array of {@link java.awt.Component} objects.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension getMaxPreferredSize(Component[] components) {
    int maxWidth = 0;
    int maxHeight = 0;

    for (int i = 0; i < components.length; i++) {
      if (components[i] != null) {
        Dimension min = components[i].getPreferredSize();
        int w = min.width;
        int h = min.height;

        if (maxHeight < h)
          maxHeight = h;

        if (maxWidth < w)
          maxWidth = w;
      }
    }

    return new Dimension(maxWidth, maxHeight);
  }

  /**
   * <p>getMinMaximumSize.</p>
   *
   * @param components an array of {@link java.awt.Component} objects.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension getMinMaximumSize(Component[] components) {
    int minWidth = Integer.MAX_VALUE;
    int minHeight = Integer.MAX_VALUE;

    for (int i = 0; i < components.length; i++) {
      if (components[i] != null) {
        Dimension min = components[i].getMaximumSize();
        int w = min.width;
        int h = min.height;

        if (minWidth > w)
          minWidth = w;

        if (minHeight > h)
          minHeight = h;
      }
    }

    return new Dimension(minWidth, minHeight);
  }

  /**
   * <p>rotate.</p>
   *
   * @param dir a {@link net.infonode.util.Direction} object.
   * @param insets a {@link java.awt.Insets} object.
   * @return a {@link java.awt.Insets} object.
   */
  public static Insets rotate(Direction dir, Insets insets) {
    return dir == Direction.RIGHT ? insets :
           dir == Direction.DOWN ? new Insets(insets.right, insets.top, insets.left, insets.bottom) :
           dir == Direction.LEFT ? new Insets(insets.bottom, insets.right, insets.top, insets.left) :
           new Insets(insets.left, insets.bottom, insets.right, insets.top);
  }

  /**
   * <p>unrotate.</p>
   *
   * @param dir a {@link net.infonode.util.Direction} object.
   * @param insets a {@link java.awt.Insets} object.
   * @return a {@link java.awt.Insets} object.
   */
  public static Insets unrotate(Direction dir, Insets insets) {
    return dir == Direction.RIGHT ? insets :
           dir == Direction.DOWN ? new Insets(insets.left, insets.bottom, insets.right, insets.top) :
           dir == Direction.LEFT ? new Insets(insets.bottom, insets.right, insets.top, insets.left) :
           new Insets(insets.right, insets.top, insets.left, insets.bottom);
  }

  /**
   * <p>add.</p>
   *
   * @param dim a {@link java.awt.Dimension} object.
   * @param insets a {@link java.awt.Insets} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension add(Dimension dim, Insets insets) {
    return new Dimension(dim.width + insets.left + insets.right, dim.height + insets.top + insets.bottom);
  }

  /**
   * <p>getValidSize.</p>
   *
   * @param dim a {@link java.awt.Dimension} object.
   * @param component a {@link java.awt.Component} object.
   * @return a {@link java.awt.Dimension} object.
   */
  public static Dimension getValidSize(Dimension dim, Component component) {
    Dimension minSize = component.getMinimumSize();
    Dimension maxSize = component.getMaximumSize();
    return new Dimension(Math.max(minSize.width, Math.min(dim.width, maxSize.width)),
                         Math.max(minSize.height, Math.min(dim.height, maxSize.height)));
  }

  /**
   * <p>getChildContaining.</p>
   *
   * @param parent a {@link java.awt.Component} object.
   * @param component a {@link java.awt.Component} object.
   * @return a {@link java.awt.Component} object.
   */
  public static Component getChildContaining(Component parent, Component component) {
    return component == null ?
           null : component.getParent() == parent ? component : getChildContaining(parent, component.getParent());
  }

  /**
   * <p>getBorderLayoutOrientation.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   * @return a {@link java.lang.String} object.
   */
  public static String getBorderLayoutOrientation(Direction direction) {
    return direction == Direction.UP ? BorderLayout.NORTH :
           direction == Direction.DOWN ? BorderLayout.SOUTH :
           direction == Direction.LEFT ? BorderLayout.WEST :
           BorderLayout.EAST;
  }

}
