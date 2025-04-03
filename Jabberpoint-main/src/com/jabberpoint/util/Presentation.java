package com.jabberpoint.util;

import com.jabberpoint.ui.Slide;
import com.jabberpoint.ui.SlideViewerComponent;
import com.jabberpoint.memento.Memento;
import java.util.ArrayList;

public class Presentation {

  private String showTitle;
  private ArrayList<Slide> showList = null;
  private int currentSlideNumber = 0;
  private SlideViewerComponent slideViewComponent = null;

  public Presentation() {
    slideViewComponent = null;
    clear();
  }

  public Presentation(SlideViewerComponent slideViewerComponent) {
    this.slideViewComponent = slideViewerComponent;
    clear();
  }

  public int getSize() {
    return showList.size();
  }

  public String getTitle() {
    return showTitle;
  }

  public void setTitle(String nt) {
    showTitle = nt;
  }

  public void setShowView(SlideViewerComponent slideViewerComponent) {
    this.slideViewComponent = slideViewerComponent;
  }

  public int getSlideNumber() {
    return currentSlideNumber;
  }

  public void setSlideNumber(int number) {
    currentSlideNumber = number;
    if (slideViewComponent != null) {
      slideViewComponent.update(this, getCurrentSlide());
    }
  }

  public void prevSlide() {
    if (currentSlideNumber > 0) {
      setSlideNumber(currentSlideNumber - 1);
    }
  }

  public void nextSlide() {
    if (currentSlideNumber < (showList.size() - 1)) {
      setSlideNumber(currentSlideNumber + 1);
    }
  }

  public void clear() {
    showList = new ArrayList<Slide>();
    setSlideNumber(-1);
  }

  public void append(Slide slide) {
    showList.add(slide);
  }

  public void deleteSlide(int index) {
    if (index < 0 || index >= showList.size()) {
      return;
    }

    showList.remove(index);

    if (showList.isEmpty()) {
      setSlideNumber(-1);
    } else if (currentSlideNumber >= showList.size()) {
      setSlideNumber(showList.size() - 1);
    } else {
      setSlideNumber(currentSlideNumber);
    }
  }

  public Slide getSlide(int number) {
    if (number < 0 || number >= getSize()) {
      return null;
    }
    return showList.get(number);
  }

  public Slide getCurrentSlide() {
    return getSlide(currentSlideNumber);
  }

  public Memento createMemento() {
    ArrayList<Slide> slidesCopy = new ArrayList<>();
    for (Slide slide : showList) {
      slidesCopy.add(new Slide(slide));
    }

    return new Memento(showTitle, slidesCopy);
  }

  public void restoreMemento(Memento memento) {
    this.showTitle = memento.getSavedTitle();
    this.showList = new ArrayList<>(memento.getSavedSlides());

    if (currentSlideNumber >= showList.size()) {
      setSlideNumber(showList.size() > 0 ? 0 : -1);
    } else {
      setSlideNumber(currentSlideNumber);
    }
  }

  public void exit(int n) {
    System.exit(n);
  }
}

