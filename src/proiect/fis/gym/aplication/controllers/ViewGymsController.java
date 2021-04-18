package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.exceptions.IncorectLoginException;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.GymManagerService;

import java.io.IOException;

public class ViewGymsController {

    @FXML
    public Button backToProfileButton;
    @FXML
    public ListView coursesListView;

    @FXML
    private void initialize(){
        fillCoursesListView();
    }

    public void handleBackToProfileButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) backToProfileButton.getScene().getWindow();
            String path = "../fxml/" + "GymManagerProfile.fxml";
            //System.out.println(path);
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(viewRegisterRoot, 800, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillCoursesListView(){
        GymManager manager = GymManagerProfileController.getManagerFromDatabase(LoginController.getCurrentUsername());
        if(manager != null){
            for(Course course: manager.getCourseList()){
                coursesListView.getItems().add(course.toString());
            }
        }
    }
}