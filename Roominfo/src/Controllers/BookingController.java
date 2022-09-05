/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import roominfo.Tenant_list;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class BookingController implements Initializable {

    @FXML
    private JFXTextField search_tf;
    @FXML
    private Label guest_id;
    @FXML
    private Label guest_name;
    @FXML
    private JFXDatePicker check_in_date;
    @FXML
    private JFXDatePicker check_out_date;
    @FXML
    private JFXComboBox<String> room_num = new JFXComboBox<>();
    @FXML
    private JFXComboBox<String> no_guest = new JFXComboBox<>();
    @FXML
    private JFXComboBox<String> room_type_cb = new JFXComboBox<>();
    @FXML
    private TableView<Tenant_list> guest_table;
    @FXML
    private TableColumn<Tenant_list, String> g_id;
    @FXML
    private TableColumn<Tenant_list, String> g_name;
    @FXML
    private TableColumn<Tenant_list, String> g_mobile;

    ObservableList<Tenant_list> Guest = FXCollections.observableArrayList();
    Connection connection;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //guest number add
        no_guest.getItems().add("01");
        no_guest.getItems().add("02");
        no_guest.getItems().add("03");

        //check-in date picker
        check_in_date.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(LocalDate.now()) < 0);
            }
        });

        //check out date disable
        check_out_date.setDisable(true);

        //room type disable
        room_type_cb.setDisable(true);

        //room number disable
        room_num.setDisable(true);

        //room type information from database
        try {
            room_type_selection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        // TODO
    }   
    
    

    
    
    @FXML
    private void set_guest_info(ActionEvent event) {
         if (guest_table.getSelectionModel().getSelectedItem() != null) {

            Tenant_list data = guest_table.getSelectionModel().getSelectedItem();
            guest_id.setText(String.valueOf(data.getID()));
            guest_name.setText(data.getName());

        } else {
            JOptionPane.showMessageDialog(null, "Please Select a Guest");
        }
    }

    @FXML
    private void room_type_action(ActionEvent event) throws ClassNotFoundException, SQLException {
        room_num.setValue(null);
        room_num.setDisable(false);
        room_num.getItems().clear();

        if (check_in_date.getValue() != null && check_out_date.getValue() != null && room_type_cb.getValue() != null) {

            DBConnect();
            String qu;
            String room_query;

            if (room_type_cb.getValue().equalsIgnoreCase("Single bedroom")) {
                qu = "SELECT Singlebedroom.room_id FROM Singlebedroom LEFT JOIN Booking ON Singlebedroom.room_id=Booking.Room_id"
                        + " WHERE Singlebedroom.room_status='vacant' OR (Booking.Check_in<='" + Date.valueOf(LocalDate.now())
                        + "' AND Booking.Check_out<='" + Date.valueOf(check_in_date.getValue()) + "' AND Booking.booking_status='active') OR ('"
                        + Date.valueOf(LocalDate.now()) + "'< Booking.Check_in AND ('" + Date.valueOf(check_out_date.getValue())
                        + "'<= Booking.Check_in OR Booking.Check_out <='" + Date.valueOf(check_in_date.getValue()) + "') AND Booking.booking_status='active')"
                        + "GROUP BY (Singlebedroom.room_id)";
                            room_query = "use HouseRent;UPDATE room_tab SET booked_room=booked_room+1, vacant_room=vacant_room-1 WHERE room_type='Single bedroom'";
            } else {
                qu = "SELECT Doublebedroom.room_id FROM Doublebedroom LEFT JOIN Booking ON Doublebedroom.room_id=Booking.Room_id"
                        + " WHERE Doublebedroom.room_status='vacant' OR (Booking.Check_in<='" + Date.valueOf(LocalDate.now())
                        + "' AND Booking.Check_out<='" + Date.valueOf(check_in_date.getValue()) + "' AND Booking.booking_status='active') OR ('"
                        + Date.valueOf(LocalDate.now()) + "'< Booking.Check_in AND ('" + Date.valueOf(check_out_date.getValue())
                        + "'<= Booking.Check_in OR Booking.Check_out <='" + Date.valueOf(check_in_date.getValue()) + "') AND Booking.booking_status='active')"
                        + "GROUP BY (Doublebedroom.room_id)";
                 room_query = "use HouseRent;UPDATE room_tab SET booked_room=booked_room+1, vacant_room=vacant_room-1 WHERE room_type='Double bedroom'";
            }

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(qu);
             Statement updaterm=connection.createStatement();
             updaterm.executeUpdate(room_query);

            while (rs.next()) {
                room_num.getItems().add(String.valueOf(rs.getInt("room_id")));
                //System.out.println("Working");
            }
            connection.close();
        }

    }

    @FXML
    private void check_in_date_action(ActionEvent event) {
         check_out_date.setValue(null);
        check_out_date.setDisable(false);

        LocalDate chk_in_date = check_in_date.getValue();

        check_out_date.setDayCellFactory(param -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.compareTo(chk_in_date) <= 0);
            }
        });

        //reset room_type
        room_type_cb.setValue(null);
        room_type_cb.setDisable(true);

        //reset room_id
        room_num.setValue(null);
        room_num.setDisable(true);
 
    }

    @FXML
    private void check_out_action(ActionEvent event) {
         room_type_cb.setDisable(false);
        room_type_cb.setValue(null);

        //reset room_id
        room_num.setValue(null);
        room_num.setDisable(true);

    }

    @FXML
    private void submit_action(ActionEvent event) {
         if (!(guest_id.getText().isEmpty()) && check_in_date.getValue() != null && check_out_date.getValue() != null) {

            int rand = ThreadLocalRandom.current().nextInt(100, 999 + 1);
            String randomNum = Integer.toString(rand);

            SimpleDateFormat formatter = new SimpleDateFormat("yyMdd");
            java.util.Date currDate = Calendar.getInstance().getTime();
            String date = formatter.format(currDate);

            String num = date + randomNum;
            int book_num = Integer.parseInt(num);

            //insert value into booking table
            String query = "INSERT INTO Booking (Booking_id,Tenant_Id,Check_in,Check_out,Guest_num,Room_type,Room_id,booking_status)"
                    + " values(" + book_num + "," + Integer.parseInt(guest_id.getText()) + ",'" + Date.valueOf(check_in_date.getValue())
                    + "','" + Date.valueOf(check_out_date.getValue()) + "'," + Integer.parseInt(no_guest.getValue()) + ",'" + room_type_cb.getValue() + "',"
                    + Integer.parseInt(room_num.getValue()) + ", 'active')";
      
            try {
                DBConnect();
                Statement stmt = connection.createStatement();
                stmt.executeUpdate(query);

                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Booking Complete");
                a.show();
                
                Guest.clear();
                guest_table.getItems().removeAll();
                guest_table.setItems(Guest);

                room_type_cb.setValue(null);
                no_guest.setValue(null);
                room_num.setValue(null);
                check_out_date.setValue(null);
                check_in_date.setValue(null);

                guest_name.setText("");
                guest_id.setText("");
                guest_id.setText("");
                search_tf.setText("");

                //System.out.println(query);
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(ex.getMessage());
                a.show();
            }

            //update the room status in one_bedroom OR two_bedroom villa
            String que;

            if (room_type_cb.getValue().equalsIgnoreCase("one bedroom villa")) {
                que = "UPDATE Singlebedroom SET room_status='booked' WHERE room_id= " + Integer.parseInt(room_num.getValue());
            } else {
                que = "UPDATE Doublebedroom SET room_status='booked' WHERE room_id= " + Integer.parseInt(room_num.getValue());
            }

            try {
                Statement updStm = connection.createStatement();
                updStm.executeUpdate(que);

            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(ex.getMessage());
                a.show();
            }

            // System.out.println(que);
        } else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Invalid");
            a.show();
        }
        
       
    }
    void room_type_selection() throws ClassNotFoundException, SQLException {

        DBConnect();
        Statement stm = connection.createStatement();
        String query = "SELECT room_type FROM room_tab";
        ResultSet rs = stm.executeQuery(query);

        while (rs.next()) {
            room_type_cb.getItems().add(rs.getString("room_type"));
        }
        connection.close();
    }

    //for db connect
    void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=HouseRent;";
        connection = DriverManager.getConnection(URL);
    }

    @FXML
    private void search(ActionEvent event) throws ClassNotFoundException, SQLException {
        Guest.clear();
        guest_table.getItems().removeAll();
        guest_table.setItems(Guest);
        String search_Value = search_tf.getText();

        if (!(search_Value.isEmpty())) {
            DBConnect();
            String query = "SELECT Tenant_Id, Tenant_Name, Tenant_Phone FROM Tenant WHERE Tenant_Id LIKE '%" + search_Value + "%'"
                    + "OR Tenant_Name LIKE '%" + search_Value + "%' OR Tenant_Phone LIKE '%" + search_Value + "%'";

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
               Guest.add(new Tenant_list(rs.getInt("Tenant_Id"), rs.getString("Tenant_Name"), rs.getString("Tenant_Phone")));
               //Guest.add(new Tenant_list (rs.getInt("Tenant_Id"),rs.getString("Tenant Name"),rs.getString("Tenant_Phone")));
            }

            g_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
            g_name.setCellValueFactory(new PropertyValueFactory<>("name"));
            g_mobile.setCellValueFactory(new PropertyValueFactory<>("phone"));

            guest_table.setItems(Guest);
        }
        
        
    }

   
    
    
}
