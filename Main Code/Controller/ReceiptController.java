package Controller;

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
import java.util.regex.Pattern;

public class ReceiptController {
	
	public ReceiptController() {
	}

	public boolean generateReceiptByFlatBooking(FlatBooking flatBooking) {
		if (flatBooking == null) {
            return false;
        }
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    StringBuilder printableReceipt = new StringBuilder();
		printableReceipt.append("HDB BOOKING RECEIPT\n");
	    printableReceipt.append("====================\n");
	    printableReceipt.append("Receipt Date: ").append(dateFormat.format(new Date())).append("\n");
	    
	    printableReceipt.append("Applicant Details:\n");
	    printableReceipt.append("Name: ").append(flatBooking.getApplicant().getName()).append("\n");
	    printableReceipt.append("NRIC: ").append(flatBooking.getApplicant().getNRIC()).append("\n");
	    
	    printableReceipt.append("Project Details:\n");
	    printableReceipt.append("Project: ").append(flatBooking.getProject().getProjectName()).append("\n");
	    printableReceipt.append("Flat Type: ").append(flatBooking.getFlatType().toString()).append("\n");
	    printableReceipt.append("Flat ID: ").append(flatBooking.getFlatID()).append("\n\n");
	    
	    printableReceipt.append("Processed By:\n");
	    printableReceipt.append("Officer Name: ").append(flatBooking.getOfficer().getName()).append("\n\n");
	    printableReceipt.append("Officer NRIC: ").append(flatBooking.getOfficer().getNRIC()).append("\n\n");
	    
	    printableReceipt.append("Important Information:\n");
	    printableReceipt.append("1. Please retain this receipt for your records.\n");
	    printableReceipt.append("2. For enquiries, contact HDB Customer Service.\n");
	    return true;
	}
	
	public boolean generateReceiptByApplication(Application application) {
		if (application == null || application.getApplicant() == null || 
	            application.getProject() == null || application.getFlatType() == null) {
	            return false;
	        }
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	    StringBuilder printableReceipt = new StringBuilder();
		printableReceipt.append("HDB BOOKING RECEIPT\n");
	    printableReceipt.append("====================\n");
	    printableReceipt.append("Receipt Date: ").append(dateFormat.format(new Date())).append("\n");
	    
	    printableReceipt.append("Applicant Details:\n");
	    printableReceipt.append("Name: ").append(application.getApplicant().getName()).append("\n");
	    printableReceipt.append("NRIC: ").append(application.getApplicant().getNRIC()).append("\n");
	    
	    printableReceipt.append("Project Details:\n");
	    printableReceipt.append("Project: ").append(application.getProject().getProjectName()).append("\n");
	    printableReceipt.append("Flat Type: ").append(application.getFlatType().toString()).append("\n");
	    printableReceipt.append("Flat ID: ").append(application.getApplicant().getFlatBooking().getFlatID()).append("\n\n");
	    
	    printableReceipt.append("Processed By:\n");
	    printableReceipt.append("Officer Name: ").append(application.getApplicant().getFlatBooking().getOfficer().getName()).append("\n\n");
	    printableReceipt.append("Officer NRIC: ").append(application.getApplicant().getFlatBooking().getOfficer().getNRIC()).append("\n\n");
	    
	    printableReceipt.append("Important Information:\n");
	    printableReceipt.append("1. Please retain this receipt for your records.\n");
	    printableReceipt.append("2. For enquiries, contact HDB Customer Service.\n");
	    return true;
	}
	
	public static void setReceipts(List<Receipt> receipts) {
		// TODO Auto-generated method stub
		
	}

}
