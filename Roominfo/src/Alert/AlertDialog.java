/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Alert;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author User
 */
public class AlertDialog {
    public static void display(String title, String message){
        Stage window=new Stage();
        window.setTitle(title);
        window.setMinWidth(300);
        window.setMaxHeight(150);
        
        Label label1 =new Label();
        label1.setText(message);
        Button buttonok=new Button("OK");
        buttonok.setOnAction(e -> window.close());
        
        VBox layout=new VBox(5);
        layout.getChildren().addAll(label1,buttonok);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene=new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
    
    }
    
    
}
