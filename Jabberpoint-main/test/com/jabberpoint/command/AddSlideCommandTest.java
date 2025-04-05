package com.jabberpoint.command;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.ui.Slide;
import com.jabberpoint.util.Presentation;
import java.awt.Frame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

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

