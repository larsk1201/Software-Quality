package com.jabberpoint.command;

import com.jabberpoint.util.Presentation;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

/**
 * <p>This is the com.jabberpoint.command.KeyController (KeyListener)</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class KeyController extends KeyAdapter {

  private Presentation presentation; // Er worden commando's gegeven aan de presentatie
  private Command nextSlideCommand;
  private Command prevSlideCommand;
  private Command exitCommand;
  private Command undoCommand;

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

  public Command getUndoCommand() {
    return this.undoCommand;
  }

  public void setUndoCommand(Command undoCommand) {
    this.undoCommand = undoCommand;
  }

  public void keyPressed(KeyEvent keyEvent) {
    // Debug print to see which key is being pressed
    System.out.println("Key pressed: " + keyEvent.getKeyCode() + " (" + KeyEvent.getKeyText(keyEvent.getKeyCode()) + ")");

    switch (keyEvent.getKeyCode()) {
      case KeyEvent.VK_PAGE_DOWN:
      case KeyEvent.VK_DOWN:
      case KeyEvent.VK_ENTER:
      case KeyEvent.VK_RIGHT:
      case KeyEvent.VK_SPACE:
      case '+':
        if (nextSlideCommand != null) {
          nextSlideCommand.execute();
        }
        break;
      case KeyEvent.VK_PAGE_UP:
      case KeyEvent.VK_UP:
      case KeyEvent.VK_LEFT:
      case KeyEvent.VK_BACK_SPACE:
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
      case KeyEvent.VK_Z:
        if ((keyEvent.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
          if (undoCommand != null) {
            undoCommand.execute();
          }
        }
        break;
      default:
        break;
    }
  }
}

