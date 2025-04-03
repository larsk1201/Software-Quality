package com.jabberpoint.util;

import com.jabberpoint.ui.Slide;
import com.jabberpoint.ui.SlideViewerComponent;
import com.jabberpoint.memento.Memento;
import java.util.ArrayList;


/**
 * <p>com.jabberpoint.util.Presentation houdt de slides in de presentatie bij.</p>
 * <p>Er is slechts ��n instantie van deze klasse aanwezig.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class Presentation {

  private String showTitle; // de titel van de presentatie
  private ArrayList<Slide> showList = null; // een ArrayList met de Slides
  private int currentSlideNumber = 0; // het slidenummer van de huidige com.jabberpoint.ui.Slide
  private SlideViewerComponent slideViewComponent = null; // de viewcomponent voor de Slides

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

  // geef het nummer van de huidige slide
  public int getSlideNumber() {
    return currentSlideNumber;
  }

  // verander het huidige-slide-nummer en laat het aan het window weten.
  public void setSlideNumber(int number) {
    currentSlideNumber = number;
    if (slideViewComponent != null) {
      slideViewComponent.update(this, getCurrentSlide());
    }
  }

  // ga naar de vorige slide tenzij je aan het begin van de presentatie bent
  public void prevSlide() {
    if (currentSlideNumber > 0) {
      setSlideNumber(currentSlideNumber - 1);
    }
  }

  // Ga naar de volgende slide tenzij je aan het einde van de presentatie bent.
  public void nextSlide() {
    if (currentSlideNumber < (showList.size() - 1)) {
      setSlideNumber(currentSlideNumber + 1);
    }
  }

  // Verwijder de presentatie, om klaar te zijn voor de volgende
  public void clear() {
    showList = new ArrayList<Slide>();
    setSlideNumber(-1);
  }

  // Voeg een slide toe aan de presentatie
  public void append(Slide slide) {
    showList.add(slide);
  }

  // Delete a slide from the presentation
  public void deleteSlide(int index) {
    if (index < 0 || index >= showList.size()) {
      return; // Invalid index
    }

    // Remove the slide
    showList.remove(index);

    // Adjust the current slide number if needed
    if (showList.isEmpty()) {
      setSlideNumber(-1); // No slides left
    } else if (currentSlideNumber >= showList.size()) {
      setSlideNumber(showList.size() - 1); // Go to the last slide
    } else {
      // Stay on the same slide number (which is now a different slide)
      // but need to update the view
      setSlideNumber(currentSlideNumber);
    }
  }

  // Geef een slide met een bepaald slidenummer
  public Slide getSlide(int number) {
    if (number < 0 || number >= getSize()) {
      return null;
    }
    return (Slide) showList.get(number);
  }

  // Geef de huidige com.jabberpoint.ui.Slide
  public Slide getCurrentSlide() {
    return getSlide(currentSlideNumber);
  }

  public Memento createMemento() {
    return new Memento(showTitle, new ArrayList<>(showList));
  }

  public void restoreMemento(Memento memento) {
    this.showTitle = memento.getSavedTitle();
    this.showList = new ArrayList<>(memento.getSavedSlides());
    setSlideNumber(0); // Reset to the first slide after restoring
  }

  public void exit(int n) {
    System.exit(n);
  }
}

