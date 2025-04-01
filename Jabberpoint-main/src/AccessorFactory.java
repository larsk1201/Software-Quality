public class AccessorFactory {

  public Accessor createAccessor(String type) {
    if ("XML".equalsIgnoreCase(type)) {
      return new XMLAccessor();
    } else if ("Demo".equalsIgnoreCase(type)) {
      return new DemoPresentation();
    } else {
      throw new IllegalArgumentException("Unknown accessor type: " + type);
    }
  }
}
