package controller;

import java.util.Date;

public interface EditAppointmentListener {
    void onEditAppointment(
            String patientId, String clinicianId, String facilityId,
            Date appointmentDate, String appointmentTime, int durationMinutes,
            String appointmentType, String reasonForVisit, String notes
    );
}
