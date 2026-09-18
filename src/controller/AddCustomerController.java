package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.Customer;
import repository.CustomerRepository;
import util.AlertUtil;
import util.ValidationUtil;

public class AddCustomerController {

    @FXML
    private TextField txtId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtCnic;

    @FXML
    private TextArea txtAddress;

    private final CustomerRepository repo = new CustomerRepository();

    @FXML
    private void handleSave() {

        String id = txtId.getText().trim().toUpperCase();
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String cnic = txtCnic.getText().trim();
        String address = txtAddress.getText().trim();

        // Validation

        if (ValidationUtil.isEmpty(id)
                || ValidationUtil.isEmpty(name)
                || ValidationUtil.isEmpty(phone)
                || ValidationUtil.isEmpty(email)
                || ValidationUtil.isEmpty(cnic)
                || ValidationUtil.isEmpty(address)) {

            AlertUtil.showError(
                    "Validation Error",
                    "Please fill in all fields."
            );
            return;
        }

        if (!ValidationUtil.isValidPhone(phone)) {

            AlertUtil.showError(
                    "Invalid Phone",
                    "Please enter a valid phone number."
            );
            return;
        }

        if (repo.findById(id) != null) {

            AlertUtil.showError(
                    "Duplicate Customer",
                    "Customer ID already exists."
            );
            return;
        }

        Customer customer = new Customer(
                id,
                name,
                phone,
                email,
                cnic,
                address
        );

        repo.add(customer);

        AlertUtil.showInfo(
                "Success",
                "Customer added successfully."
        );

        handleCancel();
    }

    @FXML
    private void handleCancel() {

        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();

    }

}