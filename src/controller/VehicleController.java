package controller;

import java.io.File;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Vehicle;
import repository.VehicleRepository;
import util.AlertUtil;

public class VehicleController {

    @FXML
    private TableView<Vehicle> tableVehicles;

    @FXML
    private TableColumn<Vehicle, String> colId;

    @FXML
    private TableColumn<Vehicle, String> colBrand;

    @FXML
    private TableColumn<Vehicle, String> colModel;

    @FXML
    private TableColumn<Vehicle, Double> colPrice;

    @FXML
    private TableColumn<Vehicle, Boolean> colAvailable;

    @FXML
    private TextField txtSearch;

    @FXML
    private ImageView imgPreview;

    @FXML
    private Label lblBrand;

    @FXML
    private Label lblModel;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblStatus;

    private final VehicleRepository repo =
            new VehicleRepository();

    private final ObservableList<Vehicle> masterData =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        colId.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        colBrand.setCellValueFactory(
                new PropertyValueFactory<>("brand"));

        colModel.setCellValueFactory(
                new PropertyValueFactory<>("model"));

        colPrice.setCellValueFactory(
                new PropertyValueFactory<>("pricePerDay"));

        colAvailable.setCellValueFactory(
                new PropertyValueFactory<>("available"));

        loadVehicles();

        tableVehicles.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldValue, vehicle) -> {

                    if (vehicle != null) {

                        updateImagePreview(vehicle.getImagePath());

                        lblBrand.setText(
                                "Brand : " + vehicle.getBrand());

                        lblModel.setText(
                                "Model : " + vehicle.getModel());

                        lblPrice.setText(
                                "Price : Rs. " +
                                        vehicle.getPricePerDay());

                        lblStatus.setText(
                                "Status : " +
                                        (vehicle.isAvailable()
                                                ? "Available"
                                                : "Rented"));

                    }

                });

        txtSearch.textProperty().addListener(
                (obs, oldValue, newValue) ->
                        filterVehicles(newValue));

    }

    @FXML
    public void loadVehicles() {

        masterData.setAll(repo.findAll());

        tableVehicles.setItems(masterData);

    }

    private void updateImagePreview(String imageName) {

        try {

            File file =
                    new File("src/images/" + imageName);

            if (file.exists()) {

                imgPreview.setImage(
                        new Image(file.toURI().toString()));

            }

            else {

                File defaultImage =
                        new File("src/images/default.jpg");

                if (defaultImage.exists()) {

                    imgPreview.setImage(
                            new Image(defaultImage.toURI().toString()));

                }

            }

        }

        catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void filterVehicles(String keyword) {

        if (keyword == null || keyword.isEmpty()) {

            tableVehicles.setItems(masterData);

            return;

        }

        ObservableList<Vehicle> filtered =
                FXCollections.observableArrayList();

        for (Vehicle vehicle : masterData) {

            if (vehicle.getBrand().toLowerCase()
                    .contains(keyword.toLowerCase())

                    ||

                    vehicle.getModel().toLowerCase()
                            .contains(keyword.toLowerCase())) {

                filtered.add(vehicle);

            }

        }

        tableVehicles.setItems(filtered);

    }

    @FXML
    private void openAddUi() throws IOException {

        openModal(
                "/View/AddVehicle.fxml",
                "Add Vehicle");

        loadVehicles();

    }

    @FXML
    private void openUpdateUi() throws IOException {

        Vehicle selected =
                tableVehicles.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            AlertUtil.showError(
                    "Selection Required",
                    "Please select a vehicle.");

            return;

        }

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/View/UpdateVehicle.fxml"));

        Parent root = loader.load();

        UpdateVehicleController controller =
                loader.getController();

        controller.setVehicleData(selected);

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
                getClass().getResource("/css/dashboard.css")
                        .toExternalForm());

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle("Update Vehicle");

        stage.setScene(scene);

        stage.showAndWait();

        loadVehicles();

    }

    @FXML
    private void handleDelete() {

        Vehicle selected =
                tableVehicles.getSelectionModel()
                        .getSelectedItem();

        if (selected == null) {

            AlertUtil.showError(
                    "Selection Required",
                    "Please select a vehicle.");

            return;

        }

        repo.delete(selected.getId());

        loadVehicles();

        imgPreview.setImage(null);

        lblBrand.setText("Brand : -");

        lblModel.setText("Model : -");

        lblPrice.setText("Price : -");

        lblStatus.setText("Status : -");

        AlertUtil.showInfo(
                "Success",
                "Vehicle deleted successfully.");

    }

    private void openModal(String fxml,
                           String title)
            throws IOException {

        Parent root =
                FXMLLoader.load(
                        getClass().getResource(fxml));

        Scene scene = new Scene(root);

        scene.getStylesheets().add(
                getClass().getResource("/css/dashboard.css")
                        .toExternalForm());

        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.setTitle(title);

        stage.setScene(scene);

        stage.showAndWait();

    }

}