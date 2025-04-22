package interfaces;

import entity.FlatBooking;
import entity.HDBOfficer;

public interface IFlatBookingControl {
    boolean approveFlatBookingInteractive(HDBOfficer officer, IProjectQueryControl projectControl);
}
