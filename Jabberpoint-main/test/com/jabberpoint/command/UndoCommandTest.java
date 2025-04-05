package com.jabberpoint.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.memento.Memento;
import java.util.Stack;

@RunWith(MockitoJUnitRunner.class)
public class UndoCommandTest {

    @Mock
    private Presentation mockPresentation;

    @Mock
    private PresentationCaretaker mockCaretaker;

    @Mock
    private Stack<Memento> mockStack;

    @Test
    public void undoCommandExecuteCallsLoadOnCaretakerWhenHistoryIsNotEmpty() {
        when(mockCaretaker.getHistory()).thenReturn(mockStack);
        when(mockStack.isEmpty()).thenReturn(false);

        UndoCommand command = new UndoCommand(mockPresentation, mockCaretaker, null);
        command.execute();

        verify(mockCaretaker, times(1)).load(mockPresentation);
    }

    @Test
    public void undoCommandExecuteDoesNotCallLoadOnCaretakerWhenHistoryIsEmpty() {
        when(mockCaretaker.getHistory()).thenReturn(mockStack);
        when(mockStack.isEmpty()).thenReturn(true);

        UndoCommand command = new UndoCommand(mockPresentation, mockCaretaker, null);
        command.execute();

        verify(mockCaretaker, never()).load(any(Presentation.class));
    }
}

