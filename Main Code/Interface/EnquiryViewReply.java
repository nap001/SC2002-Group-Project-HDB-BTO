package Interface;

import Controller.EnquiryControl;
import Entity.Enquiry;
import java.util.List;

public interface EnquiryViewReply {
    void replyToEnquiries(IEnquiryControl enquiryControl, IProjectControl projectControl);
    void viewAllEnquiries(IEnquiryControl enquiryControl);
}
