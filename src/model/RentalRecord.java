package model;

public class RentalRecord {

    private String rentalId;

    private Customer customer;

    private Vehicle vehicle;

    private int rentalDays;

    private double totalCost;

    private boolean returned;

    // ==========================
    // Constructor
    // ==========================

    public RentalRecord(String rentalId,
                        Customer customer,
                        Vehicle vehicle,
                        int rentalDays,
                        double totalCost,
                        boolean returned) {

        this.rentalId = rentalId;
        this.customer = customer;
        this.vehicle = vehicle;
        this.rentalDays = rentalDays;
        this.totalCost = totalCost;
        this.returned = returned;

    }

    // ==========================
    // Getters & Setters
    // ==========================

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public int getRentalDays() {
        return rentalDays;
    }

    public void setRentalDays(int rentalDays) {
        this.rentalDays = rentalDays;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    // ==========================
    // Helper Methods
    // ==========================

    public String getCustomerName() {

        return customer != null
                ? customer.getName()
                : "";

    }

    public String getVehicleName() {

        return vehicle != null
                ? vehicle.getBrand() + " " + vehicle.getModel()
                : "";

    }

    public String getStatus() {

        return returned
                ? "Returned"
                : "Rented";

    }

    // ==========================
    // Save to File
    // ==========================

    @Override
    public String toString() {

        return rentalId + ","
                + customer.getId() + ","
                + vehicle.getId() + ","
                + rentalDays + ","
                + totalCost + ","
                + returned;

    }

}