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


// $Id: WindowAncestors.java,v 1.2 2005/12/04 13:35:12 jesper Exp $
package net.infonode.docking.internal;

import net.infonode.docking.DockingWindow;

/**
 * <p>WindowAncestors class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.2 $
 */
public class WindowAncestors {
  private DockingWindow[] ancestors;
  private boolean minimized;
  private boolean undocked;

  /**
   * <p>Constructor for WindowAncestors.</p>
   *
   * @param ancestors an array of {@link net.infonode.docking.DockingWindow} objects.
   * @param minimized a boolean.
   * @param undocked a boolean.
   */
  public WindowAncestors(DockingWindow[] ancestors, boolean minimized, boolean undocked) {
    this.ancestors = ancestors;
    this.minimized = minimized;
    this.undocked = undocked;
  }

  /**
   * <p>Getter for the field <code>ancestors</code>.</p>
   *
   * @return an array of {@link net.infonode.docking.DockingWindow} objects.
   */
  public DockingWindow[] getAncestors() {
    return ancestors;
  }

  /**
   * <p>isMinimized.</p>
   *
   * @return a boolean.
   */
  public boolean isMinimized() {
    return minimized;
  }

  /**
   * <p>isUndocked.</p>
   *
   * @return a boolean.
   */
  public boolean isUndocked() {
    return undocked;
  }
}
