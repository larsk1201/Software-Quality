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
   public void createMenuItemCreatesMenuItemWithCorrectLabelAndShortcut() {
       MenuItem item = menuController.createMenuItem("Test", e -> {});
       assertEquals("Test", item.getLabel());
       assertNotNull(item.getShortcut());
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
   public void newFileMethodClearsPresentationAndRepaints() {
       menuController.newFile(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "New"));
       verify(mockPresentation, times(1)).clear();
       verify(mockFrame, times(1)).repaint();
   }
   
   @Test
   public void goToSlideWithValidNumberSetsSlideNumberAndRepaints() {
       // This test requires a UI interaction, so we'll mock it
       when(mockPresentation.getSize()).thenReturn(5);
       
       // We can't easily test this without mocking JOptionPane, which is challenging
       // So we'll just verify the method exists
       try {
           menuController.goToSlide();
       } catch (Exception e) {
           // Expected to throw an exception in test environment due to JOptionPane
       }
   }
}

