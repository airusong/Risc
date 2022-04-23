package edu.duke.ece651.mp.common;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TerritoryTest {
  @Test
  public void test_name() {

    Territory<Character> terr1=new Territory<Character>("Narnia","Green",new ArrayList<String>(), 2,3);

    assertEquals("Narnia",terr1.getName());
  }

}
