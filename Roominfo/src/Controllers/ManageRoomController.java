/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import roominfo.room_info;
import roominfo.villa_info;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class ManageRoomController implements Initializable {

    Connection connection;
    ObservableList<room_info> room_list = FXCollections.observableArrayList();
    ObservableList<villa_info> type_list = FXCollections.observableArrayList();
    @FXML
    private TableView<room_info> addroomTableView;
    @FXML
    private TableColumn<room_info, String> columnroomid;
    @FXML
    private TableColumn<room_info, String> columnroomstatus;
    @FXML
    private JFXComboBox<String> room_cb;
    @FXML
    private TableView<villa_info> villa_type;
    @FXML
    private TableColumn<villa_info, String> villa_type_column;
    @FXML
    private TableColumn<villa_info, String> villa_total_room_column;
    @FXML
    private TableColumn<villa_info, String> villa_rate_column;
    @FXML
    private TextField update_price_tf;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            room_type_selection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        type_set();
        // TODO
    }    

    @FXML
    private void add_room_action(ActionEvent event) {
         try {
            String add_room_query;
            String room_query;

            if (room_cb.getValue().equalsIgnoreCase("Single bedroom")) {
                add_room_query = "INSERT INTO Singlebedroom (room_status) VALUES ('vacant')";
                room_query = "UPDATE room_tab SET total_room=total_room+1, vacant_room=vacant_room+1 WHERE room_type='Single bedroom'";
            } else {
                add_room_query = "INSERT INTO Doublebedroom (room_status) VALUES ('vacant')";
                room_query = "UPDATE room_tab SET total_room=total_room+1, vacant_room=vacant_room+1 WHERE room_type='Double bedroom'";
            }

            DBConnect();
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(add_room_query);
            stmt.executeUpdate(room_query);

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Room Add Completed");
            a.show();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    @FXML
    private void delete_room_action(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (addroomTableView.getSelectionModel().getSelectedItem() != null) {

            room_info data = addroomTableView.getSelectionModel().getSelectedItem();
            int id = data.getRoom_id();
            String status = data.getRoom_status();

            int returnValue = JOptionPane.showConfirmDialog(null, "Are You Sure?", "Want to Remove?", JOptionPane.YES_NO_OPTION);

            if (returnValue == JOptionPane.YES_OPTION) {

                if (!(status.equals("booked"))) {

                    String dlt_query;
                    String dec_query;

                    if (room_cb.getValue().equalsIgnoreCase("Single bedroom")) {

                        dlt_query = "DELETE FROM Singlebedroom WHERE room_id=" + id;

                        dec_query = "UPDATE room_tab SET total_room=total_room-1, vacant_room=vacant_room-1 WHERE room_type='Single bedroom'";

                    } else {

                        dlt_query = "DELETE FROM Doublebedroom WHERE room_id=" + id;

                        dec_query = "UPDATE room_tab SET total_room=total_room-1, vacant_room=vacant_room-1 WHERE room_type='Double bedroom'";
                    }
                    DBConnect();

                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate(dlt_query);
                    stmt.executeUpdate(dec_query);

                    connection.close();

                    addroomTableView.getItems().remove(data);

                    Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                    a.setContentText("Room Deleted");
                    a.show();
                } else {
                    JOptionPane.showMessageDialog(null, "Can't Remove Booked Room..!!!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please Select a Room");
        }
        
    }

    @FXML
    private void room_cb_action(ActionEvent event) {
        addroomTableView.getItems().clear();
        try {
            String query_room_type;

            if (room_cb.getValue().equalsIgnoreCase("Single bedroom")) {
                query_room_type = "SELECT * FROM Singlebedroom";
            } else {
                query_room_type = "SELECT * FROM Doublebedroom";
            }
            DBConnect();
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query_room_type);

            while (rs.next()) {
                room_list.add(new room_info(rs.getInt("room_id"), rs.getString("room_status")));
            }

            columnroomid.setCellValueFactory(new PropertyValueFactory<>("room_id"));
            columnroomstatus.setCellValueFactory(new PropertyValueFactory<>("room_status"));

            addroomTableView.setItems(room_list);
            connection.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @FXML
    private void update_price(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (villa_type.getSelectionModel().getSelectedItem() != null && !(update_price_tf.getText().isEmpty())) {

            villa_info data = villa_type.getSelectionModel().getSelectedItem();

            String villaType = data.getType();

            DBConnect();

            String query = "UPDATE room_tab SET rate=" + update_price_tf.getText() + "WHERE room_type='" + villaType + "'";

            Statement stmt = connection.createStatement();
            stmt.executeUpdate(query);

            connection.close();

            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setContentText("Price Updated");
            a.show();
        } else {
            JOptionPane.showMessageDialog(null, "Please Select a Villa");
        }
 
    }

    @FXML
    private void refresh_type_table(ActionEvent event) {
        update_price_tf.setText("");
        type_set();
    }
    
    void type_set() {

        villa_type.getItems().clear();
        try {
            DBConnect();

            Statement stm = connection.createStatement();
            String query = "SELECT room_type, rate, total_room FROM room_tab";
            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                type_list.add(new villa_info(rs.getInt("rate"), rs.getInt("total_room"), rs.getString("room_type")));
            }

            villa_type_column.setCellValueFactory(new PropertyValueFactory<>("type"));
            villa_total_room_column.setCellValueFactory(new PropertyValueFactory<>("totalRoom"));
            villa_rate_column.setCellValueFactory(new PropertyValueFactory<>("villa_price"));

            villa_type.setItems(type_list);
            connection.close();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    void room_type_selection() throws ClassNotFoundException, SQLException {

        DBConnect();
        Statement stm = connection.createStatement();
        String query = "SELECT room_type FROM room_tab";
        ResultSet rs = stm.executeQuery(query);

        while (rs.next()) {
            room_cb.getItems().add(rs.getString("room_type"));
        }
        connection.close();
    }

    void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=HouseRent;";
        connection = DriverManager.getConnection(URL);
    }

    
}
