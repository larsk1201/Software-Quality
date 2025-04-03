package com.jabberpoint.memento;

import com.jabberpoint.ui.Slide;
import java.util.ArrayList;

public class Memento {

  private String savedTitle;
  private ArrayList<Slide> savedSlides;

  public Memento(String title, ArrayList<Slide> slides) {
    this.savedTitle = title;
    this.savedSlides = slides;
  }

  public String getSavedTitle() {
    return this.savedTitle;
  }

  public void setSavedTitle(String savedTitle) {
    this.savedTitle = savedTitle;
  }

  public ArrayList<Slide> getSavedSlides() {
    return this.savedSlides;
  }

  public void setSavedSlides(ArrayList<Slide> savedSlides) {
    this.savedSlides = savedSlides;
  }
}

