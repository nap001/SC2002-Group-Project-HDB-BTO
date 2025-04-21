// In Interface/UserAuthenticator.java
package Interface;

import Entity.User;

public interface UserAuthenticator {
    User validateLogin(String nric, String password);
}
