package Interface;

import Controller.EnquiryControl;
import Entity.Enquiry;
import java.util.List;

public interface EnquiryViewReply {
    void replyToEnquiries(IEnquiryControl enquiryControl);
    void viewAllEnquiries(IEnquiryControl enquiryControl);
}
