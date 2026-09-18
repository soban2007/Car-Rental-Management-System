package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.AuthenticationService;
import util.AlertUtil;

public class LoginController {

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnLogin;

    private final AuthenticationService authService = new AuthenticationService();

    @FXML
    private void handleLogin(ActionEvent event) {

        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();

        if (username.isEmpty()) {
            AlertUtil.showError("Missing Username", "Please enter your username.");
            return;
        }

        if (password.isEmpty()) {
            AlertUtil.showError("Missing Password", "Please enter your password.");
            return;
        }

        if (authService.authenticate(username, password)) {

            try {

                FXMLLoader loader = new FXMLLoader(
                        getClass().getResource("/View/Dashboard.fxml"));

                Parent root = loader.load();

                Scene scene = new Scene(root);

                scene.getStylesheets().add(
                        getClass().getResource("/css/style.css").toExternalForm());

                scene.getStylesheets().add(
                        getClass().getResource("/css/dashboard.css").toExternalForm());

                scene.getStylesheets().add(
                        getClass().getResource("/css/table.css").toExternalForm());

                Stage stage = (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

                stage.setTitle("Car Rental Management System");

                stage.setScene(scene);

                stage.centerOnScreen();

                stage.show();

            } catch (IOException e) {

                e.printStackTrace();

                AlertUtil.showError(
                        "Loading Error",
                        "Unable to load Dashboard."
                );
            }

        } else {

            AlertUtil.showError(
                    "Login Failed",
                    "Invalid username or password."
            );

            txtPassword.clear();
            txtPassword.requestFocus();
        }
    }

    @FXML
    private void handleGoToRegister() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/View/Register.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass().getResource("/css/login.css").toExternalForm());

            Stage stage = (Stage) btnLogin.getScene().getWindow();

            stage.setTitle("Register - Car Rental Management System");

            stage.setScene(scene);

            stage.centerOnScreen();

            stage.show();

        } catch (IOException e) {

            e.printStackTrace();

            AlertUtil.showError(
                    "Navigation Error",
                    "Unable to open Register page."
            );
        }
    }

    @FXML
    private void handleForgotPassword() {

        AlertUtil.showInfo(
                "Forgot Password",
                "Please contact the System Administrator to reset your password."
        );

    }

}