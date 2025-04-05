package com.jabberpoint.command;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jabberpoint.util.Presentation;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class KeyControllerTest {

  @Before
  public void setUp() {
    System.setProperty("java.awt.headless", "true");
    System.setProperty("testfx.robot", "glass");
    System.setProperty("testfx.headless", "true");
    System.setProperty("prism.order", "sw");
    System.setProperty("prism.text", "t2k");

    System.setProperty("javafx.embed.singleThread", "true");
    System.setProperty("javafx.platform", "headless");
  }

  @Mock
  private Presentation mockPresentation;

  @Mock
  private Command mockNextCommand;

  @Mock
  private Command mockPrevCommand;

  @Mock
  private Command mockExitCommand;

  @Mock
  private Command mockUndoCommand;

  @Test
  public void keyPressedWithNextKeysExecutesNextCommand() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setNextSlideCommand(mockNextCommand);

    // Mock right arrow key press event
    KeyEvent rightEvent = mock(KeyEvent.class);
    when(rightEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);
    controller.keyPressed(rightEvent);

    // Mock space key press event
    KeyEvent spaceEvent = mock(KeyEvent.class);
    when(spaceEvent.getKeyCode()).thenReturn(KeyEvent.VK_SPACE);
    controller.keyPressed(spaceEvent);

    // Verify that the next slide command was executed twice
    verify(mockNextCommand, times(2)).execute();
  }

  @Test
  public void keyPressedWithPrevKeysExecutesPrevCommand() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setPrevSlideCommand(mockPrevCommand);

    // Mock left arrow key press event
    KeyEvent leftEvent = mock(KeyEvent.class);
    when(leftEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);
    controller.keyPressed(leftEvent);

    // Mock backspace key press event
    KeyEvent backspaceEvent = mock(KeyEvent.class);
    when(backspaceEvent.getKeyCode()).thenReturn(KeyEvent.VK_BACK_SPACE);
    controller.keyPressed(backspaceEvent);

    // Verify that the prev slide command was executed twice
    verify(mockPrevCommand, times(2)).execute();
  }

  @Test
  public void keyPressedWithEscapeExecutesExitCommand() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setExitCommand(mockExitCommand);

    // Mock escape key press event
    KeyEvent escapeEvent = mock(KeyEvent.class);
    when(escapeEvent.getKeyCode()).thenReturn(KeyEvent.VK_ESCAPE);
    controller.keyPressed(escapeEvent);

    // Verify that the exit command was executed once
    verify(mockExitCommand, times(1)).execute();
  }

  @Test
  public void keyPressedWithCtrlZExecutesUndoCommand() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setUndoCommand(mockUndoCommand);

    // Mock Ctrl+Z key press event
    KeyEvent ctrlZEvent = mock(KeyEvent.class);
    when(ctrlZEvent.getKeyCode()).thenReturn(KeyEvent.VK_Z);
    when(ctrlZEvent.getModifiersEx()).thenReturn(InputEvent.CTRL_DOWN_MASK);  // Simulate CTRL key
    controller.keyPressed(ctrlZEvent);

    // Verify that the undo command was executed once
    verify(mockUndoCommand, times(1)).execute();
  }

  @Test
  public void keyPressedWithUnknownKeyDoesNothing() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setNextSlideCommand(mockNextCommand);
    controller.setPrevSlideCommand(mockPrevCommand);
    controller.setExitCommand(mockExitCommand);
    controller.setUndoCommand(mockUndoCommand);

    // Mock unknown key press event (F1 key)
    KeyEvent randomEvent = mock(KeyEvent.class);
    when(randomEvent.getKeyCode()).thenReturn(KeyEvent.VK_F1);
    controller.keyPressed(randomEvent);

    // Verify that no command was executed for the unknown key
    verify(mockNextCommand, never()).execute();
    verify(mockPrevCommand, never()).execute();
    verify(mockExitCommand, never()).execute();
    verify(mockUndoCommand, never()).execute();
  }

  @Test
  public void gettersAndSettersWorkCorrectly() {
    KeyController controller = new KeyController(mockPresentation);

    assertSame(mockPresentation, controller.getPresentation());

    Presentation newPresentation = mock(Presentation.class);
    controller.setPresentation(newPresentation);
    assertSame(newPresentation, controller.getPresentation());

    controller.setNextSlideCommand(mockNextCommand);
    assertSame(mockNextCommand, controller.getNextSlideCommand());

    controller.setPrevSlideCommand(mockPrevCommand);
    assertSame(mockPrevCommand, controller.getPrevSlideCommand());

    controller.setExitCommand(mockExitCommand);
    assertSame(mockExitCommand, controller.getExitCommand());

    controller.setUndoCommand(mockUndoCommand);
    assertSame(mockUndoCommand, controller.getUndoCommand());
  }
}
