package com.jabberpoint.factory;

import com.jabberpoint.util.Presentation;
import java.io.IOException;

/**
 * <p>Een com.jabberpoint.factory.Accessor maakt het mogelijk om gegevens voor een presentatie
 * te lezen of te schrijven.</p>
 * <p>Niet-abstracte subklassen moeten de load en de save methode implementeren.</p>
 *
 * @author Ian F. Darwin, ian@darwinsys.com, Gert Florijn, Sylvia Stuurman
 * @version 1.6 2014/05/16 Sylvia Stuurman
 */

public abstract class Accessor {

  public static final String DEMO_NAME = "Demonstratie presentatie";
  public static final String DEFAULT_EXTENSION = ".xml";

  public static Accessor getDemoAccessor() {
    return new DemoPresentation();
  }

  public Accessor() {
  }

  abstract public void loadFile(Presentation presentation, String filename) throws IOException;

  abstract public void saveFile(Presentation presentation, String filename) throws IOException;

}
