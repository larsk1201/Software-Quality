package com.jabberpoint;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import com.jabberpoint.factory.Accessor;
import com.jabberpoint.util.Style;
import org.junit.Before;
import org.junit.Test;

public class DirectJabberPointTest {

  @Before
  public void setUp() {
    System.setProperty("java.awt.headless", "true");
  }

  @Test
  public void testMainMethodExecutesWithoutArguments() {
    try {
      JabberPoint.initializeApplication(new String[]{}, false);
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void testMainMethodWithInvalidFile() {
    try {
      JabberPoint.initializeApplication(new String[]{"nonexistent.xml"}, false);
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void testStyleCreation() {
    Style.createStyles();
    assertNotNull(Style.getStyle(0));
    assertNotNull(Style.getStyle(1));
  }

  @Test
  public void testDemoAccessorCreation() {
    Accessor accessor = Accessor.getDemoAccessor();
    assertNotNull(accessor);
  }
}
