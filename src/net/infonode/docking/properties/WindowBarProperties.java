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


// $Id: WindowBarProperties.java,v 1.14 2004/08/11 13:47:58 jesper Exp $
package net.infonode.docking.properties;

import net.infonode.gui.DynamicUIManager;
import net.infonode.gui.DynamicUIManagerListener;
import net.infonode.properties.gui.util.ComponentProperties;
import net.infonode.properties.propertymap.*;
import net.infonode.properties.types.IntegerProperty;
import net.infonode.tabbedpanel.TabbedUIDefaults;
import net.infonode.tabbedpanel.titledtab.TitledTabSizePolicy;
import net.infonode.util.Direction;

import java.awt.*;
import java.beans.PropertyChangeEvent;

/**
 * Properties and property values for window bars.
 *
 * @author $Author: jesper $
 * @version $Revision: 1.14 $
 */
public class WindowBarProperties extends PropertyMapContainer {
  /**
   * Property group containing all window bar properties.
   */
  public static final PropertyMapGroup PROPERTIES = new PropertyMapGroup("Window Bar Properties", "");

  /**
   * The window bar component property values.
   */
  public static final PropertyMapProperty COMPONENT_PROPERTIES = new PropertyMapProperty(PROPERTIES,
                                                                                         "Component Properties",
                                                                                         "The window bar component properties.",
                                                                                         ComponentProperties.PROPERTIES);

  /**
   * Inside this distance from the content panel edge the user can resize the content panel.
   */
  public static final IntegerProperty CONTENT_PANEL_EDGE_RESIZE_DISTANCE =
      IntegerProperty.createPositive(PROPERTIES,
                                     "Content Panel Edge Resize Distance",
                                     "Inside this distance from the content panel edge the user can resize the content panel.",
                                     2,
                                     PropertyMapValueHandler.INSTANCE);

  /**
   * The minimum width of the window bar. If greater than 0, the window bar will always be visible and the user can drag
   * windows to it.
   */
  public static final IntegerProperty MINIMUM_WIDTH =
      IntegerProperty.createPositive(PROPERTIES,
                                     "Minimum Width",
                                     "The minimum width of the window bar. If greater than 0, the window bar will " +
                                     "always be visible and the user can drag windows to it.",
                                     2,
                                     PropertyMapValueHandler.INSTANCE);

  /**
   * Properties for the tab window used by this window bar.
   */
  public static final PropertyMapProperty TAB_WINDOW_PROPERTIES =
      new PropertyMapProperty(PROPERTIES, "Tab Window Properties", "", TabWindowProperties.PROPERTIES);

  private static WindowBarProperties COMMON_DEFAULT_VALUES = new WindowBarProperties(PROPERTIES.getDefaultMap());
  private static WindowBarProperties[] DEFAULT_VALUES = new WindowBarProperties[4];

  static {
    {
      WindowBarProperties p = COMMON_DEFAULT_VALUES;

      p.setMinimumWidth(4);
      p.setContentPanelEdgeResizeEdgeDistance(6);

      p.getTabWindowProperties().getTabbedPanelProperties()
          .setTabDeselectable(true)
          .setAutoSelectTab(false);

      p.getTabWindowProperties().getTabbedPanelProperties().getContentPanelProperties().getComponentProperties()
          .setInsets(new Insets(4, 4, 4, 4));
    }

    {
      WindowTabProperties p = COMMON_DEFAULT_VALUES.getTabWindowProperties().getTabProperties();

      p.getTitledTabProperties()
          .setSizePolicy(TitledTabSizePolicy.EQUAL_SIZE)
//          .addSuperObject(HighlightedTabSetup.createTabProperties())
          .setHighlightedRaised(0);

/*      p.getFocusedProperties()
          .setBackgroundColor(Color.YELLOW);

  */
      p.getTitledTabProperties().getNormalProperties()
          .getComponentProperties().setInsets(new Insets(2, 8, 2, 8));

      p.getNormalButtonProperties().getCloseButtonProperties().setVisible(true);
      p.getNormalButtonProperties().getRestoreButtonProperties().setVisible(true);
    }

    for (int i = 0; i < Direction.DIRECTIONS.length; i++) {
      Direction dir = Direction.DIRECTIONS[i];
      WindowBarProperties properties = new WindowBarProperties(COMMON_DEFAULT_VALUES);
      properties.getTabWindowProperties().getTabbedPanelProperties().setTabAreaOrientation(dir);
      properties.getTabWindowProperties().getTabProperties().getTitledTabProperties().
          getNormalProperties().setDirection(dir.isHorizontal() ? Direction.DOWN : Direction.RIGHT);
      DEFAULT_VALUES[dir.getValue()] = properties;
    }

    DynamicUIManager.getInstance().addListener(new DynamicUIManagerListener() {
      public void lookAndFeelChanged() {
        updateVisualProperties();
      }

      public void propertyChange(PropertyChangeEvent event) {
        updateVisualProperties();
      }
    });

    updateVisualProperties();
  }

  private static void updateVisualProperties() {
    WindowTabProperties p = COMMON_DEFAULT_VALUES.getTabWindowProperties().getTabProperties();

    p.getTitledTabProperties().getNormalProperties().getComponentProperties().setBackgroundColor(TabbedUIDefaults.getHighlightedStateBackground());
  }

  /**
   * Creates a property object which inherits the default property values.
   *
   * @param location the location of the window bar
   * @return a property object which inherits the default property values
   */
  public static WindowBarProperties createDefault(Direction location) {
    return new WindowBarProperties(DEFAULT_VALUES[location.getValue()]);
  }

  /**
   * Creates an empty property object.
   */
  public WindowBarProperties() {
    super(PropertyMapFactory.create(PROPERTIES));
  }

  /**
   * Creates a property object containing the map.
   *
   * @param map the property map
   */
  public WindowBarProperties(PropertyMap map) {
    super(map);
  }

  /**
   * Creates a property object that inherit values from another property object.
   *
   * @param inheritFrom the object from which to inherit property values
   */
  public WindowBarProperties(WindowBarProperties inheritFrom) {
    super(PropertyMapFactory.create(inheritFrom.getMap()));
  }

  /**
   * Adds a super object from which property values are inherited.
   *
   * @param properties the object from which to inherit property values
   * @return this
   */
  public WindowBarProperties addSuperObject(WindowBarProperties properties) {
    getMap().addSuperMap(properties.getMap());
    return this;
  }

  /**
   * Returns the distance from the content panel edge which inside the user can resize the content panel.
   * @return the distance from the content panel edge which inside the user can resize the content panel
   */
  public int getContentPanelEdgeResizeDistance() {
    return CONTENT_PANEL_EDGE_RESIZE_DISTANCE.get(getMap());
  }

  /**
   * Sets the distance from the content panel edge which inside the user can resize the content panel.
   *
   * @param width the distance from the content panel edge which inside the user can resize the content panel
   * @return this
   */
  public WindowBarProperties setContentPanelEdgeResizeEdgeDistance(int width) {
    CONTENT_PANEL_EDGE_RESIZE_DISTANCE.set(getMap(), width);
    return this;
  }

  /**
   * Returns the minimum width of the window bar.
   * @return the minimum width of the window bar
   */
  public int getMinimumWidth() {
    return MINIMUM_WIDTH.get(getMap());
  }

  /**
   * Sets the minimum width of the window bar.
   *
   * @param width the minimum width of the window bar
   * @return this
   */
  public WindowBarProperties setMinimumWidth(int width) {
    MINIMUM_WIDTH.set(getMap(), width);
    return this;
  }

  /**
   * Returns the tab window property values.
   * @return the tab window property values
   */
  public TabWindowProperties getTabWindowProperties() {
    return new TabWindowProperties(TAB_WINDOW_PROPERTIES.get(getMap()));
  }

  /**
   * Returns the property values for the window bar component.
   * @return the property values for the window bar component
   */
  public ComponentProperties getComponentProperties() {
    return new ComponentProperties(COMPONENT_PROPERTIES.get(getMap()));
  }

}
