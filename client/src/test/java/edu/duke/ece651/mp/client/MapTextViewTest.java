package edu.duke.ece651.mp.client;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class MapTextViewTest {
  @Test
  public void test_displayEmptyMap() {
    V1Map<Character> m = new V1Map<Character>();
    MapTextView mapView = new MapTextView(m);
    String expected = "Green player:\n"
      + "-----------\n"
      + "Narnia\n"
      + "Midemio\n"
      + "Oz\n"
      + "\n"
      + "Blue player:\n"
      + "-----------\n"
      + "Elantris\n"
      + "Roshar\n"
      + "Scadnal\n";
    assertEquals(expected, mapView.displayMap());
  }
}
