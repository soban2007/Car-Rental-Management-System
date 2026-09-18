package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.RentalRecord;
import repository.RentalRepository;
import service.RentalService;

public class RentalHistoryController {

    @FXML
    private TableView<RentalRecord> tableRentals;

    @FXML
    private TableColumn<RentalRecord, String> colRentalId;

    @FXML
    private TableColumn<RentalRecord, String> colCustomer;

    @FXML
    private TableColumn<RentalRecord, String> colVehicle;

    @FXML
    private TableColumn<RentalRecord, Integer> colDays;

    @FXML
    private TableColumn<RentalRecord, Double> colCost;

    @FXML
    private TableColumn<RentalRecord, Boolean> colReturned;

    private final RentalRepository rentalRepo = new RentalRepository();

    private final RentalService rentalService = new RentalService();

    @FXML
    public void initialize() {

        colRentalId.setCellValueFactory(
                new PropertyValueFactory<>("rentalId"));

        colCustomer.setCellValueFactory(
                new PropertyValueFactory<>("customerName"));

        colVehicle.setCellValueFactory(
                new PropertyValueFactory<>("vehicleName"));

        colDays.setCellValueFactory(
                new PropertyValueFactory<>("days"));

        colCost.setCellValueFactory(
                new PropertyValueFactory<>("totalCost"));

        colReturned.setCellValueFactory(
                new PropertyValueFactory<>("returned"));

        loadRentals();

    }

    private void loadRentals() {

        ObservableList<RentalRecord> list =
                FXCollections.observableArrayList(
                        rentalRepo.findAll());

        tableRentals.setItems(list);

    }

    @FXML
    private void handleRefresh() {

        loadRentals();

    }

    @FXML
    private void handleReturnVehicle() {

        RentalRecord rental =
                tableRentals.getSelectionModel().getSelectedItem();

        if (rental == null) {

            showAlert(
                    Alert.AlertType.WARNING,
                    "Please select a rental."
            );

            return;

        }

        if (rental.isReturned()) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Vehicle already returned."
            );

            return;

        }

        boolean success =
                rentalService.processReturn(
                        rental.getRentalId());

        if (success) {

            showAlert(
                    Alert.AlertType.INFORMATION,
                    "Vehicle returned successfully."
            );

            loadRentals();

        }

        else {

            showAlert(
                    Alert.AlertType.ERROR,
                    "Unable to return vehicle."
            );

        }

    }

    private void showAlert(Alert.AlertType type,
                           String message) {

        Alert alert = new Alert(type);

        alert.setHeaderText(null);

        alert.setContentText(message);

        alert.showAndWait();

    }

}