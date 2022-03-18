package edu.duke.ece651.mp.server;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.V1Map;

public class AttackChecking<T> {
  public boolean checkMyRule(V1Map<T> map,String source,String destination,int attackingunits){
    Territory<T> attacker=map.myTerritories.get(source);
    Territory<T> defender=map.myTerritories.get(destination);
    //check if the attacker and defender are adjacent and belong to different players
    if(!attacker.getAdjacency().contains(destination)||attacker.getColor().equals(defender.getColor())){
      return false;
    }
    //check if the attcker has enough units
    if(attacker.getUnit()<attackingunits){
      return false;
    }
    return true;
    }
}
