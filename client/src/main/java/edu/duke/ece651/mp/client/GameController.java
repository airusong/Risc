package edu.duke.ece651.mp.client;

import edu.duke.ece651.mp.common.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;

public class GameController {

    // Text fields to show player color
    @FXML private TextField player_info;

    // Stack panes holding all territory elements
    @FXML private StackPane Territory1;
    @FXML private StackPane Territory2;
    @FXML private StackPane Territory3;
    @FXML private StackPane Territory4;
    @FXML private StackPane Territory5;
    @FXML private StackPane Territory6;

    // Nodes for rectangular boxes in the map representing territories
    @FXML private Node Terr1Box;
    @FXML private Node Terr2Box;
    @FXML private Node Terr3Box;
    @FXML private Node Terr4Box;
    @FXML private Node Terr5Box;
    @FXML private Node Terr6Box;

    // Text fields to show territory names in map
    @FXML private TextField Terr1Name;
    @FXML private TextField Terr2Name;
    @FXML private TextField Terr3Name;
    @FXML private TextField Terr4Name;
    @FXML private TextField Terr5Name;
    @FXML private TextField Terr6Name;

    // Text fields to show territory units
    @FXML private TextField Terr1Units;
    @FXML private TextField Terr2Units;
    @FXML private TextField Terr3Units;
    @FXML private TextField Terr4Units;
    @FXML private TextField Terr5Units;
    @FXML private TextField Terr6Units;

    public void setName(String name){
        player_info.setText(name);
    }

    /**
     * Method to setup the map in the UI
     * @param TextPlayer
     */
    public void setUpMap(TextPlayer player) {
        Map<T> initialMap = player.theMap;
    }

}
