package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class V2Map<T> implements edu.duke.ece651.mp.common.Map<T>, Serializable {
  public HashMap<String, Territory<T>> myTerritories; // key=name, value=object itself
  public ArrayList<String> players_colors;

  /**
   * Constructor construct a V2Map specified with Territoris in it
   * 
   * @param hashmap myTerritories, the key is the territory itself and the value
   *                is the list of adjancent territories
   */
  public V2Map(ArrayList<String> players_colors) {
    this.myTerritories = new HashMap<>();
    this.players_colors = players_colors;
    setMap();
    addAdjacency();
    addResources();
    addUnits();
  }

  public V2Map() {
    this.myTerritories = new HashMap<>();
    ArrayList<String> players_colors = new ArrayList<String>();
    players_colors.add("Green");
    players_colors.add("Blue");
    this.players_colors = players_colors;
    setMap();
    addAdjacency();
    addResources();
    addUnits();
  }

  public HashMap<String, Territory<T>> getAllTerritories() {
    return myTerritories;
  }

  public ArrayList<String> getPlayerColors() {
    return players_colors;
  }

  /**
   * function used in constructor to initialize the hashmap
   * 
   **/
  protected void setMap() {
    // PLAYER 1
    String player_color = players_colors.get(0);
    ArrayList<Unit> unit_list = new ArrayList<Unit>();
    myTerritories.put("Narnia", new Territory<T>("Narnia", player_color, new ArrayList<String>(), 8));
    myTerritories.put("Midemio", new Territory<T>("Midemio", player_color, new ArrayList<String>(), 3));
    myTerritories.put("Oz", new Territory<T>("Oz", player_color, new ArrayList<String>(), 12));

    // PLAYER 2
    player_color = players_colors.get(1);
    myTerritories.put("Elantris", new Territory<T>("Elantris", player_color, new ArrayList<String>(), 7));
    myTerritories.put("Scadnal", new Territory<T>("Scadnal", player_color, new ArrayList<String>(), 10));
    myTerritories.put("Roshar", new Territory<T>("Roshar", player_color, new ArrayList<String>(), 6));
  }

  /**
   * function used to initialize the adjacency information in the map
   **/
  protected void addAdjacency() {
    myTerritories.get("Narnia").addAdjacency("Midemio");
    myTerritories.get("Narnia").addAdjacency("Elantris");
    myTerritories.get("Midemio").addAdjacency("Narnia");
    myTerritories.get("Midemio").addAdjacency("Oz");
    myTerritories.get("Midemio").addAdjacency("Elantris");
    myTerritories.get("Midemio").addAdjacency("Scadnal");
    myTerritories.get("Oz").addAdjacency("Midemio");
    myTerritories.get("Oz").addAdjacency("Scadnal");
    myTerritories.get("Elantris").addAdjacency("Scadnal");
    myTerritories.get("Elantris").addAdjacency("Narnia");;
    myTerritories.get("Elantris").addAdjacency("Roshar");;
    myTerritories.get("Elantris").addAdjacency("Midemio");
    myTerritories.get("Scadnal").addAdjacency("Roshar");
    myTerritories.get("Scadnal").addAdjacency("Elantris");;
    myTerritories.get("Scadnal").addAdjacency("Oz");;
    myTerritories.get("Scadnal").addAdjacency("Midemio");
    myTerritories.get("Roshar").addAdjacency("Elantris");
    myTerritories.get("Roshar").addAdjacency("Scadnal");
  }

  /**
   * function used to initialize the Resource information in the map
   **/
  protected void addResources() {
    myTerritories.get("Narnia").addResource(new Resource("food", 10));
    myTerritories.get("Narnia").addResource(new Resource("tech", 20));
    myTerritories.get("Midemio").addResource(new Resource("food", 20));
    myTerritories.get("Midemio").addResource(new Resource("tech", 10));
    myTerritories.get("Oz").addResource(new Resource("food", 20));
    myTerritories.get("Oz").addResource(new Resource("tech", 20));

    myTerritories.get("Elantris").addResource(new Resource("food", 20));
    myTerritories.get("Elantris").addResource(new Resource("tech", 15));
    myTerritories.get("Scadnal").addResource(new Resource("food", 15));
    myTerritories.get("Scadnal").addResource(new Resource("tech", 15));
    myTerritories.get("Roshar").addResource(new Resource("food", 15));
    myTerritories.get("Roshar").addResource(new Resource("tech", 20));
  }

  /**
   * function used to initialize the unit information in the map
   **/
  protected void addUnits() {
    myTerritories.get("Narnia").addUnit(new Unit("ALEVEL", 20));
    myTerritories.get("Midemio").addUnit(new Unit("ALEVEL", 20));
    myTerritories.get("Oz").addUnit(new Unit("ALEVEL", 20));

    myTerritories.get("Elantris").addUnit(new Unit("ALEVEL", 20));
    myTerritories.get("Scadnal").addUnit(new Unit("ALEVEL", 20));
    myTerritories.get("Roshar").addUnit(new Unit("ALEVEL", 20));
  }

  /**
   * Write out the V1Map object for serialization to the ObjectOutputStream s.
   * readObject depends on this data format.
   */
  private void writeObject(ObjectOutputStream s) throws IOException {
    // Call even if there is no default serializable fields.
    s.defaultWriteObject();

    // Hashmap is serializable
    s.writeObject(myTerritories);

    // otehr fields go here
    s.writeObject(players_colors);
  }

  /**
   * Read in the V1Map object from the ObjectInputStream s. Was written to by
   * writeObject.
   * 
   * @serialData Read serializable fields, if any exist.
   */
  @SuppressWarnings({ "unchecked" })
  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    /*
     * Call even if there is no default serializable fields. Enables default
     * serializable fields to be added in future versions and skipped by this
     * version which has no default serializable fields.
     */
    s.defaultReadObject();

    // restore the HashMap
    myTerritories = (HashMap<String, Territory<T>>) s.readObject();
    // other fields go here
  }

  /**
   * method to get territories grouped by owner
   * 
   * @return hashmap with key as the owner/player color and value as an ArrayList
   *         of territories owned by the player
   */
  public HashMap<String, ArrayList<String>> getOwnersTerritoryGroups() {
    HashMap<String, ArrayList<String>> territoryGroups = new HashMap<>();
    territoryGroups.put("Green", new ArrayList<>());
    territoryGroups.put("Blue", new ArrayList<>());

    for (Map.Entry<String, Territory<T>> entry : myTerritories.entrySet()) {
      Territory<T> terr = entry.getValue();
      String terrOwner = terr.getColor();
      territoryGroups.get(terrOwner).add(terr.getName());
    }
    return territoryGroups;
  }

  /*
   * method to get units' types of specific territory
   * 
   * @input String: the name of territory
   * 
   * @return ArrayList<String> consist of units' types
   */
  public ArrayList<String> getTerritoryUnitType(String currTerritory) {
    ArrayList<String> unit_type_list = new ArrayList<String>();
    for (Map.Entry<String, Territory<T>> entry : myTerritories.entrySet()) {
      Territory<T> terr = entry.getValue();
      if (terr.getName().equals(currTerritory)) {
        ArrayList<Unit> unit_list = terr.getUnitList();
        for (Unit unit : unit_list) {
          unit_type_list.add(unit.getUnitType());
          System.out.println("The unit type is :" + unit.getUnitType());
        }
      }
    }
    return unit_type_list;
  }

  /*
   * Update Map according to move order
   * 
   * @input dep: start of Moving Action
   * des: destination of Moving Action
   * unit_type: moving units' type
   * n1: #unit in dep
   * n2: #unit in des
   */
  public void updateMap(String dep, String des, String unit_type, int n1, int n2) {
    Territory<T> t1 = myTerritories.get(dep);
    t1.updateUnit(unit_type, n1);
    myTerritories.put(dep, t1);

    Territory<T> t2 = myTerritories.get(des);
    t2.updateUnit(unit_type, n2);
    myTerritories.put(des, t2);
  }

  /* Update TempMap for moving all units before attacking */
  public void updateTempMap(String dep, String unit_type, int n) {
    Territory<T> t = myTerritories.get(dep);
    t.updateUnit(unit_type, n);
  }

  /* Update Territory in the Map, including changing the owner of the Territory */
  
  public void updateTerritoryInMap(String territoryName, String unitType, int unitChange, String newOwnerColor) {
    Territory<T> terr = myTerritories.get(territoryName);
    int currUnits = terr.getUnit(unitType);
    int newUnits = currUnits + unitChange;
    terr.updateUnit(unitType, newUnits);
    if (newOwnerColor != "Unchanged") {
      terr.updateColor(newOwnerColor);
    }
    myTerritories.put(territoryName, terr);
  }


  /* Update Territory in the Map, including changing the owner of the Territory */
  // For the Use of updating combate result.
  public void updateTerritoryInMap(String territoryName, HashMap<String, Integer> unit_change, String newOwnerColor) {
    Territory<T> terr = myTerritories.get(territoryName);
    for(Map.Entry<String, Integer> set: unit_change.entrySet()){
      int unitChange = set.getValue();
      int currUnits = terr.getUnit(set.getKey());
      int newUnits = currUnits + unitChange;
      terr.updateUnit(set.getKey(), newUnits);
    }
    if (newOwnerColor != "Unchanged") {
      terr.updateColor(newOwnerColor);
    }
    myTerritories.put(territoryName, terr);
  }

  
  public void updateTerritoryInMap(String territoryName, String unitType, int unitChange) {
    updateTerritoryInMap(territoryName, unitType, unitChange, "Unchanged");
  }
  

  // Increase #Units after fighting - add one basic unit - ALEVEL
  public void updateMapbyOneUnit() {
    HashMap<String, Territory<T>> myT = myTerritories;
    int basic_unit_num = 0;
    for (Map.Entry<String, Territory<T>> set : myT.entrySet()) {
      Territory<T> temp = set.getValue();
      // just add One basic Unit to Territory
      for (Unit unit : temp.getUnitList()) {
        if (unit.getUnitType().equals("ALEVEL")) {
          basic_unit_num = unit.getUnitNum();
        }
      }
      temp.updateUnit("ALEVEL", basic_unit_num + 1);
      myTerritories.put(temp.getName(), temp);
    }
    System.out.println("End of turn: Added one basic unit to each territory.");
  }

  public ArrayList<String> getPlayerTerritories(String player_color) {
    HashMap<String, ArrayList<String>> terrGroups = getOwnersTerritoryGroups();
    ArrayList<String> terrList = terrGroups.get(player_color);
    return terrList;
  }

}
