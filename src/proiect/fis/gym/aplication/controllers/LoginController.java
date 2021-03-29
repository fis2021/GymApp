package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    public Text loginMessage;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField usernameField;

    @FXML
    public void handleRegisterButton(){
        try {
            Stage stage =(Stage) loginMessage.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/registerCustomer.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 700, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLoginButton() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || username.isEmpty()) {
            loginMessage.setText("Please type in a username!");
            return;
        }

        if (password == null || password.isEmpty()) {
            loginMessage.setText("Password cannot be empty");
            return;
        }

        if (username.equals("customer") && password.equals("customer")) {
            loginMessage.setText("Logged in as student!");
            return;
        }

        if (username.equals("manager") && password.equals("manager")) {
            loginMessage.setText("Logged in as manager!");
            return;
        }

        loginMessage.setText("Incorrect login!");
    }
}

