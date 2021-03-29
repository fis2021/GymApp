package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class RegisterController {

    public void selectRoleChoiceBoxAction(ChoiceBox<String> choiceBox){
        choiceBox.getSelectionModel().selectedItemProperty().addListener
                ((v, oldValue, newValue) ->
                        {
                            try {
                                Stage stage = (Stage) choiceBox.getScene().getWindow();
                                String loadedFXML = "register" + newValue.replace(" ", "") + ".fxml" ;
                                String path = "../fxml/" + loadedFXML;
                                Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource(path));
                                Scene scene = new Scene(viewRegisterRoot, 700, 500);
                                stage.setScene(scene);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                );
    }

    public void handleBackToLoginButtonLogic(Control control){
        try {
            Stage stage = (Stage) control.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 600, 400);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
