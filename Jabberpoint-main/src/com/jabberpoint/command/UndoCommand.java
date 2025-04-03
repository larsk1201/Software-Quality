package com.jabberpoint.command;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.memento.PresentationCaretaker;
import javax.swing.JOptionPane;
import java.awt.Frame;

public class UndoCommand implements Command {

  private Presentation presentation;
  private PresentationCaretaker caretaker;
  private Frame parent;

  public UndoCommand(Presentation presentation, PresentationCaretaker caretaker, Frame parent) {
    this.presentation = presentation;
    this.caretaker = caretaker;
    this.parent = parent;
  }

  @Override
  public void execute() {
    if (caretaker != null) {
      if (caretaker.getHistory().isEmpty()) {
        if (parent != null) {
          JOptionPane.showMessageDialog(parent,
              "Nothing to undo.",
              "Undo",
              JOptionPane.INFORMATION_MESSAGE);
        }
        return;
      }
      caretaker.load(presentation);
    }
  }
}

