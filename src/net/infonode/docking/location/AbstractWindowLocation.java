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


// $Id: AbstractWindowLocation.java,v 1.15 2005/03/17 16:19:37 jesper Exp $
package net.infonode.docking.location;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.internalutil.InternalDockingUtil;
import net.infonode.util.IntList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.WeakReference;

/**
 * <p>Abstract AbstractWindowLocation class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.15 $
 */
abstract public class AbstractWindowLocation implements WindowLocation {
  private WindowLocation parentLocation;
  private WeakReference window;

  /**
   * <p>set.</p>
   *
   * @param parent a {@link net.infonode.docking.DockingWindow} object.
   * @param child a {@link net.infonode.docking.DockingWindow} object.
   * @return a boolean.
   */
  abstract protected boolean set(DockingWindow parent, DockingWindow child);

  /**
   * <p>Constructor for AbstractWindowLocation.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param parentLocation a {@link net.infonode.docking.location.WindowLocation} object.
   */
  protected AbstractWindowLocation(DockingWindow window, WindowLocation parentLocation) {
    this.window = new WeakReference(window);
    this.parentLocation = parentLocation;
  }

  /**
   * <p>Constructor for AbstractWindowLocation.</p>
   */
  protected AbstractWindowLocation() {
  }

  /** {@inheritDoc} */
  public boolean set(DockingWindow window) {
    DockingWindow w = getWindow();

    if (w != null)
      set(w, window);
    else if (parentLocation != null)
      parentLocation.set(window);

    return true;
  }

  private DockingWindow getWindow() {
    if (window == null)
      return null;

    DockingWindow w = (DockingWindow) window.get();
    return w != null && w.getRootWindow() != null && !w.isMinimized() ? w : null;
  }

  /** {@inheritDoc} */
  public void write(ObjectOutputStream out) throws IOException {
    out.writeBoolean(parentLocation != null);

    if (parentLocation != null)
      parentLocation.write(out);

    DockingWindow w = getWindow();
    out.writeBoolean(w != null);

    if (w != null) {
      InternalDockingUtil.getWindowPath(w).write(out);
    }
  }

  /**
   * <p>read.</p>
   *
   * @param in a {@link java.io.ObjectInputStream} object.
   * @param rootWindow a {@link net.infonode.docking.RootWindow} object.
   * @throws java.io.IOException if any.
   */
  protected void read(ObjectInputStream in, RootWindow rootWindow) throws IOException {
    parentLocation = in.readBoolean() ? LocationDecoder.decode(in, rootWindow) : null;
    window = in.readBoolean() ?
             new WeakReference(InternalDockingUtil.getWindow(rootWindow, IntList.decode(in))) : null;
  }

}
