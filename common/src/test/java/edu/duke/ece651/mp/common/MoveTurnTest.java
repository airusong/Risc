package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.MoveTurn;
import edu.duke.ece651.mp.common.Territory;

public class MoveTurnTest {
    @Test
    public void test_constructor() throws UnknownHostException, IOException{
        Territory<Character> t1 = new Territory<Character>("Narnia","Green",new ArrayList<String>(),8);
        Territory<Character> t2 = new Territory<Character>("Midemio","Green",new ArrayList<String>(),3);
        MoveTurn<Character> mt = new MoveTurn<Character>("move", t1, t2, 2, 1);
        assertEquals("move", mt.type);
        assertEquals(t1, mt.getDep());
        assertEquals(t2, mt.getDes());
        assertEquals(2, mt.num_unit);
        assertEquals(1, mt.getPlayerID());
    }
}
