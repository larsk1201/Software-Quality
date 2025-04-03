package com.jabberpoint;

import com.jabberpoint.command.*;
import com.jabberpoint.factory.Accessor;
import com.jabberpoint.factory.XMLAccessor;
import com.jabberpoint.memento.PresentationCaretaker;
import com.jabberpoint.ui.MenuController;
import com.jabberpoint.ui.SlideViewerFrame;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import java.io.IOException;
import javax.swing.JOptionPane;

public class JabberPoint {

  protected static final String IOERR = "IO Error: ";
  protected static final String JABERR = "Jabberpoint Error ";
  protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";

  public static void main(String argv[]) {

    Style.createStyles();
    Presentation presentation = new Presentation();
    PresentationCaretaker caretaker = new PresentationCaretaker();
    SlideViewerFrame frame = new SlideViewerFrame(JABVERSION, presentation);

    // Initialize commands
    Command nextSlideCommand = new NextSlideCommand(presentation);
    Command prevSlideCommand = new PrevSlideCommand(presentation);
    Command exitCommand = new ExitCommand();
    Command undoCommand = new UndoCommand(caretaker, presentation);

    // Set commands in KeyController
    KeyController keyController = new KeyController(presentation);
    keyController.setNextSlideCommand(nextSlideCommand);
    keyController.setPrevSlideCommand(prevSlideCommand);
    keyController.setExitCommand(exitCommand);
    keyController.setUndoCommand(undoCommand);

    // Set commands in MenuController
    MenuController menuController = new MenuController(frame, presentation);
    menuController.setUndoCommand(undoCommand);

    frame.addKeyListener(keyController);
    frame.setMenuBar(menuController);

    try {
      if (argv.length == 0) { // a demo presentation
        Accessor.getDemoAccessor().loadFile(presentation, "");
      } else {
        new XMLAccessor().loadFile(presentation, argv[0]);
      }
      presentation.setSlideNumber(0);
    } catch (IOException ex) {
      JOptionPane.showMessageDialog(null,
          IOERR + ex, JABERR,
          JOptionPane.ERROR_MESSAGE);
    }
  }
}