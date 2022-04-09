package edu.duke.ece651.mp.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import edu.duke.ece651.mp.common.*;

public class HandleOrder<T> {
    public ArrayList<TurnList> all_order_list;
    Map<T> theMap;
    private final MoveChecking<T> moveChecker;
    ArrayList<String> turnStatus;
    private FoodResourceList food_list;
    private TechResourceList tech_list;
    public ArrayList<String> players_identity;

    HandleOrder() {
        this.all_order_list = new ArrayList<TurnList>();
        this.theMap = new V2Map<>();
        this.moveChecker = null;
        this.turnStatus = new ArrayList<>();
        this.players_identity = new ArrayList<String>();
        this.food_list = new FoodResourceList(players_identity);
        this.tech_list = new TechResourceList(players_identity);
    }

    HandleOrder(ArrayList<TurnList> all_order_list, Map<T> theMap, MoveChecking<T> moveChecker,
            ArrayList<String> players_identity, FoodResourceList food_list, TechResourceList tech_list) {
        this.all_order_list = all_order_list;
        this.theMap = theMap;
        MapTextView test = new MapTextView((V2Map) theMap);
        test.displayMap();
        this.moveChecker = moveChecker;
        this.turnStatus = new ArrayList<>();
        this.players_identity = players_identity;
        this.food_list = food_list;
        this.tech_list = tech_list;
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

        Map<T> tempMap = theMap; // validationMap is used for validate AttackTurn

        ArrayList<TurnList> valid_attack_order_list = new ArrayList<TurnList>();

        AttackChecking<T> ruleChecker = new AttackChecking<>();

        // filter out valid attack orders from all player and updated the tempMap used
        // by HandleSingleAttackOrder
        for (int i = 0; i < all_order_list.size(); i++) {
            TurnList curr = all_order_list.get(i);
            TurnList curr_valid = new TurnList(curr.player_info);
            for (int j = 0; j < curr.getListLength(); j++) {
                Turn curr_turn = curr.order_list.get(j);
                if (curr_turn.getTurnType().equals("Attack")) {
                    if (ruleChecker.checkMyRule(tempMap, (AttackTurn) curr_turn)) {
                        curr_valid.order_list.add(curr_turn);
                        // update the ValidationMap
                        //int move_unit = curr_turn.getNumber(curr_turn.getTurnType());
                        //int new_unit = tempMap.getAllTerritories().get(((AttackTurn) curr_turn).getSource())
                        //.getUnit(curr_turn.getTurnType())
                        //       - move_unit;
                        tempMap.updateTempMap(((AttackTurn) curr_turn).getSource(), (AttackTurn) curr_turn);
                    }
                    turnStatus.add(ruleChecker.attackStatus);
                }
            }
            valid_attack_order_list.add(curr_valid);
        }

        ArrayList<HashMap<String, ArrayList<Turn>>> res = new ArrayList<HashMap<String, ArrayList<Turn>>>();
        // sort attack turns for the same player.
        for (int i = 0; i < valid_attack_order_list.size(); i++) {
            // System.out.println("sort attack orders: ");
            TurnList curr = valid_attack_order_list.get(i);
            HashMap<String, ArrayList<Turn>> temp = new HashMap<String, ArrayList<Turn>>();
            for (int j = 0; j < curr.getListLength(); j++) {
                AttackTurn curr_turn = (AttackTurn) curr.order_list.get(j);

                // Group the AttackTurns which attack the same Territory together
                String des = curr_turn.getDestination();
                if (temp.get(des) != null) {
                    temp.get(des).add(curr_turn);
                } else {
                    ArrayList<Turn> newTurnList = new ArrayList<Turn>();
                    newTurnList.add(curr_turn);
                    temp.put(des, newTurnList);
                }
            }
            res.add(temp);
        }

        for (HashMap<String, ArrayList<Turn>> hm : res) {
            // Same Player & Same Territory
            for (ArrayList<Turn> t : hm.values()) {
                handleSingleAttackOrder(t, tempMap);
            }
        }
    }

    /**
     * Method to handle Move Order
     * 
     */
    public void handleSingleMoveOrder(MoveTurn moveOrder) {
        String dep = moveOrder.getSource();
        String des = moveOrder.getDestination();
        String player_color = moveOrder.getPlayerColor();

        // int unitsToMove = moveOrder.getNumber();

        // A move order can have different unit types to move
        // So we'll go over the entire hashmap
        for (String unit_type : moveOrder.getUnitList().keySet()) {
            int unitsToMove = moveOrder.getNumber(unit_type);
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
        } // end of for-loop
    }

    /**
     * Method to handle Attack Order
     * 
     */

    public void handleSingleAttackOrder(ArrayList<Turn> attackOrder, Map<T> tempMap) {
        String attackResult = "";
        attackResult += resolveCombat(attackOrder, tempMap);
        turnStatus.add(attackResult);
    }
    /**
     *
     * @param attackOrder
     * @param tempMap
     * @return
     */
    private String resolveCombat(ArrayList<Turn> attackOrder, Map<T> tempMap) {

        String combatResult;
        int attacking_units = 0;
        String attackerTerritory = "";
        String defenderTerritory = "";
        String player_color = "";
        HashMap<String,Integer> attacking_map=new HashMap<>();//key:type value: unit number
        //loop through all the attackorder in the arraylist
        for (Turn temp : attackOrder) {
            AttackTurn t = (AttackTurn) temp;
            //attacking_units += t.getNumber(); // add up units
            int total_attackingunits=0;
            for (String s : t.getUnitList().keySet()) {
                if (!attacking_map.containsKey(s)) {
                    attacking_map.put(s, t.getUnitList().get(s));
                } else {
                    attacking_map.replace(s, attacking_map.get(s), attacking_map.get(s) + t.getUnitList().get(s));
                }
                total_attackingunits+=t.getUnitList().get(s).intValue();
            }
            attackerTerritory = t.getSource(); // any Source
            defenderTerritory = t.getDestination(); // same Destination
            player_color = t.getPlayerColor();
            // reduce food resources in all attackerTerritory
            Territory<T> attacker=theMap.getAllTerritories().get(attackerTerritory);
            attacker.updateResource(new FoodResource(total_attackingunits));
        }
        // update the attacking_list for attacking from the attacking map
        ArrayList<Unit> attacking_list=new ArrayList<>();
        for(String s:attacking_map.keySet()){
            if(attacking_map.get(s).intValue()!=0) {
                attacking_list.add(new Unit(s, attacking_map.get(s).intValue()));
            }
        }
        Collections.sort(attacking_list,(o1, o2) -> o1.getBonus() - o2.getBonus());


        Territory<T> attacker = tempMap.getAllTerritories().get(attackerTerritory);
        Territory<T> defender = tempMap.getAllTerritories().get(defenderTerritory);
        //int defending_units = defender.getUnit("ALEVEL");
        //list of different type of unit for the defender
        ArrayList<Unit> defending_copy_list = defender.getUnitList();
        ArrayList<Unit> defending_list=new ArrayList<>();
        for(Unit u:defending_copy_list){
            if(u.getUnitNum()!=0) {
                defending_list.add(new Unit(u.getUnitType(), u.getUnitNum()));
            }
        }
        Collections.sort(defending_list,(o1, o2) -> o1.getBonus() - o2.getBonus());
        Random attackerDice = new Random();
        Random defenderDice = new Random();
        int diceSides = 20;
        System.out.println("Starting combat...");
        // start combat
        //define a count the total loop times
        int count=0;
        while (true) {

            // step-1: role a 20 sided twice for both players
            int attackerDiceVal = attackerDice.nextInt(diceSides);
            int defenderDiceVal = defenderDice.nextInt(diceSides);

            //step-3: detect the result, if one side lose, update the map
            //if no side has lost, continue the loop
            String loserTerr;
            if (defender_result(defending_list)) {
                //clear all the units in temp map and add the attacker's attacking units take hold
                ArrayList<String> origin_list = tempMap.getTerritoryUnitType(defenderTerritory);
                for (String s : origin_list) {
                    tempMap.updateTerritoryInMap(defenderTerritory, s, -defender.getUnit(s), defender.getColor());
                }
                for (Unit u : attacking_list) {
                    String unit_type = u.getUnitType();
                    defender.updateUnit(unit_type, 0);
                    tempMap.updateTerritoryInMap(defenderTerritory, u.getUnitType(), u.getUnitNum(), attacker.getColor());
                }
                loserTerr = defenderTerritory;
                combatResult = "Attacker won!";
                break;
            } else if (attacker_result(attacking_list)) {
                for (Unit u : defending_list) {
                    String unit_type = u.getUnitType();
                    tempMap.updateTerritoryInMap(defenderTerritory, u.getUnitType(), u.getUnitNum() - defender.getUnit(unit_type), defender.getColor());
                }
                loserTerr = attackerTerritory;
                combatResult = "Defender won!";
                break;
            }
            //else {
            //    continue;
            //}


            //step-2: compare dice values. Lower loses 1 unit
            //if: attacker wins
            //else: defender wins
            if (count % 2 == 0) {
                if (attackerDiceVal + attacking_list.get(attacking_list.size() - 1).getBonus() > defenderDiceVal + defending_list.get(0).getBonus()) {
                    int origin_unit = defending_list.get(0).getUnitNum();
                    int curr_unit = origin_unit - 1;
                    defending_list.get(0).updateUnit(curr_unit);
                    if (curr_unit == 0) {
                        defending_list.remove(0);
                    }
                } else {
                    int origin_unit = attacking_list.get(attacking_list.size() - 1).getUnitNum();
                    int curr_unit = origin_unit - 1;
                    attacking_list.get(attacking_list.size() - 1).updateUnit(curr_unit);
                    if (curr_unit == 0) {
                        attacking_list.remove(attacking_list.size() - 1);
                    }
                }
            } else {//attacker wins
                if (attackerDiceVal + attacking_list.get(0).getBonus() > defenderDiceVal + defending_list.get(defending_list.size() - 1).getBonus()) {
                    int origin_unit = defending_list.get(defending_list.size() - 1).getUnitNum();
                    int curr_unit = origin_unit - 1;
                    defending_list.get(defending_list.size() - 1).updateUnit(curr_unit);
                    if (curr_unit == 0) {
                        defending_list.remove(defending_list.size() - 1);
                    }
                } else {//defender wins
                    int origin_unit = attacking_list.get(0).getUnitNum();
                    int curr_unit = origin_unit - 1;
                    attacking_list.get(0).updateUnit(curr_unit);
                    if (curr_unit == 0) {
                        attacking_list.remove(attacking_list.size() - 1);
                    }
                }
            }
            count++;
        }
            //step-3: detect the result, if one side lose, update the map
            //if no side has lost, continue the loop
//            String loserTerr;
//            if (defender_result(defending_list)) {
//                //clear all the units in temp map and add the attacker's attacking units take hold
//                ArrayList<String> origin_list=tempMap.getTerritoryUnitType(defenderTerritory);
//                for(String s:origin_list){
//                        tempMap.updateTerritoryInMap(defenderTerritory, s, -defender.getUnit(s), defender.getColor());
//                }
//                for (Unit u : attacking_list) {
//                    String unit_type=u.getUnitType();
//                    defender.updateUnit(unit_type,0);
//                    tempMap.updateTerritoryInMap(defenderTerritory, u.getUnitType(), u.getUnitNum(), attacker.getColor());
//                }
//                loserTerr = defenderTerritory;
//                combatResult = "Attacker won!";
//                break;
//            } else if (attacker_result(attacking_list)) {
//                for (Unit u : defending_list) {
//                    String unit_type=u.getUnitType();
//                    tempMap.updateTerritoryInMap(defenderTerritory, u.getUnitType(), u.getUnitNum()-defender.getUnit(unit_type), defender.getColor());
//                }
//                loserTerr = attackerTerritory;
//                combatResult = "Defender won!";
//                break;
//            } else {
//                continue;
//            }
//
//
//        }
        //update true map
        this.theMap = tempMap;
        return combatResult;
    }

    /**  function to see the remain of the defender
     *
     *
     * @param defender_list
     * @return true when defender lose all its units
     */
    private boolean defender_result(ArrayList<Unit> defender_list){
        for(Unit u:defender_list){
            if(u.getUnitNum()!=0){
                return false;
            }
        }
        return true;
    }

    /** function to see the remain of the attacker
     *
     *
     * @param attacking
     * @return true when attacker lose all its units
     */
    private boolean attacker_result(ArrayList<Unit> attacking){
        for(Unit u:attacking){
            if(u.getUnitNum()>0){
                return false;
            }
        }
        return true;
    }


    /**
     * Method to handle All Upgrade Orders
     * 
     */
    public void handleAllUpgradeOrder() {
        for (int i = 0; i < all_order_list.size(); i++) {
            TurnList curr = all_order_list.get(i);
            for (int j = 0; j < curr.getListLength(); j++) {
                Turn curr_turn = curr.order_list.get(j);
                if (curr_turn.getTurnType().equals("Upgrade")) {
                    // System.out.println("Try to handle upgrade order");
                    handleSingleUpgradeOrder((UpgradeTurn) curr_turn);
                }
            }
        }
    }

    public void handleSingleUpgradeOrder(UpgradeTurn upgradeTurn) {
        UpgradeChecking upgradeChecker = new UpgradeChecking<>();
        if (upgradeChecker.checkMyRule(theMap, upgradeTurn, tech_list)) {
            // the Upgrade Order is valid.
            // update the tech resources of players
            int upgrade_cost = upgradeChecker.upgrade_cost;
            tech_list.addResource(upgradeTurn.getPlayerColor(), new TechResource(-upgrade_cost));
            String old_type = upgradeTurn.getOldUnitType();
            String new_type = upgradeTurn.getNewUnitType();
            int unitChange = upgradeTurn.getNumber();
            theMap.updateMapForUpgrade(upgradeTurn.getFromTerritory(), old_type, new_type, unitChange);
        }
        turnStatus.add(upgradeChecker.upgradeStatus);
    }

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
        handleAllUpgradeOrder();

        // NEED TO RETURN UPDATED MAP
        return theMap;
    }

    public FoodResourceList getFoodList() {
        return this.food_list;
    }

    public TechResourceList getTechList() {
        return this.tech_list;
    }
}
