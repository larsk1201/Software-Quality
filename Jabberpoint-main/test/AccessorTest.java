import com.jabberpoint.factory.Accessor;
import com.jabberpoint.util.Presentation;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class AccessorTest {

  @Test
  void testGetDemoAccessor() {
    Accessor accessor = Accessor.getDemoAccessor();
    assertNotNull(accessor);
  }

  @Test
  void testLoadFile() {
    Accessor accessor = Accessor.getDemoAccessor();
    Presentation presentation = new Presentation();
    assertDoesNotThrow(() -> accessor.loadFile(presentation, ""));
  }

  @Test
  void testSaveFile() {
    Accessor accessor = Accessor.getDemoAccessor();
    Presentation presentation = new Presentation();
    assertDoesNotThrow(() -> accessor.saveFile(presentation, "output_demo.xml"));
  }
}