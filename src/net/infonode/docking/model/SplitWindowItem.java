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


// $Id: SplitWindowItem.java,v 1.12 2007/01/28 21:25:10 jesper Exp $
package net.infonode.docking.model;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.properties.SplitWindowProperties;
import net.infonode.properties.propertymap.PropertyMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * <p>SplitWindowItem class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.12 $
 */
public class SplitWindowItem extends WindowItem {
  /** Constant <code>emptyProperties</code> */
  public static final SplitWindowProperties emptyProperties = new SplitWindowProperties();

  private boolean isHorizontal;
  private float dividerLocation;
  private SplitWindowProperties splitWindowProperties;
  private SplitWindowProperties parentProperties = emptyProperties;

  /**
   * <p>Constructor for SplitWindowItem.</p>
   */
  public SplitWindowItem() {
    splitWindowProperties = new SplitWindowProperties(emptyProperties);
  }

  /**
   * <p>Constructor for SplitWindowItem.</p>
   *
   * @param windowItem a {@link net.infonode.docking.model.SplitWindowItem} object.
   */
  public SplitWindowItem(SplitWindowItem windowItem) {
    super(windowItem);
    splitWindowProperties = new SplitWindowProperties(windowItem.getSplitWindowProperties().getMap().copy(true, true));
    splitWindowProperties.getMap().replaceSuperMap(windowItem.getParentSplitWindowProperties().getMap(),
                                                   emptyProperties.getMap());
  }

  /**
   * <p>Constructor for SplitWindowItem.</p>
   *
   * @param leftWindow a {@link net.infonode.docking.model.WindowItem} object.
   * @param rightWindow a {@link net.infonode.docking.model.WindowItem} object.
   * @param horizontal a boolean.
   * @param dividerLocation a float.
   */
  public SplitWindowItem(WindowItem leftWindow, WindowItem rightWindow, boolean horizontal, float dividerLocation) {
    addWindow(leftWindow);
    addWindow(rightWindow);
    isHorizontal = horizontal;
    this.dividerLocation = dividerLocation;
  }

  /** {@inheritDoc} */
  protected DockingWindow createWindow(ViewReader viewReader, ArrayList childWindows) {
    return childWindows.size() == 0 ? null :
           childWindows.size() == 1 ? (DockingWindow) childWindows.get(0) :
           viewReader.createSplitWindow((DockingWindow) childWindows.get(0),
                                        (DockingWindow) childWindows.get(1),
                                        this);
  }

  /**
   * <p>isHorizontal.</p>
   *
   * @return a boolean.
   */
  public boolean isHorizontal() {
    return isHorizontal;
  }

  /**
   * <p>Getter for the field <code>dividerLocation</code>.</p>
   *
   * @return a float.
   */
  public float getDividerLocation() {
    return dividerLocation;
  }

  /**
   * <p>setHorizontal.</p>
   *
   * @param horizontal a boolean.
   */
  public void setHorizontal(boolean horizontal) {
    isHorizontal = horizontal;
  }

  /**
   * <p>Setter for the field <code>dividerLocation</code>.</p>
   *
   * @param dividerLocation a float.
   */
  public void setDividerLocation(float dividerLocation) {
    this.dividerLocation = dividerLocation;
  }

  /**
   * <p>Getter for the field <code>splitWindowProperties</code>.</p>
   *
   * @return a {@link net.infonode.docking.properties.SplitWindowProperties} object.
   */
  public SplitWindowProperties getSplitWindowProperties() {
    return splitWindowProperties;
  }

  /**
   * <p>getParentSplitWindowProperties.</p>
   *
   * @return a {@link net.infonode.docking.properties.SplitWindowProperties} object.
   */
  public SplitWindowProperties getParentSplitWindowProperties() {
    return parentProperties;
  }

  /**
   * <p>setParentSplitWindowProperties.</p>
   *
   * @param parentProperties a {@link net.infonode.docking.properties.SplitWindowProperties} object.
   */
  public void setParentSplitWindowProperties(SplitWindowProperties parentProperties) {
    splitWindowProperties.getMap().replaceSuperMap(this.parentProperties.getMap(), parentProperties.getMap());
    this.parentProperties = parentProperties;
  }

  /**
   * <p>copy.</p>
   *
   * @return a {@link net.infonode.docking.model.WindowItem} object.
   */
  public WindowItem copy() {
    return new SplitWindowItem(this);
  }

  /** {@inheritDoc} */
  public void write(ObjectOutputStream out, WriteContext context, ViewWriter viewWriter) throws IOException {
    out.writeInt(WindowItemDecoder.SPLIT);
    super.write(out, context, viewWriter);
  }

  /** {@inheritDoc} */
  public void writeSettings(ObjectOutputStream out, WriteContext context) throws IOException {
    out.writeBoolean(isHorizontal);
    out.writeFloat(dividerLocation);
    super.writeSettings(out, context);
  }

  /** {@inheritDoc} */
  public void readSettings(ObjectInputStream in, ReadContext context) throws IOException {
    if (context.getVersion() >= 3) {
      isHorizontal = in.readBoolean();
      dividerLocation = in.readFloat();
    }

    super.readSettings(in, context);
  }

  /**
   * <p>getPropertyObject.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.PropertyMap} object.
   */
  protected PropertyMap getPropertyObject() {
    return getSplitWindowProperties().getMap();
  }

  /**
   * <p>toString.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return "SplitWindow: " + super.toString();
  }

}
