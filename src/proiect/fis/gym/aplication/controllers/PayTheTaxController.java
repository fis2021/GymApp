package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import proiect.fis.gym.aplication.exceptions.IncorectCVCException;
import proiect.fis.gym.aplication.exceptions.IncorectCardNumberException;
import proiect.fis.gym.aplication.exceptions.NotEnoughMoneyException;
import proiect.fis.gym.aplication.exceptions.incorectCardDetailsException;
import proiect.fis.gym.aplication.model.Bank;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.BankService;
import proiect.fis.gym.aplication.services.GymManagerService;

import java.time.LocalDate;
import java.util.Objects;

import static proiect.fis.gym.aplication.controllers.CommonFunctionality.CVCException;
import static proiect.fis.gym.aplication.controllers.CommonFunctionality.checkTextAreaInAPaneAreNotEmpty;


//import static proiect.fis.gym.aplication.services.CustomerService.paymentFieldsException;

public class PayTheTaxController {
    @FXML
    public Button backButton;
    @FXML
    public GridPane paymentGridPane;
    @FXML
    public Label CVCLabel;
    @FXML
    public TextField cvcTextField;
    @FXML
    public TextField cardNumberTextField;
    @FXML
    public TextField ownerNameTextField;
    @FXML
    public ChoiceBox monthChoiceBox;
    @FXML
    public ChoiceBox yearChoiceBox;
    @FXML
    public Text paymentMessage;

    private static void enoughMoney(String cardOwnerName,String expM,String expY,String cardN,String CVC) throws NotEnoughMoneyException {
        int tax = 20;

        for(Bank bank : BankService.getBankRepository().find()){
            if(cardOwnerName.equals(bank.getNumeDetinator()) && expM.equals(bank.getLuna()) && expY.equals(bank.getAnu()) && cardN.equals(bank.getNumarCard()) && CVC.equals(bank.getCVC())){
                if((tax > Float.parseFloat(bank.getSum()))){
                    throw new NotEnoughMoneyException();
                }else{
                    System.out.println("ALL GOOD");
                    bank.setSum(Float.toString(Float.parseFloat(bank.getSum()) - tax) );
                    BankService.getBankRepository().update(bank);
                }
            }
        }
    }

    public void handlePayButton(ActionEvent actionEvent) {
        String username = LoginController.getCurrentUsername();
        GymManager manager = GymManagerProfileController.getManagerFromDatabase(username);

        if(!manager.getHasPaid()) {
            try {

                CommonFunctionality.CVCException(cvcTextField.getText());
                CommonFunctionality.cardNumberException(cardNumberTextField.getText());
                CommonFunctionality.cardDetailsException(ownerNameTextField.getText(), monthChoiceBox.getValue().toString(),
                        yearChoiceBox.getValue().toString(), cardNumberTextField.getText(),
                        cvcTextField.getText());
                enoughMoney(ownerNameTextField.getText(), monthChoiceBox.getSelectionModel().getSelectedItem().toString()
                        , yearChoiceBox.getSelectionModel().getSelectedItem().toString(), cardNumberTextField.getText(),
                        cvcTextField.getText());

                paymentMessage.setText("Successful payment");

                manager.setHasPaid(true);
                GymManagerService.getGymManagerRepository().update(manager);
            } catch (IncorectCVCException | IncorectCardNumberException | incorectCardDetailsException | NotEnoughMoneyException e) {
                paymentMessage.setText(e.getMessage());
            } finally {
                if (cvcTextField.getText().isEmpty() || ownerNameTextField.getText().isEmpty() || cardNumberTextField.getText().isEmpty()) {
                    paymentMessage.setText("Please complete all the fields");
                }
            }
        }
        else{
            paymentMessage.setText("You have already paid");
        }
    }

    public void handleBackButton(ActionEvent actionEvent) {
        CommonFunctionality cf = new CommonFunctionality();
        cf.openNewScene("GymManagerProfile.fxml", backButton, 800, 500);
    }
}
