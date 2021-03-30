package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterAdminController extends RegisterController {
    @FXML
    private ChoiceBox<String> selectRoleChoiceBox;

    @FXML
    private TextField firstNameField,lastNameField,emailField,usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    public void initialize(){
        selectRoleChoiceBoxAction(selectRoleChoiceBox);
    }

    public void handleSubmitRegistrationButton(ActionEvent actionEvent) {
    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectRoleChoiceBox);
    }
}