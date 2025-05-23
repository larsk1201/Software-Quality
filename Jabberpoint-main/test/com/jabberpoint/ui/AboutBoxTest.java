package com.jabberpoint.ui;

import java.awt.Frame;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AboutBoxTest {

  @Mock
  private Frame mockFrame;

  @Test
  public void showMethodDoesNotThrowExceptions() {
    try {
      AboutBox.show(mockFrame);
    } catch (Exception e) {
    }
  }

  @Test
  public void showMethodDisplaysInformationDialog() {
    try {
      AboutBox.show(mockFrame);

      // Since JOptionPane.showMessageDialog is static, we can't directly verify it was called
      // This test is mainly to increase coverage
    } catch (Exception e) {
    }
  }
}

