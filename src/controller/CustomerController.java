package controller;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Customer;
import repository.CustomerRepository;
import util.AlertUtil;

public class CustomerController {

    @FXML
    private TableView<Customer> tableCustomers;

    @FXML
    private TableColumn<Customer, String> colId;

    @FXML
    private TableColumn<Customer, String> colName;

    @FXML
    private TableColumn<Customer, String> colPhone;

    @FXML
    private TableColumn<Customer, String> colEmail;

    @FXML
    private TableColumn<Customer, String> colCnic;

    @FXML
    private TableColumn<Customer, String> colAddress;

    @FXML
    private TextField txtSearch;

    private final CustomerRepository repo = new CustomerRepository();

    private final ObservableList<Customer> masterData =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colName.setCellValueFactory(
                new PropertyValueFactory<>("name"));

        colPhone.setCellValueFactory(
                new PropertyValueFactory<>("phone"));

        colEmail.setCellValueFactory(
                new PropertyValueFactory<>("email"));

        colCnic.setCellValueFactory(
                new PropertyValueFactory<>("cnic"));

        colAddress.setCellValueFactory(
                new PropertyValueFactory<>("address"));

        loadTable();

        txtSearch.textProperty().addListener(
                (obs, oldValue, newValue) ->
                        filterCustomers(newValue));
    }

    private void loadTable() {

        masterData.setAll(repo.findAll());

        tableCustomers.setItems(masterData);
    }

    private void filterCustomers(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {

            tableCustomers.setItems(masterData);
            return;
        }

        String search = keyword.toLowerCase();

        ObservableList<Customer> filtered =
                FXCollections.observableArrayList();

        for (Customer customer : masterData) {

            if (customer.getId().toLowerCase().contains(search)
                    || customer.getName().toLowerCase().contains(search)
                    || customer.getPhone().toLowerCase().contains(search)
                    || customer.getEmail().toLowerCase().contains(search)
                    || customer.getCnic().toLowerCase().contains(search)
                    || customer.getAddress().toLowerCase().contains(search)) {

                filtered.add(customer);
            }
        }

        tableCustomers.setItems(filtered);
    }

    @FXML
    private void openAddUi() {

        try {

            openModal(
                    "/View/AddCustomer.fxml",
                    "Add Customer");

            loadTable();

        } catch (IOException e) {

            e.printStackTrace();

            AlertUtil.showError(
                    "Error",
                    "Unable to open Add Customer window.");
        }
    }

    @FXML
    private void openUpdateUi() {

        Customer customer =
                tableCustomers.getSelectionModel().getSelectedItem();

        if (customer == null) {

            AlertUtil.showError(
                    "No Selection",
                    "Please select a customer first.");

            return;
        }

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/View/UpdateCustomer.fxml"));

            Parent root = loader.load();

            UpdateCustomerController controller =
                    loader.getController();

            controller.setData(customer);

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass()
                            .getResource("/css/dashboard.css")
                            .toExternalForm());

            Stage stage = new Stage();

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.setTitle("Update Customer");

            stage.setResizable(false);

            stage.setScene(scene);

            stage.showAndWait();

            loadTable();

        } catch (IOException e) {

            e.printStackTrace();

            AlertUtil.showError(
                    "Error",
                    "Unable to open Update Customer window.");
        }
    }

    @FXML
    private void handleDelete() {

        Customer customer =
                tableCustomers.getSelectionModel().getSelectedItem();

        if (customer == null) {

            AlertUtil.showError(
                    "No Selection",
                    "Please select a customer to delete.");

            return;
        }

        repo.delete(customer.getId());

        loadTable();

        AlertUtil.showInfo(
                "Success",
                "Customer deleted successfully.");
    }

    private void openModal(String fxml, String title)
            throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(fxml));

        Parent root = loader.load();

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
                getClass()
                        .getResource("/css/dashboard.css")
                        .toExternalForm());

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle(title);

        stage.setResizable(false);

        stage.setScene(scene);

        stage.showAndWait();
    }
}