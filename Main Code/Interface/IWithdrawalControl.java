package Interface;

import Boundary.Applicant;
import Entity.ApplicantApplication;

public interface IWithdrawalControl {
    boolean requestWithdrawal(Applicant applicant, ApplicantApplication application);
    boolean hasPendingWithdrawal(Applicant applicant);
    void displayWithdrawalDetails(Applicant applicant);
}
