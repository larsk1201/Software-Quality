package com.jabberpoint.ui;

import com.jabberpoint.command.Command;
import com.jabberpoint.factory.XMLAccessor;
import com.jabberpoint.util.Presentation;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

public class MenuController extends MenuBar {
  private Frame parent;
  private Presentation presentation;
  private Command addSlideCommand;
  private Command deleteSlideCommand;
  private Command undoCommand;

  public MenuController(Frame frame, Presentation pres) {
    this.parent = frame;
    this.presentation = pres;
    setupFileMenu();
    setupViewMenu();
    setupEditMenu();
    setupHelpMenu();
  }

  private void setupFileMenu() {
    Menu fileMenu = new Menu("File");
    fileMenu.add(createMenuItem("Load", this::loadFile));
    fileMenu.add(createMenuItem("New", this::newFile));
    fileMenu.add(createMenuItem("Save", this::saveFile));
    fileMenu.addSeparator();
    fileMenu.add(createMenuItem("Exit", e -> System.exit(0)));
    add(fileMenu);
  }

  private void setupViewMenu() {
    Menu viewMenu = new Menu("View");
    viewMenu.add(createMenuItem("Next", e -> presentation.nextSlide()));
    viewMenu.add(createMenuItem("Prev", e -> presentation.prevSlide()));
    viewMenu.add(createMenuItem("Go to", e -> goToSlide()));
    add(viewMenu);
  }

  private void setupEditMenu() {
    Menu editMenu = new Menu("Edit");
    editMenu.add(createMenuItem("Add Slide", e -> addSlideCommand.execute()));
    editMenu.add(createMenuItem("Delete Slide", e -> deleteSlideCommand.execute()));
    editMenu.add(createMenuItem("Undo", e -> undoCommand.execute()));
    add(editMenu);
  }

  private void setupHelpMenu() {
    Menu helpMenu = new Menu("Help");
    helpMenu.add(createMenuItem("About", e -> AboutBox.show(parent)));
    setHelpMenu(helpMenu);
  }

  private MenuItem createMenuItem(String name, ActionListener action) {
    MenuItem menuItem = new MenuItem(name, new MenuShortcut(name.charAt(0)));
    menuItem.addActionListener(action);
    return menuItem;
  }

  private void loadFile(ActionEvent e) {
    FileDialog fileDialog = new FileDialog(parent, "Load File", FileDialog.LOAD);
    fileDialog.setVisible(true);
    String fileName = fileDialog.getFile();
    if (fileName != null) {
      try {
        // The XMLAccessor will now clear the presentation before loading
        new XMLAccessor().loadFile(presentation, fileDialog.getDirectory() + fileName);
        if (presentation.getSize() > 0) {
          presentation.setSlideNumber(0);
        }
        parent.repaint();
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(parent,
            "IO Error: " + ex.getMessage(),
            "Load Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
      }
    }
  }

  private void newFile(ActionEvent e) {
    presentation.clear();
    parent.repaint();
  }

  private void saveFile(ActionEvent e) {
    FileDialog fileDialog = new FileDialog(parent, "Save File", FileDialog.SAVE);
    fileDialog.setVisible(true);
    String fileName = fileDialog.getFile();
    if (fileName != null) {
      try {
        new XMLAccessor().saveFile(presentation, fileDialog.getDirectory() + fileName);
      } catch (IOException ex) {
        JOptionPane.showMessageDialog(parent,
            "IO Error: " + ex.getMessage(),
            "Save Error",
            JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace();
      }
    }
  }

  private void goToSlide() {
    String input = JOptionPane.showInputDialog(parent, "Enter slide number:");
    if (input != null) {
      try {
        int slideNumber = Integer.parseInt(input);
        if (slideNumber > 0 && slideNumber <= presentation.getSize()) {
          presentation.setSlideNumber(slideNumber - 1); // Assuming slide numbers are 1-based
          parent.repaint();
        } else {
          JOptionPane.showMessageDialog(parent,
              "Invalid slide number. Please enter a number between 1 and " + presentation.getSize(),
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(parent, "Invalid slide number", "Error", JOptionPane.ERROR_MESSAGE);
      }
    }
  }

  public void setAddSlideCommand(Command addSlideCommand) {
    this.addSlideCommand = addSlideCommand;
  }

  public void setDeleteSlideCommand(Command deleteSlideCommand) {
    this.deleteSlideCommand = deleteSlideCommand;
  }

  public void setUndoCommand(Command undoCommand) {
    this.undoCommand = undoCommand;
  }
}

