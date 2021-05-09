package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.services.CustomerService;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.Customer;

import java.io.IOException;
import java.util.ArrayList;

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
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/MakePayment.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 700, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void backToLogin(){
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 800, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleBackToMainPageButton(){
        try {
            Stage stage =(Stage) paymentMessage.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/CustomerAfterLoginPage.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 800, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleViewCoursesButton1() {
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/SmartfitDetails.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 880, 1100);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleViewCoursesButton2() {
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/GymOneDetails.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 880, 1100);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleViewCoursesButton3() {
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/IguanaDetails.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 880, 1100);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleExtendButton(){
        try {
            Stage stage =(Stage) sceneChanger.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/ExtendSubscription.fxml"));
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

    public void handleMakePaymentButton(){
        try {
            CustomerService.makePayment(selectGym.getValue(),ownerName.getText(),month.getValue(),year.getValue(),cardNumber.getText(),cvc.getText(),duration.getValue());
            paymentMessage.setText("Payment made successfully");
        } catch (incorectCardDetailsException | IncorectCardNumberException | IncorectCVCException | NotEnoughMoneyException | CheckPaymentFieldNotEmptyException e) {
            paymentMessage.setText(e.getMessage());
        }
    }

    public void handleExtendSubscriptionButton(){
        try {
            CustomerService.extendSubscription(selectGym.getValue(),ownerName.getText(),month.getValue(),year.getValue(),cardNumber.getText(),cvc.getText(),duration.getValue());
            paymentMessage.setText("Subscription extended successfully");
        } catch (noActiveSubscriptionException| incorectCardDetailsException | IncorectCardNumberException | IncorectCVCException | NotEnoughMoneyException | CheckPaymentFieldNotEmptyException e) {
            paymentMessage.setText(e.getMessage());
        }
    }

    public void handleViewJoinedCoursesButton(){
        String username = LoginController.getCurrentUsername();
        ObjectRepository<Customer> customerRepository= CustomerService.getCustomerRepository();
        String a ="";
        for(Customer customer : customerRepository.find()){
            if(customer.getUsername().equals(username)){
                ArrayList<Course> array=customer.getArraySmart();

                for(int i=0;i<array.size();i++)
                    a+="Smartfit: " + array.get(i) + "\n";
                array = customer.getArrayIguana();
                for(int i=0;i<array.size();i++)
                    a+="Iguana: " + array.get(i) + "\n";
                array = customer.getArrayOne();
                for(int i=0;i<array.size();i++)
                    a+="GymOne: " + array.get(i) + "\n";
            }
        }
        customerMessage.setText(a);
    }

    public void handleBackToLoginButton(){
        handleBackToLoginButtonLogic(selectGym);
    }
}
