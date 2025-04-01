import java.util.Stack;

public class PresentationCaretaker {

  private Stack<Memento> history;

  public Stack<Memento> getHistory() {
    return this.history;
  }

  public void setHistory(Stack<Memento> history) {
    this.history = history;
  }

  public void save(Presentation presentation) {

  }

  public void load(Presentation presentation) {

  }
}
