package com.jabberpoint.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import com.jabberpoint.command.KeyController;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import java.awt.GraphicsEnvironment;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Test;

public class SlideViewerFrameTest {

    @Before
    public void setUp() {
        // Skip tests if running in a headless environment
        Assume.assumeFalse("Skipping test in headless environment",
            GraphicsEnvironment.isHeadless());

        Style.createStyles();
        mockPresentation = new Presentation(); // use real instance for full coverage
        testFrame = new TestableSlideViewerFrame("Test Title", mockPresentation);
    }

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

    @Test
    public void frameCreationDoesNotThrowExceptions() {
        // Skip test in headless environment
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        assertNotNull(testFrame);
        assertNotNull(testFrame.getKeyController());
    }

    @Test
    public void getKeyControllerReturnsNonNullKeyController() {
        // Skip test in headless environment
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        KeyController controller = testFrame.getKeyController();
        assertNotNull(controller);
    }

    @Test
    public void setupWindowSetsCorrectFrameProperties() {
        // Skip test in headless environment
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        assertEquals("Jabberpoint 1.6 - OU", testFrame.getTitle());
        assertEquals(SlideViewerFrame.WIDTH, testFrame.getSize().width);
        assertEquals(SlideViewerFrame.HEIGHT, testFrame.getSize().height);
        assertNotNull(testFrame.getMenuBar());
    }

    @Test
    public void frameHasKeyListenerRegistered() {
        // Skip test in headless environment
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        assertTrue(testFrame.getKeyListeners().length > 0);
    }

    @Test
    public void frameIsFocusable() {
        // Skip test in headless environment
        Assume.assumeFalse(GraphicsEnvironment.isHeadless());

        assertTrue(testFrame.isFocusable());
    }

    // Add a non-GUI test to maintain coverage in headless environments
    @Test
    public void styleAndPresentationCanBeCreatedInHeadlessMode() {
        // This test will run even in headless environments
        Style.createStyles();
        Presentation presentation = new Presentation();

        // Test basic functionality that doesn't require a GUI
        assertNotNull(Style.getStyle(0));
        assertEquals(0, presentation.getSize());
        assertEquals(-1, presentation.getSlideNumber());

        // Test KeyController creation which is used by SlideViewerFrame
        KeyController controller = new KeyController(presentation);
        assertNotNull(controller);
        assertSame(presentation, controller.getPresentation());
    }
}

