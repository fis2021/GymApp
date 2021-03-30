package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private ChoiceBox<String> selectRoleChoiceBox;

    @FXML
    public void initialize(){
        selectRoleChoiceBoxAction(selectRoleChoiceBox);
        checkEmail(emailField, emailWarningLabel);
        checkAdminCode();
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

        if(adminCodeWarningLabel.visibleProperty().equals(true)){
            return false;
        }
        else{
            return true;
        }
    }

    public void handleSubmitRegistrationButton(ActionEvent actionEvent) {
    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectRoleChoiceBox);
    }
}