package repository;

import java.util.ArrayList;
import java.util.List;

import model.Customer;
import model.RentalRecord;
import model.Vehicle;
import util.FileManager;

public class RentalRepository {

    private static final String FILE_PATH = "data/rentals.txt";

    private final VehicleRepository vehicleRepo =
            new VehicleRepository();

    private final CustomerRepository customerRepo =
            new CustomerRepository();

    // ==========================
    // Load All Rentals
    // ==========================

    public List<RentalRecord> findAll() {

        List<RentalRecord> rentals =
                new ArrayList<>();

        List<String> lines =
                FileManager.readLines(FILE_PATH);

        for (String line : lines) {

            try {

                String[] data = line.split(",");

                if (data.length >= 6) {

                    Customer customer =
                            customerRepo.findById(data[1]);

                    Vehicle vehicle =
                            vehicleRepo.findById(data[2]);

                    if (customer == null) {

                        customer = new Customer(

                                data[1],
                                "Unknown",
                                "N/A",
                                "N/A",
                                "N/A",
                                "N/A"

                        );

                    }

                    if (vehicle == null) {

                        continue;

                    }

                    RentalRecord rental =
                            new RentalRecord(

                                    data[0],
                                    customer,
                                    vehicle,
                                    Integer.parseInt(data[3]),
                                    Double.parseDouble(data[4]),
                                    Boolean.parseBoolean(data[5])

                            );

                    rentals.add(rental);

                }

            }

            catch (Exception e) {

                System.out.println(
                        "Invalid Rental Record : "
                                + line
                );

            }

        }

        return rentals;

    }

    // ==========================
    // Save All
    // ==========================

    public void saveAll(List<RentalRecord> rentals) {

        List<String> lines =
                new ArrayList<>();

        for (RentalRecord rental : rentals) {

            lines.add(rental.toString());

        }

        FileManager.writeLines(FILE_PATH, lines);

    }

    // ==========================
    // Add Rental
    // ==========================

    public void add(RentalRecord rental) {

        List<RentalRecord> rentals =
                findAll();

        rentals.add(rental);

        saveAll(rentals);

    }

    // ==========================
    // Update Rental
    // ==========================

    public void update(RentalRecord updatedRental) {

        List<RentalRecord> rentals =
                findAll();

        for (int i = 0; i < rentals.size(); i++) {

            if (rentals.get(i)
                    .getRentalId()
                    .equalsIgnoreCase(updatedRental.getRentalId())) {

                rentals.set(i, updatedRental);

                break;

            }

        }

        saveAll(rentals);

    }

    // ==========================
    // Find Rental By ID
    // ==========================

    public RentalRecord findById(String rentalId) {

        for (RentalRecord rental : findAll()) {

            if (rental.getRentalId()
                    .equalsIgnoreCase(rentalId)) {

                return rental;

            }

        }

        return null;

    }

}