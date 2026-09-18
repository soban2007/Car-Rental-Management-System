package model;

public class Customer {

    private String id;
    private String name;
    private String phone;
    private String email;
    private String cnic;
    private String address;

    // Constructor
    public Customer(String id,
                    String name,
                    String phone,
                    String email,
                    String cnic,
                    String address) {

        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.cnic = cnic;
        this.address = address;
    }

    // Getters

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getCnic() {
        return cnic;
    }

    public String getAddress() {
        return address;
    }

    // Setters

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {

        return id + ","
                + name + ","
                + phone + ","
                + email + ","
                + cnic + ","
                + address;

    }

}