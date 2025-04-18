package Controller;

import Database.ApplicantApplicationList;
import Entity.ApplicantApplication;

public class ApplicantApplicationControl {
    private ApplicantApplicationList applicationList;

    public ApplicantApplicationControl(ApplicantApplicationList applicationList) {
        this.applicationList = applicationList;
    }

    public void addApplication(ApplicantApplication application) {
        applicationList.addApplication(application);
    }

    public boolean hasApplication(String nric, String projectName) {
        return applicationList.getAllApplications().stream()
            .anyMatch(app -> app.getNric().equals(nric) && app.getProjectName().equals(projectName));
    }
    
    public void displayApplicationDetails(ApplicantApplication application) {
        if (application == null) {
            System.out.println("No application found.");
            return;
        }

        System.out.println("=== Applicant Application Details ===");
        System.out.println("Name: " + application.getName());
        System.out.println("NRIC: " + application.getNric());
        System.out.println("Marital Status: " + application.getMaritalStatus());
        System.out.println("Project Name: " + application.getProjectName());
        System.out.println("Flat Type: " + application.getFlatType());
        System.out.println("Application Status: " + application.getApplicationStatus());
    }

}

