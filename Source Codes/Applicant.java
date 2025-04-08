import java.util.ArrayList;
import java.util.List;

public class Applicant extends User {
    private List<Enquiry> enquiries;
    private ApplicantApplication application;
    private FlatType bookedFlat;
    private boolean hasWithdrawn;

    public Applicant(String NRIC, String password, int age, String maritalStatus) {
        super(NRIC, password, age, maritalStatus);
        this.enquiries = new ArrayList<>();
        this.application = null;
        this.bookedFlat = null;
        this.hasWithdrawn = false;
    }

    public void applyForProject(ApplicantApplication application) {
        if (this.application != null && this.application.getApplicationStatus() == ApplicantApplication.ApplicationStatus.SUCCESSFUL) {
            System.out.println("You have already applied for a project and it is successful.");
            return;
        }
        this.application = application;
        System.out.println("Application submitted successfully.");
    }

    public void withdrawApplication() {
        if (application == null) {
            System.out.println("No application to withdraw.");
            return;
        }
        hasWithdrawn = true;
        System.out.println("Application withdrawn successfully.");
    }

    public void submitEnquiry(Enquiry enquiry) {
        enquiries.add(enquiry);
        System.out.println("Enquiry submitted.");
    }

    public void viewEnquiries() {
        for (Enquiry e : enquiries) {
            System.out.println("Enquiry ID: " + e.getEnquiryId() + ", Project: " + e.getProject().getProjectName());
        }
    }

    public void deleteEnquiry(int enquiryId) {
        enquiries.removeIf(e -> e.getEnquiryId() == enquiryId);
        System.out.println("Enquiry deleted.");
    }

    public void setBookedFlat(FlatType flatType) {
        this.bookedFlat = flatType;
    }

    public FlatType getFlatType() {
        return bookedFlat;
    }

    public List<Enquiry> getEnquiries() {
        return enquiries;
    }

    public ApplicantApplication getApplication() {
        return application;
    }

    public boolean hasWithdrawn() {
        return hasWithdrawn;
    }
}
