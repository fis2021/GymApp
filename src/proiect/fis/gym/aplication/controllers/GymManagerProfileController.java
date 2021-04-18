package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.exceptions.CourseAlreadyExistException;
import proiect.fis.gym.aplication.exceptions.FieldsAreNotEmptyException;
import proiect.fis.gym.aplication.exceptions.IncorectLoginException;
import proiect.fis.gym.aplication.model.Course;
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
    @FXML
    public TextField courseNameTextField;
    @FXML
    public TextField trainerNameTextField;
    @FXML
    public TextField scheduleTextField;
    @FXML
    public Label errorMessageAddCourseLabel;

    private String currentUserName;


    @FXML
    private void initialize() throws IncorectLoginException {
        currentUserName = LoginController.getCurrentUsername();
        //System.out.println(currentUserName);
        if(currentUserName != null) {
            modifyCurrentManagerProfile(currentUserName);
        }
    }

    public static GymManager getManagerFromDatabase(String username){
        for(GymManager manager : GymManagerService.getGymManagerRepository().find()) {
            if (username.equals(manager.getUsername())) {
                return manager;
            }
        }
        return null;
    }

    private void modifyCurrentManagerProfile(String username) throws IncorectLoginException {
        GymManager manager = getManagerFromDatabase(username);
        if(manager != null){
            gymNameLabel.setText(manager.getCompanyName());
            managerNameLabel.setText(manager.getFirstName() + " " + manager.getLastName());
            locationLabel.setText(manager.getGymLocation());
            phoneLabel.setText(manager.getPhoneNumber());
            emailLabel.setText(manager.getEmail());
        }
    }

    public void handleAddCourseButton(ActionEvent actionEvent) {
        courseGridPane.setVisible(true);
        submitNewCourseButton.setVisible(true);
    }

    public void handleSubmitNewCourseButton(ActionEvent actionEvent){
        Course toBeAdded;
        GymManager currentManager = getManagerFromDatabase(currentUserName);

        //testam daca sunt valide casutele de nume, antrenor, program
        try {
            if(CommonFunctionality.checkTextFieldsInAPaneAreNotEmpty(courseGridPane)) {
                toBeAdded = new Course(courseNameTextField.getText(), trainerNameTextField.getText(), scheduleTextField.getText());

                if (!currentManager.findCourse(toBeAdded)) {
                    currentManager.getCourseList().add(toBeAdded);
                }
                else {
                    throw new CourseAlreadyExistException();
                }

                GymManagerService.getGymManagerRepository().update(currentManager);
                errorMessageAddCourseLabel.setVisible(false);
            }
            else{
                throw new FieldsAreNotEmptyException();
            }
        }
        catch(FieldsAreNotEmptyException | CourseAlreadyExistException e){
            errorMessageAddCourseLabel.setVisible(true);
            errorMessageAddCourseLabel.setText(e.getMessage());
        }
    }

    private void openNewScene(String fxmlLoaded){
       try {
            Stage stage = (Stage) submitNewCourseButton.getScene().getWindow();
            String path = "../fxml/" + fxmlLoaded;
            //System.out.println(path);
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(viewRegisterRoot, 800, 800);
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