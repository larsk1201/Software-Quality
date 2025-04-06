# Jabberpoint

Jabberpoint is a lightweight presentation tool modeled with clean architecture and refactored to follow SOLID design principles.

## Latest Release

You can find the latest release in the [Releases](https://github.com/larsk1201/Software-Quality/releases) tab of this repository.

## Design Patterns

As part of the refactoring process, three key design patterns were introduced to improve the application's flexibility and maintainability:

- **Factory Method Pattern**: Used in the `AccessorFactory` class to create appropriate `Accessor` instances without modifying client code.
- **Memento Pattern**: Implemented via the `PresentationCaretaker` and `Memento` classes to enable undo functionality by capturing and restoring presentation states.
- **Command Pattern**: All user actions (such as adding, deleting, or navigating slides) are encapsulated in individual command classes implementing a common `Command` interface.

These patterns help promote clean separation of concerns and make the system easier to extend and test.

## SOLID Principles

This application has been refactored to follow the SOLID principles of object-oriented design, which help improve modularity, scalability, and maintainability.

### Single Responsibility Principle (SRP)
Each class has a single, well-defined responsibility:

- **Commands**: `AddSlideCommand`, `DeleteSlideCommand`, `NextSlideCommand`, `PrevSlideCommand`, `ExitCommand`, and `UndoCommand` each encapsulate a specific user action.
- **Core Logic**: `Presentation` manages presentation data; `Slide` represents an individual slide.
- **Persistence**: `XMLAccessor` and `DemoPresentation` handle loading and saving presentations.
- **User Interaction**: `MenuController` and `KeyController` manage user input.

### Open/Closed Principle (OCP)
The system is open for extension but closed for modification:

- New commands can be added by implementing the `Command` interface without altering existing command logic.
- The `AccessorFactory` allows for plug-and-play accessors without modifying the factory logic.

### Liskov Substitution Principle (LSP)
Subclasses can stand in for their base classes:

- All command classes implement the `Command` interface and can be used interchangeably.
- `XMLAccessor` and `DemoPresentation` extend the `Accessor` abstract class, ensuring consistent behavior.

### Interface Segregation Principle (ISP)
Interfaces are kept lean and focused:

- The `Command` interface only defines the `execute()` method, avoiding unnecessary complexity for implementers.

### Dependency Inversion Principle (DIP)
High-level modules rely on abstractions, not concrete implementations:

- `KeyController` and `MenuController` use the `Command` interface, not specific command classes.
- `PresentationCaretaker` depends on the `Memento` abstraction rather than concrete presentation states.
