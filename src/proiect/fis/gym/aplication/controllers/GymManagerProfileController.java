package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.exceptions.IncorectLoginException;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.GymManagerService;

import javax.naming.ldap.Control;
import java.io.IOException;

import static proiect.fis.gym.aplication.services.LoginService.login;

public class GymManagerProfileController {
    @FXML
    public Label gymNameLabel;
    @FXML
    public Label managerNameLabel;
    @FXML
    public Label locationLabel;
    @FXML
    public Label emailLabel;
    @FXML
    public Label phoneLabel;
    @FXML
    public GridPane courseGridPane;
    @FXML
    public Button submitNewCourseButton;

    private String currentUserName;


    @FXML
    private void initialize() throws IncorectLoginException {
        currentUserName = LoginController.getCurrentUsername();
        //System.out.println(currentUserName);
        if(currentUserName != null) {
            modifyCurrentManagerProfile(currentUserName);
        }
    }

    private void modifyCurrentManagerProfile(String username) throws IncorectLoginException {
        for(GymManager manager : GymManagerService.getGymManagerRepository().find()){
            if(username.equals(manager.getUsername()) ){
                gymNameLabel.setText(manager.getCompanyName());
                managerNameLabel.setText(manager.getFirstName() + " " + manager.getLastName());
                locationLabel.setText(manager.getGymLocation());
                phoneLabel.setText(manager.getPhoneNumber());
                emailLabel.setText(manager.getEmail());
                break;
            }
        }
    }

    public void handleAddCourseButton(ActionEvent actionEvent) {
        courseGridPane.setVisible(true);
        submitNewCourseButton.setVisible(true);
    }

    private void openNewScene(String fxmlLoaded){
       try {
            Stage stage = (Stage) submitNewCourseButton.getScene().getWindow();
            String path = "../fxml/" + fxmlLoaded;
            //System.out.println(path);
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(viewRegisterRoot, 600, 400);
            stage.setScene(scene);
       } catch (IOException e) {
            e.printStackTrace();
       }
    }

    public void handleLogOutButton(ActionEvent actionEvent) {
        openNewScene("login.fxml");
    }

    public void handleViewCoursesButton(ActionEvent actionEvent) {
        openNewScene("ViewCourses.fxml");
    }
}
