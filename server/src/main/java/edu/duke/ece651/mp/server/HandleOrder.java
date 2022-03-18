package edu.duke.ece651.mp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.Territory;

public class HandleOrder<T> {
    public ArrayList<TurnList> all_order_list;
    Map<T> theMap;
    
    HandleOrder(){
        this.all_order_list = new ArrayList<TurnList>();
        this.theMap = null;
    }

    HandleOrder(ArrayList<TurnList> all_order_list, Map<T> theMap){
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
                Turn curr_turn = (Turn) (curr.order_list.get(j));
                if (curr_turn.getTurnType().equals("Move")) {
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
                if (curr_turn.getTurnType().equals("Attack")) {
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
        // TO DO: add RuleChecker
        int move_num = moveOrder.getNumber();
        String dep = moveOrder.getSource();
        String des = moveOrder.getDestination();
        String player_color = moveOrder.getPlayerColor();
        // update Territory & Map
        int unit_num_dep = ((Territory<T>) theMap.getAllTerritories().get(dep)).getUnit();
        int new_unit_num_dep = unit_num_dep - move_num;
        int unit_num_des = ((Territory<T>) theMap.getAllTerritories().get(des)).getUnit();
        int new_unit_num_des = unit_num_des + move_num;
        theMap.updateMap(dep, des, new_unit_num_dep, new_unit_num_des);
    }

    /**
     * Method to handle Attack Order
     * 
     */
    public void handleSingleAttackOrder(Turn attackOrder) {
        // handle attack order
        int attack_num = attackOrder.getNumber();
        String dep = attackOrder.getSource();
        String des = attackOrder.getDestination();
        String player_color = attackOrder.getPlayerColor();

        AttackChecking<T> ruleChecker = new AttackChecking<>();
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
    public void handleOrders(ArrayList<TurnList>all_order_list, Map<T> theMap) {
        this.all_order_list = all_order_list;
        this.theMap = theMap;
        handleAllMoveOrder();
        //handleAllAttackOrder();
        //updateMapbyOneUnit(theMap);
    }
}
