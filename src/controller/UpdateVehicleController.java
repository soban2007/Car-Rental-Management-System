package controller;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Car;
import model.Vehicle;
import repository.VehicleRepository;
import util.AlertUtil;
import util.ValidationUtil;

public class UpdateVehicleController {
    @FXML private TextField txtId, txtBrand, txtModel, txtPrice;
    @FXML private CheckBox fxId, chkAvailable;
    @FXML private ComboBox<String> comboImages;

    private VehicleRepository repo = new VehicleRepository();

    @FXML
    public void initialize() {
        comboImages.getItems().addAll("corolla.jpg", "civic.jpg", "alto.jpg", "default.jpg");
    }

    public void setVehicleData(Vehicle v) {
        txtId.setText(v.getId());
        txtBrand.setText(v.getBrand());
        txtModel.setText(v.getModel());
        txtPrice.setText(String.valueOf(v.getPricePerDay()));
        chkAvailable.setSelected(v.isAvailable());
        comboImages.getSelectionModel().select(v.getImagePath());
    }

    @FXML void handle傾pdate() {
        if (ValidationUtil.isEmpty(txtBrand.getText()) || ValidationUtil.isEmpty(txtModel.getText()) || 
            !ValidationUtil.isPositiveDouble(txtPrice.getText())) {
            AlertUtil.showError("Validation Fault", "Check data structure requirements constraint boundaries.");
            return;
        }
        Car updated = new Car(txtId.getText(), txtBrand.getText(), txtModel.getText(), 
                Double.parseDouble(txtPrice.getText()), chkAvailable.isSelected(), comboImages.getValue());
        repo.update(updated);
        AlertUtil.showInfo("Synchronized", "Modified entity properties persisted completely.");
        handleCancel();
    }

    @FXML void handleCancel() {
        ((Stage) txtId.getScene().getWindow()).close();
    }
}