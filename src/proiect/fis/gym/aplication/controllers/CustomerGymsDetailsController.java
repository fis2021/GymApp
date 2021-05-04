package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import jdk.nashorn.internal.objects.annotations.Where;
import org.dizitart.no2.objects.ObjectRepository;
import proiect.fis.gym.aplication.exceptions.*;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.model.Review;
import proiect.fis.gym.aplication.services.CustomerService;
import proiect.fis.gym.aplication.services.GymManagerService;

import java.io.IOException;

public class CustomerGymsDetailsController{

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

    @FXML
    public Label joinedCourses;


    private static ObjectRepository<Customer> customerRepository=CustomerService.getCustomerRepository();
    TableColumn<Course, String> column1 = new TableColumn<>("Course");
    TableColumn<Course, String> column2 = new TableColumn<>("Trainer");
    TableColumn<Course, String> column3 = new TableColumn<>("Schedule");
    TableColumn<Review, String> column4 = new TableColumn<>("Reviews");

    @FXML
    private void initialize(){
        fillCoursesListView1();
        fillReviewList();
    }

    public void fillReviewList(){
        GymManager manager = GymManagerProfileController.getManagerFromDatabase("SmartFit");
        column4.setMinWidth(790);
        column4.setCellValueFactory(new PropertyValueFactory<>("review"));

        if(reviewTableView.getColumns() != null) {
            reviewTableView.getColumns().add(column4);
        }

        if(manager != null){
            if(manager.getReviewList() != null) {
                for (Review review : manager.getReviewList()) {
                    reviewTableView.getItems().add(review);
                }
            }
        }
    }

    public void fillCoursesListView1(){
        GymManager manager = GymManagerProfileController.getManagerFromDatabase("SmartFit");

        column1.setMinWidth(300);
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));


        column2.setMinWidth(223);
        column2.setCellValueFactory(new PropertyValueFactory<>("trainer"));


        column3.setMinWidth(223);
        column3.setCellValueFactory(new PropertyValueFactory<>("schedule"));


        //coursesTableView.setEditable(true);
        if(coursesTableView.getColumns() != null) {
            coursesTableView.getColumns().add(column1);
            coursesTableView.getColumns().add(column2);
            coursesTableView.getColumns().add(column3);
            addButtonToTable("Join");

        }

        if(manager != null){
            for(Course course: manager.getCourseList()){
                coursesTableView.getItems().add(course);
            }
        }
    }

    private void addButtonToTable(String buttonText) {
        TableColumn<Course, Void> colBtn = new TableColumn();

        Callback<TableColumn<Course, Void>, TableCell<Course, Void>> cellFactory = new Callback<TableColumn<Course, Void>, TableCell<Course, Void>>() {
            @Override
            public TableCell<Course, Void> call(final TableColumn<Course, Void> param) {
                final TableCell<Course, Void> cell = new TableCell<Course, Void>() {

                    //functionalitate pt butonul de delete:
                    private final Button btn = new Button(buttonText);
                    {
                        btn.setOnAction((ActionEvent event) -> {

                            int ok=0;
                            String username = LoginController.getCurrentUsername();
                            for(Customer customer : customerRepository.find()){
                                if(username.equals(customer.getUsername())){
                                    Course joinedCourse = getTableView().getItems().get(getIndex());
                                    try {
                                        CustomerService.checkActive(customer,"SmartFit");
                                        CustomerService.alreadyJoined(customer, joinedCourse);
                                    } catch (CheckJoinedCourse e) {
                                        joinedCourses.setText(e.getMessage());
                                        break;
                                    } catch (noActiveSubscriptionException e) {
                                        joinedCourses.setText(e.getMessage());
                                        break;
                                    }
                                    customer.addSmartfitCourse(joinedCourse);
                                    customerRepository.update(customer);
                                    ok=1;
                                    break;
                                }
                            }
                            if(ok==1) {
                                joinedCourses.setText("Congrats " + LoginController.getCurrentUsername() + " you joined a course");
                            }


                        });

                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        coursesTableView.getColumns().add(colBtn);

    }

    public void backToLogin(){
        try {
            Stage stage =(Stage) warningLabel.getScene().getWindow();
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource("../fxml/login.fxml"));
            Scene scene = new Scene(viewRegisterRoot, 800, 500);
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
        GymManager currentManager = getManagerFromDatabase("SmartFit");

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
            Scene scene = new Scene(viewRegisterRoot, 800, 600);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
