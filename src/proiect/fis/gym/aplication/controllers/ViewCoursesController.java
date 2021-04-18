package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import proiect.fis.gym.aplication.exceptions.IncorectLoginException;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.GymManagerService;

import java.io.IOException;

public class ViewCoursesController {

    @FXML
    public Button backToProfileButton;
    @FXML
    public TableView coursesTableView;


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

        TableColumn<Course, String> column1 = new TableColumn<>("Course");
        column1.setMinWidth(150);
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Course, String> column2 = new TableColumn<>("Trainer");
        column2.setMinWidth(100);
        column2.setCellValueFactory(new PropertyValueFactory<>("trainer"));

        TableColumn<Course, String> column3 = new TableColumn<>("Schedule");
        column3.setMinWidth(70);
        column3.setCellValueFactory(new PropertyValueFactory<>("schedule"));

        coursesTableView.getColumns().add(column1);
        coursesTableView.getColumns().add(column2);
        coursesTableView.getColumns().add(column3);
        addButtonToTable("EDIT");
        addButtonToTable("SAVE");

        //coursesTableView.getColumns().add(editButtonColumn);
        //coursesTableView.getColumns().add(column5);

        if(manager != null){
            for(Course course: manager.getCourseList()){
                coursesTableView.getItems().add(course);
            }
        }
    }

    //adauga buton pt fiecare linie din tabel
    private void addButtonToTable(String text) {
        TableColumn<Course, Void> colBtn = new TableColumn();

        Callback<TableColumn<Course, Void>, TableCell<Course, Void>> cellFactory = new Callback<TableColumn<Course, Void>, TableCell<Course, Void>>() {
            @Override
            public TableCell<Course, Void> call(final TableColumn<Course, Void> param) {
                final TableCell<Course, Void> cell = new TableCell<Course, Void>() {

                    private final Button btn = new Button(text);
                    {
                        //login for edit button
                        btn.setOnAction((ActionEvent event) -> {

                        });

                        //logic for save button
                        btn.setOnAction((ActionEvent event) -> {

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
}