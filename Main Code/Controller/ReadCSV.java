package Controller;
import Application.*;
import Entities.*;
import Enum.*;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ReadCSV {
	    private static final String APPLICATIONS_CSV = "C:\\Users\\wenju\\eclipse-workspace\\SC2002proj\\src\\Project\\BTOApplicationList.csv";
	    private static final String ENQUIRIES_CSV = "C:\\Users\\wenju\\eclipse-workspace\\SC2002proj\\src\\Project\\Enquiry.csv";
	    private static final String OFFICER_REGISTRATION_CSV = "C:\\Users\\wenju\\eclipse-workspace\\SC2002proj\\src\\Project\\OfficerRegistrationList.csv";  
	    private static final String PROJECTS_CSV = "C:\\Users\\wenju\\eclipse-workspace\\SC2002proj\\src\\Project\\ProjectList.csv";
	    private static final String USERS_CSV = "C:\\Users\\wenju\\eclipse-workspace\\SC2002proj\\src\\Project\\UserList.csv"; 
	    private static final String NOTIFICATION_CSV = "C:\\Users\\wenju\\eclipse-workspace\\SC2002proj\\src\\Project\\Notifications.csv";
	    private static final String FLATBOOKING_CSV = "C:\\Users\\wenju\\eclipse-workspace\\SC2002proj\\src\\Project\\FlatBooking.csv";
	    private static final String RECEIPT_CSV = "C:\\Users\\wenju\\eclipse-workspace\\SC2002proj\\src\\Project\\Receipt.csv";
	    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	    

	    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");
	    
	    public List<Application> loadApplicationsFromCSV() {
	        List<Application> applications = new ArrayList<>();
	        try (BufferedReader br = new BufferedReader(new FileReader(APPLICATIONS_CSV))) {
	            String line;
	            br.readLine();
	            while ((line = br.readLine()) != null) {
	                String[] columns = line.split(",");
	                if (columns.length == 4) { 
	                    String nric = columns[0];
	                    String projectName = columns[1];
	                    String flatType = columns[2];
	                    String statusString = columns[3].trim();
	                    ApplicationStatus status = null;
	                    try {
	                        status = ApplicationStatus.valueOf(statusString);
	                    } catch (IllegalArgumentException e) {
	                        System.out.println("Invalid status value: " + statusString + " for project: " + projectName + ". Defaulting to PENDING.");
	                        status = ApplicationStatus.PENDING;
	                    }

	                    Application app = new Application(nric, projectName, flatType, status,);
	                    applications.add(app);
	                }
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return applications;
	    }
	    
	    public void saveApplicationsToCSV(List<Application> applications) {
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPLICATIONS_CSV, true))) { // Append mode (true)
	            
	            // Check if the file is empty and write the header if needed
	            if (new java.io.File(APPLICATIONS_CSV).length() == 0) {
	                writer.write("nric,projectName,flatType,status\n");
	            }

	            // Write each application in CSV format
	            for (Application app : applications) {
	                // Write the application data in CSV format
	                String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\"",
	                    app.getNric(),
	                    app.getProjectName(),
	                    app.getFlatType(),
	                    app.getStatus().name()); // Assuming ApplicationStatus is an enum
	                writer.write(line + "\n");
	            }

	            System.out.println("Applications saved successfully!");
	        } catch (IOException e) {
	            System.out.println("Error saving applications to CSV");
	            e.printStackTrace();
	        }
	    }
	    
	        public List<Enquiry> loadEnquiriesFromCSV() {
	            List<Enquiry> enquiries = new ArrayList<>();
	            try (BufferedReader br = new BufferedReader(new FileReader(ENQUIRIES_CSV))) {
	                String line;
		            br.readLine();
	                while ((line = br.readLine()) != null) {
	                    String[] columns = line.split(",");
	                    if (columns.length >= 8) { 
	                        String applicantNric = columns[0];
	                        String projectName = columns[1];
	                        String enquiryContent = columns[2];
	                        int enquiryID = parseInt(columns[3]);
	                        String StringsubmissionDate = columns[4];
	                        String response = columns[5];
	                        String responderNRIC = columns[6];
	                        String StringresponseDate = columns[7];
	                        Date submissionDate = null;
	                        Date responseDate = null;
	                        try {
	                            // Convert submissionDate and responseDate to Date objects
	                            submissionDate = dateFormat.parse(StringsubmissionDate);
	                            responseDate = dateFormat.parse(StringresponseDate);
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }

	                        Enquiry enquiry = new Enquiry(enquiryID, projectName, enquiryContent, applicantNric, response, responderNRIC, submissionDate, responseDate);
	                        enquiries.add(enquiry);
	                    	}
	                    }
	                } 
	            catch (IOException e) { e.printStackTrace();}
	            return enquiries;
	        }

	        public void saveEnquiriesToCSV(List<Enquiry> enquiries) {
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ENQUIRIES_CSV, true))) { // Append mode (true)
	                // Check if the file is empty and write header only if needed
	                if (new java.io.File(ENQUIRIES_CSV).length() == 0) {
	                    writer.write("enquiryID,projectName,enquiryContent,applicantNric,submissionDate,response,responderNRIC,responseDate\n");
	                }

	                // SimpleDateFormat to format dates
	                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust date format as needed

	                // Write each enquiry in CSV format
	                for (Enquiry enquiry : enquiries) {
	                    // Write the enquiry data in CSV format, handling fields directly without escaping
	                    String line = String.format("\"%d\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
	                        enquiry.getEnquiryID(),
	                        enquiry.getProjectName(),
	                        enquiry.getEnquiryContent(),
	                        enquiry.getApplicantNric(),
	                        dateFormat.format(enquiry.getSubmissionDate()), // Use date format for submissionDate
	                        enquiry.getResponse(),
	                        enquiry.getResponderNric(),
	                        dateFormat.format(enquiry.getResponseDate())); // Use date format for responseDate
	                    writer.write(line + "\n");
	                }

	                System.out.println("Enquiries saved successfully!");
	            } catch (IOException e) {
	                System.out.println("Error saving enquiries to CSV");
	                e.printStackTrace();
	            }
	        }
	        
	        public List<OfficerApplication> loadOfficerApplicationsFromCSV() {
	    	    List<OfficerApplication> officerApplications = new ArrayList<>();
	    	    try (BufferedReader br = new BufferedReader(new FileReader(OFFICER_REGISTRATION_CSV))) {
	    	        String line;
	    	        br.readLine(); // Skip the header line
	    	        
	    	        while ((line = br.readLine()) != null) {
	    	            String[] columns = line.split(",");
	    	            if (columns.length == 4) {  // Assuming 4 columns: Officer Name, Officer NRIC, Project Name, Application Status
	    	                String officerNRIC = columns[0].trim();
	    	                String projectName = columns[2].trim();
	    	                String statusString = columns[3].trim();
		                    ApplicationStatus status = null;
		                    try {
		                        status = ApplicationStatus.valueOf(statusString);
		                    } catch (IllegalArgumentException e) {
		                        System.out.println("Invalid status value: " + statusString + " for project: " + projectName + ". Defaulting to PENDING.");
		                        status = ApplicationStatus.PENDING;
		                    }

	    	                OfficerApplication officerApplication = new OfficerApplication (officerNRIC, projectName, status); // Example, modify if needed
	    	                officerApplications.add(officerApplication); 
	    	            }
	    	        }
	    	    } catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    	    return officerApplications;
	    	}
	        
	        public boolean saveOfficerApplications(List<OfficerApplication> officerApplications) {
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(OFFICER_REGISTRATION_CSV))) {
	                // Write header
	                writer.write("Officer NRIC\tProject Name\tStatus");
	                writer.newLine();
	                
	                // Write data
	                for (OfficerApplication officerApplication : officerApplications) {
	                    writer.write(String.format("%s\t%s\t%s", 
	                        officerApplication.getOfficerNRIC(),
	                        officerApplication.getProjectName(),
	                        officerApplication.getApplicationStatus().name()));
	                    writer.newLine();
	                }

	                return true;
	            } catch (IOException e) {
	                System.out.println("Error: Failed to save officer applications data. " + e.getMessage());
	                return false;
	            }
	        }
	
	public List<Project> loadProjectsFromCSV() {
	    List<Project> projects = new ArrayList<>();
	    
	    try (BufferedReader br = new BufferedReader(new FileReader(PROJECTS_CSV))) {
	        String line;
	        br.readLine();
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(",");
	            
	            if (columns.length == 13) {
	                String projectName = columns[0].trim();
	                String neighborhood = columns[1].trim();
	                String region = columns[2].trim();
	                int numUnitsType1 = Integer.parseInt(columns[3].trim());
	                int sellingPriceType1 = Integer.parseInt(columns[4].trim());
	                int numUnitsType2 = Integer.parseInt(columns[5].trim());
	                int sellingPriceType2 = Integer.parseInt(columns[6].trim());
	                String applicationOpeningDate = columns[7].trim(); 
	                String applicationClosingDate = columns[8].trim();
	                String manager = columns[9].trim();
	                int officerSlot = Integer.parseInt(columns[10].trim());
	                String officers = columns[11].trim();
	                
	                Project project = new Project(projectName, neighborhood, region, numUnitsType1, sellingPriceType1,
                            numUnitsType2, sellingPriceType2, applicationOpeningDate, applicationClosingDate,
                            manager, officerSlot, List.of(officers.split(",")));
	                projects.add(project);
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

	    return projects;
	}
	
	public void saveProjectsToCSV(List<Project> projects) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(PROJECTS_CSV))) {
	        
	        // Write header for the CSV file
	        writer.write("projectName,neighborhood,region,numUnitsType1,sellingPriceType1,numUnitsType2,sellingPriceType2,applicationOpeningDate,applicationClosingDate,manager,officerSlot,officers\n");

	        // Write each project in CSV format
	        for (Project project : projects) {
	            String officers = String.join(",", project.getOfficers()); // Join the list of officers with commas

	            String line = String.format("\"%s\",\"%s\",\"%s\",%d,%d,%d,%d,\"%s\",\"%s\",\"%s\",%d,\"%s\"",
	                project.getProjectName(),
	                project.getNeighborhood(),
	                project.getRegion(),
	                project.getNumUnitsType1(),
	                project.getSellingPriceType1(),
	                project.getNumUnitsType2(),
	                project.getSellingPriceType2(),
	                project.getApplicationOpeningDate(),
	                project.getApplicationClosingDate(),
	                project.getManager(),
	                project.getOfficerSlot(),
	                officers); // Format the officers as a comma-separated string
	            writer.write(line + "\n");
	        }

	        System.out.println("Projects saved successfully!");
	    } catch (IOException e) {
	        System.out.println("Error saving projects to CSV");
	        e.printStackTrace();
	    }
	}
	public List<User> loadUsersFromCSV() {
		List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_CSV))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                
                if (columns.length == 6) {  // Assuming 6 columns (NRIC, Name, Age, MaritalStatus, Password, UserType)
                    String name = columns[0];
                    String nric = columns[1];
                    int age = Integer.parseInt(columns[2]);
                    String maritalStatus = columns[3];
                    String password = columns[4];
                    String userType = columns[5];

                    if (userType.equals("Officer")) {
                        users.add(new HDBOfficer(name, nric, age, maritalStatus, password, userType));
                    } else if (userType.equals("Manager")) {
                        users.add(new HDBManager(name, nric, age, maritalStatus, password, userType));
                    } else if (userType.equals("Applicant")) {
                        users.add(new Applicant(name, nric, age, maritalStatus, password, userType));
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to load all applications, please try again. " + e.getMessage());
        }
        return users;
	}
	
	public void saveUsersToCSV(List<User> users) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_CSV))) {
	        
	        // Write header for the CSV file
	        writer.write("name,nric,age,maritalStatus,password,userType\n");

	        // Write each user in CSV format
	        for (User user : users) {
	            // Write user data in CSV format
	            String line = String.format("\"%s\",\"%s\",%d,\"%s\",\"%s\",\"%s\"",
	                user.getName(),
	                user.getNric(),
	                user.getAge(),
	                user.getMaritalStatus(),
	                user.getPassword(),
	                user.getUserType()); // Assuming getUserType() returns the correct type

	            writer.write(line + "\n");
	        }

	        System.out.println("Users saved successfully!");
	    } catch (IOException e) {
	        System.out.println("Error saving users to CSV");
	        e.printStackTrace();
	    }
	}
	
	public List<Notification> loadNotificationsFromCSV() {
	    List<Notification> notifications = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(NOTIFICATION_CSV))) {
	        String line;
	        br.readLine(); // Skip the header
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(",");
	            if (columns.length == 2) {  // NRIC of User and Notification
	                String nric = columns[0].trim();
	                String notification = columns[1].trim();
	                
	                notifications.add(new Notification(nric, notification));
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Unable to load notifications, please try again. " + e.getMessage());
	    }
	    return notifications;
	}
	
	
	public void saveNotificationsToCSV(List<Notification> notifications) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(NOTIFICATION_CSV))) {
	        // Write header
	        writer.write("NRIC of User,Notification\n");

	        // Write each notification to the CSV
	        for (Notification notification : notifications) {
	            String line = String.format("\"%s\",\"%s\"",
	                notification.getNric(),
	                notification.getMessage());
	            writer.write(line + "\n");
	        }
	        System.out.println("Notifications saved successfully!");
	    } catch (IOException e) {
	        System.out.println("Error saving notifications to CSV");
	        e.printStackTrace();
	    }
	}

	public List<FlatBooking> loadFlatBookingsFromCSV() {
	    List<FlatBooking> flatBookings = new ArrayList<>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if needed
	    try (BufferedReader br = new BufferedReader(new FileReader(FLATBOOKING_CSV))) {
	        String line;
	        br.readLine();  // Skip header
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(",");
	            if (columns.length == 7) {  // Assuming 7 columns as mentioned
	                String nric = columns[0].trim();
	                String projectName = columns[1].trim();
	                FlatType flatType = FlatType.valueOf(columns[2].trim());  // Assuming FlatType is an enum
	                String flatId = columns[3].trim();
	                String bookingDateStr = columns[4].trim();  // Booking Date as String
	                ApplicationStatus bookingStatus = ApplicationStatus.valueOf(columns[5].trim());  // Assuming ApplicationStatus is an enum
	                String rejectionReason = columns[6].trim();

	                // Parse bookingDate to Date
	                Date bookingDate = null;
	                try {
	                    bookingDate = dateFormat.parse(bookingDateStr);
	                } catch (Exception e) {
	                    System.out.println("Error parsing booking date: " + bookingDateStr);
	                    e.printStackTrace();
	                }

	                // Create FlatBooking object
	                FlatBooking booking = new FlatBooking(nric, projectName, flatType, flatId, bookingDate, bookingStatus, rejectionReason);
	                flatBookings.add(booking);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Unable to load flat bookings, please try again. " + e.getMessage());
	    }
	    return flatBookings;
	}
	
	public void saveFlatBookingsToCSV(List<FlatBooking> flatBookings) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if needed
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(FLATBOOKING_CSV))) {
	        // Write header
	        writer.write("NRIC,Project Name,Flat Type,Flat ID,Booking Date,Booking Status,Rejection Reason\n");

	        // Write each flat booking in CSV format
	        for (FlatBooking booking : flatBookings) {
	            String bookingDateStr = dateFormat.format(booking.getBookingDate());  // Format bookingDate to string

	            String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
	                booking.getNric(),
	                booking.getProjectName(),
	                booking.getFlatType().name(),  // Assuming FlatType is an enum
	                booking.getFlatId(),
	                bookingDateStr,
	                booking.getBookingStatus().name(),  // Assuming ApplicationStatus is an enum
	                booking.getRejectionReason());
	            writer.write(line + "\n");
	        }
	        System.out.println("Flat bookings saved successfully!");
	    } catch (IOException e) {
	        System.out.println("Error saving flat bookings to CSV");
	        e.printStackTrace();
	    }
	}
	
	public List<Receipt> loadReceiptsFromCSV() {
	    List<Receipt> receipts = new ArrayList<>();
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if needed
	    try (BufferedReader br = new BufferedReader(new FileReader(RECEIPT_CSV))) {
	        String line;
	        br.readLine();  // Skip header
	        while ((line = br.readLine()) != null) {
	            String[] columns = line.split(",");
	            if (columns.length == 7) {  // Assuming 7 columns as mentioned
	                String applicantNric = columns[0].trim();
	                String projectName = columns[1].trim();
	                String officerNric = columns[2].trim();
	                FlatType flatType = FlatType.valueOf(columns[3].trim());  // Assuming FlatType is an enum
	                String flatId = columns[4].trim();
	                String receiptDateStr = columns[5].trim();  // Receipt Date as String
	                String receiptContent = columns[6].trim();

	                // Parse receiptDate to Date
	                Date receiptDate = null;
	                try {
	                    receiptDate = dateFormat.parse(receiptDateStr);
	                } catch (Exception e) {
	                    System.out.println("Error parsing receipt date: " + receiptDateStr);
	                    e.printStackTrace();
	                }

	                // Create Receipt object
	                Receipt receipt = new Receipt(applicantNric, projectName, officerNric, flatType, flatId, receiptDate, receiptContent);
	                receipts.add(receipt);
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Unable to load receipts, please try again. " + e.getMessage());
	    }
	    return receipts;
	}
	
	public void saveReceiptsToCSV(List<Receipt> receipts) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Adjust format if needed
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(RECEIPT_CSV))) {
	        // Write header
	        writer.write("Applicant NRIC,Project Name,Officer NRIC,Flat Type,Flat ID,Receipt Date,Receipt Content\n");

	        // Write each receipt in CSV format
	        for (Receipt receipt : receipts) {
	            String receiptDateStr = dateFormat.format(receipt.getReceiptDate());  // Format receiptDate to string

	            String line = String.format("\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"",
	                receipt.getApplicantNric(),
	                receipt.getProjectName(),
	                receipt.getOfficerNric(),
	                receipt.getFlatType().name(),  // Assuming FlatType is an enum
	                receipt.getFlatId(),
	                receiptDateStr,
	                receipt.getReceiptContent());
	            writer.write(line + "\n");
	        }
	        System.out.println("Receipts saved successfully!");
	    } catch (IOException e) {
	        System.out.println("Error saving receipts to CSV");
	        e.printStackTrace();
	    }
	}

	
	public int parseInt(String value) {
	    try {
	        return Integer.parseInt(value);
	    } catch (NumberFormatException e) {
	        System.out.println("Error parsing integer: " + value);
	        return -1;  // Return a default or error value (-1 or any value you prefer to indicate an invalid parse)
	    }
	}

}
