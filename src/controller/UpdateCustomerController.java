package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import model.Customer;
import repository.CustomerRepository;
import util.AlertUtil;
import util.ValidationUtil;

public class UpdateCustomerController {

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

    // Load selected customer data
    public void setData(Customer customer) {

        txtId.setText(customer.getId());
        txtName.setText(customer.getName());
        txtPhone.setText(customer.getPhone());
        txtEmail.setText(customer.getEmail());
        txtCnic.setText(customer.getCnic());
        txtAddress.setText(customer.getAddress());

    }

    @FXML
    private void handleUpdate() {

        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String email = txtEmail.getText().trim();
        String cnic = txtCnic.getText().trim();
        String address = txtAddress.getText().trim();

        // Validation
        if (ValidationUtil.isEmpty(name)
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

        Customer updatedCustomer = new Customer(
                id,
                name,
                phone,
                email,
                cnic,
                address
        );

        repo.update(updatedCustomer);

        AlertUtil.showInfo(
                "Success",
                "Customer updated successfully."
        );

        handleCancel();

    }

    @FXML
    private void handleCancel() {

        Stage stage = (Stage) txtId.getScene().getWindow();
        stage.close();

    }

}