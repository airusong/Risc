package edu.duke.ece651.mp.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class startpage extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //String javaVersion = System.getProperty("java.version");
        //String javafxVersion = System.getProperty("javafx.version");
        //Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        URL xmlResource = getClass().getResource("/ui/firstpage.fxml");
        AnchorPane gp = FXMLLoader.load(xmlResource);
        Scene scene = new Scene(gp, 640, 480);
        stage.setScene(scene);
        stage.show();

        /*
        Stage stage2 = new Stage();
        URL xmlResource2 = getClass().getResource("/ui/gamepage.fxml");
        AnchorPane gp2 = FXMLLoader.load(xmlResource2);
        Scene scene2 = new Scene(gp2, 640, 480);
        stage2.setScene(scene2);
        stage2.show();
        */

    }
}
