package com.jabberpoint.factory;

import com.jabberpoint.ui.BitmapItem;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.ui.Slide;

/**
 * Een ingebouwde demo-presentatie
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

class DemoPresentation extends Accessor {

  public void loadFile(Presentation presentation, String unusedFilename) {
    presentation.setTitle("Demo com.jabberpoint.util.Presentation");
    Slide slide;
    slide = new Slide();
    slide.setTitle("com.jabberpoint.JabberPoint");
    slide.append(1, "Het Java Presentatie Tool");
    slide.append(2, "Copyright (c) 1996-2000: Ian Darwin");
    slide.append(2, "Copyright (c) 2000-now:");
    slide.append(2, "Gert Florijn en Sylvia Stuurman");
    slide.append(4, "com.jabberpoint.JabberPoint aanroepen zonder bestandsnaam");
    slide.append(4, "laat deze presentatie zien");
    slide.append(1, "Navigeren:");
    slide.append(3, "Volgende slide: PgDn of Enter");
    slide.append(3, "Vorige slide: PgUp of up-arrow");
    slide.append(3, "Stoppen: q or Q");
    presentation.append(slide);

    slide = new Slide();
    slide.setTitle("Demonstratie van levels en stijlen");
    slide.append(1, "Level 1");
    slide.append(2, "Level 2");
    slide.append(1, "Nogmaals level 1");
    slide.append(1, "Level 1 heeft stijl nummer 1");
    slide.append(2, "Level 2 heeft stijl nummer 2");
    slide.append(3, "Zo ziet level 3 er uit");
    slide.append(4, "En dit is level 4");
    presentation.append(slide);
  }

  public void saveFile(Presentation presentation, String unusedFilename) {
    throw new IllegalStateException("Save As->Demo! aangeroepen");
  }
}
