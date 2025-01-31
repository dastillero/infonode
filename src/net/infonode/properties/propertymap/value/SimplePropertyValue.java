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


// $Id: SimplePropertyValue.java,v 1.14 2005/03/17 16:15:32 jesper Exp $
package net.infonode.properties.propertymap.value;

import net.infonode.properties.propertymap.PropertyMapImpl;
import net.infonode.util.Printer;
import net.infonode.util.Utils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * <p>SimplePropertyValue class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.14 $
 */
public class SimplePropertyValue implements PropertyValue {
  private final Object value;

  /**
   * <p>Constructor for SimplePropertyValue.</p>
   *
   * @param value a {@link java.lang.Object} object.
   */
  public SimplePropertyValue(Object value) {
    this.value = value;
  }

  /** {@inheritDoc} */
  public void updateListener(boolean enable) {
  }

  /**
   * <p>getParent.</p>
   *
   * @return a {@link net.infonode.properties.propertymap.value.PropertyValue} object.
   */
  public PropertyValue getParent() {
    return null;
  }

  /** {@inheritDoc} */
  public Object get(PropertyMapImpl object) {
    return value;
  }

  /** {@inheritDoc} */
  public Object getWithDefault(PropertyMapImpl object) {
    return value;
  }

  /** {@inheritDoc} */
  public PropertyValue getSubValue(PropertyMapImpl object) {
    return null;
  }

  /**
   * <p>unset.</p>
   */
  public void unset() {
  }

  /**
   * <p>toString.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String toString() {
    return String.valueOf(value);
  }

  /** {@inheritDoc} */
  public void dump(Printer printer) {
    printer.println(toString());
  }

  /** {@inheritDoc} */
  public boolean equals(Object obj) {
    return obj != null &&
           obj instanceof SimplePropertyValue &&
           Utils.equals(((SimplePropertyValue) obj).value, value);
  }

  /**
   * <p>hashCode.</p>
   *
   * @return a int.
   */
  public int hashCode() {
    return value.hashCode();
  }

  /** {@inheritDoc} */
  public void write(ObjectOutputStream out) throws IOException {
    out.writeInt(ValueDecoder.SIMPLE);
    out.writeObject(value);
  }

  /**
   * <p>isSerializable.</p>
   *
   * @return a boolean.
   */
  public boolean isSerializable() {
    return value instanceof Serializable;
  }

  /**
   * <p>decode.</p>
   *
   * @param in a {@link java.io.ObjectInputStream} object.
   * @return a {@link net.infonode.properties.propertymap.value.PropertyValue} object.
   * @throws java.io.IOException if any.
   */
  public static PropertyValue decode(ObjectInputStream in) throws IOException {
    try {
      return new SimplePropertyValue(in.readObject());
    }
    catch (ClassNotFoundException e) {
      throw new IOException(e.getMessage());
    }
  }

  /**
   * <p>skip.</p>
   *
   * @param in a {@link java.io.ObjectInputStream} object.
   * @throws java.io.IOException if any.
   */
  public static void skip(ObjectInputStream in) throws IOException {
    try {
      in.readObject();
    }
    catch (ClassNotFoundException e) {
      throw new IOException(e.getMessage());
    }
  }

  /** {@inheritDoc} */
  public PropertyValue copyTo(PropertyMapImpl propertyMap) {
    return this;
  }

}
