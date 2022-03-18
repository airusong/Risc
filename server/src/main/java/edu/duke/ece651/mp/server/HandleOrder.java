package edu.duke.ece651.mp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.V1Map;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.MoveChecking;
import edu.duke.ece651.mp.common.OwnerChecking;
import edu.duke.ece651.mp.common.PathChecking;
import edu.duke.ece651.mp.common.Territory;

public class HandleOrder<T> {
    public ArrayList<TurnList> all_order_list;
    Map<T> theMap;
    private final MoveChecking<T> moveChecker;

    HandleOrder() {
        this.all_order_list = new ArrayList<TurnList>();
        this.theMap = new V1Map<>();
        this.moveChecker = null;
    }

    HandleOrder(ArrayList<TurnList> all_order_list, Map<T> theMap, MoveChecking<T> moveChecker) {
        this.all_order_list = all_order_list;
        this.theMap = theMap;
        this.moveChecker = moveChecker;
    }

    /**
     * Method to handle All Move Orders
     * 
     */
    public String handleAllMoveOrder() {
        for (int i = 0; i < all_order_list.size(); i++) {
            TurnList curr = all_order_list.get(i);
            for (int j = 0; j < curr.getListLength(); j++) {
                Turn curr_turn = (Turn) (curr.order_list.get(j));
                if (curr_turn.getTurnType().equals("Move")) {
                    // Check Rules
                    String moveProblem = moveChecker.checkMoving(theMap, curr_turn.getSource(),
                            curr_turn.getDestination(), curr_turn.getNumber());
                    if (moveProblem == null) {
                        handleSingleMoveOrder(curr_turn);
                        continue;
                    } else {
                        //System.out.println(moveProblem);
                        return moveProblem;
                    }
                }
            }
        }
        // System.out.println("All valid");
        return null;
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
        int unit_num_dep = ((Territory) theMap.getAllTerritories().get(dep)).getUnit();
        int new_unit_num_dep = unit_num_dep - move_num;
        int unit_num_des = ((Territory) theMap.getAllTerritories().get(des)).getUnit();
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
        // TO DO:
    }

    /**
     * Method to handle Attack Order
     * 
     */
    public void updateMapbyOneUnit() {
        theMap.updateMapbyOneUnit();
    }

    /**
     * Method to handle All kinds of Orders
     * 
     */
    public String handleOrders() {
        String moveProblem = this.handleAllMoveOrder();
        // System.out.println(moveProblem);
        /*
         * if(moveProblem != null){
         * System.out.println("Move Order invalid.");
         * }
         * else{
         * System.out.println("Successfully moved.");
         * }
         */
        handleAllAttackOrder();
        updateMapbyOneUnit();
        return moveProblem;
    }
}
