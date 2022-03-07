package edu.duke.ece651.mp.client;
import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.mp.common.V1Map;

/**
 * This class handles textual display of a Map (i.e. 
 * converting a Map class to a string to show to the user).  */

public class MapTextView {
  /**
   * The Map to display
   */
  private V1Map<Character> toDisplay;

  /**
   * Constructs a MapView, given the map it will display.
   * 
   * @param toMap is the Map to display
   */
  public MapTextView(V1Map<Character> toDisplay) {
    this.toDisplay = toDisplay;
  }

  
  public void updateTextView(V1Map<Character> newMap) {
    this.toDisplay = newMap;
  }

  /**
   * Display the map
   * 
   * Example format:
   * 
   * Green player:
   * ------------
   * 10 units in Narnia (next to: Elantris, Midkemia)
   * 12 units in Midkemia (next to: Narnia, Elantris, Scadrial, Oz)
   * 8 units in Oz (next to: Midkemia, Scadrial, Mordor, Gondor)
   * 
   * Blue player:
   * -----------
   * 6 units in Elantris (next to: Roshar, Scadrial, Midkemia, Narnia)
   * 3 units in Roshar (next to: Hogwarts, Scadrial, Elantris)
   * 5 units in Scadrial (next to: Elantris, Roshar, Hogwats, Mordor Oz, Midkemia, Elantris)
   */
  public String displayMap() {
    HashMap<String, ArrayList<String>> terrGroups = toDisplay.getOwnersTerritoryGroups();
    
    StringBuilder ans = new StringBuilder(""); // empty at first
    
    ans.append(displayPlayerTerritories("Green", terrGroups));
    
    ans.append("\n"); // empty line between two players' info
    
    ans.append(displayPlayerTerritories("Blue", terrGroups));
    
    return ans.toString();
  }

  /**
   * This method displays the map/territory info for each player
   * @param player's color, HashMap with player's color as key and list of player's territories as value
   * @return the display info as string
   */
  private String displayPlayerTerritories(String playerColor, HashMap<String, ArrayList<String>> terrGroups) {
    ArrayList<String> terrList = terrGroups.get(playerColor);
    StringBuilder playerInfo = new StringBuilder("");
    playerInfo.append(makePlayerHeader(playerColor));
    for (String terrName : terrList) {
      playerInfo.append(terrName);
      playerInfo.append("\n");
    }
    return playerInfo.toString();

  }

  /** This method makes the header for showing each
   * player's territory information
   */
  private String makePlayerHeader(String playerName) {
    StringBuilder header = new StringBuilder(""); // empty at first
    header.append(playerName);
    header.append(" player:");
    header.append("\n");
    header.append("-----------");
    header.append("\n");

    return header.toString();
  }

}
