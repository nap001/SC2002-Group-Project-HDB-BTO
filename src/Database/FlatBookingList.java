package Database;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Entity.FlatBooking;

public class FlatBookingList {
    private List<FlatBooking> flatBookings;

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
}
