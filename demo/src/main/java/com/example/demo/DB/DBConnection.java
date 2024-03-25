package com.example.demo.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class DBConnection {

    public static Connection dbConnection(){
        try{
            Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/Intel","root","root");
            return connect;
            //System.out.println("Connection success");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static void changeScene( String title, String fxmlFile){
        try {
            Parent root = null;
            root = FXMLLoader.load(DBConnection.class.getResource(fxmlFile));
            Stage stage = new Stage();
            stage.setTitle(title);
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
