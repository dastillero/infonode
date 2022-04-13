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


// $Id: RootWindowItem.java,v 1.8 2005/03/11 13:16:49 jesper Exp $
package net.infonode.docking.model;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.properties.RootWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;

import java.util.ArrayList;

/**
 * <p>RootWindowItem class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.8 $
 */
public class RootWindowItem extends WindowItem {
  private RootWindowProperties rootWindowProperties = RootWindowProperties.createDefault();

  /**
   * <p>Constructor for RootWindowItem.</p>
   */
  public RootWindowItem() {
  }

  /**
   * <p>Constructor for RootWindowItem.</p>
   *
   * @param windowItem a {@link net.infonode.docking.model.RootWindowItem} object.
   */
  public RootWindowItem(RootWindowItem windowItem) {
    super(windowItem);
  }

  /**
   * <p>createPropertyObject.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.PropertyMap} object.
   */
  protected PropertyMap createPropertyObject() {
    return new RootWindowProperties().getMap();
  }

  /**
   * <p>getPropertyObject.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.PropertyMap} object.
   */
  protected PropertyMap getPropertyObject() {
    return rootWindowProperties.getMap();
  }

  /**
   * <p>Getter for the field <code>rootWindowProperties</code>.</p>
   *
   * @return a {@link net.infonode.docking.properties.RootWindowProperties} object.
   */
  public RootWindowProperties getRootWindowProperties() {
    return rootWindowProperties;
  }

  /**
   * <p>isRestoreWindow.</p>
   *
   * @return a boolean.
   */
  public boolean isRestoreWindow() {
    return true;
  }

  /** {@inheritDoc} */
  protected DockingWindow createWindow(ViewReader viewReader, ArrayList childWindows) {
    return childWindows.size() == 0 ? null :
           (DockingWindow) childWindows.get(0);
  }

  /**
   * <p>getRootItem.</p>
   *
   * @return a {@link net.infonode.docking.model.RootWindowItem} object.
   */
  public RootWindowItem getRootItem() {
    return this;
  }

  /**
   * <p>copy.</p>
   *
   * @return a {@link net.infonode.docking.model.WindowItem} object.
   */
  public WindowItem copy() {
    return new RootWindowItem(this);
  }

}
