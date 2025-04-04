package com.jabberpoint.ui;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;

@RunWith(MockitoJUnitRunner.class)
public class SlideViewerComponentTest {

    private SlideViewerComponent component;
    
    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private JFrame mockFrame;
    
    @Mock
    private Graphics mockGraphics;
    
    @Mock
    private Slide mockSlide;
    
    @Before
    public void setUp() {
        Style.createStyles();
        component = new SlideViewerComponent(mockPresentation, mockFrame);
    }
    
    @Test
    public void componentCreationSucceeds() {
        assertNotNull(component);
    }
    
    @Test
    public void getPreferredSizeReturnsCorrectDimensions() {
        Dimension size = component.getPreferredSize();
        assertEquals(Slide.WIDTH, size.width);
        assertEquals(Slide.HEIGHT, size.height);
    }
    
    @Test
    public void updateChangesSlideAndUpdatesFrameTitle() {
        when(mockPresentation.getTitle()).thenReturn("Test Title");
        component.update(mockPresentation, mockSlide);
        verify(mockFrame).setTitle("Test Title");
    }
    
    @Test
    public void paintComponentDrawsSlideInformation() {
        when(mockPresentation.getSlideNumber()).thenReturn(0);
        when(mockPresentation.getSize()).thenReturn(1);
        component.paintComponent(mockGraphics);
        verify(mockGraphics).setColor(any());
        verify(mockGraphics).fillRect(anyInt(), anyInt(), anyInt(), anyInt());
    }
}

