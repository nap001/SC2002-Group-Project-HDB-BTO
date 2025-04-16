package Controller;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class AuthorisationController {
	private Map<String, User> users;
	
	public AuthorisationController() {
		users = new HashMap<>();
	}
	
	public boolean validateNRIC(String NRIC) {
		//return true/false if the NRIC matches the format
		Pattern pattern = Pattern.compile("^[ST]\\d{7}[A-Z]$");
	    return pattern.matcher(NRIC).matches();
	}
	
	public boolean validatePassword(String password) {
		//Checks if password has at least one uppercase and one lowercase and minimum of 8 characters
	    if (password == null || password.length() < 8) {
	        return false;
	    }
	    boolean hasUpper = false;
	    boolean hasLower = false;
	    for (char c : password.toCharArray()) {
	        if (Character.isUpperCase(c)) {
	            hasUpper = true;
	        } else if (Character.isLowerCase(c)) {
	            hasLower = true;
	        }
	        if (hasUpper && hasLower) {
	            return true;
	        }
	    }
	    return false;
	}
	
	
	public void login(String NRIC, String password) {
	    while (true) {
	        boolean loginSuccessful = false;

	        if (users.isEmpty()) {
	            System.out.println("No users found.");
	            return;
	        }

	        // Loop through the list of users (List<User>)
	        for (User user : users.values()) {
	            // Get the NRIC and password from the User object
	            String NRICrep = user.getNRIC();
	            String passwordrep = user.getPassword();
	            String userType = user.getUserType();

	            // Check if the NRIC and password match
	            if (NRIC.equals(NRICrep) && password.equals(passwordrep)) {
	                loginSuccessful = true;
	                System.out.println("Login successful!");

	                // Determine which menu to show based on the user type
	                if (userType.equals("Officer")) {
	                    user = new HDBOfficer(user.getName(), NRICrep, user.getAge(), user.getMaritialStatus(), passwordrep, userType);
	                    OfficerInterface officerInterface = new OfficerInterface();
						officerInterface.showOfficerMenu();
	                    break;
	                } else if (userType.equals("Manager")) {
	                    user = new HDBManager(user.getName(), NRICrep, user.getAge(), user.getMaritialStatus(), passwordrep, userType);
	                    ManagerInterface managerInterface = new ManagerInterface();
						managerInterface.showManagerMenu();
	                    break;
	                } else if (userType.equals("Applicant")) {
	                    user = new Applicant(user.getName(), NRICrep, user.getAge(), user.getMaritialStatus(), passwordrep, userType);
	                    ApplicantInterface applicantInterface = new ApplicantInterface();
						applicantInterface.showApplicantMenu();
	                    break;
	                }
	            }
	        }
	        if (!loginSuccessful) {
	            System.out.println("Invalid NRIC or password. Please try again.");
	        }
	    }
	}
	
	public List<User> getAllUser() {
		List<User> result = new ArrayList<>();
		for (User user:users.values()) {
			result.add(user);
		}
		return result;
	}
	
	public Map<String, User> getUsers(){
		return users;
	}
	
	public void setUsers(Map<String, User> users) {
		this.users=users;
	}
}
