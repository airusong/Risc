package edu.duke.ece651.mp.common;

import java.util.HashMap;

public class OwnerChecking<T> extends MoveChecking<T> {

  public OwnerChecking(MoveChecking<T> next) {
    super(next);
  };

  /**
   * Method to check rule for all unit types
   */
  public String checkMyRule(Map<T> map, String source, String destination, HashMap<String, Integer> allUnits,String player_color) {
    Territory<T> s = map.getAllTerritories().get(source);
    Territory<T> d = map.getAllTerritories().get(destination);
    //check if allunits contains nothing
    if(allUnits.isEmpty()){
      return null;
    }
    // first check if both units are owned by the player, except the spy unit
    if(!allUnits.containsKey("SPY")) {
      if (!s.getColor().equals(d.getColor())&&!s.getColor().equals(player_color)) {
        return "not same owner";
      } else if (!hasEnoughUnits(s, allUnits)) {
        return "Insuffcient units";
      } else {
        return null;
      }
    }else{
      //check the spy move
      if (!hasEnoughUnits(s, allUnits)){
        return "Insuffcient units";
      }else if(!s.getColor().equals(player_color)&&allUnits.get("SPY")>1){
        return "can only move 1 spy in enemy's territory";
      } else{
        return null;
      }
    }
  }

  /**
   * Method to check if a territpry has enough units for moving
   * @param Territory, HashMap of units
   */
  private boolean hasEnoughUnits(Territory<T> territory, HashMap<String, Integer> allunits) {
    // for each unit type, check if the territory has enough units
    for (String unitType : allunits.keySet()) {
      if (territory.getUnit(unitType) < allunits.get(unitType)) {
        return false;
      }
    }
    return true;
  }
}
