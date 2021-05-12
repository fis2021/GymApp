package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.exceptions.IncorectLoginException;
import proiect.fis.gym.aplication.services.LoginService;

import java.io.IOException;

public class LoginController {
    private static String currentUsername;

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
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/registerCustomer.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 700, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleLoginButton() {
       int c=0;
        currentUsername = usernameField.getText();
        try {
            c= LoginService.login(usernameField.getText(), passwordField.getText());
            if(c==1) {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/CustomerAfterLoginPage.fxml")), 800, 600));
            }
            if(c==2){
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/AdminProfile.fxml")), 800, 500));
            }
            if(c==3){
                //currentUsername = usernameField.getText();
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("fxml/GymManagerProfile.fxml")), 800, 500));
            }

       } catch (IncorectLoginException e) {
           loginMessage.setText(e.getMessage());
        } catch (IOException e){
            e.printStackTrace();
       }

    }

    public static String getCurrentUsername(){
        return currentUsername;
    }

    public static void setUsername(String username){ currentUsername=username;}

}

