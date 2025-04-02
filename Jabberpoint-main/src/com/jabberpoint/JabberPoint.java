package com.jabberpoint;

import com.jabberpoint.factory.Accessor;
import com.jabberpoint.factory.XMLAccessor;
import com.jabberpoint.ui.SlideViewerFrame;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.util.Style;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 * com.jabberpoint.JabberPoint Main Programma
 * <p>This program is distributed under the terms of the accompanying
 * COPYRIGHT.txt file (which is NOT the GNU General Public License). Please read it. Your use of the
 * software constitutes acceptance of the terms in the COPYRIGHT.txt file.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public class JabberPoint {

  protected static final String IOERR = "IO Error: ";
  protected static final String JABERR = "Jabberpoint Error ";
  protected static final String JABVERSION = "Jabberpoint 1.6 - OU version";

  /**
   * Het Main Programma
   */
  public static void main(String argv[]) {

    Style.createStyles();
    Presentation presentation = new Presentation();
    new SlideViewerFrame(JABVERSION, presentation);
    try {
      if (argv.length == 0) { // een demo presentatie
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
