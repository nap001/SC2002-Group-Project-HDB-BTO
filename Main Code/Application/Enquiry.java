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

public class Enquiry {
	private int enquiryID;
	private User applicant;
	private Project project;
	private String enquiryString;
	private Date enquiryDate;
	private String response;
	private Date responseDate;
	private User responder;
	private boolean responded;
    
    public Enquiry(User applicant, Project project, String enquiryString) {
    	this.applicant = applicant;
    	this.project = project;
    	this.enquiryString = enquiryString;
    	this.enquiryDate = new Date();
    	this.responded = false;
    }
    
    public int getEnquiryID() {return enquiryID;}
	public void setEnquiryID(int enquiryID) {this.enquiryID = enquiryID;}
	
    public User getApplicant() {return applicant;}
    public Project getProject() {return project;}
    
    public String getEnquiryString() {return enquiryString;}
    public void setEnquiryString(String enquiry) {
    	this.enquiryString = enquiry;
    }
    
    public Date getEnquiryDate() {return enquiryDate;}
    public void setEnquiryDate(Date newdate) {
    	this.enquiryDate = newdate;
    }
    
	public String getResponse() {return response;}
	public void setResponse(String response) {
		if (!this.responded) {
			this.response = response;
			this.responded = (response!= null && !response.isEmpty());
			if (this.responded) {
				this.responseDate = new Date();
			}
		}
	}
	public boolean hasResponse() {
		if (response == null) {
			return true;
		}
		return false;
	}
    public Date getResponseDate() {return responseDate;}
	public void setResponseDate(Date responseDate) {this.responseDate = responseDate;}

	public User getResponder() {return responder;}
	public void setResponder(User responder) {this.responder = responder;}

	public boolean isResponded() {return responded;}
	public void setResponded(boolean responded) {this.responded = responded;}

}
