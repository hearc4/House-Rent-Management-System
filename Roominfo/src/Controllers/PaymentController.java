/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import roominfo.Payment;
import java.sql.PreparedStatement;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class PaymentController implements Initializable {

    @FXML
    private Label tamount;
    @FXML
    private Label payAmmount;
    @FXML
    private TableView<Payment> paymentTableView;
     ObservableList<Payment> payment = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> readbookId;
    @FXML
    private TableColumn<?, ?> readbookDate;
    @FXML
    private TableColumn<?, ?> Booking_name;
    @FXML
    private TableColumn<?, ?> readTenantId;
    @FXML
    private Button search_pay;
    @FXML
    private TextField searchText;
    @FXML
    private Button searchbtn;
    @FXML
    private Button confirm_btn;
    @FXML
    private JFXComboBox<String> payment_Type;
    @FXML
    private Label tamount1;
    @FXML
    private Label DueAmmount;
    @FXML
    private JFXComboBox<String> Advance_Pay;
      Connection connection;
       public static int rate = 0;
       public int due = 0;
       

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        payment_Type.getItems().add("Cash");
        payment_Type.getItems().add("Card");
        Advance_Pay.getItems().add("5000");
        Advance_Pay.getItems().add("10000");
        Advance_Pay.getItems().add("15000");
        
        // TODO
    }    

    @FXML
    private void payBtn(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (paymentTableView.getSelectionModel().getSelectedItem() != null) {

            Payment data = paymentTableView.getSelectionModel().getSelectedItem();
            //payAmmount.setText(String.valueOf(data.getReadbookId()));

            int id = data.readbookId;
            DBConnect();
            String query2 = "Select * from Tenant Inner Join Booking on Tenant.Tenant_Id  = Booking.Tenant_Id join room_tab on Booking.Room_type=room_tab.room_type where Booking_id=" + id;
            //System.out.println("connected");
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query2);

            rs.next();
            rate = rs.getInt("rate");
            String d2 = null;
           
            
            d2 = rs.getString("Check_in");
            long diff = 0;
            java.sql.Date date = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            String d1 = rs.getString("Check_out");
            try {
                Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(d2);
                Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(d1);

                long one = date1.getTime();
                long two = date2.getTime();
                long mx = 0;
                long mn = 0;
                
                
                if(one>=two){
                    mx = one;
                    mn = two;
                }
                else{
                    mn = one;
                    mx = two;
                }
                diff = ((mx-mn)/ (1000 * 60 * 60 * 24))% 365;
                System.out.println(one + " " + two + " " + diff);
                if(diff%30==0){
                    diff /= 30;
                }
                else{
                    diff /= 30;
                    diff++;
                }
                
                System.out.println(one + " " + two + " " + diff);
            } catch (ParseException e) {
            }
            rate = (int) (rate * diff);
            payAmmount.setText("" + rate);

        } else {
            JOptionPane.showMessageDialog(null, "Please Select a Guest");
        }

    }

    @FXML
    private void clickSearch(ActionEvent event) throws ClassNotFoundException, SQLException {
        payment.clear();
        paymentTableView.getItems().removeAll();
        paymentTableView.setItems(payment);
        String search_Value = searchText.getText();
        System.out.println("connected");

        if (!(search_Value.isEmpty())) {

            DBConnect();

            //String query = "SELECT Booking_id, Guest_id,Check_in FROM Booking WHERE Booking_id LIKE '" + search_Value + "'"
            //+ "OR Guest_id LIKE '" + search_Value + "%' OR Check_in  LIKE '" + search_Value + "'";
            String query1 = "Select * from Tenant Inner Join Booking on Tenant.Tenant_Id  = Booking.Tenant_Id join room_tab on Booking.Room_type=room_tab.room_type where Tenant_Name='" + search_Value + "'";
            //String query1="Select Booking_id,GUEST_TABLE.Guest_Id,Check_in,Guest_Name from GUEST_TABLE Inner Join Booking on GUEST_TABLE.Guest_Id  = Booking.Guest_id join room_tab on Booking.Room_type=room_tab.room_type  where Guest_Name="+ search_Value;

            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query1);

            String d1 = null;
            String d2 = null;
            int rate = 0;
            int bookid;
            while (rs.next()) {
                payment.add(new Payment(rs.getInt("Booking_id"), rs.getInt("Tenant_Id"), rs.getString("Check_in"), rs.getString("Tenant_Name")));
                
            }

            readbookId.setCellValueFactory(new PropertyValueFactory<>("readbookId"));
            readTenantId.setCellValueFactory(new PropertyValueFactory<>("readTenantId"));
            readbookDate.setCellValueFactory(new PropertyValueFactory<>("readbookDate"));
            Booking_name.setCellValueFactory(new PropertyValueFactory<>("Booking_name"));

            paymentTableView.setItems(payment);
        }

    }

    @FXML
    private void pay_confirm(ActionEvent event) throws ClassNotFoundException, SQLException {
        if (paymentTableView.getSelectionModel().getSelectedItem() != null) {

            Payment data = paymentTableView.getSelectionModel().getSelectedItem();
            //payAmmount.setText(String.valueOf(data.getReadbookId()));

            int id = data.readbookId;
            DBConnect();
            String query2 = "Select room_tab.room_type from Tenant Inner Join Booking on Tenant.Tenant_Id  = Booking.Tenant_Id join room_tab on Booking.Room_type=room_tab.room_type where Booking_id=" + id;
            //System.out.println("connected");
            Statement stm = connection.createStatement();
            ResultSet rs = stm.executeQuery(query2);
            String type = null;
            rs.next();
            type = rs.getString("room_type");

            String que1 = "update room_tab set booked_room=booked_room+1, vacant_room=total_room-booked_room-1 where room_type = '" + type + "'";
            stm.executeUpdate(que1);

            String que3 = "update Booking set booking_status='completed' where Booking_id=" + id;
            stm.executeUpdate(que3);

            int rand = ThreadLocalRandom.current().nextInt(200, 999 + 1);
            String randomNum = Integer.toString(rand);

            SimpleDateFormat formatter = new SimpleDateFormat("yyMdd");
            java.util.Date currDate = Calendar.getInstance().getTime();
            String date = formatter.format(currDate);

            String num = date + randomNum;
            int paymentid = Integer.parseInt(num);
            DBConnect();

            System.out.println(date);

            //String query = "insert into PAYMENT(PATMENT_ID, Booking_id, TOTAL_AMMOUNT,Due_AMMOUNT, PAYMENT_STATUS) values(" + num + ", " + id + ", " + rate + "," + due + ", 'Paid')";

           // Statement stmt = connection.createStatement();
            //stmt.executeUpdate(query);
            String query = "insert into PAYMENT (PATMENT_ID, Booking_id, TOTAL_AMMOUNT,Due_AMMOUNT, PAYMENT_STATUS) values (?,?,?,?,?) ";
        PreparedStatement ps = connection.prepareStatement(query);
        ps.setInt(1, rand);
        ps.setInt(2, id);
        ps.setInt(3, rate);
        ps.setInt(4, due);
        ps.setString(5,payment_Type.getValue());
        
        
        ps.executeUpdate();
            
            
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Payment Complete");
                a.show();
            

        } else {
            JOptionPane.showMessageDialog(null, "Please Select a Guest");
        }

        
    }

    @FXML
    private void BookingAdvance(ActionEvent event) {
        String adv = Advance_Pay.getValue();
        long advance = Long.parseLong(adv);
        
         due = (int) (rate - advance);
        
        
        DueAmmount.setText(String.valueOf(due));
    }
     void DBConnect() throws ClassNotFoundException, SQLException {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=HouseRent;";
        connection = DriverManager.getConnection(URL);
    }

    @FXML
    private void payMethod(ActionEvent event) {
        
        
    }

    @FXML
    private void logOutbtn(ActionEvent event) {
        try {
            Parent home = FXMLLoader.load(getClass().getResource("/FXMLs/userLogIn.fxml"));
            Scene scn = new Scene(home);
            Stage log_in = (Stage) ((Node) event.getSource()).getScene().getWindow();

            log_in.setScene(scn);
            log_in.setTitle("Log In");
            log_in.show();

        } catch (IOException ex) {
            System.out.println("Can't Load");
        }
    }

    
}
