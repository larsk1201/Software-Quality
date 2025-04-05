package com.jabberpoint.ui;

import static org.junit.Assert.*;

import com.jabberpoint.command.KeyController;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import org.junit.Before;
import org.junit.Test;

public class SlideViewerFrameTest {

    private Presentation mockPresentation;
    private SlideViewerFrame testFrame;

    // Subclass to suppress actual window display
    static class TestableSlideViewerFrame extends SlideViewerFrame {
        public TestableSlideViewerFrame(String title, Presentation presentation) {
            super(title, presentation);
        }

        @Override
        public void setVisible(boolean b) {
            // Do nothing – override to suppress GUI
        }
    }

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        Style.createStyles();
        mockPresentation = new Presentation(); // use real instance for full coverage
        testFrame = new TestableSlideViewerFrame("Test Title", mockPresentation);
    }

    @Test
    public void frameCreationDoesNotThrowExceptions() {
        assertNotNull(testFrame);
        assertNotNull(testFrame.getKeyController());
    }

    @Test
    public void getKeyControllerReturnsNonNullKeyController() {
        KeyController controller = testFrame.getKeyController();
        assertNotNull(controller);
    }

    @Test
    public void setupWindowSetsCorrectFrameProperties() {
        assertEquals("Jabberpoint 1.6 - OU", testFrame.getTitle());
        assertEquals(SlideViewerFrame.WIDTH, testFrame.getSize().width);
        assertEquals(SlideViewerFrame.HEIGHT, testFrame.getSize().height);
        assertNotNull(testFrame.getMenuBar());
    }

    @Test
    public void frameHasKeyListenerRegistered() {
        assertTrue(testFrame.getKeyListeners().length > 0);
    }

    @Test
    public void frameIsFocusable() {
        assertTrue(testFrame.isFocusable());
    }
}
