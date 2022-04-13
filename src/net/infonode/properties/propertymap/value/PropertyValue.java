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


// $Id: PropertyValue.java,v 1.10 2005/03/17 16:15:32 jesper Exp $
package net.infonode.properties.propertymap.value;

import net.infonode.properties.propertymap.PropertyMapImpl;
import net.infonode.util.Printer;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * <p>PropertyValue interface.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.10 $
 */
public interface PropertyValue {
  /**
   * <p>get.</p>
   *
   * @param map a {@link net.infonode.properties.propertymap.PropertyMapImpl} object.
   * @return a {@link java.lang.Object} object.
   */
  Object get(PropertyMapImpl map);

  /**
   * <p>getWithDefault.</p>
   *
   * @param object a {@link net.infonode.properties.propertymap.PropertyMapImpl} object.
   * @return a {@link java.lang.Object} object.
   */
  Object getWithDefault(PropertyMapImpl object);

  /**
   * <p>getSubValue.</p>
   *
   * @param object a {@link net.infonode.properties.propertymap.PropertyMapImpl} object.
   * @return a {@link net.infonode.properties.propertymap.value.PropertyValue} object.
   */
  PropertyValue getSubValue(PropertyMapImpl object);

  /**
   * <p>unset.</p>
   */
  void unset();

  /**
   * <p>getParent.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.value.PropertyValue} object.
   */
  PropertyValue getParent();

  /**
   * <p>dump.</p>
   *
   * @param printer a {@link net.infonode.util.Printer} object.
   */
  void dump(Printer printer);

  /**
   * <p>write.</p>
   *
   * @param out a {@link java.io.ObjectOutputStream} object.
   * @throws java.io.IOException if any.
   */
  void write(ObjectOutputStream out) throws IOException;

  /**
   * <p>updateListener.</p>
   *
   * @param enable a boolean.
   */
  void updateListener(boolean enable);

  /**
   * <p>isSerializable.</p>
   *
   * @return a boolean.
   */
  boolean isSerializable();

  /**
   * <p>copyTo.</p>
   *
   * @param propertyMap a {@link net.infonode.properties.propertymap.PropertyMapImpl} object.
   * @return a {@link net.infonode.properties.propertymap.value.PropertyValue} object.
   */
  PropertyValue copyTo(PropertyMapImpl propertyMap);
}
