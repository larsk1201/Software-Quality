package com.jabberpoint;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import com.jabberpoint.factory.Accessor;
import com.jabberpoint.util.Style;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DirectJabberPointTest {

  @Before
  @Before
  public void setUp() {
    System.setProperty("java.awt.headless", "true");
    System.setProperty("testfx.robot", "glass");
    System.setProperty("testfx.headless", "true");
    System.setProperty("prism.order", "sw");
    System.setProperty("prism.text", "t2k");
  }

  @After
  public void tearDown() {
    System.clearProperty("java.awt.headless");
  }

  @Test
  public void testMainMethodExecutesWithoutArguments() {
    try {
      JabberPoint.main(new String[]{});
      assertTrue(true);
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void testMainMethodWithInvalidFile() {
    try {
      JabberPoint.main(new String[]{"nonexistent.xml"});
      assertTrue(true);
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