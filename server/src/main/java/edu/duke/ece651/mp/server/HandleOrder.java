package edu.duke.ece651.mp.server;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

import edu.duke.ece651.mp.common.AttackTurn;
import edu.duke.ece651.mp.common.FoodResourceList;
import edu.duke.ece651.mp.common.Map;
import edu.duke.ece651.mp.common.MapTextView;
import edu.duke.ece651.mp.common.MoveChecking;
import edu.duke.ece651.mp.common.MoveTurn;
import edu.duke.ece651.mp.common.TechResource;
import edu.duke.ece651.mp.common.TechResourceList;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.UpgradeTurn;
import edu.duke.ece651.mp.common.V2Map;

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
    ArrayList<HashMap<String, ArrayList<Turn>>> res = new ArrayList<HashMap<String, ArrayList<Turn>>>();
    // sort attack turns for the same player. & Update the tempMap.
    for (int i = 0; i < valid_attack_order_list.size(); i++) {
      // System.out.println("sort attack orders: ");
      TurnList curr = valid_attack_order_list.get(i);
      HashMap<String, ArrayList<Turn>> temp = new HashMap<String, ArrayList<Turn>>();
      for (int j = 0; j < curr.getListLength(); j++) {
        AttackTurn curr_turn = (AttackTurn) curr.order_list.get(j);
        // Generate/Update the temp map
        String move_unit_type = "ALEVEL"; // HARDCODE FOR BASE VERSION
        int move_units = curr_turn.getNumber();
        int new_units = tempMap.getAllTerritories().get(curr_turn.getSource()).getUnit(move_unit_type) - move_units;
        tempMap.updateTempMap(curr_turn.getSource(), move_unit_type, new_units);
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
      for (ArrayList<Turn> t : hm.values()) {
        handleSingleAttackOrder(t, tempMap);
      }
    }
    /*
     * for (TurnList hm : valid_attack_order_list) {
     * handleSingleAttackOrder(hm.order_list, tempMap);
     * System.out.print("To Do: Handle Single Attack Order"); }
     */

  }

  /**
   * Method to handle Move Order
   * 
   */
  public void handleSingleMoveOrder(MoveTurn moveOrder) {
    String dep = moveOrder.getSource();
    String des = moveOrder.getDestination();
    String player_color = moveOrder.getPlayerColor();
    HashMap<String, Integer> moveUnits = moveOrder.getUnitList();
    // Check Rules - Owner and Path
    String moveProblem = moveChecker.checkMoving(theMap, dep, des, moveUnits, player_color);

    // Check Rule - Food Resource
    // Find the minimum cost path from source to destination
    // the cost of each move is (total size of territories moved through) * (number
    // of units moved).
    int minimumCost = calculateMinimumCostToMove(dep, des, moveUnits);

    // Check if the player has enough food resources to make the move
    int playerFoodResource = tech_list.resource_list.get(player_color).getResourceAmount();
    if (minimumCost > playerFoodResource) {
      moveProblem = "Not enough food resource - atleast " + minimumCost + " required";
    } else { // deduct the player's resource
      food_list.addResource(player_color, -minimumCost);
    }

    String moveResult;

    if (moveProblem == null) {
      // update Territory & Map

      for (String unit_type : moveUnits.keySet()) {
        int unitsToMove = moveUnits.get(unit_type);
        // update source territory
        theMap.updateTerritoryInMap(dep, unit_type, unitsToMove * (-1)); // -1 for taking units out
        // update destination territory
        theMap.updateTerritoryInMap(des, unit_type, unitsToMove); // for adding

      }
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
    attackResult += resolveCombat(attackOrder, tempMap);
    turnStatus.add(attackResult);
  }

  /**
   * Method to resolve combat in any one territory
   */

  // To Do: The Algorithm of combat has to change.
  private String resolveCombat(ArrayList<Turn> attackOrder, Map<T> tempMap) {

    String combatResult;
    int attacking_units = 0;
    String attackerTerritory = "";
    String defenderTerritory = "";
    String player_color = "";

    for (Turn temp : attackOrder) {
      AttackTurn t = (AttackTurn) temp;
      attacking_units += t.getNumber(); // add up units
      attackerTerritory = t.getSource(); // any Source
      defenderTerritory = t.getDestination(); // same Destination
      player_color = t.getPlayerColor();
      // theMap.updateTerritoryInMap(attackerTerritory, (t.getNumber()) * (-1)); //
      // reduce #units in all attackerTerritory
    }

    Territory<T> attacker = tempMap.getAllTerritories().get(attackerTerritory);
    Territory<T> defender = tempMap.getAllTerritories().get(defenderTerritory);
    int defending_units = defender.getUnit("ALEVEL");

    Random attackerDice = new Random();
    Random defenderDice = new Random();
    int diceSides = 20;
    System.out.println("Starting combat...");
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
      }

      else { // (attackerDiceVal > defenderDiceVal) - defender lost
        loserTerr = defenderTerritory;
        defending_units--;
        loserTerrRemainingUnits = defending_units;
      }

      // step-3: check if the loser territory ran out of units
      // if no, continue. If yes, update map with result
      if (loserTerrRemainingUnits > 0) {
        continue;
      } else {
        // attacker territory lost the units no matter what
        // theMap.updateTerritoryInMap(attackerTerritory, (attackOrder.getNumber()) *
        // (-1)); // -1 for making it
        // negative

        int unitChange;
        if (loserTerr == attackerTerritory) {
          // System.out.println("defending_units is: " + defending_units);
          // System.out.println("defenders units is: " + defender.getUnit());
          unitChange = defending_units - defender.getUnit("ALEVEL");
          tempMap.updateTerritoryInMap(defenderTerritory, "ALEVEL", unitChange);
          combatResult = "Defender won!";
        }

        else { // loserTerr == defenderTerritory
               // System.out.println("defending_units is: " + defending_units);
               // System.out.println("defenders units is: " + defender.getUnit());
          unitChange = attacking_units - defender.getUnit("ALEVEL");
          tempMap.updateTerritoryInMap(defenderTerritory, "ALEVEL", unitChange, player_color);
          combatResult = "Attacker won!";
        }
        break;
      }
    }
    // Update True Map
    this.theMap = tempMap;
    return combatResult;

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
    UpgradeChecking<T> upgradeChecker = new UpgradeChecking<>();
    if (upgradeChecker.checkMyRule(theMap, upgradeTurn, tech_list)) {
      // the Upgrade Order is valid.
      // update the tech resources of players
      int upgrade_cost = upgradeChecker.upgrade_cost;
      tech_list.addResource(upgradeTurn.getPlayerColor(), new TechResource(-upgrade_cost));
      String old_type = upgradeTurn.getOldUnitType();
      String new_type = upgradeTurn.getNewUniType();
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

  /**
   * Method to compute all possible paths from the source territory to destination
   * territory using recursion
   * 
   * @param source territory, destination territory, list of all paths (used in
   *               recursion) currPath that's being computed
   * @return ArrayList of all possible path where path is an ArrayList of
   *         territories
   *
   */
  private ArrayList<Deque<Territory<T>>> computeAllPossiblePaths(String source, String destination,
      HashMap<String, Integer> allunits) {
    ArrayList<Deque<Territory<T>>> allPaths = new ArrayList<>();
    // Algorithm used: Depth First Search
    Deque<Territory<T>> stack = new ArrayDeque<>();

    // Keep track of territories visited to avoid loop
    Deque<Territory<T>> visited = new ArrayDeque<>();

    // get the source territory
    Territory<T> start = theMap.getAllTerritories().get(source);
    stack.push(start);
    String colorID = start.getColor();

    // Deque<Territory<T>> res = new ArrayDeque<>();
    // while all the territories are not visited
    while (!stack.isEmpty()) {
      Territory<T> currTerritory = stack.pop();
      // res.add(currTerritory);
      String currname = currTerritory.getName();

      if (currname.equals(destination)) { // found a path
        // add the path to the list
        Deque<Territory<T>> res = new ArrayDeque<Territory<T>>(visited);
        allPaths.add(res);
        continue;
      }

      // if the territory is not visited already
      if (!visited.contains(currTerritory)) {
        visited.push(currTerritory);

        // for each neighbour of this territory
        for (String s : currTerritory.getAdjacency()) {
          Territory<T> thisTerritory = theMap.getAllTerritories().get(s);
          // if the territory is owned by the player
          if (thisTerritory.getColor().equals(colorID)) {
            stack.push(thisTerritory);
          }
        }
      }
    }

    return allPaths;
  }

  /*
   * (String source, String destination, ArrayList<ArrayList<String>> allpaths,
   * ArrayList<String> currpath) {
   * 
   * // create the array if they are null in first iteration if (allpaths == null)
   * { allpaths = new ArrayList<>(); }
   * 
   * if(currpath == null) { currpath = new ArrayList<>(); }
   * 
   * if (source.equals(destination)) { // Base case of the recursive algorithm
   * allpaths.add(currpath); return allpaths; }
   * 
   * Territory<T> currTerritory = theMap.getAllTerritories().get(source);
   * 
   * String color = currTerritory.getColor(); for (String currTerrName :
   * currTerritory.getAdjacency()) { Territory<T> next =
   * theMap.getAllTerritories().get(currTerrName);
   * 
   * // if the adjacent territory is owned by the player if
   * (next.getColor().equals(color)) { // add it to the current path
   * currpath.add(currTerrName);
   * 
   * // now use this territory as the source and recurse allpaths =
   * computeAllPossiblePaths(currTerrName, destination, allpaths, currpath);
   * 
   * // remove the last item from the list i.e. reset // for next iteration
   * currpath.remove(currpath.size() - 1); } } // end of For loop return allpaths;
   * }
   */

  /**
   * Method to caculate the minimum cost to move from source to destination
   * 
   * @param source territory, destination territory, number of units to move
   * @return Integer cost
   */
  private int calculateMinimumCostToMove(String source, String destination, HashMap<String, Integer> moveUnits) {
    int totalUnitsToMove = 0;
    for (HashMap.Entry<String, Integer> unit : moveUnits.entrySet()) {
      totalUnitsToMove += unit.getValue();
    }
    System.out.println("units to move: " + totalUnitsToMove);

    // create an empty path of all pa
    ArrayList<Deque<Territory<T>>> allpaths = computeAllPossiblePaths(source, destination, moveUnits);
    int number = Integer.MAX_VALUE;
    int total_unit = 0;
    // compute the minimal cost for the move
    for (Deque<Territory<T>> currPath : allpaths) {
      total_unit = 0;
      for (Territory<T> terr : currPath) {
        total_unit += terr.getSize();
      }
      number = Math.min(number, total_unit);
    }

    return totalUnitsToMove * number;
  }

  /**
   * Method to printout the path
   */
  private void printPath(Deque<Territory<T>> path) {
    String sep = "";
    for (Territory<T> territory : path) {
      System.out.print(sep + territory.getName());
      sep = "-->";
    }
    System.out.print("\n");
  }

}
