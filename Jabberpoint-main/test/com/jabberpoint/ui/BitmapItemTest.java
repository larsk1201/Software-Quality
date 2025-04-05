package com.jabberpoint.ui;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Style;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BitmapItemTest {

  @Mock
  private Graphics mockGraphics;

  @Mock
  private ImageObserver mockObserver;

  private Style testStyle;
  private BufferedImage testImage;

  @Before
  public void setUp() {
    Style.createStyles();
    testStyle = Style.getStyle(1);
    testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
  }

  @Test
  public void constructorWithLevelAndNameSetsProperties() {
    // Create a subclass that doesn't try to load the file
    BitmapItem item = new BitmapItem(1, null) {
      {
        setImageName("test-image.jpg");
      }
    };
    assertEquals(1, item.getLevel());
    assertEquals("test-image.jpg", item.getName());
  }

  @Test
  public void constructorWithNoArgumentsCreatesEmptyItem() {
    BitmapItem item = new BitmapItem();
    assertEquals(0, item.getLevel());
    assertNull(item.getBufferedImage());
  }

  @Test
  public void gettersAndSettersWorkCorrectly() {
    BitmapItem item = new BitmapItem();

    item.setBufferedImage(testImage);
    assertSame(testImage, item.getBufferedImage());

    item.setImageName("test.jpg");
    assertEquals("test.jpg", item.getImageName());
    assertEquals("test.jpg", item.getName());
  }

  @Test
  public void getBoundingBoxCalculatesCorrectDimensions() {
    BitmapItem item = new BitmapItem(1, testImage, "test.jpg");

    Rectangle box = item.getBoundingBox(mockGraphics, mockObserver, 1.0f, testStyle);

    assertNotNull(box);
    assertEquals(testStyle.indent, box.x);
    assertEquals(0, box.y);
    assertEquals(100, box.width);
    assertEquals(testStyle.leading + 100, box.height);
  }

  @Test
  public void drawRendersImageAtCorrectPosition() {
    BitmapItem item = new BitmapItem(1, testImage, "test.jpg");

    item.draw(10, 20, 1.0f, mockGraphics, testStyle, mockObserver);

    verify(mockGraphics).drawImage(
        eq(testImage),
        eq(10 + testStyle.indent),
        eq(20 + testStyle.leading),
        eq(100),
        eq(100),
        eq(mockObserver)
    );
  }

  @Test
  public void toStringReturnsCorrectFormat() {
    BitmapItem item = new BitmapItem(2, (BufferedImage)null, "test.jpg");
    String result = item.toString();
    assertTrue(result.contains("BitmapItem"));
    assertTrue(result.contains("2"));
    assertTrue(result.contains("test.jpg"));
  }

  @Test
  public void constructorWithBufferedImageAndNameSetsProperties() {
    BitmapItem item = new BitmapItem(testImage, "test.jpg");
    assertEquals(0, item.getLevel());
    assertEquals("test.jpg", item.getName());
    assertSame(testImage, item.getBufferedImage());
  }

  @Test
  public void drawWithNullImageDoesNotThrowException() {
    BitmapItem item = new BitmapItem(1, (BufferedImage)null, "test.jpg");

    // This should not throw an exception
    try {
      item.draw(10, 20, 1.0f, mockGraphics, testStyle, mockObserver);
      // If we reach here, no exception was thrown
      assertTrue(true);
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void getBoundingBoxWithNullImageReturnsMinimalRectangle() {
    BitmapItem item = new BitmapItem(1, (BufferedImage)null, "test.jpg");

    try {
      Rectangle box = new Rectangle(testStyle.indent, 0, 0, testStyle.leading);
      assertEquals(box, item.getBoundingBox(mockGraphics, mockObserver, 1.0f, testStyle));
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }
}

