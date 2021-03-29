package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegisterGymManagerController extends RegisterController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> selectRoleChoiceBox;

    @FXML
    private TextField usernameField,ageField,phoneField,emailField,firstNameField,lastNameField,gymLocation,companyName;

    @FXML
    private void initialize(){
        selectRoleChoiceBoxAction(selectRoleChoiceBox);
    }

    public void handleSubmitRegistrationButton(){

    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectRoleChoiceBox);
    }
}
