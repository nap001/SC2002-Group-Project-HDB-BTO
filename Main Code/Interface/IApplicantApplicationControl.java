package Interface;

import Boundary.Applicant;
import Entity.ApplicantApplication;

public interface IApplicantApplicationControl {
    
    // Method to add an application to the application list
    void addApplication(ApplicantApplication application);
    
    // Method to check if an application already exists for a given NRIC and project name
    boolean hasApplication(String nric, String projectName);
    
    // Method to display the details of an applicant's application
    void displayApplicationDetails(ApplicantApplication application);
    
    // Method to process the application for an applicant
    void processApplication(Applicant applicant, IApplicantProjectControl projectControl);

}
