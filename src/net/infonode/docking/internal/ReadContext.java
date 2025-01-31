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


// $Id: ReadContext.java,v 1.4 2005/12/04 13:46:04 jesper Exp $
package net.infonode.docking.internal;

import net.infonode.docking.RootWindow;
import net.infonode.docking.ViewSerializer;

/**
 * <p>ReadContext class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.4 $
 */
public class ReadContext {
  private RootWindow rootWindow;
  private int version;
  private boolean propertyValuesAvailable;
  private boolean readPropertiesEnabled;

  /**
   * <p>Constructor for ReadContext.</p>
   *
   * @param rootWindow a {@link net.infonode.docking.RootWindow} object.
   * @param version a int.
   * @param propertyValuesAvailable a boolean.
   * @param readPropertiesEnabled a boolean.
   */
  public ReadContext(RootWindow rootWindow,
                     int version,
                     boolean propertyValuesAvailable,
                     boolean readPropertiesEnabled) {
    this.rootWindow = rootWindow;
    this.version = version;
    this.propertyValuesAvailable = propertyValuesAvailable;
    this.readPropertiesEnabled = readPropertiesEnabled;
  }

  /**
   * <p>Getter for the field <code>rootWindow</code>.</p>
   *
   * @return a {@link net.infonode.docking.RootWindow} object.
   */
  public RootWindow getRootWindow() {
    return rootWindow;
  }

  /**
   * <p>getViewSerializer.</p>
   *
   * @return a {@link net.infonode.docking.ViewSerializer} object.
   */
  public ViewSerializer getViewSerializer() {
    return rootWindow.getViewSerializer();
  }

  /**
   * <p>isPropertyValuesAvailable.</p>
   *
   * @return a boolean.
   */
  public boolean isPropertyValuesAvailable() {
    return propertyValuesAvailable;
  }

  /**
   * <p>Getter for the field <code>readPropertiesEnabled</code>.</p>
   *
   * @return a boolean.
   */
  public boolean getReadPropertiesEnabled() {
    return readPropertiesEnabled;
  }

  /**
   * <p>Getter for the field <code>version</code>.</p>
   *
   * @return returns the serialized version
   */
  public int getVersion() {
    return version;
  }
}
