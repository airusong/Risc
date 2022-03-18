package edu.duke.ece651.mp.server;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.V1Map;
import edu.duke.ece651.mp.common.Territory;
public class AttackCheckingTest {
  @Test
  public void test_attackingcheck() {
    ArrayList<String> players_colors = new ArrayList<String>(Arrays.asList("Green", "Blue"));
    V1Map<Character> map = new V1Map<>(players_colors);
    AttackChecking<Character> check=new AttackChecking<>();
    assertEquals(check.checkMyRule(map,"Narnia","Midemio",2), false);
    assertEquals(check.checkMyRule(map,"Narnia","Elantris",10), false);
    assertEquals(check.checkMyRule(map,"Narnia","Scadnal",10), false);
    assertEquals(check.checkMyRule(map,"Narnia","Elantris",5), true);
  }

}
