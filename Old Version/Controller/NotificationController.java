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

public class NotificationController {
	private User user;
	private List<Notification> notifications;

	    public void updateNotification(Notification notification) {
	        // Print the notification message (you can replace this with GUI display logic if needed)
	        System.out.println(notification.formatNotification());
	    }

	    // Method to notify about a change in Application status
	    public void notifyApplicationStatusChange(String applicationId, String newStatus) {
	        String message = "The status of application " + applicationId + " has changed to: " + newStatus;
	        Notification notification = new Notification("Application", applicationId, message);
	        sendNotification(notification);
	    }

	    // Method to notify about a change in OfficerRegistration status
	    public void notifyOfficerRegistrationStatusChange(String officerId, String newStatus) {
	        String message = "The status of Officer Registration for officer " + officerId + " has changed to: " + newStatus;
	        Notification notification = new Notification("OfficerRegistration", officerId, message);
	        sendNotification(notification);
	    }
	}
	
}
