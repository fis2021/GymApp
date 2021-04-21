package proiect.fis.gym.aplication.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.GymManager;

public class GymOneDatailsController {

    @FXML
    public TableView coursesTableView;

    @FXML
    public Label warningLabel;

    TableColumn<Course, String> column1 = new TableColumn<>("Course");
    TableColumn<Course, String> column2 = new TableColumn<>("Trainer");
    TableColumn<Course, String> column3 = new TableColumn<>("Schedule");

    @FXML
    private void initialize(){
        fillCoursesListView3();
    }

    public void fillCoursesListView3(){
        GymManager manager = GymManagerProfileController.getManagerFromDatabase("GymOne");

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

}
