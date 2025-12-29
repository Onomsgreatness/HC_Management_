package controller;

import model.*;
import java.util.ArrayList;

public class HCController {

    private HCModel model;

    public HCController() {
        this.model = new HCModel();
    }

    // ================= Patient =================

    public void addPatient(Patient patient) {
        model.addPatient(patient);
    }

    public Patient getPatient(String patientId) {
        return model.getPatient(patientId);
    }

    public ArrayList<Patient> getAllPatients() {
        return model.getAllPatients();
    }

    // ================= Clinician =================

    public void addClinician(Clinician clinician) {
        model.addClinician(clinician);
    }

    public Clinician getClinician(String clinicianId) {
        return model.getClinician(clinicianId);
    }

    public ArrayList<Clinician> getAllClinicians() {
        return model.getAllClinicians();
    }

    // ================= Facility =================

    public Facility getFacility(String facilityId) {
        return model.getFacility(facilityId);
    }

    public ArrayList<Facility> getAllFacilities() {
        return model.getAllFacilities();
    }

    // ================= Appointment =================
    public void addAppointment(Appointment appointment) {
        model.addAppointment(appointment);
    }

    public Appointment getAppointment(String appointmentId) {
        return model.getAppointment(appointmentId);
    }

    public ArrayList<Appointment> getAllAppointments() {
        return model.getAllAppointments();
    }

    // ================= Prescription =================
    public void addPrescription(Prescription prescription) {
        model.addPrescription(prescription, false);
    }

    public Prescription getPrescription(String prescriptionId) {
        return model.getPrescription(prescriptionId);
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return model.getAllPrescriptions();
    }

    // ================= Referral =================

    /**
     * Creates a referral after validating referenced entities
     */
    public boolean createReferral(Referral referral)
    {
        return model.createReferral(referral);
    }

    public void updateReferralStatus(String referralId, ReferralStatus status) {
        model.updateReferralStatus(referralId, status);
    }

    public ArrayList<Referral> getAllReferrals() {
        return model.getAllReferrals();
    }

    public ArrayList<Referral> getPendingReferrals() {
        return model.getPendingReferrals();
    }

    public String generateReferralId() {
        return model.generateReferralId();
    }

    // ================= Shutdown =================
    public void shutdown() {
        model.saveAllData();
    }
}
