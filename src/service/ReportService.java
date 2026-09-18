package service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Customer;
import model.RentalRecord;
import model.Vehicle;
import repository.CustomerRepository;
import repository.RentalRepository;
import repository.VehicleRepository;

public class ReportService {

    private final VehicleRepository vehicleRepo =
            new VehicleRepository();

    private final CustomerRepository customerRepo =
            new CustomerRepository();

    private final RentalRepository rentalRepo =
            new RentalRepository();

    // =============================
    // Dashboard Statistics
    // =============================

    public int getTotalVehicles() {
        return vehicleRepo.findAll().size();
    }

    public long getAvailableVehiclesCount() {
        return vehicleRepo.findAll()
                .stream()
                .filter(Vehicle::isAvailable)
                .count();
    }

    public long getRentedVehiclesCount() {
        return vehicleRepo.findAll()
                .stream()
                .filter(v -> !v.isAvailable())
                .count();
    }

    public int getTotalCustomers() {
        return customerRepo.findAll().size();
    }

    public int getTotalRentals() {
        return rentalRepo.findAll().size();
    }

    public double getTotalRevenue() {

        double revenue = 0;

        for (RentalRecord rental : rentalRepo.findAll()) {

            revenue += rental.getTotalCost();

        }

        return revenue;

    }

    // =============================
    // Most Expensive Vehicle
    // =============================

    public Vehicle getMostExpensiveVehicle() {

        return vehicleRepo.findAll()

                .stream()

                .max(Comparator.comparingDouble(
                        Vehicle::getPricePerDay))

                .orElse(null);

    }

    // =============================
    // Most Rented Vehicle
    // =============================

    public Vehicle getMostRentedVehicle() {

        List<RentalRecord> rentals =
                rentalRepo.findAll();

        if (rentals.isEmpty()) {

            return null;

        }

        Map<String, Integer> count =
                new HashMap<>();

        for (RentalRecord rental : rentals) {

            String id =
                    rental.getVehicle().getId();

            count.put(id,
                    count.getOrDefault(id, 0) + 1);

        }

        String mostRentedId = null;

        int max = 0;

        for (String id : count.keySet()) {

            if (count.get(id) > max) {

                max = count.get(id);

                mostRentedId = id;

            }

        }

        return vehicleRepo.findById(mostRentedId);

    }

}