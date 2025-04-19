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
    boolean manageOfficerRegistration(HDBManager manager, String projectName, IProjectControl projectControl);
    boolean approveApplicantApplication(HDBManager manager, IProjectControl projectControl);
    boolean approveWithdrawals(HDBManager manager, IProjectControl projectControl);
}
