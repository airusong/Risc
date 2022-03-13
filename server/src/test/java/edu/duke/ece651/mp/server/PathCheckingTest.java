package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.V1Map;
import edu.duke.ece651.mp.common.Territory;
public class PathCheckingTest {
  @Test
  public void test_path() {
    ArrayList<String> players_colors = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    V1Map<Character> map = new V1Map<>(players_colors);
    PathChecking<Character> check=new PathChecking<>(null); 
    assertEquals(check.checkMyRule(map,"Narnia","Midemio",2), null);
  }
  @Test
  public void test_chain(){
    OwnerChecking<Character> ocheck=new OwnerChecking<>(null);
    MoveChecking<Character> mcheck=new PathChecking<>(ocheck);
    ArrayList<String> players_colors = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    V1Map<Character> map = new V1Map<>(players_colors);
    assertEquals(mcheck.checkMyRule(map,"Narnia","Midemio",2),null);
  }

}
