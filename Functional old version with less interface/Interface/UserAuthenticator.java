// In Interface/UserAuthenticator.java
package Interface;

import Boundary.User;

public interface UserAuthenticator {
    User validateLogin(String nric, String password);
}
