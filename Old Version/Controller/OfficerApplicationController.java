package Controller;

import Application.*;
import Boundaries.*;
import Collection.OfficerApplicationList;
import Entities.*;
import Enum.*;

import java.util.List;
import java.util.ArrayList;

public class OfficerApplicationController {
    private OfficerApplicationList applicationList;

    public OfficerApplicationController(OfficerApplicationList applicationList) {
        this.applicationList = applicationList;
    }

    public OfficerApplication createNewOfficerApplication(HDBOfficer officer, Project project) {
        if (project.getAvailableOfficerSlot() <= 0) {
            System.out.println("This project has reached the full capacity of Officers, please register for another project.");
            return null;
        }

        OfficerApplication officerApplication = new OfficerApplication(officer, project);
        applicationList.addItem(officerApplication);
        project.addOfficerApplication(officerApplication);
        officer.addOfficerApplication(officerApplication);

        return officerApplication;
    }

    public boolean approveOfficerApplication(OfficerApplication application) {
        if (application == null || !applicationList.getApplications().contains(application)) {
            System.out.println("No such application has been found, please submit a complete Officer Application.");
            return false;
        }

        if (application.getApplicationStatus() == ApplicationStatus.SUCCESSFUL) {
            System.out.println("Application has already been approved.");
            return false;
        }

        application.setApplicationStatus(ApplicationStatus.SUCCESSFUL);
        application.getProject().addOfficers(application.getOfficer().getName());
        application.getProject().reduceAvailableOfficerCount();
        application.getOfficer().addProject(application.getProject());

        return true;
    }

    public boolean rejectOfficerApplication(OfficerApplication application) {
        if (application == null || !applicationList.getApplications().contains(application)) {
            System.out.println("No such application has been found, please submit a complete Officer Application.");
            return false;
        }

        application.setApplicationStatus(ApplicationStatus.UNSUCCESSFUL);
        return true;
    }

    public List<OfficerApplication> getOfficerApplicationByOfficer(HDBOfficer officer) {
        List<OfficerApplication> result = new ArrayList<>();
        for (OfficerApplication app : applicationList.getApplications()) {
            if (app.getOfficer().getNRIC().equals(officer.getNRIC())) {
                result.add(app);
            }
        }
        return result;
    }

    public List<OfficerApplication> getAllApplications() {
        return applicationList.getApplications();
    }

    public void setRegistrations(List<OfficerApplication> applications) {
        for (OfficerApplication application : applications) {
            applicationList.addItem(application);
            application.getProject().addOfficerApplication(application);
            application.getOfficer().addOfficerApplication(application);
        }
    }

    public void saveApplications() {
        applicationList.saveItems();
    }
}
