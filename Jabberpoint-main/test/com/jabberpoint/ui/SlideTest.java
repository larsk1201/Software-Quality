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
import java.awt.image.ImageObserver;
import java.util.Vector;

@RunWith(MockitoJUnitRunner.class)
public class SlideTest {

    private Slide slide;

    @Mock
    private Graphics mockGraphics;

    @Mock
    private ImageObserver mockObserver;

    @Before
    public void setUp() {
        slide = new Slide();
        Style.createStyles();
    }

    @Test
    public void newSlideHasZeroSizeAndNullTitle() {
        assertNotNull(slide);
        assertEquals(0, slide.getSize());
        assertNull(slide.getTitle());
    }

    @Test
    public void setTitleChangesTheTitleOfTheSlide() {
        slide.setTitle("Test Title");
        assertEquals("Test Title", slide.getTitle());
    }

    @Test
    public void appendSlideItemAddsItemToSlideAndIncreasesSize() {
        SlideItem item = new TextItem(1, "Test Item");
        slide.append(item);
        assertEquals(1, slide.getSize());
        assertEquals(item, slide.getSlideItem(0));
    }

    @Test
    public void appendWithLevelAndStringCreatesAndAddsTextItem() {
        slide.append(1, "Test Text");
        assertEquals(1, slide.getSize());
        SlideItem item = slide.getSlideItem(0);
        assertTrue(item instanceof TextItem);
        assertEquals(1, item.getLevel());
        assertEquals("Test Text", ((TextItem)item).getText());
    }

    @Test
    public void getSlideItemsReturnsAllItemsInTheSlide() {
        slide.append(1, "Item 1");
        slide.append(2, "Item 2");
        Vector<SlideItem> items = slide.getSlideItems();
        assertEquals(2, items.size());
    }

    @Test
    public void drawRendersTheSlideTitleAndAllItems() {
        slide.setTitle("Test Slide");
        slide.append(1, "Test Item");
        Rectangle area = new Rectangle(0, 0, 800, 600);
        slide.draw(mockGraphics, area, mockObserver);
        verify(mockGraphics, atLeastOnce()).drawString(anyString(), anyInt(), anyInt());
    }
}

