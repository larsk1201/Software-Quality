package com.jabberpoint.ui;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.command.KeyController;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;

@RunWith(MockitoJUnitRunner.class)
public class SlideViewerFrameTest {

    @Mock
    private Presentation mockPresentation;
    
    @Test
    public void frameCreationDoesNotThrowExceptions() {
        try {
            Style.createStyles();
            
            SlideViewerFrame frame = new SlideViewerFrame("Test Frame", mockPresentation) {
                @Override
                public void setVisible(boolean visible) {
                }
            };
            
            assertNotNull(frame);
            assertNotNull(frame.getKeyController());
            
            frame.dispose();
        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }
    
    @Test
    public void getKeyControllerReturnsNonNullKeyController() {
        Style.createStyles();
        SlideViewerFrame frame = new SlideViewerFrame("Test Frame", mockPresentation) {
            @Override
            public void setVisible(boolean visible) {
            }
        };
        
        KeyController controller = frame.getKeyController();
        
        assertNotNull(controller);
        
        frame.dispose();
    }
}

