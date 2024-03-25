package com.example.demo.Controller;

import com.example.demo.DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LogInController implements Initializable {
    @FXML
    private Button close_btn;

    @FXML
    private ImageView image;

    @FXML
    private AnchorPane left_form;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password;

    @FXML
    private AnchorPane right_form;

    @FXML
    private Button signup_btn;

    @FXML
    private TextField username;


    //sql tools
    private Connection connection;
    private PreparedStatement prepare;
    private ResultSet resultSet;

    static String storedName = "";


    //Login user
    public void loginUser(){
        Alert alert;


        String sql = "SELECT * FROM users WHERE username = ? and password = ?";
        connection = DBConnection.dbConnection();

        try{
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, username.getText());
            prepare.setString(2, password.getText());

            resultSet = prepare.executeQuery();

            //check if any field is empty
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please Fill in All Fields");
                alert.show();
            }else{
                //return true if there is a matching record
                if(resultSet.next()){
                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Login!");
                    alert.showAndWait();
                    AuthController.authorise(username.getText(), password.getText());
                    storedName = username.getText();

                  try {
                        login_btn.getScene().getWindow().hide();
                        URL fxmlUrl = getClass().getResource("/com/example/demo/Views/dashboard.fxml");
                        Parent root = FXMLLoader.load(fxmlUrl);
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                   }catch (Exception e){
                       e.printStackTrace();
                    }

                }else{
                    //show error
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid Details");
                    alert.show();
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if(resultSet != null){
                try{
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(prepare != null){
                try{
                    prepare.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection != null){
                try{
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static String storeUser(){
        String name = storedName;
        return name;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    //close window method
    public void close(){
        System.exit(0);
    }
}
