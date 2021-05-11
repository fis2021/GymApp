package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.services.GymManagerService;

public class RegisterGymManagerController extends RegisterController {

    @FXML
    public Label passwordWarningLabel;
    @FXML
    public Label emailWarningLabel;
    @FXML
    public Label passwordRegexWarningLabel;
    @FXML
    public Label phoneWarningLabel;
    @FXML
    public Label registrationMessageLabel;
    @FXML
    public GridPane gridPane;
    @FXML
    public Label usernameWarningLabel;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> selectRoleChoiceBox;

    @FXML
    private TextField usernameField,phoneField,emailField,firstNameField,lastNameField,gymLocation,companyName;

    @FXML
    private void initialize(){
        selectRoleChoiceBoxAction(selectRoleChoiceBox);
        checkPhoneNumberFormat(phoneField, phoneWarningLabel);
        checkEmail(emailField, emailWarningLabel);
        passwordMatcher(passwordField, confirmPasswordField, passwordWarningLabel);
        passwordMatcher(confirmPasswordField, passwordField, passwordWarningLabel);
        passwordNotMatchingRegex(passwordField, passwordRegexWarningLabel);
        checkUsernameFormat(usernameField, usernameWarningLabel);
    }

    public void handleSubmitRegistrationButton(){
        try {
            CommonFunctionality.checkTextFieldsInAPaneAreNotEmpty(gridPane);

            GymManagerService.addUser(confirmPasswordField.getText(), firstNameField.getText(),
                    lastNameField.getText(), phoneField.getText(), emailField.getText(),
                    gymLocation.getText(), companyName.getText(), usernameField.getText(), passwordField.getText());
            registrationMessageLabel.setText("Account created successfully!");
            registrationMessageLabel.setVisible(true);
        } catch (UsernameAlreadyExistsException | FieldsAreNotEmptyException | ValidPasswordException | NotMatchingPasswordsException | corectEmailException | validPhoneNumberException | ValidUsernameException | ManagerUsernameIsNotOnShortListException e) {
            registrationMessageLabel.setText(e.getMessage());
            registrationMessageLabel.setVisible(true);
        }
    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectRoleChoiceBox);
    }
}
