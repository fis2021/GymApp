package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.exceptions.EmptyTextArea;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.model.Review;
import proiect.fis.gym.aplication.services.GymManagerService;

import java.io.IOException;

public class IguanaDetailsController {

    @FXML
    public TableView coursesTableView,reviewTableView;

    @FXML
    public Label warningLabel;

    @FXML
    public GridPane reviewGridPane;

    @FXML
    public Button submitNewReviewButton;

    @FXML
    public TextArea textArea;

    @FXML
    public Label errorMessageAddCourseLabel;

    @FXML
    public Label message;

    TableColumn<Course, String> column1 = new TableColumn<>("Course");
    TableColumn<Course, String> column2 = new TableColumn<>("Trainer");
    TableColumn<Course, String> column3 = new TableColumn<>("Schedule");
    TableColumn<Review, String> column4 = new TableColumn<>("Reviews");

    @FXML
    private void initialize(){
        fillCoursesListView2();
        fillReviewList2();
    }

    public void fillReviewList2(){
        GymManager manager = GymManagerProfileController.getManagerFromDatabase("Iguana");
        column4.setMinWidth(300);
        column4.setCellValueFactory(new PropertyValueFactory<>("review"));

        if(reviewTableView.getColumns() != null) {
            reviewTableView.getColumns().add(column4);
        }

        if(manager != null){
            for(Review review: manager.getReviewList()){
                reviewTableView.getItems().add(review);
            }
        }
    }

    public void fillCoursesListView2(){
        GymManager manager = GymManagerProfileController.getManagerFromDatabase("Iguana");

        column1.setMinWidth(300);
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));


        column2.setMinWidth(200);
        column2.setCellValueFactory(new PropertyValueFactory<>("trainer"));


        column3.setMinWidth(200);
        column3.setCellValueFactory(new PropertyValueFactory<>("schedule"));


        //coursesTableView.setEditable(true);
        if(coursesTableView.getColumns() != null) {
            coursesTableView.getColumns().add(column1);
            coursesTableView.getColumns().add(column2);
            coursesTableView.getColumns().add(column3);

        }

        if(manager != null){
            for(Course course: manager.getCourseList()){
                coursesTableView.getItems().add(course);
            }
        }
    }

    public void backToLogin(){
        try {
            Stage stage =(Stage) warningLabel.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 700, 500);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAddReviewButton(){
        reviewGridPane.setVisible(true);
        submitNewReviewButton.setVisible(true);
        message.setVisible(false);
    }

    public static GymManager getManagerFromDatabase(String username){
        for(GymManager manager : GymManagerService.getGymManagerRepository().find()) {
            if (username.equals(manager.getUsername())) {
                return manager;
            }
        }
        return null;
    }

    public void handleSubmitReviewButton(){
        Review toBeAdded;
        GymManager currentManager = getManagerFromDatabase("Iguana");

        try {
            if(CommonFunctionality.checkTextAreaInAPaneAreNotEmpty(reviewGridPane)) {
                toBeAdded = new Review(textArea.getText());
                currentManager.getReviewList().add(toBeAdded);
                GymManagerService.getGymManagerRepository().update(currentManager);
                reviewTableView.getItems().add(toBeAdded);
                errorMessageAddCourseLabel.setVisible(false);
                reviewGridPane.setVisible(false);
                submitNewReviewButton.setVisible(false);
                message.setVisible(true);
                message.setText("Review submitted successfully");
            }
            else{
                throw new EmptyTextArea();
            }
        } catch(EmptyTextArea e){
            errorMessageAddCourseLabel.setVisible(true);
            errorMessageAddCourseLabel.setText(e.getMessage());
        }

    }

    public void handleBackToMainPageButton(){
        try {
            Stage stage =(Stage) warningLabel.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/CustomerAfterLoginPage.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 800, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
