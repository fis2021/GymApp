package proiect.fis.gym.aplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.*;

import java.nio.file.Files;
import java.nio.file.Path;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        initDirectory();
        CustomerService.initDatabase();
        LoginService.initDatabase();
        GymManagerService.initDatabase();
        AdminService.initDatabase();
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        primaryStage.setTitle("Gym Aplication");
        primaryStage.setScene(new Scene(root, 400, 400));
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
