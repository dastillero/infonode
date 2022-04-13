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


// $Id: TabWindowItem.java,v 1.13 2007/01/28 21:25:10 jesper Exp $
package net.infonode.docking.model;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.properties.TabWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * <p>TabWindowItem class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.13 $
 */
public class TabWindowItem extends AbstractTabWindowItem {
  /** Constant <code>emptyProperties</code> */
  public static final TabWindowProperties emptyProperties = new TabWindowProperties();

  private TabWindowProperties tabWindowProperties;
  private TabWindowProperties parentProperties = emptyProperties;

  /**
   * <p>Constructor for TabWindowItem.</p>
   */
  public TabWindowItem() {
    tabWindowProperties = new TabWindowProperties(emptyProperties);
  }

  /**
   * <p>Constructor for TabWindowItem.</p>
   *
   * @param windowItem a {@link net.infonode.docking.model.TabWindowItem} object.
   */
  public TabWindowItem(TabWindowItem windowItem) {
    super(windowItem);
    tabWindowProperties = new TabWindowProperties(windowItem.getTabWindowProperties().getMap().copy(true, true));
    tabWindowProperties.getMap().replaceSuperMap(windowItem.getParentTabWindowProperties().getMap(),
                                                 emptyProperties.getMap());
  }

  /** {@inheritDoc} */
  protected DockingWindow createWindow(ViewReader viewReader, ArrayList childWindows) {
    return childWindows.size() == 0 ? null :
           viewReader.createTabWindow((DockingWindow[]) childWindows.toArray(new DockingWindow[childWindows.size()]),
                                      this);
  }

  /**
   * <p>Getter for the field <code>tabWindowProperties</code>.</p>
   *
   * @return a {@link net.infonode.docking.properties.TabWindowProperties} object.
   */
  public TabWindowProperties getTabWindowProperties() {
    return tabWindowProperties;
  }

  /**
   * <p>Setter for the field <code>tabWindowProperties</code>.</p>
   *
   * @param tabWindowProperties a {@link net.infonode.docking.properties.TabWindowProperties} object.
   */
  public void setTabWindowProperties(TabWindowProperties tabWindowProperties) {
    this.tabWindowProperties = tabWindowProperties;
  }

  /**
   * <p>getParentTabWindowProperties.</p>
   *
   * @return a {@link net.infonode.docking.properties.TabWindowProperties} object.
   */
  public TabWindowProperties getParentTabWindowProperties() {
    return parentProperties;
  }

  /**
   * <p>setParentTabWindowProperties.</p>
   *
   * @param parentProperties a {@link net.infonode.docking.properties.TabWindowProperties} object.
   */
  public void setParentTabWindowProperties(TabWindowProperties parentProperties) {
    tabWindowProperties.getMap().replaceSuperMap(this.parentProperties.getMap(), parentProperties.getMap());
    this.parentProperties = parentProperties;
  }

  /**
   * <p>copy.</p>
   *
   * @return a {@link net.infonode.docking.model.WindowItem} object.
   */
  public WindowItem copy() {
    return new TabWindowItem(this);
  }

  /** {@inheritDoc} */
  public void write(ObjectOutputStream out, WriteContext context, ViewWriter viewWriter) throws IOException {
    out.writeInt(WindowItemDecoder.TAB);
    super.write(out, context, viewWriter);
  }

  /**
   * <p>getPropertyObject.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.PropertyMap} object.
   */
  protected PropertyMap getPropertyObject() {
    return getTabWindowProperties().getMap();
  }

  /**
   * <p>clearWindows.</p>
   */
  public void clearWindows() {
    // Do nothing
  }

  /**
   * <p>toString.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return "TabWindow: " + super.toString();
  }

}
