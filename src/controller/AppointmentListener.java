/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package controller;

import java.util.Date;

public interface AppointmentListener {
    void onAddAppointment(String patientId, String clinicianId, String facilityId,
                          Date appointmentDate, String appointmentTime, int durationMinutes,
                          String appointmentType, String reasonForVisit, String notes);
}
