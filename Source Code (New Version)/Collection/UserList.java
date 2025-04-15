package Collection;

import java.util.List;
import Entities.User;
import Interfaces.CSVHandler;

public class UserList {
    private List<User> users;
    private final String csvPath;
    private final CSVHandler<User> csvHandler;

    public UserList(String csvPath, CSVHandler<User> csvHandler) {
        this.csvPath = csvPath;
        this.csvHandler = csvHandler;
        this.users = csvHandler.loadFromCSV(csvPath);
    }

    public List<User> getUsers() {
        return users;
    }

    public void addItem(User user) {
        users.add(user);
    }

    public void removeItem(User user) {
        users.remove(user);
    }

    public void saveItems() {
        csvHandler.saveToCSV(users, csvPath);
    }

    public User findByname(String username) {
        for (User user : users) {
            if (user.getName().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }
}
