package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class CustomerController {

    @FXML
    private Text sceneChanger;


    @FXML
    public void handleCreateSubscription(){
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/MakePayment.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 700, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
