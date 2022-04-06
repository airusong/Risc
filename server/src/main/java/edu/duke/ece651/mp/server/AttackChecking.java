package edu.duke.ece651.mp.server;

import java.util.ArrayList;

import edu.duke.ece651.mp.common.*;

public class AttackChecking<T> {
  String attackStatus;


  public ArrayList<AttackTurn> checkMyRule(Map<T> map, ArrayList<AttackTurn> attackOrder) {
    ArrayList<AttackTurn> ans = new ArrayList<AttackTurn>();
    for (AttackTurn t : attackOrder) {
      boolean b = checkMyRule(map, t);
      if (b) {
        ans.add(t);
      }
    }
    return ans;
  }

  public boolean checkMyRule(Map<T> map, AttackTurn attackOrder) {
    int attackingunits = attackOrder.getNumber();
    String source = attackOrder.getSource();
    String destination = attackOrder.getDestination();
    UnitType unit_type = attackOrder.getUnitType();
    String player_color = attackOrder.getPlayerColor();

    Territory<T> attacker = map.getAllTerritories().get(source);
    Territory<T> defender = map.getAllTerritories().get(destination);

    attackStatus = player_color + ": Attack order from "
        + source + " into " + destination + " with "
        + attackingunits + " units was ";

    // check if the source belongs to the attacker
    if (!attacker.getColor().equals(player_color)) {
      attackStatus += "invalid as the source is not owned by the player";
      return false;
    }

    // check if the attacker and defender are adjacent
    if (!attacker.getAdjacency().contains(destination) || attacker.getColor().equals(defender.getColor())) {
      attackStatus += "invalid as there is no valid path";
      return false;
    }

    // check if the attacker and defender belong to different players
    if (!attacker.getAdjacency().contains(destination) || attacker.getColor().equals(defender.getColor())) {
      attackStatus += "invalid as both territories are owned by same player";
      return false;
    }

    // check if the attcker has enough units
    if (attacker.getUnit(unit_type) < attackingunits) {
      attackStatus += "invalid as attacker doesn't have enough units";
      return false;
    }

    // passed all rules!
    attackStatus += "valid - ";
    return true;
  }
}
