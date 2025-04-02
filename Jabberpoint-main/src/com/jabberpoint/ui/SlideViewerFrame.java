package com.jabberpoint.ui;

import com.jabberpoint.command.KeyController;
import com.jabberpoint.util.Presentation;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

/**
 * <p>Het applicatiewindow voor een slideviewcomponent</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class SlideViewerFrame extends JFrame {

  private static final long serialVersionUID = 3227L;

  private static final String JABTITLE = "Jabberpoint 1.6 - OU";
  public final static int WIDTH = 1200;
  public final static int HEIGHT = 800;

  public SlideViewerFrame(String title, Presentation presentation) {
    super(title);
    SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
    presentation.setShowView(slideViewerComponent);
    setupWindow(slideViewerComponent, presentation);
  }

  // De GUI opzetten
  public void setupWindow(SlideViewerComponent
      slideViewerComponent, Presentation presentation) {
    setTitle(JABTITLE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });
    getContentPane().add(slideViewerComponent);
    addKeyListener(new KeyController(presentation)); // een controller toevoegen
    setMenuBar(new MenuController(this, presentation));  // nog een controller toevoegen
    setSize(new Dimension(WIDTH, HEIGHT)); // Dezelfde maten als com.jabberpoint.ui.Slide hanteert.
    setVisible(true);
  }
}
