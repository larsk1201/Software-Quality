package com.jabberpoint.command;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.ui.Slide;
import java.awt.Frame;

@RunWith(MockitoJUnitRunner.class)
public class CommandTest {

    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private PresentationCaretaker mockCaretaker;
    
    @Mock
    private Frame mockFrame;

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
        when(mockCaretaker.getHistory()).thenReturn(new java.util.Stack<>());
        when(mockCaretaker.getHistory().isEmpty()).thenReturn(false);
        
        UndoCommand command = new UndoCommand(mockPresentation, mockCaretaker, mockFrame);
        command.execute();
        verify(mockCaretaker, times(1)).load(mockPresentation);
    }
    
    @Test
    public void undoCommandExecuteDoesNothingWhenHistoryIsEmpty() {
        when(mockCaretaker.getHistory()).thenReturn(new java.util.Stack<>());
        when(mockCaretaker.getHistory().isEmpty()).thenReturn(true);
        
        UndoCommand command = new UndoCommand(mockPresentation, mockCaretaker, mockFrame);
        command.execute();
        verify(mockCaretaker, never()).load(any(Presentation.class));
    }
    
    @Test
    public void addSlideCommandExecuteCreatesNewSlideAndAddsItToPresentation() {
        when(mockPresentation.getSize()).thenReturn(5);
        
        AddSlideCommand command = new AddSlideCommand(mockPresentation, mockFrame, mockCaretaker) {
            @Override
            public void execute() {
                Slide newSlide = new Slide();
                newSlide.setTitle("Test Slide");
                mockPresentation.append(newSlide);
                mockPresentation.setSlideNumber(mockPresentation.getSize() - 1);
            }
        };
        
        command.execute();
        
        verify(mockPresentation, times(1)).append(any(Slide.class));
        verify(mockPresentation, times(1)).setSlideNumber(anyInt());
    }
    
    @Test
    public void deleteSlideCommandExecuteDeletesCurrentSlideFromPresentation() {
        when(mockPresentation.getSize()).thenReturn(3);
        when(mockPresentation.getSlideNumber()).thenReturn(1);
        
        DeleteSlideCommand command = new DeleteSlideCommand(mockPresentation, mockFrame, mockCaretaker) {
            @Override
            public void execute() {
                mockPresentation.deleteSlide(mockPresentation.getSlideNumber());
            }
        };
        
        command.execute();
        
        verify(mockPresentation, times(1)).deleteSlide(1);
    }
    
    @Test
    public void keyControllerKeyPressedWithRightArrowExecutesNextSlideCommand() {
        KeyController controller = new KeyController(mockPresentation);
        Command mockNextCommand = mock(Command.class);
        controller.setNextSlideCommand(mockNextCommand);
        
        java.awt.event.KeyEvent nextKeyEvent = new java.awt.event.KeyEvent(
            new java.awt.Button(), java.awt.event.KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, java.awt.event.KeyEvent.VK_RIGHT, ' ');
        controller.keyPressed(nextKeyEvent);
        
        verify(mockNextCommand, times(1)).execute();
    }
    
    @Test
    public void keyControllerKeyPressedWithLeftArrowExecutesPrevSlideCommand() {
        KeyController controller = new KeyController(mockPresentation);
        Command mockPrevCommand = mock(Command.class);
        controller.setPrevSlideCommand(mockPrevCommand);
        
        java.awt.event.KeyEvent prevKeyEvent = new java.awt.event.KeyEvent(
            new java.awt.Button(), java.awt.event.KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, java.awt.event.KeyEvent.VK_LEFT, ' ');
        controller.keyPressed(prevKeyEvent);
        
        verify(mockPrevCommand, times(1)).execute();
    }
    
    @Test
    public void keyControllerKeyPressedWithQExecutesExitCommand() {
        KeyController controller = new KeyController(mockPresentation);
        Command mockExitCommand = mock(Command.class);
        controller.setExitCommand(mockExitCommand);
        
        java.awt.event.KeyEvent exitKeyEvent = new java.awt.event.KeyEvent(
            new java.awt.Button(), java.awt.event.KeyEvent.KEY_PRESSED, 
            System.currentTimeMillis(), 0, 'q', 'q');
        controller.keyPressed(exitKeyEvent);
        
        verify(mockExitCommand, times(1)).execute();
    }
}

