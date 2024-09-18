package org.zakco.tp03_2048;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("2048.fxml"));

        // Question 23 Instance de la facade de jeu


        Scene scene = new Scene(fxmlLoader.load(), 425, 800);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        // On recupere le controleur instancié par le FXMLLoader
        HelloController controller = fxmlLoader.getController();

        //  Question 24 -> Appel de la methode initialiser
        controller.initializeGame();
    }

    public static void main(String[] args) {
        launch();
    }
}