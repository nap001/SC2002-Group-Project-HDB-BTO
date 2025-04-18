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


public class OfficerApplication{
	private HDBOfficer officer;
	protected ApplicationStatus applicationStatus;
	
	public OfficerApplication() {
		this.applicationStatus = ApplicationStatus.PENDING;
	}
	
	public OfficerApplication(HDBOfficer officer, Project project) {
	 	this();
	 	this.officer = officer;
	 	this.project = project;
	}

	//If manager tries to override an unsuccessful application, it will not be allowed
	//Only PENDING applications can be approved
	public void approveOfficerApplication() {
		if (ApplicationStatus.UNSUCCESSFUL.equals(applicationStatus)) {
			System.out.println(officer.getName() + "'s application has already been rejected, please inform him to submit another application.");
		}
		if (ApplicationStatus.PENDING.equals(applicationStatus)) {
			this.applicationStatus = ApplicationStatus.SUCCESSFUL;
		}
	}
	//SETTERS AND GETTERS//

	public HDBOfficer getOfficer() {return officer;}
	public void setOfficer(HDBOfficer officer) {this.officer = officer;}
	public Project getProject() {return project;}
	public void setProject(Project project) {this.project = project;}
	private Project project;
	public ApplicationStatus getApplicationStatus() {return applicationStatus;}
	public void setApplicationStatus(ApplicationStatus applicationStatus) {this.applicationStatus = applicationStatus;}
	
}

