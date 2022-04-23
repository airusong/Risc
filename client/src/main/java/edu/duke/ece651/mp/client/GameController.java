package edu.duke.ece651.mp.client;

import edu.duke.ece651.mp.common.*;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.HashMap;

public class GameController {

  // Text fields to show territory names in map
  @FXML
  private Button Terr1Button;
  @FXML
  private Button Terr2Button;
  @FXML
  private Button Terr3Button;
  @FXML
  private Button Terr4Button;
  @FXML
  private Button Terr5Button;
  @FXML
  private Button Terr6Button;

  private ArrayList<Button> terrButtons;

  private HashMap<String, Button> TerritoryButtons;
  private ArrayList<String> TerritoryNames;

  // Text field to show game status
  @FXML
  private TextField GameStatus;

  /**
   * Method to setup the map in the UI
   * 
   */
  public void setUpMap() {
    V2Map<Character> initialMap = theTextPlayer.theMap;
    // setPlayerResourceView();
    setPlayerResourceDisplay();
    setUpTerritories(initialMap);
  }

  /**
   * Method to setup territories
   */
  private void setUpTerritories(V2Map<Character> initialMap) {
    // setup name, units and color of each territory first
    initTerritories(initialMap);

    // setup tooltips to show territory details
    // setTerritoryDetailsView();
  }

  HashMap<String, Tooltip> TerritoryTooltips;

  /**
   * Method to set tooltips so that territory details are shown when the user
   * hovers the mouse over it
   */

  private void setTerritoryDetailsView() {
    TerritoryTooltips = new HashMap<>();
    for (String terrName : TerritoryButtons.keySet()) {
      final Tooltip tooltip = new Tooltip();
      String terrDetails = theTextPlayer.theMap.getAllTerritories().get(terrName).getTerritoryDetails();
      tooltip.setText(terrDetails);
      TerritoryButtons.get(terrName).setTooltip(tooltip);
      TerritoryTooltips.put(terrName, tooltip);
    }
  }

  private void setTerritoryDetailsDisplay(String terrName) {
    Territory curTerr = theTextPlayer.theMap.getAllTerritories().get(terrName);
    int foodnum = curTerr.getFoodNum();
    int technum = curTerr.getTechNum();
    ArrayList<Unit> unit_list = curTerr.getUnitList();

    for(String unit: UnitNums.keySet()){
      UnitNums.get(unit).setText(Integer.toString(0)); // default: 0
      for(Unit u: unit_list){
        if(unit.equals(u.getUnitType())){
          UnitNums.get(unit).setText(Integer.toString(u.getUnitNum()));
          break;
        }
      }
    }
    terrfood.setText(Integer.toString(foodnum));
    terrtech.setText(Integer.toString(technum));
  }


  /**
   * Method to update the tooltip details
   */
  private void updateTerritoryDetailsView() {
    for (HashMap.Entry<String, Tooltip> entry : TerritoryTooltips.entrySet()) {
      // Before updating the tooltip, see if this territory is hidden or not. If it is hidden now, don't update the tooltip. That way we'll still show the information from previous turn
      if(theTextPlayer.theMap.getAllTerritories().get(entry.getKey()).getColor().equals("Hidden")) {
        continue; // don't update the tooltip
      }
      
      String terrDetails = theTextPlayer.theMap.getAllTerritories().get(entry.getKey()).getTerritoryDetails();

      // Check if there is any of the player's spies in an enemy territory
      if(!theTextPlayer.theMap.getAllTerritories().get(entry.getKey()).getColor().equals(theTextPlayer.identity)) {
        // if it's an enemy territory

        if(theTextPlayer.theMap.spy_map.get(theTextPlayer.identity).containsKey(entry.getKey())) {
          // if this territory exists in the player's spy map
          int spyNum = theTextPlayer.theMap.spy_map.get(theTextPlayer.identity).get(entry.getKey());
          terrDetails += "\n*****************************\n";
          terrDetails += "\n !!! My Spies : " + spyNum + " !!!\n";
        }
      }
      
      entry.getValue().setText(terrDetails);
    }
  }

  ArrayList<StringProperty> terrUnitsList = new ArrayList<>();

  /**
   * Method to initialize the hashmaps
   */
  private void initTerritories(V2Map<Character> initialMap) {
    // init lists with Java FX components
    initLists();
    System.out.println("In initTerritories");
    HashMap<String, Territory<Character>> allTerritories = initialMap.getAllTerritories();

    // organize the territories according to player color
    HashMap<String, ArrayList<String>> terrGroups = initialMap.getOwnersTerritoryGroups();

    // TerritoryButtons = new HashMap<>();
    TerritoryNames = new ArrayList();
    int i = 0;
    //System.out.println(terrGroups.keySet());
    for (String player_color : terrGroups.keySet()) {
      Color terrColor = Color.WHITE; // default
      if (player_color.equals("Green")) {
        terrColor = Color.GREEN;
      } else if (player_color.equals("Blue")) {
        terrColor = Color.BLUE;
      }
      // get territories of this player color
      ArrayList<String> terrList = terrGroups.get(player_color);
      //System.out.println(player_color + ": " + terrList);
      for (String terrName : terrList) {
        String button_style = "-fx-background-color: rgba(240,240,240,.3)"; // default: white
        
        if(terrColor == Color.GREEN){
          button_style = "-fx-background-color: rgba(60,179,113,.3)";
          System.out.println(terrName + ": green");
        }
        else if(terrColor == Color.BLUE){
          button_style = "-fx-background-color: rgba(0,0,255,.3)";
          System.out.println(terrName + ": blue");
        }
        
        // Button curbutton = TerritoryButtons.get(terrName);
        TerritoryButtons.get(terrName).setStyle(button_style);
        // TerritoryButtons.put(terrName, curbutton);
        TerritoryNames.add(terrName);
        i++;
      }
    }
  }

  /**
   * Method to initialize the lists of Java FX components
   */
  private void initLists() {
    // Add rectangles

    terrButtons = new ArrayList<Button>();
    terrButtons.add(Terr1Button);
    terrButtons.add(Terr2Button);
    terrButtons.add(Terr3Button);
    terrButtons.add(Terr4Button);
    terrButtons.add(Terr5Button);
    terrButtons.add(Terr6Button);

    TerritoryButtons = new HashMap<String, Button>();
    TerritoryButtons.put("Narnia", terrButtons.get(0));
    TerritoryButtons.put("Midemio", terrButtons.get(1));
    TerritoryButtons.put("Oz", terrButtons.get(2));
    TerritoryButtons.put("Elantris", terrButtons.get(3));
    TerritoryButtons.put("Roshar", terrButtons.get(4));
    TerritoryButtons.put("Scadnal", terrButtons.get(5));


    // add UnitsTypes hashMap
    UnitNums = new HashMap<String, Label>();
    UnitNums.put("Guards",unit1num);
    UnitNums.put("Infantry",unit2num);
    UnitNums.put("SPY",unit8num);
    UnitNums.put("Archer",unit3num);
    UnitNums.put("Cavalry",unit4num);
    UnitNums.put("Dwarves",unit5num);
    UnitNums.put("Orcs",unit6num);
    UnitNums.put("Elves",unit7num);
  }

  private TextPlayer theTextPlayer;
  ObservableList<String> playeraction_list = FXCollections.observableArrayList("Move", "Attack", "Upgrade","Cloak");
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
  @FXML
  private TextField SPY;

  private HashMap<String, TextField> UnitTypeEntries;

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
  //cloaking order inputs
  @FXML
  public Pane CloakingPane;
  @FXML
  public ComboBox<String> CloakingTerritory;


  @FXML
  private Label totalfood;
  @FXML
  private Label totaltech;

  @FXML
  private Label unit1num;
  @FXML
  private Label unit2num;
  @FXML
  private Label unit3num;
  @FXML
  private Label unit4num;
  @FXML
  private Label unit5num;
  @FXML
  private Label unit6num;
  @FXML
  private Label unit7num;
  @FXML
  private Label unit8num;

  private HashMap<String, Label> UnitNums;

  @FXML
  private Label terrfood;
  @FXML
  private Label terrtech;

  public void setPlayer(TextPlayer player) {
    theTextPlayer = player;
  }

  public void initGame() {
    // Step-1 of playGame()
    theTextPlayer.receiveMap();
    theTextPlayer.receiveResource();
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
    setSourceBox(CloakingTerritory);
    setDestinationBox();
  }

  /**
   * Method to create a list pf all unit type textfield entries
   */
  private void initiateUnitList() {
    UnitTypeEntries = new HashMap<>();
    UnitTypeEntries.put("Guards", Units_A);
    UnitTypeEntries.put("Infantry", Units_B);
    UnitTypeEntries.put("Archer", Units_C);
    UnitTypeEntries.put("Cavalry", Units_D);
    UnitTypeEntries.put("Dwarves", Units_E);
    UnitTypeEntries.put("Orcs", Units_F);
    UnitTypeEntries.put("Elves", Units_G);
    UnitTypeEntries.put("SPY", SPY);
  }

  public void setName() {
    String name = theTextPlayer.identity;
    player_info.setText(name);
  }

  Tooltip playerResourceTooltip;

  /**
   * Method to set the tooltip to view player's resources
   */
  private void setPlayerResourceView() {
    final Tooltip tooltip = new Tooltip();
    String resourceDetails = theTextPlayer.getResourcesDtails();
    tooltip.setText(resourceDetails);
    player_info.setTooltip(tooltip);

    playerResourceTooltip = tooltip;
  }

  /**
   * Method to display player's resources
   */
  private void setPlayerResourceDisplay(){
    int food = theTextPlayer.getTotalFoodResourceAmount();
    int tech = theTextPlayer.getTotalTechResourceAmount();
    totalfood.setText("total Food:" + Integer.toString(food));
    totaltech.setText("total Tech:" + Integer.toString(tech));
  }

  /**
   * Method to update player resources view
   */
  private void updatePlayerResourceView() {
    String resourceDetails = theTextPlayer.getResourcesDtails();
    playerResourceTooltip.setText(resourceDetails);
  }

  /**
   * Method to update player resources display
   */
  private void updatePlayerResourceDisplay(){
    setPlayerResourceDisplay();
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
    source_list.addAll(theTextPlayer.getOthersTerritories());
    
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

  public String getCloakingTerritory(){
    return (String) CloakingTerritory.getValue();
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
    // destination_list.addAll(des_territory_list);
    destination_list.addAll(theTextPlayer.getMyOwnTerritories());
    destination_list.addAll(theTextPlayer.getOthersTerritories());
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
    // upgrade from should be showing units owned by the territory
    UpgradeFrom.setItems(unitType_list);

    // upgrade to should be should be showing all the available levels
    unitType_list.clear();
    unitType_list.addAll(theTextPlayer.theMap.AllUnitTypes);
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
   * 
   * @param indicate which textfield
   */
  public int getUnitNum(TextField UnitType) {
    int enteredVal;
    try {
      enteredVal = Integer.parseInt(UnitType.getText());
    } catch (NumberFormatException e) {
      enteredVal = 0;
    }
    return enteredVal;
  }

  public String getPlayerColor() {
    return theTextPlayer.identity;
  }

  /*
   * Method to check if the user inputs for adding order is valid
   * 
   * @returns true if valid inputs and false otherwise
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
    boolean allZero = false;
    for (String unitType : UnitTypeEntries.keySet()) {
      int unitNum = getUnitNum(UnitTypeEntries.get(unitType));
      if (unitNum == 0) {
        allZero &= true;
      } else {
        allZero &= false;
        if (unitNum < 0) {
          errormessage.appendText(unitType + " Unit number must be positive & greater than zero.");
          result &= false;
        }
      }
    }
    if (allZero) {
      errormessage.appendText("Unit number must be provided for atleast one unit type.");
      result &= false;
    }
    return result;
  }

  @FXML
  void onAddOrderButton(MouseEvent event) {
    boolean result = errorMessageShowing();

    if (result) { // if the inputs are valid
      // Check the type order
      if (getAction().equals("Move")) {
        HashMap<String, Integer> units = getUnitsEntry();
        Turn newOrder = new MoveTurn(getSource(), getDestination(), units, getPlayerColor());
        // newOrder.printTurn();
        myTurn.addTurn(newOrder);
        GameStatus.setText(getAction() + " order from " + getSource() + " to " + getDestination() + " added");
      } else if (getAction().equals("Attack")) {
        HashMap<String, Integer> units = getUnitsEntry();
        Turn newOrder = new AttackTurn(getSource(), getDestination(), units, getPlayerColor());
        // newOrder.printTurn();
        myTurn.addTurn(newOrder);
        GameStatus.setText(getAction() + " order from " + getSource() + " to " + getDestination() + " added");
      } else if (getAction().equals("Upgrade")) {
        // create upgrade
        Turn newOrder = new UpgradeTurn(getUpgradeSource(), getUpgradeFromUnitType(), getUpgradeToUnitType(),
                getUnitNum(UpgradeUnits), getPlayerColor());
        myTurn.addTurn(newOrder);
        GameStatus.setText(getAction() + " order in " + getUpgradeSource() + " from " + getUpgradeFromUnitType()
                + " to " + getUpgradeToUnitType() + " added");
      } else if (getAction().equals("Cloak")) {
        Turn newOrder = new CloakingTurn(getCloakingTerritory(), getPlayerColor());
        myTurn.addTurn(newOrder);
        GameStatus.setText(getAction() + " " + getCloakingTerritory() + " for 3 Turns");
      }
    }
    // theClient.theTextPlayer.takeAndSendTurn();
    System.out.println("Added a New Order");
    clearSelectedAction();

  }

  /**
   * Method to create a hashmap with keys as unit type and value as number of
   * units entered by the player Note the return hashmap includes the unit entry
   * which were valid i.e. non-zero and positive. The returned hashmap cannot be
   * empty as we check for all zero inputs in errorMessageShowing() method
   */
  private HashMap<String, Integer> getUnitsEntry() {
    HashMap<String, Integer> units = new HashMap<>();
    for (String unitType : UnitTypeEntries.keySet()) {
      int unitNum = getUnitNum(UnitTypeEntries.get(unitType));
      // Don't add if there are 0 units
      if (unitNum > 0) {
        units.put(unitType, unitNum);
      }
    }
    return units;
  }

  @FXML
  private void clearSelectedAction() {
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
    theTextPlayer.receiveResource();
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
    // update player's resources tooltip
    // updatePlayerResourceView();
    updatePlayerResourceDisplay();

    // update the tooltips
    // updateTerritoryDetailsView();

    HashMap<String, Territory<Character>> allTerritories = theTextPlayer.theMap.getAllTerritories();
    for (String terrName : TerritoryNames) {
      // Update color of territory
      String player_color = allTerritories.get(terrName).getColor();
      String button_style = "-fx-background-color: rgba(240,240,240,.3)"; // default: white
      if (player_color.equals("Green")) {
        button_style = "-fx-background-color: rgba(60,179,113,.3)";
      } else if (player_color.equals("Blue")) {
        button_style = "-fx-background-color: rgba(0,0,255,.3)";
      }
      TerritoryButtons.get(terrName).setStyle(button_style);
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
      CloakingPane.setVisible(false);

    } else if(selectedAction.equals("Cloak")){
      UpgradePane.setVisible(false);
      MoveAttackPane.setVisible(false);
      CloakingPane.setVisible(true);
    }else if (selectedAction.equals("Move") || selectedAction.equals("Attack")) {
      MoveAttackPane.setVisible(true);
      UpgradePane.setVisible(false);
      CloakingPane.setVisible(false);

    } else {
      MoveAttackPane.setVisible(false);
      UpgradePane.setVisible(false);
      CloakingPane.setVisible(false);

    }
  }

  @FXML
  void onTerrButtonClick(MouseEvent event){
    Button sourceButton = (Button)event.getSource();
    String terrName = sourceButton.getText();

    setTerritoryDetailsDisplay(terrName);
  }

}
