package com.jabberpoint.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.jabberpoint.command.KeyController;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlideViewerFrameTest {

    @Mock
    private Presentation mockPresentation;

    @Before
    public void setUp() {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
    }

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

    @Test
    public void setupWindowSetsCorrectFrameProperties() {
        Style.createStyles();
        SlideViewerFrame frame = new SlideViewerFrame("Test Frame", mockPresentation) {
            @Override
            public void setVisible(boolean visible) {
            }
        };
        assertEquals("Jabberpoint 1.6 - OU", frame.getTitle());
        assertEquals(SlideViewerFrame.WIDTH, frame.getSize().width);
        assertEquals(SlideViewerFrame.HEIGHT, frame.getSize().height);
        assertNotNull(frame.getMenuBar());
        frame.dispose();
    }

    @Test
    public void frameHasKeyListenerRegistered() {
        Style.createStyles();
        SlideViewerFrame frame = new SlideViewerFrame("Test Frame", mockPresentation) {
            @Override
            public void setVisible(boolean visible) {
            }
        };
        assertTrue(frame.getKeyListeners().length > 0);
        frame.dispose();
    }

    @Test
    public void frameRequestsFocusOnCreation() {
        Style.createStyles();
        final boolean[] focusRequested = {false};
        SlideViewerFrame frame = new SlideViewerFrame("Test Frame", mockPresentation) {
            @Override
            public void setVisible(boolean visible) {
            }
            @Override
            public void requestFocus() {
                focusRequested[0] = true;
            }
        };
        assertTrue("Focus should have been requested", focusRequested[0]);
        frame.dispose();
    }
}