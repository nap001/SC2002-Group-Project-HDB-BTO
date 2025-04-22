package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ENUM.ApplicationStatus;
import ENUM.FlatType;
import database.ApplicantApplicationList;
import database.FlatBookingList;
import database.ProjectList;
import entity.Applicant;
import entity.ApplicantApplication;
import entity.FlatBooking;
import entity.HDBOfficer;
import entity.Project;
import interfaces.IFlatBookingControl;
import interfaces.IProjectQueryControl;

public class FlatBookingControl implements IFlatBookingControl{
	Scanner sc = new Scanner(System.in);
    private ProjectList projectDatabase;
    private ApplicantApplicationList applicationDatabase;
    private FlatBookingList flatbookingDatabase;

    public FlatBookingControl(ProjectList projectDatabase, ApplicantApplicationList applicationDatabase, FlatBookingList flatbookingDatabase) {
        this.projectDatabase = projectDatabase;
        this.applicationDatabase = applicationDatabase;
        this.flatbookingDatabase = flatbookingDatabase;
    }

	
	public boolean approveFlatBookingInteractive(HDBOfficer officer, IProjectQueryControl projectControl) {
	    Project assignedProject = officer.getAssignedProject(projectControl);
	
	    if (assignedProject == null) {
	        System.out.println("Officer has no assigned project.");
	        return false;
	    }
	
	    // Filter successful applications for the assigned project
	    List<ApplicantApplication> successfulApps = new ArrayList<>();
	    for (ApplicantApplication app : applicationDatabase.getAllApplications()) {
	        if (app.getProjectName().equals(assignedProject.getProjectName()) &&
	            app.getApplicationStatus() == ApplicationStatus.SUCCESSFUL) {
	            successfulApps.add(app);
	        }
	    }
	
	    if (successfulApps.isEmpty()) {
	        System.out.println("No successful applications found for the assigned project.");
	        return false;
	    }
	
	    // Display all successful applications
	    System.out.println("\nSuccessful Applications for Project: " + assignedProject.getProjectName());
	    for (int i = 0; i < successfulApps.size(); i++) {
	        ApplicantApplication app = successfulApps.get(i);
	        System.out.printf("[%d] NRIC: %s | Name: %s | Flat Type: %s%n",
	            i + 1,
	            app.getApplicant().getNRIC(),
	            app.getApplicant().getName(),
	            app.getFlatType());
	    }
	
	    // Ask officer to select which one to approve
	    System.out.print("Enter the number of the applicant to approve booking: ");
	    int choice = sc.nextInt();
	    sc.nextLine(); // consume newline
	
	    if (choice < 1 || choice > successfulApps.size()) {
	        System.out.println("Invalid choice.");
	        return false;
	    }
	
	    ApplicantApplication selectedApp = successfulApps.get(choice - 1);
	    Applicant applicant = selectedApp.getApplicant();
	
	    // Extract relevant data from the selected application
	    String projectName = selectedApp.getProjectName();
	    FlatType flatType = selectedApp.getFlatType();
	
	    // Check unit availability
	    Map<FlatType, Integer> unitCountMap = assignedProject.getUnitCountMap();
	    int availableUnits = unitCountMap.getOrDefault(flatType, 0);
	
	    if (availableUnits <= 0) {
	        System.out.println("No available units for the selected flat type.");
	        return false;
	    }

	    // Update application status to BOOKED
	    selectedApp.setApplicationStatus(ApplicationStatus.BOOKED);
	    applicant.getApplication().setFlatType(flatType); // ensure this setter exists
	
	    // Reduce available units for this flat type
	    unitCountMap.put(flatType, availableUnits - 1);
	    assignedProject.setUnitCountMap(unitCountMap);
	
	    // Create the flat booking from the applicant application
	    FlatBooking booking = new FlatBooking(applicant, assignedProject, flatType);
	    booking.setBookingStatus(ApplicationStatus.SUCCESSFUL);
	    booking.setBookingDate(new Date());
	    booking.setOfficer(officer);
	
	    // Add the booking to project and the flat booking list
	    flatbookingDatabase.addBooking(booking); // assume this method exists
	
	    System.out.println("Flat booking approved for " + applicant.getName());
	    return true;
	
	}	

}