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
import java.util.Date;

public class FlatBooking {
	    private Applicant applicant;
		private Project project;
	    private FlatType flatType;
	    private int flatID;
	    private Date bookingDate;
	    private ApplicationStatus bookingStatus;
	    private String message;
	    private HDBOfficer officer;

	    public FlatBooking() {
	    	this.bookingDate = new Date();
	    	this.bookingStatus = ApplicationStatus.PENDING;
	    }
	    public FlatBooking(Applicant applicant, Project project, FlatType flatType, int flatID) {
	    	this.applicant = applicant;
	    	this.project = project;
	    	this.flatType = flatType;
	    	this.flatID = flatID;
	    }
	    public String getBookingDetails() {
	        StringBuilder details = new StringBuilder();
	        details.append("Booking Details: \n");
	        details.append("Applicant: ").append(applicant.getName()).append("\n");
	        details.append("NRIC: ").append(applicant.getNRIC()).append("\n");
	        details.append("Project: ").append(project.getProjectName()).append("\n");
	        details.append("Flat Type: ").append(flatType).append("\n");
	        details.append("Flat ID: ").append(flatID).append("\n");
	        details.append("Booking Date: ").append(bookingDate).append("\n");
	        details.append("Status: ").append(bookingStatus).append("\n");
	        
	        if (ApplicationStatus.SUCCESSFUL.equals(bookingStatus) && message != null) {
	            details.append(message).append("\n");
	        }
	        
	        if (ApplicationStatus.UNSUCCESSFUL.equals(bookingStatus) && message != null) {
	            details.append("Rejection Reason: ").append(message).append("\n");
	        }
	        
	        return details.toString();
	    }
	    
	    public void approveFlatBooking(HDBOfficer Officer) {
	    	if (ApplicationStatus.PENDING.equals(bookingStatus)) {
	    		this.bookingStatus = ApplicationStatus.SUCCESSFUL;
	    		this.officer = Officer;
	    		String congratulatorymessage = new String("Congratuations your booking is sucessful!");
	    		this.message = congratulatorymessage;
	    	}
	    }
	    
	    public void rejectFlatBooking(HDBOfficer officer, String message) {
	    	if (ApplicationStatus.PENDING.equals(bookingStatus)) {
	    		this.bookingStatus = ApplicationStatus.UNSUCCESSFUL;
	    		this.officer = officer;
	    		this.message = message;
	    	}
	    }
	    
	    public String generateReceipt() {
	    	return getBookingDetails();
	    }
	    
	    //Standard Getter and Setters
	    public Applicant getApplicant() {
			return applicant;
		}
		public void setApplicant(Applicant applicant) {
			this.applicant = applicant;
		}
		public Project getProject() {
			return project;
		}
		public void setProject(Project project) {
			this.project = project;
		}
		public FlatType getFlatType() {
			return flatType;
		}
		public void setFlatType(FlatType flatType) {
			this.flatType = flatType;
		}
		public int getFlatID() {
			return flatID;
		}
		public void setFlatID(int flatID) {
			this.flatID = flatID;
		}
		public Date getBookingDate() {
			return bookingDate;
		}
		public void setBookingDate(Date bookingDate) {
			this.bookingDate = bookingDate;
		}
		public ApplicationStatus getBookingStatus() {
			return bookingStatus;
		}
		public void setBookingStatus(ApplicationStatus bookingStatus) {
			this.bookingStatus = bookingStatus;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public HDBOfficer getOfficer() {
			return officer;
		}
		public void setOfficer(HDBOfficer officer) {
			this.officer = officer;
		}
	}
