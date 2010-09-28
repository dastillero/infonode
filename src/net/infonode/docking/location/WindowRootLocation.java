/** 
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


// $Id: WindowRootLocation.java,v 1.2 2004/06/29 14:40:05 jesper Exp $
package net.infonode.docking.location;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.RootWindow;
import net.infonode.util.Direction;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author $Author: jesper $
 * @version $Revision: 1.2 $
 */
public class WindowRootLocation extends AbstractWindowLocation {
  public WindowRootLocation(RootWindow rootWindow) {
    super(rootWindow, null);
  }

  private WindowRootLocation() {
  }

  protected void set(DockingWindow parent, DockingWindow child) {
    RootWindow rootWindow = (RootWindow) parent;
    DockingWindow w = rootWindow.getWindow();

    if (w == null)
      rootWindow.setWindow(child);
    else
      w.split(child, Direction.RIGHT, 0.5F);
  }

  public void write(ObjectOutputStream out) throws IOException {
    out.writeInt(LocationDecoder.ROOT);
    super.write(out);
  }

  public static WindowRootLocation decode(ObjectInputStream in, RootWindow rootWindow) throws IOException {
    WindowRootLocation location = new WindowRootLocation();
    location.read(in, rootWindow);
    return location;
  }

}
