package edu.duke.ece651.mp.common;

import java.util.ArrayList;
import java.util.HashMap;

public interface Map<T> {
  public HashMap<String, Territory<T>> getAllTerritories();

  public ArrayList<String> getPlayerColors();

  public void updateMap(String dep, String des, String unit_type, int n1, int n2);

  // public void updateTempMap(String dep, String unit_type, int n);
  public void updateTempMap(String dep, AttackTurn attackTurn);

  public void updateTerritoryInMap(String territoryName, String unit_type, int unitChage, String newOwnerColor);

  public void updateTerritoryInMap(String territoryName, String unit_type, int unitChage);

  public void updateMapbyOneUnit();

  public void updateMapForUpgrade(String fromTerritory, String old_type, String new_type, int unitChange);

  public ArrayList<String> getTerritoryUnitType(String currTerritory);
  // public void updateTerritoryInMap(String territoryName, HashMap<UnitType,
  // Integer> unit_change, String newOwnerColor);
}
