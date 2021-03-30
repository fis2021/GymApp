package proiect.fis.gym.aplication.controllers;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import proiect.fis.gym.aplication.services.AdminService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.NitriteBuilder;
import org.dizitart.no2.event.ChangeInfo;
import org.dizitart.no2.event.ChangeListener;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.UsernameAlreadyExistsException;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.services.AdminService;

import java.nio.file.Paths;
import java.util.regex.Matcher;

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
    private ChoiceBox<String> selectRoleChoiceBox;

    @FXML
    public void initialize(){
        AdminService.initDatabase();
        selectRoleChoiceBoxAction(selectRoleChoiceBox);

        checkEmail(emailField, emailWarningLabel);
        passwordMatcher(passwordField, confirmPasswordField, passwordWarning);
        passwordMatcher(confirmPasswordField, passwordField, passwordWarning);
        checkAdminCode();

        submitRegistrationButton.setDisable(true);
        submitButtonDisable();
    }

    private void submitButtonDisable(){
        //submitRegistrationButton.setDisable(true);

        for ( Node node : gridPaneAdmin.getChildren() )
        {
            if(node instanceof TextField) {
                ((TextField) node).textProperty().addListener((observable, oldValue, newValue) ->
                {
                    //conditii validare enableing submitRegistrationButton
                    if (
                            checkAdminCode()&&
                            !adminCodeWarningLabel.isVisible() &&
                            !passwordWarning.isVisible() &&
                            !emailWarningLabel.isVisible() &&
                            !firstNameField.getText().equals("") &&
                            !lastNameField.getText().equals("")  &&
                            !usernameField.getText().equals("") &&
                            !emailField.getText().equals("") &&
                            !passwordField.getText().equals("") &&
                            !confirmPasswordField.getText().equals("") &&
                            !adminCodeField.getText().equals("")
                    ){
                        submitRegistrationButton.setDisable(false);
                    }
                    else{
                        submitRegistrationButton.setDisable(true);
                    }
                });

            }
        }

    }

    public boolean checkAdminCode(){

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

        if(adminCodeField.getText().equals(adminCode) ){
            return true;
        }
        else{
            return false;
        }
    }

    public void handleSubmitRegistrationButton(ActionEvent actionEvent) {
        try {
            AdminService.addUser(firstNameField.getText(), lastNameField.getText(), emailField.getText(), usernameField.getText(), passwordField.getText());
            registrationMessageLabel.setText("Account created successfully!");
            registrationMessageLabel.setVisible(true);
        } catch (UsernameAlreadyExistsException e) {
            registrationMessageLabel.setText(e.getMessage());
            registrationMessageLabel.setVisible(true);
        }
    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectRoleChoiceBox);
    }
}