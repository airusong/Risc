package edu.duke.ece651.mp.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class GameController {

    @FXML
    private TextField player_info;

    public void setName(String name){
        player_info.setText(name);
    }

}
