package edu.duke.ece651.mp.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ButtonController {

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
            System.out.println("Before initiate the Game");
            theClient.theTextPlayer.initiateGame();
            System.out.println("Successfully connect to Server!");

            // display map


        }catch(Exception e){
            e.printStackTrace();
        }



    }

}


