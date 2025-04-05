package com.jabberpoint;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import com.jabberpoint.util.Style;
import com.jabberpoint.util.Presentation;
import com.jabberpoint.factory.Accessor;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class DirectJabberPointTest {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;

  @Before
  public void setUpStreams() {
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
  }

  @Test
  public void testMainMethodExecutesWithoutArguments() {
    try {
      // Call the main method directly
      JabberPoint.main(new String[]{});
      // If we get here, the test passes
      assertTrue(true);
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void testMainMethodWithInvalidFile() {
    try {
      // Call the main method with a non-existent file
      JabberPoint.main(new String[]{"nonexistent.xml"});
      // If we get here, the test passes
      assertTrue(true);
    } catch (Exception e) {
      fail("Exception should not be thrown: " + e.getMessage());
    }
  }

  @Test
  public void testStyleCreation() {
    // This directly tests a method called by JabberPoint.main()
    Style.createStyles();
    assertNotNull(Style.getStyle(0));
    assertNotNull(Style.getStyle(1));
  }

  @Test
  public void testDemoAccessorCreation() {
    // This directly tests a method called by JabberPoint.main()
    Accessor accessor = Accessor.getDemoAccessor();
    assertNotNull(accessor);
  }
}

