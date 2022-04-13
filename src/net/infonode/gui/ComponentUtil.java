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


// $Id: ComponentUtil.java,v 1.25 2005/12/04 13:46:04 jesper Exp $

package net.infonode.gui;

import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.util.Direction;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * <p>ComponentUtil class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class ComponentUtil {
  private ComponentUtil() {
  }

  /**
   * <p>getChildAt.</p>
   *
   * @param container a {@link java.awt.Container} object.
   * @param p a {@link java.awt.Point} object.
   * @return a {@link java.awt.Component} object.
   */
  public static final Component getChildAt(Container container, Point p) {
    Component c = container.getComponentAt(p);
    return c == null || c.getParent() != container ? null : c;
  }

  /**
   * <p>getVisibleChildAt.</p>
   *
   * @param container a {@link java.awt.Container} object.
   * @param p a {@link java.awt.Point} object.
   * @return a {@link java.awt.Component} object.
   */
  public static final Component getVisibleChildAt(Container container, Point p) {
    for (int i = 0; i < container.getComponentCount(); i++) {
      Component c = container.getComponent(i);
      if (c.isVisible() && c.contains(p.x - c.getX(), p.y - c.getY()))
        return c;
    }

    return null;
  }

  /**
   * <p>getChildAtLine.</p>
   *
   * @param container a {@link java.awt.Container} object.
   * @param p a {@link java.awt.Point} object.
   * @param horizontal a boolean.
   * @return a {@link java.awt.Component} object.
   */
  public static final Component getChildAtLine(Container container, Point p, boolean horizontal) {
    if (horizontal) {
      for (int i = 0; i < container.getComponentCount(); i++) {
        Component c = container.getComponent(i);
        if (p.x >= c.getX() && p.x < c.getX() + c.getWidth())
          return c;
      }
    }
    else {
      for (int i = 0; i < container.getComponentCount(); i++) {
        Component c = container.getComponent(i);
        if (p.y >= c.getY() && p.y < c.getY() + c.getHeight())
          return c;
      }
    }

    return null;
  }

  /**
   * <p>getComponentTreePosition.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param pos a {@link java.util.ArrayList} object.
   */
  public static void getComponentTreePosition(Component c, ArrayList pos) {
    if (c.getParent() == null) {
      return;
    }

    getComponentTreePosition(c.getParent(), pos);

    pos.add(Integer.valueOf(c.getParent().getComponentCount() - ComponentUtil.getComponentIndex(c)));
  }

  /**
   * <p>findComponentUnderGlassPaneAt.</p>
   *
   * @param p a {@link java.awt.Point} object.
   * @param top a {@link java.awt.Component} object.
   * @return a {@link java.awt.Component} object.
   */
  public static Component findComponentUnderGlassPaneAt(Point p, Component top) {
    Component c = null;

    if (top.isShowing()) {
      if (top instanceof RootPaneContainer)
        c =
        ((RootPaneContainer) top).getLayeredPane().findComponentAt(
            SwingUtilities.convertPoint(top, p, ((RootPaneContainer) top).getLayeredPane()));
      else
        c = ((Container) top).findComponentAt(p);
    }

    return c;
  }

  /**
   * <p>getComponentIndex.</p>
   *
   * @param component a {@link java.awt.Component} object.
   * @return a int.
   */
  public static final int getComponentIndex(Component component) {
    if (component != null && component.getParent() != null) {
      Container c = component.getParent();
      for (int i = 0; i < c.getComponentCount(); i++) {
        if (c.getComponent(i) == component)
          return i;
      }
    }

    return -1;
  }

  /**
   * <p>getBorderLayoutOrientation.</p>
   *
   * @param d a {@link net.infonode.util.Direction} object.
   * @return a {@link java.lang.String} object.
   */
  public static final String getBorderLayoutOrientation(Direction d) {
    return d == Direction.UP ?
           BorderLayout.NORTH :
           d == Direction.LEFT ? BorderLayout.WEST : d == Direction.DOWN ? BorderLayout.SOUTH : BorderLayout.EAST;
  }

  /**
   * <p>getBackgroundColor.</p>
   *
   * @param component a {@link java.awt.Component} object.
   * @return a {@link java.awt.Color} object.
   */
  public static Color getBackgroundColor(Component component) {
    if (component == null)
      return null;

    if (component instanceof BackgroundPainter) {
      ComponentPainter painter = ((BackgroundPainter) component).getComponentPainter();

      if (painter != null) {
        Color c = painter.getColor(component);

        if (c != null)
          return c;
      }
    }

    return component.isOpaque() ? component.getBackground() : getBackgroundColor(component.getParent());
  }

  /**
   * <p>countComponents.</p>
   *
   * @param c a {@link java.awt.Container} object.
   * @return a int.
   */
  public static int countComponents(Container c) {
    int num = 1;
    for (int i = 0; i < c.getComponentCount(); i++) {
      Component comp = c.getComponent(i);
      if (comp instanceof Container)
        num += countComponents((Container) comp);
      else
        num++;
    }

    return num;
  }

  /**
   * <p>getVisibleChildrenCount.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a int.
   */
  public static int getVisibleChildrenCount(Component c) {
    if (c == null || !(c instanceof Container))
      return 0;

    int count = 0;
    Container container = (Container) c;

    for (int i = 0; i < container.getComponentCount(); i++)
      if (container.getComponent(i).isVisible())
        count++;

    return count;
  }

  /**
   * <p>getTopLevelAncestor.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a {@link java.awt.Component} object.
   */
  public static Component getTopLevelAncestor(Component c) {
    while (c != null) {
      if (c instanceof Window)
        break;
      c = c.getParent();
    }
    return c;
  }

  /**
   * <p>hasVisibleChildren.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a boolean.
   */
  public static boolean hasVisibleChildren(Component c) {
    return getVisibleChildrenCount(c) > 0;
  }

  /**
   * <p>isOnlyVisibleComponent.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a boolean.
   */
  public static boolean isOnlyVisibleComponent(Component c) {
    return c != null && c.isVisible() && getVisibleChildrenCount(c.getParent()) == 1;
  }

  /**
   * <p>isOnlyVisibleComponents.</p>
   *
   * @param c an array of {@link java.awt.Component} objects.
   * @return a boolean.
   */
  public static boolean isOnlyVisibleComponents(Component[] c) {
    if (c != null && c.length > 0) {
      boolean visible = getVisibleChildrenCount(c[0].getParent()) == c.length;
      if (visible)
        for (int i = 0; i < c.length; i++)
          visible = visible && c[i].isVisible();
      return visible;
    }
    return false;
  }

  /**
   * <p>findFirstComponentOfType.</p>
   *
   * @param comp a {@link java.awt.Component} object.
   * @param c a {@link java.lang.Class} object.
   * @return a {@link java.awt.Component} object.
   */
  public static Component findFirstComponentOfType(Component comp, Class c) {
    if (c.isInstance(comp))
      return comp;

    if (comp instanceof Container) {
      Container container = (Container) comp;
      for (int i = 0; i < container.getComponentCount(); i++) {
        Component comp2 = findFirstComponentOfType(container.getComponent(i), c);
        if (comp2 != null)
          return comp2;
      }
    }
    return null;
  }

  /**
   * <p>isFocusable.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a boolean.
   */
  public static boolean isFocusable(Component c) {
    return c.isFocusable() && c.isDisplayable() && c.isVisible() && c.isEnabled();
  }

  /**
   * Requests focus unless the component already has focus. For some weird
   * reason calling {@link java.awt.Component#requestFocusInWindow()}when the
   * component is focus owner changes focus owner to another component!
   *
   * @param component the component to request focus for
   * @return true if the component has focus or probably will get focus,
   *         otherwise false
   */
  public static boolean requestFocus(Component component) {
    /*
     * System.out.println("Owner: " +
     * System.identityHashCode(KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner()) + ", " +
     * System.identityHashCode(component) + ", " +
     * (KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() ==
     * component));
     */
    return KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() == component ||
           component.requestFocusInWindow();
  }

  /**
   * Requests focus for a component. If that's not possible it's
   * {@link java.awt.FocusTraversalPolicy}is checked. If that doesn't work all it's
   * children is recursively checked with this method.
   *
   * @param component the component to request focus for
   * @return the component which has focus or probably will obtain focus, null
   *         if no component will receive focus
   */
  public static Component smartRequestFocus(Component component) {
    if (requestFocus(component))
      return component;

    if (component instanceof JComponent) {
      FocusTraversalPolicy policy = ((JComponent) component).getFocusTraversalPolicy();

      if (policy != null) {
        Component focusComponent = policy.getDefaultComponent((Container) component);

        if (focusComponent != null && requestFocus(focusComponent)) {
          return focusComponent;
        }
      }
    }

    if (component instanceof Container) {
      Component[] children = ((Container) component).getComponents();

      for (int i = 0; i < children.length; i++) {
        component = smartRequestFocus(children[i]);

        if (component != null)
          return component;
      }
    }

    return null;
  }

  /**
   * Calculates preferred max height for the given components without checking
   * isVisible.
   *
   * @param components Components to check
   * @return max height
   */
  public static int getPreferredMaxHeight(Component[] components) {
    int height = 0;
    for (int i = 0; i < components.length; i++) {
      int k = (int) components[i].getPreferredSize().getHeight();
      if (k > height)
        height = k;
    }
    return height;
  }

  /**
   * Calculates preferred max width for the given components without checking
   * isVisible.
   *
   * @param components Components to check
   * @return max width
   */
  public static int getPreferredMaxWidth(Component[] components) {
    int width = 0;
    for (int i = 0; i < components.length; i++) {
      int k = (int) components[i].getPreferredSize().getWidth();
      if (k > width)
        width = k;
    }
    return width;
  }

  /**
   * <p>setAllOpaque.</p>
   *
   * @param c a {@link java.awt.Container} object.
   * @param opaque a boolean.
   */
  public static void setAllOpaque(Container c, boolean opaque) {
    if (c instanceof JComponent) {
      ((JComponent) c).setOpaque(opaque);
      for (int i = 0; i < c.getComponentCount(); i++) {
        Component comp = c.getComponent(i);
        if (comp instanceof Container)
          setAllOpaque((Container) comp, opaque);
      }
    }
  }

  /**
   * <p>validate.</p>
   *
   * @param c a {@link javax.swing.JComponent} object.
   */
  public static void validate(JComponent c) {
    c.revalidate();
  }

  /**
   * <p>validate.</p>
   *
   * @param c a {@link java.awt.Component} object.
   */
  public static void validate(Component c) {
    if (c instanceof JComponent)
      ((JComponent) c).revalidate();
    else
      c.validate();
  }
}
