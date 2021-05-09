package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.GymManagerService;
import proiect.fis.gym.aplication.services.AdminService;

public class AdminProfileController {
    @FXML
    private Text forTest;
    @FXML
    public TableView managersTableView;
    @FXML
    public Label warningLabel;
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
        TableColumn<GymManager, Void> colBtn = new TableColumn();

        Callback<TableColumn<GymManager, Void>, TableCell<GymManager, Void>> cellFactory = new Callback<TableColumn<GymManager, Void>, TableCell<GymManager, Void>>() {
            @Override
            public TableCell<GymManager, Void> call(final TableColumn<GymManager, Void> param) {
                final TableCell<GymManager, Void> cell = new TableCell<GymManager, Void>() {

                    //functionalitate pt butonul de delete:
                    private final Button btn = new Button(buttonText);
                    {
                        //aplicam taxa
                        btn.setOnAction((ActionEvent event) -> {
                            GymManager selectedGym = getTableView().getItems().get(getIndex());

                            AdminService.taxGym(selectedGym, warningLabel);

                            //facem update in baza de date
                            GymManagerService.getGymManagerRepository().update(selectedGym);
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
        cf.openNewScene("login.fxml", managersTableView, 800, 500);
    }
}
