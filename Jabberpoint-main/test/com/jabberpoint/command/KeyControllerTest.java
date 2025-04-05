package com.jabberpoint.command;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

  @Before
  public void setUp() {
    System.setProperty("java.awt.headless", "true");
    System.setProperty("testfx.robot", "glass");
    System.setProperty("testfx.headless", "true");
    System.setProperty("prism.order", "sw");
    System.setProperty("prism.text", "t2k");
  }

  @Test
  public void keyPressedWithNextKeysExecutesNextCommand() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setNextSlideCommand(mockNextCommand);

    // Only test right arrow and space
    KeyEvent rightEvent = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, ' ');
    controller.keyPressed(rightEvent);

    KeyEvent spaceEvent = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_SPACE, ' ');
    controller.keyPressed(spaceEvent);

    verify(mockNextCommand, times(2)).execute();
  }

  @Test
  public void keyPressedWithPrevKeysExecutesPrevCommand() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setPrevSlideCommand(mockPrevCommand);

    // Only test left arrow and backspace
    KeyEvent leftEvent = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, ' ');
    controller.keyPressed(leftEvent);

    KeyEvent backspaceEvent = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_BACK_SPACE, '\b');
    controller.keyPressed(backspaceEvent);

    verify(mockPrevCommand, times(2)).execute();
  }

  @Test
  public void keyPressedWithEscapeExecutesExitCommand() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setExitCommand(mockExitCommand);

    KeyEvent escapeEvent = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, (char)27);
    controller.keyPressed(escapeEvent);

    verify(mockExitCommand, times(1)).execute();
  }

  @Test
  public void keyPressedWithCtrlZExecutesUndoCommand() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setUndoCommand(mockUndoCommand);

    KeyEvent ctrlZEvent = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), InputEvent.CTRL_DOWN_MASK, KeyEvent.VK_Z, 'z');
    controller.keyPressed(ctrlZEvent);

    verify(mockUndoCommand, times(1)).execute();
  }

  @Test
  public void keyPressedWithUnknownKeyDoesNothing() {
    KeyController controller = new KeyController(mockPresentation);
    controller.setNextSlideCommand(mockNextCommand);
    controller.setPrevSlideCommand(mockPrevCommand);
    controller.setExitCommand(mockExitCommand);
    controller.setUndoCommand(mockUndoCommand);

    KeyEvent randomEvent = new KeyEvent(new java.awt.Button(), KeyEvent.KEY_PRESSED,
        System.currentTimeMillis(), 0, KeyEvent.VK_F1, ' ');
    controller.keyPressed(randomEvent);

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