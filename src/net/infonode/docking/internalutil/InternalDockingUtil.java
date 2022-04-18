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


// $Id: InternalDockingUtil.java,v 1.28 2009/02/05 15:57:55 jesper Exp $
package net.infonode.docking.internalutil;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JPopupMenu;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.TabWindow;
import net.infonode.docking.View;
import net.infonode.docking.action.DockingWindowAction;
import net.infonode.docking.properties.WindowTabButtonProperties;
import net.infonode.docking.util.DockingUtil;
import net.infonode.docking.util.ViewMap;
import net.infonode.properties.propertymap.PropertyMap;
import net.infonode.util.IntList;
import net.infonode.util.Printer;

/**
 * <p>InternalDockingUtil class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.28 $
 */
public class InternalDockingUtil {
  private InternalDockingUtil() {
  }

  /** Constant <code>DEFAULT_BUTTON_ICON_SIZE=10</code> */
  public static final int DEFAULT_BUTTON_ICON_SIZE = 10;

  /**
   * <p>getViews.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param views a {@link java.util.ArrayList} object.
   */
  public static void getViews(DockingWindow window, ArrayList views) {
    if (window == null)
      return;
    else if (window instanceof View)
      views.add(window);
    else {
      for (int i = 0; i < window.getChildWindowCount(); i++)
        getViews(window.getChildWindow(i), views);
    }
  }

  /**
   * <p>getWindowPath.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.util.IntList} object.
   */
  public static IntList getWindowPath(DockingWindow window) {
    return getWindowPath(window, IntList.EMPTY_LIST);
  }

  /**
   * Returns the window located at <code>windowPath</code>.
   *
   * @param relativeToWindow the window the path is relative to
   * @param windowPath       the window path
   * @return the window located at <code>windowPath</code>
   */
  public static DockingWindow getWindow(DockingWindow relativeToWindow, IntList windowPath) {
    return windowPath.isEmpty() ?
                                 relativeToWindow :
                                   windowPath.getValue() >= relativeToWindow.getChildWindowCount() ? null :
                                     getWindow(relativeToWindow.getChildWindow(windowPath.getValue()), windowPath.getNext());
  }

  private static IntList getWindowPath(DockingWindow window, IntList tail) {
    DockingWindow parent = window.getWindowParent();
    return parent == null ? tail : getWindowPath(parent, new IntList(parent.getChildWindowIndex(window), tail));
  }

  /**
   * <p>addDebugMenuItems.</p>
   *
   * @param menu a {@link javax.swing.JPopupMenu} object.
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  public static void addDebugMenuItems(JPopupMenu menu, final DockingWindow window) {
    menu.add("Dump Tree").addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        dump(window, new Printer());
      }
    });
  }

  /**
   * <p>dump.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param printer a {@link net.infonode.util.Printer} object.
   */
  public static void dump(DockingWindow window, Printer printer) {
    DockingWindow parent = window.getWindowParent();

    String clName = window.getClass().getName();

    printer.println(clName.substring(clName.lastIndexOf('.') + 1) + ", " +
        System.identityHashCode(window) + " (" +
        (parent == null ? "null" : String.valueOf(System.identityHashCode(parent))) + "), '" +
        window.getTitle() + "', " +
        (window.isVisible() ? "visible" : "not visible") + ", " +
        (window.isMaximized() ? "maximized" : "not maximized") + ", " +
        (window.getChildWindowCount() > 0 ? ":" : ""));

    if (window.getChildWindowCount() > 0) {
      printer.beginSection();

      for (int i = 0; i < window.getChildWindowCount(); i++) {
        if (window.getChildWindow(i) == null)
          printer.println("null");
        else
          dump(window.getChildWindow(i), printer);
      }

      printer.endSection();
    }
  }

  /**
   * <p>createInnerRootWindow.</p>
   *
   * @param views an array of {@link net.infonode.docking.View} objects.
   * @return a {@link net.infonode.docking.RootWindow} object.
   */
  public static RootWindow createInnerRootWindow(View[] views) {
    RootWindow rootWindow = DockingUtil.createRootWindow(new ViewMap(views), true);
    rootWindow.getRootWindowProperties().getWindowAreaProperties().setBackgroundColor(null);
    rootWindow.getRootWindowProperties().getWindowAreaShapedPanelProperties().setComponentPainter(null);
    rootWindow.getRootWindowProperties().getComponentProperties().setBackgroundColor(null);
    rootWindow.getRootWindowProperties().getComponentProperties().setBorder(null);
    //rootWindow.getRootWindowProperties().getWindowAreaProperties().setBorder(new LineBorder(Color.GRAY));
    return rootWindow;
  }

  /**
   * <p>updateButtons.</p>
   *
   * @param buttonInfos an array of {@link net.infonode.docking.internalutil.ButtonInfo} objects.
   * @param buttons an array of {@link javax.swing.AbstractButton} objects.
   * @param container a {@link java.awt.Container} object.
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param map a {@link net.infonode.properties.propertymap.PropertyMap} object.
   * @param changes a {@link java.util.Map} object.
   * @return a boolean.
   */
  public static boolean updateButtons(ButtonInfo[] buttonInfos,
                                      AbstractButton[] buttons,
                                      Container container,
                                      DockingWindow window,
                                      PropertyMap map,
                                      Map changes) {
    //    DockingWindow window = w.getOptimizedWindow();
    boolean updateContainer = false;

    for (int i = 0; i < buttonInfos.length; i++) {
      WindowTabButtonProperties p = new WindowTabButtonProperties(buttonInfos[i].getProperty().get(map));
      DockingWindowAction action = p.getAction();
      Map propertyChanges = changes == null ? null : (Map) changes.get(p.getMap());
      //      boolean v = p.isVisible();
      //      boolean b = action != null && action.isPerformable(window);
      boolean visible = p.isVisible() && action != null && action.getAction(window).isEnabled();

      if ((buttons[i] == null || (propertyChanges != null && propertyChanges.containsKey(
          WindowTabButtonProperties.FACTORY))) &&
          p.getFactory() != null &&
          action != null) {
        buttons[i] = p.getFactory().createButton(window);
        buttons[i].setFocusable(false);
        buttons[i].addActionListener(action.getAction(window).toSwingAction());
        updateContainer = true;
      }

      if (buttons[i] != null) {
        buttons[i].setToolTipText(p.getToolTipText());
        buttons[i].setIcon(p.getIcon());
        buttons[i].setRolloverIcon(p.getRolloverIcon());
        buttons[i].setPressedIcon(p.getPressedIcon());
        buttons[i].setBorderPainted(false);
        buttons[i].setVisible(visible);
      }
    }

    if (updateContainer && container != null) {
      container.removeAll();

      for (int j = 0; j < buttonInfos.length; j++) {
        if (buttons[j] != null)
          container.add(buttons[j]);
      }
    }

    return updateContainer;
  }

  /**
   * <p>addToRootWindow.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param rootWindow a {@link net.infonode.docking.RootWindow} object.
   */
  public static void addToRootWindow(DockingWindow window, RootWindow rootWindow) {
    if (rootWindow == null)
      return;

    DockingWindow w = rootWindow.getWindow();

    if (w == null)
      rootWindow.setWindow(window);
    else if (w instanceof TabWindow)
      ((TabWindow) w).addTab(window);
    else
      rootWindow.setWindow(new TabWindow(new DockingWindow[]{w, window}));
  }
}
