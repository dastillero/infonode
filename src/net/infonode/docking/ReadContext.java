/** 
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


// $Id: ReadContext.java,v 1.5 2004/07/06 15:00:02 jesper Exp $
package net.infonode.docking;

/**
 *
 * @author $Author: jesper $
 * @version $Revision: 1.5 $
 */
class ReadContext {
  private ViewSerializer viewSerializer;
  private int version;
  private boolean propertyValuesAvailable;
  private boolean readPropertiesEnabled;

  ReadContext(ViewSerializer viewSerializer,
                     int version,
                     boolean propertyValuesAvailable,
                     boolean readProperties) {
    this.viewSerializer = viewSerializer;
    this.version = version;
    this.propertyValuesAvailable = propertyValuesAvailable;
    this.readPropertiesEnabled = readProperties;
  }

  ViewSerializer getViewSerializer() {
    return viewSerializer;
  }

  boolean isPropertyValuesAvailable() {
    return propertyValuesAvailable;
  }

  boolean getReadPropertiesEnabled() {
    return readPropertiesEnabled;
  }
}
