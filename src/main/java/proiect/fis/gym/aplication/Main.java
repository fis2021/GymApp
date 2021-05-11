package proiect.fis.gym.aplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import proiect.fis.gym.aplication.services.CustomerService;
import proiect.fis.gym.aplication.services.FileSystemService;
import proiect.fis.gym.aplication.services.LoginService;
import proiect.fis.gym.aplication.services.*;


import proiect.fis.gym.aplication.services.AdminService;


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
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
       // BankService.addBank("Radu Mihai","01","21","1234567812342323","303","20000");
        //BankService.addBank("Andrei Marian","01","21","1234567812345678","111","10");
        //Parent root = FXMLLoader.load(getClass().getResource("fxml/CustomerAfterLoginPage.fxml"));
        primaryStage.setTitle("Gym Aplication");
        primaryStage.setScene(new Scene(root, 800, 500));
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
