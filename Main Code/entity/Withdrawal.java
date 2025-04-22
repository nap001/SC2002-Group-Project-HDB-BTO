package entity;

import ENUM.ApplicationStatus;
import boundary.*;
import controller.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Withdrawal implements Serializable{
	private Applicant applicant;
	private ApplicantApplication application;
    private ApplicationStatus status;
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control


	public Withdrawal(Applicant applicant, ApplicantApplication application) {
		this.applicant = applicant;
		this.application = application;
		this.status = ApplicationStatus.WITHDRAWNPENDING;
	}
	
	public Applicant getApplicant() {return applicant;}
    public void setApplicant(Applicant applicant) {this.applicant = applicant;}
    
	public ApplicantApplication getApplication() {return application;}
	public void setApplication(ApplicantApplication application) {this.application = application;}
	
	public ApplicationStatus getStatus() {return status;}
	public void setStatus(ApplicationStatus status) {this.status = status;}
	public boolean updateStatus(ApplicationStatus status) {this.status = status; return true;}
	
	
}
