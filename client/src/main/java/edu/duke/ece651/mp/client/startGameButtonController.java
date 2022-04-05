package edu.duke.ece651.mp.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class startGameButtonController {

    @FXML
    private Button startGame;


    @FXML
    void onStartGameButton(MouseEvent event) {

        try {
            // build Connection with Server
            System.out.println("Welcome to our game!");
            int port = 8080;
            String servername = "127.0.0.1";
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            Client theClient = new Client(servername, port, input, System.out);
            theClient.theTextPlayer.initiateGame();
            System.out.println("Successfully connect to Server!");

            // switch to gamepage.fxml;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/gamepage.fxml"));
                String player_color = theClient.theTextPlayer.identity;
                System.out.println("The player's color is: " + player_color);
                //primaryStage.setTitle("RISC GAME")
                AnchorPane root = (AnchorPane) loader.load();

                GameController gameController = loader.getController();

                gameController.setPlayer(theClient.theTextPlayer);
                gameController.initGame();
                gameController.setUpMap(theClient.theTextPlayer);

                startGame.getScene().setRoot(root);

            }catch(Exception e){
                e.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}


