/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class UserRegistrationController implements Initializable {

     @FXML
    private TextField tenantName;
    @FXML
    private TextField tenantAdress;
    @FXML
    private TextField tenantEmail;
    @FXML
    private ComboBox<String> tenantGender;
    @FXML
    private TextField tenantPhone;
    @FXML
    private Button submitbtn;
    @FXML
    private Label mark1;
    @FXML
    private Label mark2;
    @FXML
    private Label mark3;
    private Label mark4;
    @FXML
    private Label mark5;
    Connection connection = null;
    Statement stmt;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    @FXML
    private Button logInSubmit;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         tenantGender.getItems().add("Male");
        tenantGender.getItems().add("Female");
       
    } 
   public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                            "[a-zA-Z0-9_+&*-]+)*@" +
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                            "A-Z]{2,7}$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    @FXML
    private void handelbtnSubmit(ActionEvent event) {
         String name = tenantName.getText();
        String adress = tenantAdress.getText();
        String email = tenantEmail.getText();
        String gender=tenantGender.getValue();
        
        String phone = tenantPhone.getText();

        if (!(tenantName.getText().isEmpty()) && !(tenantAdress.getText().isEmpty()) && !(tenantEmail.getText().isEmpty()) && tenantGender.getValue() != null && !(tenantPhone.getText().isEmpty())
                ) {

//         
            String query = "use HouseRent;insert into Tenant(Tenant_Name,Tenant_Adress,Tenant_Email,Tenant_Phone,Tenant_Gender) values(?,?,?,?,?)";

            try {
                DBConnect();
               // query = "use RentalHouse;insert into Tenant(Tenant_Id,Tenant_Name,Tenant_Adress,Tenant_Email,Tenant_Phone,Tenant_Gender) values(1000,'Hridoy','Narsingdi','190104119@aust.edu','01785809356','Male')";
                PreparedStatement ps = connection.prepareStatement(query);
               
               // ps.setInt(1, tenNum);
                ps.setString(1, name);
                ps.setString(2, adress);
                ps.setString(3, email);
                ps.setString(4,phone );
                ps.setString(5, gender);

                ps.executeUpdate();
                Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                a.setContentText("Registration Complete ");
                a.show();

               

            } catch (Exception ex) {
                
                ex.printStackTrace();
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(ex.getMessage());
                a.show();
            }

            //System.out.println(tenNum);

        } else {
            if (name.isEmpty()) {
                mark1.setText("Please Enter Your name");
            }
                if (!name.isEmpty()) {
                mark1.setText("");
            }
            if (adress.isEmpty()) {
                mark2.setText("Please Enter Your Adress");
            }
            if (!adress.isEmpty()) {
                mark2.setText("");
            }
            if (email.isEmpty()) {
                mark3.setText("Please Enter Your Email");
            }
            if (!isEmailValid(email))
            {
              mark3.setText("Enter valid email");
            }
            

            if (!email.isEmpty()) {
                mark3.setText("");
            }
           
            if (phone.isEmpty()) {
                mark5.setText("Please Enter Your phone No");
            }
           
        }
    }
    void DBConnect() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception ex) {
            System.out.println(ex);
        }

        String connectionUrl = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13";
        try {
            connection = DriverManager.getConnection(connectionUrl);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void logInBtn(ActionEvent event) {
        try {
                    Parent home = FXMLLoader.load(getClass().getResource("/FXMLs/userLogIn.fxml"));
                    Scene scn = new Scene(home);
                   Stage dashboard = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    dashboard.setScene(scn);
                    dashboard.setTitle("Log In");
                    dashboard.show();
                System.out.println("Found");

                } catch (IOException ex) {
                    System.out.println("Can't Load");
                }

    }
   
}
