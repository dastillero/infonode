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


//$Id: ImageUtils.java,v 1.7 2005/02/16 11:28:14 jesper Exp $
package net.infonode.util; // Generated package name

import net.infonode.util.math.Int4;

import java.awt.*;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.net.URL;

/**
 * <p>ImageUtils class.</p>
 *
 * @author trueh
 * @version $Id: $Id
 */
public final class ImageUtils {
  /**
   * <p>create.</p>
   *
   * @param filename a {@link java.lang.String} object.
   * @return a {@link java.awt.Image} object.
   * @throws net.infonode.util.ImageException if any.
   */
  public static final Image create(String filename) throws ImageException {
    Image image = Toolkit.getDefaultToolkit().createImage(filename);
    waitImage(image);
    return image;
  }

  /**
   * <p>create.</p>
   *
   * @param url a {@link java.net.URL} object.
   * @return a {@link java.awt.Image} object.
   * @throws net.infonode.util.ImageException if any.
   */
  public static final Image create(URL url) throws ImageException {
    Image image = Toolkit.getDefaultToolkit().createImage(url);
    waitImage(image);
    return image;
  }

  /**
   * <p>create.</p>
   *
   * @param data an array of byte.
   * @return a {@link java.awt.Image} object.
   * @throws net.infonode.util.ImageException if any.
   */
  public static final Image create(byte data[]) throws ImageException {
    Image image = Toolkit.getDefaultToolkit().createImage(data);
    waitImage(image);
    return image;
  }

  /**
   * <p>waitImage.</p>
   *
   * @param image a {@link java.awt.Image} object.
   * @throws net.infonode.util.ImageException if any.
   */
  public static final void waitImage(Image image) throws ImageException {
    MediaTracker tracker = new MediaTracker(new Canvas()); //use dummy component
    tracker.addImage(image, 1);

    try {
      tracker.waitForID(1);
    }
    catch (InterruptedException e) {
      throw new ImageException("Interrupted when creating image!");
    }
  }

  /**
   * <p>getPixels.</p>
   *
   * @param image a {@link java.awt.Image} object.
   * @return an array of int.
   * @throws net.infonode.util.ImageException if any.
   */
  public static final int[] getPixels(Image image) throws ImageException {
    return getPixels(image, 0, 0, image.getWidth(null), image.getHeight(null));
  }

  /**
   * <p>getPixels.</p>
   *
   * @param image a {@link java.awt.Image} object.
   * @param x a int.
   * @param y a int.
   * @param width a int.
   * @param height a int.
   * @return an array of int.
   * @throws net.infonode.util.ImageException if any.
   */
  public static final int[] getPixels(Image image, int x, int y, int width, int height) throws ImageException {
    int[] pixels = new int[width * height];
    PixelGrabber pg = new PixelGrabber(image, x, y, width, height, pixels, 0, width);
    try {
      pg.grabPixels();
    }
    catch (InterruptedException e) {
      throw new ImageException("Interrupted waiting for pixels");
    }

    if ((pg.getStatus() & ImageObserver.ABORT) != 0)
      throw new ImageException("Image fetch aborted or errored");

    return pixels;
  }

  /**
   * <p>getAlpha.</p>
   *
   * @param pixel a int.
   * @return a int.
   */
  public static final int getAlpha(int pixel) {
    return (pixel >> 24) & 0xff;
  }

  /**
   * <p>getRed.</p>
   *
   * @param pixel a int.
   * @return a int.
   */
  public static final int getRed(int pixel) {
    return (pixel >> 16) & 0xff;
  }

  /**
   * <p>getGreen.</p>
   *
   * @param pixel a int.
   * @return a int.
   */
  public static final int getGreen(int pixel) {
    return (pixel >> 8) & 0xff;
  }

  /**
   * <p>getBlue.</p>
   *
   * @param pixel a int.
   * @return a int.
   */
  public static final int getBlue(int pixel) {
    return pixel & 0xff;
  }

  /**
   * <p>createPixel.</p>
   *
   * @param red a int.
   * @param green a int.
   * @param blue a int.
   * @return a int.
   */
  public static final int createPixel(int red, int green, int blue) {
    return (0xff << 24) | (red << 16) | (green << 8) | blue;
  }

  /**
   * <p>toIntColor.</p>
   *
   * @param i a {@link net.infonode.util.math.Int4} object.
   * @return a int.
   */
  public static int toIntColor(Int4 i) {
    return ((i.getD() << 8) & 0xff000000) |
           (i.getA() & 0xff0000) |
           ((i.getB() >> 8) & 0xff00) |
           ((i.getC() >> 16) & 0xff);
  }

  /**
   * <p>toInt4.</p>
   *
   * @param c a {@link java.awt.Color} object.
   * @return a {@link net.infonode.util.math.Int4} object.
   */
  public static Int4 toInt4(Color c) {
    return new Int4(c.getRed() << 16, c.getGreen() << 16, c.getBlue() << 16, c.getAlpha() << 16);
  }

  /**
   * <p>toColor.</p>
   *
   * @param c a {@link net.infonode.util.math.Int4} object.
   * @return a {@link java.awt.Color} object.
   */
  public static Color toColor(Int4 c) {
    return new Color(c.getA() >> 16, c.getB() >> 16, c.getC() >> 16, c.getD() >> 16);
  }

  /**
   * <p>createGradientPixels.</p>
   *
   * @param colors an array of {@link java.awt.Color} objects.
   * @param width a int.
   * @param height a int.
   * @return an array of int.
   */
  public static final int[] createGradientPixels(Color[] colors, int width, int height) {
    int[] pixels = new int[width * height];
    createGradientPixels(colors, width, height, pixels);
    return pixels;
  }

  /**
   * <p>createGradientPixels.</p>
   *
   * @param colors an array of {@link java.awt.Color} objects.
   * @param width a int.
   * @param height a int.
   * @param pixels an array of int.
   * @return an array of int.
   */
  public static final int[] createGradientPixels(Color[] colors, int width, int height, int[] pixels) {
    int p = 0;

    Int4 c1 = toInt4(colors[0]);
    Int4 c2 = toInt4(colors[1]);
    Int4 dc1 = toInt4(colors[2]).sub(toInt4(colors[0])).div(height);
    Int4 dc2 = toInt4(colors[3]).sub(toInt4(colors[1])).div(height);
    Int4 d = new Int4();
    Int4 c = new Int4();

    for (int y = 0; y < height; y++) {
      d.set(c2).sub(c1).div(width);
      c.set(c1);

      for (int stop = p + width; p < stop; p++) {
        pixels[p] = toIntColor(c);
        c.add(d);
      }

      c1.add(dc1);
      c2.add(dc2);
    }

    return pixels;
  }

  /**
   * <p>createTransform.</p>
   *
   * @param direction a {@link net.infonode.util.Direction} object.
   * @param horizontalFlip a boolean.
   * @param verticalFlip a boolean.
   * @param width a int.
   * @param height a int.
   * @return a {@link java.awt.geom.AffineTransform} object.
   */
  public static AffineTransform createTransform(Direction direction,
                                                boolean horizontalFlip,
                                                boolean verticalFlip,
                                                int width,
                                                int height) {
    int hf = horizontalFlip ? -1 : 1;
    int vf = verticalFlip ? -1 : 1;
    int m00 = direction == Direction.RIGHT ? hf : direction == Direction.LEFT ? -hf : 0;
    int m01 = direction == Direction.DOWN ? -vf : direction == Direction.UP ? vf : 0;
    int m10 = direction == Direction.DOWN ? hf : direction == Direction.UP ? -hf : 0;
    int m11 = direction == Direction.RIGHT ? vf : direction == Direction.LEFT ? -vf : 0;
    return new AffineTransform(m00,
                               m10,
                               m01,
                               m11,
                               m00 == -1 ? width : m01 == -1 ? height : 0,
                               m10 == -1 ? width : m11 == -1 ? height : 0);
  }
}
