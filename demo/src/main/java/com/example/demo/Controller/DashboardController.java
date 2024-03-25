package com.example.demo.Controller;

import com.example.demo.DB.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class DashboardController implements Initializable {
    @FXML
    private Button book_btn;

    @FXML
    private TableColumn<RoomData, String> book_description_col;

    @FXML
    private TableColumn<RoomData, Integer> book_index_col;

    @FXML
    private TextField book_index_tfield;

    @FXML
    private Label book_pane;

    @FXML
    private TableColumn<RoomData, Integer> book_price_col;

    @FXML
    private TextField book_price_tfield;

    @FXML
    private TableColumn<RoomData, String> book_roomNo_col;

    @FXML
    private TextField book_roomNo_tfield;


    @FXML
    private AnchorPane book_room_form;

    @FXML
    private TextField book_search;

    @FXML
    private TableColumn<RoomData, Boolean> book_status_col;

    @FXML
    private TableView<RoomData> book_table;

    @FXML
    private TableView<?> center_table_pane;

    @FXML
    private Button feedBack_btn;

    @FXML
    private AnchorPane finance_form;

    @FXML
    private TableColumn<?, ?> finance_amountPaid_clmn;

    @FXML
    private TableColumn<?, ?> finance_balance_clmn;

    @FXML
    private TableColumn<?, ?> finance_data_clmn;

    @FXML
    private TableColumn<?, ?> finance_index_clmn;

    @FXML
    private TableColumn<?, ?> finance_price_clmn;

    @FXML
    private TableColumn<?, ?> finance_roomNo_clmn;

    @FXML
    private Button finances_btn;

    @FXML
    private Button home_btn;

    @FXML
    private AnchorPane info_form;

    @FXML
    private AnchorPane left_inner_pane;

    @FXML
    private AnchorPane left_pane;

    @FXML
    private Button notifications_btn;

    @FXML
    private AnchorPane profile_form;

    @FXML
    private TextArea profile_addBio;

    @FXML
    private Label profile_bio;

    @FXML
    private Button profile_btn;

    @FXML
    private TextField profile_change_username;

    @FXML
    private ImageView profile_img;

    @FXML
    private Label profile_months;

    @FXML
    private Label profile_roomNo;

    @FXML
    private Button profile_save_btn;

    @FXML
    private Label profile_show_bio;

    @FXML
    private Label profile_show_months;

    @FXML
    private Label profile_show_room;

    @FXML
    private Button rateUs_btn;

    @FXML
    private Label profile_username;


    @FXML
    private Button signOut_btn;

    @FXML
    private Label username_label;

    @FXML
    private Label welcome_label;

    private Connection connection;
    private PreparedStatement preparedStatement;

    private Statement statement;

    private ResultSet resultSet;

    public void close(){
        System.exit(0);
    }

    public void minimise(){

    }

    public void switchForm(ActionEvent event){
        if(event.getSource() == home_btn){
            book_room_form.setVisible(true);
            info_form.setVisible(false);
            finance_form.setVisible(false);
            profile_form.setVisible(false);

            showRoomsList();
        }else if(event.getSource() == finances_btn){
            finance_form.setVisible(true);
            info_form.setVisible(false);
            book_room_form.setVisible(false);
            profile_form.setVisible(false);
        }else if(event.getSource() == profile_btn){
            profile_form.setVisible(true);
            info_form.setVisible(false);
            finance_form.setVisible(false);
           book_room_form.setVisible(false);
        }else if(event.getSource() == notifications_btn){
            info_form.setVisible(true);
            book_room_form.setVisible(false);
            finance_form.setVisible(false);
            profile_form.setVisible(false);
        }
    }

    public void logout() {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText("LogOut");
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                AuthController.deAuthorise(LogInController.storeUser());

                //HIDE YOUR DASHBOARD FORM
                signOut_btn.getScene().getWindow().hide();
                SceneController.changeScene("Login", "/com/example/demo/Views/login.fxml");

            } else {
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //populates
    public ObservableList<RoomData> addRoomData(){
        ObservableList<RoomData> listRooms = FXCollections.observableArrayList();

        String sql = "SELECT * FROM rooms";
        connection = DBConnection.dbConnection();

        try{
            RoomData roomData;
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                roomData = new RoomData(resultSet.getInt("id"),
                        resultSet.getString("roomNo"),
                        resultSet.getInt("price"),
                        resultSet.getBoolean("status"),
                        resultSet.getString("description"),
                        resultSet.getInt("tenant"));

                listRooms.add(roomData);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return listRooms;
    }

    public ObservableList<RoomData> showRoomList;

    public void showRoomsList(){
        showRoomList = addRoomData();
        book_index_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        book_roomNo_col.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        book_status_col.setCellValueFactory(new PropertyValueFactory<>("status"));
        book_description_col.setCellValueFactory(new PropertyValueFactory<>("description"));
        book_price_col.setCellValueFactory(new PropertyValueFactory<>("price"));

        book_table.setItems(showRoomList);
    }

    public void bookRoomSelect(){
        RoomData roomData = book_table.getSelectionModel().getSelectedItem();
        int index = book_table.getSelectionModel().getSelectedIndex();
         if((index - 1 ) < -1){return; }

         book_index_tfield.setText(String.valueOf(roomData.getId()));
         book_roomNo_tfield.setText(roomData.getRoomNo());
         book_price_tfield.setText(String.valueOf(roomData.getPrice()));
    }

    public static PreparedStatement pStatement;
    private String roomNo;
    int userId;

    AtomicReference<String> amount = new AtomicReference<>("");
    public void bookRoom(){
        AuthController.isAuthorized();
        if(AuthController.isAuthorized()){
            System.out.println("User can book");
            Alert alert;
            //Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setTitle("Book");
//            alert.setContentText(null);
//            alert.show();


            String user = LogInController.storeUser();
            String squeryRoom = "SELECT roomNo FROM rooms WHERE id = ?";
            String updateRoom = "UPDATE rooms SET tenant = ?, status = ? WHERE id = ?";
            String squeryUser = "SELECT id FROM users WHERE userName = ?";
            connection = DBConnection.dbConnection();

            try{
                preparedStatement = connection.prepareStatement(squeryRoom);
                preparedStatement.setString(1, book_index_tfield.getText());
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    roomNo = resultSet.getString("roomNo");
                }else{
                     alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("The room doesN't exist");
                    alert.show();
                }

                preparedStatement = connection.prepareStatement(squeryUser);
                preparedStatement.setString(1, user);
                resultSet = preparedStatement.executeQuery();
                if(resultSet.next()){
                    userId = resultSet.getInt("id");
                }

                book_btn.setOnAction(event -> {
                    TextInputDialog dialog = new TextInputDialog();
                    Alert alert1;
                    dialog.setTitle("Input Dialog");
                    dialog.setHeaderText("Please enter amount:");
                    dialog.setContentText("Amount:");

                    // Show the dialog and wait for the user's response
                    Optional<String> result = dialog.showAndWait();

                    // Process the user's input
                    result.ifPresent(value -> {
                        // Do something with the value entered by the user
                        System.out.println("User entered: " + value);
                        // Here, you can store the value in a variable or use it as needed
                        amount.set(value);
                    });
                    alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setTitle("Booked!!");
                    alert1.setContentText("You have successfully booked a room");
                    alert1.show();
                });

                preparedStatement = connection.prepareStatement(updateRoom);
                preparedStatement.setInt(1, userId);
                preparedStatement.setBoolean(2, true);
                preparedStatement.setInt(3, Integer.parseInt(book_index_tfield.getText()));

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Deauthorisation status updated successfully for room: " + roomNo);
                } else {
                    System.out.println("Failed to update deauthorisation status for room: " + roomNo);
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
        }else{
            System.out.println("User can not book");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(null);
            alert.show();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showRoomsList();
        username_label.setText(LogInController.storeUser());
        profile_username.setText(LogInController.storeUser());
        profile_roomNo.setText(roomNo);
    }
}
