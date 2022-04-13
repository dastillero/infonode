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


// $Id: AbstractPolygonBorder.java,v 1.18 2005/12/04 13:46:04 jesper Exp $

package net.infonode.gui.shaped.border;

import net.infonode.gui.GraphicsUtil;
import net.infonode.gui.HighlightPainter;
import net.infonode.gui.InsetsUtil;
import net.infonode.gui.colorprovider.BackgroundPainterColorProvider;
import net.infonode.gui.colorprovider.ColorProvider;
import net.infonode.gui.colorprovider.FixedColorProvider;
import net.infonode.gui.shaped.ShapedUtil;
import net.infonode.gui.shaped.panel.ShapedPanel;

import java.awt.*;

/**
 * <p>Abstract AbstractPolygonBorder class.</p>
 *
 * @author johan
 * @version $Id: $Id
 */
abstract public class AbstractPolygonBorder extends AbstractShapedBorder {
  private static final long serialVersionUID = 1;

  private static final Insets HIGHLIGHT_INSETS = new Insets(1, 1, 0, 0);
  private ColorProvider lineColor;
  private ColorProvider highlightColor = new FixedColorProvider(new Color(255, 255, 255));
  private ColorProvider middleColor;
  private ColorProvider shadowColor;

  /**
   * <p>Constructor for AbstractPolygonBorder.</p>
   *
   * @param lineColor a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   */
  protected AbstractPolygonBorder(ColorProvider lineColor) {
    this(lineColor, FixedColorProvider.WHITE);
  }

  /**
   * <p>Constructor for AbstractPolygonBorder.</p>
   *
   * @param lineColor a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   * @param highlightColor a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   */
  protected AbstractPolygonBorder(ColorProvider lineColor, ColorProvider highlightColor) {
    this(lineColor, highlightColor, BackgroundPainterColorProvider.INSTANCE, null);
  }

  /**
   * <p>Constructor for AbstractPolygonBorder.</p>
   *
   * @param lineColor a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   * @param highlightColor a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   * @param middleColor a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   * @param shadowColor a {@link net.infonode.gui.colorprovider.ColorProvider} object.
   */
  protected AbstractPolygonBorder(ColorProvider lineColor,
                                  ColorProvider highlightColor,
                                  ColorProvider middleColor,
                                  ColorProvider shadowColor) {
    this.lineColor = lineColor;
    this.highlightColor = highlightColor;
    this.middleColor = middleColor;
    this.shadowColor = shadowColor;
  }

  /** {@inheritDoc} */
  public Shape getShape(Component c, int x, int y, int width, int height) {
    int w = ShapedUtil.getWidth(c, width, height);
    int h = ShapedUtil.getHeight(c, width, height);
    Polygon polygon = getPolygon(c, x, y, w, h);

    //printPoints(polygon);

    //System.out.println("Polygon: width=" + w + " height=" + h);

    return polygon;
  }

  /**
   * <p>isBorderOpaque.</p>
   *
   * @return a boolean.
   */
  public boolean isBorderOpaque() {
    return false;
  }

  /** {@inheritDoc} */
  public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
    Shape clip = g.getClip();
    g.clipRect(x, y, width, height);

    try {
      int w = ShapedUtil.getWidth(c, width, height);
      height = ShapedUtil.getHeight(c, width, height);
      width = w;

      Polygon polygon = getPolygon(c, x, y, width, height);
      Graphics2D g2 = (Graphics2D) g;

      if (highlightColor != null) {
        paintHighlight(c, g2, polygon, width, height);
      }

      if (lineColor != null) {
        g.setColor(lineColor.getColor());
        paintPolygon(c, g2, polygon, width, height);
      }
    }
    finally {
      g.setClip(clip);
    }
  }

  /** {@inheritDoc} */
  public Insets getBorderInsets(Component c) {
    Insets insets = getShapedBorderInsets(c);
    insets = ShapedUtil.transformInsets(c, insets);
    return highlightColor != null ? InsetsUtil.add(getShapedBorderHighlightInsets(c), insets) : insets;
  }

  /**
   * <p>getShapedBorderInsets.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a {@link java.awt.Insets} object.
   */
  protected Insets getShapedBorderInsets(Component c) {
    return new Insets(0, 0, 0, 0);
  }

  /**
   * <p>getShapedBorderHighlightInsets.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a {@link java.awt.Insets} object.
   */
  protected Insets getShapedBorderHighlightInsets(Component c) {
    return HIGHLIGHT_INSETS;
  }

  /**
   * <p>createPolygon.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param width a int.
   * @param height a int.
   * @return a {@link java.awt.Polygon} object.
   */
  protected Polygon createPolygon(Component c, int width, int height) {
    return new Polygon();
  }

  /**
   * <p>paintPolygon.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param g a {@link java.awt.Graphics2D} object.
   * @param polygon a {@link java.awt.Polygon} object.
   * @param width a int.
   * @param height a int.
   */
  protected void paintPolygon(Component c, Graphics2D g, Polygon polygon, int width, int height) {
    int i = 0;

    while (i < polygon.npoints) {
      if (lineIsDrawn(i, polygon)) {
        int ni = (i + 1) % polygon.npoints;
        GraphicsUtil.drawOptimizedLine(g, polygon.xpoints[i],
                                       polygon.ypoints[i],
                                       polygon.xpoints[ni],
                                       polygon.ypoints[ni]);
      }

      i++;
    }
  }

  /**
   * <p>paintHighlight.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @param g a {@link java.awt.Graphics2D} object.
   * @param polygon a {@link java.awt.Polygon} object.
   * @param width a int.
   * @param height a int.
   */
  protected void paintHighlight(Component c, Graphics2D g, Polygon polygon, int width, int height) {
    Color c1 = highlightColor == null ? null : highlightColor.getColor(c);
    Color c2 = middleColor.getColor(c);
    Color c3 = shadowColor == null ? null : shadowColor.getColor(c);

    boolean clockWise = isPointsClockwise(c);

    for (int i = 0; i < polygon.npoints; i++) {
      int ni = (i + 1) % polygon.npoints;

      if (lineIsDrawn(i, polygon)) {
        HighlightPainter.drawLine(g,
                                  polygon.xpoints[i],
                                  polygon.ypoints[i],
                                  polygon.xpoints[ni],
                                  polygon.ypoints[ni],
                                  clockWise,
                                  true,
                                  c1, c2, c3);
      }
    }
  }

  /**
   * <p>lineIsDrawn.</p>
   *
   * @param index a int.
   * @param polygon a {@link java.awt.Polygon} object.
   * @return a boolean.
   */
  protected boolean lineIsDrawn(int index, Polygon polygon) {
    return true;
  }

/*  protected int getWidth(Component c) {
    Insets i = getOuterInsets(c);
    Direction d = ShapedUtil.getDirection(c);
    if (d == Direction.UP || d == Direction.DOWN)
      return c.getHeight() - i.top - i.bottom;

    return c.getWidth() - i.left - i.right;
  }

  protected int getHeight(Component c) {
    Insets i = getOuterInsets(c);
    Direction d = ShapedUtil.getDirection(c);
    if (d == Direction.UP || d == Direction.DOWN)
      return c.getWidth() - i.left - i.right;

    return c.getHeight() - i.top - i.bottom;
  }
*/
  /**
   * <p>isHighlightable.</p>
   *
   * @param deltaX a int.
   * @param deltaY a int.
   * @return a boolean.
   */
  protected boolean isHighlightable(int deltaX, int deltaY) {
    return deltaX > deltaY;
  }

  /**
   * <p>isPointsClockwise.</p>
   *
   * @param c a {@link java.awt.Component} object.
   * @return a boolean.
   */
  protected boolean isPointsClockwise(Component c) {
    if (c instanceof ShapedPanel)
      return !(((ShapedPanel) c).isHorizontalFlip() ^ ((ShapedPanel) c).isVerticalFlip());

    return true;
  }

  /**
   * <p>getHighlightOffsetX.</p>
   *
   * @param deltaX a int.
   * @param deltaY a int.
   * @return a int.
   */
  protected int getHighlightOffsetX(int deltaX, int deltaY) {
    return deltaY - deltaX > 0 ? (deltaX + deltaY > 0 ? -1 : 0) : (deltaX + deltaY > 0 ? 0 : 1); //-deltaY > deltaX ? 1 : 0;
  }

  /**
   * <p>getHighlightOffsetY.</p>
   *
   * @param deltaX a int.
   * @param deltaY a int.
   * @return a int.
   */
  protected int getHighlightOffsetY(int deltaX, int deltaY) {
    return deltaY - deltaX > 0 ? (deltaX + deltaY > 0 ? 0 : -1) : (deltaX + deltaY > 0 ? 1 : 0); //-deltaY > deltaX ? 1 : 0;
  }

  /**
   * <p>setPoint.</p>
   *
   * @param polygon a {@link java.awt.Polygon} object.
   * @param x a int.
   * @param y a int.
   */
  protected void setPoint(Polygon polygon, int x, int y) {
    polygon.xpoints[polygon.npoints] = x;
    polygon.ypoints[polygon.npoints] = y;
    polygon.npoints++;
  }

  private Polygon getPolygon(Component c, int x, int y, int width, int height) {
    Polygon polygon = createPolygon(c, width, height);
    flipPolygon(c, polygon, width, height);
    rotatePolygon(c, polygon, width, height);
    fixGraphicsOffset(c, polygon, x, y);
    return polygon;
  }

  private void flipPolygon(Component c, Polygon polygon, int width, int height) {
    if (c instanceof ShapedPanel) {
      if (((ShapedPanel) c).isHorizontalFlip()) {
        for (int i = 0; i < polygon.npoints; i++)
          polygon.xpoints[i] = Math.abs(width - polygon.xpoints[i]) - 1;
      }

      if (((ShapedPanel) c).isVerticalFlip()) {
        for (int i = 0; i < polygon.npoints; i++)
          polygon.ypoints[i] = Math.abs(height - polygon.ypoints[i]) - 1;
      }
    }
  }

  private void rotatePolygon(Component c, Polygon polygon, int width, int height) {
    ShapedUtil.rotate(polygon, ShapedUtil.getDirection(c), width, height);
  }

  private void fixGraphicsOffset(Component c, Polygon polygon, int x, int y) {
    for (int i = 0; i < polygon.npoints; i++) {
      polygon.xpoints[i] += x;
      polygon.ypoints[i] += y;
    }
  }

}
