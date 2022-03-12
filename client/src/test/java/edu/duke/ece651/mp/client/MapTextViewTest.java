package edu.duke.ece651.mp.client;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.V1Map;

public class MapTextViewTest {
  @Test
  public void test_displayMap() {
    ArrayList<String> players_colors = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    V1Map<Character> m = new V1Map<Character>(players_colors);
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
