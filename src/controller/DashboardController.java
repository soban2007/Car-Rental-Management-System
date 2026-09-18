package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.ReportService;
import util.AlertUtil;

public class DashboardController {

    @FXML
    private StackPane contentArea;

    @FXML
    private Label lblTotalVehicles;

    @FXML
    private Label lblAvailable;

    @FXML
    private Label lblRented;

    @FXML
    private Label lblCustomers;

    @FXML
    private Label lblRentals;

    @FXML
    private Label lblRevenue;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @FXML
    private BarChart<String, Number> revenueChart;

    @FXML
    private PieChart statusChart;

    private final ReportService reportService =
            new ReportService();

    @FXML
    public void initialize() {

        loadMetricsData();

        startClock();

    }

    private void startClock() {

        lblDate.setText(
                LocalDate.now().format(
                        DateTimeFormatter.ofPattern("dd MMM yyyy"))
        );

        Timeline timeline =
                new Timeline(

                        new KeyFrame(

                                Duration.seconds(1),

                                e -> lblTime.setText(

                                        LocalTime.now().format(

                                                DateTimeFormatter.ofPattern("hh:mm:ss a")
                                        )

                                )

                        )

                );

        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();

    }
    
    

    private void loadMetricsData() {

        lblTotalVehicles.setText(
                String.valueOf(
                        reportService.getTotalVehicles()
                )
        );

        lblAvailable.setText(
                String.valueOf(
                        reportService.getAvailableVehiclesCount()
                )
        );

        lblRented.setText(
                String.valueOf(
                        reportService.getRentedVehiclesCount()
                )
        );

        lblCustomers.setText(
                String.valueOf(
                        reportService.getTotalCustomers()
                )
        );

        lblRentals.setText(
                String.valueOf(
                        reportService.getTotalRentals()
                )
        );

        lblRevenue.setText(
                "Rs. " +
                        reportService.getTotalRevenue()
        );

        revenueChart.getData().clear();

        XYChart.Series<String, Number> series =
                new XYChart.Series<>();

        series.setName("Revenue");

        series.getData().add(

                new XYChart.Data<>(

                        "Revenue",

                        reportService.getTotalRevenue()

                )

        );

        revenueChart.getData().add(series);

        ObservableList<PieChart.Data> pieData =
                FXCollections.observableArrayList(

                        new PieChart.Data(
                                "Available",
                                reportService.getAvailableVehiclesCount()),

                        new PieChart.Data(
                                "Rented",
                                reportService.getRentedVehiclesCount())

                );

        statusChart.setData(pieData);

    }

    private void setView(String fxmlPath) {

        try {

            Parent view = FXMLLoader.load(
                    getClass().getResource(fxmlPath));

            contentArea.getChildren().clear();

            contentArea.getChildren().add(view);

        }

        catch (Exception e) {

            e.printStackTrace();

            AlertUtil.showError(
                    "Navigation Error",
                    "Unable to open\n" + fxmlPath);

        }

    }

    @FXML
    private void showDashboard(ActionEvent event) {

        loadMetricsData();

    }

    @FXML
    private void showVehicles(ActionEvent event) {

        setView("/View/Vehicles.fxml");

    }

    @FXML
    private void showCustomers(ActionEvent event) {

        setView("/View/Customers.fxml");

    }

    @FXML
    private void showRentals(ActionEvent event) {

        setView("/View/Rentals.fxml");

    }

    @FXML
    private void showHistory(ActionEvent event) {

        setView("/View/RentalHistory.fxml");

    }

    @FXML
    private void showReports(ActionEvent event) {

        setView("/View/Reports.fxml");

    }

    @FXML
    private void handleLogout(ActionEvent event) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/View/Login.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass().getResource("/css/login.css")
                            .toExternalForm());

            Stage stage =
                    (Stage) contentArea.getScene().getWindow();

            stage.setScene(scene);

            stage.setTitle("Car Rental Management System");

            stage.centerOnScreen();

            stage.show();

        }

        catch (IOException e) {

            e.printStackTrace();

            AlertUtil.showError(
                    "Logout Error",
                    "Unable to open Login Screen.");

        }

    }

}