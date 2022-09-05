/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;
import roominfo.Tenant_list;
import roominfo.room_info;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class Tenant_infoController implements Initializable {

    @FXML
    private TableView<Tenant_list> guest_table;
    @FXML
    private TableColumn<Tenant_list, String> guest_id;
    @FXML
    private TableColumn<Tenant_list, String> guest_name;
    @FXML
    private TableColumn<Tenant_list, String> guest_address;
    @FXML
    private TableColumn<Tenant_list, String> guest_email;
    @FXML
    private TableColumn<Tenant_list, String> guest_phone;
    @FXML
    private TableColumn<Tenant_list, String> guest_gender;
    ObservableList<Tenant_list> roomList = FXCollections.observableArrayList();
    Connection connection = null;
    Statement stmt;
    private PreparedStatement pst = null;
    private ResultSet rs = null;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         setValue();
        // TODO
    }    


    
      
    @FXML
    private void refresh_guest_table(ActionEvent event) throws ClassNotFoundException, SQLException {
        guest_table.getItems().clear();
         setValue();
       
        
    }
    
    void setValue() {
        try {
            DBConnect();
            String query = "SELECT * FROM Tenant";
            Statement stm = connection.createStatement();

            ResultSet rs = stm.executeQuery(query);

            while (rs.next()) {
                roomList.add(new Tenant_list(rs.getInt("Tenant_Id"), rs.getString("Tenant_Name"),rs.getString("Tenant_Adress"),rs.getString("Tenant_Email"),rs.getString("Tenant_Gender"),rs.getString("Tenant_Phone")));
            }

            guest_id.setCellValueFactory(new PropertyValueFactory<>("ID"));
            guest_name.setCellValueFactory(new PropertyValueFactory<>("name"));
             guest_address.setCellValueFactory(new PropertyValueFactory<>("address"));
             guest_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            guest_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
             guest_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            
             

            guest_table.setItems(roomList);
            connection.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    


    
    
    //for db connect
    void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=HouseRent;";
        connection = DriverManager.getConnection(URL);
    }

    
}
