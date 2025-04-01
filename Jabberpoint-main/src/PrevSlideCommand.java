public class PrevSlideCommand implements Command {

  private Presentation presentation;

  public PrevSlideCommand(Presentation presentation) {
    this.presentation = presentation;
  }

  @Override
  public void execute() {
    presentation.prevSlide();
  }
}
