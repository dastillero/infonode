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


// $Id: PropertyMapRef.java,v 1.6 2005/02/16 11:28:15 jesper Exp $
package net.infonode.properties.propertymap.ref;

import net.infonode.properties.propertymap.PropertyMapImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * <p>PropertyMapRef interface.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.6 $
 */
public interface PropertyMapRef {
  /**
   * <p>getMap.</p>
   *
   * @param map a {@link net.infonode.properties.propertymap.PropertyMapImpl} object.
   * @return a {@link net.infonode.properties.propertymap.PropertyMapImpl} object.
   */
  PropertyMapImpl getMap(PropertyMapImpl map);

  /**
   * <p>write.</p>
   *
   * @param out a {@link java.io.ObjectOutputStream} object.
   * @throws java.io.IOException if any.
   */
  void write(ObjectOutputStream out) throws IOException;
}
