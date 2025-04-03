package com.jabberpoint.ui;

import com.jabberpoint.command.KeyController;
import com.jabberpoint.util.Presentation;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class SlideViewerFrame extends JFrame {

  private static final long serialVersionUID = 3227L;

  private static final String JABTITLE = "Jabberpoint 1.6 - OU";
  public final static int WIDTH = 1200;
  public final static int HEIGHT = 800;
  private KeyController keyController;

  public SlideViewerFrame(String title, Presentation presentation) {
    super(title);
    SlideViewerComponent slideViewerComponent = new SlideViewerComponent(presentation, this);
    presentation.setShowView(slideViewerComponent);
    setupWindow(slideViewerComponent, presentation);
  }

  public void setupWindow(SlideViewerComponent
      slideViewerComponent, Presentation presentation) {
    setTitle(JABTITLE);
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.exit(0);
      }
    });

    getContentPane().add(slideViewerComponent);

    keyController = new KeyController(presentation);
    addKeyListener(keyController);

    setMenuBar(new MenuController(this, presentation));
    setSize(new Dimension(WIDTH, HEIGHT));
    setVisible(true);

    requestFocus();
  }

  public KeyController getKeyController() {
    return keyController;
  }
}

