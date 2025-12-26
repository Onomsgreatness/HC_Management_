import controller.ReferralManager;
import model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/*References: Dr John Kanyaru*/

public class HCModel {
    private  HashMap<String, Patient> patients;
    private  HashMap<String, Clinician> clinicians;
    private  HashMap<String, Facility> facilities;
    private  HashMap<String, Staff> staff;
    private  HashMap<String, Appointment> appointments;
    private  HashMap<String, Prescription> prescriptions;
    private ReferralManager referralManager;

    private static final String PATIENTS_FILE = "patients.csv";
    private static final String CLINICIANS_FILE = "clinicians.csv";
    private static final String FACILITIES_FILE = "facilities.csv";
    private static final String STAFF_FILE = "staff.csv";
    private static final String APPOINTMENTS_FILE = "appointments.csv";
    private static final String PRESCRIPTIONS_FILE = "prescriptions.csv";
    private static final String REFERRALS_FILE = "referrals.csv";

    public HCModel() {
        patients = new HashMap<>();
        clinicians = new HashMap<>();
        facilities = new HashMap<>();
        staff = new HashMap<>();
        appointments = new HashMap<>();
        prescriptions = new HashMap<>();
        referralManager = ReferralManager.getInstance();

        loadAllData();
    }

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

    // ================Patient Management ==================
    private void loadPatients() {
        ArrayList<String> lines = CSVHandler.readLines(PATIENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Patient patient = Patient.fromCSV(lines.get(i));
            if (patient != null) {
                patients.put(patient.getPatientId(), patient);
            }
        }
    }

    private void savePatients() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Patient> patientList = new ArrayList<>(patients.values());
        for (int i = 0; i < patientList.size(); i++) {
            lines.add(patientList.get(i).toCSV());
        }
        CSVHandler.writeLines(PATIENTS_FILE, lines);
    }

    public void addPatient(Patient patient) {
        patients.put(patient.getPatientId(), patient);
        savePatients();
    }

    public void updatePatient(String patientId, Patient updatePatient) {
        patients.put(patientId, updatePatient);
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


    //=================== Clinician Management =====================
    private void loadClinicians() {
        ArrayList<String> lines = CSVHandler.readLines(CLINICIANS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Clinician clinician = Clinician.fromCSV(lines.get(i));
            if (clinician != null) {
                clinicians.put(clinician.getClinicianId(), clinician);
            }
        }
    }

    private void saveClinicians() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Clinician> clinicianList = new ArrayList<>(clinicians.values());
        for (int i = 0; i < clinicianList.size(); i++) {
            lines.add(clinicianList.get(i).toCSV());
        }
        CSVHandler.writeLines(CLINICIANS_FILE, lines);
    }

    public void addClinician(Clinician clinician) {
        clinicians.put(clinician.getClinicianId(), clinician);
        saveClinicians();
    }

    public void updateClinician(String clinicianId, Clinician updatedClinician) {
        clinicians.put(clinicianId, updatedClinician);
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

    // =============== Facility Management ==============

    private void loadFacilities() {
        ArrayList<String> lines = CSVHandler.readLines(FACILITIES_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Facility facility = Facility.fromCSV(lines.get(i));
            if (facility != null) {
                facilities.put(facility.getFacilityId(), facility);
            }
        }
    }

    private void saveFacilities() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Facility> facilityList = new ArrayList<>(facilities.values());
        for (int i = 0; i < facilityList.size(); i++) {
            lines.add(facilityList.get(i).toCSV());
        }
        CSVHandler.writeLines(FACILITIES_FILE, lines);
    }

    public void addFacility(Facility facility) {
        facilities.put(facility.getFacilityId(), facility);
        saveFacilities();
    }

    public Facility getFacility(String facilityId) {
        return facilities.get(facilityId);
    }

    public ArrayList<Facility> getAllFacilities() {
        return new ArrayList<>(facilities.values());
    }

    // =============== Staff Management ==============
    private void loadStaff() {
        ArrayList<String> lines = CSVHandler.readLines(STAFF_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Staff staffMember = Staff.fromCSV(lines.get(i));
            if (staffMember != null) {
                staff.put(staffMember.getStaffId(), staffMember);
            }
        }
    }

    private void saveStaff() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Staff> staffList = new ArrayList<>(staff.values());
        for (int i = 0; i < staffList.size(); i++) {
            lines.add(staffList.get(i).toCSV());
        }
        CSVHandler.writeLines(STAFF_FILE, lines);
    }

    public Staff getStaff(String staffId) {
        return staff.get(staffId);
    }

    public ArrayList<Staff> getAllStaff() {
        return new ArrayList<>(staff.values());
    }

    // =============== Appointment Management ==============
    private void loadAppointments() {
        ArrayList<String> lines = CSVHandler.readLines(APPOINTMENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Appointment appointment = Appointment.fromCSV(lines.get(i));
            if (appointment != null) {
                appointments.put(appointment.getAppointmentId(), appointment);
            }
        }
    }

    private void saveAppointments() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Appointment> appointmentList = new ArrayList<>(appointments.values());
        for (int i = 0; i < appointmentList.size(); i++) {
            lines.add(appointmentList.get(i).toCSV());
        }
        CSVHandler.writeLines(APPOINTMENTS_FILE, lines);
    }

    public void addAppointment(Appointment appointment) {
        appointments.put(appointment.getAppointmentId(), appointment);
        saveAppointments();
    }

    public void updateAppointment(String appointmentId, Appointment updatedAppointment) {
        appointments.put(appointmentId, updatedAppointment);
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

    // =============== Prescription Management ==============
    private void loadPrescriptions() {
        ArrayList<String> lines = CSVHandler.readLines(PRESCRIPTIONS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Prescription prescription = Prescription.fromCSV(lines.get(i));
            if (prescription != null) {
                prescriptions.put(prescription.getPrescriptionId(), prescription);
            }
        }
    }

    private void savePrescriptions() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Prescription> prescriptionList = new ArrayList<>(prescriptions.values());
        for (int i = 0; i < prescriptionList.size(); i++) {
            lines.add(prescriptionList.get(i).toCSV());
        }
        CSVHandler.writeLines(PRESCRIPTIONS_FILE, lines);
    }

    public void addPrescription(Prescription prescription, boolean generateDocument) {
        prescriptions.put(prescription.getPrescriptionId(), prescription);
        savePrescriptions();
    }

    public void updatePrescription(String prescriptionId, Prescription updatedPrescription) {
        prescriptions.put(prescriptionId, updatedPrescription);
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

    // =============== Referral Management ==============
    private void loadReferrals() {
        ArrayList<String> lines = CSVHandler.readLines(REFERRALS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Referral referral = Referral.fromCSV(lines.get(i));
            if (referral != null) {
                referralManager.addReferral(referral);
            }
        }
    }

    private void saveReferrals() {
        ArrayList<String> lines = new ArrayList<>();
        ArrayList<Referral> referralList = referralManager.getAllReferrals();
        for (int i = 0; i < referralList.size(); i++) {
            lines.add(referralList.get(i).toCSV());
        }
        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    public boolean createReferral(Referral referral) {
        Patient patient = patients.get(referral.getPatientId());
        Clinician referringClinician = clinicians.get(referral.getReferringClinicianId());
        Clinician referredToClinician = clinicians.get(referral.getReferredToClinicianId());
        Facility referringFacility = facilities.get(referral.getReferringFacilityId());
        Facility referredToFacility = facilities.get(referral.getReferringToFacilityId());

        if (patient == null || referringClinician == null || referredToClinician == null ||
                referringFacility == null || referredToFacility == null) {
            return false;
        }

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

//    public String generateReferralId() {
//        return referralManager.generateNextReferralId();
//    }

}
