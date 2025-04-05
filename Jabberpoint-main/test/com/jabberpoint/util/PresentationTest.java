package com.jabberpoint.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jabberpoint.memento.Memento;
import com.jabberpoint.ui.Slide;
import com.jabberpoint.ui.SlideViewerComponent;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PresentationTest {

  private Presentation presentation;

  @Mock
  private SlideViewerComponent mockViewer;

  @Before
  public void setUp() {
    presentation = new Presentation(mockViewer);
  }

  @Test
  public void newPresentationHasZeroSizeAndNegativeOneSlideNumber() {
    assertNotNull(presentation);
    assertEquals(0, presentation.getSize());
    assertEquals(-1, presentation.getSlideNumber());
  }

  @Test
  public void setTitleChangesTheTitleOfThePresentation() {
    presentation.setTitle("Test Presentation");
    assertEquals("Test Presentation", presentation.getTitle());
  }

  @Test
  public void appendSlideAddsSlideAndIncreasesSize() {
    Slide slide = new Slide();
    slide.setTitle("Test Slide");
    presentation.append(slide);
    assertEquals(1, presentation.getSize());
    assertSame(slide, presentation.getSlide(0));
  }

  @Test
  public void setSlideNumberChangesCurrentSlideAndUpdatesViewer() {
    Slide slide1 = new Slide();
    Slide slide2 = new Slide();
    presentation.append(slide1);
    presentation.append(slide2);
    presentation.setSlideNumber(1);
    assertEquals(1, presentation.getSlideNumber());
    verify(mockViewer, times(1)).update(presentation, slide2);
  }

  @Test
  public void nextSlideIncrementsSlideNumberWhenNotAtEnd() {
    Slide slide1 = new Slide();
    Slide slide2 = new Slide();
    presentation.append(slide1);
    presentation.append(slide2);
    presentation.setSlideNumber(0);
    presentation.nextSlide();
    assertEquals(1, presentation.getSlideNumber());
  }

  @Test
  public void nextSlideDoesNotChangeSlideNumberWhenAtEnd() {
    Slide slide = new Slide();
    presentation.append(slide);
    presentation.setSlideNumber(0);
    presentation.nextSlide();
    assertEquals(0, presentation.getSlideNumber());
  }

  @Test
  public void prevSlideDecrementsSlideNumberWhenNotAtBeginning() {
    Slide slide1 = new Slide();
    Slide slide2 = new Slide();
    presentation.append(slide1);
    presentation.append(slide2);
    presentation.setSlideNumber(1);
    presentation.prevSlide();
    assertEquals(0, presentation.getSlideNumber());
  }

  @Test
  public void prevSlideDoesNotChangeSlideNumberWhenAtBeginning() {
    Slide slide = new Slide();
    presentation.append(slide);
    presentation.setSlideNumber(0);
    presentation.prevSlide();
    assertEquals(0, presentation.getSlideNumber());
  }

  @Test
  public void clearRemovesAllSlidesAndResetsSlideNumber() {
    Slide slide = new Slide();
    presentation.append(slide);
    presentation.setSlideNumber(0);
    presentation.clear();
    assertEquals(0, presentation.getSize());
    assertEquals(-1, presentation.getSlideNumber());
  }

  @Test
  public void deleteSlideRemovesSlideAndAdjustsCurrentSlideNumber() {
    Slide slide1 = new Slide();
    Slide slide2 = new Slide();
    Slide slide3 = new Slide();
    presentation.append(slide1);
    presentation.append(slide2);
    presentation.append(slide3);
    presentation.setSlideNumber(1);
    presentation.deleteSlide(1);
    assertEquals(2, presentation.getSize());
    assertEquals(1, presentation.getSlideNumber());
    assertSame(slide3, presentation.getCurrentSlide());
  }

  @Test
  public void deleteLastSlideMovesCurrentSlideNumberToPreviousSlide() {
    Slide slide1 = new Slide();
    Slide slide2 = new Slide();
    presentation.append(slide1);
    presentation.append(slide2);
    presentation.setSlideNumber(1);
    presentation.deleteSlide(1);
    assertEquals(1, presentation.getSize());
    assertEquals(0, presentation.getSlideNumber());
  }

  @Test
  public void deleteAllSlidesResetsSlideNumberToNegativeOne() {
    Slide slide = new Slide();
    presentation.append(slide);
    presentation.setSlideNumber(0);
    presentation.deleteSlide(0);
    assertEquals(0, presentation.getSize());
    assertEquals(-1, presentation.getSlideNumber());
  }

  @Test
  public void createMementoSavesCurrentTitleAndSlides() {
    presentation.setTitle("Test Presentation");
    Slide slide = new Slide();
    slide.setTitle("Test Slide");
    presentation.append(slide);
    Memento memento = presentation.createMemento();
    assertEquals("Test Presentation", memento.getSavedTitle());
    assertEquals(1, memento.getSavedSlides().size());
  }

  @Test
  public void restoreMementoReplacesCurrentTitleAndSlidesAndResetsSlideNumber() {
    ArrayList<Slide> slides = new ArrayList<>();
    Slide slide = new Slide();
    slide.setTitle("Memento Slide");
    slides.add(slide);
    Memento memento = new Memento("Memento Presentation", slides);

    // Set up the presentation with a slide first
    presentation.append(new Slide());
    presentation.setSlideNumber(0);

    presentation.restoreMemento(memento);
    assertEquals("Memento Presentation", presentation.getTitle());
    assertEquals(1, presentation.getSize());
    assertEquals("Memento Slide", presentation.getSlide(0).getTitle());
    assertEquals(0, presentation.getSlideNumber());
  }
}

