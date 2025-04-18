package Database;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Boundary.Applicant;
import Entity.FlatBooking;

public class FlatBookingList implements Serializable{
    private List<FlatBooking> flatBookings;
    private static final long serialVersionUID = 1L;  // Add serialVersionUID for version control

    public FlatBookingList() {
        this.flatBookings = new ArrayList<>();
    }

    public void addBooking(FlatBooking booking) {
        if (booking != null) {
            flatBookings.add(booking);
        }
    }

    public boolean removeBooking(FlatBooking booking) {
        return flatBookings.remove(booking);
    }

    public List<FlatBooking> getAllBookings() {
        return new ArrayList<>(flatBookings);
    }

    public List<FlatBooking> getBookingsByApplicantNRIC(String nric) {
        return flatBookings.stream()
                .filter(b -> b.getApplicant().getNRIC().equalsIgnoreCase(nric))
                .collect(Collectors.toList());
    }

    public List<FlatBooking> getBookingsByProjectName(String projectName) {
        return flatBookings.stream()
                .filter(b -> b.getProject().getProjectName().equalsIgnoreCase(projectName))
                .collect(Collectors.toList());
    }



    public void clearAllBookings() {
        flatBookings.clear();
    }

    public FlatBooking getBookingByApplicant(Applicant applicant) {
        if (applicant == null) return null;

        for (FlatBooking booking : flatBookings) {
            if (booking.getApplicant().equals(applicant)) {
                return booking;
            }
        }
        return null; // No matching booking found
    }

}
