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


// $Id: DockingWindowAdapter.java,v 1.13 2005/12/04 13:46:05 jesper Exp $
package net.infonode.docking;

/**
 * Adapter class which implements the {@link net.infonode.docking.DockingWindowListener} methods with empty bodies.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.13 $
 * @since IDW 1.1.0
 */
public class DockingWindowAdapter implements DockingWindowListener {
  /** {@inheritDoc} */
  public void windowShown(DockingWindow window) {
  }

  /** {@inheritDoc} */
  public void windowHidden(DockingWindow window) {
  }

  /** {@inheritDoc} */
  public void viewFocusChanged(View previouslyFocusedView, View focusedView) {
  }

  /** {@inheritDoc} */
  public void windowAdded(DockingWindow addedToWindow, DockingWindow addedWindow) {
  }

  /** {@inheritDoc} */
  public void windowRemoved(DockingWindow removedFromWindow, DockingWindow removedWindow) {
  }

  /** {@inheritDoc} */
  public void windowClosing(DockingWindow window) throws OperationAbortedException {
  }

  /** {@inheritDoc} */
  public void windowClosed(DockingWindow window) {
  }

  /** {@inheritDoc} */
  public void windowUndocking(DockingWindow window) throws OperationAbortedException {
  }

  /** {@inheritDoc} */
  public void windowUndocked(DockingWindow window) {
  }

  /** {@inheritDoc} */
  public void windowDocking(DockingWindow window) throws OperationAbortedException {
  }

  /** {@inheritDoc} */
  public void windowDocked(DockingWindow window) {
  }

  /** {@inheritDoc} */
  public void windowMinimized(DockingWindow window) {
  }

  /** {@inheritDoc} */
  public void windowMaximized(DockingWindow window) {
  }

  /** {@inheritDoc} */
  public void windowRestored(DockingWindow window) {
  }

  /** {@inheritDoc} */
  public void windowMaximizing(DockingWindow window) throws OperationAbortedException {
  }

  /** {@inheritDoc} */
  public void windowMinimizing(DockingWindow window) throws OperationAbortedException {
  }

  /** {@inheritDoc} */
  public void windowRestoring(DockingWindow window) throws OperationAbortedException {
  }

}
