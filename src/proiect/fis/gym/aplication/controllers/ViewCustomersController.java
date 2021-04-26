package proiect.fis.gym.aplication.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import proiect.fis.gym.aplication.model.Course;
import proiect.fis.gym.aplication.model.Customer;
import proiect.fis.gym.aplication.model.GymManager;
import proiect.fis.gym.aplication.services.CustomerService;
import proiect.fis.gym.aplication.services.LoginService;

import java.io.IOException;

public class ViewCustomersController {
    @FXML
    public TableView customersTableView;
    TableColumn<Customer, String> firstNameColumn = new TableColumn<>("First Name");
    TableColumn<Customer, String> lastNameColumn = new TableColumn<>("Last Name");
    TableColumn<Customer, String> phoneColumn = new TableColumn<>("Phone");
    TableColumn<Customer, String> emailColumn = new TableColumn<>("Email");

    @FXML
    public void initialize(){
        fillCustomersTable();
    }

    private void fillCustomersTable(){
        firstNameColumn.setMinWidth(200);
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        lastNameColumn.setMinWidth(200);
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        phoneColumn.setMinWidth(130);
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        emailColumn.setMinWidth(270);
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        if(customersTableView.getColumns() != null) {
            customersTableView.getColumns().add(firstNameColumn);
            customersTableView.getColumns().add(lastNameColumn);
            customersTableView.getColumns().add(phoneColumn);
            customersTableView.getColumns().add(emailColumn);
        }

        //adaugam clientii din baza de data in tabel
        GymManager currentManager = GymManagerProfileController.getManagerFromDatabase(LoginController.getCurrentUsername());
        for(Customer customer : CustomerService.getCustomerRepository().find()){
            for(String gym: customer.getGym2()){
                if(currentManager.getUsername().equals(gym)){
                    customersTableView.getItems().add(customer);
                }
            }
        }
    }

    public void handleBackToProfileButton(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) customersTableView.getScene().getWindow();
            String path = "../fxml/" + "GymManagerProfile.fxml";
            Parent viewRegisterRoot = FXMLLoader.load(getClass().getResource(path));
            Scene scene = new Scene(viewRegisterRoot, 800, 800);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
