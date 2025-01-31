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


// $Id: SimpleAction.java,v 1.4 2005/02/16 11:28:10 jesper Exp $
package net.infonode.gui.action;

import net.infonode.gui.icon.IconProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * An action with an icon and a title.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.4 $
 * @since IDW 1.3.0
 */
abstract public class SimpleAction implements IconProvider {
  /**
   * <p>getName.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  abstract public String getName();

  /**
   * <p>perform.</p>
   */
  abstract public void perform();

  /**
   * <p>isEnabled.</p>
   *
   * @return a boolean.
   */
  abstract public boolean isEnabled();

  /**
   * <p>Constructor for SimpleAction.</p>
   */
  protected SimpleAction() {
  }

  /**
   * Converts this action into a Swing {@link javax.swing.Action}.
   *
   * @return the Swing {@link javax.swing.Action}
   */
  public Action toSwingAction() {
    AbstractAction action = new AbstractAction(getName(), getIcon()) {
      public void actionPerformed(ActionEvent e) {
        perform();
      }
    };
    action.setEnabled(isEnabled());
    return action;
  }
}
