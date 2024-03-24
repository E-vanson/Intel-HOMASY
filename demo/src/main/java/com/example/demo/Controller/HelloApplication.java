package com.example.demo.Controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//import org.w3c.dom.events.MouseEvent;
import javafx.stage.StageStyle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class HelloApplication extends Application {

//    private double x = 0;
//    private double y = 0;
    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlUrl = getClass().getResource("/com/example/demo/Views/signup.fxml");
        System.out.println("FXML file location: " + fxmlUrl);
        if (fxmlUrl == null) {
            throw new FileNotFoundException("FXML file not found");
        }

        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root);


        stage.initStyle(StageStyle.TRANSPARENT);

        stage.setTitle("LogIn");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
