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


// $Id: DraggableComponent.java,v 1.32 2009/02/05 15:57:56 jesper Exp $

package net.infonode.gui.draggable;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;

import net.infonode.gui.ComponentUtil;
import net.infonode.gui.EventUtil;

/**
 * <p>DraggableComponent class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class DraggableComponent {
  private final JComponent component;
  private JComponent[] eventComponents;

  private boolean reorderEnabled = true;
  private boolean enabled = true;
  private boolean reorderRestoreOnDrag;
  private boolean detectOuterAreaAsLine = true;
  private boolean enableInsideDrag;
  private boolean selectOnMousePress;

  private boolean mousePressed;
  private boolean dragEventFired;
  private boolean dragStarted;

  private boolean ignoreAddNotify = false;

  private int dragIndex;
  private int dragFromIndex;
  private int abortDragKeyCode = KeyEvent.VK_ESCAPE;

  private ArrayList layoutOrderList;

  private ArrayList listeners;
  private JComponent outerParentArea;

  private final KeyEventDispatcher abortDragKeyDispatcher = new KeyEventDispatcher() {
    public boolean dispatchKeyEvent(KeyEvent e) {
      if (mousePressed && e.getKeyCode() == abortDragKeyCode) {
        if (e.getID() == KeyEvent.KEY_PRESSED)
          dragCompleted(null);
        return true;
      }
      return false;
    }
  };

  private final MouseInputListener mouseInputListener = new MouseInputAdapter() {
    public void mousePressed(MouseEvent e) {
      //if (MouseEventCoalesceManager.getInstance().isPressedAllowed(e))
      pressed(e);
    }

    public void mouseReleased(MouseEvent e) {
      //if (MouseEventCoalesceManager.getInstance().isReleasedAllowed(e))
      released(e);
    }

    public void mouseDragged(MouseEvent e) {
      //if (MouseEventCoalesceManager.getInstance().isDraggedAllowed(e))
      dragged(e);
    }
  };

  /**
   * <p>Constructor for DraggableComponent.</p>
   *
   * @param component a {@link javax.swing.JComponent} object.
   */
  public DraggableComponent(JComponent component) {
    this(component, component);
  }

  /**
   * <p>Constructor for DraggableComponent.</p>
   *
   * @param component a {@link javax.swing.JComponent} object.
   * @param eventComponent a {@link javax.swing.JComponent} object.
   */
  public DraggableComponent(JComponent component, JComponent eventComponent) {
    this(component, new JComponent[]{eventComponent});
  }

  /**
   * <p>Constructor for DraggableComponent.</p>
   *
   * @param component a {@link javax.swing.JComponent} object.
   * @param eventComponents an array of {@link javax.swing.JComponent} objects.
   */
  public DraggableComponent(JComponent component, JComponent[] eventComponents) {
    this.component = component;
    component.addComponentListener(new ComponentAdapter() {

      public void componentResized(ComponentEvent e) {
        fireChangedEvent(DraggableComponentEvent.TYPE_UNDEFINED);
      }

      /*public void componentMoved(ComponentEvent e) {
        fireChangedEvent(DraggableComponentEvent.TYPE_MOVED);
      }*/
    });
    setEventComponents(eventComponents);
  }

  /**
   * <p>addListener.</p>
   *
   * @param l a {@link net.infonode.gui.draggable.DraggableComponentListener} object.
   */
  public void addListener(DraggableComponentListener l) {
    if (listeners == null)
      listeners = new ArrayList(2);

    listeners.add(l);
  }

  /**
   * <p>removeListener.</p>
   *
   * @param l a {@link net.infonode.gui.draggable.DraggableComponentListener} object.
   */
  public void removeListener(DraggableComponentListener l) {
    if (listeners != null) {
      listeners.remove(l);

      if (listeners.size() == 0)
        listeners = null;
    }
  }

  /**
   * <p>Getter for the field <code>component</code>.</p>
   *
   * @return a {@link javax.swing.JComponent} object.
   */
  public JComponent getComponent() {
    return component;
  }

  /**
   * <p>Getter for the field <code>eventComponents</code>.</p>
   *
   * @return an array of {@link javax.swing.JComponent} objects.
   */
  public JComponent[] getEventComponents() {
    return eventComponents;
  }

  /**
   * <p>Setter for the field <code>eventComponents</code>.</p>
   *
   * @param eventComponents an array of {@link javax.swing.JComponent} objects.
   */
  public void setEventComponents(JComponent[] eventComponents) {
    if (this.eventComponents != null) {
      for (int i = 0; i < this.eventComponents.length; i++) {
        this.eventComponents[i].removeMouseListener(mouseInputListener);
        this.eventComponents[i].removeMouseMotionListener(mouseInputListener);
      }
    }

    this.eventComponents = eventComponents;

    if (this.eventComponents != null) {
      for (int i = 0; i < this.eventComponents.length; i++) {
        this.eventComponents[i].addMouseListener(mouseInputListener);
        this.eventComponents[i].addMouseMotionListener(mouseInputListener);
      }
    }
  }

  /**
   * <p>Getter for the field <code>abortDragKeyCode</code>.</p>
   *
   * @return a int.
   */
  public int getAbortDragKeyCode() {
    return abortDragKeyCode;
  }

  /**
   * <p>Setter for the field <code>abortDragKeyCode</code>.</p>
   *
   * @param abortDragKeyCode a int.
   */
  public void setAbortDragKeyCode(int abortDragKeyCode) {
    this.abortDragKeyCode = abortDragKeyCode;
  }

  /**
   * <p>isEnabled.</p>
   *
   * @return a boolean.
   */
  public boolean isEnabled() {
    return enabled;
  }

  /**
   * <p>Setter for the field <code>enabled</code>.</p>
   *
   * @param enabled a boolean.
   */
  public void setEnabled(boolean enabled) {
    if (this.enabled != enabled) {
      this.enabled = enabled;
      fireChangedEvent(enabled ? DraggableComponentEvent.TYPE_ENABLED : DraggableComponentEvent.TYPE_DISABLED);
    }
  }

  /**
   * <p>isReorderEnabled.</p>
   *
   * @return a boolean.
   */
  public boolean isReorderEnabled() {
    return reorderEnabled;
  }

  /**
   * <p>Setter for the field <code>reorderEnabled</code>.</p>
   *
   * @param reorderEnabled a boolean.
   */
  public void setReorderEnabled(boolean reorderEnabled) {
    this.reorderEnabled = reorderEnabled;
  }

  /**
   * <p>isReorderRestoreOnDrag.</p>
   *
   * @return a boolean.
   */
  public boolean isReorderRestoreOnDrag() {
    return reorderRestoreOnDrag;
  }

  /**
   * <p>Setter for the field <code>reorderRestoreOnDrag</code>.</p>
   *
   * @param reorderRestoreOnDrag a boolean.
   */
  public void setReorderRestoreOnDrag(boolean reorderRestoreOnDrag) {
    this.reorderRestoreOnDrag = reorderRestoreOnDrag;
  }

  /**
   * <p>isDetectOuterAreaAsLine.</p>
   *
   * @return a boolean.
   */
  public boolean isDetectOuterAreaAsLine() {
    return detectOuterAreaAsLine;
  }

  /**
   * <p>Setter for the field <code>detectOuterAreaAsLine</code>.</p>
   *
   * @param detectOuterAreaAsLine a boolean.
   */
  public void setDetectOuterAreaAsLine(boolean detectOuterAreaAsLine) {
    this.detectOuterAreaAsLine = detectOuterAreaAsLine;
  }

  /**
   * <p>isEnableInsideDrag.</p>
   *
   * @return a boolean.
   */
  public boolean isEnableInsideDrag() {
    return enableInsideDrag;
  }

  /**
   * <p>Setter for the field <code>enableInsideDrag</code>.</p>
   *
   * @param enableInsideDrag a boolean.
   */
  public void setEnableInsideDrag(boolean enableInsideDrag) {
    this.enableInsideDrag = enableInsideDrag;
  }

  /**
   * <p>isSelectOnMousePress.</p>
   *
   * @return a boolean.
   */
  public boolean isSelectOnMousePress() {
    return selectOnMousePress;
  }

  /**
   * <p>Setter for the field <code>selectOnMousePress</code>.</p>
   *
   * @param selectOnMousePress a boolean.
   */
  public void setSelectOnMousePress(boolean selectOnMousePress) {
    this.selectOnMousePress = selectOnMousePress;
  }

  /**
   * <p>drag.</p>
   *
   * @param p a {@link java.awt.Point} object.
   */
  public void drag(Point p) {
    if (enabled) {
      dragIndex = getComponentIndex(component);
      dragFromIndex = dragIndex;
      doDrag(p);
    }
  }

  /**
   * <p>abortDrag.</p>
   */
  public void abortDrag() {
    if (dragStarted)
      dragCompleted(null);
  }

  /**
   * <p>Setter for the field <code>layoutOrderList</code>.</p>
   *
   * @param layoutOrderList a {@link java.util.ArrayList} object.
   */
  public void setLayoutOrderList(ArrayList layoutOrderList) {
    this.layoutOrderList = layoutOrderList;
  }

  /**
   * <p>select.</p>
   */
  public void select() {
    if (enabled)
      fireSelectedEvent();
  }

  /**
   * <p>Setter for the field <code>outerParentArea</code>.</p>
   *
   * @param outerParentArea a {@link javax.swing.JComponent} object.
   */
  public void setOuterParentArea(JComponent outerParentArea) {
    this.outerParentArea = outerParentArea;
  }

  /**
   * <p>isIgnoreAddNotify.</p>
   *
   * @return a boolean.
   */
  public boolean isIgnoreAddNotify() {
    return ignoreAddNotify;
  }

  /**
   * <p>Setter for the field <code>ignoreAddNotify</code>.</p>
   *
   * @param ignoreAddNotify a boolean.
   */
  public void setIgnoreAddNotify(boolean ignoreAddNotify) {
    this.ignoreAddNotify = ignoreAddNotify;
  }

  private void pressed(MouseEvent e) {
    if (enabled && e.getButton() == MouseEvent.BUTTON1) {
      if (selectOnMousePress && !e.isShiftDown())
        select();
      dragStarted = false;
      KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(abortDragKeyDispatcher);
      mousePressed = true;
      dragIndex = getComponentIndex(component);
      dragFromIndex = dragIndex;

      fireChangedEvent(DraggableComponentEvent.TYPE_PRESSED);
    }
  }

  private void released(MouseEvent e) {
    if (mousePressed) {
      if (e.getButton() == MouseEvent.BUTTON1)
        dragCompleted(e);
      else {
        dragCompleted(null);
        e.consume();
      }
    }
  }

  private void dragged(MouseEvent e) {
    if (enabled && mousePressed) {
      Point p = SwingUtilities.convertPoint((JComponent) e.getSource(), e.getPoint(), component);
      if (dragStarted || enableInsideDrag || !component.contains(p)) {
        if (reorderEnabled)
          doDrag(p);
        else
          dragStarted = true;

        fireDraggedEvent(EventUtil.convert(e, component, p));
      }
    }
  }

  private void dragCompleted(MouseEvent e) {
    mousePressed = false;
    dragStarted = false;

    KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(abortDragKeyDispatcher);

    if (e == null) {
      restoreComponentOrder();
      fireNotDroppedEvent();
    }
    else if (!checkParentContains(
        SwingUtilities.convertPoint((JComponent) e.getSource(), e.getPoint(), component.getParent()))) {
      restoreComponentOrder();
      fireDroppedEvent(EventUtil.convert(e, component));
    }
    else {
      fireDroppedEvent(EventUtil.convert(e, component));
      //if (component.contains(p))
      if (!selectOnMousePress && !e.isShiftDown())
        fireSelectedEvent();
    }

    fireChangedEvent(DraggableComponentEvent.TYPE_RELEASED);
  }

  private void updateParent() {
    if (component.getParent() != null) {
      ComponentUtil.validate(component.getParent());
      //component.getParent().repaint();
    }
  }

  private void doDrag(Point p) {
    dragStarted = true;
    JComponent parent = (JComponent) component.getParent();

    if (parent.getComponentCount() == 1)
      return;

    Point p2 = SwingUtilities.convertPoint(component, p, parent);
    int toIndex = getMoveComponentIndex(p2);
    if (toIndex != -1) {
      toIndex = Math.min(toIndex, parent.getComponentCount() - 1);
      Component fromComponent = getComponent(parent, dragIndex);
      int fromDimension;
      int toPos;
      int toDimension;

      if (isVerticalDrag()) {
        fromDimension = fromComponent.getHeight();
        toPos = (int) SwingUtilities.convertPoint(parent, p2, getComponent(parent, toIndex)).getY();
        toDimension = getComponent(parent, toIndex).getHeight();
      }
      else {
        fromDimension = fromComponent.getWidth();
        toPos = (int) SwingUtilities.convertPoint(parent, p2, getComponent(parent, toIndex)).getX();
        toDimension = getComponent(parent, toIndex).getWidth();
      }

      if ((toIndex > dragIndex && toDimension - toPos > fromDimension) ||
          ((dragIndex == -1 || toIndex < dragIndex) && toPos > fromDimension))
        return;

      if (dragIndex != -1 && dragIndex != toIndex) {
        removeComponent(parent, fromComponent, dragIndex);
        addComponent(parent, fromComponent, toIndex);
        fireChangedEvent(DraggableComponentEvent.TYPE_MOVED);
      }
    }

    if (toIndex < 0) {
      restoreComponentOrder();
    }
    else
      dragIndex = toIndex;
  }

  private boolean isVerticalDrag() {
    JComponent parent = (JComponent) component.getParent();
    if (parent.getComponentCount() > 1)
      return getComponent(parent, 0).getY() < getComponent(parent, 1).getY();

    return false;
  }

  private boolean checkParentContains(Point p) {
    if (outerParentArea == null)
      return component.getParent().contains(p);

    Point p2 = SwingUtilities.convertPoint(component.getParent(), p, outerParentArea);
    if (detectOuterAreaAsLine) {
      Insets i = new Insets(0, 0, 0, 0);//outerParentArea.getInsets();
      return component.getParent().contains(p)
      ||
      (outerParentArea.contains(p2) &&
          (isVerticalDrag()
              ?
               (p2.getX() >= i.left && p2.getX() < (outerParentArea.getWidth() - i.right))
               :
                 (p2.getY() >= i.top && p2.getY() < (outerParentArea.getHeight() - i.bottom))));
    }

    return component.getParent().contains(p) || outerParentArea.contains(p2);
  }

  private int getMoveComponentIndex(Point p) {
    JComponent parent = (JComponent) component.getParent();
    if (checkParentContains(p)) {
      boolean vertical = isVerticalDrag();
      for (int i = 0; i < parent.getComponentCount() - 1; i++) {
        Point p2 = getComponent(parent, i + 1).getLocation();

        if (vertical) {
          if (p.getY() >= 0 && p.getY() < p2.getY())
            return i;
        }
        else {
          if (p.getX() >= 0 && p.getX() < p2.getX())
            return i;
        }
      }

      if (dragIndex == -1)
        return parent.getComponentCount();
      else if (vertical)
        return p.getY() < 0 ? 0 : parent.getComponentCount() - 1;
      else
        return p.getX() < 0 ? 0 : parent.getComponentCount() - 1;
    }

    return -1;
  }

  private JComponent getComponent(Container parent, int index) {
    if (layoutOrderList != null)
      return (JComponent) layoutOrderList.get(index);

    return (JComponent) parent.getComponent(index);
  }

  private int getComponentIndex(Component c) {
    if (layoutOrderList != null)
      return layoutOrderList.indexOf(c);

    return ComponentUtil.getComponentIndex(c);
  }

  private void addComponent(Container parent, Component c, int index) {
    if (layoutOrderList != null) {
      layoutOrderList.add(index, c);
      parent.add(c, index);
    }
    else
      parent.add(c, index);

    revalidateComponentTree((JComponent) c);
  }

  private void removeComponent(Container parent, Component c, int index) {
    revalidateComponentTree((JComponent) c);

    if (layoutOrderList != null)
      if (index < 0) {
        layoutOrderList.remove(c);
        parent.remove(c);
      }
      else {
        Component tmp = (Component) layoutOrderList.get(index);
        layoutOrderList.remove(index);
        parent.remove(tmp);
      }
    else if (index < 0)
      parent.remove(c);
    else
      parent.remove(index);
  }

  private void revalidateComponentTree(JComponent c) {
    Container parent = c.getParent();
    int index = ComponentUtil.getComponentIndex(c);
    if (index > 0)
      doRevalidateComponentTree((JComponent) parent.getComponent(index - 1));
    doRevalidateComponentTree(c);
    if (index < parent.getComponentCount() - 1)
      doRevalidateComponentTree((JComponent) parent.getComponent(index + 1));
  }

  private void doRevalidateComponentTree(JComponent c) {
    c.revalidate();
    int count = c.getComponentCount();
    for (int i = 0; i < count; i++)
      doRevalidateComponentTree(((JComponent) c.getComponent(i)));
  }

  private void restoreComponentOrder() {
    if (reorderEnabled && dragIndex != -1 && dragFromIndex != -1 && dragIndex != dragFromIndex) {
      Container parent = component.getParent();
      Component comp = getComponent(parent, dragIndex);
      removeComponent(parent, comp, -1);
      dragIndex = dragFromIndex;
      addComponent(parent, comp, dragIndex);
      fireChangedEvent(DraggableComponentEvent.TYPE_MOVED);
    }
  }

  private void fireChangedEvent(int type) {
    updateParent();

    if (listeners != null) {
      DraggableComponentEvent event = new DraggableComponentEvent(this, type);
      Object[] l = listeners.toArray();
      for (int i = 0; i < l.length; i++)
        ((DraggableComponentListener) l[i]).changed(event);
    }
  }

  private void fireSelectedEvent() {
    updateParent();

    if (listeners != null) {
      DraggableComponentEvent event = new DraggableComponentEvent(this);
      Object[] l = listeners.toArray();
      for (int i = 0; i < l.length; i++)
        ((DraggableComponentListener) l[i]).selected(event);
    }
  }

  private void fireDraggedEvent(MouseEvent mouseEvent) {
    dragEventFired = true;
    if (listeners != null) {
      DraggableComponentEvent event = new DraggableComponentEvent(this, mouseEvent);
      Object[] l = listeners.toArray();
      for (int i = 0; i < l.length; i++)
        ((DraggableComponentListener) l[i]).dragged(event);
    }
  }

  private void fireDroppedEvent(MouseEvent mouseEvent) {
    updateParent();

    if (dragEventFired) {
      dragEventFired = false;
      if (listeners != null) {
        DraggableComponentEvent event = new DraggableComponentEvent(this, mouseEvent);
        Object[] l = listeners.toArray();
        for (int i = 0; i < l.length; i++)
          ((DraggableComponentListener) l[i]).dropped(event);
      }
    }
  }

  private void fireNotDroppedEvent() {
    updateParent();

    if (dragEventFired) {
      dragEventFired = false;
      if (listeners != null) {
        DraggableComponentEvent event = new DraggableComponentEvent(this);
        Object[] l = listeners.toArray();
        for (int i = 0; i < l.length; i++)
          ((DraggableComponentListener) l[i]).dragAborted(event);
      }
    }
  }
}
