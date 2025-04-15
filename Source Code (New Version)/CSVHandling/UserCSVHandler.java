package CSVHandling;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Application.*;
import Entities.User;
import Enum.ApplicationStatus;
import Interfaces.CSVHandler;


class UserCSVHandler implements CSVHandler<User> {
    @Override
    public List<User> loadFromCSV(String filePath) {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length >= 3) {
                    String name = columns[0];
                    String nric = columns[1];
                    String role = columns[2];
                    users.add(new User(name, nric, role));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void saveToCSV(List<User> users, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("name,nric,role\n");
            for (User user : users) {
                writer.write(String.format("%s,%s,%s\n", user.getName(), user.getNRIC(), user.getRole()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
