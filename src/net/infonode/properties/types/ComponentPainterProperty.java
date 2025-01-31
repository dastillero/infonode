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


// $Id: ComponentPainterProperty.java,v 1.4 2005/02/16 11:28:15 jesper Exp $
package net.infonode.properties.types;

import net.infonode.gui.componentpainter.ComponentPainter;
import net.infonode.properties.base.PropertyGroup;
import net.infonode.properties.util.PropertyValueHandler;
import net.infonode.properties.util.ValueHandlerProperty;

/**
 * A property of type {@link net.infonode.gui.componentpainter.ComponentPainter}.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.4 $
 */
public class ComponentPainterProperty extends ValueHandlerProperty {
  /**
   * <p>Constructor for ComponentPainterProperty.</p>
   *
   * @param group a {@link net.infonode.properties.base.PropertyGroup} object.
   * @param name a {@link java.lang.String} object.
   * @param description a {@link java.lang.String} object.
   * @param valueStorage a {@link net.infonode.properties.util.PropertyValueHandler} object.
   */
  public ComponentPainterProperty(PropertyGroup group,
                                  String name,
                                  String description,
                                  PropertyValueHandler valueStorage) {
    super(group, name, ComponentPainter.class, description, valueStorage);
  }

  /**
   * Returns the {@link net.infonode.gui.componentpainter.ComponentPainter} value of this property in a value container.
   *
   * @param valueContainer the value container
   * @return the {@link net.infonode.gui.componentpainter.ComponentPainter} value of this property
   */
  public ComponentPainter get(Object valueContainer) {
    return (ComponentPainter) getValue(valueContainer);
  }

  /**
   * Sets the {@link net.infonode.gui.componentpainter.ComponentPainter} value of this property in a value container.
   *
   * @param valueContainer   the value container
   * @param componentPainter the {@link net.infonode.gui.componentpainter.ComponentPainter} value
   */
  public void set(Object valueContainer, ComponentPainter componentPainter) {
    setValue(valueContainer, componentPainter);
  }

}
