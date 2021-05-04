package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.exceptions.FieldsAreNotEmptyException;
import proiect.fis.gym.aplication.exceptions.IncorectCVCException;
import proiect.fis.gym.aplication.exceptions.IncorectCardNumberException;
import proiect.fis.gym.aplication.exceptions.incorectCardDetailsException;
import proiect.fis.gym.aplication.model.Bank;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.BankService;

import java.io.IOException;
import java.util.regex.Pattern;


public class CommonFunctionality {
    
    //checks if the text fields from a pane are empty
    public static boolean checkTextFieldsInAPaneAreNotEmpty(Pane pane) {
        for(Node node: pane.getChildren()){
            if(node instanceof TextField){
                if(((TextField)node).getText().isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }


    public static boolean checkTextAreaInAPaneAreNotEmpty(Pane pane) {
        for(Node node: pane.getChildren()){
            if(node instanceof TextArea){
                if(((TextArea)node).getText().isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    public void openNewScene(String fxmlLoaded, Control control){
        try {
            Stage stage = (Stage) control.getScene().getWindow();
            String path = "../fxml/" + fxmlLoaded;
            //System.out.println(path);
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource(path));

            int width = 900;
            int height = 800;

            if(fxmlLoaded.equals("login.fxml")){
                height = 600;
            }

            Scene scene = new Scene(viewRegisterRoot, width, height);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void CVCException(String CVC) throws IncorectCVCException {
        if(CVC.length()!=3){
            throw new IncorectCVCException();
        }
    }

    public static void cardNumberException(String cardN) throws IncorectCardNumberException {
        String regex= "^\\d{16}$";
        Pattern pat = Pattern.compile(regex);
        if( !(pat.matcher(cardN).matches()) )
            throw new IncorectCardNumberException();
    }

    public static void cardDetailsException(String cardOwnerName,String expM,String expY,String cardN,String CVC) throws incorectCardDetailsException {
        int ok=0;
        for(Bank bank : BankService.getBankRepository().find()){
            if(cardOwnerName.equals(bank.getNumeDetinator()) && expM.equals(bank.getLuna()) && expY.equals(bank.getAnu()) && cardN.equals(bank.getNumarCard()) && CVC.equals(bank.getCVC())){
                ok=1;
                break;
            }
        }
        if(ok==0)
            throw new incorectCardDetailsException();
    }
}
