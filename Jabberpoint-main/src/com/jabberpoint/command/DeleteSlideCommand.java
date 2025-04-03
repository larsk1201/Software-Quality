package com.jabberpoint.command;

import com.jabberpoint.util.Presentation;
import javax.swing.JOptionPane;
import java.awt.Frame;

public class DeleteSlideCommand implements Command {

  private Presentation presentation;
  private Frame parent;

  public DeleteSlideCommand(Presentation presentation, Frame parent) {
    this.presentation = presentation;
    this.parent = parent;
  }

  @Override
  public void execute() {
    // Check if there are any slides to delete
    if (presentation.getSize() <= 0) {
      JOptionPane.showMessageDialog(parent,
          "No slides to delete.",
          "Delete Slide",
          JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    // Ask for confirmation before deleting
    int currentSlide = presentation.getSlideNumber() + 1; // Convert to 1-based for display
    int confirm = JOptionPane.showConfirmDialog(
        parent,
        "Are you sure you want to delete slide " + currentSlide + "?",
        "Delete Slide",
        JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
      // Delete the current slide
      presentation.deleteSlide(presentation.getSlideNumber());
    }
  }
}

