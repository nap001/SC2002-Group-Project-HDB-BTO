
import java.util.List;

public class HDBOfficer extends Applicant {
    private Project assignedProject;

    public HDBOfficer(String NRIC, String password, int age, String maritalStatus) {
        super(NRIC, password, age, maritalStatus);
        this.assignedProject = null;
    }

    public void assignProject(Project project) {
        this.assignedProject = project;
        System.out.println("Assigned to project: " + project.getProjectName());
    }

    public Project getAssignedProject() {
        return assignedProject;
    }

    public void bookFlatForApplicant(Applicant applicant, FlatType flatType) {
        if (assignedProject == null) {
            System.out.println("You are not assigned to any project.");
            return;
        }

        int remaining = assignedProject.getRemainingUnits(flatType);
        if (remaining <= 0) {
            System.out.println("No more units available for " + flatType);
            return;
        }

        applicant.setBookedFlat(flatType);
        applicant.getApplication().setApplicationStatus(ApplicantApplication.ApplicationStatus.BOOKED);
        assignedProject.getAvailableUnits().put(flatType, remaining - 1);

        System.out.println("Flat booked for applicant: " + applicant.getNRIC());
    }

    public void handleEnquiries(List<Enquiry> enquiries) {
        System.out.println("Handling enquiries for project: " + assignedProject.getProjectName());
        for (Enquiry e : enquiries) {
            if (e.getProject().equals(assignedProject)) {
                System.out.println("Enquiry ID: " + e.getEnquiryId());
            }
        }
    }
}
