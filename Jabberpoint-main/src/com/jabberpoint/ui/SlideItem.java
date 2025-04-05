package com.jabberpoint.ui;

import com.jabberpoint.util.Style;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

/**
 * <p>De abstracte klasse voor een item op een com.jabberpoint.ui.Slide<p>
 * <p>Alle SlideItems hebben tekenfunctionaliteit.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class SlideItem {

  private int level = 0; // het level van het slideitem

  public SlideItem(int lev) {
    level = lev;
  }

  public SlideItem() {
    this(0);
  }

  // Geef het level
  public int getLevel() {
    return level;
  }

  // Geef de bounding box
  public abstract Rectangle getBoundingBox(Graphics g,
      ImageObserver observer, float scale, Style style);

  // teken het item
  public abstract void draw(int x, int y, float scale,
      Graphics g, Style style, ImageObserver observer);
}
