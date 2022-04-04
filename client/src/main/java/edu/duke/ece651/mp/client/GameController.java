package edu.duke.ece651.mp.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    public ChoiceBox<String> playeraction=new ChoiceBox<>();
    @FXML
    public MenuButton from;
    @FXML
    public MenuButton to;
    @FXML
    public Button commit;
    @FXML
    public TextField unit;
    @FXML
    public Button order;
    @FXML
    private TextField player_info;

    public void setName(String name){
        player_info.setText(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        playeraction.getItems().add("move");
        playeraction.getItems().add("attack");
        playeraction.getItems().add("Upgrade");
    }

}
