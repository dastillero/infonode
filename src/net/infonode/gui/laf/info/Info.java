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


// $Id: Info.java,v 1.3 2004/09/21 08:42:19 johan Exp $
package net.infonode.gui.laf.info;

import net.infonode.gui.ReleaseInfoDialog;
import net.infonode.gui.laf.InfoNodeLookAndFeelReleaseInfo;

/**
 * <p>Info class.</p>
 *
 * @author $Author: johan $
 * @version $Revision: 1.3 $
 */
public class Info {
  /**
   * <p>main.</p>
   *
   * @param args an array of {@link java.lang.String} objects.
   */
  public static final void main(String[] args) {
    ReleaseInfoDialog.showDialog(InfoNodeLookAndFeelReleaseInfo.getReleaseInfo(), null);
    System.exit(0);
  }
}
