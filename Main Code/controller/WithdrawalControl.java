package controller;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import ENUM.ApplicationStatus;
import ENUM.FlatType;
import database.WithdrawalList;
import entity.Applicant;
import entity.ApplicantApplication;
import entity.Project;
import entity.Withdrawal;
import interfaces.IWithdrawalControl;

public class WithdrawalControl implements IWithdrawalControl{
	private WithdrawalList withdrawalList;

	public WithdrawalControl(WithdrawalList withdrawalList) {
	    this.withdrawalList = withdrawalList;
	}

	public boolean requestWithdrawal(Applicant applicant, ApplicantApplication application) {
	    if (hasPendingWithdrawal(applicant)) {
	        return false;
	    }

	    Withdrawal withdrawal = new Withdrawal(applicant, application);
	    withdrawalList.addWithdrawal(withdrawal);
	    return true;
	}
	
	public boolean hasPendingWithdrawal(Applicant applicant) {
	    for (Withdrawal withdrawal : withdrawalList.getAllWithdrawals()) {
	        if (withdrawal.getApplicant().equals(applicant) &&
	            withdrawal.getStatus() == ApplicationStatus.WITHDRAWNPENDING) {
	            return true;
	        }
	    }
	    return false;
	}

	public void displayWithdrawalDetails(Applicant applicant) {
	    List<Withdrawal> withdrawals = withdrawalList.getAllWithdrawals();
	    boolean found = false;
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
	    for (Withdrawal withdrawal : withdrawals) {
	        if (withdrawal.getApplicant().equals(applicant)) {
	            found = true;
	            System.out.println("===========================================");
	            System.out.println("Applicant Name : " + withdrawal.getApplication().getName());
	            System.out.println("Project Name   : " + withdrawal.getApplication().getProjectName());
	            System.out.println("Flat Type      : " + withdrawal.getApplication().getFlatType());
	            System.out.println("Status         : " + withdrawal.getStatus());
	            System.out.println("===========================================");
	        }
	    }
	
	    if (!found) {
	        System.out.println("No withdrawal records found for this applicant.");
	    }
	}

}
