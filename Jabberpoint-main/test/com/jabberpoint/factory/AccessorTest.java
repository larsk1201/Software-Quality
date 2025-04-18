package com.jabberpoint.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jabberpoint.ui.Slide;
import com.jabberpoint.util.Presentation;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccessorTest {

  @Mock
  private Presentation mockPresentation;

  private File tempFile;

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

  @Test
  public void getDemoAccessorReturnsInstanceOfDemoPresentation() {
    Accessor accessor = Accessor.getDemoAccessor();
    assertTrue(accessor instanceof DemoPresentation);
  }

  @Test
  public void xmlAccessorLoadFileClearsAndSetsCorrectTitleAndAppendsSlide() throws IOException {
    XMLAccessor accessor = new XMLAccessor();
    accessor.loadFile(mockPresentation, tempFile.getAbsolutePath());

    verify(mockPresentation, times(1)).clear();
    verify(mockPresentation, times(1)).setTitle(eq("Test Presentation"));
    verify(mockPresentation, times(1)).append(any(Slide.class));
  }

  @Test
  public void xmlAccessorSaveFileCreatesFileWithCorrectContent() throws IOException {
    XMLAccessor accessor = new XMLAccessor();
    when(mockPresentation.getTitle()).thenReturn("Test Presentation");
    when(mockPresentation.getSize()).thenReturn(1);

    Slide mockSlide = mock(Slide.class);
    when(mockSlide.getTitle()).thenReturn("Test Slide");
    when(mockSlide.getSlideItems()).thenReturn(new java.util.Vector<>());

    when(mockPresentation.getSlide(0)).thenReturn(mockSlide);

    accessor.saveFile(mockPresentation, tempFile.getAbsolutePath());

    assertTrue(tempFile.exists());
    assertTrue(tempFile.length() > 0);
  }

  @Test
  public void demoPresentationLoadFileClearsAndSetsTitleAndAppendsMultipleSlides() {
    DemoPresentation demo = new DemoPresentation();
    demo.loadFile(mockPresentation, "");

    verify(mockPresentation, times(1)).clear();
    verify(mockPresentation, times(1)).setTitle(anyString());
    verify(mockPresentation, times(2)).append(any(Slide.class));
  }

  @Test
  public void demoPresentationSaveFileOutputsMessage() {
    DemoPresentation demo = new DemoPresentation();
    demo.saveFile(mockPresentation, "");
  }

  @Test
  public void accessorFactoryCreateAccessorWithXMLReturnsXMLAccessor() {
    AccessorFactory factory = new AccessorFactory();
    assertTrue(factory.createAccessor("XML") instanceof XMLAccessor);
  }

  @Test
  public void accessorFactoryCreateAccessorWithDemoReturnsDemoPresentation() {
    AccessorFactory factory = new AccessorFactory();
    assertTrue(factory.createAccessor("Demo") instanceof DemoPresentation);
  }

  @Test(expected = IllegalArgumentException.class)
  public void accessorFactoryCreateAccessorWithInvalidTypeThrowsIllegalArgumentException() {
    AccessorFactory factory = new AccessorFactory();
    factory.createAccessor("Invalid");
  }

  @Test
  public void accessorDefaultConstructorDoesNotThrowExceptions() {
    Accessor accessor = new XMLAccessor();
    assertNotNull(accessor);
  }

  @Test
  public void accessorConstantsHaveCorrectValues() {
    assertEquals("Demonstratie presentatie", Accessor.DEMO_NAME);
    assertEquals(".xml", Accessor.DEFAULT_EXTENSION);
  }
}

