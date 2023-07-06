package org.example.util;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.entity.User;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersLoader {
    public static Map<String, User> loadUsers(File usersFile) {
        Map<String, User> users = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(usersFile))) {
            List<String[]> records = reader.readAll();
            int id = 1;
            for (String[] record : records) {
                String name = record[0];
                String referenceNumber = record[1];
                int priceListNumber = Integer.parseInt(record[2]);
                User user = new User(id++, name, referenceNumber, priceListNumber); // assign ID
                users.put(referenceNumber, user);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
        return users;
    }
}
