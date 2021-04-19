package proiect.fis.gym.aplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.*;

import proiect.fis.gym.aplication.services.AdminService;
import proiect.fis.gym.aplication.services.FileSystemService;
import proiect.fis.gym.aplication.services.CustomerService;
import proiect.fis.gym.aplication.services.LoginService;


import java.nio.file.Files;
import java.nio.file.Path;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        initDirectory();
        BankService.initDatabase();
        AdminService.initDatabase();
        CustomerService.initDatabase();
        GymManagerService.initDatabase();
        LoginService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/CustomerAfterLoginPage.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("fxml/CustomerAfterLoginPage.fxml"));
        /*BankService.addBank("Radu Mihai","01","21","1234567812342323","303","5000");
        BankService.addBank("Marcel Andrei","07","24","1255599913330103","987","5000");
        BankService.addBank("Pavel Szabo","12","23","0223555518129999","031","10");
        BankService.addBank("Stefan Lucian","10","22","7453512319870111","645","5000");*/
        primaryStage.setTitle("Gym Aplication");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    private void initDirectory() {
        Path applicationHomePath = FileSystemService.APPLICATION_HOME_PATH;
        if (!Files.exists(applicationHomePath))
            applicationHomePath.toFile().mkdirs();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
