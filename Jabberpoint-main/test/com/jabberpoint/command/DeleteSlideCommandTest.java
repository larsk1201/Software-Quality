package com.jabberpoint.command;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.util.Presentation;
import java.awt.Frame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DeleteSlideCommandTest {

  @Mock
  private Presentation mockPresentation;

  @Mock
  private PresentationCaretaker mockCaretaker;

  @Mock
  private Frame mockFrame;

  @Test
  public void executeDeletesCurrentSlideWhenConfirmed() {
    when(mockPresentation.getSize()).thenReturn(2);
    when(mockPresentation.getSlideNumber()).thenReturn(1);

    DeleteSlideCommand command = new DeleteSlideCommand(mockPresentation, mockFrame,
        mockCaretaker) {
      @Override
      public void execute() {
        if (mockPresentation.getSize() <= 0) {
          return;
        }

        if (mockCaretaker != null) {
          mockCaretaker.save(mockPresentation);
        }

        mockPresentation.deleteSlide(mockPresentation.getSlideNumber());
      }
    };

    command.execute();

    verify(mockCaretaker, times(1)).save(mockPresentation);
    verify(mockPresentation, times(1)).deleteSlide(1);
  }

  @Test
  public void executeDoesNothingWhenNoSlides() {
    when(mockPresentation.getSize()).thenReturn(0);

    DeleteSlideCommand command = new DeleteSlideCommand(mockPresentation, mockFrame,
        mockCaretaker) {
      @Override
      public void execute() {
        if (mockPresentation.getSize() <= 0) {
          return;
        }

        mockPresentation.deleteSlide(mockPresentation.getSlideNumber());
      }
    };

    command.execute();

    verify(mockPresentation, never()).deleteSlide(anyInt());
  }
}

