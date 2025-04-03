package com.jabberpoint.command;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.memento.PresentationCaretaker;

public class UndoCommand implements Command {

  private Presentation presentation;
  private PresentationCaretaker caretaker;

  public UndoCommand(Presentation presentation, PresentationCaretaker caretaker) {
    this.presentation = presentation;
    this.caretaker = caretaker;
  }

  @Override
  public void execute() {
    caretaker.load(presentation);
  }
}

