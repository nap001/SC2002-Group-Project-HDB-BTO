package Boundary;

import java.io.Serializable;
import java.util.List;

import Controller.EnquiryControl;
import Controller.FlatBookingControl;
import Controller.ProjectControl;
import Controller.ReceiptGenerator;
import ENUM.FlatType;
import Entity.ApplicantApplication;
import Entity.Enquiry;
import Entity.OfficerRegistration;
import Controller.OfficerRegistrationControl;
import Entity.Project;
import Interface.EnquiryViewReply;
import Interface.ProjectView;

public class HDBOfficer extends Applicant implements ProjectView,EnquiryViewReply, Serializable{
    private Project assignedProject;
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public HDBOfficer(String NRIC, String password, int age, String maritalStatus, String name) {
        super(NRIC, password, age, maritalStatus, name);
        this.assignedProject = null;
    }
    
    public void setAssignedProject(Project assignedProject) {
		this.assignedProject = assignedProject;
	}

	@Override
    public String getRole() {
        return "Officer";
    }
    
    @Override
    public void viewAllProject(ProjectControl projectControl) {
        projectControl.viewAllProject();
    }
    
    public void assignProject(Project project) {
        this.assignedProject = project;
        System.out.println("Assigned to project: " + project.getProjectName());
    }

    public Project getAssignedProject() {
        return assignedProject;
    }
	public void registerforProject(Project project, boolean isApplicant, boolean isHandling, OfficerRegistrationControl officerRegistrationControl) {
	    OfficerRegistration registration = new OfficerRegistration(this, isApplicant, isHandling, project);
	    officerRegistrationControl.addRegistration(registration);
	    System.out.println("Successfully applied for project: " + project.getProjectName());
	}

	public void approveFlatBooking(HDBOfficer officer, FlatBookingControl flatBookingControl) {
			boolean success = flatBookingControl.approveFlatBookingInteractive(this);
			
			if (success) {
			System.out.println("Flat booking approved successfully.");
			} else {
			System.out.println("Flat booking approval failed.");
			}
		}



    @Override
    public void replyToEnquiries(EnquiryControl enquiryControl) {
        enquiryControl.replyToEnquiries(this); 
    }

    @Override
    public void viewAllEnquiries(EnquiryControl enquiryControl) {
        enquiryControl.viewAllEnquiries();
    }

    public void generateReceipts(ReceiptGenerator receiptGenerator) {
        if (assignedProject == null) {
            System.out.println("No project is currently assigned to this officer.");
            return;
        }

        receiptGenerator.generateReceiptsForProject(assignedProject);
    }

}
