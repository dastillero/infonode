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


// $Id: NullWindowAction.java,v 1.4 2005/02/16 11:28:14 jesper Exp $
package net.infonode.docking.action;

import net.infonode.docking.DockingWindow;

import java.io.ObjectStreamException;

/**
 * Does nothing.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.4 $
 * @since IDW 1.3.0
 */
public class NullWindowAction extends DockingWindowAction {
  private static final long serialVersionUID = 1;

  /**
   * The only instance of this class.
   */
  public static final NullWindowAction INSTANCE = new NullWindowAction();

  private NullWindowAction() {
  }

  /**
   * <p>getName.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return "Null";
  }

  /** {@inheritDoc} */
  public boolean isPerformable(DockingWindow window) {
    return true;
  }

  /** {@inheritDoc} */
  public void perform(DockingWindow window) {
  }

  /**
   * <p>readResolve.</p>
   *
   * @return a {@link java.lang.Object} object.
   * @throws java.io.ObjectStreamException if any.
   */
  protected Object readResolve() throws ObjectStreamException {
    return INSTANCE;
  }

}
