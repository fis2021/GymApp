package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegisterController {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    protected static int criteriaRegisterAdmin  = 0;

    public void selectRoleChoiceBoxAction(ChoiceBox<String> choiceBox) {
        choiceBox.getSelectionModel().selectedItemProperty().addListener
                ((v, oldValue, newValue) ->
                        {
                            try {
                                Stage stage = (Stage) choiceBox.getScene().getWindow();
                                String loadedFXML = "register" + newValue.replace(" ", "") + ".fxml";
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

    public boolean passwordMatcher(TextField passwordField, TextField confirmPasswordField, Label warningLabel){
        confirmPasswordField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);

            if (!passwordField.getText().equals(confirmPasswordField.getText()) ){
                warningLabel.setVisible(true);
            }
            else{
                warningLabel.setVisible(false);
            }
        });

        if(warningLabel.isVisible()  || passwordField.getText().equals("")){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkEmail(TextField emailField, Label warningLabel) {

        emailField.textProperty().addListener((observable, oldValue, newValue) ->
        {
            //System.out.println("textfield changed from " + oldValue + " to " + newValue);

            Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailField.getText());
            if (!matcher.find()){
                warningLabel.setVisible(true);
                //warningLabel.setText(=);
            }
            else{
                warningLabel.setVisible(false);
            }
        });

        if(warningLabel.isVisible() || !emailField.getText().equals("")){
            return false;
        }
        else{
            return true;
        }
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
