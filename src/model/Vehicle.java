package model;

public abstract class Vehicle {

    private String id;
    private String brand;
    private String model;
    private double pricePerDay;
    private boolean available;
    private String imagePath;

    // ================= Constructor =================

    public Vehicle(String id,
                   String brand,
                   String model,
                   double pricePerDay,
                   boolean available,
                   String imagePath) {

        this.id = id;
        this.brand = brand;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.available = available;

        if (imagePath == null || imagePath.trim().isEmpty()) {
            this.imagePath = "default.jpg";
        } else {
            this.imagePath = imagePath;
        }
    }

    // ================= Abstract Method =================

    public abstract String getVehicleType();

    // ================= Getters =================

    public String getId() {
        return id;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getImagePath() {
        return imagePath;
    }

    // ================= Setters =================

    public void setId(String id) {
        this.id = id;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setImagePath(String imagePath) {

        if (imagePath == null || imagePath.trim().isEmpty()) {
            this.imagePath = "default.jpg";
        } else {
            this.imagePath = imagePath;
        }

    }

    // ================= Display =================

    @Override
    public String toString() {
        return brand + " " + model;
    }

    // ================= Save to TXT File =================

    public String toFileString() {

        return id + "," +
               brand + "," +
               model + "," +
               pricePerDay + "," +
               available + "," +
               imagePath;

    }

}