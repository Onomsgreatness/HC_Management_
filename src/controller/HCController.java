package controller;

import model.*;
import view.HCView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * HCController (MVC)
 * - Listener interfaces live here (Bookshop style)
 * - Referrals are processed by ReferralManager (Singleton) to avoid duplication
 */
public class HCController {

    // ================== Controller fields ==================

    private final HCModel model;
    private final HCView view;

    private static final String PRESCRIPTION_OUTPUT_FILE = "prescriptions_output.txt";

    public HCController(HCModel model, HCView view) {
        this.model = model;
        this.view = view;

        wireViewListeners();
        loadIntoTables();
    }

    // ================== Wiring ==================

    private void wireViewListeners() {

        // Patients
        view.setAddPatientListener(p -> {
            model.addPatient(p);
            loadIntoTables();
            view.showSuccess("Patient added.");
        });

        view.setUpdatePatientListener((id, updated) -> {
            model.updatePatient(id, updated);
            loadIntoTables();
            view.showSuccess("Patient updated.");
        });

        view.setDeletePatientListener(id -> {
            model.deletePatient(id);
            loadIntoTables();
            view.showSuccess("Patient deleted.");
        });

        // Clinicians
        view.setAddClinicianListener(c -> {
            model.addClinician(c);
            loadIntoTables();
            view.showSuccess("Clinician added.");
        });

        view.setUpdateClinicianListener((id, updated) -> {
            model.updateClinician(id, updated);
            loadIntoTables();
            view.showSuccess("Clinician updated.");
        });

        view.setDeleteClinicianListener(id -> {
            model.deleteClinician(id);
            loadIntoTables();
            view.showSuccess("Clinician deleted.");
        });

        // Appointments
        view.setAddAppointmentListener(a -> {
            model.addAppointment(a);
            loadIntoTables();
            view.showSuccess("Appointment added.");
        });

        view.setUpdateAppointmentListener((id, updated) -> {
            model.updateAppointment(id, updated);
            loadIntoTables();
            view.showSuccess("Appointment updated.");
        });

        view.setDeleteAppointmentListener(id -> {
            model.deleteAppointment(id);
            loadIntoTables();
            view.showSuccess("Appointment deleted.");
        });

        // Prescriptions
        view.setAddPrescriptionListener((rx, generateDoc) -> {
            model.addPrescription(rx, generateDoc);
            if (generateDoc) writePrescriptionText(rx);
            loadIntoTables();
            view.showSuccess("Prescription added.");
        });

        view.setUpdatePrescriptionListener((id, updated) -> {
            model.updatePrescription(id, updated);
            loadIntoTables();
            view.showSuccess("Prescription updated.");
        });

        view.setDeletePrescriptionListener(id -> {
            model.deletePrescription(id);
            loadIntoTables();
            view.showSuccess("Prescription deleted.");
        });

        // Referrals (Singleton does output)
        view.setCreateReferralListener((ref, autoProcess) -> {
            boolean ok = model.createReferral(ref);
            if (!ok) {
                view.showError("Referral failed: check Patient/Clinician/Facility IDs exist.");
                return;
            }

            // queue + optionally process (ReferralManager writes referral output + audit)
            ReferralManager.getInstance().enqueueReferral(ref);

            if (autoProcess) {
                ReferralManager.getInstance().processNextReferral(model);
            }

            loadIntoTables();
            view.showSuccess(autoProcess ? "Referral created & processed." : "Referral created & queued.");
        });

        view.setUpdateReferralStatusListener((refId, status) -> {
            model.updateReferralStatus(refId, status);
            loadIntoTables();
            view.showSuccess("Referral status updated.");
        });

//        view.setProcessNextReferralListener(() -> {
//            ReferralManager.getInstance().processNextReferral(model);
//            loadIntoTables();
//            view.showSuccess("Processed next referral (if any).");
//        });

        // Refresh / Save
        view.setRefreshAllListener(() -> {
            model.loadAllData();
            loadIntoTables();
            view.showSuccess("Refreshed from CSV files.");
        });

        view.setSaveAllListener(() -> {
            if (!model.hasAnyDataLoaded()) {
                view.showError("Model empty - not saving (prevents wiping CSV).");
                return;
            }
            model.saveAllData();
            view.showSuccess("Saved all data.");
        });

        // Close
        view.setOnCloseListener(() -> {
            if (model.hasAnyDataLoaded()) model.saveAllData();
        });
    }

    // ================== Table refresh ==================

    private void loadIntoTables() {
        view.refreshPatientsTable(model.getAllPatients());
        view.refreshCliniciansTable(model.getAllClinicians());
        view.refreshAppointmentsTable(model.getAllAppointments());
        view.refreshPrescriptionsTable(model.getAllPrescriptions());
        view.refreshReferralsTable(model.getAllReferrals());
    }

    // ================== Prescription document output ==================

    private void writePrescriptionText(Prescription rx) {
        Patient p = model.getPatient(rx.getPatientId());
        Clinician c = model.getClinician(rx.getClinicianId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder sb = new StringBuilder();
        sb.append("=== PRESCRIPTION DOCUMENT (Simulated) ===\n");
        sb.append("Prescription ID: ").append(rx.getPrescriptionId()).append("\n");
        sb.append("Date: ").append(rx.getPrescriptionDate() != null ? sdf.format(rx.getPrescriptionDate()) : "N/A").append("\n\n");

        sb.append("PATIENT:\n");
        if (p != null) {
            sb.append("  ").append(p.getFirstName()).append(" ").append(p.getLastName()).append("\n");
            sb.append("  NHS: ").append(p.getNhsNumber()).append("\n");
        } else {
            sb.append("  Unknown patient: ").append(rx.getPatientId()).append("\n");
        }
        sb.append("\n");

        sb.append("PRESCRIBER:\n");
        if (c != null) {
            sb.append("  ").append(c.getTitle()).append(" ").append(c.getFirstName()).append(" ").append(c.getLastName()).append("\n");
            sb.append("  Speciality: ").append(c.getSpeciality()).append("\n");
        } else {
            sb.append("  Unknown clinician: ").append(rx.getClinicianId()).append("\n");
        }
        sb.append("\n");

        sb.append("MEDICATION:\n");
        sb.append("  Name: ").append(rx.getMedicationName()).append("\n");
        sb.append("  Dosage: ").append(rx.getDosage()).append("\n");
        sb.append("  Frequency: ").append(rx.getFrequency()).append("\n");
        sb.append("  DurationDays: ").append(rx.getDurationDays()).append("\n");
        sb.append("  Quantity: ").append(rx.getQuantity()).append("\n");
        sb.append("  Instructions: ").append(rx.getInstruction()).append("\n\n");

        sb.append("PHARMACY: ").append(rx.getPharmacyName()).append("\n");
        sb.append("STATUS: ").append(rx.getStatus()).append("\n");
        sb.append("========================================\n\n");

        appendToFile(PRESCRIPTION_OUTPUT_FILE, sb.toString());
        appendToFile("prescriptions_audit_log.txt",
                sdf.format(new Date()) + " - Created prescription " + rx.getPrescriptionId()
                        + " for patient " + rx.getPatientId() + "\n");
    }

    private void appendToFile(String filename, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(content);
            if (!content.endsWith("\n")) bw.newLine();
        } catch (IOException e) {
            view.showError("Failed writing file: " + filename + " (" + e.getMessage() + ")");
        }
    }

    // convenience helpers (optional)
    public String nextPatientId() { return model.generatePatientId(); }
    public String nextClinicianId() { return model.generateClinicianId(); }
    public String nextAppointmentId() { return model.generateAppointmentId(); }
    public String nextPrescriptionId() { return model.generatePrescriptionId(); }
    public String nextReferralId() { return model.generateReferralId(); }

    public ArrayList<Facility> getAllFacilities() { return model.getAllFacilities(); }
    public ArrayList<Patient> getAllPatients() { return model.getAllPatients(); }
    public ArrayList<Clinician> getAllClinicians() { return model.getAllClinicians(); }

    // ================== Listener Interfaces (Bookshop style) ==================

    public interface AddPatientListener { void onAddPatient(Patient p); }
    public interface UpdatePatientListener { void onUpdatePatient(String patientId, Patient updated); }
    public interface DeletePatientListener { void onDeletePatient(String patientId); }

    public interface AddClinicianListener { void onAddClinician(Clinician c); }
    public interface UpdateClinicianListener { void onUpdateClinician(String clinicianId, Clinician updated); }
    public interface DeleteClinicianListener { void onDeleteClinician(String clinicianId); }

    public interface AddAppointmentListener { void onAddAppointment(Appointment a); }
    public interface UpdateAppointmentListener { void onUpdateAppointment(String appointmentId, Appointment updated); }
    public interface DeleteAppointmentListener { void onDeleteAppointment(String appointmentId); }

    public interface AddPrescriptionListener { void onAddPrescription(Prescription rx, boolean generateDoc); }
    public interface UpdatePrescriptionListener { void onUpdatePrescription(String rxId, Prescription updated); }
    public interface DeletePrescriptionListener { void onDeletePrescription(String rxId); }

    public interface CreateReferralListener { void onCreateReferral(Referral r, boolean autoProcess); }
    public interface UpdateReferralStatusListener { void onUpdateReferralStatus(String referralId, ReferralStatus status); }
    public interface ProcessNextReferralListener { void onProcessNextReferral(); }

    public interface RefreshAllListener { void onRefreshAll(); }
    public interface SaveAllListener { void onSaveAll(); }
    public interface OnCloseListener { void onClose(); }
}
