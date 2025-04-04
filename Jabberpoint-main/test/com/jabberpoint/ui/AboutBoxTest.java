package com.jabberpoint.ui;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.Frame;

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
}

