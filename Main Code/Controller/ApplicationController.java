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

public class ApplicationController {
	private Map<String, Application> applications;
	
	public ApplicationController() {
		applications = new HashMap<>();
	}
	
	public boolean submitApplication(Applicant applicant, Project project) {
		//Applicant unable to submit another application when there is an existing one
		if (applicant.getApplication()!=null) {
			return false;
		}
		
		Application application = new Application(applicant, project);
		applicant.setApplication(application);
		project.addApplication(application);
		applications.put(applicant.getNRIC(), application);
		return true;
	}
	
	public boolean processApplication(Application application) {
	     if (application == null){
	         return false;
	     }
	     
	     FlatType requestedType = application.getFlatType();
	     Project project = application.getProject();
	     
	     int availableUnits = project.getAvailableFlatType(requestedType);
	     
	     if (availableUnits > 0) {
	         application.setApplicationStatus(ApplicationStatus.SUCCESSFUL);
	         project.updateFlatTypeCount(requestedType, availableUnits - 1);
	         
	         return true;
	     } else {
	         application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL);
	         return false;
	     }
	 }
	
	public Application getApplicationByNRIC(String NRIC) {
		return applications.get(NRIC);
	}
	
	public List<Application> getApplicationByProject(Project project){
		List<Application> result = new ArrayList<>();
		for (Application application:applications.values()) {
			if (application.getProject().equals(project)) {
				result.add(application);
			}
		}
		return result;
	}
	
	public List<Application> getAllApplication(){
		List<Application> result = new ArrayList<>();
		for (Application application:applications.values()) {
				result.add(application);}
		return result;
	}
	
	public void viewApplicationDetails(Application application) {
        if (application != null) {
            System.out.println("Application Details:");
            System.out.println("==================================");
            System.out.println("NRIC: " + application.getApplicant().getNRIC());
            System.out.println("Project Name: " + application.getProject().getProjectName());
            System.out.println("Flat Type: " + application.getFlatType());
            System.out.println("Status: " + application.getApplicationStatus());
            System.out.println("==================================");
        } else {
            System.out.println("The application is null, no details available.");
        }
    }
	
	public boolean requestWithdrawal(Application application) {
		//no such application or application is empty
		if (application == null || applications.containsValue(application) ) {
			return false;
		}
		if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING) ||
				application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNSUCCESSFUL) ||
				application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNUNSUCCESSFUL)) {
			System.out.println("Application has already been submitted for withdrawal, unable to submit another withdrawal.");
			return false;
		}
		application.setApplicationStatus(ApplicationStatus.WITHDRAWNPENDING);
		return true;
	}
	
	public boolean approveWithdrawal(Application application) {
	     if (application == null || !applications.containsValue(application) || 
	         !application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING)) {
	         return false;
	     }
	     
	     // Update application withdrawal status
	     application.setApplicationStatus(ApplicationStatus.WITHDRAWNSUCCESSFUL);
	     application.getApplicant().setApplication(null);
	     
	     // If the application was successful, increase the available units
	     if (application.getApplicationStatus().equals(ApplicationStatus.SUCCESSFUL)) {
	         FlatType flatType = application.getFlatType();
	         Project project = application.getProject();
	         
	         int currentUnits = project.getAvailableFlatType(flatType);
	         project.updateFlatTypeCount(flatType, currentUnits + 1);
	     }
	     
	     return true;   
	 }
}
