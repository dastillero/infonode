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


// $Id: DropAction.java,v 1.6 2005/02/16 11:28:14 jesper Exp $
package net.infonode.docking.internalutil;

import net.infonode.docking.DockingWindow;

import java.awt.event.MouseEvent;

/**
 * <p>Abstract DropAction class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.6 $
 */
abstract public class DropAction {
  /**
   * <p>execute.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param mouseEvent a {@link java.awt.event.MouseEvent} object.
   */
  abstract public void execute(DockingWindow window, MouseEvent mouseEvent);

  /**
   * <p>Constructor for DropAction.</p>
   */
  protected DropAction() {
  }

  /**
   * <p>showTitle.</p>
   *
   * @return a boolean.
   */
  public boolean showTitle() {
    return true;
  }

  /**
   * <p>clear.</p>
   *
   * @param window a {@link net.infonode.docking.DockingWindow} object.
   * @param newDropAction a {@link net.infonode.docking.internalutil.DropAction} object.
   */
  public void clear(DockingWindow window, DropAction newDropAction) {
  }

}
