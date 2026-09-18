package controller;

import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Customer;
import model.Vehicle;
import repository.CustomerRepository;
import repository.RentalRepository;
import repository.VehicleRepository;
import service.RentalService;
import util.AlertUtil;
import util.ValidationUtil;

public class RentalController {

    @FXML
    private TextField txtRentalId;

    @FXML
    private TextField txtDays;

    @FXML
    private ComboBox<String> comboCustomers;

    @FXML
    private ComboBox<String> comboVehicles;

    @FXML
    private Label lblCost;

    private final CustomerRepository customerRepo = new CustomerRepository();
    private final VehicleRepository vehicleRepo = new VehicleRepository();
    private final RentalRepository rentalRepo = new RentalRepository();
    private final RentalService rentalService = new RentalService();

    @FXML
    public void initialize() {

        try {

            loadCustomers();
            loadVehicles();

            txtDays.textProperty().addListener((obs, oldVal, newVal) -> calculateEstimatedCost());

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadCustomers() {

        comboCustomers.getItems().clear();

        comboCustomers.getItems().addAll(

                customerRepo.findAll()

                        .stream()

                        .map(c -> c.getId() + " - " + c.getName())

                        .collect(Collectors.toList())

        );

    }

    private void loadVehicles() {

        comboVehicles.getItems().clear();

        comboVehicles.getItems().addAll(

                vehicleRepo.findAll()

                        .stream()

                        .filter(Vehicle::isAvailable)

                        .map(v -> v.getId() + " - " + v.getBrand() + " " + v.getModel())

                        .collect(Collectors.toList())

        );

    }

    @FXML
    private void calculateEstimatedCost() {

        lblCost.setText("Rs. 0");

        if (comboVehicles.getValue() == null)
            return;

        if (!ValidationUtil.isPositiveInteger(txtDays.getText()))
            return;

        String vehicleId = comboVehicles.getValue().split(" - ")[0];

        Vehicle vehicle = vehicleRepo.findById(vehicleId);

        if (vehicle == null)
            return;

        int days = Integer.parseInt(txtDays.getText());

        double cost = rentalService.calculateRentalCost(vehicle, days);

        lblCost.setText("Rs. " + String.format("%.2f", cost));

    }

    @FXML
    private void handleRent() {

        String rentalId = txtRentalId.getText().trim();

        if (ValidationUtil.isEmpty(rentalId)
                || comboCustomers.getValue() == null
                || comboVehicles.getValue() == null
                || !ValidationUtil.isPositiveInteger(txtDays.getText())) {

            AlertUtil.showError("Validation Error", "Please complete all fields.");

            return;

        }

        if (rentalRepo.findById(rentalId) != null) {

            AlertUtil.showError("Duplicate Rental", "Rental ID already exists.");

            return;

        }

        String customerId = comboCustomers.getValue().split(" - ")[0];
        String vehicleId = comboVehicles.getValue().split(" - ")[0];

        Customer customer = customerRepo.findById(customerId);
        Vehicle vehicle = vehicleRepo.findById(vehicleId);

        int days = Integer.parseInt(txtDays.getText());

        boolean success = rentalService.processRental(rentalId, customer, vehicle, days);

        if (success) {

            AlertUtil.showInfo("Success", "Vehicle rented successfully.");

            clearForm();

            loadVehicles();

        } else {

            AlertUtil.showError("Rental Failed", "Vehicle is not available.");

        }

    }

    @FXML
    private void clearForm() {

        txtRentalId.clear();
        txtDays.clear();

        comboCustomers.getSelectionModel().clearSelection();
        comboVehicles.getSelectionModel().clearSelection();

        lblCost.setText("Rs. 0");

    }

}