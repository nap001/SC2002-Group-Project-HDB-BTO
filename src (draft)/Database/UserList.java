package Database;

import java.util.ArrayList;
import java.util.List;

import Boundary.User;

public class UserList {
    private List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    public User validateLogin(String nric, String password) {
        for (User user : userList) {
            if (user.getNRIC().equalsIgnoreCase(nric) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userList;
    }
}
