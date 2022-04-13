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


// $Id: RotatableLabel.java,v 1.8 2005/12/04 13:46:04 jesper Exp $
package net.infonode.gui;

import net.infonode.util.Direction;

import javax.swing.*;
import javax.swing.plaf.LabelUI;
import java.awt.*;

/**
 * <p>RotatableLabel class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public class RotatableLabel extends JLabel {
  private RotatableLabelUI ui = new RotatableLabelUI(Direction.RIGHT);

  /**
   * <p>Constructor for RotatableLabel.</p>
   *
   * @param text a {@link java.lang.String} object.
   */
  public RotatableLabel(String text) {
    super(text);
    init();
  }

  /**
   * <p>Constructor for RotatableLabel.</p>
   *
   * @param text a {@link java.lang.String} object.
   * @param icon a {@link javax.swing.Icon} object.
   */
  public RotatableLabel(String text, Icon icon) {
    super(text, icon, LEFT);
    init();
  }

  private void init() {
    super.setUI(ui);
    super.setOpaque(false);
  }

  /**
   * <p>getDirection.</p>
   *
   * @return a {@link net.infonode.util.Direction} object.
   */
  public Direction getDirection() {
    return ui.getDirection();
  }

  /**
   * <p>setDirection.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   */
  public void setDirection(Direction direction) {
    if (ui.getDirection() != direction) {
      ui.setDirection(direction);
      revalidate();
    }
  }

  /**
   * <p>setMirror.</p>
   *
   * @param mirror a boolean.
   */
  public void setMirror(boolean mirror) {
    ui.setMirror(mirror);
    revalidate();
  }

  /**
   * <p>isMirror.</p>
   *
   * @return a boolean.
   */
  public boolean isMirror() {
    return ui.isMirror();
  }

  /** {@inheritDoc} */
  public void setUI(LabelUI ui) {
    // Ignore
  }

  private boolean isVertical() {
    return !ui.getDirection().isHorizontal();
  }

  private Dimension rotateDimension(Dimension dim) {
    return dim == null ? null : isVertical() ? new Dimension(dim.height, dim.width) : dim;
  }

  /**
   * <p>getPreferredSize.</p>
   *
   * @return a {@link java.awt.Dimension} object.
   */
  public Dimension getPreferredSize() {
    return rotateDimension(super.getPreferredSize());
  }

  /**
   * <p>getMinimumSize.</p>
   *
   * @return a {@link java.awt.Dimension} object.
   */
  public Dimension getMinimumSize() {
    return rotateDimension(super.getMinimumSize());
  }

  /**
   * <p>getMaximumSize.</p>
   *
   * @return a {@link java.awt.Dimension} object.
   */
  public Dimension getMaximumSize() {
    return rotateDimension(super.getMaximumSize());
  }

  /** {@inheritDoc} */
  public void setMinimumSize(Dimension minimumSize) {
    super.setMinimumSize(rotateDimension(minimumSize));
  }

  /** {@inheritDoc} */
  public void setMaximumSize(Dimension maximumSize) {
    super.setMaximumSize(rotateDimension(maximumSize));
  }

  /** {@inheritDoc} */
  public void setPreferredSize(Dimension preferredSize) {
    super.setPreferredSize(rotateDimension(preferredSize));
  }

  /** {@inheritDoc} */
  public void setOpaque(boolean opaque) {
  }

}
