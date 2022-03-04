package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TerritoryTest {
  @Test
  public void test_name() {
    Territory<Character> terr1=new Territory<>("Narnia","Green");
    assertEquals("Narnia",terr1.getName());
    assertEquals("Green",terr1.getColor());
  }

}
