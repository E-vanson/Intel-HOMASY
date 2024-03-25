package com.example.demo.Controller;

import com.example.demo.DB.DBConnection;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

public class SignUpController implements Initializable {
    @FXML
    private ImageView close_btn;

    @FXML
    private  TextField email_tfield;

    @FXML
    private   TextField emergency_contact_tfield;

    @FXML
    private   RadioButton female_radioBtn;

    @FXML
    private  Button login_btn;

    @FXML
    private   RadioButton male_radioBtn;

    @FXML
    private   RadioButton other_radioBtn;

    @FXML
    private   PasswordField password_tfield;

    @FXML
    private   TextField phoneNo_tfield;

    @FXML
    private  TextField regNo_tfield;

    @FXML
    private  Button signup_btn;

    @FXML
    private  TextField username_tfield;

    @FXML
    private   Button viewHostels_btn;

    private  PreparedStatement preparedStatement;

    private  PreparedStatement prepare;
    private  Connection connection;
    private  ResultSet resultSet;

    public  void signUp(ActionEvent event,String regNo,String username, String email, String phoneNo, String password, String emergencyContact, String gender){
        Alert alert;
        String insertUser = "INSERT INTO users (regNo,username, email, phoneNo, password, emergencyContact, gender) VALUES (?, ?, ?, ?, ?, ?, ?)";
        String checkUserExists = "SELECT * FROM users WHERE username = ? AND regNo = ?";
        connection = DBConnection.dbConnection();

        try{
            preparedStatement = connection.prepareStatement(checkUserExists);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, regNo);
            resultSet = preparedStatement.executeQuery();

            if(resultSet.isBeforeFirst()){
                System.out.println("User Exists");
                 alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Can't use the username");
                alert.show();
            }else{
                prepare = connection.prepareStatement(insertUser);
                prepare.setString(1,regNo);
                prepare.setString(2,username);
                prepare.setString(3,email);
                prepare.setString(4,phoneNo);
                prepare.setString(5,password);
                prepare.setString(6,emergencyContact);
                prepare.setString(7,gender);

                int rowsAffected = prepare.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("User inserted successfully");
                    // Optionally, you can show a success message here
                } else {
                    System.out.println("Failed to insert user");
                    // Optionally, you can show an error message here
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                if(resultSet != null){resultSet.close();}
                if(prepare != null){prepare.close();}
                if(preparedStatement != null){preparedStatement.close();}
                if(connection != null){connection.close();}
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void toLogin(){
        login_btn.getScene().getWindow().hide();
        SceneController.changeScene("Login", "/com/example/demo/Views/login.fxml");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ToggleGroup toggleGroup = new ToggleGroup();
        male_radioBtn.setToggleGroup(toggleGroup);
        female_radioBtn.setToggleGroup(toggleGroup);
        other_radioBtn.setToggleGroup(toggleGroup);

        male_radioBtn.setSelected(true);

        signup_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName = ((RadioButton) toggleGroup.getSelectedToggle()).getText();

                if(!regNo_tfield.getText().trim().isEmpty()
                && !username_tfield.getText().trim().isEmpty()
                && !email_tfield.getText().trim().isEmpty()
                && !password_tfield.getText().trim().isEmpty()
                && !phoneNo_tfield.getText().trim().isEmpty()
                && !emergency_contact_tfield.getText().trim().isEmpty()){
                    signUp(event,regNo_tfield.getText(),
                            username_tfield.getText(),
                            email_tfield.getText(),
                            phoneNo_tfield.getText(),
                            password_tfield.getText(),
                            emergency_contact_tfield.getText(),
                            toggleName);
                    signup_btn.getScene().getWindow().hide();
                    SceneController.changeScene("Dashboard", "/com/example/demo/Views/login.fxml");
                }else{
                    System.out.println("Fill in all info");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Fill in all fields");
                    alert.show();
                }
                SceneController.changeScene("Dashboard", "/com/example/demo/Views/login.fxml");
            }
        });

        viewHostels_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                viewHostels(event);
            }
        });
    }

    public void viewHostels(ActionEvent event){
        viewHostels_btn.getScene().getWindow().hide();
        SceneController.changeScene("Dashboard", "/com/example/demo/Views/dashboard.fxml");
    }
}
