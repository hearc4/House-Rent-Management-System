/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class LandLoardController implements Initializable {

    @FXML
    private BorderPane super_borderPane;
    @FXML
    private Label super_ad_name;
    @FXML
    private Label super_ad_email;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void manage_guest(ActionEvent event) {
    }

    @FXML
    private void manage_rooms(ActionEvent event) {
    }

    @FXML
    private void super_log_out(ActionEvent event) {
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
