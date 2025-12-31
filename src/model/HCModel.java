/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HCModel {

    // ======= Storage, Maps keyed by ID =======
    private final HashMap<String, Patient> patients = new HashMap<>();
    private final HashMap<String, Clinician> clinicians = new HashMap<>();
    private final HashMap<String, Facility> facilities = new HashMap<>();
    private final HashMap<String, Appointment> appointments = new HashMap<>();
    private final HashMap<String, Prescription> prescriptions = new HashMap<>();
    private final HashMap<String, Staff> staff = new HashMap<>();
    private final ReferralManager referralManager;

    // ======= File names =======
    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String STAFF_FILE = "staff.csv";

    public HCModel() {
        referralManager = ReferralManager.getInstance();
        loadAllData();
    }

    public void loadAllData() {
        loadPatients();
        loadClinicians();
        loadFacilities();
        loadAppointments();
        loadPrescriptions();
        loadStaff();
        referralManager.loadReferrals();
    }

    public void saveAllData() {
        savePatients();
        saveClinicians();
        saveFacilities();
        saveAppointments();
        savePrescriptions();
        saveStaff();
        referralManager.saveReferrals();
    }

    // =========================
    // Patients
    // =========================
    private void loadPatients() {
        patients.clear();
        ArrayList<String> lines = CSVHandler.readLines(PATIENTS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (i == 0 && looksLikeHeader(line, "patient_id")) continue;

            Patient p = Patient.fromCSV(line);
            if (p != null) {
                patients.put(p.getPatientId(), p);
            }
        }
    }

    private void savePatients() {
        List<String> lines = new ArrayList<>();
        for (Patient p : patients.values()) {
            lines.add(p.toCSV());
        }
        CSVHandler.writeLines(PATIENTS_FILE, lines);
    }

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<>(patients.values());
    }

    public Patient getPatient(String patientId) {
        return patients.get(patientId);
    }

    public void addPatient(Patient p) {
        if (p == null || p.getPatientId() == null) return;
        patients.put(p.getPatientId(), p);
        savePatients();
    }

    public void updatePatient(Patient p) {
        addPatient(p); // same as add (overwrite)
    }

    public void deletePatient(String patientId) {
        if (patientId == null) return;
        patients.remove(patientId);
        savePatients();
    }

    public String generatePatientId() {
        return "P" + String.format("%03d", patients.size() + 1);
    }

    // =========================
    // Clinicians
    // =========================
    private void loadClinicians() {
        clinicians.clear();
        ArrayList<String> lines = CSVHandler.readLines(CLINICIANS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (i == 0 && looksLikeHeader(line, "clinician_id")) continue;

            Clinician c = Clinician.fromCSV(line);
            if (c != null) {
                clinicians.put(c.getClinicianId(), c);
            }
        }
    }

    private void saveClinicians() {
        List<String> lines = new ArrayList<>();
        for (Clinician c : clinicians.values()) {
            lines.add(c.toCSV());
        }
        CSVHandler.writeLines(CLINICIANS_FILE, lines);
    }

    public ArrayList<Clinician> getAllClinicians() {
        return new ArrayList<>(clinicians.values());
    }

    public Clinician getClinician(String clinicianId) {
        return clinicians.get(clinicianId);
    }

    public void addClinician(Clinician c) {
        if (c == null || c.getClinicianId() == null) return;
        clinicians.put(c.getClinicianId(), c);
        saveClinicians();
    }

    public void updateClinician(Clinician c) {
        addClinician(c);
    }

    public void deleteClinician(String clinicianId) {
        if (clinicianId == null) return;
        clinicians.remove(clinicianId);
        saveClinicians();
    }

    public String generateClinicianId() {
        return "C" + String.format("%03d", clinicians.size() + 1);
    }

    // =========================
    // Facilities
    // =========================
    private void loadFacilities() {
        facilities.clear();
        ArrayList<String> lines = CSVHandler.readLines(FACILITIES_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (i == 0 && looksLikeHeader(line, "facility_id")) continue;

            Facility f = Facility.fromCSV(line);
            if (f != null) {
                facilities.put(f.getFacilityId(), f);
            }
        }
    }

    private void saveFacilities() {
        List<String> lines = new ArrayList<>();
        for (Facility f : facilities.values()) {
            lines.add(f.toCSV());
        }
        CSVHandler.writeLines(FACILITIES_FILE, lines);
    }

    public ArrayList<Facility> getAllFacilities() {
        return new ArrayList<>(facilities.values());
    }

    public Facility getFacility(String facilityId) {
        return facilities.get(facilityId);
    }

    public void addFacility(Facility f) {
        if (f == null || f.getFacilityId() == null) return;
        facilities.put(f.getFacilityId(), f);
        saveFacilities();
    }

    public void updateFacility(Facility f) {
        addFacility(f);
    }

    public void deleteFacility(String facilityId) {
        if (facilityId == null) return;
        facilities.remove(facilityId);
        saveFacilities();
    }

    public String generateFacilityId() {
        return "F" + String.format("%03d", facilities.size() + 1);
    }

    // =========================
    // Appointments
    // =========================
    private void loadAppointments() {
        appointments.clear();
        ArrayList<String> lines = CSVHandler.readLines(APPOINTMENTS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (i == 0 && looksLikeHeader(line, "appointment_id")) continue;

            Appointment a = Appointment.fromCSV(line);
            if (a != null) {
                appointments.put(a.getAppointmentId(), a);
            }
        }
    }

    private void saveAppointments() {
        List<String> lines = new ArrayList<>();
        for (Appointment a : appointments.values()) {
            lines.add(a.toCSV());
        }
        CSVHandler.writeLines(APPOINTMENTS_FILE, lines);
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments.values());
    }

    public Appointment getAppointment(String appointmentId) {
        return appointments.get(appointmentId);
    }

    public void addAppointment(Appointment a) {
        if (a == null || a.getAppointmentId() == null) return;
        appointments.put(a.getAppointmentId(), a);
        saveAppointments();
    }

    public void updateAppointment(Appointment a) {
        addAppointment(a);
    }

    public void deleteAppointment(String appointmentId) {
        if (appointmentId == null) return;
        appointments.remove(appointmentId);
        saveAppointments();
    }

    public String generateAppointmentId() {
        return "A" + String.format("%03d", appointments.size() + 1);
    }

    // =========================
    // Prescriptions
    // =========================
    private void loadPrescriptions() {
        prescriptions.clear();
        ArrayList<String> lines = CSVHandler.readLines(PRESCRIPTIONS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (i == 0 && looksLikeHeader(line, "prescription_id")) continue;

            Prescription p = Prescription.fromCSV(line);
            if (p != null) {
                prescriptions.put(p.getPrescriptionId(), p);
            }
        }
    }

    private void savePrescriptions() {
        List<String> lines = new ArrayList<>();
        for (Prescription p : prescriptions.values()) {
            lines.add(p.toCSV());
        }
        CSVHandler.writeLines(PRESCRIPTIONS_FILE, lines);
    }

    public ArrayList<Prescription> getAllPrescriptions() {
        return new ArrayList<>(prescriptions.values());
    }

    public Prescription getPrescription(String prescriptionId) {
        return prescriptions.get(prescriptionId);
    }

    public void addPrescription(Prescription p) {
        if (p == null || p.getPrescriptionId() == null) return;
        prescriptions.put(p.getPrescriptionId(), p);
        savePrescriptions();
    }

    public void updatePrescription(Prescription p) {
        addPrescription(p);
    }

    public void deletePrescription(String prescriptionId) {
        if (prescriptionId == null) return;
        prescriptions.remove(prescriptionId);
        savePrescriptions();
    }

    public String generatePrescriptionId() {
        return "RX" + String.format("%03d", prescriptions.size() + 1);
    }

    // =========================
    // Staff
    // =========================
    private void loadStaff() {
        staff.clear();
        ArrayList<String> lines = CSVHandler.readLines(STAFF_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);

            if (i == 0 && looksLikeHeader(line, "staff_id")) continue;

            Staff s = Staff.fromCSV(line);
            if (s != null) {
                staff.put(s.getStaffId(), s);
            }
        }
    }

    private void saveStaff() {
        List<String> lines = new ArrayList<>();
        for (Staff s : staff.values()) {
            lines.add(s.toCSV());
        }
        CSVHandler.writeLines(STAFF_FILE, lines);
    }

    public ArrayList<Staff> getAllStaff() {
        return new ArrayList<>(staff.values());
    }

    public Staff getStaff(String staffId) {
        return staff.get(staffId);
    }

    public void addStaff(Staff s) {
        if (s == null || s.getStaffId() == null) return;
        staff.put(s.getStaffId(), s);
        saveStaff();
    }

    public void updateStaff(Staff s) {
        addStaff(s);
    }

    public void deleteStaff(String staffId) {
        if (staffId == null) return;
        staff.remove(staffId);
        saveStaff();
    }

    public String generateStaffId() {
        return "ST" + String.format("%03d", staff.size() + 1);
    }

    // =========================
    // Referrals (Singleton)
    // =========================
    public ReferralManager getReferralManager() {
        return referralManager;
    }

    public ArrayList<Referral> getAllReferrals() {
        return referralManager.getAllReferrals();
    }


    private boolean looksLikeHeader(String line, String firstColumnName) {
        if (line == null) return false;
        String l = line.trim().toLowerCase();
        return l.startsWith(firstColumnName.toLowerCase()) || l.contains(firstColumnName.toLowerCase());
    }
}
