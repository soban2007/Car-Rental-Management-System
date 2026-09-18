package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.Vehicle;
import service.ReportService;

public class ReportController {

    @FXML
    private Label lblTotalVehicles;

    @FXML
    private Label lblAvailable;

    @FXML
    private Label lblRented;

    @FXML
    private Label lblTotalCustomers;

    @FXML
    private Label lblTotalRentals;

    @FXML
    private Label lblTotalRevenue;

    @FXML
    private Label lblMostExpensive;

    @FXML
    private Label lblMostRented;

    private final ReportService reportService =
            new ReportService();

    @FXML
    public void initialize() {

        loadReport();

    }

    private void loadReport() {

        lblTotalVehicles.setText(
                String.valueOf(
                        reportService.getTotalVehicles()));

        lblAvailable.setText(
                String.valueOf(
                        reportService.getAvailableVehiclesCount()));

        lblRented.setText(
                String.valueOf(
                        reportService.getRentedVehiclesCount()));

        lblTotalCustomers.setText(
                String.valueOf(
                        reportService.getTotalCustomers()));

        lblTotalRentals.setText(
                String.valueOf(
                        reportService.getTotalRentals()));

        lblTotalRevenue.setText(
                "Rs. " +
                String.format("%.2f",
                        reportService.getTotalRevenue()));

        Vehicle expensive =
                reportService.getMostExpensiveVehicle();

        if (expensive != null) {

            lblMostExpensive.setText(

                    expensive.getBrand()
                    + " "
                    + expensive.getModel()
                    + " (Rs."
                    + expensive.getPricePerDay()
                    + "/day)"

            );

        } else {

            lblMostExpensive.setText("N/A");

        }

        Vehicle rented =
                reportService.getMostRentedVehicle();

        if (rented != null) {

            lblMostRented.setText(

                    rented.getBrand()
                    + " "
                    + rented.getModel()

            );

        } else {

            lblMostRented.setText("N/A");

        }

    }

}