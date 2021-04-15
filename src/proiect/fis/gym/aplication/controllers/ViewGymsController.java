package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewGymsController {

    @FXML
    public Button backToProfileButton;

    public void handleBackToProfileButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) backToProfileButton.getScene().getWindow();
            String path = "../fxml/" + "GymManagerProfile.fxml";
            //System.out.println(path);
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(viewRegisterRoot, 800, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
