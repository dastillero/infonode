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


// $Id: TabDropDownListVisiblePolicy.java,v 1.11 2005/02/16 11:28:15 jesper Exp $
package net.infonode.tabbedpanel;

/**
 * TabDropDownListVisiblePolicy tells the tabbed panel when to show a drop down
 * list of tabs.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.11 $
 * @see TabbedPanel
 * @see TabbedPanelProperties
 * @see TabbedPanelProperties
 * @since ITP 1.1.0
 */
public enum TabDropDownListVisiblePolicy {
  /**
   * Never drop down list policy. This means that no drop down list will be shown
   * in the tabbed panel.
   */
  NEVER,

  /**
   * More than one tab list policy. This means that a drop down list will be shown
   * if there are more than one tab in the tabbed panel.
   */
  MORE_THAN_ONE_TAB,

  /**
   * Tabs not visible list policy. This means that a drop down list will be shown when
   * there are tabs are not entirely visible, i.e. scrolled out.
   */
  TABS_NOT_VISIBLE;
}
