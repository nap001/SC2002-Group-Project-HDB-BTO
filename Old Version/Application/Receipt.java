package Application;
import Application.*;
import Boundaries.*;
import Controller.*;
import Entities.*;
import Enum.*;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Receipt {
	    private User applicant;
		private Project project;
	    private FlatType flatType;
	    private int flatID;
	    private Date receiptDate;
	    private String receiptContent;
	    private HDBOfficer officer;

	    // Constructor
	    public Receipt(User applicant, Project project, FlatType flatType, int flatID, HDBOfficer officer) {
	        this.applicant = applicant;
	        this.project = project;
	        this.flatType = flatType;
	        this.flatID = flatID;
	        this.receiptDate = new Date();
	        this.officer = officer;
	    }

		public void generateReceipt() {
	
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		    
		    StringBuilder printableReceipt = new StringBuilder();
		    
		    printableReceipt.append("HDB BOOKING RECEIPT\n");
		    printableReceipt.append("====================\n");
		    printableReceipt.append("Receipt Date: ").append(dateFormat.format(receiptDate)).append("\n");
		    
		    printableReceipt.append("Applicant Details:\n");
		    printableReceipt.append("Name: ").append(applicant.getName()).append("\n");
		    printableReceipt.append("NRIC: ").append(applicant.getNRIC()).append("\n");
		    
		    printableReceipt.append("Project Details:\n");
		    printableReceipt.append("Project: ").append(project.getProjectName()).append("\n");
		    printableReceipt.append("Flat Type: ").append(flatType.toString()).append("\n");
		    printableReceipt.append("Flat ID: ").append(flatID).append("\n\n");
		    
		    printableReceipt.append("Processed By:\n");
		    printableReceipt.append("Officer Name: ").append(officer.getName()).append("\n\n");
		    printableReceipt.append("Officer NRIC: ").append(officer.getNRIC()).append("\n\n");
		    
		    printableReceipt.append("Important Information:\n");
		    printableReceipt.append("1. Please retain this receipt for your records.\n");
		    printableReceipt.append("2. For enquiries, contact HDB Customer Service.\n");
		    
		    // Set the generated content
		    this.receiptContent = printableReceipt.toString();
		}
	    
	    public User getApplicant() {
			return applicant;
		}

		public void setApplicant(User applicant) {
			this.applicant = applicant;
		}

		public Project getProject() {
			return project;
		}

		public void setProject(Project project) {
			this.project = project;
		}

		public FlatType getFlatType() {
			return flatType;
		}

		public void setFlatType(FlatType flatType) {
			this.flatType = flatType;
		}

		public int getFlatID() {
			return flatID;
		}

		public void setFlatID(int flatID) {
			this.flatID = flatID;
		}

		public Date getReceiptDate() {
			return receiptDate;
		}

		public void setReceiptDate(Date receiptDate) {
			this.receiptDate = receiptDate;
		}

		public String getReceiptContent() {
			return receiptContent;
		}

		public void setReceiptContent(String receiptContent) {
			this.receiptContent = receiptContent;
		}
		
		public HDBOfficer getOfficer() {
			return officer;
		}

		public void setOfficer(HDBOfficer officer) {
			this.officer = officer;
		}
	}

