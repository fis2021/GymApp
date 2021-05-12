package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import proiect.fis.gym.aplication.exceptions.CourseAlreadyExistException;
import proiect.fis.gym.aplication.exceptions.FieldsAreNotEmptyException;
import proiect.fis.gym.aplication.exceptions.IncorectLoginException;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.GymManagerService;
import proiect.fis.gym.aplication.services.LoginService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GymManagerProfileController {
    @FXML
    private Label gymNameLabel;
    @FXML
    private Label managerNameLabel;
    @FXML
    private Label locationLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private GridPane courseGridPane;
    @FXML
    private Button submitNewCourseButton;
    @FXML
    private TextField courseNameTextField;
    @FXML
    private TextField trainerNameTextField;
    @FXML
    private TextField scheduleTextField;
    @FXML
    private Label errorMessageAddCourseLabel;
    @FXML
    private ImageView profilePicImageView;
    @FXML
    private Label taxWarningLabel;

    private String currentUserName;

    @FXML
    private Text text;

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
        cf.openNewScene("login.fxml", gymNameLabel, 800, 500);
    }

    public void handleViewCoursesButton(ActionEvent actionEvent) {
        cf.openNewScene("ViewCourses.fxml", gymNameLabel, 900, 800);
    }

    public void handleViewCustomersButton(ActionEvent actionEvent) {
        cf.openNewScene("ViewCustomers.fxml", gymNameLabel, 900, 800);
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
        else if(!manager.isTaxed()){
            taxWarningLabel.setVisible(true);
            taxWarningLabel.setText("You have not been taxed yet");
        }
        else{
            //deschidem o scena pt plata taxei:
            CommonFunctionality cf = new CommonFunctionality();
            cf.openNewScene("PayTheTax.fxml", taxWarningLabel, 600,500);
        }
    }
}