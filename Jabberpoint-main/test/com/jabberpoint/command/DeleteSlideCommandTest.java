package com.jabberpoint.command;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.memento.PresentationCaretaker;
import java.awt.Frame;

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

    DeleteSlideCommand command = new DeleteSlideCommand(mockPresentation, mockFrame, mockCaretaker) {
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

    DeleteSlideCommand command = new DeleteSlideCommand(mockPresentation, mockFrame, mockCaretaker) {
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

