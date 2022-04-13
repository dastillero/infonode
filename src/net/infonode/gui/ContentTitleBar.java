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


// $Id: ContentTitleBar.java,v 1.10 2009/02/05 15:57:56 jesper Exp $

package net.infonode.gui;

import java.awt.*;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import net.infonode.gui.hover.panel.HoverableShapedPanel;
import net.infonode.util.Alignment;
import net.infonode.util.Direction;

/**
 * <p>ContentTitleBar class.</p>
 *
 * @author johan
 * @version $Id: $Id
 */
public class ContentTitleBar extends HoverableShapedPanel {
  private final ComponentPaintChecker repaintChecker;
  private JComponent[] leftTitleComponents;
  private JComponent[] rightTitleComponents;
  private Insets[] leftTitleComponentsInsets;
  private Insets[] rightTitleComponentsInsets;

  private boolean flipTitleComponents = false;

  private GridBagConstraints constraints = new GridBagConstraints();

  private Insets labelInsets = InsetsUtil.EMPTY_INSETS;
  private Alignment labelAlignment = Alignment.LEFT;

  private final RotatableLabel label = new RotatableLabel("") {
    public Dimension getMinimumSize() {
      Dimension d = super.getMinimumSize();
      if (getDirection().isHorizontal())
        return new Dimension(0, d.height);

      return new Dimension(d.width, 0);
    }
  };

  /**
   * <p>Constructor for ContentTitleBar.</p>
   */
  public ContentTitleBar() {
    this(null);
  }

  /**
   * <p>Constructor for ContentTitleBar.</p>
   *
   * @param hoveredComponent a {@link java.awt.Component} object.
   */
  public ContentTitleBar(Component hoveredComponent) {
    super(new GridBagLayout(), null, hoveredComponent);
    repaintChecker = new ComponentPaintChecker(this);
    add(label);

    updateLayout();
  }

  /**
   * <p>Getter for the field <code>label</code>.</p>
   *
   * @return a {@link javax.swing.JLabel} object.
   */
  public JLabel getLabel() {
    return label;
  }

  /**
   * <p>getText.</p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getText() {
    return label.getText();
  }

  /**
   * <p>getIcon.</p>
   *
   * @return a {@link javax.swing.Icon} object.
   */
  public Icon getIcon() {
    return label.getIcon();
  }

  /**
   * <p>setIcon.</p>
   *
   * @param icon a {@link javax.swing.Icon} object.
   */
  public void setIcon(Icon icon) {
    if (label.getIcon() != icon) {
      label.setIcon(icon);

      doUpdate();
    }
  }

  /**
   * <p>Getter for the field <code>labelAlignment</code>.</p>
   *
   * @return a {@link net.infonode.util.Alignment} object.
   */
  public Alignment getLabelAlignment() {
    return labelAlignment;
  }

  /**
   * <p>Setter for the field <code>labelAlignment</code>.</p>
   *
   * @param labelAlignment a {@link net.infonode.util.Alignment} object.
   */
  public void setLabelAlignment(Alignment labelAlignment) {
    if (this.labelAlignment != labelAlignment) {
      this.labelAlignment = labelAlignment;
      updateLabelAlignment();
    }
  }

  /**
   * <p>setLayoutDirection.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   */
  public void setLayoutDirection(Direction direction) {
    if (label.getDirection() != direction) {
      label.setDirection(direction);
      updateLayout();
    }
  }

  /**
   * <p>Getter for the field <code>labelInsets</code>.</p>
   *
   * @return a {@link java.awt.Insets} object.
   */
  public Insets getLabelInsets() {
    return labelInsets;
  }

  /**
   * <p>Setter for the field <code>labelInsets</code>.</p>
   *
   * @param labelInsets a {@link java.awt.Insets} object.
   */
  public void setLabelInsets(Insets labelInsets) {
    this.labelInsets = labelInsets;

    GridBagConstraints c = ((GridBagLayout) getLayout()).getConstraints(label);
    c.insets = InsetsUtil.rotate(getDirection(), labelInsets);
    ((GridBagLayout) getLayout()).setConstraints(label, c);

    doUpdate();
  }

  /**
   * <p>isFlipTitleComponents.</p>
   *
   * @return a boolean.
   */
  public boolean isFlipTitleComponents() {
    return flipTitleComponents;
  }

  /**
   * <p>Setter for the field <code>flipTitleComponents</code>.</p>
   *
   * @param flipTitleComponents a boolean.
   */
  public void setFlipTitleComponents(boolean flipTitleComponents) {
    if (this.flipTitleComponents != flipTitleComponents) {
      this.flipTitleComponents = flipTitleComponents;
      updateLayout();
    }
  }

  /**
   * <p>Getter for the field <code>leftTitleComponents</code>.</p>
   *
   * @return an array of {@link javax.swing.JComponent} objects.
   */
  public JComponent[] getLeftTitleComponents() {
    return leftTitleComponents;
  }

  /**
   * <p>Setter for the field <code>leftTitleComponents</code>.</p>
   *
   * @param leftTitleComponents an array of {@link javax.swing.JComponent} objects.
   */
  public void setLeftTitleComponents(JComponent[] leftTitleComponents) {
    setLeftTitleComponents(leftTitleComponents,
        leftTitleComponents == null ? null : createEmptyInsets(leftTitleComponents.length));
  }

  /**
   * <p>Setter for the field <code>leftTitleComponents</code>.</p>
   *
   * @param leftTitleComponents an array of {@link javax.swing.JComponent} objects.
   * @param leftTitleComponentsInsets an array of {@link java.awt.Insets} objects.
   */
  public void setLeftTitleComponents(JComponent[] leftTitleComponents, Insets[] leftTitleComponentsInsets) {
    JComponent[] oldComponents = this.leftTitleComponents;
    this.leftTitleComponents = leftTitleComponents;
    this.leftTitleComponentsInsets = leftTitleComponentsInsets;
    updateTitleComponents(oldComponents, leftTitleComponents);
  }

  /**
   * <p>Getter for the field <code>rightTitleComponents</code>.</p>
   *
   * @return an array of {@link javax.swing.JComponent} objects.
   */
  public JComponent[] getRightTitleComponents() {
    return rightTitleComponents;
  }

  /**
   * <p>Setter for the field <code>rightTitleComponents</code>.</p>
   *
   * @param rightTitleComponents an array of {@link javax.swing.JComponent} objects.
   */
  public void setRightTitleComponents(JComponent[] rightTitleComponents) {
    setRightTitleComponents(rightTitleComponents,
        rightTitleComponents == null ? null : createEmptyInsets(rightTitleComponents.length));
  }

  /**
   * <p>Setter for the field <code>rightTitleComponents</code>.</p>
   *
   * @param rightTitleComponents an array of {@link javax.swing.JComponent} objects.
   * @param rightTitleComponentsInsets an array of {@link java.awt.Insets} objects.
   */
  public void setRightTitleComponents(JComponent[] rightTitleComponents, Insets[] rightTitleComponentsInsets) {
    JComponent[] oldComponents = this.rightTitleComponents;
    this.rightTitleComponents = rightTitleComponents;
    this.rightTitleComponentsInsets = rightTitleComponentsInsets;
    updateTitleComponents(oldComponents, rightTitleComponents);
  }

  private Insets[] createEmptyInsets(int num) {
    Insets[] insets = new Insets[num];
    for (int i = 0; i < num; i++) {
      insets[i] = InsetsUtil.EMPTY_INSETS;
    }

    return insets;
  }

  private void updateLabelAlignment() {
    label.setHorizontalAlignment(
        labelAlignment == Alignment.LEFT ?
                                          JLabel.LEFT : labelAlignment == Alignment.RIGHT ? JLabel.RIGHT : JLabel.CENTER);
  }

  private void updateTitleComponents(JComponent[] oldComponents, JComponent[] newComponents) {
    if (oldComponents != null) {
      for (int i = 0; i < oldComponents.length; i++)
        remove(oldComponents[i]);
    }

    if (newComponents != null) {
      for (int i = 0; i < newComponents.length; i++)
        add(newComponents[i]);
    }

    updateLayout();
  }

  private void updateLayout() {
    Direction direction = label.getDirection();
    constraints = new GridBagConstraints();

    JComponent[] leftComponents = flipTitleComponents ? rightTitleComponents : leftTitleComponents;
    JComponent[] rightComponents = flipTitleComponents ? leftTitleComponents : rightTitleComponents;

    Insets[] leftInsets = flipTitleComponents ? rightTitleComponentsInsets : leftTitleComponentsInsets;
    Insets[] rightInsets = flipTitleComponents ? leftTitleComponentsInsets : rightTitleComponentsInsets;

    if (direction == Direction.LEFT || direction == Direction.UP) {
      JComponent[] tmpComponents = leftComponents;
      leftComponents = rightComponents;
      rightComponents = tmpComponents;

      Insets[] tmpInsets = leftInsets;
      leftInsets = rightInsets;
      rightInsets = tmpInsets;
    }

    if (direction.isHorizontal()) {
      int x = 0;

      if (leftComponents != null) {
        for (int i = 0; i < leftComponents.length; i++) {
          int index = direction == Direction.RIGHT ? i : leftComponents.length - i - 1;
          setConstraints(leftComponents[index],
              leftInsets[index],
              x++,
              0,
              1,
              1,
              GridBagConstraints.NONE,
              0,
              0,
              GridBagConstraints.CENTER);
        }
      }

      setConstraints(label, labelInsets, x++, 0, 1, 1, GridBagConstraints.BOTH, 1, 1, GridBagConstraints.CENTER);

      if (rightComponents != null) {
        for (int i = 0; i < rightComponents.length; i++) {
          int index = direction == Direction.RIGHT ? i : rightComponents.length - i - 1;
          setConstraints(rightComponents[index],
              rightInsets[index],
              x++,
              0,
              1,
              1,
              GridBagConstraints.NONE,
              0,
              0,
              GridBagConstraints.CENTER);
        }
      }
    }
    else {
      int y = 0;

      if (leftComponents != null) {
        for (int i = 0; i < leftComponents.length; i++) {
          int index = direction == Direction.DOWN ? i : leftComponents.length - i - 1;
          setConstraints(leftComponents[index],
              leftInsets[index],
              0,
              y++,
              1,
              1,
              GridBagConstraints.NONE,
              0,
              0,
              GridBagConstraints.CENTER);
        }
      }

      setConstraints(label, labelInsets, 0, y++, 1, 1, GridBagConstraints.BOTH, 1, 1, GridBagConstraints.CENTER);

      if (rightComponents != null) {
        for (int i = 0; i < rightComponents.length; i++) {
          int index = direction == Direction.DOWN ? i : rightComponents.length - i - 1;
          setConstraints(rightComponents[index],
              rightInsets[index],
              0,
              y++,
              1,
              1,
              GridBagConstraints.NONE,
              0,
              0,
              GridBagConstraints.CENTER);
        }
      }
    }

    doUpdate();
  }

  private void setConstraints(Component c, Insets insets, int gridx, int gridy, int gridWidth, int gridHeight, int fill, double weightx, double weighty, int anchor) {
    constraints.insets = InsetsUtil.rotate(getDirection(), insets);
    constraints.gridx = gridx;
    constraints.gridy = gridy;
    constraints.fill = fill;
    constraints.weightx = weightx;
    constraints.weighty = weighty;
    constraints.gridwidth = gridWidth;
    constraints.gridheight = gridHeight;
    constraints.anchor = anchor;

    ((GridBagLayout) getLayout()).setConstraints(c, constraints);
  }

  private void doUpdate() {
    revalidate();

    if (repaintChecker.isPaintingOk())
      repaint();
  }
}
