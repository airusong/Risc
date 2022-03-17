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
    
    HandleOrder(){
        this.all_order_list = new ArrayList<TurnList<Character>>();
        this.theMap = new V1Map();
    }

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
                Turn curr_turn = (Turn) (curr.order_list.get(j));
                System.out.println(curr_turn.type);
                if (curr_turn.getType().equals("move")) {
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
                if (curr_turn.getType().equals("attack")) {
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
        System.out.println(move_num);
        String dep = moveOrder.getDep();
        System.out.println(dep);
        String des = moveOrder.getDes();
        System.out.println(des);
        String player_color = moveOrder.getPlayerColor();
        System.out.println(player_color);
        // update Territory & Map
        int unit_num_dep = ((Territory) theMap.myTerritories.get(dep)).getUnit();
        int new_unit_num_dep = unit_num_dep - move_num;
        System.out.println(new_unit_num_dep);
        int unit_num_des = ((Territory) theMap.myTerritories.get(des)).getUnit();
        int new_unit_num_des = unit_num_des + move_num;
        System.out.println(new_unit_num_des);
        theMap.updateMap(theMap, dep, des, new_unit_num_dep, new_unit_num_des);
    }

    /**
     * Method to handle Attack Order
     * 
     */
    public void handleSingleAttackOrder(Turn attackOrder) {
        // handle attack order
        int attack_num = attackOrder.getNumber();
        String dep = attackOrder.getDep();
        String des = attackOrder.getDes();
        String player_color = attackOrder.getPlayerColor();
        // TO DO: 
    }


    /**
     * Method to handle Attack Order
     * 
     */
    public void updateMapbyOneUnit(V1Map theMap){
        theMap.updateMapbyOneUnit(theMap);
    }

    /**
     * Method to handle All kinds of Orders
     * 
     */
    public void handleOrders(ArrayList<TurnList<Character>>all_order_list, V1Map<Character> theMap) {
        this.all_order_list = all_order_list;
        this.theMap = theMap;
        handleAllMoveOrder();
        //handleAllAttackOrder();
        //updateMapbyOneUnit(theMap);
    }
}
