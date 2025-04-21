package Interface;

import java.util.List;

import Boundary.HDBManager;
import Entity.ApplicantApplication;
import Entity.OfficerRegistration;

public interface IManagerApplicationControl {
    List<OfficerRegistration> getOfficerRegistrationDatabase();
    List<ApplicantApplication> getApplicantDatabase();
    boolean manageOfficerRegistration(HDBManager manager, String projectName, IProjectQueryControl projectControl);
    boolean approveApplicantApplication(HDBManager manager, IProjectQueryControl projectControl);
    boolean approveWithdrawals(HDBManager manager, IProjectQueryControl projectControl);
}
