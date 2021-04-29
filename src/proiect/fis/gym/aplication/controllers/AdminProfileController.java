package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.GymManagerService;

public class AdminProfileController {
    @FXML
    public TableView managersTableView;
    TableColumn<GymManager, String> gymColumn = new TableColumn<>("Gym");

    @FXML
    public void initialize(){
        loadRegisteredManagers();
        addButtonToTable("APPLY TAX");
    }

    private void loadRegisteredManagers(){
        gymColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        gymColumn.setMinWidth(250);
        managersTableView.getColumns().add(gymColumn);

        for(GymManager manager:
                GymManagerService.getGymManagerRepository().find()){
            managersTableView.getItems().add(manager);
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

        managersTableView.getColumns().add(colBtn);

    }

    CommonFunctionality cf = new CommonFunctionality();
    public void handleLogOutButton(ActionEvent actionEvent) {
        cf.openNewScene("login.fxml", managersTableView);
    }
}
