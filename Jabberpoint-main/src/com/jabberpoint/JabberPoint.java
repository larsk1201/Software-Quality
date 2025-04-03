package com.jabberpoint;

import com.jabberpoint.command.AddSlideCommand;
import com.jabberpoint.command.Command;
import com.jabberpoint.command.DeleteSlideCommand;
import com.jabberpoint.command.ExitCommand;
import com.jabberpoint.command.KeyController;
import com.jabberpoint.command.NextSlideCommand;
import com.jabberpoint.command.PrevSlideCommand;
import com.jabberpoint.command.UndoCommand;
import com.jabberpoint.factory.Accessor;
import com.jabberpoint.factory.XMLAccessor;
import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.ui.MenuController;
import com.jabberpoint.ui.SlideViewerFrame;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import java.io.IOException;
import javax.swing.JOptionPane;

// Java
public class JabberPoint {
  public static void main(String[] argv) {
    Style.createStyles();
    Presentation presentation = new Presentation();
    PresentationCaretaker caretaker = new PresentationCaretaker();
    SlideViewerFrame frame = new SlideViewerFrame("Jabberpoint 1.6 - OU version", presentation);

    Command nextSlideCommand = new NextSlideCommand(presentation);
    Command prevSlideCommand = new PrevSlideCommand(presentation);
    Command exitCommand = new ExitCommand();
    Command undoCommand = new UndoCommand(presentation, caretaker, frame);
    Command addSlideCommand = new AddSlideCommand(presentation, frame, caretaker);
    Command deleteSlideCommand = new DeleteSlideCommand(presentation, frame, caretaker);

    KeyController keyController = frame.getKeyController();
    keyController.setNextSlideCommand(nextSlideCommand);
    keyController.setPrevSlideCommand(prevSlideCommand);
    keyController.setExitCommand(exitCommand);
    keyController.setUndoCommand(undoCommand);

    MenuController menuController = (MenuController) frame.getMenuBar();
    menuController.setUndoCommand(undoCommand);
    menuController.setAddSlideCommand(addSlideCommand);
    menuController.setDeleteSlideCommand(deleteSlideCommand);

    try {
      if (argv.length == 0) {
        Accessor.getDemoAccessor().loadFile(presentation, "");
      } else {
        new XMLAccessor().loadFile(presentation, argv[0]);
      }
      presentation.setSlideNumber(0);
      frame.repaint();
      caretaker.clearHistory();
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(null, "IO Error: " + ex, "Jabberpoint Error", JOptionPane.ERROR_MESSAGE);
    }
  }
}