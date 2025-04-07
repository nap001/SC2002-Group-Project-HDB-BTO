import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerApplicationControl {
    private ProjectList projectDatabase;
    private List<OfficerRegistration> officerRegistrationDatabase;
    private List<ApplicantApplication> applicantDatabase;

    public ManagerApplicationControl(ProjectList projectDatabase, List<OfficerRegistration> officerRegistrationDatabase, List<ApplicantApplication> applicantDatabase) {
        this.projectDatabase = projectDatabase;
        this.officerRegistrationDatabase = officerRegistrationDatabase;
        this.applicantDatabase = applicantDatabase;
    }


    public List<OfficerRegistration> getOfficerRegistrationDatabase() {
        return officerRegistrationDatabase;
    }

    public List<ApplicantApplication> getApplicantDatabase() {
        return applicantDatabase;
    }

    // Updated Officer approval method that uses internal database
    public boolean approveOfficerRegistration(HDBManager manager, int projectID, int registrationID, boolean approved) {
        // Ensure the manager is managing this project
        if (!manager.isManagingProject(projectID)) {
            System.out.println("You are not authorized to approve registrations for this project.");
            return false;
        }

        // Fetch the project
        Project project = projectDatabase.getProjects(projectID);
        if (project == null) {
            System.out.println("Project not found.");
            return false;
        }

        // Filter relevant registrations for the project
        List<OfficerRegistration> matchingRegistrations = officerRegistrationDatabase.stream()
            .filter(reg -> reg.getProject().equals(project) && reg.isApplicant())
            .collect(Collectors.toList());

        if (matchingRegistrations.isEmpty()) {
            System.out.println("No officer registrations found for this project.");
            return false;
        }

        // Find the registration by ID
        OfficerRegistration selectedRegistration = null;
        for (OfficerRegistration reg : matchingRegistrations) {
            if (reg.getRegistrationID() == registrationID) {
                selectedRegistration = reg;
                break;
            }
        }

        if (selectedRegistration == null) {
            System.out.println("Officer registration with the provided ID not found.");
            return false;
        }

        // Approve or reject the registration
        selectedRegistration.setRegistrationStatus(approved);

        if (approved) {
            if (selectedRegistration.isHandling()) {
                project.getHdbOfficers().add(selectedRegistration.getOfficer());
                project.setOfficerSlots(project.getOfficerSlots() - 1);
                System.out.println("Officer registration approved and assigned to project.");
            } else {
                System.out.println("Officer registration approved (not assigned as handling).");
            }
        } else {
            System.out.println("Officer registration rejected.");
        }

        return true;
    }

    // Updated Applicant approval method that uses internal database
    public boolean approveApplicantApplication(HDBManager manager, int applicationID, boolean approved) {
        // Iterate over the list to find the application by ID
        ApplicantApplication application = null;
        for (ApplicantApplication app : applicantDatabase) {
            if (app.getApplicationID() == applicationID) {
                application = app;
                break;
            }
        }

        if (application == null) {
            System.out.println("Application not found.");
            return false;
        }

        Project project = application.getProject();
        FlatType flatType = application.getFlatType();
        ApplicationStatus status = application.getApplicationStatus();

        // Check if the application is still pending
        if (status != ApplicantApplication.ApplicationStatus.PENDING) {
            System.out.println("This application is not in a pending state.");
            return false;
        }

        // Check if the manager is managing the project
        if (!manager.isManagingProject(project.getProjectID())) {
            System.out.println("You are not authorized to approve applications for this project.");
            return false;
        }

        // Retrieve the current availability map
        Map<FlatType, Integer> availableUnitsMap = project.getAvailableUnits();
        int availableUnits = availableUnitsMap.getOrDefault(flatType, 0);

        if (approved) {
            if (availableUnits > 0) {
                // Approve the application
                application.setApplicationStatus(ApplicantApplication.ApplicationStatus.SUCCESSFUL);

                // Add the applicant to the project
                project.addApplicantToProject(application.getApplicant());

                // Update the available units map
                availableUnitsMap.put(flatType, availableUnits - 1);
                project.setAvailableUnits(availableUnitsMap);

                System.out.println("Application approved for Applicant ID: " + application.getApplicantID());
            } else {
                application.setApplicationStatus(ApplicantApplication.ApplicationStatus.UNSUCCESSFUL);
                System.out.println("No available units for the requested flat type.");
            }
        } else {
            application.setApplicationStatus(ApplicantApplication.ApplicationStatus.REJECTED);
            System.out.println("Application rejected for Applicant ID: " + application.getApplicantID());
        }

        // Remove the application from the database
        applicantDatabase.remove(application);
        return true;
    }
}
