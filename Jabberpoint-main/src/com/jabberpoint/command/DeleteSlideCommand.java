package com.jabberpoint.command;

import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.util.Presentation;
import java.awt.Frame;
import javax.swing.JOptionPane;

public class DeleteSlideCommand implements Command {

  private Presentation presentation;
  private Frame parent;
  private PresentationCaretaker caretaker;

  public DeleteSlideCommand(Presentation presentation, Frame parent, PresentationCaretaker caretaker) {
    this.presentation = presentation;
    this.parent = parent;
    this.caretaker = caretaker;
  }

  @Override
  public void execute() {
    if (presentation.getSize() <= 0) {
      JOptionPane.showMessageDialog(parent,
          "No slides to delete.",
          "Delete Slide",
          JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    int currentSlide = presentation.getSlideNumber() + 1;
    int confirm = JOptionPane.showConfirmDialog(
        parent,
        "Are you sure you want to delete slide " + currentSlide + "?",
        "Delete Slide",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      if (caretaker != null) {
        caretaker.save(presentation);
      }

      presentation.deleteSlide(presentation.getSlideNumber());
    }
  }
}

