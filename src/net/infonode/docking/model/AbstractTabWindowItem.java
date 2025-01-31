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


// $Id: AbstractTabWindowItem.java,v 1.5 2005/02/16 11:28:14 jesper Exp $
package net.infonode.docking.model;

import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * <p>Abstract AbstractTabWindowItem class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.5 $
 */
abstract public class AbstractTabWindowItem extends WindowItem {
  private WindowItem selectedItem;

  /**
   * <p>Constructor for AbstractTabWindowItem.</p>
   */
  protected AbstractTabWindowItem() {
  }

  /**
   * <p>Constructor for AbstractTabWindowItem.</p>
   *
   * @param windowItem a {@link net.infonode.docking.model.WindowItem} object.
   */
  protected AbstractTabWindowItem(WindowItem windowItem) {
    super(windowItem);
  }

  /**
   * <p>Getter for the field <code>selectedItem</code>.</p>
   *
   * @return a {@link net.infonode.docking.model.WindowItem} object.
   */
  public WindowItem getSelectedItem() {
    return selectedItem;
  }

  /**
   * <p>Setter for the field <code>selectedItem</code>.</p>
   *
   * @param selectedItem a {@link net.infonode.docking.model.WindowItem} object.
   */
  public void setSelectedItem(WindowItem selectedItem) {
    this.selectedItem = selectedItem;
  }

  /** {@inheritDoc} */
  public void writeSettings(ObjectOutputStream out, WriteContext context) throws IOException {
    super.writeSettings(out, context);
    out.writeInt(getWindowIndex(selectedItem));
  }

  /** {@inheritDoc} */
  public void readSettings(ObjectInputStream in, ReadContext context) throws IOException {
    super.readSettings(in, context);

    if (context.getVersion() >= 3) {
      int selectedIndex = in.readInt();
      selectedItem = selectedIndex == -1 ? null : getWindow(selectedIndex);
    }
  }

}
