package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.services.AdminService;

public class RegisterAdminController extends RegisterController {
    private static final String adminCode = "1234";
    @FXML
    public TextField emailField;
    @FXML
    public Label emailWarningLabel;
    @FXML
    public TextField adminCodeField;
    @FXML
    public Label adminCodeWarningLabel;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField confirmPasswordField;
    @FXML
    public TextField firstNameField;
    @FXML
    public TextField lastNameField;
    @FXML
    public Label registrationMessageLabel;
    @FXML
    public Button submitRegistrationButton;
    @FXML
    public Label passwordWarning;
    @FXML
    public GridPane gridPaneAdmin;
    @FXML
    public Label passwordRegexWarning;
    @FXML
    private ChoiceBox<String> selectRoleChoiceBox;

    @FXML
    public void initialize(){
        selectRoleChoiceBoxAction(selectRoleChoiceBox);

        checkEmail(emailField, emailWarningLabel);
        passwordNotMatchingRegex(passwordField, passwordRegexWarning);
        passwordMatcher(passwordField, confirmPasswordField, passwordWarning);
        passwordMatcher(confirmPasswordField, passwordField, passwordWarning);
        checkAdminCode();
    }

    private void checkAdminCode(){

        adminCodeField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);
            if(adminCodeField.getText().equals(adminCode)){
                adminCodeWarningLabel.setVisible(false);
            }
            else{
                adminCodeWarningLabel.setVisible(true);
            }
        });

    }

    public void handleSubmitRegistrationButton(ActionEvent actionEvent) {
        try {
            //AdminService.initDatabase();
            CommonFunctionality.checkTextFieldsInAPaneAreNotEmpty(gridPaneAdmin);
            AdminService.addUser(adminCodeField.getText(), confirmPasswordField.getText(),firstNameField.getText(), lastNameField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText());
            registrationMessageLabel.setText("Account created successfully!");
            registrationMessageLabel.setVisible(true);
        } catch (UsernameAlreadyExistsException | FieldsAreNotEmptyException | ValidPasswordException | NotMatchingPasswordsException | corectEmailException | InvalidAdminCodeException e) {
            registrationMessageLabel.setText(e.getMessage());
            registrationMessageLabel.setVisible(true);
            //AdminService.closeDatabase();
        }
    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectRoleChoiceBox);
    }
}