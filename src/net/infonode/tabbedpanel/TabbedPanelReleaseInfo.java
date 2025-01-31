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


// $Id: TabbedPanelReleaseInfo.java,v 1.8 2004/09/22 14:33:49 jesper Exp $
package net.infonode.tabbedpanel;

import net.infonode.util.AntUtils;
import net.infonode.util.ReleaseInfo;

/**
 * Tabbed Panel release information. Contains product name, vendor, build time
 * and version info for the current release.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.8 $
 */
public class TabbedPanelReleaseInfo {
  private static ReleaseInfo productInfo =
      new ReleaseInfo("InfoNode Tabbed Panel GPL",
                      "NNL Technology AB",
                      AntUtils.getBuildTime(1235486906703L),
                      AntUtils.createProductVersion(1, 8, 0),
                      "GNU General Public License, Version 2",
                      "http://www.infonode.net");

  private TabbedPanelReleaseInfo() {
  }

  /**
   * Gets the release information
   *
   * @return release information
   */
  public static ReleaseInfo getReleaseInfo() {
    return productInfo;
  }
}
