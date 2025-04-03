import com.jabberpoint.factory.XMLAccessor;
import com.jabberpoint.util.Presentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class XMLAccessorTest {

  private XMLAccessor xmlAccessor;
  private Presentation presentation;

  @BeforeEach
  void setUp() {
    xmlAccessor = new XMLAccessor();
    presentation = new Presentation();
  }

  @Test
  void testLoadFile() {
    assertDoesNotThrow(() -> xmlAccessor.loadFile(presentation, "test_presentation.xml"));
    assertEquals("Test Presentation", presentation.getTitle());
  }

  @Test
  void testSaveFile() {
    presentation.setTitle("Test Presentation");
    assertDoesNotThrow(() -> xmlAccessor.saveFile(presentation, "output_presentation.xml"));
  }
}