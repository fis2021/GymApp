package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.*;
import proiect.fis.gym.aplication.services.CustomerService;
import proiect.fis.gym.aplication.exceptions.*;


public class RegisterCustomerController extends RegisterController {
    @FXML
    private Text registerMessage;
    @FXML
    private TextField firstNameField,lastNameField,phoneField,emailField,usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ChoiceBox<String> selectRoleChoiceBox;

    @FXML
    public void initialize(){
        selectRoleChoiceBoxAction(selectRoleChoiceBox);
    }

    public void handleSubmitRegistrationButton(){
        try {
            CustomerService.addUser(usernameField.getText(), passwordField.getText(), selectRoleChoiceBox.getValue(), firstNameField.getText(), lastNameField.getText(), phoneField.getText(), emailField.getText());
            registerMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            registerMessage.setText(e.getMessage());
        } catch (corectEmailException e){
            registerMessage.setText(e.getMessage());
        } catch (FieldsAreNotEmptyException e){
            registerMessage.setText(e.getMessage());
        } catch (ValidPasswordException e){
            registerMessage.setText(e.getMessage());
        } catch(ValidUsernameException e){
            registerMessage.setText(e.getMessage());
        } catch(validPhoneNumberException e){
            registerMessage.setText(e.getMessage());
        }
    }


    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectRoleChoiceBox);
    }
}
