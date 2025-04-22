package interfaces;

import java.util.List;

import entity.ApplicantApplication;
import entity.HDBManager;
import entity.OfficerRegistration;

public interface IManagerApplicationControl {
    List<OfficerRegistration> getOfficerRegistrationDatabase();
    List<ApplicantApplication> getApplicantDatabase();
    boolean manageOfficerRegistration(HDBManager manager, String projectName, IProjectQueryControl projectControl);
    boolean approveApplicantApplication(HDBManager manager, IProjectQueryControl projectControl);
    boolean approveWithdrawals(HDBManager manager, IProjectQueryControl projectControl);
}
