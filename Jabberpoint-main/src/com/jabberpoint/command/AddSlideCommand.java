package com.jabberpoint.command;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.ui.Slide;
import javax.swing.JOptionPane;
import java.awt.Frame;

public class AddSlideCommand implements Command {

  private Presentation presentation;
  private Frame parent;
  private PresentationCaretaker caretaker;

  public AddSlideCommand(Presentation presentation, Frame parent, PresentationCaretaker caretaker) {
    this.presentation = presentation;
    this.parent = parent;
    this.caretaker = caretaker;
  }

  @Override
  public void execute() {
    Slide newSlide = new Slide();

    String title = JOptionPane.showInputDialog(parent, "Enter slide title:", "New Slide", JOptionPane.QUESTION_MESSAGE);

    if (title == null) {
      return;
    }

    if (caretaker != null) {
      caretaker.save(presentation);
    }

    newSlide.setTitle(title);
    presentation.append(newSlide);

    presentation.setSlideNumber(presentation.getSize() - 1);
  }
}

