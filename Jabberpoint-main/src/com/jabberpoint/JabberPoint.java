package com.jabberpoint;

import com.jabberpoint.command.*;
import com.jabberpoint.factory.Accessor;
import com.jabberpoint.factory.XMLAccessor;
import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.ui.MenuController;
import com.jabberpoint.ui.SlideViewerFrame;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import javax.swing.JOptionPane;
import java.io.IOException;

public class JabberPoint {

  public static void main(String[] argv) {
    initializeApplication(argv, true);
  }

  public static void initializeApplication(String[] argv, boolean launchGUI) {
    Style.createStyles();
    Presentation presentation = new Presentation();
    PresentationCaretaker caretaker = new PresentationCaretaker();
    SlideViewerFrame frame = null;

    if (launchGUI) {
      frame = new SlideViewerFrame("Jabberpoint 1.6 - OU version", presentation);
    }

    Command nextSlideCommand = new NextSlideCommand(presentation);
    Command prevSlideCommand = new PrevSlideCommand(presentation);
    Command exitCommand = new ExitCommand();
    Command undoCommand = new UndoCommand(presentation, caretaker, frame);
    Command addSlideCommand = new AddSlideCommand(presentation, frame, caretaker);
    Command deleteSlideCommand = new DeleteSlideCommand(presentation, frame, caretaker);

    if (launchGUI && frame != null) {
      KeyController keyController = frame.getKeyController();
      keyController.setNextSlideCommand(nextSlideCommand);
      keyController.setPrevSlideCommand(prevSlideCommand);
      keyController.setExitCommand(exitCommand);
      keyController.setUndoCommand(undoCommand);

      MenuController menuController = (MenuController) frame.getMenuBar();
      menuController.setUndoCommand(undoCommand);
      menuController.setAddSlideCommand(addSlideCommand);
      menuController.setDeleteSlideCommand(deleteSlideCommand);
    }

    try {
      if (argv.length == 0) {
        Accessor.getDemoAccessor().loadFile(presentation, "");
      } else {
        new XMLAccessor().loadFile(presentation, argv[0]);
      }

      if (presentation.getSize() > 0) {
        presentation.setSlideNumber(0);
      }

      if (launchGUI && frame != null) {
        frame.repaint();
      }
      caretaker.clearHistory();
    } catch (IOException ex) {
      if (launchGUI) {
        JOptionPane.showMessageDialog(null, "IO Error: " + ex, "Jabberpoint Error", JOptionPane.ERROR_MESSAGE);
      } else {
        System.err.println("IO Error: " + ex);
      }
    }
  }
}
