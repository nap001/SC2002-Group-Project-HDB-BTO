package Database;

import java.util.ArrayList;
import java.util.List;

import Entity.ApplicantApplication;
import Entity.Project;

public class ApplicantApplicationList {

    private List<ApplicantApplication> applications;

    public ApplicantApplicationList() {
        this.applications = new ArrayList<>();
    }

    // Add a new application
    public void addApplication(ApplicantApplication application) {
        if (application != null) {
            applications.add(application);
        }
    }

    // Remove an application
    public boolean removeApplication(ApplicantApplication application) {
        return applications.remove(application);
    }

    // Get all applications
    public List<ApplicantApplication> getAllApplications() {
        return new ArrayList<>(applications);
    }

    // Get application by NRIC
    public ApplicantApplication getApplicationByNric(String nric) {
        for (ApplicantApplication app : applications) {
            if (app.getNric().equals(nric)) {
                return app;
            }
        }
        return null;
    }

    // Get applications by project
    public List<ApplicantApplication> getApplicationsByProject(Project project) {
        List<ApplicantApplication> result = new ArrayList<>();
        for (ApplicantApplication app : applications) {
            if (app.getProjectName().equals(project.getProjectName())) {
                result.add(app);
            }
        }
        return result;
    }

    // Check if applicant already applied for a project
    public boolean hasApplied(String nric) {
        for (ApplicantApplication app : applications) {
            if (app.getNric().equals(nric)) {
                return true;
            }
        }
        return false;
    }
}
