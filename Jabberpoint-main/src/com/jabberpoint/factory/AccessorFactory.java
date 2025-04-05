package com.jabberpoint.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class AccessorFactory {

  private Map<String, Supplier<Accessor>> registry = new HashMap<>();

  public AccessorFactory() {
    registry.put("XML", XMLAccessor::new);
    registry.put("Demo", DemoPresentation::new);
  }

  public void registerAccessor(String type, Supplier<Accessor> supplier) {
    registry.put(type, supplier);
  }

  public Accessor createAccessor(String type) {
    Supplier<Accessor> accessor = registry.get(type);
    if (accessor != null) {
      return accessor.get();
    }
    throw new IllegalArgumentException("Unknown accessor type: " + type);
  }
}