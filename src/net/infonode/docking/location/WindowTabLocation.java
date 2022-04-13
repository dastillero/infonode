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


// $Id: WindowTabLocation.java,v 1.5 2005/02/16 11:28:14 jesper Exp $
package net.infonode.docking.location;

import net.infonode.docking.AbstractTabWindow;
import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p>WindowTabLocation class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.5 $
 */
public class WindowTabLocation extends AbstractWindowLocation {
  private int index;

  /**
   * <p>Constructor for WindowTabLocation.</p>
   *
   * @param window a {@link net.infonode.docking.AbstractTabWindow} object.
   * @param parentLocation a {@link net.infonode.docking.location.WindowLocation} object.
   * @param index a int.
   */
  public WindowTabLocation(AbstractTabWindow window, WindowLocation parentLocation, int index) {
    super(window, parentLocation);
    this.index = index;
  }

  private WindowTabLocation(int index) {
    this.index = index;
  }

  /** {@inheritDoc} */
  public boolean set(DockingWindow parent, DockingWindow child) {
    ((AbstractTabWindow) parent).addTab(child, index);
    return true;
  }

  /** {@inheritDoc} */
  public void write(ObjectOutputStream out) throws IOException {
    out.writeInt(LocationDecoder.TAB);
    out.writeInt(index);
    super.write(out);
  }

  /**
   * <p>decode.</p>
   *
   * @param in a {@link java.io.ObjectInputStream} object.
   * @param rootWindow a {@link net.infonode.docking.RootWindow} object.
   * @return a {@link net.infonode.docking.location.WindowTabLocation} object.
   * @throws java.io.IOException if any.
   */
  public static WindowTabLocation decode(ObjectInputStream in, RootWindow rootWindow) throws IOException {
    WindowTabLocation location = new WindowTabLocation(in.readInt());
    location.read(in, rootWindow);
    return location;
  }
}
