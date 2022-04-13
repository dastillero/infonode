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


// $Id: TabData.java,v 1.4 2009/02/05 15:57:56 jesper Exp $
package net.infonode.tabbedpanel.theme.internal.laftheme;

import java.awt.Dimension;
import java.util.ArrayList;

import net.infonode.gui.DimensionUtil;
import net.infonode.tabbedpanel.Tab;
import net.infonode.tabbedpanel.TabbedPanel;
import net.infonode.util.Direction;

class TabData {
  private final ArrayList tabList = new ArrayList();

  private final ArrayList visibleTabRects = new ArrayList();

  private TabbedPanel tabbedPanel;

  private Direction areaOrientation;

  private int tabAreaHeight;

  private int tabAreaWidth;

  private int selectedTabPainterIndex;

  private Dimension tpInternalSize;

  private Tab preTab;
  private Tab postTab;

  /**
   * <p>Constructor for TabData.</p>
   */
  public TabData() {
    reset();
  }

  /**
   * <p>reset.</p>
   */
  public void reset() {
    tabList.clear();
    visibleTabRects.clear();
    tabbedPanel = null;
    areaOrientation = null;
    tabAreaHeight = 0;
    tabAreaWidth = 0;
    selectedTabPainterIndex = -1;
    tpInternalSize = null;
    preTab = null;
    postTab = null;
  }

  /**
   * <p>Getter for the field <code>tabList</code>.</p>
   *
   * @return a {@link java.util.ArrayList} object.
   */
  public ArrayList getTabList() {
    return tabList;
  }

  /**
   * <p>Getter for the field <code>visibleTabRects</code>.</p>
   *
   * @return a {@link java.util.ArrayList} object.
   */
  public ArrayList getVisibleTabRects() {
    return visibleTabRects;
  }

  /**
   * <p>Getter for the field <code>areaOrientation</code>.</p>
   *
   * @return a {@link net.infonode.util.Direction} object.
   */
  public Direction getAreaOrientation() {
    return areaOrientation;
  }

  /**
   * <p>Getter for the field <code>tabbedPanel</code>.</p>
   *
   * @return a {@link net.infonode.tabbedpanel.TabbedPanel} object.
   */
  public TabbedPanel getTabbedPanel() {
    return tabbedPanel;
  }

  /**
   * <p>initialize.</p>
   *
   * @param tabbedPanel a {@link net.infonode.tabbedpanel.TabbedPanel} object.
   */
  public void initialize(TabbedPanel tabbedPanel) {
    this.tabbedPanel = tabbedPanel;
    areaOrientation = tabbedPanel.getProperties().getTabAreaOrientation();
    tpInternalSize = DimensionUtil.getInnerDimension(tabbedPanel.getSize(), tabbedPanel.getInsets());
  }

  /**
   * <p>getTabbedPanelSize.</p>
   *
   * @return a {@link java.awt.Dimension} object.
   */
  public Dimension getTabbedPanelSize() {
    return tpInternalSize;
  }

  /**
   * <p>getTabbedPanelWidth.</p>
   *
   * @return a int.
   */
  public int getTabbedPanelWidth() {
    return tpInternalSize.width;
  }

  /**
   * <p>getTabbedPanelHeight.</p>
   *
   * @return a int.
   */
  public int getTabbedPanelHeight() {
    return tpInternalSize.height;
  }

  /**
   * <p>isHorizontalLayout.</p>
   *
   * @return a boolean.
   */
  public boolean isHorizontalLayout() {
    return !areaOrientation.isHorizontal();
  }

  /**
   * <p>Getter for the field <code>selectedTabPainterIndex</code>.</p>
   *
   * @return a int.
   */
  public int getSelectedTabPainterIndex() {
    return selectedTabPainterIndex;
  }

  /**
   * <p>Setter for the field <code>selectedTabPainterIndex</code>.</p>
   *
   * @param selectedTabPainterIndex a int.
   */
  public void setSelectedTabPainterIndex(int selectedTabPainterIndex) {
    this.selectedTabPainterIndex = selectedTabPainterIndex;
  }

  /**
   * <p>getTabCount.</p>
   *
   * @return a int.
   */
  public int getTabCount() {
    return tabList.size();
  }

  /**
   * <p>Getter for the field <code>tabAreaHeight</code>.</p>
   *
   * @return a int.
   */
  public int getTabAreaHeight() {
    return tabAreaHeight;
  }

  /**
   * <p>Setter for the field <code>tabAreaHeight</code>.</p>
   *
   * @param tabAreaHeight a int.
   */
  public void setTabAreaHeight(int tabAreaHeight) {
    this.tabAreaHeight = tabAreaHeight;
  }

  /**
   * <p>Getter for the field <code>tabAreaWidth</code>.</p>
   *
   * @return a int.
   */
  public int getTabAreaWidth() {
    return tabAreaWidth;
  }

  /**
   * <p>Setter for the field <code>tabAreaWidth</code>.</p>
   *
   * @param tabAreaWidth a int.
   */
  public void setTabAreaWidth(int tabAreaWidth) {
    this.tabAreaWidth = tabAreaWidth;
  }

  /**
   * <p>Getter for the field <code>postTab</code>.</p>
   *
   * @return a {@link net.infonode.tabbedpanel.Tab} object.
   */
  public Tab getPostTab() {
    return postTab;
  }

  /**
   * <p>Setter for the field <code>postTab</code>.</p>
   *
   * @param postTab a {@link net.infonode.tabbedpanel.Tab} object.
   */
  public void setPostTab(Tab postTab) {
    this.postTab = postTab;
  }

  /**
   * <p>Getter for the field <code>preTab</code>.</p>
   *
   * @return a {@link net.infonode.tabbedpanel.Tab} object.
   */
  public Tab getPreTab() {
    return preTab;
  }

  /**
   * <p>Setter for the field <code>preTab</code>.</p>
   *
   * @param preTab a {@link net.infonode.tabbedpanel.Tab} object.
   */
  public void setPreTab(Tab preTab) {
    this.preTab = preTab;
  }
}
