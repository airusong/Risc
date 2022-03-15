package edu.duke.ece651.mp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.V1Map;
import edu.duke.ece651.mp.common.Territory;

public class HandleOrder {
    public ArrayList<TurnList<Character>> all_order_list;
    V1Map theMap;
    
    HandleOrder(ArrayList<TurnList<Character>> all_order_list, V1Map theMap){
        this.all_order_list = all_order_list;
        this.theMap = theMap;
        
    }
    /**
     * Method to handle All Move Orders
     * 
     */
    public void handleAllMoveOrder() {
        for (int i = 0; i < all_order_list.size(); i++) {
            TurnList curr = all_order_list.get(i);
            for (int j = 0; j < curr.getListLength(); j++) {
                Turn curr_turn = (Turn) curr.order_list.get(j);
                if (curr_turn.type == "move") {
                    handleSingleMoveOrder(curr_turn);
                }
            }
        }
    }

    /**
     * Method to handle All Attack Orders
     * 
     */
    public void handleAllAttackOrder() {
        for (int i = 0; i < all_order_list.size(); i++) {
            TurnList curr = all_order_list.get(i);
            for (int j = 0; j < curr.getListLength(); j++) {
                Turn curr_turn = (Turn) curr.order_list.get(j);
                if (curr_turn.type == "attack") {
                    handleSingleAttackOrder(curr_turn);
                }
            }
        }
    }

    /**
     * Method to handle Move Order
     * 
     */
    public void handleSingleMoveOrder(Turn moveOrder) {
        // how to handle move action??
        // TO DO: add RuleChecker
        int move_num = moveOrder.getNumber();
        Territory dep = moveOrder.getDep();
        Territory des = moveOrder.getDes();
        int player_id = moveOrder.getPlayerID();

        // update Territory & Map
        int unit_num_dep = ((Territory) theMap.myTerritories.get(dep.getName())).getUnit();
        int new_unit_num_dep = unit_num_dep - move_num;

        int unit_num_des = ((Territory) theMap.myTerritories.get(des.getName())).getUnit();
        int new_unit_num_des = unit_num_des + move_num;

        theMap.updateMap(dep.getName(), des.getName(), new_unit_num_dep, new_unit_num_des);
    }

    /**
     * Method to handle Attack Order
     * 
     */
    public void handleSingleAttackOrder(Turn attackOrder) {
        // handle attack order
        int attack_num = attackOrder.getNumber();
        Territory dep = attackOrder.getDep();
        Territory des = attackOrder.getDes();
        int player_id = attackOrder.getPlayerID();
    }


    /**
     * Method to handle Attack Order
     * 
     */
    public void updateMapbyOneUnit(){
        theMap.updateMapbyOneUnit();
    }

    /**
     * Method to handle All kinds of Orders
     * 
     */
    public void handleOrders() {
        handleAllMoveOrder();
        handleAllAttackOrder();
        updateMapbyOneUnit();
    }
}
