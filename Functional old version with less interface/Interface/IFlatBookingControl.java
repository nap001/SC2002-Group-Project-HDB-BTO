package Interface;

import Boundary.HDBOfficer;
import Entity.FlatBooking;

public interface IFlatBookingControl {
    boolean approveFlatBookingInteractive(HDBOfficer officer);
}
