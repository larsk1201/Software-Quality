import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * <p>This is the KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class KeyController extends KeyAdapter {

  private Presentation presentation; // Er worden commando's gegeven aan de presentatie
  private Command nextSlideCommand;
  private Command prevSlideCommand;
  private Command exitCommand;

  public KeyController(Presentation presentation) {
    this.presentation = presentation;
  }

  public Presentation getPresentation() {
    return this.presentation;
  }

  public void setPresentation(Presentation presentation) {
    this.presentation = presentation;
  }

  public Command getNextSlideCommand() {
    return this.nextSlideCommand;
  }

  public void setNextSlideCommand(Command nextSlideCommand) {
    this.nextSlideCommand = nextSlideCommand;
  }

  public Command getPrevSlideCommand() {
    return this.prevSlideCommand;
  }

  public void setPrevSlideCommand(Command prevSlideCommand) {
    this.prevSlideCommand = prevSlideCommand;
  }

  public Command getExitCommand() {
    return this.exitCommand;
  }

  public void setExitCommand(Command exitCommand) {
    this.exitCommand = exitCommand;
  }

  public void keyPressed(KeyEvent keyEvent) {
    switch (keyEvent.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_ENTER:
      case '+':
        if (nextSlideCommand != null) {
          nextSlideCommand.execute();
        }
        break;
      case KeyEvent.VK_PAGE_UP:
      case KeyEvent.VK_UP:
      case '-':
        if (prevSlideCommand != null) {
          prevSlideCommand.execute();
        }
        break;
      case 'q':
      case 'Q':
        if (exitCommand != null) {
          exitCommand.execute();
        }
        break; // wordt nooit bereikt als het goed is
      default:
        break;
    }
  }
}
