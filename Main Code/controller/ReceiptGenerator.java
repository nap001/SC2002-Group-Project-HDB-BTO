package controller;

import java.util.List;

import database.FlatBookingList;
import entity.Applicant;
import entity.FlatBooking;
import entity.Project;
import interfaces.IReceiptGenerator;

public class ReceiptGenerator implements IReceiptGenerator {
    private FlatBookingList flatBookingList;

    public ReceiptGenerator(database.FlatBookingList flatBookingList) {
        this.flatBookingList = flatBookingList;
    }

    public void generateReceiptsForProject(Project project) {
        List<FlatBooking> bookings = flatBookingList.getAllBookings();

        boolean found = false;

        for (FlatBooking booking : bookings) {
            if (booking.getProject().getProjectName().equals(project.getProjectName())) {
                Applicant applicant = booking.getApplicant();
                System.out.println("=== Receipt ===");
                System.out.println("Name         : " + applicant.getName());
                System.out.println("NRIC         : " + applicant.getNRIC());
                System.out.println("Age          : " + applicant.getAge());
                System.out.println("Marital Status: " + applicant.getMaritalStatus());
                System.out.println("Flat Type    : " + booking.getFlatType());
                System.out.println("Project Name : " + project.getProjectName());
                System.out.println("Neighbourhood: " + project.getNeighbourhood());
                System.out.println("-----------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No flat bookings found for this project.");
        }
    }
}
