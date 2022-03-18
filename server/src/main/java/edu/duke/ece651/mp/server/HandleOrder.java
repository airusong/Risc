package edu.duke.ece651.mp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.Territory;

public class HandleOrder<T> {
  public ArrayList<TurnList> all_order_list;
  Map<T> theMap;
  ArrayList<String> turnStatus;

  HandleOrder() {
    this.all_order_list = new ArrayList<TurnList>();
    this.theMap = null;
    this.turnStatus = new ArrayList<>();
  }

  HandleOrder(ArrayList<TurnList> all_order_list, Map<T> theMap) {
    this.all_order_list = all_order_list;
    this.theMap = theMap;
    this.turnStatus = new ArrayList<>();
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

    AttackChecking<T> ruleChecker = new AttackChecking<>();
    if (ruleChecker.checkMyRule(theMap, attackOrder)) { // rule is VALID
      resolveCombat(attackOrder);
    }
    turnStatus.add(ruleChecker.attackStatus);
  }

  /**
   * Method to resolve combat in any one territory
   */
  private void resolveCombat(Turn attackOrder) {
    int attacking_units = attackOrder.getNumber();
    String attackerTerritory = attackOrder.getSource();
    String defenderTerritory = attackOrder.getDestination();
    String player_color = attackOrder.getPlayerColor();

    Territory<T> attacker = theMap.getAllTerritories().get(attackerTerritory);
    Territory<T> defender = theMap.getAllTerritories().get(defenderTerritory);
    int defending_units = defender.getUnit();

    Random attackerDice = new Random();
    Random defenderDice = new Random();
    int diceSides = 20;
    while (true) { // start combat

      // step-1: role a 20 sided twice for both players
      int attackerDiceVal = attackerDice.nextInt(diceSides);
      int defenderDiceVal = defenderDice.nextInt(diceSides);

      // step-2: compare dice values. Lower loses 1 unit
      String loserTerr;
      int loserTerrRemainingUnits;
      if (attackerDiceVal <= defenderDiceVal) { // attacker lost (in a tie, defender wins)
        loserTerr = attackerTerritory;
        attacking_units--;
        loserTerrRemainingUnits = attacking_units;
      } else { // (attackerDiceVal > defenderDiceVal) - defender lost
        loserTerr = defenderTerritory;
        defending_units--;
        loserTerrRemainingUnits = defending_units;
      }

      // step-3: check if the loser territory ran out of units
      // if no, continue. If yes, update map with result
      if(loserTerrRemainingUnits > 0) {
        continue;
      }
      else {
        // attacker territory lost the units no matter what
        theMap.updateTerritoryInMap(attackerTerritory, attacking_units*(-1)); // -1 for making it negative

        int unitChange;
        if(loserTerr == attackerTerritory) {
          unitChange = defending_units - defender.getUnit();
          theMap.updateTerritoryInMap(defenderTerritory, unitChange);
        }
        
        else { // loserTerr == defenderTerritory
          unitChange = attacking_units - defender.getUnit();
          theMap.updateTerritoryInMap(defenderTerritory, unitChange, player_color);          
        }
        
        break;
      }
    }

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
  public Map<T> handleOrders(ArrayList<TurnList> all_order_list, Map<T> theMap) {
    this.all_order_list = all_order_list;
    this.theMap = theMap;
    handleAllMoveOrder();
    // handleAllAttackOrder();
    // updateMapbyOneUnit(theMap);

    // NEED TO RETURN UPDATED MAP
    return theMap;
  }
}
