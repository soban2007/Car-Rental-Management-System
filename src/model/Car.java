package model;

public class Car extends Vehicle {

    public Car(String id,
               String brand,
               String model,
               double pricePerDay,
               boolean available,
               String imagePath) {

        super(id, brand, model, pricePerDay, available, imagePath);
    }

    @Override
    public String getVehicleType() {
        return "Car";
    }
}