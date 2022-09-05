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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class AdminHomeController implements Initializable {

    @FXML
    private BorderPane booking_choose_pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        start();
        // TODO
    }    

    @FXML
    private void Tenant_info(ActionEvent event) {
        start();
    }

    @FXML
    private void Manage_room(ActionEvent event) {
        try {
            Pane view = new FXMLLoader().load(getClass().getResource("/FXMLs/ManageRoom.fxml"));
            booking_choose_pane.setCenter(view);
        } catch (Exception ex) {
            System.out.println("can't load");
        }
    }
    
    void start() {
        try {
            Pane view = new FXMLLoader().load(getClass().getResource("/FXMLs/Tenant_info.fxml"));
            booking_choose_pane.setCenter(view);
        } catch (Exception ex) {
            System.out.println("can't load");
        }
    }

    @FXML
    private void LogOutBtn(ActionEvent event) {
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
