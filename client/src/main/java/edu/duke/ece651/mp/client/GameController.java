package edu.duke.ece651.mp.client;

import java.util.ArrayList;
import java.util.HashMap;

import edu.duke.ece651.mp.common.*;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import edu.duke.ece651.mp.common.V2Map;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.print.attribute.HashPrintServiceAttributeSet;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Popup;

public class GameController {
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

  // Shaped for rectangular boxes in the map representing territories
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

  // Lines between rectangles to show adjacency
  @FXML
  private Line Line1to2;
  @FXML
  private Line Line1to3;
  @FXML
  private Line Line1to4;
  @FXML
  private Line Line1to5;
  @FXML
  private Line Line1to6;
  @FXML
  private Line Line2to3;
  @FXML
  private Line Line2to4;
  @FXML
  private Line Line2to5;
  @FXML
  private Line Line2to6;
  @FXML
  private Line Line3to4;
  @FXML
  private Line Line3to5;
  @FXML
  private Line Line3to6;
  @FXML
  private Line Line4to5;
  @FXML
  private Line Line4to6;
  @FXML
  private Line Line5to6;

  // The first key in the string is from territory, and the second key is to
  // territory
  private HashMap<String, HashMap<String, Line>> TerritoryAdjacency;

  // Text field to show game status
  @FXML
  private TextField GameStatus;

  /**
   * Method to setup the map in the UI
   * 
   */
  public void setUpMap() {
    V2Map<Character> initialMap = theTextPlayer.theMap;
    setUpTerritories(initialMap);
  }

  /**
   * Method to setup territories
   */
  private void setUpTerritories(V2Map<Character> initialMap) {
    // setup name, units and color of each territory first
    initTerritories(initialMap);

    // draw lines between territories for showing adjacency
    setAdjacency(initialMap);

  }

  ArrayList<StringProperty> terrUnitsList = new ArrayList<>();

  /**
   * Method to initialize the hashmaps
   */
  private void initTerritories(V2Map<Character> initialMap) {
    // init lists with Java FX components
    initLists();

    HashMap<String, Territory<Character>> allTerritories = initialMap.getAllTerritories();

    // organize the territories according to player color
    HashMap<String, ArrayList<String>> terrGroups = initialMap.getOwnersTerritoryGroups();

    TerritoryBoxes = new HashMap<>();
    TerritoryNames = new HashMap<>();
    TerritoryUnits = new HashMap<>();
    int i = 0;
    for (String player_color : initialMap.getPlayerColors()) {
      Color terrColor = Color.WHITE; // default
      if (player_color.equals("Green")) {
        terrColor = Color.GREEN;
      } else if (player_color.equals("Blue")) {
        terrColor = Color.BLUE;
      }
      // get territories of this player color
      ArrayList<String> terrList = terrGroups.get(player_color);
      for (String terrName : terrList) {
        terrBoxes.get(i).setFill(terrColor);
        terrNames.get(i).setText(terrName);
        // To Do: Display different number of various UnitType
        // Now just display Alevel
        terrUnits.get(i).setText("ALEVEL:" + allTerritories.get(terrName).getUnit("ALEVEL"));
        TerritoryBoxes.put(terrName, terrBoxes.get(i));
        TerritoryNames.put(terrName, terrNames.get(i));
        TerritoryUnits.put(terrName, terrUnits.get(i));
        i++;
      }
    }
  }

  /**
   * Method to initialize the lists of Java FX components
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

  /**
   * Method to draw lines between territories in the UI based on their adjacency
   */
  private void setAdjacency(V2Map<Character> initialMap) {
    initAdjacencyList();

    HashMap<String, Territory<Character>> allTerritories = initialMap.getAllTerritories();
    // get adjacency for each
    for (String terrName : allTerritories.keySet()) {
      ArrayList<String> adjacentTerr = allTerritories.get(terrName).getAdjacency();
      // for each adjacent territory
      for (String adjTerr : adjacentTerr) {
        TerritoryAdjacency.get(terrName).get(adjTerr).setVisible(true);
      }
    }
  }

  /**
   * method to create the adjacency map
   */
  private void initAdjacencyList() {
    String fromTerritory;

    TerritoryAdjacency = new HashMap<>();

    // Territory 1
    fromTerritory = terrNames.get(0).getText();
    HashMap<String, Line> terr1Adj = new HashMap<>();
    terr1Adj.put(terrNames.get(1).getText(), Line1to2);
    terr1Adj.put(terrNames.get(2).getText(), Line1to3);
    terr1Adj.put(terrNames.get(3).getText(), Line1to4);
    terr1Adj.put(terrNames.get(4).getText(), Line1to5);
    terr1Adj.put(terrNames.get(5).getText(), Line1to6);
    TerritoryAdjacency.put(fromTerritory, terr1Adj);

    // Territory 2
    fromTerritory = terrNames.get(1).getText();
    HashMap<String, Line> terr2Adj = new HashMap<>();
    terr2Adj.put(terrNames.get(0).getText(), Line1to2);
    terr2Adj.put(terrNames.get(2).getText(), Line2to3);
    terr2Adj.put(terrNames.get(3).getText(), Line2to4);
    terr2Adj.put(terrNames.get(4).getText(), Line2to5);
    terr2Adj.put(terrNames.get(5).getText(), Line2to6);
    TerritoryAdjacency.put(fromTerritory, terr2Adj);

    // Territory 3
    fromTerritory = terrNames.get(2).getText();
    HashMap<String, Line> terr3Adj = new HashMap<>();
    terr3Adj.put(terrNames.get(0).getText(), Line1to3);
    terr3Adj.put(terrNames.get(1).getText(), Line2to3);
    terr3Adj.put(terrNames.get(3).getText(), Line3to4);
    terr3Adj.put(terrNames.get(4).getText(), Line3to5);
    terr3Adj.put(terrNames.get(5).getText(), Line3to6);
    TerritoryAdjacency.put(fromTerritory, terr3Adj);

    // Territory 4
    fromTerritory = terrNames.get(3).getText();
    HashMap<String, Line> terr4Adj = new HashMap<>();
    terr4Adj.put(terrNames.get(0).getText(), Line1to4);
    terr4Adj.put(terrNames.get(1).getText(), Line2to4);
    terr4Adj.put(terrNames.get(2).getText(), Line3to4);
    terr4Adj.put(terrNames.get(4).getText(), Line4to5);
    terr4Adj.put(terrNames.get(5).getText(), Line4to6);
    TerritoryAdjacency.put(fromTerritory, terr4Adj);

    // Territory 5
    fromTerritory = terrNames.get(4).getText();
    HashMap<String, Line> terr5Adj = new HashMap<>();
    terr5Adj.put(terrNames.get(0).getText(), Line1to5);
    terr5Adj.put(terrNames.get(1).getText(), Line2to5);
    terr5Adj.put(terrNames.get(2).getText(), Line3to5);
    terr5Adj.put(terrNames.get(3).getText(), Line4to5);
    terr5Adj.put(terrNames.get(5).getText(), Line5to6);
    TerritoryAdjacency.put(fromTerritory, terr5Adj);

    // Territory 6
    fromTerritory = terrNames.get(5).getText();
    HashMap<String, Line> terr6Adj = new HashMap<>();
    terr6Adj.put(terrNames.get(0).getText(), Line1to6);
    terr6Adj.put(terrNames.get(1).getText(), Line2to6);
    terr6Adj.put(terrNames.get(2).getText(), Line3to6);
    terr6Adj.put(terrNames.get(3).getText(), Line4to6);
    terr6Adj.put(terrNames.get(4).getText(), Line5to6);
    TerritoryAdjacency.put(fromTerritory, terr6Adj);

  }

  private TextPlayer theTextPlayer;
  ObservableList<String> playeraction_list = FXCollections.observableArrayList("Move", "Attack", "Upgrade");
  ObservableList<String> source_list = FXCollections.observableArrayList();
  ObservableList<String> destination_list = FXCollections.observableArrayList();
  ObservableList<String> unitType_list = FXCollections.observableArrayList();

  TurnList myTurn;

  @FXML
  private Label player_info;
  @FXML
  private ComboBox<String> playeraction;
  @FXML
  private Button order;
  @FXML
  private Button commit;
  @FXML
  public TextArea turnstatus;
  @FXML
  public TextArea errormessage;

  // Move and Attack order inputs
  @FXML
  private Pane MoveAttackPane;
  @FXML
  private ComboBox<String> from;
  @FXML
  private ComboBox<String> to;
  @FXML
  private TextField Units_A;
  @FXML
  private TextField Units_B;
  @FXML
  private TextField Units_C;
  @FXML
  private TextField Units_D;
  @FXML
  private TextField Units_E;
  @FXML
  private TextField Units_F;
  @FXML
  private TextField Units_G;

  private ArrayList<TextField> UnitTypeEntries;

  // Upgrade Order inputs
  @FXML
  private Pane UpgradePane;
  @FXML
  private TextField UpgradeUnits;
  @FXML
  private ComboBox<String> UpgradeTerritory;
  @FXML
  private ComboBox<String> UpgradeFrom;
  @FXML
  private ComboBox<String> UpgradeTo;

  public void setPlayer(TextPlayer player) {
    theTextPlayer = player;
  }

  public void initGame() {
    // Step-1 of playGame()
    theTextPlayer.receiveMap();
    setUpMap();

    // Step-2 of playGame()
    // Receive Game Status from server
    receiveAndUpdateGameStatus();

    setName();
    myTurn = new TurnList(theTextPlayer.identity);
    
    initiateUnitList();
    setOrderPane();
  }

  /**
   * Method to set up all the entry boxes for taking order
   */
  private void setOrderPane() {
    setActionBox();
    setTerritoryDropDowns();
    setUnitTypeBox();
  }

  /**
   * Method to set up drop downs for territories
   */
  private void setTerritoryDropDowns() {
    setSourceBox(from);
    setSourceBox(UpgradeTerritory);
    setDestinationBox();
  }

  /**
   * Method to create a list pf all unit type textfield entries
   */
  private void initiateUnitList() {
    UnitTypeEntries = new ArrayList<>();
    UnitTypeEntries.add(Units_A);
    UnitTypeEntries.add(Units_B);
    UnitTypeEntries.add(Units_C);
    UnitTypeEntries.add(Units_D);
    UnitTypeEntries.add(Units_E);
    UnitTypeEntries.add(Units_F);
    UnitTypeEntries.add(Units_G);
  }

  public void setName() {
    String name = theTextPlayer.identity;
    player_info.setText(name);
  }

  @FXML
  public void setActionBox() {
    playeraction.setItems(playeraction_list);
    playeraction.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        setTerritoryDropDowns();
      }
    });
  }

  public String getAction() {
    return (String) playeraction.getValue();
  }

  @FXML
  public void setSourceBox(ComboBox<String> whichBox) {
    ArrayList<String> own_territory_list = theTextPlayer.getMyOwnTerritories();
    source_list.clear();
    source_list.addAll(own_territory_list);
    whichBox.setItems(source_list);
    whichBox.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        setUnitTypeBox();
      }
    });
  }

  public String getSource() {
    return (String) from.getValue();
  }

  public String getUpgradeSource() {
    return (String) UpgradeTerritory.getValue();
  }

  @FXML
  public void setDestinationBox() {
    ArrayList<String> des_territory_list = new ArrayList<String>();
    if (getAction() != null) {
      if (getAction().equals("Move")) {
        des_territory_list = theTextPlayer.getMyOwnTerritories();
      } else if (getAction().equals("Attack")) {
        des_territory_list = theTextPlayer.getOthersTerritories();
      }
    }
    destination_list.clear();
    destination_list.addAll(des_territory_list);
    to.setItems(destination_list);
  }

  public String getDestination() {
    return (String) to.getValue();
  }

  /**
   * Method to set the unit type boxes for Upgrade Turn
   */
  public void setUnitTypeBox() {
    ArrayList<String> own_unitType_list = theTextPlayer.theMap.getTerritoryUnitType(getUpgradeSource());
    unitType_list.clear();
    unitType_list.addAll(own_unitType_list);
    UpgradeFrom.setItems(unitType_list);
    UpgradeTo.setItems(unitType_list);
  }

  /**
   * Method to get the unit type selected
   */
  public String getUpgradeFromUnitType() {
    return UpgradeFrom.getValue();
  }

  /**
   * Method to get the unit type selected
   */
  public String getUpgradeToUnitType() {
    return UpgradeTo.getValue();
  }

  /**
   * Method to get user entered unit number
   */
  public int getUnitNum_A() {
    int enteredVal;
    try {
      enteredVal = Integer.parseInt(Units_A.getText());
    } catch (NumberFormatException e) {
      enteredVal = 0;
    }
    return enteredVal;
  }

  /**
   * Method to get user entered unit number
   */
  public int getUnitNum_B() throws NumberFormatException {
    return Integer.parseInt(Units_B.getText());
  }

  /**
   * Method to get user entered unit number
   */
  public int getUnitNum_C() throws NumberFormatException {
    return Integer.parseInt(Units_C.getText());
  }

  /**
   * Method to get user entered unit number
   */
  public int getUnitNum_D() throws NumberFormatException {
    return Integer.parseInt(Units_D.getText());
  }

  /**
   * Method to get user entered unit number
   */
  public int getUnitNum_E() throws NumberFormatException {
    return Integer.parseInt(Units_E.getText());
  }

  /**
   * Method to get user entered unit number
   */
  public int getUnitNum_F() throws NumberFormatException {
    return Integer.parseInt(Units_F.getText());
  }

  /**
   * Method to get user entered unit number
   */
  public int getUnitNum_G() throws NumberFormatException {
    return Integer.parseInt(Units_G.getText());
  }

  public String getPlayerColor() {
    return theTextPlayer.identity;
  }

  /*
   * Method to check if the user inputs for adding order is valid
   */
  @FXML
  boolean errorMessageShowing() {
    // Clear any previous error message
    errormessage.clear();

    boolean result = true;

    // For move/attack order, make sure the "from" and "to" are entered
    if ((getAction().equals("Move") || getAction().equals("Attack"))
        && (getSource() == null || getDestination() == null)) {
      errormessage.appendText("Both source and destination is needed.");
      result &= false;
    }

    // Now check if the unit number is positive and greater than zero
    if ((getUnitNum_A() <= 0) || (getUnitNum_B() <= 0) || (getUnitNum_C() <= 0) || (getUnitNum_D() <= 0)
        || (getUnitNum_E() <= 0) || (getUnitNum_F() <= 0) || (getUnitNum_G() <= 0)) {
      errormessage.appendText("Unit number must be positive & greater than zero");
      result &= false;
    }
    return result;
  }

  @FXML
  void onAddOrderButton(MouseEvent event) {
    boolean result = errorMessageShowing();

    if (result) { // if the inputs are valid
      // Check the type order
      if (getAction().equals("Move") || getAction().equals("Upgrade")) {
        Turn newOrder = new MoveTurn(getSource(), getDestination(), "ALEVEL", getUnitNum_A(), getPlayerColor());
        // newOrder.printTurn();
        myTurn.addTurn(newOrder);
        GameStatus.setText(getAction() + " order from " + getSource() + " to " + getDestination() + " added");
      } else if (getAction().equals("Attack")) {
        Turn newOrder = new AttackTurn(getSource(), getDestination(), "ALEVEL", getUnitNum_A(), getPlayerColor());
        // newOrder.printTurn();
        myTurn.addTurn(newOrder);
        GameStatus.setText(getAction() + " order from " + getSource() + " to " + getDestination() + " added");
      } else if (getAction().equals("Upgrade")) {
        // create upgrade
        GameStatus.setText(getAction() + " order in " + getUpgradeSource() + " from " + getUpgradeFromUnitType() + " to " + getUpgradeToUnitType() + " added");
      }
      // theClient.theTextPlayer.takeAndSendTurn();
      System.out.println("Added a New Order");
    }
    clearSelectedAction();

  }

  @FXML
  private void clearSelectedAction() {
    // Hide the order details pane
    // MoveAttackPane.setVisible(false);
    // UpgradePane.setVisible(false);

    playeraction.setValue("Select an action");
  }

  @FXML
  void onCommitButton(MouseEvent event) {
    // Clear the turn status box before committing new turn
    turnstatus.deleteText(0, turnstatus.getLength());

    clearSelectedAction();

    // send TurnList to Server
    // Step-3 in Master playGame() in server
    // Similar to "takeAndSendTurn"
    theTextPlayer.connectionToMaster.sendToServer(myTurn);
    GameStatus.setText("Turn sent to server. Waiting for turn result...");
    System.out.println("Sent the TurnList");

    // Disable the button
    order.setDisable(true);
    commit.setDisable(true);

    // receive turn status
    // Step-4 in Master playGame() in server
    ArrayList<String> turnResult = theTextPlayer.receiveTurnStatus();

    // display the turn status in UI
    for (String s : turnResult) {
      turnstatus.appendText(s + "\n");
    }
    // remove the current turnlist
    myTurn.order_list.clear();

    // receive updated map
    // Step-1 in Master playGame() in server
    theTextPlayer.receiveMap();
    updateUIMap();
    // updateBox
    setOrderPane();
    
    // receive game status
    // Step-2 in Master playGame() in server
    String gameStatus = receiveAndUpdateGameStatus();

    if (gameStatus.startsWith("Ready")) {
      // ready for next turn
      // re-enable commit button
      order.setDisable(false);
      commit.setDisable(false);
    } else {
      // end of game
      GameStatus.appendText(" GAME OVER!!!");
    }
  }

  /**
   * method to update the map after each turn
   */
  private void updateUIMap() {
    HashMap<String, Territory<Character>> allTerritories = theTextPlayer.theMap.getAllTerritories();
    for (String terrName : TerritoryUnits.keySet()) {
      // Update number of units
      TerritoryUnits.get(terrName).setText(Integer.toString(allTerritories.get(terrName).getUnit("ALEVEL")));

      // Update color of territory
      String player_color = allTerritories.get(terrName).getColor();
      Color terrColor = Color.WHITE; // default
      if (player_color.equals("Green")) {
        terrColor = Color.GREEN;
      } else if (player_color.equals("Blue")) {
        terrColor = Color.BLUE;
      }
      TerritoryBoxes.get(terrName).setFill(terrColor);
    }
  }

  /**
   * method to receive the game status and update the GUI
   */
  private String receiveAndUpdateGameStatus() {
    String gamestatus = theTextPlayer.receiveAndPrintGameStatus();
    GameStatus.setText(gamestatus);
    return gamestatus;
  }

  /**
   * Method to show/hide specific order details pane based on selected action
   */
  @FXML
  void OnSelectedOrderType() {
    String selectedAction = getAction();
    if (selectedAction.equals("Upgrade")) {
      UpgradePane.setVisible(true);
      MoveAttackPane.setVisible(false);

    } else if (selectedAction.equals("Move") || selectedAction.equals("Attack")) {
      MoveAttackPane.setVisible(true);
      UpgradePane.setVisible(false);
    } else {
      MoveAttackPane.setVisible(false);
      UpgradePane.setVisible(false);
    }
  }

}
