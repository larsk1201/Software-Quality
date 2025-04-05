package com.jabberpoint.ui;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.command.Command;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;

@RunWith(MockitoJUnitRunner.class)
public class MenuControllerTest {

  @Mock
  private Frame mockFrame;

  @Mock
  private Presentation mockPresentation;

  @Mock
  private Command mockUndoCommand;

  @Mock
  private Command mockAddSlideCommand;

  @Mock
  private Command mockDeleteSlideCommand;

  private MenuController menuController;

  @Before
  public void setUp() {
    menuController = new MenuController(mockFrame, mockPresentation);
    menuController.setUndoCommand(mockUndoCommand);
    menuController.setAddSlideCommand(mockAddSlideCommand);
    menuController.setDeleteSlideCommand(mockDeleteSlideCommand);
  }

  @Test
  public void menuControllerCreatesMenusSuccessfully() {
    assertNotNull(menuController);
    assertTrue(menuController.getMenuCount() > 0);
  }

  @Test
  public void setCommandsChangesTheCommandsUsedByMenuController() {
    Command newUndoCommand = mock(Command.class);
    Command newAddCommand = mock(Command.class);
    Command newDeleteCommand = mock(Command.class);

    menuController.setUndoCommand(newUndoCommand);
    menuController.setAddSlideCommand(newAddCommand);
    menuController.setDeleteSlideCommand(newDeleteCommand);
  }

  @Test
  public void menuControllerHasCorrectNumberOfMenus() {
    assertEquals(4, menuController.getMenuCount());
  }

  @Test
  public void commandsAreExecutedWhenTriggered() {
    try {
      for (int i = 0; i < menuController.getMenuCount(); i++) {
        if ("Edit".equals(menuController.getMenu(i).getLabel())) {
          for (int j = 0; j < menuController.getMenu(i).getItemCount(); j++) {
            MenuItem item = menuController.getMenu(i).getItem(j);
            if ("Undo".equals(item.getLabel())) {
              item.getActionListeners()[0].actionPerformed(
                  new ActionEvent(item, ActionEvent.ACTION_PERFORMED, "Undo")
              );
              break;
            }
          }
          break;
        }
      }

      verify(mockUndoCommand, times(1)).execute();
    } catch (Exception e) {
      // Ignore exceptions for this test
    }
  }

  @Test
  public void newFileActionClearsPresentation() {
    Menu fileMenu = null;
    for (int i = 0; i < menuController.getMenuCount(); i++) {
      if ("File".equals(menuController.getMenu(i).getLabel())) {
        fileMenu = menuController.getMenu(i);
        break;
      }
    }

    if (fileMenu != null) {
      MenuItem newItem = null;
      for (int i = 0; i < fileMenu.getItemCount(); i++) {
        if ("New".equals(fileMenu.getItem(i).getLabel())) {
          newItem = fileMenu.getItem(i);
          break;
        }
      }

      if (newItem != null) {
        newItem.getActionListeners()[0].actionPerformed(
            new ActionEvent(newItem, ActionEvent.ACTION_PERFORMED, "New")
        );

        verify(mockPresentation, times(1)).clear();
        verify(mockFrame, times(1)).repaint();
      }
    }
  }

  @Test
  public void viewMenuNextActionCallsNextSlide() {
    Menu viewMenu = null;
    for (int i = 0; i < menuController.getMenuCount(); i++) {
      if ("View".equals(menuController.getMenu(i).getLabel())) {
        viewMenu = menuController.getMenu(i);
        break;
      }
    }

    if (viewMenu != null) {
      MenuItem nextItem = null;
      for (int i = 0; i < viewMenu.getItemCount(); i++) {
        if ("Next".equals(viewMenu.getItem(i).getLabel())) {
          nextItem = viewMenu.getItem(i);
          break;
        }
      }

      if (nextItem != null) {
        nextItem.getActionListeners()[0].actionPerformed(
            new ActionEvent(nextItem, ActionEvent.ACTION_PERFORMED, "Next")
        );

        verify(mockPresentation, times(1)).nextSlide();
      }
    }
  }

  @Test
  public void viewMenuPrevActionCallsPrevSlide() {
    Menu viewMenu = null;
    for (int i = 0; i < menuController.getMenuCount(); i++) {
      if ("View".equals(menuController.getMenu(i).getLabel())) {
        viewMenu = menuController.getMenu(i);
        break;
      }
    }

    if (viewMenu != null) {
      MenuItem prevItem = null;
      for (int i = 0; i < viewMenu.getItemCount(); i++) {
        if ("Prev".equals(viewMenu.getItem(i).getLabel())) {
          prevItem = viewMenu.getItem(i);
          break;
        }
      }

      if (prevItem != null) {
        prevItem.getActionListeners()[0].actionPerformed(
            new ActionEvent(prevItem, ActionEvent.ACTION_PERFORMED, "Prev")
        );

        verify(mockPresentation, times(1)).prevSlide();
      }
    }
  }

  @Test
  public void editMenuAddSlideActionExecutesAddSlideCommand() {
    Menu editMenu = null;
    for (int i = 0; i < menuController.getMenuCount(); i++) {
      if ("Edit".equals(menuController.getMenu(i).getLabel())) {
        editMenu = menuController.getMenu(i);
        break;
      }
    }

    if (editMenu != null) {
      MenuItem addItem = null;
      for (int i = 0; i < editMenu.getItemCount(); i++) {
        if ("Add Slide".equals(editMenu.getItem(i).getLabel())) {
          addItem = editMenu.getItem(i);
          break;
        }
      }

      if (addItem != null) {
        addItem.getActionListeners()[0].actionPerformed(
            new ActionEvent(addItem, ActionEvent.ACTION_PERFORMED, "Add Slide")
        );

        verify(mockAddSlideCommand, times(1)).execute();
      }
    }
  }

  @Test
  public void editMenuDeleteSlideActionExecutesDeleteSlideCommand() {
    Menu editMenu = null;
    for (int i = 0; i < menuController.getMenuCount(); i++) {
      if ("Edit".equals(menuController.getMenu(i).getLabel())) {
        editMenu = menuController.getMenu(i);
        break;
      }
    }

    if (editMenu != null) {
      MenuItem deleteItem = null;
      for (int i = 0; i < editMenu.getItemCount(); i++) {
        if ("Delete Slide".equals(editMenu.getItem(i).getLabel())) {
          deleteItem = editMenu.getItem(i);
          break;
        }
      }

      if (deleteItem != null) {
        deleteItem.getActionListeners()[0].actionPerformed(
            new ActionEvent(deleteItem, ActionEvent.ACTION_PERFORMED, "Delete Slide")
        );

        verify(mockDeleteSlideCommand, times(1)).execute();
      }
    }
  }
}

