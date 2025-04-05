package com.jabberpoint.ui;

import com.jabberpoint.util.Style;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.Vector;

public class Slide implements Cloneable {

  public final static int WIDTH = 1200;
  public final static int HEIGHT = 800;
  protected String title;
  protected Vector<SlideItem> items;

  public Slide() {
    items = new Vector<SlideItem>();
  }

  public Slide(Slide source) {
    this.title = source.title;
    this.items = new Vector<SlideItem>();

    for (SlideItem item : source.items) {
      if (item instanceof TextItem) {
        TextItem textItem = (TextItem) item;
        this.items.add(new TextItem(textItem.getLevel(), textItem.getText()));
      } else if (item instanceof BitmapItem) {
        BitmapItem bitmapItem = (BitmapItem) item;
        this.items.add(new BitmapItem(bitmapItem.getLevel(), bitmapItem.getName()));
      } else {
        this.items.add(item);
      }
    }
  }

  public void append(SlideItem anItem) {
    items.addElement(anItem);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String newTitle) {
    title = newTitle;
  }

  public void append(int level, String message) {
    append(new TextItem(level, message));
  }

  public SlideItem getSlideItem(int number) {
    return (SlideItem) items.elementAt(number);
  }

  public Vector<SlideItem> getSlideItems() {
    return items;
  }

  public int getSize() {
    return items.size();
  }

  public void draw(Graphics g, Rectangle area, ImageObserver view) {
    float scale = getScale(area);
    int y = area.y;
    SlideItem slideItem = new TextItem(0, getTitle());
    Style style = Style.getStyle(slideItem.getLevel());
    slideItem.draw(area.x, y, scale, g, style, view);
    y += slideItem.getBoundingBox(g, view, scale, style).height;
    for (int number = 0; number < getSize(); number++) {
      slideItem = (SlideItem) getSlideItems().elementAt(number);
      style = Style.getStyle(slideItem.getLevel());
      slideItem.draw(area.x, y, scale, g, style, view);
      y += slideItem.getBoundingBox(g, view, scale, style).height;
    }
  }

  private float getScale(Rectangle area) {
    return Math.min(((float) area.width) / ((float) WIDTH),
        ((float) area.height) / ((float) HEIGHT));
  }
}

