package com.jabberpoint.memento;

import com.jabberpoint.util.Presentation;
import java.util.Stack;

public class PresentationCaretaker {

  private Stack<Memento> history = new Stack<>();

  public Stack<Memento> getHistory() {
    return this.history;
  }

  public void setHistory(Stack<Memento> history) {
    this.history = history;
  }

  public void save(Presentation presentation) {
    history.push(presentation.createMemento());
  }

  public void load(Presentation presentation) {
    if (!history.isEmpty()) {
      presentation.restoreMemento(history.pop());
    }
  }
}
