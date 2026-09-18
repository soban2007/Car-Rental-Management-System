package controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Admin;
import service.AuthenticationService;
import util.AlertUtil;

public class RegisterController {

    @FXML
    private TextField txtFullName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Button btnRegister;

    private final AuthenticationService authService = new AuthenticationService();

    @FXML
    private void handleRegister() {

        String fullName = txtFullName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        String confirmPassword = txtConfirmPassword.getText().trim();

        // Validate Empty Fields
        if (fullName.isEmpty() ||
            email.isEmpty() ||
            phone.isEmpty() ||
            username.isEmpty() ||
            password.isEmpty() ||
            confirmPassword.isEmpty()) {

            AlertUtil.showError(
                    "Missing Information",
                    "Please fill in all fields.");

            return;
        }

        // Password Match
        if (!password.equals(confirmPassword)) {

            AlertUtil.showError(
                    "Password Error",
                    "Passwords do not match.");

            return;
        }

        // Username Exists
        if (authService.usernameExists(username)) {

            AlertUtil.showError(
                    "Duplicate Username",
                    "Username already exists.");

            return;
        }

        // Create Admin Object
        Admin admin = new Admin(
                fullName,
                email,
                phone,
                username,
                password
        );

        // Save Admin
        if (authService.register(admin)) {

            AlertUtil.showInfo(
                    "Registration Successful",
                    "Administrator account created successfully.");

            clearFields();

            goToLogin();

        } else {

            AlertUtil.showError(
                    "Registration Failed",
                    "Unable to register administrator.");

        }

    }

    @FXML
    private void goToLogin() {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource("/View/Login.fxml"));

            Parent root = loader.load();

            Scene scene = new Scene(root);

            scene.getStylesheets().add(
                    getClass()
                    .getResource("/css/login.css")
                    .toExternalForm());

            Stage stage =
                    (Stage) btnRegister.getScene().getWindow();

            stage.setTitle("Car Rental Management System");

            stage.setScene(scene);

            stage.centerOnScreen();

            stage.show();

        }

        catch (IOException e) {

            e.printStackTrace();

            AlertUtil.showError(
                    "Navigation Error",
                    "Unable to open Login page.");

        }

    }

    private void clearFields() {

        txtFullName.clear();
        txtEmail.clear();
        txtPhone.clear();
        txtUsername.clear();
        txtPassword.clear();
        txtConfirmPassword.clear();

    }

}