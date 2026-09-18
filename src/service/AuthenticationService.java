package service;

import model.Admin;
import repository.AdminRepository;

public class AuthenticationService {

    private final AdminRepository adminRepo =
            new AdminRepository();

    public boolean authenticate(String username,
                                String password) {

        Admin admin =
                adminRepo.findByUsername(username);

        return admin != null &&
               admin.getPassword().equals(password);

    }

    public boolean register(Admin admin) {

        if (usernameExists(admin.getUsername())) {

            return false;

        }

        adminRepo.save(admin);

        return true;

    }

    public boolean usernameExists(String username) {

        return adminRepo.usernameExists(username);

    }

}