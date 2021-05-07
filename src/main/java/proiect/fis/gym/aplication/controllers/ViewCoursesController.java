package proiect.fis.gym.aplication.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import proiect.fis.gym.aplication.exceptions.CourseAlreadyExistException;
import proiect.fis.gym.aplication.exceptions.FieldsAreNotEmptyException;
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
    public Button editButton;
    @FXML
    public Button stopEditButton;
    @FXML
    public Label warningLabel;
    @FXML
    public Button yesButton;
    @FXML
    public Button noButton;
    TableColumn<Course, String> column1 = new TableColumn<>("Course");
    TableColumn<Course, String> column2 = new TableColumn<>("Trainer");
    TableColumn<Course, String> column3 = new TableColumn<>("Schedule");

    @FXML
    private void initialize(){
        fillCoursesListView();
    }

    public void handleBackToProfileButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) backToProfileButton.getScene().getWindow();
            String path = "fxml/" + "GymManagerProfile.fxml";
            //System.out.println(path);
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Scene scene = new Scene(viewRegisterRoot, 800, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillCoursesListView(){
        GymManager manager = GymManagerProfileController.getManagerFromDatabase(LoginController.getCurrentUsername());

        column1.setMinWidth(300);
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column1.setOnEditCommit(this::changeNameCellEvent);

        column2.setMinWidth(200);
        column2.setCellValueFactory(new PropertyValueFactory<>("trainer"));
        column2.setOnEditCommit(this::changeTrainerCellEvent);

        column3.setMinWidth(200);
        column3.setCellValueFactory(new PropertyValueFactory<>("schedule"));
        column3.setOnEditCommit(this::changeScheduleCellEvent);

        //coursesTableView.setEditable(true);
        if(coursesTableView.getColumns() != null) {
            coursesTableView.getColumns().add(column1);
            coursesTableView.getColumns().add(column2);
            coursesTableView.getColumns().add(column3);
            addButtonToTable("DELETE");
        }

        if(manager != null){
            for(Course course: manager.getCourseList()){
                coursesTableView.getItems().add(course);
            }
        }
    }

    public void handleEditButton(ActionEvent actionEvent) {
        coursesTableView.setEditable(true);
        column1.setCellFactory(TextFieldTableCell.forTableColumn());
        column2.setCellFactory(TextFieldTableCell.forTableColumn());
        column3.setCellFactory(TextFieldTableCell.forTableColumn());

        stopEditButton.setDisable(false);
    }

    public void changeNameCellEvent(TableColumn.CellEditEvent edittedCell){
        Course courseSelected = (Course)coursesTableView.getSelectionModel().getSelectedItem();
        
        try {
            if (!edittedCell.getNewValue().toString().equals("")) {
                GymManager manager = GymManagerProfileController.getManagerFromDatabase(LoginController.getCurrentUsername());
                //testam daca cursul nu e duplicat:
                Course courseEditted = new Course(edittedCell.getNewValue().toString(), courseSelected.getTrainer(), courseSelected.getSchedule());
                if(manager.findCourse(courseEditted)){
                    throw new CourseAlreadyExistException();
                }

                //update in baza de data la current entry:
                manager.getCourseFromList(courseSelected).setName(edittedCell.getNewValue().toString());
                GymManagerService.getGymManagerRepository().update(manager);

                //modificam tabelul:
                courseSelected.setName(edittedCell.getNewValue().toString());
                warningLabel.setVisible(false);
            }else{
                throw new FieldsAreNotEmptyException();
            }
        }catch (FieldsAreNotEmptyException | CourseAlreadyExistException e){
            warningLabel.setVisible(true);
            warningLabel.setText(e.getMessage());
        }
    }

    public void changeTrainerCellEvent(TableColumn.CellEditEvent edittedCell){
        Course courseSelected = (Course)coursesTableView.getSelectionModel().getSelectedItem();

        try {
            if (!edittedCell.getNewValue().toString().equals("")) {
                GymManager manager = GymManagerProfileController.getManagerFromDatabase(LoginController.getCurrentUsername());
                //testam daca cursul nu e duplicat:
                Course courseEditted = new Course(courseSelected.getName(), edittedCell.getNewValue().toString(), courseSelected.getSchedule());
                if(manager.findCourse(courseEditted)){
                    throw new CourseAlreadyExistException();
                }

                //update in baza de data la current entry:
                manager.getCourseFromList(courseSelected).setTrainer(edittedCell.getNewValue().toString());
                GymManagerService.getGymManagerRepository().update(manager);

                //modificam tabelul:
                courseSelected.setTrainer(edittedCell.getNewValue().toString());
                warningLabel.setVisible(false);
            } else {
                throw new FieldsAreNotEmptyException();
            }
        }catch (FieldsAreNotEmptyException | CourseAlreadyExistException e){
            warningLabel.setVisible(true);
            warningLabel.setText(e.getMessage());
        }
    }

    public void changeScheduleCellEvent(TableColumn.CellEditEvent edittedCell){
        Course courseSelected = (Course)coursesTableView.getSelectionModel().getSelectedItem();

        try {
            if(!edittedCell.getNewValue().toString().equals("")) {
                GymManager manager = GymManagerProfileController.getManagerFromDatabase(LoginController.getCurrentUsername());
                //testam daca cursul nu e duplicat:
                Course courseEditted = new Course(courseSelected.getName(), courseSelected.getTrainer(), edittedCell.getNewValue().toString());
                if(manager.findCourse(courseEditted)){
                    throw new CourseAlreadyExistException();
                }

                //update in baza de data la current entry:
                manager.getCourseFromList(courseSelected).setSchedule(edittedCell.getNewValue().toString());
                GymManagerService.getGymManagerRepository().update(manager);

                //modificam tabelul:
                courseSelected.setSchedule(edittedCell.getNewValue().toString());
                warningLabel.setVisible(false);
            }else{
                throw new FieldsAreNotEmptyException();
            }
        }catch (FieldsAreNotEmptyException | CourseAlreadyExistException e){
            warningLabel.setVisible(true);
            warningLabel.setText(e.getMessage());
        }
    }

    public void handleStopEditButton(ActionEvent actionEvent) {
        coursesTableView.setEditable(false);
        stopEditButton.setDisable(true);
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
                            //stergem din baza de date:
                            GymManager manager = GymManagerProfileController.getManagerFromDatabase(LoginController.getCurrentUsername());
                            Course deletedCourse = getTableView().getItems().get(getIndex());

                            if(manager.findCourse(deletedCourse)){
                                manager.getCourseList().remove(manager.getCourseFromList(deletedCourse));
                                GymManagerService.getGymManagerRepository().update(manager);
                            }
                            //stergem din tabel:
                            coursesTableView.getItems().remove(deletedCourse);
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