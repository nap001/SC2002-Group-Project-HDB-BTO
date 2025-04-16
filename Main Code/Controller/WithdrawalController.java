package Controller;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class WithdrawalController {
	private Map<String, Withdrawal> withdrawals;
	
	public WithdrawalController() {
		withdrawals = new HashMap<>();
	}
	
	public Withdrawal submitWithdrawl (Applicant applicant, Application application) {
		if (application == null || application.getApplicant() != applicant) {
			return null;
		}
		
		//Already applied for an withdrawal beforehand
		if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING)) {
			System.out.println("You have already submitted a withdrawal, please wait for approval.");
			return null;
		}
		if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNUNSUCCESSFUL)) {
			System.out.println("Application has been already submitted before for withdrawl.");
			System.out.println("Your result:");
			System.out.println("Withdrawal is unsucessful, please contact HDB Officer for more information.");
			return null;
		}
		if (application.getApplicationStatus().equals(ApplicationStatus.WITHDRAWNPENDING)) {
			System.out.println("Application has been already submitted before for withdrawl.");
			System.out.println("Your result:");
			System.out.println("Withdrawal is sucessful.");
			return null;
		}
		
		//create a new withdrawal with "PENDING"
		Withdrawal withdrawal = new Withdrawal(applicant, application);
		application.setApplicationStatus(ApplicationStatus.WITHDRAWNPENDING);
		
		//add the new withdrawal inside local database
		withdrawals.put(applicant.getNRIC(), withdrawal);
		
		return withdrawal;
	}
	
		public boolean approveWithdrawal(Withdrawal withdrawal) {
			if (withdrawal == null) {
				System.out.println("No such withdrawal applicaiton found.");
				return false;
			}
			
			withdrawal.setStatus(ApplicationStatus.SUCCESSFUL);
			withdrawal.getApplication().setApplicationStatus(ApplicationStatus.WITHDRAWNSUCCESSFUL);
			withdrawal.getApplicant().setAppliedProject(null);
			
			notifyApplicantStatus(withdrawal);
			return true;
		}
		
		public boolean rejectWithdrawal(Withdrawal withdrawal) {
			if (withdrawal == null) {
				System.out.println("No such withdrawal applicaiton found.");
				return false;
			}
			
			withdrawal.setStatus(ApplicationStatus.UNSUCCESSFUL);
			withdrawal.getApplication().setApplicationStatus(ApplicationStatus.WITHDRAWNUNSUCCESSFUL);
			
			notifyApplicantStatus(withdrawal);
			return true;
		}
		
		public Withdrawal getWithdrawalByApplicant(Applicant applicant) {
			if (applicant == null) {
				System.out.println("No such withdrawal applicaiton found.");
				return null;
			}
			
			withdrawals.get(applicant.getNRIC());
		}
		
		public List<Withdrawal> getWithdrawals(){
			return new ArrayList<>(withdrawals.values());
		}
		
		public void setWithdrawals(List<Withdrawal> withdrawalsList) {
			//remove any existing withdrawals in this local database
			this.withdrawals.clear();
			
			for (Withdrawal withdrawal:withdrawalsList) {
				this.withdrawals.put(withdrawal.getApplicant().getNRIC(), withdrawal);
			}
		}
		
		private void notifyApplicantStatus(Withdrawal withdrawal) {

		}
}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	: