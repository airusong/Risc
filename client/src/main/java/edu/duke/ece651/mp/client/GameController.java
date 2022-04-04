package edu.duke.ece651.mp.client;

import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.V1Map;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class GameController {

  // Text fields to show player color
  @FXML
  private TextField player_info;

  // Stack panes holding all territory elements
  @FXML
  private StackPane Territory1;
  @FXML
  private StackPane Territory2;
  @FXML
  private StackPane Territory3;
  @FXML
  private StackPane Territory4;
  @FXML
  private StackPane Territory5;
  @FXML
  private StackPane Territory6;

  private StackPane[] terrStackPanes = { Territory1, Territory2, Territory3, Territory4, Territory5, Territory6 };

  // Nodes for rectangular boxes in the map representing territories
  @FXML
  private Shape Terr1Box;
  @FXML
  private Shape Terr2Box;
  @FXML
  private Shape Terr3Box;
  @FXML
  private Shape Terr4Box;
  @FXML
  private Shape Terr5Box;
  @FXML
  private Shape Terr6Box;

  private ArrayList<Shape> terrBoxes;

  // Text fields to show territory names in map
  @FXML
  private TextField Terr1Name;
  @FXML
  private TextField Terr2Name;
  @FXML
  private TextField Terr3Name;
  @FXML
  private TextField Terr4Name;
  @FXML
  private TextField Terr5Name;
  @FXML
  private TextField Terr6Name;

  private ArrayList<TextField> terrNames;

  // Text fields to show territory units
  @FXML
  private TextField Terr1Units;
  @FXML
  private TextField Terr2Units;
  @FXML
  private TextField Terr3Units;
  @FXML
  private TextField Terr4Units;
  @FXML
  private TextField Terr5Units;
  @FXML
  private TextField Terr6Units;

  private ArrayList<TextField> terrUnits;

  private HashMap<String, Shape> TerritoryBoxes;
  private HashMap<String, TextField> TerritoryNames;
  private HashMap<String, TextField> TerritoryUnits;

  public void setName(String name) {
    player_info.setText(name);
  }

  /**
   * Method to setup the map in the UI
   * 
   * @param TextPlayer
   */
  public void setUpMap(TextPlayer player) {
    V1Map<Character> initialMap = player.theMap;
    setUpTerritories(initialMap);
  }

  /**
   * Method to setup territories
   */
  private void setUpTerritories(V1Map<Character> initialMap) {
    // init lists with Java FX components
    initLists();

    // setup name, units and color of each territory first
    initTerritories(initialMap);

    // draw lines between territories for showing adjacency

  }

  /**
   * Method to initialize the hashmaps
   */
  private void initTerritories(V1Map<Character> initialMap) {
    HashMap<String, Territory<Character>> allTerritories = initialMap.getAllTerritories();

    // organize the territories according to player color
    HashMap<String, ArrayList<String>> terrGroups = initialMap.getOwnersTerritoryGroups();

    TerritoryBoxes = new HashMap<>();
    TerritoryNames = new HashMap<>();
    TerritoryUnits = new HashMap<>();
    int i = 0;
    for (String player_color : initialMap.getPlayerColors()) {
      Color terrColor = Color.WHITE; // default
         if(player_color.equals("Green")) {
        terrColor = Color.GREEN;
      }
         else if(player_color.equals("Blue")) {
           terrColor = Color.BLUE;
         }
      // get territories of this player color
      ArrayList<String> terrList = terrGroups.get(player_color);
      for (String terrName : terrList) {
        terrBoxes.get(i).setFill(terrColor);
        terrNames.get(i).setText(terrName);
        terrUnits.get(i).setText(Integer.toString(allTerritories.get(terrName).getUnit()));
        TerritoryBoxes.put(terrName, terrBoxes.get(i));
        TerritoryNames.put(terrName, terrNames.get(i));
        TerritoryUnits.put(terrName, terrUnits.get(i));
        i++;
      }

    }
  }

  /**
   *
   */
  private void initLists() {
    // Add rectangles
    terrBoxes = new ArrayList<Shape>();
    terrBoxes.add(Terr1Box);
    terrBoxes.add(Terr2Box);
    terrBoxes.add(Terr3Box);
    terrBoxes.add(Terr4Box);
    terrBoxes.add(Terr5Box);
    terrBoxes.add(Terr6Box);

    // Add names
    terrNames = new ArrayList<TextField>();
    terrNames.add(Terr1Name);
    terrNames.add(Terr2Name);
    terrNames.add(Terr3Name);
    terrNames.add(Terr4Name);
    terrNames.add(Terr5Name);
    terrNames.add(Terr6Name);

    // Add units
    terrUnits = new ArrayList<TextField>();
    terrUnits.add(Terr1Units);
    terrUnits.add(Terr2Units);
    terrUnits.add(Terr3Units);
    terrUnits.add(Terr4Units);
    terrUnits.add(Terr5Units);
    terrUnits.add(Terr6Units);
  }

}
