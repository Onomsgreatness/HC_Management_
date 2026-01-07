package controller;

import java.util.Date;

public interface EditPrescriptionListener {
    void onEditPrescription(
            String prescriptionId, String patientId, String clinicianId, String appointmentId,
            Date prescriptionDate, String medicationName, String dosage, String frequency,
            int durationDays, String quantity, String instruction, String pharmacyName, String status,
            Date issueDate, Date collectionDate
    );
}
