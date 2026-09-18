package service;

import java.util.List;

import model.Customer;
import model.RentalRecord;
import model.Vehicle;
import repository.RentalRepository;
import repository.VehicleRepository;

public class RentalService {

    private final RentalRepository rentalRepo =
            new RentalRepository();

    private final VehicleRepository vehicleRepo =
            new VehicleRepository();

    // ===============================
    // Rent Vehicle
    // ===============================

    public boolean processRental(String rentalId,
                                 Customer customer,
                                 Vehicle vehicle,
                                 int days) {

        if (customer == null || vehicle == null) {

            return false;

        }

        if (!vehicle.isAvailable()) {

            return false;

        }

        double totalCost =
                vehicle.getPricePerDay() * days;

        RentalRecord rental =
                new RentalRecord(

                        rentalId,
                        customer,
                        vehicle,
                        days,
                        totalCost,
                        false

                );

        // Mark vehicle as rented

        vehicle.setAvailable(false);

        vehicleRepo.update(vehicle);

        // Save rental

        rentalRepo.add(rental);

        return true;

    }

    // ===============================
    // Return Vehicle
    // ===============================

    public boolean processReturn(String rentalId) {

        List<RentalRecord> rentals =
                rentalRepo.findAll();

        for (RentalRecord rental : rentals) {

            if (rental.getRentalId().equalsIgnoreCase(rentalId)
                    && !rental.isReturned()) {

                rental.setReturned(true);

                Vehicle vehicle =
                        rental.getVehicle();

                vehicle.setAvailable(true);

                vehicleRepo.update(vehicle);

                rentalRepo.update(rental);

                return true;

            }

        }

        return false;

    }

    // ===============================
    // Calculate Rental Cost
    // ===============================

    public double calculateRentalCost(Vehicle vehicle,
                                      int days) {

        if (vehicle == null || days <= 0) {

            return 0;

        }

        return vehicle.getPricePerDay() * days;

    }

}