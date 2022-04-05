package edu.duke.ece651.mp.client;

import edu.duke.ece651.mp.common.AttackTurn;
import edu.duke.ece651.mp.common.MoveTurn;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GameController{

    private TextPlayer theTextPlayer;
    ObservableList<String> playeraction_list = FXCollections.observableArrayList("Move","Attack","Upgrade");
    ObservableList<String> source_list = FXCollections.observableArrayList();
    ObservableList<String> destination_list = FXCollections.observableArrayList();
    ObservableList<String> unitType_list = FXCollections.observableArrayList();

    TurnList myTurn = new TurnList();


    @FXML
    private TextField player_info;
    @FXML
    private ChoiceBox<String> playeraction;
    @FXML
    private ChoiceBox<String> from;
    @FXML
    private ChoiceBox<String> to;
    @FXML
    private ChoiceBox<String> unitType;
    @FXML
    private TextField unit;
    @FXML
    private Button order;
    @FXML
    private Button commit;


    public void setPlayer(TextPlayer player){
        theTextPlayer = player;
    }

    public void initGame() {
        theTextPlayer.receiveMap();

        setName();
        setActionBox();
        setSourceBox();
        setDestinationBox();
    }

    public void setName(){
        String name = theTextPlayer.identity;
        player_info.setText(name);
    }

    @FXML
    public void setActionBox(){
        playeraction.setValue("Move");
        playeraction.setItems(playeraction_list);
        playeraction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setSourceBox();
                setDestinationBox();
                setUnitType();
            }
        });
    }

    public String getAction(){
        return (String) playeraction.getValue();
    }

    @FXML
    public void setSourceBox(){
        ArrayList<String> own_territory_list =  theTextPlayer.getMyOwnTerritories();
        source_list.clear();
        source_list.addAll(own_territory_list);
        from.setItems(source_list);
    }

    public String getSource(){
        return (String) from.getValue();
    }

    @FXML
    public void setDestinationBox(){
        ArrayList<String> des_territory_list = new ArrayList<String>();
        if(getAction().equals("Move")) {
            des_territory_list = theTextPlayer.getMyOwnTerritories();
        }
        else if(getAction().equals("Attack")){
            des_territory_list = theTextPlayer.getOthersTerritories();
        }
        destination_list.clear();
        destination_list.addAll(des_territory_list);
        to.setItems(destination_list);
    }

    public String getDestination(){
        return (String) to.getValue();
    }

    public void setUnitType(){
        String curTerritory = getSource();
        ArrayList<Unit> own_unitType_list = theTextPlayer.getTerritoryUnitList();
        unitType_list.clear();
        unitType_list.addAll(own_unitType_list);
        from.setItems(source_list);
    }

    public String getUnitType(){
        return (String)unitType.getValue();
    }

    public int getUnitNum(){
        return Integer.parseInt(unit.getText());
    }

    public String getPlayerColor(){
        return theTextPlayer.identity;
    }

    @FXML
    void onAddOrderButton(MouseEvent event){
        if(getAction().equals("Move") || getAction().equals("Upgrade")) {
            Turn newOrder = new MoveTurn(getSource(), getDestination(), getUnitType(), getUnitNum(),getPlayerColor());
            myTurn.addTurn(newOrder);
        }
        else if(getAction().equals("Attack")){
            Turn newOrder = new AttackTurn(getSource(), getDestination(), getUnitType(),getUnitNum(),getPlayerColor());
            myTurn.addTurn(newOrder);
        }
        // theClient.theTextPlayer.takeAndSendTurn();
        System.out.println("Add a New Order");
    }

    @FXML
    void onCommitButton(MouseEvent event){
        // send TurnList to Server
        theTextPlayer.connectionToMaster.sendToServer(myTurn);
        System.out.println("Send the TurnList");
    }


}