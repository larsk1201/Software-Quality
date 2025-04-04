package com.jabberpoint.command;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.memento.PresentationCaretaker;
import java.awt.Frame;
import java.util.Stack;

@RunWith(MockitoJUnitRunner.class)
public class UndoCommandTest {

    @Mock
    private Presentation mockPresentation;
    
    @Mock
    private PresentationCaretaker mockCaretaker;
    
    @Mock
    private Frame mockFrame;
    
    @Test
    public void undoCommandExecuteCallsLoadOnCaretakerWhenHistoryIsNotEmpty() {
        Stack<Object> nonEmptyStack = new Stack<>();
        nonEmptyStack.push(new Object());
        
        when(mockCaretaker.getHistory()).thenReturn(nonEmptyStack);
        
        UndoCommand command = new UndoCommand(mockPresentation, mockCaretaker, mockFrame);
        command.execute();
        
        verify(mockCaretaker, times(1)).load(mockPresentation);
    }
    
    @Test
    public void undoCommandExecuteDoesNotCallLoadOnCaretakerWhenHistoryIsEmpty() {
        Stack<Object> emptyStack = new Stack<>();
        
        when(mockCaretaker.getHistory()).thenReturn(emptyStack);
        
        UndoCommand command = new UndoCommand(mockPresentation, mockCaretaker, mockFrame);
        command.execute();
        
        verify(mockCaretaker, never()).load(any(Presentation.class));
    }
}

