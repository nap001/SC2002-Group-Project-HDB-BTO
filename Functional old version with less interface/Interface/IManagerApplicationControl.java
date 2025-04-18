package Interface;

import Boundary.HDBManager;
import Controller.ProjectControl;
import Entity.ApplicantApplication;
import Entity.OfficerRegistration;
import Entity.Project;

import java.util.List;

public interface IManagerApplicationControl {
    List<OfficerRegistration> getOfficerRegistrationDatabase();
    List<ApplicantApplication> getApplicantDatabase();
    boolean manageOfficerRegistration(HDBManager manager, String projectName);
    boolean approveApplicantApplication(HDBManager manager, ProjectControl projectControl);
    boolean approveWithdrawals(HDBManager manager);
}
