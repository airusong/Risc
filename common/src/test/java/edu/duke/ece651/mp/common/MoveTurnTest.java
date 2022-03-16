package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class MoveTurnTest {
  @Test
  public void test_MoveTurn() throws UnknownHostException, IOException{
        Territory<Character> t1 = new Territory<Character>("Narnia","Green",new ArrayList<String>(),8);
        Territory<Character> t2 = new Territory<Character>("Midemio","Green",new ArrayList<String>(),3);
        MoveTurn<Character> mt = new MoveTurn<Character>(t1, t2, 2, 1);
        assertEquals("Move", mt.type);
        assertEquals(t1, mt.getSource());
        assertEquals(t2, mt.getDestination());
        assertEquals(2, mt.num_unit);
        assertEquals(1, mt.getPlayerID());
    }
}
