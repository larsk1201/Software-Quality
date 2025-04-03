package com.jabberpoint.command;

import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.util.Presentation;

public class UndoCommand implements Command {

  private PresentationCaretaker caretaker;
  private Presentation presentation;

  public UndoCommand(PresentationCaretaker caretaker, Presentation presentation) {
    this.caretaker = caretaker;
    this.presentation = presentation;
  }

  @Override
  public void execute() {
    caretaker.load(presentation);
  }
}