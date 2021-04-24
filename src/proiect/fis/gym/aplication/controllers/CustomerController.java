package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.CustomerService;
import proiect.fis.gym.aplication.services.GymManagerService;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class CustomerController extends RegisterController{

    @FXML
    private Text sceneChanger;

    @FXML
    private ChoiceBox<String> selectGym,year,month,duration;

    @FXML
    private TextField ownerName,cardNumber,cvc,username;

    @FXML
    private Text paymentMessage;

    @FXML
    private Text customerMessage;

    @FXML
    public void handleCreateSubscription(){
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/MakePayment.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 700, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void handleViewCoursesButton1() {
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/SmartfitDetails.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 880, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleViewCoursesButton2() {
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/IguanaDetails.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 880, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleViewCoursesButton3() {
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/GymOneDetails.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 880, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToLogin(){
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 700, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Customer getCustomerFromDatabase(String username){
        for(Customer customer : CustomerService.getCustomerRepository().find()) {
            if (username.equals(customer.getUsername())) {
                return customer;
            }
        }
        return null;
    }

    public void handleViewSubscriptionsButton(){
        Customer customer = CustomerController.getCustomerFromDatabase(LoginController.getCurrentUsername());
        String a = CustomerService.showSubscriptions(customer);
        customerMessage.setText(a);
    }

    public void handleBackToMainPageButton(){
        try {
            Stage stage =(Stage) paymentMessage.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/CustomerAfterLoginPage.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 800, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void handleMakePaymentButton(){
        try {
            CustomerService.makePayment(selectGym.getValue(),ownerName.getText(),month.getValue(),year.getValue(),cardNumber.getText(),cvc.getText(),duration.getValue(),username.getText());
            paymentMessage.setText("Payment made succesfully");
        } catch (incorectCardDetailsException | IncorectCardNumberException | IncorectCVCException | NotEnoughMoneyException | CheckPaymentFieldNotEmptyException e) {
            paymentMessage.setText(e.getMessage());
        }
    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectGym);
    }
}
