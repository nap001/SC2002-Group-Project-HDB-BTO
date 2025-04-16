package Controller;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;
import Collection.*;

import java.util.List;
import java.util.ArrayList;

/**
 * Handles submission, processing, and withdrawal of HDB flat applications.
 */
public class ApplicationController {
    private ApplicationList applicationList;

    /**
     * Constructs an ApplicationController with the given ApplicationList.
     * @param applicationList The list of applications to manage.
     */
    public ApplicationController(ApplicationList applicationList) {
        this.applicationList = applicationList;
    }

    /**
     * Submits a new application for a given applicant and project.
     * @param applicant The applicant submitting the application.
     * @param project The project being applied for.
     * @return true if submission was successful, false otherwise.
     */
    public boolean submitApplication(Applicant applicant, Project project) {
        if (applicant.getApplication() != null) {
            return false;
        }

        Application application = new Application(applicant, project);
        applicant.setApplication(application);
        project.addApplication(application);
        applicationList.addItem(application);
        return true;
    }

    /**
     * Processes an application and updates its status based on availability.
     * @param application The application to process.
     * @return true if successful, false otherwise.
     */
    public boolean processApplication(Application application) {
        if (application == null) {
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

    /**
     * Retrieves an application based on applicant NRIC.
     * @param NRIC The NRIC of the applicant.
     * @return The application, or null if not found.
     */
    public Application getApplicationByNRIC(String NRIC) {
        for (Application app : applicationList.getApplication()) {
            if (app.getApplicant().getNRIC().equals(NRIC)) {
                return app;
            }
        }
        return null;
    }

    /**
     * Returns a list of applications related to a specific project.
     * @param project The project to filter applications by.
     * @return List of matching applications.
     */
    public List<Application> getApplicationByProject(Project project) {
        List<Application> result = new ArrayList<>();
        for (Application application : applicationList.getApplication()) {
            if (application.getProject().equals(project)) {
                result.add(application);
            }
        }
        return result;
    }

    /**
     * Returns a list of all applications.
     * @return List of all applications.
     */
    public List<Application> getAllApplication() {
        return new ArrayList<>(applicationList.getApplication());
    }

    /**
     * Displays details of a specific application.
     * @param application The application to display.
     */
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

    /**
     * Requests withdrawal of a submitted application.
     * @param application The application to withdraw.
     * @return true if request was valid, false otherwise.
     */
    public boolean requestWithdrawal(Application application) {
        if (application == null || !applicationList.getApplication().contains(application)) {
            return false;
        }
        if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING)
                || application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNSUCCESSFUL)
                || application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNUNSUCCESSFUL)) {
            System.out.println("Application has already been submitted for withdrawal, unable to submit another withdrawal.");
            return false;
        }
        application.setApplicationStatus(ApplicationStatus.WITHDRAWNPENDING);
        return true;
    }

    /**
     * Approves a withdrawal request and updates available units if necessary.
     * @param application The application to approve for withdrawal.
     * @return true if successfully approved, false otherwise.
     */
    public boolean approveWithdrawal(Application application) {
        if (application == null || !applicationList.getApplication().contains(application)
                || !application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING)) {
            return false;
        }

        application.setApplicationStatus(ApplicationStatus.WITHDRAWNSUCCESSFUL);
        application.getApplicant().setApplication(null);

        // Restore flat unit if previously successful
        if (application.getApplicationStatus().equals(ApplicationStatus.SUCCESSFUL)) {
            FlatType flatType = application.getFlatType();
            Project project = application.getProject();
            int currentUnits = project.getAvailableFlatType(flatType);
            project.updateFlatTypeCount(flatType, currentUnits + 1);
        }

        return true;
    }
}
