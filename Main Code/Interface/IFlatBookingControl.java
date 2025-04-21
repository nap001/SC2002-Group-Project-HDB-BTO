package Interface;

import Entity.FlatBooking;
import Entity.HDBOfficer;

public interface IFlatBookingControl {
    boolean approveFlatBookingInteractive(HDBOfficer officer, IProjectQueryControl projectControl);
}
