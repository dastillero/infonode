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


// $Id: WriteContext.java,v 1.1 2004/12/14 16:39:59 jesper Exp $
package net.infonode.docking.internal;

import net.infonode.docking.ViewSerializer;

/**
 * Contains information used when writing a docking window state.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.1 $
 */
public class WriteContext {
  private boolean writePropertiesEnabled;
  private ViewSerializer viewSerializer;

  /**
   * Constructor.
   *
   * @param writePropertiesEnabled true if property values should be written
   * @param viewSerializer a {@link net.infonode.docking.ViewSerializer} object.
   */
  public WriteContext(boolean writePropertiesEnabled, ViewSerializer viewSerializer) {
    this.writePropertiesEnabled = writePropertiesEnabled;
    this.viewSerializer = viewSerializer;
  }

  /**
   * Returns true if property values should be written.
   *
   * @return true if property values should be written
   */
  public boolean getWritePropertiesEnabled() {
    return writePropertiesEnabled;
  }

  /**
   * <p>Getter for the field <code>viewSerializer</code>.</p>
   *
   * @return a {@link net.infonode.docking.ViewSerializer} object.
   */
  public ViewSerializer getViewSerializer() {
    return viewSerializer;
  }

  /**
   * <p>Setter for the field <code>viewSerializer</code>.</p>
   *
   * @param viewSerializer a {@link net.infonode.docking.ViewSerializer} object.
   */
  public void setViewSerializer(ViewSerializer viewSerializer) {
    this.viewSerializer = viewSerializer;
  }
}
