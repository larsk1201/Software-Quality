package com.jabberpoint.command;

public class ExitCommand implements Command {

  @Override
  public void execute() {
    System.exit(0);
  }
}
