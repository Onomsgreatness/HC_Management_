import controller.ReferralManager;
import model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        savePrescription();
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
}
