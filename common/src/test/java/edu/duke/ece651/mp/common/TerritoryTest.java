package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class TerritoryTest {
  @Test
  public void test_name() {
    Territory<Character> terr1=new Territory<>("Narnia","Green",new ArrayList<String>());
    assertEquals("Narnia",terr1.getName());
    assertEquals("Green",terr1.getColor());
  }

}
