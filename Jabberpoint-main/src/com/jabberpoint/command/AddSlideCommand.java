package com.jabberpoint.command;

import com.jabberpoint.util.Presentation;
import com.jabberpoint.ui.Slide;
import javax.swing.JOptionPane;
import java.awt.Frame;

public class AddSlideCommand implements Command {

  private Presentation presentation;
  private Frame parent;

  public AddSlideCommand(Presentation presentation, Frame parent) {
    this.presentation = presentation;
    this.parent = parent;
  }

  @Override
  public void execute() {
    // Create a new slide
    Slide newSlide = new Slide();

    // Ask for a title for the new slide
    String title = JOptionPane.showInputDialog(parent, "Enter slide title:", "New Slide", JOptionPane.QUESTION_MESSAGE);

    // If user cancels, don't create the slide
    if (title == null) {
      return;
    }

    // Set the title and add the slide to the presentation
    newSlide.setTitle(title);
    presentation.append(newSlide);

    // Navigate to the newly added slide
    presentation.setSlideNumber(presentation.getSize() - 1);
  }
}
