package model;

public class Admin {

    private String fullName;
    private String email;
    private String phone;
    private String username;
    private String password;

    public Admin(String fullName,
                 String email,
                 String phone,
                 String username,
                 String password) {

        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {

        return fullName + "," +
               email + "," +
               phone + "," +
               username + "," +
               password;
    }

}