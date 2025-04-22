package interfaces;

import controller.EnquiryControl;
import entity.Enquiry;

import java.util.List;

public interface EnquiryViewReply {
    void replyToEnquiries(IEnquiryControl enquiryControl, IProjectQueryControl projectControl);
    void viewAllEnquiries(IEnquiryControl enquiryControl);
}
