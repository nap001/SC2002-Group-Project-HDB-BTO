package Entities;

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

public class Applicant extends User {
	private Application application;
	private FlatBooking flatBooking;
	private FlatType confirmedFlatType;
	ReadCSV readCSV = new ReadCSV();
    
    
    //constructors
    public Applicant() {
    	super();
    }
    public Applicant(String Name, String NRIC, int age, String maritalStatus, String password, String userType) {
        super(Name, NRIC, age, maritalStatus, password, userType);
    }
    
    public Application getApplication() {
		return application;
	}
	public void setApplication(Application application) {
		this.application = application;
	}
	public FlatBooking getFlatBooking() {
		return flatBooking;
	}
	public void setFlatBooking(FlatBooking flatBooking) {
		this.flatBooking = flatBooking;
	}
	public FlatType getConfirmedFlatType() {
		return confirmedFlatType;
	}
	public void setConfirmedFlatType(FlatType confirmedFlatType) {
		this.confirmedFlatType = confirmedFlatType;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
}
