package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;

import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


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

    public void openNewScene(String fxmlLoaded, Control control,int a, int b){
        try {
            Stage stage = (Stage) control.getScene().getWindow();
            String path = "fxml/" + fxmlLoaded;
            //System.out.println(path);
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Scene scene = new Scene(viewRegisterRoot, a, b);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
