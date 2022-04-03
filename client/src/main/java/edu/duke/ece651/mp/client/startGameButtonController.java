package edu.duke.ece651.mp.client;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private TextField player_info;

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

            // switch to gamepage.fxml
            Stage primaryStage = (Stage)startGame.getScene().getWindow();
            // primaryStage.hide();

            try {
                URL xmlResource = getClass().getResource("/ui/gamepage.fxml");
                String player_color = theClient.theTextPlayer.identity;
                System.out.println("The player's color is: " + player_color);
                // player_info.setText(player_color);

                primaryStage.setTitle("RISC GAME");
                AnchorPane gp = FXMLLoader.load(xmlResource);
                Scene scene = new Scene(gp, 640, 480);
                primaryStage.setScene(scene);
                primaryStage.show();


            }catch(Exception e){
                e.printStackTrace();
            }

        }catch(Exception e){
            e.printStackTrace();
        }






    }





}


