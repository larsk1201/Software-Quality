package com.jabberpoint.command;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jabberpoint.memento.Memento;
import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.ui.Slide;
import com.jabberpoint.util.Presentation;
import java.util.Stack;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CommandTest {

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
  private PresentationCaretaker mockCaretaker;

  @Mock
  private Stack<Memento> mockStack;

  @Test
  public void nextSlideCommandExecuteCallsNextSlideOnPresentation() {
    NextSlideCommand command = new NextSlideCommand(mockPresentation);
    command.execute();
    verify(mockPresentation, times(1)).nextSlide();
  }

  @Test
  public void prevSlideCommandExecuteCallsPrevSlideOnPresentation() {
    PrevSlideCommand command = new PrevSlideCommand(mockPresentation);
    command.execute();
    verify(mockPresentation, times(1)).prevSlide();
  }

  @Test
  public void undoCommandExecuteCallsLoadOnCaretaker() {
    when(mockCaretaker.getHistory()).thenReturn(mockStack);
    when(mockStack.isEmpty()).thenReturn(false);

    UndoCommand command = new UndoCommand(mockPresentation, mockCaretaker, null);
    command.execute();
    verify(mockCaretaker, times(1)).load(mockPresentation);
  }

  @Test
  public void undoCommandExecuteDoesNothingWhenHistoryIsEmpty() {
    when(mockCaretaker.getHistory()).thenReturn(mockStack);
    when(mockStack.isEmpty()).thenReturn(true);

    UndoCommand command = new UndoCommand(mockPresentation, mockCaretaker, null);
    command.execute();
    verify(mockCaretaker, never()).load(any(Presentation.class));
  }

  @Test
  public void addSlideCommandExecuteCreatesNewSlideAndAddsItToPresentation() {
    AddSlideCommand command = new AddSlideCommand(mockPresentation, null, mockCaretaker) {
      @Override
      public void execute() {
        Slide newSlide = new Slide();
        newSlide.setTitle("Test Slide");
        mockPresentation.append(newSlide);
        mockPresentation.setSlideNumber(0);
      }
    };

    command.execute();

    verify(mockPresentation, times(1)).append(any(Slide.class));
    verify(mockPresentation, times(1)).setSlideNumber(anyInt());
  }

  @Test
  public void deleteSlideCommandExecuteDeletesCurrentSlideFromPresentation() {
    DeleteSlideCommand command = new DeleteSlideCommand(mockPresentation, null, mockCaretaker) {
      @Override
      public void execute() {
        mockPresentation.deleteSlide(0);
      }
    };

    command.execute();

    verify(mockPresentation, times(1)).deleteSlide(anyInt());
  }

  @Test
  public void keyControllerKeyPressedWithRightArrowExecutesNextSlideCommand() {
    // Mock the KeyController and the next slide command
    KeyController controller = new KeyController(mockPresentation);
    Command mockNextCommand = mock(Command.class);
    controller.setNextSlideCommand(mockNextCommand);

    // Mock the KeyEvent for the right arrow key press
    java.awt.event.KeyEvent nextKeyEvent = mock(java.awt.event.KeyEvent.class);
    when(nextKeyEvent.getKeyCode()).thenReturn(
        java.awt.event.KeyEvent.VK_RIGHT);  // Simulate RIGHT ARROW key

    // Simulate key press
    controller.keyPressed(nextKeyEvent);

    // Verify that the correct command was executed
    verify(mockNextCommand, times(1)).execute();
  }

  @Test
  public void keyControllerKeyPressedWithLeftArrowExecutesPrevSlideCommand() {
    // Mock the KeyController and the prev slide command
    KeyController controller = new KeyController(mockPresentation);
    Command mockPrevCommand = mock(Command.class);
    controller.setPrevSlideCommand(mockPrevCommand);

    // Mock the KeyEvent for the left arrow key press
    java.awt.event.KeyEvent prevKeyEvent = mock(java.awt.event.KeyEvent.class);
    when(prevKeyEvent.getKeyCode()).thenReturn(
        java.awt.event.KeyEvent.VK_LEFT);  // Simulate LEFT ARROW key

    // Simulate key press
    controller.keyPressed(prevKeyEvent);

    // Verify that the correct command was executed
    verify(mockPrevCommand, times(1)).execute();
  }

  @Test
  public void keyControllerKeyPressedWithEscapeExecutesExitCommand() {
    // Mock the KeyController and the exit command
    KeyController controller = new KeyController(mockPresentation);
    Command mockExitCommand = mock(Command.class);
    controller.setExitCommand(mockExitCommand);

    // Mock the KeyEvent for the escape key press
    java.awt.event.KeyEvent exitKeyEvent = mock(java.awt.event.KeyEvent.class);
    when(exitKeyEvent.getKeyCode()).thenReturn(
        java.awt.event.KeyEvent.VK_ESCAPE);  // Simulate ESCAPE key

    // Simulate key press
    controller.keyPressed(exitKeyEvent);

    // Verify that the correct command was executed
    verify(mockExitCommand, times(1)).execute();
  }
}
