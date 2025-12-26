import CSV.CSVHandler;
import model.Patient;
import java.util.ArrayList;
import java.util.HashMap;

public class HealthCareModel {
    private HashMap<String, Patient> patients;

    private static final String PATIENTS_FILE =  "patients.csv";

    public HealthCareModel() {
        patients = new HashMap<String, Patient>();

        loadAllData();
    }

    public void loadAllData(){
        loadPatients();

    }

    public void saveAllData(){
        savePatients();
    }

    // ========== Patient Management ==========

    private void loadPatients() {
        ArrayList<String> lines = CSVHandler.readLines(PATIENTS_FILE);
        for (int i = 0; i < lines.size(); i++) {
            Patient patient  = Patient.fromCSV(lines.get(i));
            patients.put(patient.getPatientId(), patient);
        }
    }

    private void savePatients(){
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

    public ArrayList<Patient> getAllPatients() {
        return new ArrayList<Patient>(patients.values());
    }

    public Patient getPatient(String patientId){
        return patients.get(patientId);
    }

    public String generatePatientId(){
        return "P" + (patients.size() + 1);
    }
    
}
