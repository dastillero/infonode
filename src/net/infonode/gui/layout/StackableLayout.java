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


// $Id: StackableLayout.java,v 1.23 2005/12/04 13:46:03 jesper Exp $
package net.infonode.gui.layout;

import net.infonode.gui.ComponentUtil;

import java.awt.*;

/**
 * <p>StackableLayout class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class StackableLayout implements LayoutManager2 {
  private Container container;
  private Component component;
  private boolean autoShowFirstComponent = true;
  private boolean useSelectedComponentSize;

  /**
   * <p>Constructor for StackableLayout.</p>
   *
   * @param container a {@link java.awt.Container} object.
   */
  public StackableLayout(Container container) {
    this.container = container;
  }

  /**
   * <p>usesSelectedComponentSize.</p>
   *
   * @return a boolean.
   */
  public boolean usesSelectedComponentSize() {
    return useSelectedComponentSize;
  }

  /**
   * <p>isAutoShowFirstComponent.</p>
   *
   * @return a boolean.
   */
  public boolean isAutoShowFirstComponent() {
    return autoShowFirstComponent;
  }

  /**
   * <p>Setter for the field <code>autoShowFirstComponent</code>.</p>
   *
   * @param autoShowFirstComponent a boolean.
   */
  public void setAutoShowFirstComponent(boolean autoShowFirstComponent) {
    this.autoShowFirstComponent = autoShowFirstComponent;
  }

  /**
   * <p>Setter for the field <code>useSelectedComponentSize</code>.</p>
   *
   * @param useSelectedComponentSize a boolean.
   */
  public void setUseSelectedComponentSize(boolean useSelectedComponentSize) {
    if (this.useSelectedComponentSize != useSelectedComponentSize) {
      this.useSelectedComponentSize = useSelectedComponentSize;
      ComponentUtil.validate(container);
      /*if (container instanceof JComponent)
        ((JComponent)container).revalidate();
      else
        container.validate();*/
    }
  }

  /** {@inheritDoc} */
  public Dimension maximumLayoutSize(Container target) {
    return LayoutUtil.add(LayoutUtil.getMinMaximumSize(target.getComponents()), target.getInsets());
  }

  /** {@inheritDoc} */
  public void invalidateLayout(Container target) {
  }

  /** {@inheritDoc} */
  public float getLayoutAlignmentY(Container target) {
    return 0;
  }

  /** {@inheritDoc} */
  public float getLayoutAlignmentX(Container target) {
    return 0;
  }

  /** {@inheritDoc} */
  public void addLayoutComponent(Component comp, Object constraints) {
    comp.setVisible(autoShowFirstComponent && comp.getParent().getComponentCount() == 1);

    if (comp.isVisible()) {
      component = comp;
    }
  }

  /** {@inheritDoc} */
  public void addLayoutComponent(String name, Component comp) {
    addLayoutComponent(comp, null);
  }

  /** {@inheritDoc} */
  public void removeLayoutComponent(Component comp) {
    if (comp == component)
      component = null;

    comp.setVisible(true);
  }

  /** {@inheritDoc} */
  public Dimension preferredLayoutSize(Container parent) {
    return LayoutUtil.add(useSelectedComponentSize ?
                          component == null ? new Dimension(0, 0) : component.getPreferredSize() :
                          LayoutUtil.getMaxPreferredSize(parent.getComponents()), parent.getInsets());
  }

  /** {@inheritDoc} */
  public Dimension minimumLayoutSize(Container parent) {
    return LayoutUtil.add(LayoutUtil.getMaxMinimumSize(parent.getComponents()), parent.getInsets());
  }

  /** {@inheritDoc} */
  public void layoutContainer(Container parent) {
    Component[] c = parent.getComponents();
    Insets parentInsets = parent.getInsets();
    Dimension size = LayoutUtil.getInteriorSize(parent);

    for (int i = 0; i < c.length; i++) {
      c[i].setBounds(parentInsets.left, parentInsets.top, size.width, size.height);
    }
  }

  /**
   * <p>getVisibleComponent.</p>
   *
   * @return a {@link java.awt.Component} object.
   */
  public Component getVisibleComponent() {
    return component;
  }

  /**
   * <p>showComponent.</p>
   *
   * @param c a {@link java.awt.Component} object.
   */
  public void showComponent(Component c) {
    final Component oldComponent = component;

    if (oldComponent == c)
      return;

    component = c;

    boolean hasFocus = oldComponent != null &&
                       LayoutUtil.isDescendingFrom(
                           KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner(),
                           oldComponent);

    if (oldComponent != null)
      oldComponent.setVisible(false);

    if (component != null) {
      component.setVisible(true);

      if (hasFocus)
      //component.requestFocusInWindow();
        ComponentUtil.smartRequestFocus(component);
    }

    
/*    if (oldComponent != null) {
//      oldComponent.setVisible(false);
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          if (oldComponent.getParent() == container && oldComponent != component) {
            oldComponent.setVisible(false);
          }

//          FocusUtil.unblockFocusChanges();
        }
      });
    }*/
  }

}
