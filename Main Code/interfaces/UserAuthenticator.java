// In Interface/UserAuthenticator.java
package interfaces;

import entity.User;

public interface UserAuthenticator {
    User validateLogin(String nric, String password);
}
