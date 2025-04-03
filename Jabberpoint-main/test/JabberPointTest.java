import com.jabberpoint.JabberPoint;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JabberPointTest {

  @Test
  void testMain() {
    assertDoesNotThrow(() -> JabberPoint.main(new String[]{}));
  }

  @Test
  void testMainWithFile() {
    assertDoesNotThrow(() -> JabberPoint.main(new String[]{"test_presentation.xml"}));
  }
}