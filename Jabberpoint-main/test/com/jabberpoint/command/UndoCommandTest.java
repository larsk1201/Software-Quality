package com.jabberpoint.command;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jabberpoint.memento.Memento;
import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.util.Presentation;
import java.util.Stack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

