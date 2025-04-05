package com.jabberpoint.command;

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
public class AddSlideCommandTest {

  @Mock
  private Presentation mockPresentation;

  @Mock
  private PresentationCaretaker mockCaretaker;

  @Mock
  private Frame mockFrame;

  @Test
  public void executeAddsNewSlideToPresentation() {
    AddSlideCommand command = new AddSlideCommand(mockPresentation, mockFrame, mockCaretaker) {
      @Override
      public void execute() {
        Slide newSlide = new Slide();
        newSlide.setTitle("Test Slide");

        if (mockCaretaker != null) {
          mockCaretaker.save(mockPresentation);
        }

        mockPresentation.append(newSlide);
        mockPresentation.setSlideNumber(0);
      }
    };

    command.execute();

    verify(mockCaretaker, times(1)).save(mockPresentation);
    verify(mockPresentation, times(1)).append(any(Slide.class));
    verify(mockPresentation, times(1)).setSlideNumber(anyInt());
  }
}

