package controller;

import database.OfficerRegistrationList;
import entity.ApplicantApplication;
import entity.HDBOfficer;
import entity.OfficerRegistration;
import entity.Project;
import interfaces.IOfficerRegistrationControl;

public class OfficerRegistrationControl implements IOfficerRegistrationControl{
    private OfficerRegistrationList officerRegistrationDatabase;

    public OfficerRegistrationControl(OfficerRegistrationList officerRegistrationDatabase) {
        this.officerRegistrationDatabase = officerRegistrationDatabase;
    }

    public void addRegistration(OfficerRegistration registration) {
        officerRegistrationDatabase.addRegistration(registration);
    }

    public void removeRegistration(OfficerRegistration registration) {
        officerRegistrationDatabase.removeRegistration(registration);
    }

    public OfficerRegistrationList getDatabase() {
        return officerRegistrationDatabase;
    }

    public boolean registerOfficerForProject(HDBOfficer officer, Project project) {
        // Check if the officer is already registered as applicant for the project
        ApplicantApplication application = officer.getApplication();
        if (application != null && application.getProjectName().equals(project.getProjectName())) {
            System.out.println("Officers cannot register for a project they have already applied to as an applicant.");
            return false;
        }

        // You can also add additional checks here if needed (e.g., duplicate officer registration for same project)

        OfficerRegistration registration = new OfficerRegistration(officer, false, true, project);
        addRegistration(registration);
        return true;
    }
}
