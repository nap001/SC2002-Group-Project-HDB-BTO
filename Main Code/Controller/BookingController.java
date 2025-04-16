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
import java.util.Date;
import java.util.regex.Pattern;

public class BookingController {
	private Map<String, FlatBooking> FlatBookings;
	private ReceiptController ReceiptController;
	private Map<String, Receipt> Receipts;
	
	public BookingController() {
		Map<String, FlatBooking> FlatBookings = new HashMap<>();
		ReceiptController ReceiptController = new ReceiptController();
		Map<String, Receipt> Receipts  = new HashMap<>();
	}
	
	public boolean createBooking(FlatBooking flatBooking) {
		if (flatBooking != null) {
			return false;
		}
		if (FlatBookings.containsKey(flatBooking.getApplicant().getNRIC())) {
			return false;
		}
		
		flatBooking.setBookingStatus(ApplicationStatus.SUCCESSFUL);
		FlatBookings.put(flatBooking.getApplicant().getNRIC(), flatBooking);
		flatBooking.getApplicant().setConfirmedFlatType(flatBooking.getFlatType());
		
		if (flatBooking.getApplicant().getApplication() != null && flatBooking.getApplicant().getApplication().getApplicationStatus().equals(ApplicationStatus.SUCCESSFUL)) {
			flatBooking.getApplicant().getApplication().setApplicationStatus(ApplicationStatus.BOOKED);
		}
		return true;
	}
	
	
	public boolean rejectBookingRequest(Application application, String message) {
		if (application != null) {
			return false;
		}
		//Find the FlatBooking Request
		FlatBooking flatBooking = FlatBookings.get(application.getApplicant().getNRIC());
		
		if (flatBooking == null) {
			System.out.println("No such booking request, please contact applicant to book flat.");
			return false;
		}else {
			flatBooking.setBookingStatus(ApplicationStatus.SUCCESSFUL);
			flatBooking.setMessage(message);
			return true;
		}
		
	public boolean isBookingRequestApproved(Application application) {
		if (FlatBookings.get(application.getApplicant().getNRIC()).getBookingStatus().equals(ApplicationStatus.SUCCESSFUL)) {
			return true;
		}else {
			return false;
		}
	
	public FlatBooking getBookingByApplication(Application application) {
		if (application == null) {
			System.out.println("No such application is found");
			return null;
		}
		return FlatBookings.get(application.getApplicant().getNRIC());
	}
	
	public List<FlatBooking> getBookingByApplicationStatus(ApplicationStatus String){
		List<FlatBooking> result = new ArrayList<>();
		for (FlatBooking flatbooking:FlatBookings.values()) {
			if (flatbooking.getBookingStatus().equals(String)){
				result.add(flatbooking);
			}
		}
		if (result.isEmpty()) {
			System.out.println("No such Flat Bookings have been found with application status of " + String);
		}
		return result;
	}
	
	public Receipt createReceipt(FlatBooking flatBooking) {
		if (flatBooking == null || flatBooking.getOfficer() == null) {
			System.out.println("Flat Bookings is still not yet complete, unable to generate receipt.");
			return null;
		}
		//Already has an existing Receipt
		if (Receipts.containsKey(flatBooking.getApplicant().getNRIC())){
			return Receipts.get(flatBooking.getApplicant().getNRIC());
		}
			
		Receipt receipt = new Receipt(flatBooking.getApplicant(),
				flatBooking.getProject(),
				flatBooking.getFlatType(),
				flatBooking.getFlatID(),
				flatBooking.getOfficer());
		String content = ReceiptController.generateReceipt(flatBooking);
		receipt.setReceiptContent(content);
		
		Receipts.put(flatBooking.getApplicant().getNRIC(), receipt);
		}
	
	public boolean hasReceipt(Applicant applicant) {
		return Receipts.containsKey(applicant.getNRIC());
	}
	
	public boolean hasReceipt(String NRIC) {
		return Receipts.containsKey(NRIC);
	}
	
	public Receipt getReceipyByApplicant(String NRIC) {
		return Receipts.get(NRIC);
	}

	public void setBookings(List<FlatBooking> Bookings) {
		this.FlatBookings.clear();
        for (FlatBooking booking : Bookings) {
            this.FlatBookings.put(booking.getApplicant().getNRIC(), booking);
        }
		
	}

	public void setReceipts(List<Receipt> receipts) {
		this.Receipts.clear();
        for (Receipt receipt : receipts) {
            this.Receipts.put(receipt.getApplicant().getNRIC(), receipt);
        }
		
	}
	
	public List<FlatBooking> getAllFlatBooking(){
		return new ArrayList<>(FlatBookings.values());
	}
	public List<Receipt> getAllReceipts(){
		return new ArrayList<>(Receipts.values());
	}

}