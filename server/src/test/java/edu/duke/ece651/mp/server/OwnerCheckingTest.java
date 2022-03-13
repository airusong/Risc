package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.V1Map;

public class OwnerCheckingTest {
  @Test
  public void test_owner() {
    ArrayList<String> players_colors = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    V1Map<Character> map = new V1Map<>(players_colors);
    OwnerChecking<Character> check=new OwnerChecking<>(null);
    assertEquals(check.checkMyRule(map,"Narnia","Midemio",2),null);
    assertEquals(check.checkMyRule(map,"Narnia","Midemio",10),"Insuffcient units");
    //assertEquals(check.checkMyRule(map,"Narnia","Elantris"),"not same owner");
    
  }
  @Test
  public void test_chain(){
    PathChecking<Character> pcheck=new PathChecking<>(null);
    MoveChecking<Character> mcheck=new OwnerChecking<>(pcheck);
    ArrayList<String> players_colors = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    V1Map<Character> map = new V1Map<>(players_colors);
    assertEquals(mcheck.checkMyRule(map,"Narnia","Midemio",3),null);
  }
}
