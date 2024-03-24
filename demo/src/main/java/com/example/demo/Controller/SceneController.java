package com.example.demo.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {

//    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
//        try {
//            // Load the FXML file
//            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFile));
//            Parent root = loader.load();
//
//            // Create a new scene
//            Scene scene = new Scene(root);
//
//            // Get the stage from the event
//            Stage stage = (Stage) event.getSource();
//
//            // Set the new scene and title
//            stage.setScene(scene);
//            stage.setTitle(title);
//
//            // Show the stage with the new scene
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public static void changeScene(String title, String fxmlFile){
        try{
            FXMLLoader loader = new FXMLLoader(SceneController.class.getResource(fxmlFile));
            Parent root = loader.load();
           // Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
