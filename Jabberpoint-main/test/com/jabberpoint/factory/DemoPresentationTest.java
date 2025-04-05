package com.jabberpoint.factory;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.ui.Slide;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class DemoPresentationTest {

  @Mock
  private Presentation mockPresentation;

  @Test
  public void loadFileCreatesMultipleSlides() throws IOException {
    DemoPresentation demo = new DemoPresentation();
    demo.loadFile(mockPresentation, "");

    verify(mockPresentation).clear();
    verify(mockPresentation).setTitle(eq("Demo com.jabberpoint.util.Presentation"));
    verify(mockPresentation, times(2)).append(any(Slide.class));
  }

  @Test
  public void loadFileCreatesFirstSlideWithCorrectContent() throws IOException {
    DemoPresentation demo = new DemoPresentation();

    demo.loadFile(mockPresentation, "");

    verify(mockPresentation).clear();

    verify(mockPresentation, atLeastOnce()).append(argThat(slide -> {
      if (slide instanceof Slide) {
        Slide s = (Slide) slide;
        return "com.jabberpoint.JabberPoint".equals(s.getTitle());
      }
      return false;
    }));
  }

  @Test
  public void loadFileCreatesSecondSlideWithCorrectContent() throws IOException {
    DemoPresentation demo = new DemoPresentation();

    demo.loadFile(mockPresentation, "");

    verify(mockPresentation, atLeastOnce()).append(argThat(slide -> {
      if (slide instanceof Slide) {
        Slide s = (Slide) slide;
        return "Demonstratie van levels en stijlen".equals(s.getTitle());
      }
      return false;
    }));
  }

  @Test
  public void saveFileOutputsMessage() throws IOException {
    DemoPresentation demo = new DemoPresentation();
    demo.saveFile(mockPresentation, "test.xml");
  }
}

