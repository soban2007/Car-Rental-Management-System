package repository;

import java.util.ArrayList;
import java.util.List;

import model.Customer;
import util.FileManager;

public class CustomerRepository {

    private static final String FILE_PATH = "data/customers.txt";

    // Load all customers
    public List<Customer> findAll() {

        List<Customer> customers = new ArrayList<>();

        List<String> lines = FileManager.readLines(FILE_PATH);

        for (String line : lines) {

            String[] data = line.split(",");

            if (data.length >= 6) {

                Customer customer = new Customer(
                        data[0],   // ID
                        data[1],   // Name
                        data[2],   // Phone
                        data[3],   // Email
                        data[4],   // CNIC
                        data[5]    // Address
                );

                customers.add(customer);

            }

        }

        return customers;

    }

    // Find customer by ID
    public Customer findById(String id) {

        for (Customer customer : findAll()) {

            if (customer.getId().equalsIgnoreCase(id)) {

                return customer;

            }

        }

        return null;

    }

    // Save all customers
    public void saveAll(List<Customer> customers) {

        List<String> lines = new ArrayList<>();

        for (Customer customer : customers) {

            lines.add(customer.toString());

        }

        FileManager.writeLines(FILE_PATH, lines);

    }

    // Add customer
    public void add(Customer customer) {

        List<Customer> customers = findAll();

        customers.add(customer);

        saveAll(customers);

    }

    // Update customer
    public void update(Customer updatedCustomer) {

        List<Customer> customers = findAll();

        for (int i = 0; i < customers.size(); i++) {

            if (customers.get(i).getId().equalsIgnoreCase(updatedCustomer.getId())) {

                customers.set(i, updatedCustomer);

                break;

            }

        }

        saveAll(customers);

    }

    // Delete customer
    public void delete(String id) {

        List<Customer> customers = findAll();

        customers.removeIf(customer ->
                customer.getId().equalsIgnoreCase(id));

        saveAll(customers);

    }

}