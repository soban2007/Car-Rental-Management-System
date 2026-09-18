package repository;

import java.util.ArrayList;
import java.util.List;

import model.Admin;
import util.FileManager;

public class AdminRepository {

    private static final String FILE_PATH =
            "data/admins.txt";

    public Admin findByUsername(String username) {

        List<String> admins =
                FileManager.readLines(FILE_PATH);

        for (String line : admins) {

            String[] data = line.split(",");

            if (data.length >= 5 &&
                    data[3].equalsIgnoreCase(username)) {

                return new Admin(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                );

            }

        }

        return null;

    }

    public boolean usernameExists(String username) {

        return findByUsername(username) != null;

    }

    public void save(Admin admin) {

        List<String> admins =
                FileManager.readLines(FILE_PATH);

        admins.add(admin.toString());

        FileManager.writeLines(FILE_PATH, admins);

    }

    public List<Admin> findAll() {

        List<Admin> list = new ArrayList<>();

        List<String> admins =
                FileManager.readLines(FILE_PATH);

        for (String line : admins) {

            String[] data = line.split(",");

            if (data.length >= 5) {

                list.add(new Admin(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4]
                ));

            }

        }

        return list;

    }

}