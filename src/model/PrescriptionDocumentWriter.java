/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PrescriptionDocumentWriter {

    private static final String OUT_DIR = "output/prescriptions";

    private PrescriptionDocumentWriter() {}

    public static void write(Prescription rx) {
        if (rx == null) return;

        File dir = new File(OUT_DIR);
        if (!dir.exists()) dir.mkdirs();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String filename = OUT_DIR + "/" + rx.getPrescriptionId() + "_prescription.txt";

        ArrayList<String> lines = new ArrayList<>();
        lines.add("=== PRESCRIPTION CONTENT (SIMULATED) ===");
        lines.add("Prescription ID: " + safe(rx.getPrescriptionId()));
        lines.add("Date: " + (rx.getPrescriptionDate() == null ? "" : sdf.format(rx.getPrescriptionDate())));
        lines.add("");
        lines.add("Patient ID: " + safe(rx.getPatientId()));
        lines.add("Clinician ID: " + safe(rx.getClinicianId()));
        lines.add("Appointment ID: " + safe(rx.getAppointmentId()));
        lines.add("");
        lines.add("Medication: " + safe(rx.getMedicationName()));
        lines.add("Dosage: " + safe(rx.getDosage()));
        lines.add("Frequency: " + safe(rx.getFrequency()));
        lines.add("Duration Days: " + rx.getDurationDays());
        lines.add("Quantity: " + safe(rx.getQuantity()));
        lines.add("");
        lines.add("Instructions:");
        lines.add(safe(rx.getInstruction()));
        lines.add("");
        lines.add("Pharmacy: " + safe(rx.getPharmacyName()));
        lines.add("Status: " + safe(rx.getStatus()));
        lines.add("Issue Date: " + (rx.getIssueDate() == null ? "" : sdf.format(rx.getIssueDate())));
        lines.add("Collection Date: " + (rx.getCollectionDate() == null ? "" : sdf.format(rx.getCollectionDate())));

        CSVHandler.writeLines(filename, lines);
    }

    private static String safe(String s) {
        return s == null ? "" : s;
    }
}
