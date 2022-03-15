package edu.duke.ece651.mp.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class V1Map<T> implements edu.duke.ece651.mp.common.Map<T>, Serializable {
  public HashMap<String, Territory<T>> myTerritories; // key=name, value=object itself
  public ArrayList<String> players_colors;

  /**
   * Constructor
   * construct a V1Map specified with Territoris in it
   * 
   * @param hashmap myTerritories,
   *                the key is the territory itself and
   *                the value is the list of adjancent territories
   */
  public V1Map(ArrayList<String> players_colors) {
    this.myTerritories = new HashMap<>();
    this.players_colors = players_colors;
    setMap();
    addAdjacency();
  }

  // public HashMap<String, Territory<T>> getMap(){
  // return myTerritories;
  // }
  /**
   * function used in constructor to initialize the hashmap
   * 
   **/
  protected void setMap() {
    // PLAYER 1
    String player_color = players_colors.get(0);
    myTerritories.put("Narnia", new Territory<T>("Narnia", player_color, new ArrayList<String>(), 8));
    myTerritories.put("Midemio", new Territory<T>("Midemio", player_color, new ArrayList<String>(), 3));
    myTerritories.put("Oz", new Territory<T>("Oz", player_color, new ArrayList<String>(), 12));

    // PLAYER 2
    player_color = players_colors.get(1);
    myTerritories.put("Elantris", new Territory<T>("Elantris", player_color, new ArrayList<String>(), 7));
    myTerritories.put("Scadnal", new Territory<T>("Scadnal", player_color, new ArrayList<String>(), 10));
    myTerritories.put("Roshar", new Territory<T>("Roshar", player_color, new ArrayList<String>(), 4));
  }

  /**
   * function used to initialize the adjacency information in the map
   **/
  protected void addAdjacency() {
    myTerritories.get("Narnia").addAdjacency("Midemio");
    myTerritories.get("Narnia").addAdjacency("Elantris");
    myTerritories.get("Midemio").addAdjacency("Narnia");
    myTerritories.get("Midemio").addAdjacency("Oz");
    myTerritories.get("Oz").addAdjacency("Midemio");
    myTerritories.get("Oz").addAdjacency("Roshar");
    myTerritories.get("Elantris").addAdjacency("Scadnal");
    myTerritories.get("Elantris").addAdjacency("Narnia");
    myTerritories.get("Scadnal").addAdjacency("Roshar");
    myTerritories.get("Scadnal").addAdjacency("Elantris");
    myTerritories.get("Roshar").addAdjacency("Oz");
    myTerritories.get("Roshar").addAdjacency("Scadnal");

  }

  /**
   * Write out the V1Map object for serialization
   * to the ObjectOutputStream s.
   * readObject depends on this data format.
   */
  private void writeObject(ObjectOutputStream s) throws IOException {
    // Call even if there is no default serializable fields.
    s.defaultWriteObject();

    // Hashmap is serializable
    s.writeObject(myTerritories);

    // otehr fields go here

  }

  /**
   * Read in the V1Map object from the ObjectInputStream s.
   * Was written to by writeObject.
   * 
   * @serialData Read serializable fields, if any exist.
   */
  @SuppressWarnings({ "unchecked" })
  private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
    /*
     * Call even if there is no default serializable fields.
     * Enables default serializable fields to be added in future versions
     * and skipped by this version which has no default serializable fields.
     */
    s.defaultReadObject();

    // restore the HashMap
    myTerritories = (HashMap<String, Territory<T>>) s.readObject();

    // other fields go here
  }

  /**
   * method to get territories grouped by owner
   * 
   * @return hashmap with key as the owner/player color
   *         and value as an ArrayList of territories owned by
   *         the player
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

  /* Update Map according to move order */
  public void updateMap(String dep, String des, int n1, int n2) {
    Territory t1 = myTerritories.get(dep);
    Territory t2 = myTerritories.get(des);
    t1.updateUnit(n1);
    t2.updateUnit(n2);
  }

  /* Increase #Units after fighting */
  public void updateMapbyOneUnit(){
    for(int i=0; i<myTerritories.size(); i++){
      Territory<T> temp = myTerritories.get(i);
      temp.updateUnit(temp.getUnit() + 1);
      myTerritories.put(temp.getName(), temp);
    }
  }

}
