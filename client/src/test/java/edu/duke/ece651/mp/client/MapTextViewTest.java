package edu.duke.ece651.mp.client;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MapTextViewTest {
  @Test
  public void test_displayMap() {
    V1Map<Character> m = new V1Map<Character>();
    MapTextView mapView = new MapTextView(m);
    String expected = "Green player:\n"
      + "-----------\n"
      + "Narnia (next to: Midemio, Elantris)\n"
      + "Midemio (next to: Narnia, Oz)\n"
      + "Oz (next to: Midemio, Roshar)\n"
      + "\n"
      + "Blue player:\n"
      + "-----------\n"
      + "Elantris (next to: Scadnal, Narnia)\n"
      + "Roshar (next to: Oz, Scadnal)\n"
      + "Scadnal (next to: Roshar, Elantris)\n";
    assertEquals(expected, mapView.displayMap());
  }
}
