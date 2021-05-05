package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.exceptions.CourseAlreadyExistException;
import proiect.fis.gym.aplication.exceptions.FieldsAreNotEmptyException;
import proiect.fis.gym.aplication.exceptions.IncorectLoginException;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.GymManagerService;
import proiect.fis.gym.aplication.services.LoginService;

import javax.naming.ldap.Control;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    @FXML
    public ImageView profilePicImageView;
    @FXML
    public Label taxWarningLabel;

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

            if(manager.getProfilePicURL() != null) {
                try {
                    String path = manager.getProfilePicURL();
                    Image newPhoto = new Image(new FileInputStream(path));
                    profilePicImageView.setImage(newPhoto);
                } catch (FileNotFoundException e) {
                    //e.printStackTrace();
                }
            }
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


    CommonFunctionality cf = new CommonFunctionality();
    public void handleLogOutButton(ActionEvent actionEvent) {
        cf.openNewScene("login.fxml", gymNameLabel);
    }

    public void handleViewCoursesButton(ActionEvent actionEvent) {
        cf.openNewScene("ViewCourses.fxml", gymNameLabel);
    }

    public void handleViewCustomersButton(ActionEvent actionEvent) {
        cf.openNewScene("ViewCustomers.fxml", gymNameLabel);
    }

    public void handleAddProfilePhotoButton(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Images", "*.jpeg", "*.jpg", "*.gif","*.tiff", "*.tif", "*.png", "*.jtif" ));

        File selectedImage = fc.showOpenDialog(null);

        if(selectedImage != null){
            //System.out.println("\\" + selectedImage.getAbsolutePath());
            String path = "\\" + selectedImage.getAbsolutePath();
            try {
                // schimbam poza din image view
                Image newPhoto = new Image(new FileInputStream(path));
                profilePicImageView.setImage(newPhoto);

                //punem url nou pt poza in baza de date:
                GymManager manager = getManagerFromDatabase(LoginController.getCurrentUsername());
                if(manager != null){
                    manager.setProfilePicURL(path);
                    GymManagerService.getGymManagerRepository().update(manager);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        else{
            System.out.println("NULL");
        }
    }

    public void handlePayTheTaxButton(ActionEvent actionEvent) {
        GymManager manager = getManagerFromDatabase(LoginController.getCurrentUsername());
        if(manager.getHasPaid()){
            taxWarningLabel.setVisible(true);
        }
        else{
            //deschidem o scena pt plata taxei:
            CommonFunctionality cf = new CommonFunctionality();
            cf.openNewScene("PayTheTax.fxml", taxWarningLabel);
        }
    }
}