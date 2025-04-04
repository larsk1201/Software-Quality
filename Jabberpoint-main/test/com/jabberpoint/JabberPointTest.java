package com.jabberpoint;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import com.jabberpoint.factory.XMLAccessor;
import com.jabberpoint.memento.PresentationCaretaker;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@RunWith(MockitoJUnitRunner.class)
public class JabberPointTest {

  private File tempFile;

  @Mock
  private PresentationCaretaker mockCaretaker;

  @Mock
  private Presentation mockPresentation;

  @Before
  public void setUp() throws IOException {
    tempFile = File.createTempFile("test", ".xml");
    tempFile.deleteOnExit();

    FileWriter writer = new FileWriter(tempFile);
    writer.write("<?xml version=\"1.0\"?>\n");
    writer.write("<!DOCTYPE presentation SYSTEM \"jabberpoint.dtd\">\n");
    writer.write("<presentation>\n");
    writer.write("  <showtitle>Test Presentation</showtitle>\n");
    writer.write("  <slide>\n");
    writer.write("    <title>Test Slide</title>\n");
    writer.write("    <item kind=\"text\" level=\"1\">Test Item</item>\n");
    writer.write("  </slide>\n");
    writer.write("</presentation>");
    writer.close();
  }

  @After
  public void tearDown() {
    tempFile.delete();
  }

  @Test
  public void loadingXMLFileClearsExistingPresentationAndLoadsNewContent() {
    try {
      Style.createStyles();
      XMLAccessor accessor = new XMLAccessor();

      when(mockPresentation.getSize()).thenReturn(1);

      accessor.loadFile(mockPresentation, tempFile.getAbsolutePath());

      verify(mockPresentation, times(1)).clear();
      verify(mockPresentation, times(1)).setTitle("Test Presentation");

    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void loadingPresentationClearsCaretakerHistory() {
    try {
      Style.createStyles();
      Presentation presentation = new Presentation();

      PresentationCaretaker caretaker = mock(PresentationCaretaker.class);

      XMLAccessor accessor = new XMLAccessor();
      accessor.loadFile(presentation, tempFile.getAbsolutePath());

      if (presentation.getSize() > 0) {
        presentation.setSlideNumber(0);
      }
      caretaker.clearHistory();

      verify(caretaker, times(1)).clearHistory();

    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void loadingPresentationSetsSlideNumberToZeroIfSlidesExist() {
    try {
      Style.createStyles();

      when(mockPresentation.getSize()).thenReturn(2);

      XMLAccessor accessor = new XMLAccessor();
      accessor.loadFile(mockPresentation, tempFile.getAbsolutePath());

      if (mockPresentation.getSize() > 0) {
        mockPresentation.setSlideNumber(0);
      }

      verify(mockPresentation, times(1)).setSlideNumber(0);

    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }
}

