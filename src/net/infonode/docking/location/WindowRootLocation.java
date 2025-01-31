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


// $Id: WindowRootLocation.java,v 1.8 2005/02/16 11:28:14 jesper Exp $
package net.infonode.docking.location;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.docking.internalutil.InternalDockingUtil;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p>WindowRootLocation class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.8 $
 */
public class WindowRootLocation extends AbstractWindowLocation {
  /**
   * <p>Constructor for WindowRootLocation.</p>
   *
   * @param rootWindow a {@link net.infonode.docking.RootWindow} object.
   */
  public WindowRootLocation(RootWindow rootWindow) {
    super(rootWindow, null);
  }

  private WindowRootLocation() {
  }

  /** {@inheritDoc} */
  protected boolean set(DockingWindow parent, DockingWindow child) {
    RootWindow rootWindow = (RootWindow) parent;
    InternalDockingUtil.addToRootWindow(child, rootWindow);
    return true;
  }

  /** {@inheritDoc} */
  public void write(ObjectOutputStream out) throws IOException {
    out.writeInt(LocationDecoder.ROOT);
    super.write(out);
  }

  /**
   * <p>decode.</p>
   *
   * @param in a {@link java.io.ObjectInputStream} object.
   * @param rootWindow a {@link net.infonode.docking.RootWindow} object.
   * @return a {@link net.infonode.docking.location.WindowRootLocation} object.
   * @throws java.io.IOException if any.
   */
  public static WindowRootLocation decode(ObjectInputStream in, RootWindow rootWindow) throws IOException {
    WindowRootLocation location = new WindowRootLocation();
    location.read(in, rootWindow);
    return location;
  }

}
