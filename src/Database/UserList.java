package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Boundary.User;

public class UserList implements Serializable{
    private List<User> userList = new ArrayList<>();
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

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


    public boolean exists(String nric) {
        return userList.stream().anyMatch(user -> user.getNRIC().equals(nric));
    }
}
