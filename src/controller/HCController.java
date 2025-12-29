package controller;

import model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Controller for Healthcare Referral/Prescription System
 * Coordinates Model and View (MVC)
 */
public class HCController {
    private final HCModel model;
    private final view.HCView view;

    public HCController(HCModel model, view.HCView view) {
        this.model = model;
        this.view = view;

        initView();
        wireListeners();
    }

    private void initView() {
        // initial load into UI tables
        view.refreshPatientsTable(model.getAllPatients());
        view.refreshCliniciansTable(model.getAllClinicians());
        view.refreshAppointmentsTable(model.getAllAppointments());
        view.refreshPrescriptionsTable(model.getAllPrescriptions());
        view.refreshReferralsTable(model.getAllReferrals());
    }

    private void wireListeners() {
        // PATIENTS
        view.setAddPatientListener(p -> {
            model.addPatient(p);
            view.refreshPatientsTable(model.getAllPatients());
            view.showSuccess("Patient added.");
        });

        view.setUpdatePatientListener((patientId, updated) -> {
            model.updatePatient(patientId, updated);
            view.refreshPatientsTable(model.getAllPatients());
            view.showSuccess("Patient updated.");
        });

        view.setDeletePatientListener(patientId -> {
            model.deletePatient(patientId);
            view.refreshPatientsTable(model.getAllPatients());
            view.showSuccess("Patient deleted.");
        });

        // CLINICIANS
        view.setAddClinicianListener(c -> {
            model.addClinician(c);
            view.refreshCliniciansTable(model.getAllClinicians());
            view.showSuccess("Clinician added.");
        });

        view.setUpdateClinicianListener((id, updated) -> {
            model.updateClinician(id, updated);
            view.refreshCliniciansTable(model.getAllClinicians());
            view.showSuccess("Clinician updated.");
        });

        view.setDeleteClinicianListener(id -> {
            model.deleteClinician(id);
            view.refreshCliniciansTable(model.getAllClinicians());
            view.showSuccess("Clinician deleted.");
        });

        // APPOINTMENTS
        view.setAddAppointmentListener(a -> {
            model.addAppointment(a);
            view.refreshAppointmentsTable(model.getAllAppointments());
            view.showSuccess("Appointment added.");
        });

        view.setUpdateAppointmentListener((id, updated) -> {
            model.updateAppointment(id, updated);
            view.refreshAppointmentsTable(model.getAllAppointments());
            view.showSuccess("Appointment updated.");
        });

        view.setDeleteAppointmentListener(id -> {
            model.deleteAppointment(id);
            view.refreshAppointmentsTable(model.getAllAppointments());
            view.showSuccess("Appointment deleted.");
        });

        // PRESCRIPTIONS
        view.setAddPrescriptionListener((rx, generateDoc) -> {
            model.addPrescription(rx, generateDoc);
            if (generateDoc) {
                writePrescriptionText(rx);
            }
            view.refreshPrescriptionsTable(model.getAllPrescriptions());
            view.showSuccess("Prescription added.");
        });

        view.setUpdatePrescriptionListener((id, updated) -> {
            model.updatePrescription(id, updated);
            view.refreshPrescriptionsTable(model.getAllPrescriptions());
            view.showSuccess("Prescription updated.");
        });

        view.setDeletePrescriptionListener(id -> {
            model.deletePrescription(id);
            view.refreshPrescriptionsTable(model.getAllPrescriptions());
            view.showSuccess("Prescription deleted.");
        });

        // REFERRALS (Singleton handled inside model via ReferralManager)
        view.setCreateReferralListener((referral, generateDoc) -> {
            boolean ok = model.createReferral(referral);
            if (!ok) {
                view.showError("Referral failed: check Patient/Clinician/Facility IDs exist.");
                return;
            }
            if (generateDoc) {
                writeReferralEmailText(referral);
            }
            view.refreshReferralsTable(model.getAllReferrals());
            view.showSuccess("Referral created.");
        });

        view.setUpdateReferralStatusListener((referralId, newStatus) -> {
            model.updateReferralStatus(referralId, newStatus);
            view.refreshReferralsTable(model.getAllReferrals());
            view.showSuccess("Referral status updated.");
        });

        // refresh all
        view.setRefreshAllListener(() -> {
            model.loadAllData();
            initView();
            view.showSuccess("Data refreshed from CSV files.");
        });

        view.setSaveAllListener(() -> {
            if (!model.hasAnyDataLoaded()) {
                view.showError("Not saving because model is empty (prevents wiping your CSV files).");
                return;
            }
            model.saveAllData();
            view.showSuccess("Saved all data to CSV.");
        });

        // on close: save
        view.setOnCloseListener(() -> {
            if (model.hasAnyDataLoaded()) {
                model.saveAllData();
            }
        });

    }

    // ========== Helpers for "email text" / "document text" output ==========

    private void writeReferralEmailText(Referral r) {
        // requirement: do NOT send real email; generate content and persist in file
        Patient p = model.getPatient(r.getPatientId());
        Clinician from = model.getClinician(r.getReferringClinicianId());
        Clinician to = model.getClinician(r.getReferredToClinicianId());
        Facility fromFac = model.getFacility(r.getReferringFacilityId());
        Facility toFac = model.getFacility(r.getReferringToFacilityId());

        String filename = "referral_" + r.getReferralId() + ".txt";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        StringBuilder sb = new StringBuilder();
        sb.append("=== ELECTRONIC REFERRAL (Simulated Email) ===\n");
        sb.append("Referral ID: ").append(r.getReferralId()).append("\n");
        sb.append("Date: ").append(sdf.format(r.getReferralDate())).append("\n\n");

        sb.append("TO (Receiving Clinician/Service):\n");
        sb.append("  ").append(to != null ? to.getTitle() + " " + to.getFirstName() + " " + to.getLastName() : "Unknown").append("\n");
        sb.append("  Facility: ").append(toFac != null ? toFac.getFacilityName() : "Unknown").append("\n\n");

        sb.append("FROM (Referring Clinician):\n");
        sb.append("  ").append(from != null ? from.getTitle() + " " + from.getFirstName() + " " + from.getLastName() : "Unknown").append("\n");
        sb.append("  Facility: ").append(fromFac != null ? fromFac.getFacilityName() : "Unknown").append("\n\n");

        sb.append("PATIENT DETAILS:\n");
        if (p != null) {
            sb.append("  Name: ").append(p.getFirstName()).append(" ").append(p.getLastName()).append("\n");
            sb.append("  Patient ID: ").append(p.getPatientId()).append("\n");
            sb.append("  NHS Number: ").append(p.getNhsNumber()).append("\n");
            sb.append("  Contact: ").append(p.getContact()).append("\n");
        } else {
            sb.append("  Unknown patient\n");
        }
        sb.append("\n");

        sb.append("URGENCY: ").append(r.getUrgencyLevel()).append("\n");
        sb.append("REASON: ").append(r.getReferralReason()).append("\n\n");

        sb.append("CLINICAL SUMMARY:\n").append(r.getClinicalSummary()).append("\n\n");
        sb.append("REQUESTED INVESTIGATION:\n").append(r.getRequestedInvestigation()).append("\n\n");

        sb.append("APPOINTMENT LINK: ").append(r.getAppointmentId()).append("\n");
        sb.append("STATUS: ").append(r.getStatus()).append("\n\n");

        sb.append("NOTES:\n").append(r.getNotes()).append("\n");
        sb.append("============================================\n");

        appendToFile(filename, sb.toString());
        // Also append a short line to a global audit file:
        appendToFile("referrals_audit_log.txt",
                sdf.format(new Date()) + " - Created referral " + r.getReferralId() + " for patient " + r.getPatientId() + "\n");
    }

    private void writePrescriptionText(Prescription rx) {
        Patient p = model.getPatient(rx.getPatientId());
        Clinician c = model.getClinician(rx.getClinicianId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String filename = "prescription_" + rx.getPrescriptionId() + ".txt";
        StringBuilder sb = new StringBuilder();
        sb.append("=== PRESCRIPTION DOCUMENT (Simulated) ===\n");
        sb.append("Prescription ID: ").append(rx.getPrescriptionId()).append("\n");
        sb.append("Date: ").append(sdf.format(rx.getPrescriptionDate())).append("\n\n");

        sb.append("PATIENT:\n");
        if (p != null) {
            sb.append("  ").append(p.getFirstName()).append(" ").append(p.getLastName()).append("\n");
            sb.append("  NHS: ").append(p.getNhsNumber()).append("\n");
        } else {
            sb.append("  Unknown patient\n");
        }
        sb.append("\n");

        sb.append("PRESCRIBER:\n");
        if (c != null) {
            sb.append("  ").append(c.getTitle()).append(" ").append(c.getFirstName()).append(" ").append(c.getLastName()).append("\n");
            sb.append("  Speciality: ").append(c.getSpeciality()).append("\n");
        } else {
            sb.append("  Unknown clinician\n");
        }
        sb.append("\n");

        sb.append("MEDICATION:\n");
        sb.append("  Name: ").append(rx.getMedicationName()).append("\n");
        sb.append("  Dosage: ").append(rx.getDosage()).append("\n");
        sb.append("  Frequency: ").append(rx.getFrequency()).append("\n");
        sb.append("  Duration (days): ").append(rx.getDurationDays()).append("\n");
        sb.append("  Quantity: ").append(rx.getQuantity()).append("\n");
        sb.append("  Instructions: ").append(rx.getInstruction()).append("\n\n");

        sb.append("PHARMACY:\n");
        sb.append("  ").append(rx.getPharmacyName()).append("\n");
        sb.append("Status: ").append(rx.getStatus()).append("\n");
        sb.append("========================================\n");

        appendToFile(filename, sb.toString());
        appendToFile("prescriptions_audit_log.txt",
                sdf.format(new Date()) + " - Created prescription " + rx.getPrescriptionId() + " for patient " + rx.getPatientId() + "\n");
    }

    private void appendToFile(String filename, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(content);
            if (!content.endsWith("\n")) bw.newLine();
        } catch (IOException e) {
            view.showError("Failed writing file: " + filename + " (" + e.getMessage() + ")");
        }
    }

    // ===== Convenience ID generators to help the view dialogs =====
    public String nextPatientId() { return model.generatePatientId(); }
    public String nextClinicianId() { return model.generateClinicianId(); }
    public String nextAppointmentId() { return model.generateAppointmentId(); }
    public String nextPrescriptionId() { return model.generatePrescriptionId(); }
    public String nextReferralId() { return model.generateReferralId(); }

    public ArrayList<Facility> getAllFacilities() { return model.getAllFacilities(); }
    public ArrayList<Patient> getAllPatients() { return model.getAllPatients(); }
    public ArrayList<Clinician> getAllClinicians() { return model.getAllClinicians(); }
}
