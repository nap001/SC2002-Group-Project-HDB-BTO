package entity;

import java.io.Serializable;

import interfaces.IEnquiryControl;
import interfaces.IFlatBookingControl;
import interfaces.IOfficerRegistrationControl;
import interfaces.IProjectQueryControl;
import interfaces.IProjectViewControl;
import interfaces.IReceiptGenerator;

public class HDBOfficer extends Applicant implements Serializable{
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
    
    
    public void viewAllProject(IProjectViewControl projectControl) {
        projectControl.viewAllProject();
    }
    
    public void assignProject(Project project) {
        this.assignedProject = project;
        System.out.println("Assigned to project: " + project.getProjectName());
    }
    
    public Project getPersonalAssignedProject() {
        return this.assignedProject;
    }

    public Project getAssignedProject(IProjectQueryControl projectControl) {
        this.assignedProject = projectControl.getProjectByOfficerNRIC(this.getNRIC());
        return this.assignedProject;
    }
    
    public void registerforProject(Project project, IOfficerRegistrationControl officerRegistrationControl) {
        boolean success = officerRegistrationControl.registerOfficerForProject(this, project);
        if (success) {
            System.out.println("Successfully applied for project: " + project.getProjectName());
        } else {
            System.out.println("You cannot register as an officer for this project.");
        }
    }


	public void approveFlatBooking(HDBOfficer officer, IFlatBookingControl flatBookingControl, IProjectQueryControl projectControl) {
			boolean success = flatBookingControl.approveFlatBookingInteractive(this, projectControl);
			
			if (success) {
			System.out.println("Flat booking approved successfully.");
			} else {
			System.out.println("Flat booking approval failed.");
			}
		}



    public void replyToEnquiries(IEnquiryControl enquiryControl, IProjectQueryControl projectcontrol) {
        enquiryControl.replyToEnquiries(this, projectcontrol); 
    }

    public void viewAllEnquiries(IEnquiryControl enquiryControl) {
        enquiryControl.viewAllEnquiries();
    }

    public void generateReceipts(IReceiptGenerator receiptGenerator) {
        if (assignedProject == null) {
            System.out.println("No project is currently assigned to this officer.");
            return;
        }

        receiptGenerator.generateReceiptsForProject(assignedProject);
    }

    public void viewAssignedProject(IProjectViewControl projectControl) {
        projectControl.viewAssignedProject(this);
    }


}
