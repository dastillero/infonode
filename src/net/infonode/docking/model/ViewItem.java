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


// $Id: ViewItem.java,v 1.8 2005/06/19 20:56:31 jesper Exp $
package net.infonode.docking.model;

import net.infonode.docking.DockingWindow;
import net.infonode.docking.View;
import net.infonode.docking.internal.ReadContext;
import net.infonode.docking.internal.WriteContext;
import net.infonode.docking.properties.ViewProperties;
import net.infonode.properties.propertymap.PropertyMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * <p>ViewItem class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.8 $
 */
public class ViewItem extends WindowItem {
  private ViewProperties viewProperties = new ViewProperties();

  /**
   * <p>Constructor for ViewItem.</p>
   */
  public ViewItem() {
  }

  /**
   * <p>Constructor for ViewItem.</p>
   *
   * @param viewItem a {@link net.infonode.docking.model.ViewItem} object.
   */
  public ViewItem(ViewItem viewItem) {
    super(viewItem);
  }

  /**
   * <p>getPropertyObject.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.PropertyMap} object.
   */
  protected PropertyMap getPropertyObject() {
    return viewProperties.getMap();
  }

  /** {@inheritDoc} */
  protected DockingWindow createWindow(ViewReader viewReader, ArrayList childWindows) {
    return null;
  }

  /**
   * <p>Getter for the field <code>viewProperties</code>.</p>
   *
   * @return a {@link net.infonode.docking.properties.ViewProperties} object.
   */
  public ViewProperties getViewProperties() {
    return viewProperties;
  }

  /** {@inheritDoc} */
  public void write(ObjectOutputStream out, WriteContext context, ViewWriter viewWriter) throws IOException {
    out.writeInt(WindowItemDecoder.VIEW);
    DockingWindow window = getConnectedWindow();
    viewWriter.writeView((View) getConnectedWindow(), out, context);
    out.writeBoolean(window != null && !window.isMinimized() && !window.isUndocked() && window.getRootWindow() != null);
  }

  /** {@inheritDoc} */
  public DockingWindow read(ObjectInputStream in, ReadContext context, ViewReader viewReader) throws IOException {
    return in.readBoolean() ? getConnectedWindow() : null;
  }

  /**
   * <p>copy.</p>
   *
   * @return a {@link net.infonode.docking.model.WindowItem} object.
   */
  public WindowItem copy() {
    return new ViewItem(this);
  }

}
