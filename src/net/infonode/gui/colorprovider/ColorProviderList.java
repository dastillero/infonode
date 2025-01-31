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


// $Id: ColorProviderList.java,v 1.4 2005/02/16 11:28:10 jesper Exp $
package net.infonode.gui.colorprovider;

import java.awt.*;

/**
 * <p>ColorProviderList class.</p>
 *
 * @author $Author: jesper $
 * @version $Revision: 1.4 $
 */
public class ColorProviderList extends AbstractColorProvider {
  private ColorProvider[] providers;

  /**
   * <p>Constructor for ColorProviderList.</p>
   *
   * @param colorProvider1 a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   * @param colorProvider2 a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   */
  public ColorProviderList(ColorProvider colorProvider1, ColorProvider colorProvider2) {
    this(new ColorProvider[]{colorProvider1, colorProvider2});
  }

  /**
   * <p>Constructor for ColorProviderList.</p>
   *
   * @param colorProvider1 a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   * @param colorProvider2 a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   * @param colorProvider3 a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   */
  public ColorProviderList(ColorProvider colorProvider1, ColorProvider colorProvider2, ColorProvider colorProvider3) {
    this(new ColorProvider[]{colorProvider1, colorProvider2, colorProvider3});
  }

  /**
   * <p>Constructor for ColorProviderList.</p>
   *
   * @param providers an array of {@link net.infonode.gui.colorprovider.ColorProvider} objects.
   */
  public ColorProviderList(ColorProvider[] providers) {
    this.providers = (ColorProvider[]) providers.clone();
  }

  /** {@inheritDoc} */
  public Color getColor(Component component) {
    Color c = null;

    for (int i = 0; i < providers.length; i++) {
      c = providers[i].getColor(component);

      if (c != null)
        break;
    }

    return c;
  }

  /**
   * <p>getColor.</p>
   *
   * @return a {@link java.awt.Color} object.
   */
  public Color getColor() {
    Color c = null;

    for (int i = 0; i < providers.length; i++) {
      c = providers[i].getColor();

      if (c != null)
        break;
    }

    return c;
  }
}
