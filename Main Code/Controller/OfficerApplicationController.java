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

public class OfficerApplicationController {
	private Map<String, List<OfficerApplication>> officerApplications;
	
	public OfficerApplication createNewOfficerApplication(HDBOfficer officer, Project project) {
		Map<String, List<OfficerApplication>> officerRegistrations = new HashMap<>(); 
		if (project.getAvailableOfficerSlot() <= 0) {
			System.out.println("This project has reached the full capacity of Officers, please register for another project.");
			return null;
		}
		OfficerApplication officerapplication = new OfficerApplication(officer, project);
		project.addOfficerApplication(officerapplication);
		officer.addOfficerApplication(officerapplication);
		if (!officerRegistrations.containsKey(officer.getNRIC())) {
			officerRegistrations.put(officer.getNRIC(), new ArrayList<>());
		}
		officerRegistrations.get(officer.getNRIC()).add(officerapplication);
		return officerapplication;
	}
	
	public boolean approveOfficerApplication(OfficerApplication application) {
		if (application == null || officerApplications.containsKey(application.getOfficer().getNRIC())) {
			System.out.println("No such application has been found, please submit a complete Officer Application.");
			return false;
		}
		//Update application status
		application.setApplicationStatus(ApplicationStatus.SUCCESSFUL);
		//Add one more name to project's officers
		application.getProject().addOfficers(application.getOfficer().getName());
		//Reduce the count of available officers
		application.getProject().reduceAvailableOfficerCount();
		//Add this project to the officer's projects
		application.getOfficer().addProject(application.getProject());
		return true;
	}
	
	public boolean rejectOfficerApplication(OfficerApplication application) {
		if (application == null || officerApplications.containsKey(application.getOfficer().getNRIC())) {
			System.out.println("No such application has been found, please submit a complete Officer Application.");
			return false;
		}
		//Update application status
		application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL);
		return true;
	}
	
	public List<OfficerApplication> getOfficerApplicationByOfficer(HDBOfficer officer){
		return officerApplications.getOrDefault(officer.getNRIC(), new ArrayList<>());
	}
	
	public List<OfficerApplication> getAllApplications() {
	    List<OfficerApplication> result = new ArrayList<>();
	    
	    officerApplications.values().forEach(result::addAll);
	    return result;
	}
	
	public void setRegistrations(List<OfficerApplication> applications) {
        this.officerApplications.clear();
        
        for (OfficerApplication application : applications) {
            if (!this.officerApplications.containsKey(application.getOfficer().getNRIC())) {
                this.officerApplications.put(application.getOfficer().getNRIC(), new ArrayList<>());
            }
            this.officerApplications.get(application.getOfficer().getNRIC()).add(application);
            application.getProject().addOfficerApplication(application);
    		application.getOfficer().addOfficerApplication(application);
        }
    } 
		
}

