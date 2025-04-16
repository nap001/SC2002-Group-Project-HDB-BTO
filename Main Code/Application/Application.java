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


public class Application {
	private Applicant applicant;
	private Project project;
	private FlatType flatType;
	protected ApplicationStatus applicationStatus;
	
	public Application() {
		this.applicationStatus = ApplicationStatus.PENDING;
		
	}
	
	public Application(Applicant applicant, Project project) {
		this();
		this.applicant = applicant;
		this.project = project;
	}

    public Applicant getApplicant() {return applicant;}
    public Project getProject() {return project;}
    public void setProject(Project project) {
    	this.project = project;
    }
    public FlatType getFlatType() {return flatType;}
    public void setFlatType (FlatType flattype) {this.flatType = flattype;}
    
    public void setApplicationStatus(ApplicationStatus status) {this.applicationStatus = status;}
    public ApplicationStatus getApplicationStatus() {return applicationStatus;}
    public String ApplicationStatustoString(ApplicationStatus status) {
    	String result = null;
        if (status == ApplicationStatus.PENDING) {
            result = "pending";
        } else if (status == ApplicationStatus.SUCCESSFUL) {
            result = "successful";
        } else if (status == ApplicationStatus.UNSUCCESSFUL) {
            result = "unsuccessful";
        } else if (status == ApplicationStatus.BOOKED) {
            result = "booked";
        } else if (status == ApplicationStatus.WITHDRAWNPENDING) {
            result = "withdrawal pending";
        } else if (status == ApplicationStatus.WITHDRAWNSUCCESSFUL) {
            result = "withdrawn successful";
        } else if (status == ApplicationStatus.WITHDRAWNUNSUCCESSFUL) {
            result = "withdrawn unsuccessful";
        }
        return result;
    }
}


   
    
