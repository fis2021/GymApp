package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class RegisterCustomerController extends RegisterController {
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField ageField;
    public TextField phoneField;
    public TextField emailField;
    public TextField usernameField;
    public PasswordField passwordField;
    @FXML
    private ChoiceBox<String> selectRoleChoiceBox;

    @FXML
    private Button customerRegisterButton;

    @FXML
    public void initialize(){
        selectRoleChoiceBoxAction(selectRoleChoiceBox);
    }

    public void handleSubmitRegistrationButton(ActionEvent actionEvent){

    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectRoleChoiceBox);
    }
}
