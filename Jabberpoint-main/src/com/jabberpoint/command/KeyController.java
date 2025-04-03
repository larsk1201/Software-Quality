package com.jabberpoint.command;

import com.jabberpoint.util.Presentation;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

public class KeyController extends KeyAdapter {

  private Presentation presentation;
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
    if (keyEvent.getKeyCode() == KeyEvent.VK_Z &&
        (keyEvent.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) != 0) {
      if (undoCommand != null) {
        undoCommand.execute();
      }
      return;
    }

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
        break;
      default:
        break;
    }
  }
}

