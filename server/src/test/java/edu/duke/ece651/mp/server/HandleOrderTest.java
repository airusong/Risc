package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.mp.common.AttackTurn;
import edu.duke.ece651.mp.common.MoveTurn;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.V1Map;

public class HandleOrderTest {

  @Test
  public void test_handleAllMoveOrder() {
    Map<Character> theMap = new V1Map<Character>();
    assertNotNull(theMap);
    MoveTurn mo = new MoveTurn("Narnia", "Midemio", 1, "Green");
    ArrayList<TurnList> all_order_list = new ArrayList<TurnList>();
    TurnList tl = new TurnList("Green");
    tl.order_list.add(mo);
    all_order_list.add(tl);
    assertNotNull(all_order_list);
    HandleOrder<Character> ho = new HandleOrder<Character>(all_order_list, theMap);
    ho.handleAllMoveOrder();
    assertEquals(7, ((Territory<Character>) ho.theMap.getAllTerritories().get("Narnia")).getUnit());
    assertEquals(4, ((Territory<Character>) ho.theMap.getAllTerritories().get("Midemio")).getUnit());
  }

  @Test
  public void test_handleAllAttackOrder() {
    Territory<Character> dep = new Territory<Character>("Narnia", "Green", new ArrayList<String>(), 2);
    Territory<Character> des = new Territory<Character>("Midemio", "Blue", new ArrayList<String>(), 3);
    AttackTurn mo = new AttackTurn("Narnia", "Midemio", 1, "Green");
    ArrayList<TurnList> all_order_list = new ArrayList<TurnList>();
    TurnList tl = new TurnList("Green");
    tl.order_list.add(mo);
    all_order_list.add(tl);
    ArrayList<String> players_colors = new ArrayList<String>();
    players_colors.add("Green");
    players_colors.add("Blue");
    Map<Character> theMap = new V1Map<Character>(players_colors);

    HandleOrder<Character> ho = new HandleOrder<Character>(all_order_list, theMap);
    ho.handleAllAttackOrder();
    // To add assert

  }

  @Test
  public void test_updateMapbyOneUnit() {
    Map<Character> newMap = new V1Map<Character>();
    ArrayList<TurnList> all_order_list = new ArrayList<TurnList>();

    HandleOrder<Character> ho = new HandleOrder<Character>(all_order_list, newMap);
    ho.updateMapbyOneUnit();
    assertEquals(9, ((Territory<Character>) ho.theMap.getAllTerritories().get("Narnia")).getUnit());
  }

}
