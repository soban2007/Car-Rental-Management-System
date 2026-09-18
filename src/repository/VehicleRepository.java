package repository;

import java.util.ArrayList;
import java.util.List;

import model.Car;
import model.Vehicle;
import util.FileManager;

public class VehicleRepository {

    private static final String FILE_PATH = "data/vehicles.txt";

    // ================= LOAD ALL VEHICLES =================

    public List<Vehicle> findAll() {

        List<Vehicle> vehicles = new ArrayList<>();

        List<String> lines = FileManager.readLines(FILE_PATH);

        for (String line : lines) {

            try {

                String[] data = line.split(",");

                String image = "default.jpg";

                if (data.length >= 6 && !data[5].trim().isEmpty()) {
                    image = data[5].trim();
                }

                Vehicle vehicle = new Car(

                        data[0],                              // ID
                        data[1],                              // Brand
                        data[2],                              // Model
                        Double.parseDouble(data[3]),          // Price
                        Boolean.parseBoolean(data[4]),        // Available
                        image                                 // Image

                );

                vehicles.add(vehicle);

            }

            catch (Exception e) {

                System.out.println("Invalid Vehicle Record : " + line);

            }

        }

        return vehicles;

    }

    // ================= FIND BY ID =================

    public Vehicle findById(String id) {

        for (Vehicle vehicle : findAll()) {

            if (vehicle.getId().equalsIgnoreCase(id)) {

                return vehicle;

            }

        }

        return null;

    }

    // ================= SAVE ALL =================

    public void saveAll(List<Vehicle> vehicles) {

        List<String> lines = new ArrayList<>();

        for (Vehicle vehicle : vehicles) {

            lines.add(vehicle.toFileString());

        }

        FileManager.writeLines(FILE_PATH, lines);

    }

    // ================= ADD =================

    public void add(Vehicle vehicle) {

        List<Vehicle> vehicles = findAll();

        vehicles.add(vehicle);

        saveAll(vehicles);

    }

    // ================= UPDATE =================

    public void update(Vehicle updatedVehicle) {

        List<Vehicle> vehicles = findAll();

        for (int i = 0; i < vehicles.size(); i++) {

            if (vehicles.get(i).getId().equalsIgnoreCase(updatedVehicle.getId())) {

                vehicles.set(i, updatedVehicle);

                break;

            }

        }

        saveAll(vehicles);

    }

    // ================= DELETE =================

    public void delete(String id) {

        List<Vehicle> vehicles = findAll();

        vehicles.removeIf(vehicle ->
                vehicle.getId().equalsIgnoreCase(id));

        saveAll(vehicles);

    }

    // ================= SEARCH =================

    public List<Vehicle> search(String keyword) {

        List<Vehicle> result = new ArrayList<>();

        keyword = keyword.toLowerCase();

        for (Vehicle vehicle : findAll()) {

            if (vehicle.getId().toLowerCase().contains(keyword)
                    || vehicle.getBrand().toLowerCase().contains(keyword)
                    || vehicle.getModel().toLowerCase().contains(keyword)) {

                result.add(vehicle);

            }

        }

        return result;

    }

}