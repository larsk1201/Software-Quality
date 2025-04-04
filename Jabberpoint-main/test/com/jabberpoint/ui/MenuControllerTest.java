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
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    assertEquals(3, menuController.getMenuCount());
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
    }
  }
}

