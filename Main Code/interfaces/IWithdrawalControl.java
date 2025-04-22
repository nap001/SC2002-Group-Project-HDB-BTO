package interfaces;

import entity.Applicant;
import entity.ApplicantApplication;

public interface IWithdrawalControl {
    boolean requestWithdrawal(Applicant applicant, ApplicantApplication application);
    boolean hasPendingWithdrawal(Applicant applicant);
    void displayWithdrawalDetails(Applicant applicant);
}
