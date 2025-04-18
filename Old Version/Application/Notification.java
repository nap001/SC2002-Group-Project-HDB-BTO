package Application;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Date;

public class Notification {
	    private String message;
	    private String entity;
	    private String entityDetails;

	    // Constructor to create a notification
	    public Notification(String entity, String entityDetails, String message) {
	        this.entity = entity;
	        this.entityDetails = entityDetails;
	        this.message = message;
	    }

	    // Getters and Setters
	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }

	    public String getEntity() {
	        return entity;
	    }

	    public void setEntity(String entity) {
	        this.entity = entity;
	    }

	    public String getEntityDetails() {
	        return entityDetails;
	    }

	    public void setEntityDetails(String entityDetails) {
	        this.entityDetails = entityDetails;
	    }

	    // Method to format and return the notification message
	    public String formatNotification() {
	        return "Notification for " + entity + " (" + entityDetails + "): " + message;
	    }
	}
