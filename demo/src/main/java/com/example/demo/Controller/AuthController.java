package com.example.demo.Controller;

import com.example.demo.DB.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AuthController implements Initializable {
//    @FXML
//    //private static PasswordField password;

//    @FXML
//    private static TextField username;


    public static Connection connection;
    private static PreparedStatement preparedStatement;

    private static ResultSet resultSet;

    public static void authorise(String username, String password) {
        String sql = "SELECT authorized FROM users WHERE username = ? and password = ?";
        String sql1 = "UPDATE users SET authorized = true WHERE username = ?";
        connection = DBConnection.dbConnection();
        boolean isAuthorized = false;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                isAuthorized = resultSet.getBoolean("authorized");
            }
            if(!isAuthorized){
                isAuthorized = true;
            }

            preparedStatement = connection.prepareStatement(sql1);
            preparedStatement.setString(1, username);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Authorization status updated successfully for user: " + username);
            } else {
                System.out.println("Failed to update authorization status for user: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){resultSet.close();}
                if(preparedStatement != null){preparedStatement.close();}
                if(connection != null){connection.close();}
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void deAuthorise(String username) {
        String sql = "UPDATE users SET authorized = false WHERE username = ?";
        connection = DBConnection.dbConnection();

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Deauthorisation status updated successfully for user: " + username);
            } else {
                System.out.println("Failed to update deauthorisation status for user: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (preparedStatement != null) { preparedStatement.close(); }
                if (connection != null) { connection.close(); }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isAuthorized(){
        boolean isAuthorized = false;

        String squery = "SELECT authorized FROM users WHERE username = ?";
        connection = DBConnection.dbConnection();

        try{
            preparedStatement = connection.prepareStatement(squery);
            preparedStatement.setString(1,LogInController.storeUser());
            resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                isAuthorized = resultSet.getBoolean("authorized");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isAuthorized;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
