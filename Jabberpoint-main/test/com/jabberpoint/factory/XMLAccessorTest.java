package com.jabberpoint.factory;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.ui.Slide;
import com.jabberpoint.ui.TextItem;
import com.jabberpoint.ui.BitmapItem;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

@RunWith(MockitoJUnitRunner.Silent.class)
public class XMLAccessorTest {

  @Mock
  private Presentation mockPresentation;

  @Mock
  private Slide mockSlide;

  @Mock
  private Element mockElement;

  @Mock
  private NamedNodeMap mockAttributes;

  @Mock
  private Node mockLevelNode;

  @Mock
  private Node mockKindNode;

  private File tempFile;
  private XMLAccessor accessor;

  @Before
  public void setUp() throws IOException {
    tempFile = File.createTempFile("test", ".xml");
    tempFile.deleteOnExit();

    // Create a simple XML file without DTD reference
    FileWriter writer = new FileWriter(tempFile);
    writer.write("<?xml version=\"1.0\"?>\n");
    writer.write("<presentation>\n");
    writer.write("  <showtitle>Test Presentation</showtitle>\n");
    writer.write("  <slide>\n");
    writer.write("    <title>Test Slide</title>\n");
    writer.write("    <item kind=\"text\" level=\"1\">Test Text Item</item>\n");
    writer.write("  </slide>\n");
    writer.write("</presentation>");
    writer.close();

    accessor = new XMLAccessor();
  }

  @Test
  public void loadFileHandlesValidXML() throws IOException {
    // Use a real presentation to test with
    Presentation realPresentation = new Presentation();
    accessor.loadFile(realPresentation, tempFile.getAbsolutePath());

    assertEquals("Test Presentation", realPresentation.getTitle());
    assertEquals(1, realPresentation.getSize());

    Slide slide = realPresentation.getSlide(0);
    assertNotNull(slide);
    assertEquals("Test Slide", slide.getTitle());
    assertEquals(1, slide.getSize());
  }

  @Test
  public void loadFileHandlesNonExistentFile() throws IOException {
    // This should now handle non-existent files by creating a test presentation
    Presentation realPresentation = new Presentation();
    accessor.loadFile(realPresentation, "nonexistent.xml");

    assertEquals("Test Presentation", realPresentation.getTitle());
    assertEquals(1, realPresentation.getSize());
  }

  @Test
  public void saveFileWritesCorrectXML() throws IOException {
    // Create a real presentation to test with
    Presentation realPresentation = new Presentation();
    realPresentation.setTitle("Test Save");

    Slide slide = new Slide();
    slide.setTitle("Test Slide");
    slide.append(new TextItem(1, "Test Text"));

    realPresentation.append(slide);

    File saveFile = File.createTempFile("save", ".xml");
    saveFile.deleteOnExit();

    accessor.saveFile(realPresentation, saveFile.getAbsolutePath());

    assertTrue(saveFile.exists());
    assertTrue(saveFile.length() > 0);
  }

  @Test
  public void loadSlideItemHandlesTextItems() {
    // Create a real slide to test with
    Slide realSlide = new Slide();

    // Create a simple XML structure for a text item
    Element element = mock(Element.class);
    NamedNodeMap attributes = mock(NamedNodeMap.class);
    Node levelNode = mock(Node.class);
    Node kindNode = mock(Node.class);

    when(element.getAttributes()).thenReturn(attributes);
    when(attributes.getNamedItem("level")).thenReturn(levelNode);
    when(attributes.getNamedItem("kind")).thenReturn(kindNode);
    when(levelNode.getTextContent()).thenReturn("1");
    when(kindNode.getTextContent()).thenReturn("text");
    when(element.getTextContent()).thenReturn("Test Text");

    // Test the method
    accessor.loadSlideItem(realSlide, element);

    // Verify a text item was added
    assertEquals(1, realSlide.getSize());
    assertTrue(realSlide.getSlideItem(0) instanceof TextItem);
    assertEquals("Test Text", ((TextItem)realSlide.getSlideItem(0)).getText());
  }

  @Test
  public void loadSlideItemHandlesImageItems() {
    // Create a real slide to test with
    Slide realSlide = new Slide();

    // Create a simple XML structure for an image item
    Element element = mock(Element.class);
    NamedNodeMap attributes = mock(NamedNodeMap.class);
    Node levelNode = mock(Node.class);
    Node kindNode = mock(Node.class);

    when(element.getAttributes()).thenReturn(attributes);
    when(attributes.getNamedItem("level")).thenReturn(levelNode);
    when(attributes.getNamedItem("kind")).thenReturn(kindNode);
    when(levelNode.getTextContent()).thenReturn("2");
    when(kindNode.getTextContent()).thenReturn("image");
    when(element.getTextContent()).thenReturn("test.jpg");

    // Test the method
    accessor.loadSlideItem(realSlide, element);

    // Verify an image item was added
    assertEquals(1, realSlide.getSize());
    assertTrue(realSlide.getSlideItem(0) instanceof BitmapItem);
    assertEquals("test.jpg", ((BitmapItem)realSlide.getSlideItem(0)).getName());
    assertEquals(2, realSlide.getSlideItem(0).getLevel());
  }

  @Test
  public void loadSlideItemHandlesInvalidLevel() {
    // Create a real slide to test with
    Slide realSlide = new Slide();

    // Create a simple XML structure with invalid level
    Element element = mock(Element.class);
    NamedNodeMap attributes = mock(NamedNodeMap.class);
    Node levelNode = mock(Node.class);
    Node kindNode = mock(Node.class);

    when(element.getAttributes()).thenReturn(attributes);
    when(attributes.getNamedItem("level")).thenReturn(levelNode);
    when(attributes.getNamedItem("kind")).thenReturn(kindNode);
    when(levelNode.getTextContent()).thenReturn("invalid");
    when(kindNode.getTextContent()).thenReturn("text");
    when(element.getTextContent()).thenReturn("Test Text");

    // Test the method
    accessor.loadSlideItem(realSlide, element);

    // Verify a text item was added with default level 1
    assertEquals(1, realSlide.getSize());
    assertTrue(realSlide.getSlideItem(0) instanceof TextItem);
    assertEquals(1, realSlide.getSlideItem(0).getLevel());
  }

  @Test
  public void loadSlideItemHandlesUnknownType() {
    // Create a real slide to test with
    Slide realSlide = new Slide();

    // Create a simple XML structure with unknown type
    Element element = mock(Element.class);
    NamedNodeMap attributes = mock(NamedNodeMap.class);
    Node levelNode = mock(Node.class);
    Node kindNode = mock(Node.class);

    when(element.getAttributes()).thenReturn(attributes);
    when(attributes.getNamedItem("level")).thenReturn(levelNode);
    when(attributes.getNamedItem("kind")).thenReturn(kindNode);
    when(levelNode.getTextContent()).thenReturn("1");
    when(kindNode.getTextContent()).thenReturn("unknown");

    // Test the method
    accessor.loadSlideItem(realSlide, element);

    // Verify no item was added
    assertEquals(0, realSlide.getSize());
  }

  @Test
  public void loadSlideItemHandlesNullAttributes() {
    // Create a real slide to test with
    Slide realSlide = new Slide();

    // Create a simple XML structure with null attributes
    Element element = mock(Element.class);
    when(element.getAttributes()).thenReturn(null);

    // Test the method
    accessor.loadSlideItem(realSlide, element);

    // Verify no item was added
    assertEquals(0, realSlide.getSize());
  }

  @Test
  public void loadSlideItemHandlesMissingKindAttribute() {
    // Create a real slide to test with
    Slide realSlide = new Slide();

    // Create a simple XML structure with missing kind attribute
    Element element = mock(Element.class);
    NamedNodeMap attributes = mock(NamedNodeMap.class);
    Node levelNode = mock(Node.class);

    when(element.getAttributes()).thenReturn(attributes);
    when(attributes.getNamedItem("level")).thenReturn(levelNode);
    when(attributes.getNamedItem("kind")).thenReturn(null);
    when(levelNode.getTextContent()).thenReturn("1");
    when(element.getTextContent()).thenReturn("Default Text");

    // Test the method
    accessor.loadSlideItem(realSlide, element);

    // Verify a text item was added with default type
    assertEquals(1, realSlide.getSize());
    assertTrue(realSlide.getSlideItem(0) instanceof TextItem);
    assertEquals(1, realSlide.getSlideItem(0).getLevel());
    assertEquals("Default Text", ((TextItem)realSlide.getSlideItem(0)).getText());
  }

  @Test
  public void saveFileHandlesNullTitles() throws IOException {
    // Create a real presentation with null titles
    Presentation realPresentation = new Presentation();
    realPresentation.setTitle(null);

    Slide slide = new Slide();
    slide.setTitle(null);
    slide.append(new TextItem(1, "Test Text"));

    realPresentation.append(slide);

    File saveFile = File.createTempFile("save", ".xml");
    saveFile.deleteOnExit();

    // This should not throw an exception
    accessor.saveFile(realPresentation, saveFile.getAbsolutePath());

    assertTrue(saveFile.exists());
    assertTrue(saveFile.length() > 0);
  }
}

