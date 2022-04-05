package edu.duke.ece651.mp.client;

import edu.duke.ece651.mp.common.AttackTurn;
import edu.duke.ece651.mp.common.MoveTurn;
import edu.duke.ece651.mp.common.Turn;
import edu.duke.ece651.mp.common.TurnList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class GameController{

    private TextPlayer theTextPlayer;
    ObservableList<String> playeraction_list = FXCollections.observableArrayList("Move","Attack","Upgrade");
    ObservableList<String> source_list = FXCollections.observableArrayList();
    ObservableList<String> destination_list = FXCollections.observableArrayList();

    TurnList myTurn = new TurnList();


    @FXML
    private Label player_info;
    @FXML
    private ComboBox<String> playeraction;
    @FXML
    private ComboBox<String> from;
    @FXML
    private ComboBox<String> to;
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
        System.out.println("THe name of player is:" + name);
        player_info.setText(name);
    }

    @FXML
    public void setActionBox(){
        playeraction.setValue("Move");
        playeraction.setItems(playeraction_list);
        playeraction.setPromptText("Please select a Action.");
        playeraction.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                setSourceBox();
                setDestinationBox();
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
        from.setPromptText("From Territory.");
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
        to.setPromptText("To Territory.");
    }

    public String getDestination(){
        return (String) to.getValue();
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
            Turn newOrder = new MoveTurn(getSource(), getDestination(),getUnitNum(),getPlayerColor());
            myTurn.addTurn(newOrder);
        }
        else if(getAction().equals("Attack")){
            Turn newOrder = new AttackTurn(getSource(), getDestination(),getUnitNum(),getPlayerColor());
            myTurn.addTurn(newOrder);
        }
        // theClient.theTextPlayer.takeAndSendTurn();
        System.out.println("Add a New Order");
    }

    @FXML
    void onCommitButton(MouseEvent event) {
        // send TurnList to Server
        theTextPlayer.connectionToMaster.sendToServer(myTurn);
        // empty TurnList
        myTurn = new TurnList();
        System.out.println("Send the TurnList");

        // Disable the Button
        order.setDisable(true);
        commit.setDisable(true);

        // receive turn status
        ArrayList<String> turnResult = theTextPlayer.receiveTurnStatus();
        // display the turn status in UI

        // receive updated map
        theTextPlayer.receiveMap();

    }

      /*
      // TO Do: bind it with the game status Box
      // recieve game status
      String gamestatus = theTextPlayer.receiveAndPrintGameStatus();
        if(gamestatus.startsWith("Ready")){
        order.setDisable(false);
        commit.setDisable(false);
      }
      */

}