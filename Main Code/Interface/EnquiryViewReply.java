package Interface;

import Controller.EnquiryControl;
import Entity.Enquiry;
import java.util.List;

public interface EnquiryViewReply {
    void replyToEnquiries(IEnquiryControl enquiryControl, IProjectQueryControl projectControl);
    void viewAllEnquiries(IEnquiryControl enquiryControl);
}
