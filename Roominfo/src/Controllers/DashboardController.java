/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Asus
 */
public class DashboardController implements Initializable {

    @FXML
    private BorderPane room_info_pane;
    @FXML
    private BorderPane Home_borderPane;
    @FXML
    private BorderPane Booking_pane;
    @FXML
    private BorderPane payment_pane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            //set booking pane
             Pane booking = new FXMLLoader().load(getClass().getResource("/FXMLs/Home.fxml"));
            Home_borderPane.setCenter(booking);

            //set room info pane
            Pane room = new FXMLLoader().load(getClass().getResource("/FXMLs/RoomInfo.fxml"));
            room_info_pane.setCenter(room);

            //set check in pane
            Pane guest_info = new FXMLLoader().load(getClass().getResource("/FXMLs/Booking.fxml"));
            Booking_pane.setCenter(guest_info);

            //set check out pane
            Pane check_out = new FXMLLoader().load(getClass().getResource("/FXMLs/payment.fxml"));
            payment_pane.setCenter(check_out);

            //set profile pane
            
        } catch (Exception ex) {
            System.out.println("can't load");
        }
    }
        
        
        // TODO
    }    

    

