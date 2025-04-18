package Controller;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;
import Collection.*;
import Interfaces.*;

import java.util.List;

public class WithdrawalController {
    private WithdrawalList withdrawalList;  // Change Map to WithdrawalList

    public WithdrawalController(WithdrawalList withdrawalList) {
        this.withdrawalList = withdrawalList;  // Initialize with WithdrawalList
    }

    // Method to submit a withdrawal request
    public Withdrawal submitWithdrawal(Applicant applicant, Application application) {
        if (application == null || application.getApplicant() != applicant) {
            return null;
        }

        // Already applied for a withdrawal beforehand
        if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING)) {
            System.out.println("You have already submitted a withdrawal, please wait for approval.");
            return null;
        }
        if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNUNSUCCESSFUL)) {
            System.out.println("Application has already been submitted before for withdrawal.");
            System.out.println("Your result:");
            System.out.println("Withdrawal is unsuccessful, please contact HDB Officer for more information.");
            return null;
        }
        if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING)) {
            System.out.println("Application has already been submitted before for withdrawal.");
            System.out.println("Your result:");
            System.out.println("Withdrawal is successful.");
            return null;
        }

        // Create a new withdrawal with "PENDING" status
        Withdrawal withdrawal = new Withdrawal(applicant, application);
        application.setApplicationStatus(ApplicationStatus.WITHDRAWNPENDING);

        // Add the new withdrawal inside the WithdrawalList
        withdrawalList.addItem(withdrawal);

        return withdrawal;
    }

    // Method to approve withdrawal request
    public boolean approveWithdrawal(Withdrawal withdrawal) {
        if (withdrawal == null) {
            System.out.println("No such withdrawal application found.");
            return false;
        }

        withdrawal.setStatus(ApplicationStatus.SUCCESSFUL);
        withdrawal.getApplication().setApplicationStatus(ApplicationStatus.WITHDRAWNSUCCESSFUL);
        withdrawal.getApplicant().setAppliedProject(null);

        notifyApplicantStatus(withdrawal);
        return true;
    }

    // Method to reject withdrawal request
    public boolean rejectWithdrawal(Withdrawal withdrawal) {
        if (withdrawal == null) {
            System.out.println("No such withdrawal application found.");
            return false;
        }

        withdrawal.setStatus(ApplicationStatus.UNSUCCESSFUL);
        withdrawal.getApplication().setApplicationStatus(ApplicationStatus.WITHDRAWNUNSUCCESSFUL);

        notifyApplicantStatus(withdrawal);
        return true;
    }

    // Method to get a withdrawal by applicant
    public Withdrawal getWithdrawalByApplicant(Applicant applicant) {
        if (applicant == null) {
            System.out.println("No such withdrawal application found.");
            return null;
        }

        // Iterate through withdrawals in the list and find matching applicant
        for (Withdrawal withdrawal : withdrawalList.getWithdrawals()) {
            if (withdrawal.getApplicant().equals(applicant)) {
                return withdrawal;
            }
        }
        return null;  // Return null if no matching withdrawal found
    }

    // Method to get all withdrawals
    public List<Withdrawal> getWithdrawals() {
        return withdrawalList.getWithdrawals();
    }

    // Method to set withdrawals from a list (for bulk import)
    public void setWithdrawals(List<Withdrawal> withdrawalsList) {
        // Remove any existing withdrawals in this list
        withdrawalList = new WithdrawalList("withdrawals.csv", new CSVHandler<Withdrawal>());

        for (Withdrawal withdrawal : withdrawalsList) {
            withdrawalList.addItem(withdrawal);
        }
    }

    // Method to notify the applicant about the withdrawal status
    private void notifyApplicantStatus(Withdrawal withdrawal) {
        // Implement the notification logic here (e.g., sending an email or alert)
    }
}
