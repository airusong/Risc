package edu.duke.ece651.mp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import edu.duke.ece651.mp.common.*;

public class HandleOrder<T> {
    public ArrayList<TurnList> all_order_list;
    Map<T> theMap;
    private final MoveChecking<T> moveChecker;
    ArrayList<String> turnStatus;

    HandleOrder() {
        this.all_order_list = new ArrayList<TurnList>();
        this.theMap = new V2Map<>();
        this.moveChecker = null;
        this.turnStatus = new ArrayList<>();
    }

    HandleOrder(ArrayList<TurnList> all_order_list, Map<T> theMap, MoveChecking<T> moveChecker) {
        this.all_order_list = all_order_list;
        this.theMap = theMap;
        MapTextView test = new MapTextView((V2Map) theMap);
        test.displayMap();

        this.moveChecker = moveChecker;
        this.turnStatus = new ArrayList<>();
    }

    /**
     * Method to handle All Move Orders
     * 
     */
    public void handleAllMoveOrder() {
        for (int i = 0; i < all_order_list.size(); i++) {
            TurnList curr = all_order_list.get(i);
            System.out.println("The length of the TurnList is:" + curr.getListLength());
            for (int j = 0; j < curr.getListLength(); j++) {
                Turn curr_turn = curr.order_list.get(j);
                if (curr_turn.getTurnType().equals("Move")) {
                    handleSingleMoveOrder((MoveTurn) curr_turn);
                }
            }
        }
    }

    /**
     * Method to handle All Attack Orders
     * 
     */
    public void handleAllAttackOrder() {
        // create a temporary map with correct attacker and defender unit number
        // (requirement 5d)

        Map<T> tempMap = theMap;

        ArrayList<TurnList> attack_order_list;
        TurnList attack_list;
        ArrayList<TurnList> valid_attack_order_list = new ArrayList<TurnList>();

        AttackChecking<T> ruleChecker = new AttackChecking<>();

        // filter out valid attack orders from all player
        for (int i = 0; i < all_order_list.size(); i++) {
            TurnList curr = all_order_list.get(i);
            TurnList curr_valid = new TurnList(curr.player_info);
            for (int j = 0; j < curr.getListLength(); j++) {
                Turn curr_turn = curr.order_list.get(j);
                if (curr_turn.getTurnType().equals("Attack")) {
                    if (ruleChecker.checkMyRule(theMap, (AttackTurn) curr_turn)) {
                        curr_valid.order_list.add(curr_turn);
                    }
                    turnStatus.add(ruleChecker.attackStatus);
                }
            }
            valid_attack_order_list.add(curr_valid);
        }
        // update valid attack order lists
        attack_order_list = valid_attack_order_list;

        // Q - Seems the valid_attack_order_list already sorted
        // ArrayList<HashMap<String, ArrayList<Turn>>> res = new
        // ArrayList<HashMap<String, ArrayList<Turn>>>();

        // sort attack turns for the same player. & Update the tempMap.
        for (int i = 0; i < valid_attack_order_list.size(); i++) {
            // System.out.println("sort attack orders: ");
            TurnList curr = valid_attack_order_list.get(i);
            // HashMap<String, ArrayList<Turn>> temp = new HashMap<String,
            // ArrayList<Turn>>();
            for (int j = 0; j < curr.getListLength(); j++) {
                AttackTurn curr_turn = (AttackTurn) curr.order_list.get(j);
                // Generate/Update the temp map
                String move_unit_type = curr_turn.getUnitType();
                int move_units = curr_turn.getNumber();
                int new_units = tempMap.getAllTerritories().get(curr_turn.getSource()).getUnit(move_unit_type)
                        - move_units;
                tempMap.updateTempMap(curr_turn.getSource(), move_unit_type, new_units);
                /*
                 * String des = curr_turn.getDestination();
                 * if (temp.get(des) != null) {
                 * temp.get(des).add(curr_turn);
                 * }
                 * else{
                 * ArrayList<Turn> newTurnList = new ArrayList<Turn>();
                 * newTurnList.add(curr_turn);
                 * temp.put(des, newTurnList);
                 * }
                 */
            }
            // res.add(temp);
        }
        /*
         * for(HashMap<String, ArrayList<Turn>> hm: res){
         * for (ArrayList<Turn> t : hm.values()) {
         * handleSingleAttackOrder(t, tempMap);
         * System.out.print("To Do: Handle Single Attack Order");
         * }
         * }
         */
        for (TurnList hm : valid_attack_order_list) {
            handleSingleAttackOrder(hm.order_list, tempMap);
            System.out.print("To Do: Handle Single Attack Order");
        }

    }

    /**
     * Method to handle Move Order
     * 
     */
    public void handleSingleMoveOrder(MoveTurn moveOrder) {
        int unitsToMove = moveOrder.getNumber();
        String dep = moveOrder.getSource();
        String des = moveOrder.getDestination();
        String unit_type = moveOrder.getUnitType();
        String player_color = moveOrder.getPlayerColor();

        // Check Rules
        String moveProblem = moveChecker.checkMoving(theMap, dep, des, unit_type, unitsToMove, player_color);
        String moveResult;

        if (moveProblem == null) {
            // update Territory & Map
            // update source territory
            theMap.updateTerritoryInMap(dep, unit_type, unitsToMove * (-1)); // -1 for taking units out
            // update destination territory
            theMap.updateTerritoryInMap(des, unit_type, unitsToMove); // for adding

            moveResult = "successful";
        } else {
            moveResult = "invalid (Reason: " + moveProblem + ")";
        }
        turnStatus.add(moveChecker.moveStatus + moveResult);
    }

    /**
     * Method to handle Attack Order
     * 
     */

    public void handleSingleAttackOrder(ArrayList<Turn> attackOrder, Map<T> tempMap) {
        String attackResult = "";
        //attackResult += resolveCombat(attackOrder, tempMap);
        turnStatus.add(attackResult);
    }

    /**
     * Method to resolve combat in any one territory
     */

    // To Do: The Algorithm of combat has to change.

    /*
     * private String resolveCombat(ArrayList<Turn> attackOrder, Map<T> tempMap) {
     * 
     * String combatResult;
     * int attacking_units = 0;
     * String attackerTerritory = "";
     * String defenderTerritory = "";
     * String player_color = "";
     * 
     * for(Turn t: attackOrder){
     * attacking_units += t.getNumber(); // add up units
     * attackerTerritory = t.getSource(); // any Source
     * defenderTerritory = t.getDestination(); // same Destination
     * player_color = t.getPlayerColor();
     * //theMap.updateTerritoryInMap(attackerTerritory, (t.getNumber()) * (-1)); //
     * reduce #units in all attackerTerritory
     * }
     * 
     * Territory<T> attacker = tempMap.getAllTerritories().get(attackerTerritory);
     * Territory<T> defender = tempMap.getAllTerritories().get(defenderTerritory);
     * int defending_units = defender.getUnit();
     * 
     * Random attackerDice = new Random();
     * Random defenderDice = new Random();
     * int diceSides = 20;
     * System.out.println("Starting combat...");
     * while (true) { // start combat
     * 
     * // step-1: role a 20 sided twice for both players
     * int attackerDiceVal = attackerDice.nextInt(diceSides);
     * int defenderDiceVal = defenderDice.nextInt(diceSides);
     * 
     * // step-2: compare dice values. Lower loses 1 unit
     * String loserTerr;
     * int loserTerrRemainingUnits;
     * if (attackerDiceVal <= defenderDiceVal) { // attacker lost (in a tie,
     * defender wins)
     * loserTerr = attackerTerritory;
     * attacking_units--;
     * loserTerrRemainingUnits = attacking_units;
     * }
     * 
     * else { // (attackerDiceVal > defenderDiceVal) - defender lost
     * loserTerr = defenderTerritory;
     * defending_units--;
     * loserTerrRemainingUnits = defending_units;
     * }
     * 
     * // step-3: check if the loser territory ran out of units
     * // if no, continue. If yes, update map with result
     * if (loserTerrRemainingUnits > 0) {
     * continue;
     * } else {
     * // attacker territory lost the units no matter what
     * // theMap.updateTerritoryInMap(attackerTerritory, (attackOrder.getNumber()) *
     * (-1)); // -1 for making it
     * // negative
     * 
     * int unitChange;
     * if (loserTerr == attackerTerritory) {
     * //System.out.println("defending_units is: " + defending_units);
     * //System.out.println("defenders units is: " + defender.getUnit());
     * unitChange = defending_units - defender.getUnit();
     * tempMap.updateTerritoryInMap(defenderTerritory, unitChange);
     * combatResult = "Defender won!";
     * }
     * 
     * else { // loserTerr == defenderTerritory
     * //System.out.println("defending_units is: " + defending_units);
     * //System.out.println("defenders units is: " + defender.getUnit());
     * unitChange = attacking_units - defender.getUnit();
     * tempMap.updateTerritoryInMap(defenderTerritory, unitChange, player_color);
     * combatResult = "Attacker won!";
     * }
     * break;
     * }
     * }
     * // Update True Map
     * this.theMap = tempMap;
     * return combatResult;
     * 
     * }
     */

    /**
     * Method to decrease one unit from each territory Used after each turn
     * 
     */
    public void updateMapbyOneUnit() {
        theMap.updateMapbyOneUnit();
    }

    /**
     * Method to handle All kinds of Orders
     * 
     */
    public Map<T> handleOrders(ArrayList<TurnList> all_order_list, Map<T> theMap) {
        this.all_order_list = all_order_list;
        this.theMap = theMap;
        handleAllMoveOrder();
        handleAllAttackOrder();

        // NEED TO RETURN UPDATED MAP
        return theMap;
    }

}
