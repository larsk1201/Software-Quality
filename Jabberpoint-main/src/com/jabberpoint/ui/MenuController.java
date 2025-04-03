package com.jabberpoint.ui;

import com.jabberpoint.factory.Accessor;
import com.jabberpoint.factory.XMLAccessor;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.command.Command;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * <p>De controller voor het menu</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */
public class MenuController extends MenuBar {

  private Frame parent; // het frame, alleen gebruikt als ouder voor de Dialogs
  private Presentation presentation; // Er worden commando's gegeven aan de presentatie
  private Command undoCommand;

  private static final long serialVersionUID = 227L;

  protected static final String ABOUT = "About";
  protected static final String FILE = "File";
  protected static final String EXIT = "Exit";
  protected static final String GOTO = "Go to";
  protected static final String HELP = "Help";
  protected static final String NEW = "New";
  protected static final String NEXT = "Next";
  protected static final String LOAD = "Load";
  protected static final String PAGENR = "Page number?";
  protected static final String PREV = "Prev";
  protected static final String SAVE = "Save";
  protected static final String VIEW = "View";
  protected static final String UNDO = "Undo";

  protected static final String TESTFILE = "test.xml";
  protected static final String SAVEFILE = "dump.xml";

  protected static final String IOEX = "IO Exception: ";
  protected static final String LOADERR = "Load Error";
  protected static final String SAVEERR = "Save Error";

  public MenuController(Frame frame, Presentation pres) {
    parent = frame;
    presentation = pres;
    MenuItem menuItem;
    Menu fileMenu = new Menu(FILE);
    fileMenu.add(menuItem = mkMenuItem(LOAD)); // Renamed from OPEN to LOAD
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        FileDialog fileDialog = new FileDialog(parent, "Load File", FileDialog.LOAD); // Updated dialog title
        fileDialog.setVisible(true);
        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        if (directory != null && file != null) {
          presentation.clear();
          Accessor xmlAccessor = new XMLAccessor();
          try {
            xmlAccessor.loadFile(presentation, directory + file);
            presentation.setSlideNumber(0);
          } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, IOEX + exc, LOADERR, JOptionPane.ERROR_MESSAGE);
          }
          parent.repaint();
        }
      }
    });
    fileMenu.add(menuItem = mkMenuItem(NEW));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        presentation.clear();
        parent.repaint();
      }
    });
    fileMenu.add(menuItem = mkMenuItem(SAVE));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        FileDialog fileDialog = new FileDialog(parent, "Save File", FileDialog.SAVE);
        fileDialog.setVisible(true);
        String directory = fileDialog.getDirectory();
        String file = fileDialog.getFile();
        if (directory != null && file != null) {
          Accessor xmlAccessor = new XMLAccessor();
          try {
            xmlAccessor.saveFile(presentation, directory + file);
          } catch (IOException exc) {
            JOptionPane.showMessageDialog(parent, IOEX + exc, SAVEERR, JOptionPane.ERROR_MESSAGE);
          }
        }
      }
    });
    fileMenu.addSeparator();
    fileMenu.add(menuItem = mkMenuItem(EXIT));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        presentation.exit(0);
      }
    });
    add(fileMenu);
    Menu viewMenu = new Menu(VIEW);
    viewMenu.add(menuItem = mkMenuItem(NEXT));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        presentation.nextSlide();
      }
    });
    viewMenu.add(menuItem = mkMenuItem(PREV));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        presentation.prevSlide();
      }
    });
    viewMenu.add(menuItem = mkMenuItem(GOTO));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        String pageNumberStr = JOptionPane.showInputDialog((Object) PAGENR);
        int pageNumber = Integer.parseInt(pageNumberStr);
        presentation.setSlideNumber(pageNumber - 1);
      }
    });
    add(viewMenu);
    Menu helpMenu = new Menu(HELP);
    helpMenu.add(menuItem = mkMenuItem(ABOUT));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        AboutBox.show(parent);
      }
    });
    setHelpMenu(helpMenu);    // nodig for portability (Motif, etc.).

    Menu editMenu = new Menu("Edit");
    editMenu.add(menuItem = mkMenuItem(UNDO));
    menuItem.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent actionEvent) {
        if (undoCommand != null) {
          undoCommand.execute();
        }
      }
    });
    add(editMenu);
  }

  // een menu-item aanmaken
  public MenuItem mkMenuItem(String name) {
    return new MenuItem(name, new MenuShortcut(name.charAt(0)));
  }

  public void setUndoCommand(Command undoCommand) {
    this.undoCommand = undoCommand;
  }
}