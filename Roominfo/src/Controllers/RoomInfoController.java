package Controllers;

import Alert.AlertDialog;
import com.jfoenix.controls.JFXComboBox;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import roominfo.room_info;

public class RoomInfoController implements Initializable {
    Connection connection = null;
    Statement stmt;
    private PreparedStatement pst = null;
    private ResultSet rs = null;

    @FXML
    private TableView<room_info> roomtype_TableView;
    @FXML
    private TableColumn<room_info, String> room_id_column;
    @FXML
    private TableColumn<room_info, String> room_status_column;
    @FXML
    private TableColumn<room_info,String> location;
    @FXML
    private Label lbl_rate;
    @FXML
    private Label lbl_vacant;
    @FXML
    private Label lbl_occupied;
    @FXML
    private Label lbl_totalroom;
    @FXML
    private JFXComboBox<String> room_type_cb;

    
    ObservableList<room_info> roomList = FXCollections.observableArrayList();
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            room_type_selection();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void room_type_cb_action(ActionEvent event) throws ClassNotFoundException, SQLException {

        lbl_rate.setText("");
        lbl_totalroom.setText("");
        lbl_occupied.setText("");
        lbl_vacant.setText("");

        DBConnect();
        Statement stm = connection.createStatement();

        String query_room_tab = "SELECT * FROM room_tab WHERE room_type='" + room_type_cb.getValue() + "'";
        ResultSet res = stm.executeQuery(query_room_tab);
        

        if (res.next()) {
            lbl_rate.setText(res.getString("rate"));
            lbl_totalroom.setText(res.getString("total_room"));
            lbl_occupied.setText(res.getString("booked_room"));
            lbl_vacant.setText(res.getString("vacant_room"));
        }

        setRoomInfoTable();
       
        
    }

    void setRoomInfoTable() {

        roomtype_TableView.getItems().clear();
        try {
            String query_room_type;

            if (room_type_cb.getValue().equalsIgnoreCase("Single bedroom")) {
                query_room_type = "SELECT * FROM Singlebedroom";
            } else {
                query_room_type = "SELECT * FROM Doublebedroom";
            }

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query_room_type);

            while (rs.next()) {
                roomList.add(new room_info(rs.getInt("room_id"), rs.getString("room_status")));
            }

            room_id_column.setCellValueFactory(new PropertyValueFactory<>("room_id"));
            room_status_column.setCellValueFactory(new PropertyValueFactory<>("room_status"));
             //location.setCellValueFactory(new PropertyValueFactory<>("room_location"));

            roomtype_TableView.setItems(roomList);
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
}
