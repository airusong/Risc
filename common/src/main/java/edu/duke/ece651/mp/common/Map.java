package edu.duke.ece651.mp.common;

import java.util.ArrayList;
import java.util.HashMap;

public interface Map<T> {
  public HashMap<String, Territory<T>> getAllTerritories();

  public ArrayList<String> getPlayerColors();

  public void updateMap(String dep, String des, String unit_type, int n1, int n2);
  public void updateTempMap(String dep, String unit_type, int n);

  //public void updateTerritoryInMap(String territoryName, int unitChage, String newOwnerColor);
  public void updateTerritoryInMap(String territoryName, String unitType, int unitChage);

  public void updateMapbyOneUnit();
}
