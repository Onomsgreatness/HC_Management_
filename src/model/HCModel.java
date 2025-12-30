package model;

import CSV.CSVHandler;

import java.util.ArrayList;
import java.util.HashMap;

public class HCModel {

    private final HashMap<String, Patient> patients = new HashMap<>();
    private final HashMap<String, Clinician> clinicians = new HashMap<>();
    private final HashMap<String, Facility> facilities = new HashMap<>();
    private final HashMap<String, Staff> staff = new HashMap<>();
    private final HashMap<String, Appointment> appointments = new HashMap<>();
    private final HashMap<String, Prescription> prescriptions = new HashMap<>();

    private final ReferralManager referralManager;

    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String STAFF_FILE = "staff.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String REFERRALS_FILE = "referrals.csv";

    // default: load files
    public HCModel() {
        this(true);
    }

    // for testing: pass false
    public HCModel(boolean loadFiles) {
        referralManager = ReferralManager.getInstance();
        if (loadFiles) {
            loadAllData();
        }
    }

    // ---------------- Load/Save All ----------------
    public void loadAllData() {
        loadFacilities();
        loadClinicians();
        loadPatients();
        loadStaff();
        loadAppointments();
        loadPrescriptions();
        loadReferrals();
    }

    public void saveAllData() {
        saveFacilities();
        saveClinicians();
        savePatients();
        saveStaff();
        saveAppointments();
        savePrescriptions();
        saveReferrals();
    }

    // ---------------- Patients ----------------
    private void loadPatients() {
        ArrayList<String> lines = CSVHandler.readLines(PATIENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Patient p = Patient.fromCSV(lines.get(i));
            if (p != null) patients.put(p.getPatientId(), p);
        }
    }

    private void savePatients() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Patient> list = new ArrayList<>(patients.values());
        for (int i = 0; i < list.size(); i++) lines.add(list.get(i).toCSV());
        CSVHandler.writeLines(PATIENTS_FILE, lines);
    }

    public void addPatient(Patient p) {
        if (p == null) return;
        patients.put(p.getPatientId(), p);
        savePatients();
    }

    public void updatePatient(String patientId, Patient updated) {
        if (patientId == null || updated == null) return;
        patients.put(patientId, updated);
        savePatients();
    }

    public void deletePatient(String patientId) {
        patients.remove(patientId);
        savePatients();
    }

    public Patient getPatient(String patientId) {
        return patients.get(patientId);
    }

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    public String generatePatientId() {
        return "P" + String.format("%04d", patients.size() + 1);
    }

    // ---------------- Clinicians ----------------
    private void loadClinicians() {
        ArrayList<String> lines = CSVHandler.readLines(CLINICIANS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Clinician c = Clinician.fromCSV(lines.get(i));
            if (c != null) clinicians.put(c.getClinicianId(), c);
        }
    }

    private void saveClinicians() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Clinician> list = new ArrayList<>(clinicians.values());
        for (int i = 0; i < list.size(); i++) lines.add(list.get(i).toCSV());
        CSVHandler.writeLines(CLINICIANS_FILE, lines);
    }

    public void addClinician(Clinician c) {
        if (c == null) return;
        clinicians.put(c.getClinicianId(), c);
        saveClinicians();
    }

    public void updateClinician(String clinicianId, Clinician updated) {
        if (clinicianId == null || updated == null) return;
        clinicians.put(clinicianId, updated);
        saveClinicians();
    }

    public void deleteClinician(String clinicianId) {
        clinicians.remove(clinicianId);
        saveClinicians();
    }

    public Clinician getClinician(String clinicianId) {
        return clinicians.get(clinicianId);
    }

    public ArrayList<Clinician> getAllClinicians() {
        return new ArrayList<>(clinicians.values());
    }

    public String generateClinicianId() {
        return "C" + String.format("%04d", clinicians.size() + 1);
    }

    // ---------------- Facilities ----------------
    private void loadFacilities() {
        ArrayList<String> lines = CSVHandler.readLines(FACILITIES_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Facility f = Facility.fromCSV(lines.get(i));
            if (f != null) facilities.put(f.getFacilityId(), f);
        }
    }

    private void saveFacilities() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Facility> list = new ArrayList<>(facilities.values());
        for (int i = 0; i < list.size(); i++) lines.add(list.get(i).toCSV());
        CSVHandler.writeLines(FACILITIES_FILE, lines);
    }

    public void addFacility(Facility f) {
        if (f == null) return;
        facilities.put(f.getFacilityId(), f);
        saveFacilities();
    }

    public Facility getFacility(String facilityId) {
        return facilities.get(facilityId);
    }

    public ArrayList<Facility> getAllFacilities() {
        return new ArrayList<>(facilities.values());
    }

    // ---------------- Staff ----------------
    private void loadStaff() {
        ArrayList<String> lines = CSVHandler.readLines(STAFF_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Staff s = Staff.fromCSV(lines.get(i));
            if (s != null) staff.put(s.getStaffId(), s);
        }
    }

    private void saveStaff() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Staff> list = new ArrayList<>(staff.values());
        for (int i = 0; i < list.size(); i++) lines.add(list.get(i).toCSV());
        CSVHandler.writeLines(STAFF_FILE, lines);
    }

    public Staff getStaff(String staffId) {
        return staff.get(staffId);
    }

    public ArrayList<Staff> getAllStaff() {
        return new ArrayList<>(staff.values());
    }

    // ---------------- Appointments ----------------
    private void loadAppointments() {
        ArrayList<String> lines = CSVHandler.readLines(APPOINTMENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Appointment a = Appointment.fromCSV(lines.get(i));
            if (a != null) appointments.put(a.getAppointmentId(), a);
        }
    }

    private void saveAppointments() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Appointment> list = new ArrayList<>(appointments.values());
        for (int i = 0; i < list.size(); i++) lines.add(list.get(i).toCSV());
        CSVHandler.writeLines(APPOINTMENTS_FILE, lines);
    }

    public void addAppointment(Appointment a) {
        if (a == null) return;
        appointments.put(a.getAppointmentId(), a);
        saveAppointments();
    }

    public void updateAppointment(String appointmentId, Appointment updated) {
        if (appointmentId == null || updated == null) return;
        appointments.put(appointmentId, updated);
        saveAppointments();
    }

    public void deleteAppointment(String appointmentId) {
        appointments.remove(appointmentId);
        saveAppointments();
    }

    public Appointment getAppointment(String appointmentId) {
        return appointments.get(appointmentId);
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    public String generateAppointmentId() {
        return "A" + String.format("%04d", appointments.size() + 1);
    }

    // ---------------- Prescriptions ----------------
    private void loadPrescriptions() {
        ArrayList<String> lines = CSVHandler.readLines(PRESCRIPTIONS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Prescription rx = Prescription.fromCSV(lines.get(i));
            if (rx != null) prescriptions.put(rx.getPrescriptionId(), rx);
        }
    }

    private void savePrescriptions() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Prescription> list = new ArrayList<>(prescriptions.values());
        for (int i = 0; i < list.size(); i++) lines.add(list.get(i).toCSV());
        CSVHandler.writeLines(PRESCRIPTIONS_FILE, lines);
    }

    public void addPrescription(Prescription rx, boolean generateDocument) {
        if (rx == null) return;
        prescriptions.put(rx.getPrescriptionId(), rx);
        savePrescriptions();
    }

    public void updatePrescription(String prescriptionId, Prescription updated) {
        if (prescriptionId == null || updated == null) return;
        prescriptions.put(prescriptionId, updated);
        savePrescriptions();
    }

    public void deletePrescription(String prescriptionId) {
        prescriptions.remove(prescriptionId);
        savePrescriptions();
    }

    public Prescription getPrescription(String prescriptionId) {
        return prescriptions.get(prescriptionId);
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions.values());
    }

    public String generatePrescriptionId() {
        return "RX" + String.format("%04d", prescriptions.size() + 1);
    }

    // ---------------- Referrals ----------------
    private void loadReferrals() {
        ArrayList<String> lines = CSVHandler.readLines(REFERRALS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Referral r = Referral.fromCSV(lines.get(i));
            if (r != null) referralManager.addReferral(r);
        }
    }

    private void saveReferrals() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Referral> list = referralManager.getAllReferrals();
        for (int i = 0; i < list.size(); i++) lines.add(list.get(i).toCSV());
        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    public boolean createReferral(Referral referral) {
        if (referral == null) return false;

        // validate linked entities exist
        if (getPatient(referral.getPatientId()) == null) return false;
        if (getClinician(referral.getReferringClinicianId()) == null) return false;
        if (getClinician(referral.getReferredToClinicianId()) == null) return false;
        if (getFacility(referral.getReferringFacilityId()) == null) return false;
        if (getFacility(referral.getReferringToFacilityId()) == null) return false;

        referralManager.addReferral(referral);
        saveReferrals();
        return true;
    }

    public void updateReferralStatus(String referralId, ReferralStatus newStatus) {
        referralManager.updateReferralStatus(referralId, newStatus);
        saveReferrals();
    }

    public ArrayList<Referral> getAllReferrals() {
        return referralManager.getAllReferrals();
    }

    public ArrayList<Referral> getPendingReferrals() {
        return referralManager.getPendingReferrals();
    }

    public String generateReferralId() {
        return referralManager.generateNextReferralId();
    }

    public ReferralManager getReferralManager() {
        return referralManager;
    }

    // ---------------- Safety ----------------
    public boolean hasAnyDataLoaded() {
        return !patients.isEmpty()
                || !clinicians.isEmpty()
                || !facilities.isEmpty()
                || !staff.isEmpty()
                || !appointments.isEmpty()
                || !prescriptions.isEmpty()
                || !referralManager.getAllReferrals().isEmpty();
    }
}
