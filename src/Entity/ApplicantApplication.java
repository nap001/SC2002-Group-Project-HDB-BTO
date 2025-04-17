package Entity;

import Boundary.Applicant;
import Boundary.User;
import ENUM.ApplicationStatus;
import ENUM.FlatType;

public class ApplicantApplication {
    private Applicant applicant;
    private String projectName;
    private FlatType flatType;
    private ApplicationStatus applicationStatus;

    // Default constructor
    public ApplicantApplication() {
        this.applicationStatus = ApplicationStatus.PENDING;
    }

    // Full constructor
    public ApplicantApplication(Applicant applicant, String projectName, FlatType flatType) {
        this.applicant = applicant;
        this.projectName = projectName;
        this.flatType = flatType;
        this.applicationStatus = ApplicationStatus.PENDING;
    }

    // === Getters and Setters ===
    public Applicant getApplicant() {
        return applicant;
    }

    public void setApplicant(Applicant applicant) {
        this.applicant = applicant;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    // === Convenience Getters from User ===
    public String getNric() {
        return applicant != null ? applicant.getNRIC() : null;
    }

    public String getName() {
        return applicant != null ? applicant.getName() : null;
    }

    public String getMaritalStatus() {
        return applicant != null ? applicant.getMaritalStatus() : null;
    }
}
