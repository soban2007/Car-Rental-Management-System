package controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.Car;
import repository.VehicleRepository;
import util.AlertUtil;
import util.ValidationUtil;

public class AddVehicleController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtModel;

    @FXML
    private TextField txtPrice;

    @FXML
    private ComboBox<String> comboImages;

    @FXML
    private ComboBox<String> comboAvailability;

    private final VehicleRepository repo = new VehicleRepository();

    @FXML
    public void initialize() {

        comboImages.getItems().addAll(
                "default.jpg",
                "corolla.jpg",
                "civic.jpg",
                "alto.jpg",
                "city.jpg",
                "elantra.jpg",
                "sportage.jpg"
        );

        comboImages.setValue("default.jpg");

        comboAvailability.getItems().addAll(
                "Available",
                "Rented"
        );

        comboAvailability.setValue("Available");
    }

    @FXML
    private void handleSave() {

        String id = txtId.getText().trim().toUpperCase();
        String brand = txtBrand.getText().trim();
        String model = txtModel.getText().trim();
        String priceText = txtPrice.getText().trim();

        if (ValidationUtil.isEmpty(id)
                || ValidationUtil.isEmpty(brand)
                || ValidationUtil.isEmpty(model)
                || ValidationUtil.isEmpty(priceText)) {

            AlertUtil.showError(
                    "Validation Error",
                    "Please fill all fields."
            );
            return;
        }

        if (!ValidationUtil.isPositiveDouble(priceText)) {

            AlertUtil.showError(
                    "Invalid Price",
                    "Price must be greater than zero."
            );
            return;
        }

        if (repo.findById(id) != null) {

            AlertUtil.showError(
                    "Duplicate Vehicle",
                    "Vehicle ID already exists."
            );
            return;
        }

        double price = Double.parseDouble(priceText);

        boolean available =
                comboAvailability.getValue().equals("Available");

        Car vehicle = new Car(
                id,
                brand,
                model,
                price,
                available,
                comboImages.getValue()
        );

        repo.add(vehicle);

        AlertUtil.showInfo(
                "Success",
                "Vehicle added successfully."
        );

        handleCancel();
    }

    @FXML
    private void handleCancel() {

        Stage stage = (Stage) txtId.getScene().getWindow();

        stage.close();
    }
}