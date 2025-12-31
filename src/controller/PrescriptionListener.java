/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package controller;

import java.util.Date;

public interface PrescriptionListener {
    void onAddPrescription(String patientId, String clinicianId, String appointmentId,
                           Date prescriptionDate, String medicationName, String dosage,
                           String frequency, int durationDays, String quantity,
                           String instruction, String pharmacyName);
}
