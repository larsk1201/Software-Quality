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
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.FontMetrics;
import java.awt.Font;

@RunWith(MockitoJUnitRunner.Silent.class) // Use Silent runner to avoid unnecessary stubbing warnings
public class SlideItemTest {

    @Mock
    private Graphics mockGraphics;

    @Mock
    private ImageObserver mockObserver;

    private Style testStyle;

    @Before
    public void setUp() {
        Style.createStyles();
        testStyle = Style.getStyle(1);
    }

    @Test
    public void textItemConstructorSetsLevelAndText() {
        TextItem item = new TextItem(1, "Test Text");
        assertEquals(1, item.getLevel());
        assertEquals("Test Text", item.getText());
    }

    @Test
    public void textItemEmptyConstructorSetsDefaultLevelAndText() {
        TextItem item = new TextItem();
        assertEquals(0, item.getLevel());
        assertNotEquals("", item.getText());
    }

    @Test
    public void textItemGetAttributedStringReturnsNonNullAttributedString() {
        TextItem item = new TextItem(1, "Test Text");
        java.text.AttributedString result = item.getAttributedString(testStyle, 1.0f);
        assertNotNull(result);
    }

    @Test
    public void bitmapItemConstructorSetsLevelImageAndName() {
        BufferedImage testImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        BitmapItem item = new BitmapItem(1, testImage, "test.jpg");
        assertEquals(1, item.getLevel());
        assertEquals("test.jpg", item.getName());
        assertSame(testImage, item.getBufferedImage());
    }

    @Test
    public void slideItemDefaultConstructorSetsLevelToZero() {
        SlideItem item = new TextItem();
        assertEquals(0, item.getLevel());
    }

    @Test
    public void slideItemConstructorWithLevelSetsCorrectLevel() {
        SlideItem item = new TextItem(5, "Test Text");
        assertEquals(5, item.getLevel());
    }

    @Test
    public void textItemWithNullTextHandlesGracefully() {
        TextItem item = new TextItem(1, null);
        assertEquals("", item.getText());
    }

    @Test
    public void textItemWithEmptyTextHandlesGracefully() {
        TextItem item = new TextItem(1, "");
        assertEquals("", item.getText());
    }
}

