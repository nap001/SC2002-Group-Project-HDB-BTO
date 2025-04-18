package Application;

import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

public class Withdrawal {
	private Applicant applicant;
	private Application application;
    private ApplicationStatus status;
    private Date requestDate;
    
	public Withdrawal() {
		this.status = ApplicationStatus.WITHDRAWNPENDING;
		this.requestDate = new Date();
	}

	public Withdrawal(Applicant applicant, Application application) {
		this.applicant = applicant;
		this.application = application;
	}
	
	public Applicant getApplicant() {return applicant;}
    public void setApplicant(Applicant applicant) {this.applicant = applicant;}
    
	public Application getApplication() {return application;}
	public void setApplication(Application application) {this.application = application;}
	
	public ApplicationStatus getStatus() {return status;}
	public void setStatus(ApplicationStatus status) {this.status = status;}
	public boolean updateStatus(ApplicationStatus status) {this.status = status; return true;}
	
	public Date getRequestDate() {return requestDate;}
	public void setReRequestDate(Date requestdate) {this.requestDate = requestdate;}
	
}
