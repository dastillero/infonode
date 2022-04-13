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


// $Id: DockingWindow.java,v 1.120 2008/04/04 12:42:15 jesper Exp $
package net.infonode.docking;

import net.infonode.docking.drag.DockingWindowDragger;
import net.infonode.docking.drop.ChildDropInfo;
import net.infonode.docking.drop.DropFilter;
import net.infonode.docking.drop.SplitDropInfo;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WindowAncestors;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.internalutil.DropAction;
import net.infonode.docking.location.LocationDecoder;
import net.infonode.docking.model.SplitWindowItem;
import net.infonode.docking.model.TabWindowItem;
import net.infonode.docking.model.ViewWriter;
import net.infonode.docking.model.WindowItem;
import net.infonode.docking.properties.DockingWindowProperties;
import net.infonode.docking.title.DockingWindowTitleProvider;
import net.infonode.docking.title.SimpleDockingWindowTitleProvider;
import net.infonode.docking.util.DockingUtil;
import net.infonode.gui.ComponentUtil;
import net.infonode.gui.EventUtil;
import net.infonode.gui.mouse.MouseButtonListener;
import net.infonode.gui.panel.BasePanel;
import net.infonode.properties.propertymap.*;
import net.infonode.util.ArrayUtil;
import net.infonode.util.Direction;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * This is the base class for all types of docking windows. The windows are structured in a tree, typically with a
 * {@link net.infonode.docking.RootWindow} at the root. Each DockingWindow has a window parent and a number of child windows.
 * <p>
 * <b>Warning: </b> the non-public methods in this class can be changed in non-compatible ways in future versions.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.120 $
 */
abstract public class DockingWindow extends BasePanel {
  private static int DROP_FLOATING_YOFFSET = 10;

  /**
   * Returns the icon for this window.
   *
   * @return the icon
   */
  abstract public Icon getIcon();

  /**
   * Returns the child window with index <code>index</code>.
   *
   * @param index the child window index
   * @return the child window
   */
  abstract public DockingWindow getChildWindow(int index);

  /**
   * Returns the number of child windows.
   *
   * @return the number of child windows
   */
  abstract public int getChildWindowCount();

  /**
   * <p>doReplace.</p>
   *
   * @param oldWindow a {@link net.infonode.docking.DockingWindow} object.
   * @param newWindow a {@link net.infonode.docking.DockingWindow} object.
   */
  abstract protected void doReplace(DockingWindow oldWindow, DockingWindow newWindow);

  /**
   * <p>doRemoveWindow.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  abstract protected void doRemoveWindow(DockingWindow window);

  /**
   * <p>update.</p>
   */
  abstract protected void update();

  abstract void removeWindowComponent(DockingWindow window);

  abstract void restoreWindowComponent(DockingWindow window);

  private DockingWindow windowParent;
  private WindowTab tab;
  private DockingWindow lastFocusedChildWindow;
  private WindowPopupMenuFactory popupMenuFactory;
  private ArrayList mouseButtonListeners;
  private ArrayList listeners;

  private PropertyMapListener propertiesListener = new PropertyMapListener() {
    public void propertyValuesChanged(PropertyMap propertyMap, Map changes) {
      doUpdate();

      updateButtonVisibility();
    }
  };

  private PropertyMapTreeListener propertyObjectTreeListener = new PropertyMapTreeListener() {
    public void propertyValuesChanged(Map changes) {
      doUpdate();
    }
  };

  private static HashSet optimizeWindows = new HashSet();
  private static int optimizeDepth;

  private WindowItem windowItem;
  private WeakReference lastRootWindow = new WeakReference(null);

  private static int updateModelDepth;

  /**
   * <p>Constructor for DockingWindow.</p>
   *
   * @param windowItem a {@link net.infonode.docking.model.WindowItem} object.
   */
  protected DockingWindow(WindowItem windowItem) {
    DockingWindow window = windowItem.getConnectedWindow();

    if (window != null)
      window.setWindowItem(windowItem.copy());

    this.windowItem = windowItem;
    this.windowItem.setConnectedWindow(this);

    addMouseListener(new MouseAdapter() {
      public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
          showPopupMenu(e);
        }
      }

      public void mouseReleased(MouseEvent e) {
        mousePressed(e);
      }
    });
  }

  /**
   * <p>init.</p>
   */
  protected void init() {
    PropertyMapWeakListenerManager.addWeakListener(getWindowProperties().getMap(), propertiesListener);
    PropertyMapWeakListenerManager.addWeakTreeListener(getPropertyObject(), propertyObjectTreeListener);
    doUpdate();
    updateWindowItem(getRootWindow());
  }

  /**
 *
   */
  private void doUpdate() {
    update();

    if (tab != null)
      tab.windowTitleChanged();

    if (windowParent != null && windowParent.getChildWindowCount() == 1)
      windowParent.doUpdate();
  }

  /**
   * <p>addWindowItem.</p>
   *
   * @param w a {@link net.infonode.docking.DockingWindow} object.
   * @param index a int.
   */
  protected void addWindowItem(DockingWindow w, int index) {
    boolean isRestore = w.getWindowItem().isRestoreWindow();
    windowItem.addWindow(w.getWindowItem(), index);

    if (!isRestore)
      w.updateWindowItems();
  }
  
  /**
   * <p>updateWindowItem.</p>
   *
   * @param w a {@link net.infonode.docking.DockingWindow} object.
   */
  protected void updateWindowItem(DockingWindow w) {
  	int index = getChildWindowIndex(w);
  	int modelIndex = index == 0 ? 0 : windowItem.getWindowIndex(getChildWindow(index - 1).getWindowItem()) + 1;
    windowItem.addWindow(w.getWindowItem(), modelIndex);
  }

  /**
   * <p>updateWindowItems.</p>
   */
  protected final void updateWindowItems() {
    windowItem.clearWindows();

    for (int i = 0; i < getChildWindowCount(); i++) {
      boolean isRestore = getChildWindow(i).windowItem.isRestoreWindow();
      windowItem.addWindow(getChildWindow(i).windowItem);

      if (!isRestore)
        getChildWindow(i).updateWindowItems();
    }
  }

  /**
   * <p>
   * Sets the preferred minimize direction of this window. If the {@link net.infonode.docking.WindowBar} in this direction is enabled this
   * window will be placed on that bar when {@link #minimize()} is called.
   * </p>
   *
   * <p>
   * Note that a window will "remember" the last {@link net.infonode.docking.WindowBar} it was added to so the preferred minimize direction
   * is changed when the window is added to another {@link net.infonode.docking.WindowBar}.
   * </p>
   *
   * @param direction the preferred minimize direction of this window, null (which is default value) means use the
   *                  closest, enabled {@link net.infonode.docking.WindowBar}
   * @since IDW 1.3.0
   */
  public void setPreferredMinimizeDirection(Direction direction) {
    windowItem.setLastMinimizedDirection(direction);
  }

  /**
   * <p>
   * Gets the preferred minimize direction of this window. See {@link #setPreferredMinimizeDirection(net.infonode.util.Direction)}
   * for more information.
   * </p>
   *
   * @return the preferred minimize direction of this window, null if the closest {@link net.infonode.docking.WindowBar} is used
   * @since IDW 1.3.0
   */
  public Direction getPreferredMinimizeDirection() {
    return windowItem.getLastMinimizedDirection();
  }

  private ArrayList getMouseButtonListeners() {
    return mouseButtonListeners;
  }

  private void setMouseButtonListeners(ArrayList listeners) {
    mouseButtonListeners = listeners;
  }

  private ArrayList getListeners() {
    return listeners;
  }

  private void setListeners(ArrayList listeners) {
    this.listeners = listeners;
  }

  /**
   * <p>isUndocked.</p>
   *
   * @return a boolean.
   */
  public boolean isUndocked() {
    return windowParent != null && windowParent.isUndocked();
  }

  /**
   * <p>
   * Adds a listener that receives mouse button events for window tabs. The
   * listener will be called when a mouse button is pressed, clicked or released
   * on a window tab of this window or a descendant of this window.
   * </p>
   *
   * <p>
   * The listeners are called in the reverse order they were added, so the last
   * added listener will be called first. When all the listeners of this window
   * has been called, the event is propagated up to the window parent of this
   * window, if there is one.
   * </p>
   *
   * <p>
   * The {@link java.awt.event.MouseEvent}source is the docking window connected to the tab in
   * which the mouse event occured. The event point is the mouse coordinate
   * where the event occured relative to the window.
   * </p>
   *
   * @param listenerDocking the listener
   * @since IDW 1.3.0
   */
  public void addTabMouseButtonListener(MouseButtonListener listenerDocking) {
    if (getMouseButtonListeners() == null)
      setMouseButtonListeners(new ArrayList(2));

    getMouseButtonListeners().add(listenerDocking);
  }

  /**
   * Removes a mouse button listener that has been previously added using the
   * {@link #addTabMouseButtonListener(MouseButtonListener)}.
   *
   * @param listenerDocking the listener
   * @since IDW 1.3.0
   */
  public void removeTabMouseButtonListener(MouseButtonListener listenerDocking) {
    if (getMouseButtonListeners() != null) {
      if (getMouseButtonListeners().remove(listenerDocking) && getMouseButtonListeners().size() == 0)
        setMouseButtonListeners(null);
    }
  }

  void fireTabWindowMouseButtonEvent(MouseEvent event) {
    fireTabWindowMouseButtonEvent(this, EventUtil.convert(event, this));
  }

  void fireTabWindowMouseButtonEvent(DockingWindow window, MouseEvent event) {
    if (getMouseButtonListeners() != null) {
      MouseButtonListener[] l = (MouseButtonListener[]) getMouseButtonListeners().toArray(
          new MouseButtonListener[getMouseButtonListeners().size()]);

      for (int i = l.length - 1; i >= 0; i--)
        l[i].mouseButtonEvent(event);
    }

    if (windowParent != null)
      windowParent.fireTabWindowMouseButtonEvent(window, event);
  }

  /**
   * Adds a listener which will reveive events for this window and all child windows.
   *
   * @param listener the listener
   * @since IDW 1.1.0
   */
  public void addListener(DockingWindowListener listener) {
    if (getListeners() == null)
      setListeners(new ArrayList(2));

    getListeners().add(listener);
  }

  /**
   * Removes a previously added listener.
   *
   * @param listener the listener
   * @since IDW 1.1.0
   */
  public void removeListener(DockingWindowListener listener) {
    if (getListeners() != null) {
      getListeners().remove(listener);

      if (getListeners().size() == 0)
        setListeners(null);
    }
  }

  /**
   * Returns the window parent of this window.
   *
   * @return the window parent of this window
   */
  public DockingWindow getWindowParent() {
    return windowParent;
  }

  /**
   * Splits this window in the given direction. If this window is a View which is contained in a TabWindow with a single
   * tab, the TabWindow will splitted instead of this View.
   *
   * @param splitWithWindow the splitWithWindow which to split with
   * @param direction       the split direction
   * @param dividerLocation the relative split divider location (0 - 1)
   * @return the resulting split window
   */
  public SplitWindow split(final DockingWindow splitWithWindow,
                           final Direction direction,
                           final float dividerLocation) {
    final SplitWindow w = new SplitWindow(direction == Direction.RIGHT || direction == Direction.LEFT);

    optimizeAfter(splitWithWindow.getWindowParent(), new Runnable() {
      public void run() {
        getWindowParent().replaceChildWindow(DockingWindow.this, w);
        w.setWindows(
            direction == Direction.DOWN || direction == Direction.RIGHT ? DockingWindow.this : splitWithWindow,
            direction == Direction.UP || direction == Direction.LEFT ? DockingWindow.this : splitWithWindow);
        w.setDividerLocation(dividerLocation);
        w.getWindowParent().optimizeWindowLayout();
      }
    });

    return w;
  }

  /**
   * Starts a drag and drop operation for this window.
   *
   * @param dropTarget the {@link net.infonode.docking.RootWindow} in which the window can be dropped
   * @return an {@link net.infonode.docking.drag.DockingWindowDragger} object which controls the drag and drop operation
   * @since IDW 1.3.0
   */
  public DockingWindowDragger startDrag(RootWindow dropTarget) {
    return new WindowDragger(this, dropTarget);
  }

  /**
   * Returns the properties for this window.
   *
   * @return the properties for this window
   */
  public DockingWindowProperties getWindowProperties() {
    return getWindowItem().getDockingWindowProperties();
  }

  /**
   * Returns the {@link net.infonode.docking.RootWindow} which contains this window, null if there is none.
   *
   * @return the {@link net.infonode.docking.RootWindow}, null if there is none
   */
  public RootWindow getRootWindow() {
    return windowParent == null ? null : windowParent.getRootWindow();
  }

  /**
   * Same as {@link #restore()}, but the {@link net.infonode.docking.DockingWindowListener#windowRestoring(DockingWindow)} method of
   * the window listeners will be called before restoring the window, giving them the possibility to abort the restore
   * operation.
   *
   * @throws net.infonode.docking.OperationAbortedException if the restore operation was aborted by a window listener
   * @see #restore()
   * @see DockingWindowListener#windowMinimizing(DockingWindow)
   * @see DockingWindowListener#windowMinimizing(DockingWindow)
   * @since IDW 1.4.0
   */
  public void restoreWithAbort() throws OperationAbortedException {
    fireWindowRestoring(this);
    restore();
  }

  /**
   * Restores this window to the location before it was minimized, maximized or closed.
   * If the window can't be restored to the exact same location, a good approximation is performed. It's not guaranteed
   * that the window is shown anywhere after this method has returned.
   */
  public void restore() {
    if (isMaximized())
      doRestoreFromMaximize();
    else if (isMinimized() || getRootWindow() == null) {
//    	DockingWindow lastFocused = getLastFocusedChildWindow();
      ArrayList views = new ArrayList();
      findViews(views);
      ArrayList ancestors = new ArrayList();

      for (int i = 0; i < views.size(); i++)
        ancestors.add(((DockingWindow) views.get(i)).getAncestors());

      restoreViews(views);

      for (int i = 0; i < views.size(); i++) {
        DockingWindow window = (DockingWindow) views.get(i);
        window.doFireWindowRestored(window);

        DockingWindow[] a = (DockingWindow[]) ancestors.get(i);

        for (int k = 0; k < a.length; k++)
          a[k].doFireWindowRestored(window);
      }

//			if (getRootWindow() != null && lastFocused != null)
      restoreFocus(); //FocusManager.focusWindow(lastFocused);
    }

    updateButtonVisibility();
  }

  private DockingWindow doRestoreFromMaximize() {
    DockingWindow restoredInWindow = null;

    if (isUndocked()) {
      FloatingWindow w = DockingUtil.getFloatingWindowFor(this);
      if (w != null) {
        w.setMaximizedWindow(null);
        restoredInWindow = w;
      }
    }
    else {
      RootWindow w = getRootWindow();
      if (w != null) {
        w.setMaximizedWindow(null);
        restoredInWindow = w;
      }
    }

    return restoredInWindow;
  }

  private ArrayList doRestore() {
    ArrayList views = new ArrayList();
    findViews(views);
    restoreViews(views);

    return views;
  }

  /**
   * <p>Removes this window from it's window parent. If the window parent is a split window or a tab window with
   * one child, it will be removed as well.</p>
   *
   * <p>The location of this window is saved and the window can be restored to that location using the
   * {@link #restore()} method.</p>
   *
   * <p>This method will call the {@link net.infonode.docking.DockingWindowListener#windowClosed(DockingWindow)} method of all the listeners
   * of this window and all window ancestors. The listeners of child windows will not be notified, for example closing
   * a tab window containing views will not notify the listeners of views in that tab window.</p>
   */
  public void close() {
    if (windowParent != null) {
      DockingWindow[] ancestors = getAncestors();
      optimizeAfter(windowParent, new Runnable() {
        public void run() {
          windowParent.removeChildWindow(DockingWindow.this);
        }
      });

      for (int i = ancestors.length - 1; i >= 0; i--)
        ancestors[i].fireWindowClosed(this);
    }
  }

  /**
   * Same as {@link #close()}, but the {@link net.infonode.docking.DockingWindowListener#windowClosing(DockingWindow)} method of
   * the window listeners will be called before closing the window, giving them the possibility to abort the close
   * operation.
   *
   * @throws net.infonode.docking.OperationAbortedException if the close operation was aborted by a window listener
   * @see #close()
   * @see DockingWindowListener#windowClosing(DockingWindow)
   * @see DockingWindowListener#windowClosing(DockingWindow)
   * @since IDW 1.1.0
   */
  public void closeWithAbort() throws OperationAbortedException {
    fireWindowClosing(this);
    close();
  }

  /**
   * <p>Undocks this window from it's window parent i.e. creates a {@link net.infonode.docking.FloatingWindow} containing this window.</p>
   *
   * <p>The window can be docked again by calling {@link #dock()}.</p>
   *
   * <p>This method will call the {@link net.infonode.docking.DockingWindowListener#windowUndocked(DockingWindow)} method of all the listeners
   * of this window and all window ancestors. The listeners of child windows will not be notified, for example undocking
   * a tab window containing views will not notify the listeners of views in that tab window.</p>
   *
   * @param location floating window location in screen coordinates
   * @return the floating window containing the undocked window
   * @since IDW 1.4.0
   */
  public FloatingWindow undock(Point location) {
    FloatingWindow fw = getRootWindow().createFloatingWindow(this, location);
    return fw;
  }

  /**
   * Same as {@link #undock(Point)}, but the {@link net.infonode.docking.DockingWindowListener#windowUndocking(DockingWindow)} method of
   * the window listeners will be called before undocking the window, giving them the possibility to abort the undock
   * operation.
   *
   * @param location floating window location in screen coordinates
   * @return the floating window containing the undocked window
   * @throws net.infonode.docking.OperationAbortedException if the undock operation was aborted by a window listener
   * @see #undock(Point)
   * @see DockingWindowListener#windowClosing(DockingWindow)
   * @see DockingWindowListener#windowClosing(DockingWindow)
   * @since IDW 1.4.0
   */
  public FloatingWindow undockWithAbort(Point location) throws OperationAbortedException {
    fireWindowUndocking(this);
    return undock(location);
  }


  /**
   * <p>Docks the window to the RootWindow to the location it had before it was undocked.</p>
   *
   * <p>If the window can't be docked to the exact same location, a good approximation is performed. It's not
   * guaranteed that the window is shown anywhere after this method has returned.
   * </p>
   *
   * <p>This method will call the {@link net.infonode.docking.DockingWindowListener#windowDocked(DockingWindow)} method of all the listeners
   * of this window and all window ancestors. The listeners of child windows will not be notified, for example docking
   * a tab window containing views will not notify the listeners of views in that tab window.</p>
   *
   * @since IDW 1.4.0
   */
  public void dock() {
    if (isUndocked()) {
      ArrayList dockedViews = doRestore();

      updateButtonVisibility();

      fireWindowDocked(dockedViews);

      if (dockedViews.size() > 0 && ((DockingWindow) dockedViews.get(0)).getRootWindow() != null)
        FocusManager.focusWindow((DockingWindow) dockedViews.get(0));
    }
  }

  /**
   * Same as {@link #dock()}, but the {@link net.infonode.docking.DockingWindowListener#windowDocking(DockingWindow)} method of
   * the window listeners will be called before docking the window, giving them the possibility to abort the dock
   * operation.
   *
   * @throws net.infonode.docking.OperationAbortedException if the dock operation was aborted by a window listener
   * @see #dock()
   * @see DockingWindowListener#windowDocking(DockingWindow)
   * @see DockingWindowListener#windowDocking(DockingWindow)
   * @since IDW 1.4.0
   */
  public void dockWithAbort() throws OperationAbortedException {
    if (isUndocked()) {
      fireWindowDocking(this);
      dock();
    }
  }

  /**
   * Returns the index of a child windows.
   *
   * @param window the child window
   * @return the index of the child window, -1 if the window is not a child of this window
   */
  public int getChildWindowIndex(DockingWindow window) {
    for (int i = 0; i < getChildWindowCount(); i++)
      if (getChildWindow(i) == window)
        return i;

    return -1;
  }

  /**
   * Returns the popup menu factory for this window. If it's null the window parent popup menu factory will be used
   * when the mouse popup trigger is activated on this window.
   *
   * @return the popup menu factory for this window, null if there is none
   */
  public WindowPopupMenuFactory getPopupMenuFactory() {
    return popupMenuFactory;
  }

  /**
   * Sets the popup menu factory for this window. If it's not null a popup menu will be created and shown when the mouse
   * popup trigger is activated on this window.
   *
   * @param popupMenuFactory the popup menu factory, null if no popup menu should be shown
   */
  public void setPopupMenuFactory(WindowPopupMenuFactory popupMenuFactory) {
    this.popupMenuFactory = popupMenuFactory;
  }

  /**
   * Returns true if this window is minimized, ie located in a {@link net.infonode.docking.WindowBar}.
   *
   * @return true if this window is minimized
   */
  public boolean isMinimized() {
    return windowParent != null && windowParent.isMinimized();
  }

  /**
   * Returns the child window that last contained focus.
   *
   * @return the child window that last contained focus, null if no child window has contained focus or the child
   *         has been removed from this window
   */
  public DockingWindow getLastFocusedChildWindow() {
    return lastFocusedChildWindow;
  }

  /**
   * Maximizes this window in its root window or in its floating window. If this window has no root window nothing
   * happens. This method takes the window component and displays it at the top in the root window or in the floating
   * window. It does NOT modify the window tree structure, ie the window parent remains the unchanged.
   *
   * <p>The location of this window is saved and the window can be restored to that location using the
   * {@link #restore()} method.</p>
   *
   * @since IDW 1.1.0
   */
  public final void maximize() {
    if (isUndocked()) {
      FloatingWindow w = DockingUtil.getFloatingWindowFor(this);

      if (w != null)
        w.setMaximizedWindow(this);
    }
    else {
      RootWindow rootWindow = getRootWindow();

      if (rootWindow != null)
        rootWindow.setMaximizedWindow(this);
    }

    updateButtonVisibility();
  }

  /**
   * Same as {@link #maximize()}, but the {@link net.infonode.docking.DockingWindowListener#windowMaximized(DockingWindow)} method of
   * the window listeners will be called before maximizing the window, giving them the possibility to abort the maximize
   * operation.
   *
   * @throws net.infonode.docking.OperationAbortedException if the maximize operation was aborted by a window listener
   * @see #maximize()
   * @see DockingWindowListener#windowMinimizing(DockingWindow)
   * @see DockingWindowListener#windowMinimizing(DockingWindow)
   * @since IDW 1.4.0
   */
  public void maximizeWithAbort() throws OperationAbortedException {
    if (!isMaximized()) {
      fireWindowMaximizing(this);
      maximize();
    }
  }

  /**
   * Returns true if this window has a root window and is maximized in that root window or in a floating window.
   *
   * @return true if this window has a root window and is maximized in that root window or in a floating window
   * @since IDW 1.1.0
   */
  public boolean isMaximized() {
    DockingWindow w;
    if (isUndocked()) {
      FloatingWindow floatingWindow = DockingUtil.getFloatingWindowFor(this);
      w = floatingWindow != null ? floatingWindow.getMaximizedWindow() : null;
    }
    else {
      RootWindow rootWindow = getRootWindow();
      w = rootWindow != null ? rootWindow.getMaximizedWindow() : null;
    }
    return w == this;
  }

  /**
   * Minimizes this window. The window is minimized to the {@link net.infonode.docking.WindowBar} in the preferred minimize direction,
   * see {@link #setPreferredMinimizeDirection(net.infonode.util.Direction)} and {@link #getPreferredMinimizeDirection()}.
   * If the {@link net.infonode.docking.WindowBar} in that direction is not enabled, or the direction is null, thiw window is placed on the
   * closest enabled {@link net.infonode.docking.WindowBar}.
   * If no suitable {@link net.infonode.docking.WindowBar} was found or this window already is minimized, no action is performed.
   *
   * <p>The location of this window is saved and the window can be restored to that location using the
   * {@link #restore()} method.</p>
   */
  public void minimize() {
    getOptimizedWindow().doMinimize();
  }

  /**
   * Minimizes this window to a {@link net.infonode.docking.WindowBar}located in <code>direction</code>. If no suitable {@link net.infonode.docking.WindowBar}was
   * found or this window already is minimized, no action is performed.
   *
   * <p>The location of this window is saved and the window can be restored to that location using the
   * {@link #restore()} method.</p>
   *
   * @param direction the direction in which the window bar to be minimized to is located
   */
  public void minimize(Direction direction) {
    doMinimize(direction);
  }

  /**
   * Same as {@link #minimize()}, but the {@link net.infonode.docking.DockingWindowListener#windowMinimizing(DockingWindow)} method of
   * the window listeners will be called before minimizing the window, giving them the possibility to abort the minimize
   * operation.
   *
   * @throws net.infonode.docking.OperationAbortedException if the minimize operation was aborted by a window listener
   * @see #minimize()
   * @see DockingWindowListener#windowMinimizing(DockingWindow)
   * @see DockingWindowListener#windowMinimizing(DockingWindow)
   * @since IDW 1.4.0
   */
  public void minimizeWithAbort() throws OperationAbortedException {
    if (!isMinimized() && getRootWindow().getClosestWindowBar(this) != null) {
      fireWindowMinimizing(this);
      minimize();
    }
  }

  /**
   * Same as {@link #minimize(Direction)}, but the {@link net.infonode.docking.DockingWindowListener#windowMinimizing(DockingWindow)} method of
   * the window listeners will be called before minimizing the window, giving them the possibility to abort the minimize
   * operation.
   *
   * @throws net.infonode.docking.OperationAbortedException if the minimize operation was aborted by a window listener
   * @see #minimize(Direction)
   * @see DockingWindowListener#windowMinimizing(DockingWindow)
   * @see DockingWindowListener#windowMinimizing(DockingWindow)
   * @since IDW 1.4.0
   * @param direction a {@link net.infonode.util.Direction} object.
   */
  public void minimizeWithAbort(Direction direction) throws OperationAbortedException {
    if (!isMinimized() && getRootWindow().getWindowBar(direction) != null) {
      fireWindowMinimizing(this);
      minimize(direction);
    }
  }

  private void doMinimize() {
    doMinimize(windowItem.getLastMinimizedDirection() != null &&
               getRootWindow().getWindowBar(windowItem.getLastMinimizedDirection()).isEnabled() ?
                                                                                                windowItem
                                                                                                    .getLastMinimizedDirection() :
                                                                                                                                 getRootWindow()
                                                                                                                                     .getClosestWindowBar(
                                                                                                                                         this));
  }

  private void doMinimize(Direction direction) {
    DockingWindow w = getOptimizedWindow();

    if (direction == null || w.isMinimized())
      return;

    WindowBar bar = getRootWindow().getWindowBar(direction);

    if (bar != null) {
      bar.addTab(w);
      updateButtonVisibility();
    }
  }

  /**
   * Returns true if this window can be minimized by the user.
   *
   * @return true if this window can be minimized
   * @see #minimize()
   */
  public boolean isMinimizable() {
    return getOptimizedWindow().getWindowProperties().getMinimizeEnabled() &&
           !isUndocked() &&
           getRootWindow() != null &&
           getRootWindow().windowBarEnabled();
  }

  /**
   * Returns true if this window can be maximized by the user.
   *
   * @return true if this window can be maximized
   * @see #maximize()
   * @since IDW 1.2.0
   */
  public boolean isMaximizable() {
    return !isMinimized() && /*!isUndocked() &&*/ getOptimizedWindow().getWindowProperties().getMaximizeEnabled();
  }

  /**
   * Returns true if this window can be closed by the user.
   *
   * @return true if this window can be closed
   * @see #close()
   * @see #closeWithAbort()
   * @see #close()
   * @see #closeWithAbort()
   * @since IDW 1.2.0
   */
  public boolean isClosable() {
    return getOptimizedWindow().getWindowProperties().getCloseEnabled();
  }

  /**
   * Returns true if this window can be restored by the user.
   *
   * @return true if this window can be restored
   * @see #restore()
   * @since IDW 1.2.0
   */
  public boolean isRestorable() {
    return getOptimizedWindow().getWindowProperties().getRestoreEnabled();
  }

  /**
   * Returns true if this window can be undocked to a floating window.
   *
   * @return true if this window can be undocked
   * @see #undock(Point)
   * @since IDW 1.4.0
   */
  public boolean isUndockable() {
    return getOptimizedWindow().getWindowProperties().getUndockEnabled();
  }

  /**
   * Returns true if this window can be docked to the root window from a floating window.
   *
   * @return true if this window can be docked
   * @see #dock()
   * @since IDW 1.4.0
   */
  public boolean isDockable() {
    return getOptimizedWindow().getWindowProperties().getDockEnabled();
  }

  /**
   * Replaces a child window with another window.
   *
   * @param oldWindow the child window to replaceChildWindow
   * @param newWindow the window to replaceChildWindow it with
   */
  public void replaceChildWindow(DockingWindow oldWindow,
                                 DockingWindow newWindow) {
    if (oldWindow == newWindow)
      return;

    DockingWindow nw = internalReplaceChildWindow(oldWindow, newWindow);

    if (getUpdateModel()) {
      boolean isRestore = nw.getWindowItem().isRestoreWindow();
      oldWindow.windowItem.replaceWith(nw.getWindowItem());

      if (!isRestore)
        nw.updateWindowItems();

      cleanUpModel();
    }
  }

  /**
   * <p>internalReplaceChildWindow.</p>
   *
   * @param oldWindow a {@link net.infonode.docking.DockingWindow} object.
   * @param newWindow a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.docking.DockingWindow} object.
   */
  protected DockingWindow internalReplaceChildWindow(final DockingWindow oldWindow,
                                                     final DockingWindow newWindow) {
    final WindowAncestors oldAncestors = newWindow.storeAncestors();
    final DockingWindow nw = newWindow.getContentWindow(DockingWindow.this);

    optimizeAfter(newWindow, new Runnable() {
      public void run() {
        if (nw == oldWindow)
          return;

        if (nw.getWindowParent() != null)
          nw.getWindowParent().removeChildWindow(nw);

        nw.setWindowParent(DockingWindow.this);

        if (oldWindow.isShowingInRootWindow())
          oldWindow.fireWindowHidden(oldWindow);

        oldWindow.setWindowParent(null);

        if (oldWindow == lastFocusedChildWindow)
          lastFocusedChildWindow = null;

        doReplace(oldWindow, nw);

        fireTitleChanged();

        oldWindow.fireWindowRemoved(DockingWindow.this, oldWindow);
        fireWindowRemoved(DockingWindow.this, oldWindow);
        nw.fireWindowAdded(DockingWindow.this, nw);

        if (nw.isShowingInRootWindow())
          nw.fireWindowShown(nw);

        newWindow.notifyListeners(oldAncestors);
      }
    });

    return nw;
  }

  /**
   * Returns the title of this window.
   *
   * @return the window title
   */
  public String getTitle() {
    DockingWindowTitleProvider titleProvider = getWindowProperties().getTitleProvider();
    return (titleProvider == null ? SimpleDockingWindowTitleProvider.INSTANCE : titleProvider).getTitle(this);
  }

  /**
   * <p>toString.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return getTitle();
  }

  /**
   * <p>storeAncestors.</p>
   *
   * @return a {@link net.infonode.docking.internal.WindowAncestors} object.
   */
  protected WindowAncestors storeAncestors() {
    return new WindowAncestors(getAncestors(), isMinimized(), isUndocked());
  }

  /**
   * <p>notifyListeners.</p>
   *
   * @param ancestors a {@link net.infonode.docking.internal.WindowAncestors} object.
   */
  protected void notifyListeners(WindowAncestors ancestors) {
    if (isMinimized() && !ancestors.isMinimized())
      fireWindowMinimized(this, ancestors.getAncestors());

    if (isUndocked() && !ancestors.isUndocked())
      fireWindowUndocked(this, ancestors.getAncestors());

    if (!isUndocked() && ancestors.isUndocked())
      fireWindowDocked(this, ancestors.getAncestors());
  }

  /**
   * <p>isShowingInRootWindow.</p>
   *
   * @return a boolean.
   */
  protected boolean isShowingInRootWindow() {
    return windowParent != null && windowParent.isChildShowingInRootWindow(this);
  }

  /**
   * <p>isChildShowingInRootWindow.</p>
   *
   * @param child a {@link net.infonode.docking.DockingWindow} object.
   * @return a boolean.
   */
  protected boolean isChildShowingInRootWindow(DockingWindow child) {
    return isShowingInRootWindow();
  }

  /**
   * Makes this window visible. This causes the tabs of all {@link net.infonode.docking.TabWindow} parents containing this
   * window to be selected.
   *
   * @since IDW 1.1.0
   */
  public void makeVisible() {
    showChildWindow(null);
  }

  /**
   * Requests that the last focused child window becomes visible and that focus is restored to the last focused
   * component in that window. If no child window has had focus or the child window has been removed from this window,
   * focus is transferred to a child component of this window.
   *
   * @since IDW 1.1.0
   */
  public void restoreFocus() {
    if (lastFocusedChildWindow != null)
      lastFocusedChildWindow.restoreFocus();
    else {
      DockingWindow w = getPreferredFocusChild();

      if (w != null)
        w.restoreFocus();
      else
        ComponentUtil.smartRequestFocus(this);
    }
  }

  /**
   * <p>getPreferredFocusChild.</p>
   *
   * @return a {@link net.infonode.docking.DockingWindow} object.
   */
  protected DockingWindow getPreferredFocusChild() {
    return getChildWindowCount() > 0 ? getChildWindow(0) : null;
  }

  /**
   * Returns the result after removing unnecessary tab windows which contains only one tab.
   *
   * @return the result after removing unnecessary tab windows which contains only one tab
   */
  protected DockingWindow getOptimizedWindow() {
    return this;
  }

  /**
   * <p>getBestFittedWindow.</p>
   *
   * @param parentWindow a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.docking.DockingWindow} object.
   */
  protected DockingWindow getBestFittedWindow(DockingWindow parentWindow) {
    return this;
  }

  /**
   * <p>internalClose.</p>
   */
  protected void internalClose() {
    optimizeAfter(windowParent, new Runnable() {
      public void run() {
        windowParent.removeChildWindow(DockingWindow.this);
      }
    });
  }

  /**
   * <p>showChildWindow.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  protected void showChildWindow(DockingWindow window) {
    if (windowParent != null && !isMaximized())
      windowParent.showChildWindow(this);
  }

  /**
   * <p>insideTab.</p>
   *
   * @return true if this window is inside a tab __exclude__
   */
  protected boolean insideTab() {
    return windowParent == null ? false : windowParent.childInsideTab();
  }

  /**
   * <p>childInsideTab.</p>
   *
   * @return true if the child windows are inside tabs __exclude__
   */
  protected boolean childInsideTab() {
    return windowParent == null ? false : windowParent.childInsideTab();
  }

  /**
   * <p>getAncestors.</p>
   *
   * @return an array of {@link net.infonode.docking.DockingWindow} objects.
   */
  protected DockingWindow[] getAncestors() {
    DockingWindow w = this;
    int count = 0;

    while (w != null) {
      w = w.getWindowParent();
      count++;
    }

    DockingWindow[] windows = new DockingWindow[count];
    w = this;

    while (w != null) {
      windows[--count] = w;
      w = w.getWindowParent();
    }

    return windows;
  }


  private void fireWindowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowRemoved(removedFromWindow, removedWindow);
    }

    if (windowParent != null)
      windowParent.fireWindowRemoved(removedFromWindow, removedWindow);
  }

  /**
   * <p>fireWindowShown.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  protected void fireWindowShown(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowShown(window);
    }

    if (windowParent != null)
      windowParent.fireWindowShown(window);
  }

  /**
   * <p>fireViewFocusChanged.</p>
   *
   * @param previouslyFocusedView a {@link net.infonode.docking.View} object.
   * @param focusedView a {@link net.infonode.docking.View} object.
   */
  protected void fireViewFocusChanged(View previouslyFocusedView, View focusedView) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].viewFocusChanged(previouslyFocusedView, focusedView);
    }
  }

  /**
   * <p>fireWindowHidden.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  protected void fireWindowHidden(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowHidden(window);
    }

    if (windowParent != null)
      windowParent.fireWindowHidden(window);
  }

  private void fireWindowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowAdded(addedToWindow, addedWindow);
    }

    if (windowParent != null)
      windowParent.fireWindowAdded(addedToWindow, addedWindow);
  }


  private void fireWindowClosing(DockingWindow window) throws OperationAbortedException {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowClosing(window);
    }

    if (windowParent != null)
      windowParent.fireWindowClosing(window);
  }

  private void fireWindowClosed(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowClosed(window);
    }
  }

  void fireWindowUndocking(DockingWindow window) throws OperationAbortedException {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowUndocking(window);
    }

    if (windowParent != null)
      windowParent.fireWindowUndocking(window);
  }

  void fireWindowUndocked(DockingWindow window, DockingWindow[] oldAncestors) {
    doFireWindowUndocked(window);

    for (int i = oldAncestors.length - 1; i >= 0; i--)
      oldAncestors[i].doFireWindowUndocked(this);
  }

  private void doFireWindowUndocked(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowUndocked(window);
    }
  }

  void fireWindowMinimizing(DockingWindow window) throws OperationAbortedException {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowMinimizing(window);
    }

    if (windowParent != null)
      windowParent.fireWindowMinimizing(window);
  }

  void fireWindowMaximizing(DockingWindow window) throws OperationAbortedException {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowMaximizing(window);
    }

    if (windowParent != null)
      windowParent.fireWindowMaximizing(window);
  }

  void fireWindowRestoring(DockingWindow window) throws OperationAbortedException {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowRestoring(window);
    }

    if (windowParent != null)
      windowParent.fireWindowRestoring(window);
  }

  void fireWindowDocking(DockingWindow window) throws OperationAbortedException {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowDocking(window);
    }

    if (windowParent != null)
      windowParent.fireWindowDocking(window);
  }

  void fireWindowDocked(DockingWindow window, DockingWindow[] oldAncestors) {
    doFireWindowDocked(window);

    for (int i = oldAncestors.length - 1; i >= 0; i--)
      oldAncestors[i].doFireWindowDocked(this);
  }

  void fireWindowDocked(ArrayList dockedViews) {
    for (int i = 0; i < dockedViews.size(); i++) {
      DockingWindow window = (DockingWindow) dockedViews.get(i);
      window.doFireWindowDocked(window);

      DockingWindow[] ancestors = window.getAncestors();
      for (int k = 0; k < ancestors.length; k++)
        ancestors[k].doFireWindowDocked(window);
    }
  }

  private void doFireWindowDocked(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowDocked(window);
    }
  }

  private void doFireWindowRestored(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowRestored(window);
    }
  }

  void fireWindowMaximized(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowMaximized(window);
    }

    if (windowParent != null)
      windowParent.fireWindowMaximized(window);
  }

  void fireWindowMinimized(DockingWindow window, DockingWindow[] oldAncestors) {
    doFireWindowMinimized(window);

    for (int i = oldAncestors.length - 1; i >= 0; i--)
      oldAncestors[i].doFireWindowMinimized(window);
  }

  private void doFireWindowMinimized(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowMinimized(window);
    }
  }

  void fireWindowRestored(DockingWindow window) {
    if (getListeners() != null) {
      DockingWindowListener[] l = (DockingWindowListener[]) getListeners().toArray(
          new DockingWindowListener[getListeners().size()]);

      for (int i = 0; i < l.length; i++)
        l[i].windowRestored(window);
    }

    if (windowParent != null)
      windowParent.fireWindowRestored(window);
  }

  /**
   * <p>setLastMinimizedDirection.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   */
  protected void setLastMinimizedDirection(Direction direction) {
    windowItem.setLastMinimizedDirection(direction);
  }

  /**
   * <p>clearChildrenFocus.</p>
   *
   * @param child a {@link net.infonode.docking.DockingWindow} object.
   * @param view a {@link net.infonode.docking.View} object.
   */
  protected void clearChildrenFocus(DockingWindow child, View view) {
    for (int i = 0; i < getChildWindowCount(); i++)
      if (child != getChildWindow(i))
        getChildWindow(i).clearFocus(view);
  }

  void childGainedFocus(DockingWindow child, View view) {
    if (child != null)
      lastFocusedChildWindow = child;

    clearChildrenFocus(child, view);

    if (windowParent != null)
      windowParent.childGainedFocus(this, view);
  }

  WindowTab getTab() {
    if (tab == null) {
      tab = new WindowTab(this, false);
    }

    return tab;
  }

  /**
   * <p>childRemoved.</p>
   *
   * @param child a {@link net.infonode.docking.DockingWindow} object.
   */
  protected void childRemoved(DockingWindow child) {
    if (lastFocusedChildWindow == child)
      lastFocusedChildWindow = null;
  }

  /**
   * <p>updateButtonVisibility.</p>
   */
  protected void updateButtonVisibility() {
    if (tab != null)
      tab.updateTabButtons(null);

    for (int i = 0; i < getChildWindowCount(); i++)
      getChildWindow(i).updateButtonVisibility();
  }

  /**
   * <p>readLocations.</p>
   *
   * @param in a {@link java.io.ObjectInputStream} object.
   * @param rootWindow a {@link net.infonode.docking.RootWindow} object.
   * @param version a int.
   * @throws java.io.IOException if any.
   */
  protected final void readLocations(ObjectInputStream in, RootWindow rootWindow,
                                     int version) throws IOException {
    if (version < 3)
      LocationDecoder.decode(in, rootWindow);  // Just skip location

    if (version > 1) {
      int index = in.readInt();
      lastFocusedChildWindow = index == -1 ? null : getChildWindow(index);
    }

    for (int i = 0; i < getChildWindowCount(); i++)
      getChildWindow(i).readLocations(in, rootWindow, version);
  }

  /**
   * <p>writeLocations.</p>
   *
   * @param out a {@link java.io.ObjectOutputStream} object.
   * @throws java.io.IOException if any.
   */
  protected void writeLocations(ObjectOutputStream out) throws IOException {
    out.writeInt(lastFocusedChildWindow == null ? -1 : getChildWindowIndex(lastFocusedChildWindow));

    for (int i = 0; i < getChildWindowCount(); i++)
      getChildWindow(i).writeLocations(out);
  }

  /**
   * <p>beginOptimize.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  protected static void beginOptimize(DockingWindow window) {
    optimizeDepth++;

    if (window != null)
      optimizeWindows.add(window);

    PropertyMapManager.getInstance().beginBatch();
  }

  /**
   * <p>endOptimize.</p>
   */
  protected static void endOptimize() {
    PropertyMapManager.getInstance().endBatch();

    if (--optimizeDepth == 0) {
      while (optimizeWindows.size() > 0) {
        HashSet s = optimizeWindows;
        optimizeWindows = new HashSet();

        for (Iterator it = s.iterator(); it.hasNext();) {
          DockingWindow window = (DockingWindow) it.next();
          window.optimizeWindowLayout();
        }
      }
    }
  }

  /**
   * <p>optimizeAfter.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param runnable a {@link java.lang.Runnable} object.
   */
  protected static void optimizeAfter(final DockingWindow window, final Runnable runnable) {
    FocusManager.getInstance().pinFocus(new Runnable() {
      public void run() {
        beginOptimize(window);

        try {
          runnable.run();
        }
        finally {
          endOptimize();
        }
      }
    });
  }

  /**
   * <p>needsTitleWindow.</p>
   *
   * @return a boolean.
   */
  protected boolean needsTitleWindow() {
    return false;
  }

  /**
   * <p>showsWindowTitle.</p>
   *
   * @return a boolean.
   */
  protected boolean showsWindowTitle() {
    return false;
  }

  /**
   * <p>optimizeWindowLayout.</p>
   */
  protected void optimizeWindowLayout() {
  }

  /**
   * <p>getLocationWindow.</p>
   *
   * @return a {@link net.infonode.docking.DockingWindow} object.
   */
  protected DockingWindow getLocationWindow() {
    return this;
  }

  /**
   * <p>fireTitleChanged.</p>
   */
  protected void fireTitleChanged() {
    if (tab != null)
      tab.windowTitleChanged();

    if (windowParent != null)
      windowParent.fireTitleChanged();
  }

  /**
   * <p>getContentWindow.</p>
   *
   * @param parent a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.docking.DockingWindow} object.
   */
  protected DockingWindow getContentWindow(DockingWindow parent) {
    return needsTitleWindow() && !parent.showsWindowTitle() ? new TabWindow(this) : this;
  }

  /**
   * <p>removeChildWindow.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  protected final void removeChildWindow(final DockingWindow window) {
    optimizeAfter(window.getWindowParent(), new Runnable() {
      public void run() {
        if (window.isShowingInRootWindow())
          window.fireWindowHidden(window);

        window.setWindowParent(null);

        if (lastFocusedChildWindow == window)
          lastFocusedChildWindow = null;

        doRemoveWindow(window);
        fireTitleChanged();
        window.fireWindowRemoved(DockingWindow.this, window);
        fireWindowRemoved(DockingWindow.this, window);
        afterWindowRemoved(window);
      }
    });
  }

  /**
   * <p>removeWindow.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  final protected void removeWindow(DockingWindow window) {
    window.setWindowParent(null);

    if (getUpdateModel()) {
      windowItem.removeWindow(windowItem.getChildWindowContaining(window.getWindowItem()));
      cleanUpModel();
    }
  }

  /**
   * <p>detach.</p>
   */
  final protected void detach() {
    DockingWindow oldParent = getWindowParent();

    if (oldParent != null)
      oldParent.removeChildWindow(this);
  }

  /**
   * <p>addWindow.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.docking.DockingWindow} object.
   */
  final protected DockingWindow addWindow(DockingWindow window) {
    if (window == null)
      return null;

    DockingWindow w = window.getContentWindow(this);
    w.detach();
    w.setWindowParent(this);
    fireTitleChanged();
    w.fireWindowAdded(this, w);

    if (w.isShowingInRootWindow())
      fireWindowShown(w);

    return w;
  }

  /**
   * <p>rootChanged.</p>
   *
   * @param oldRoot a {@link net.infonode.docking.RootWindow} object.
   * @param newRoot a {@link net.infonode.docking.RootWindow} object.
   */
  protected void rootChanged(RootWindow oldRoot, RootWindow newRoot) {
    if (newRoot != null)
      lastRootWindow = new WeakReference(newRoot);

    for (int i = 0; i < getChildWindowCount(); i++)
      if (getChildWindow(i) != null)
        getChildWindow(i).rootChanged(oldRoot, newRoot);

    updateWindowItem(newRoot);
  }

  /**
   * <p>clearFocus.</p>
   *
   * @param view a {@link net.infonode.docking.View} object.
   */
  protected void clearFocus(View view) {
    for (int i = 0; i < getChildWindowCount(); i++)
      getChildWindow(i).clearFocus(view);
  }

  private void setWindowParent(DockingWindow window) {
    if (window == windowParent)
      return;

    final RootWindow oldRoot = getRootWindow();

    if (windowParent != null) {
      if (isMaximized()) {
        if (isUndocked())
          DockingUtil.getFloatingWindowFor(this).setMaximizedWindow(null);
        else
          getRootWindow().setMaximizedWindow(null);
      }

      windowParent.childRemoved(this);
      clearFocus(null);

      if (tab != null)
        tab.setContentComponent(this);
    }

    windowParent = window;
    final RootWindow newRoot = getRootWindow();

    if (oldRoot != newRoot) {
      rootChanged(oldRoot, newRoot);
    }
  }

  private Direction getSplitDirection(Point p) {
    double[] relativeDist = {p.getX() / getWidth(),
                             (getWidth() - p.getX()) / getWidth(), p.getY() / getHeight(),
                             (getHeight() - p.getY()) / getHeight()};
    int index = ArrayUtil.findSmallest(relativeDist);
    return index == 0 ? Direction.LEFT : index == 1 ? Direction.RIGHT : index == 2 ? Direction.UP : Direction.DOWN;
  }

  private int getEdgeDistance(Point p, Direction dir) {
    return dir == Direction.RIGHT ? getWidth() - p.x :
           dir == Direction.DOWN ? getHeight() - p.y :
           dir == Direction.LEFT ? p.x :
           p.y;
  }

  DropAction acceptDrop(Point p, DockingWindow window) {
    DropAction da = null;
    DockingWindow fw = DockingUtil.getFloatingWindowFor(window);
    DockingWindow fw2 = DockingUtil.getFloatingWindowFor(this);

    // Check getRootWindow() != window.getRootWindow() so that a view not insidedrop window's root window can be dragged to a floating window
    if (getRootWindow() != window.getRootWindow() ||
        ((window.getWindowProperties().getDockEnabled() || fw == null || fw2 != null) &&
         (window.getWindowProperties().getUndockEnabled() || (fw == fw2)))) {
      da = !isShowing() || !contains(p) || hasParent(window) || (!getRootWindow().getRootWindowProperties()
          .getRecursiveTabsEnabled() && insideTab()) ?
                                                     null : doAcceptDrop(p, window);
    }
    //System.out.println(!isShowing() + "  " + !contains(p) + "  " + hasParent(window) + "  " + (!getRootWindow().getRootWindowProperties().getRecursiveTabsEnabled() && insideTab()));
    //System.out.println(" \n ----- Accept drop: " + (this instanceof
    // SplitWindow));
    //System.out.println("isSho " + !isShowing() + " contan " +
    // !contains(p) + " parent " + hasParent(window) + " bla " +
    // (!getRootWindow().getRootWindowProperties().getRecursiveTabsEnabled()
    // && insideTab()));
    //}

    return da;
  }

  DropAction getDefaultDropAction() {
    return new DropAction() {
      public void execute(DockingWindow window, MouseEvent mouseEvent) {
        if (getWindowProperties().getUndockEnabled() && getWindowProperties().getUndockOnDropEnabled()) {
          Point p = mouseEvent.getPoint();
          Point p2 = SwingUtilities.convertPoint((Component) mouseEvent.getSource(), p, getRootWindow());
          Point p3 = SwingUtilities.convertPoint((Component) mouseEvent.getSource(), p, getRootWindow().getRootPane());
          if (!getRootWindow().contains(p2) && !getRootWindow().floatingWindowsContainPoint(p3)) {
            FloatingWindow fw = DockingUtil.getFloatingWindowFor(window);

            if (fw == null || (fw.getChildWindowCount() > 0 && fw.getChildWindow(0).getChildWindowCount() > 1)) {
              SwingUtilities.convertPointToScreen(p, (Component) mouseEvent.getSource());
              p.x = p.x - window.getWidth() / 2;
              p.y = p.y - Math.min(DROP_FLOATING_YOFFSET, window.getHeight() / 2);
              try {
                window.undockWithAbort(p);
              }
              catch (OperationAbortedException e) {
                // Ignore
              }
            }
          }
        }
      }
    };
  }

  /**
   * <p>acceptsSplitWith.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @return a boolean.
   */
  protected boolean acceptsSplitWith(DockingWindow window) {
    return window != this;
  }

  /**
   * <p>doAcceptDrop.</p>
   *
   * @param p a {@link java.awt.Point} object.
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.docking.internalutil.DropAction} object.
   */
  protected DropAction doAcceptDrop(Point p, DockingWindow window) {
    DropAction da = acceptSplitDrop(p, window, getRootWindow().getRootWindowProperties().getEdgeSplitDistance());

    if (da != null)
      return da;

    da = acceptChildDrop(p, window);

    if (da != null)
      return da;

    da = acceptInteriorDrop(p, window);

    if (da != null)
      return da;

    return acceptSplitDrop(p, window, -1);
  }

  /**
   * <p>acceptSplitDrop.</p>
   *
   * @param p a {@link java.awt.Point} object.
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param splitDistance a int.
   * @return a {@link net.infonode.docking.internalutil.DropAction} object.
   */
  protected DropAction acceptSplitDrop(Point p, DockingWindow window, int splitDistance) {
    if (!acceptsSplitWith(window))
      return null;

    Direction splitDir = getSplitDirection(p);
    int dist = getEdgeDistance(p, splitDir);

    if (splitDistance != -1 && dist > splitDistance * getEdgeDepth(splitDir))
      return null;

    if (getSplitDropFilter().acceptDrop(new SplitDropInfo(window, this, p, splitDir)))
      return split(window, splitDir);

    return null;
  }

  /**
   * <p>split.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param splitDir a {@link net.infonode.util.Direction} object.
   * @return a {@link net.infonode.docking.internalutil.DropAction} object.
   */
  protected DropAction split(DockingWindow window, final Direction splitDir) {
    int width = splitDir == Direction.LEFT || splitDir == Direction.RIGHT ? getWidth() / 3 : getWidth();
    int height = splitDir == Direction.DOWN || splitDir == Direction.UP ? getHeight() / 3 : getHeight();
    int x = splitDir == Direction.RIGHT ? getWidth() - width : 0;
    int y = splitDir == Direction.DOWN ? getHeight() - height : 0;

    Rectangle rect = new Rectangle(x, y, width, height);
    getRootWindow().setDragRectangle(SwingUtilities.convertRectangle(this, rect, getRootWindow()));

    return new DropAction() {
      public void execute(DockingWindow window, MouseEvent mouseEvent) {
        try {
          window.beforeDrop(DockingWindow.this);
          split(window, splitDir, splitDir == Direction.UP || splitDir == Direction.LEFT ? 0.33f : 0.66f);
          window.restoreFocus();
        }
        catch (OperationAbortedException e) {
          // Ignore
        }
      }
    };
  }

  /**
   * <p>beforeDrop.</p>
   *
   * @param target a {@link net.infonode.docking.DockingWindow} object.
   * @throws net.infonode.docking.OperationAbortedException if any.
   */
  protected void beforeDrop(DockingWindow target) throws OperationAbortedException {
    if (!isMinimized() && target.isMinimized())
      fireWindowMinimizing(this);

    if (!isUndocked() && target.isUndocked())
      fireWindowUndocking(this);
  }

  /**
   * <p>createTabWindow.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.docking.internalutil.DropAction} object.
   */
  protected DropAction createTabWindow(DockingWindow window) {
    getRootWindow().setDragRectangle(SwingUtilities.convertRectangle(getParent(), getBounds(), getRootWindow()));

    return new DropAction() {
      public void execute(final DockingWindow window, MouseEvent mouseEvent) {
        optimizeAfter(window.getWindowParent(), new Runnable() {
          public void run() {
            try {
              window.beforeDrop(DockingWindow.this);
              TabWindow tabWindow = new TabWindow();
              windowParent.replaceChildWindow(DockingWindow.this, tabWindow);
              tabWindow.addTab(DockingWindow.this);
              tabWindow.addTab(window);
            }
            catch (OperationAbortedException e) {
              // Ignore
            }
          }
        });
      }
    };
  }

  /**
   * <p>acceptInteriorDrop.</p>
   *
   * @param p a {@link java.awt.Point} object.
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.docking.internalutil.DropAction} object.
   */
  protected DropAction acceptInteriorDrop(Point p, DockingWindow window) {
    return null;
  }

  /**
   * <p>hasParent.</p>
   *
   * @param w a {@link net.infonode.docking.DockingWindow} object.
   * @return a boolean.
   */
  protected boolean hasParent(DockingWindow w) {
    return w == this || (getWindowParent() != null && getWindowParent().hasParent(w));
  }

  /**
   * <p>oldRead.</p>
   *
   * @param in a {@link java.io.ObjectInputStream} object.
   * @param context a {@link net.infonode.docking.internal.ReadContext} object.
   * @return a {@link net.infonode.docking.DockingWindow} object.
   * @throws java.io.IOException if any.
   */
  protected DockingWindow oldRead(ObjectInputStream in, ReadContext context) throws IOException {
    windowItem.readSettings(in, context);
    return this;
  }

  /**
   * <p>getPropertyObject.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.PropertyMap} object.
   */
  abstract protected PropertyMap getPropertyObject();

  /**
   * <p>createPropertyObject.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.PropertyMap} object.
   */
  abstract protected PropertyMap createPropertyObject();

  void showPopupMenu(MouseEvent event) {
    if (event.isConsumed())
      return;

    DockingWindow w = this;

    while (w.getPopupMenuFactory() == null) {
      w = w.getWindowParent();

      if (w == null)
        return;
    }

    JPopupMenu popupMenu = w.getPopupMenuFactory().createPopupMenu(this);

    if (popupMenu != null && popupMenu.getComponentCount() > 0)
      popupMenu.show(event.getComponent(), event.getX(), event.getY());
  }

  /**
   * <p>setFocused.</p>
   *
   * @param focused a boolean.
   */
  protected void setFocused(boolean focused) {
    if (tab != null)
      tab.setFocused(focused);
  }

  /**
   * <p>getEdgeDepth.</p>
   *
   * @param dir a {@link net.infonode.util.Direction} object.
   * @return a int.
   */
  protected int getEdgeDepth(Direction dir) {
    return 1 + (windowParent == null ? 0 : windowParent.getChildEdgeDepth(this, dir));
  }

  /**
   * <p>getChildEdgeDepth.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param dir a {@link net.infonode.util.Direction} object.
   * @return a int.
   */
  protected int getChildEdgeDepth(DockingWindow window, Direction dir) {
    return windowParent == null ? 0 : windowParent.getChildEdgeDepth(this, dir);
  }

  /**
   * <p>acceptChildDrop.</p>
   *
   * @param p a {@link java.awt.Point} object.
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @return a {@link net.infonode.docking.internalutil.DropAction} object.
   */
  protected DropAction acceptChildDrop(Point p, DockingWindow window) {
    for (int i = 0; i < getChildWindowCount(); i++) {
      DockingWindow childWindow = getChildWindow(i);
      Point p2 = SwingUtilities.convertPoint(this, p, childWindow);

      if (getChildDropFilter().acceptDrop(new ChildDropInfo(window, this, p, childWindow))) {
        DropAction da = childWindow.acceptDrop(p2, window);

        if (da != null)
          return da;
      }
    }

    return null;
  }

  /**
   * <p>Getter for the field <code>windowItem</code>.</p>
   *
   * @return a {@link net.infonode.docking.model.WindowItem} object.
   */
  protected WindowItem getWindowItem() {
    return windowItem;
  }

  /**
   * <p>getUpdateModel.</p>
   *
   * @return a boolean.
   */
  protected boolean getUpdateModel() {
    return updateModelDepth == 0 && windowItem.isRestoreWindow();
  }

  private void findViews(ArrayList views) {
    if (this instanceof View)
      views.add(this);

    for (int i = 0; i < getChildWindowCount(); i++)
      getChildWindow(i).findViews(views);
  }

  private void restoreViews(ArrayList views) {
    for (int i = 0; i < views.size(); i++)
      ((DockingWindow) views.get(i)).restoreItem();
  }

  /**
   * <p>beginUpdateModel.</p>
   */
  protected static void beginUpdateModel() {
    updateModelDepth++;
  }

  /**
   * <p>endUpdateModel.</p>
   */
  protected static void endUpdateModel() {
    updateModelDepth--;
  }

  private void restoreItem() {
    beginUpdateModel();

    try {
      if (windowItem != null) {
        WindowItem item = windowItem;

        while (item.getParent() != null) {
          DockingWindow parentWindow = item.getParent().getConnectedWindow();

          if (parentWindow != null && parentWindow.getRootWindow() != null && !parentWindow.isMinimized() &&
              !parentWindow.isUndocked()) {
            if (parentWindow instanceof TabWindow)
              insertTab((TabWindow) parentWindow, this);
            else if (parentWindow instanceof RootWindow) {
              DockingWindow w = getContainer(item.getParent(), windowItem);
              ((RootWindow) parentWindow).setWindow(w);
            }

            return;
          }
          else {
            DockingWindow w = null;

            for (int i = 0; i < item.getParent().getWindowCount(); i++) {
              WindowItem child = item.getParent().getWindow(i);

              if (child != item) {
                w = child.getVisibleDockingWindow();

                if (w != null)
                  break;
              }
            }

            if (w != null) {
              final DockingWindow w1 = w;
              final WindowItem fitem = item;

              optimizeAfter(w.getWindowParent(), new Runnable() {
                public void run() {
                  if (fitem.getParent() instanceof SplitWindowItem) {
                    SplitWindowItem splitWindowItem = (SplitWindowItem) fitem.getParent();
                    boolean isLeft = splitWindowItem.getWindow(0) == fitem;
                    SplitWindow newWindow = new SplitWindow(splitWindowItem.isHorizontal(),
                                                            splitWindowItem.getDividerLocation(),
                                                            null,
                                                            null,
                                                            splitWindowItem);
                    w1.getWindowParent().internalReplaceChildWindow(w1, newWindow);
                    DockingWindow w = getContainer(splitWindowItem, windowItem);
                    DockingWindow w2 = w1.getContainer(splitWindowItem, w1.windowItem);
                    newWindow.setWindows(isLeft ? w : w2, isLeft ? w2 : w);
                  }
                  else if (fitem.getParent() instanceof TabWindowItem) {
                    TabWindowItem tabWindowItem = (TabWindowItem) fitem.getParent();
                    TabWindow newWindow = new TabWindow(null, tabWindowItem);
                    w1.getWindowParent().internalReplaceChildWindow(w1, newWindow);
                    insertTab(newWindow, DockingWindow.this);
                    insertTab(newWindow, w1.getOptimizedWindow());
                  }
                }
              });

              return;
            }
          }

          item = item.getParent();
        }
      }

      final RootWindow rootWindow = (RootWindow) lastRootWindow.get();

      if (rootWindow != null) {
        final WindowItem topItem = getWindowItem().getTopItem();

        optimizeAfter(null, new Runnable() {
          public void run() {
            DockingWindow w = rootWindow.getWindow();

            if (w == null) {
              WindowItem wi = rootWindow.getWindowItem();

              if (wi.getWindowCount() == 0)
                wi.addWindow(topItem);
              else {
                SplitWindowItem splitWindowItem = new SplitWindowItem();
                splitWindowItem.addWindow(wi.getWindow(0));
                splitWindowItem.addWindow(topItem);
                wi.addWindow(splitWindowItem);
              }

              rootWindow.setWindow(getContainer(topItem, getWindowItem()));
            }
            else {
              SplitWindow newWindow = new SplitWindow(true);
              newWindow.getWindowItem().addWindow(rootWindow.getWindowItem().getWindow(0));
              newWindow.getWindowItem().addWindow(topItem);
              rootWindow.setWindow(newWindow);
              newWindow.setWindows(w, getContainer(topItem, getWindowItem()));
              rootWindow.getWindowItem().addWindow(newWindow.getWindowItem());
            }
          }
        });
      }
    }
    finally {
      endUpdateModel();
    }
  }

  private static void insertTab(TabWindow tabWindow, DockingWindow window) {
    int index = 0;
    WindowItem item = tabWindow.getWindowItem();
    WindowItem childItem = item.getChildWindowContaining(window.getWindowItem());

    for (int i = 0; i < item.getWindowCount(); i++) {
      WindowItem wi = item.getWindow(i);

      if (wi == childItem)
        break;

      DockingWindow w = wi.getVisibleDockingWindow();

      if (w != null)
        index++;
    }

    tabWindow.addTabNoSelect(window, index);
    tabWindow.updateSelectedTab();
  }

  private DockingWindow getContainer(WindowItem topItem, WindowItem item) {
    if (!needsTitleWindow())
      return this;

    while (item != topItem) {
      if (item instanceof TabWindowItem) {
        TabWindow w = new TabWindow(null, (TabWindowItem) item);
        w.addTabNoSelect(this, 0);
        return w;
      }

      item = item.getParent();
    }

    TabWindow w = new TabWindow();
    w.addTabNoSelect(this, 0);
    item.replaceWith(w.getWindowItem());
    w.getWindowItem().addWindow(item);
    return w;
  }

  private void setWindowItem(WindowItem windowItem) {
    this.windowItem = windowItem;
    windowItem.setConnectedWindow(this);
    updateWindowItem(getRootWindow());
  }

  /**
   * <p>updateWindowItem.</p>
   *
   * @param rootWindow a {@link net.infonode.docking.RootWindow} object.
   */
  protected void updateWindowItem(RootWindow rootWindow) {
    windowItem.setParentDockingWindowProperties(rootWindow == null ?
                                                WindowItem.emptyProperties :
                                                rootWindow.getRootWindowProperties().getDockingWindowProperties());
  }

  /**
   * <p>afterWindowRemoved.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   */
  protected void afterWindowRemoved(DockingWindow window) {
  }

  /**
   * <p>write.</p>
   *
   * @param out a {@link java.io.ObjectOutputStream} object.
   * @param context a {@link net.infonode.docking.internal.WriteContext} object.
   * @param viewWriter a {@link net.infonode.docking.model.ViewWriter} object.
   * @throws java.io.IOException if any.
   */
  protected void write(ObjectOutputStream out, WriteContext context, ViewWriter viewWriter) throws IOException {
  }

  /**
   * <p>cleanUpModel.</p>
   */
  protected void cleanUpModel() {
    if (windowParent != null)
      windowParent.cleanUpModel();
  }
/*  static int cc = 0;
  protected void finalize() {
		try {
			System.out.println("\n\ndocking window finalized " + cc++ + "  " + hashCode() + "\n");
			super.finalize();
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}*/

  DropFilter getSplitDropFilter() {
    return getWindowProperties().getDropFilterProperties().getSplitDropFilter();
  }

  DropFilter getChildDropFilter() {
    return getWindowProperties().getDropFilterProperties().getChildDropFilter();
  }

  DropFilter getInteriorDropFilter() {
    return getWindowProperties().getDropFilterProperties().getInteriorDropFilter();
  }

  DropFilter getInsertTabDropFilter() {
    return getWindowProperties().getDropFilterProperties().getInsertTabDropFilter();
  }
  
  /**
   * <p>createTopLevelComponent.</p>
   *
   * @return a {@link java.awt.Window} object.
   */
  protected Window createTopLevelComponent() {
	return null;
  }
}
