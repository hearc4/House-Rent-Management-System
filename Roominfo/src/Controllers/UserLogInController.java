/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author Asus
 */
public class UserLogInController implements Initializable {

    @FXML
    private Label email_err;
    @FXML
    private Label password_err;
    @FXML
    private JFXTextField user_email_tf;
    @FXML
    private JFXPasswordField user_password_tf;
    @FXML
    private JFXComboBox<String> logInCombo;
     

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        logInCombo.getItems().add("Admin");
        logInCombo.getItems().add("Tenant");
        logInCombo.getItems().add("LandLord");
        
        // TODO
    }    

    @FXML
    private void logIn(ActionEvent event) throws ClassNotFoundException, SQLException {
        String email = user_email_tf.getText();
        String phone = user_password_tf.getText();

        if (!email.isEmpty() && !phone.isEmpty() && logInCombo.getValue().equals("Tenant") ) {

            //database connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=HouseRent;";
            Connection connection = DriverManager.getConnection(URL);

            Statement stm = connection.createStatement();
            String query = "SELECT Tenant_Name, Tenant_Phone FROM Tenant WHERE Tenant_Email='" + email + "' AND Tenant_Phone='" + phone + "'";
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {

                email = rs.getString("Tenant_Name");
                phone = rs.getString("Tenant_Phone");

                try {
                    Parent home = FXMLLoader.load(getClass().getResource("/FXMLs/Dashboard.fxml"));
                    Scene scn = new Scene(home);
                   Stage dashboard = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    dashboard.setScene(scn);
                    dashboard.setTitle("DashBoard");
                    dashboard.show();
                System.out.println("Found");

                } catch (IOException ex) {
                    System.out.println("Can't Load");
                }

            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Not Found");
                a.show();

                user_email_tf.clear();
                user_password_tf.clear();

                email_err.setText("");
                password_err.setText("");
            }
        }
       else if (!email.isEmpty() && !phone.isEmpty() && logInCombo.getValue().equals("Admin") ) {

            //database connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=HouseRent;";
            Connection connection = DriverManager.getConnection(URL);

            Statement stm = connection.createStatement();
            String query = "SELECT email, phone FROM AdminPanel WHERE email='" + email + "' AND phone='" + phone + "'";
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {

                email = rs.getString("email");
                phone = rs.getString("phone");

                try {
                    Parent home = FXMLLoader.load(getClass().getResource("/FXMLs/AdminHome.fxml"));
                    Scene scn = new Scene(home);
                   Stage dashboard = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    dashboard.setScene(scn);
                    dashboard.setTitle("DashBoard");
                    dashboard.show();
                System.out.println("Found");

                } catch (IOException ex) {
                    System.out.println("Can't Load");
                }

            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Not Found");
                a.show();

                user_email_tf.clear();
                user_password_tf.clear();

                email_err.setText("");
                password_err.setText("");
            }
        }
       
       else if (!email.isEmpty() && !phone.isEmpty() && logInCombo.getValue().equals("LandLord") ) {

            //database connection
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String URL = "jdbc:sqlserver://localhost:1433;user=sa;password=p@ssword13;" + "databaseName=HouseRent;";
            Connection connection = DriverManager.getConnection(URL);

            Statement stm = connection.createStatement();
            String query = "SELECT email, phone FROM LandLordPanel WHERE email='" + email + "' AND phone='" + phone + "'";
            ResultSet rs = stm.executeQuery(query);

            if (rs.next()) {

                email = rs.getString("email");
                phone = rs.getString("phone");

                try {
                    Parent home = FXMLLoader.load(getClass().getResource("/FXMLs/LandLoard.fxml"));
                    Scene scn = new Scene(home);
                   Stage dashboard = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    dashboard.setScene(scn);
                    dashboard.setTitle("DashBoard");
                    dashboard.show();
                System.out.println("Found");

                } catch (IOException ex) {
                    System.out.println("Can't Load");
                }

            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setContentText("Not Found");
                a.show();

                user_email_tf.clear();
                user_password_tf.clear();

                email_err.setText("");
                password_err.setText("");
            }
        }
       
        else {

            if (email.isEmpty()) {
                email_err.setText("*");
            }
            if (!email.isEmpty()) {
                email_err.setText("");
            }
            if (phone.isEmpty()) {
                password_err.setText("*");
            }
            if (!phone.isEmpty()) {
                password_err.setText("");
            }
        }
        
        
    }
        
    

    @FXML
    private void Register(ActionEvent event) {
         try {
                    Parent home = FXMLLoader.load(getClass().getResource("/FXMLs/userRegistration.fxml"));
                    Scene scn = new Scene(home);
                   Stage dashboard = (Stage) ((Node) event.getSource()).getScene().getWindow();

                    dashboard.setScene(scn);
                    dashboard.setTitle("Registration");
                    dashboard.show();
                System.out.println("Found");

                } catch (IOException ex) {
                    System.out.println("Can't Load");
                }

        
       
       

    }

    
}
