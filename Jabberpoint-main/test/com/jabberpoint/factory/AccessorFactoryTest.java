package com.jabberpoint.factory;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.function.Supplier;

@RunWith(MockitoJUnitRunner.class)
public class AccessorFactoryTest {

  @Test
  public void createAccessorWithXMLReturnsXMLAccessor() {
    AccessorFactory factory = new AccessorFactory();
    Accessor accessor = factory.createAccessor("XML");
    assertTrue(accessor instanceof XMLAccessor);
  }

  @Test
  public void createAccessorWithDemoReturnsDemoPresentation() {
    AccessorFactory factory = new AccessorFactory();
    Accessor accessor = factory.createAccessor("Demo");
    assertTrue(accessor instanceof DemoPresentation);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createAccessorWithInvalidTypeThrowsIllegalArgumentException() {
    AccessorFactory factory = new AccessorFactory();
    factory.createAccessor("Invalid");
  }

  @Test
  public void registerAccessorAddsNewAccessorType() {
    AccessorFactory factory = new AccessorFactory();

    factory.registerAccessor("Custom", () -> new XMLAccessor());

    Accessor accessor = factory.createAccessor("Custom");
    assertTrue(accessor instanceof XMLAccessor);
  }

  @Test
  public void registerAccessorOverridesExistingType() {
    AccessorFactory factory = new AccessorFactory();

    factory.registerAccessor("XML", DemoPresentation::new);

    Accessor accessor = factory.createAccessor("XML");
    assertTrue(accessor instanceof DemoPresentation);
  }

  @Test
  public void constructorInitializesDefaultAccessors() {
    AccessorFactory factory = new AccessorFactory();

    assertTrue(factory.createAccessor("XML") instanceof XMLAccessor);
    assertTrue(factory.createAccessor("Demo") instanceof DemoPresentation);
  }
}

