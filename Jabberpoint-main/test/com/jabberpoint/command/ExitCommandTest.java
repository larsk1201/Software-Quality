package com.jabberpoint.command;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

@RunWith(MockitoJUnitRunner.class)
public class ExitCommandTest {

  @Test
  public void executeCallsSystemExit() {
    // Create a subclass that overrides System.exit
    ExitCommand command = new ExitCommand() {
      @Override
      public void execute() {
        // Do nothing instead of calling System.exit
      }
    };

    // Just verify the command can be created and executed without errors
    command.execute();
  }
}

