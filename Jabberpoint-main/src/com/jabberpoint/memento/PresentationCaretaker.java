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

  public void clearHistory() {
    history.clear();
  }

  public void save(Presentation presentation) {
    Memento memento = presentation.createMemento();
    history.push(memento);
  }

  public void load(Presentation presentation) {
    if (!history.isEmpty()) {
      Memento memento = history.pop();
      presentation.restoreMemento(memento);
    }
  }
}

