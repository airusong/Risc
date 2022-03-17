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
import edu.duke.ece651.mp.common.V1Map;

public class HandleOrderTest {
    
    @Test
    public void test_handleAllMoveOrder(){
        V1Map theMap = new V1Map();
        assertNotNull(theMap);
        MoveTurn mo = new MoveTurn("Narnia", "Midemio", 1, "Green");
        ArrayList<TurnList> all_order_list = new ArrayList<TurnList>();
        TurnList tl = new TurnList("Green");
        tl.order_list.add(mo);
        all_order_list.add(tl);
        assertNotNull(all_order_list);
        HandleOrder ho = new HandleOrder(all_order_list, theMap);
        ho.handleAllMoveOrder();
        assertEquals(7, ((Territory<Character>) ho.theMap.myTerritories.get("Narnia")).getUnit());
        assertEquals(4, ((Territory<Character>) ho.theMap.myTerritories.get("Midemio")).getUnit());
    }
    
    @Test
    public void test_handleAllAttackOrder(){
        Territory<Character> dep = new Territory<Character>("Narnia","Green",new ArrayList<String>(), 2);
        Territory<Character> des = new Territory<Character>("Midemio","Blue", new ArrayList<String>(), 3);
        AttackTurn mo = new AttackTurn("Narnia", "Midemio", 1, "Green");
        ArrayList<TurnList> all_order_list = new ArrayList<TurnList>();
        TurnList tl = new TurnList("Green");
        tl.order_list.add(mo);
        all_order_list.add(tl);
        ArrayList<String> players_colors = new ArrayList<String>();
        players_colors.add("Green");
        players_colors.add("Blue");
        V1Map theMap = new V1Map(players_colors);

        HandleOrder ho = new HandleOrder(all_order_list, theMap);
        ho.handleAllAttackOrder();
        // To add assert
        
    }

    @Test
    public void test_updateMapbyOneUnit(){
        HandleOrder ho = new HandleOrder();
        ho.updateMapbyOneUnit();
        assertEquals(9, ((Territory<Character>) ho.theMap.myTerritories.get("Narnia")).getUnit());
    }

}
