package model;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Prescription {
    private String prescriptionId;
    private String patientId;
    private String clinicianId;
    private String appointmentId;
    private Date prescriptionDate;
    private String medicationName;
    private String dosage;
    private String frequency;
    private int durationDays;
    private String quantity;
    private String instruction;
    private String pharmacyName;
    private StatusType status;
    private Date issueDate;
    private Date collectionDate;

    public Prescription(String prescriptionId, String patientId, String clinicianId, String appointmentId,
                        Date prescriptionDate, String medicationName, String dosage, String frequency,
                        int durationDays, String quantity, String instruction, String pharmacyName, StatusType status,
                        Date issueDate, Date collectionDate) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.appointmentId = appointmentId;
        this.prescriptionDate = prescriptionDate;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.quantity = quantity;
        this.instruction = instruction;
        this.pharmacyName = pharmacyName;
        this.status = status;
        this.issueDate = issueDate;
        this.collectionDate = collectionDate;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(String clinicianId) {
        this.clinicianId = clinicianId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String toCSV(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return prescriptionId + "," + patientId + "," + clinicianId + "," + appointmentId + ","
                + sdf.format(prescriptionDate) + "," + medicationName + "," + dosage + "," + frequency
                + "," + durationDays + "," + quantity + "," + instruction + "," + pharmacyName + ","
                + status.name() + "," + sdf.format(issueDate) + "," + sdf.format(collectionDate);
    }

    public static Prescription fromCSV(String csvLine){
        try{
            String[] parts = csvLine.split(",");
            StatusType status = StatusType.valueOf(parts[12].trim());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new Prescription(parts[0], parts[1], parts[2], parts[3], sdf.parse(parts[4]), parts[5],
                    parts[6], parts[7], Integer.parseInt(parts[8]), parts[9], parts[10], parts[11], status,
                    sdf.parse(parts[13]), sdf.parse(parts[14]));
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return "Prescription{" +
                "prescriptionId='" + prescriptionId + "\n" +
                ", patientId=" + patientId +
                ", clinicianId=" + clinicianId +
                ", appointmentId=" + appointmentId +
                ", prescriptionDate=" + sdf.format(prescriptionDate) +
                ", medicationName='" + medicationName + "\n"+
                ", dosage='" + dosage + "\n" +
                ", frequency='" + frequency + "\n" +
                ", durationDays=" + durationDays +
                ", quantity='" + quantity + "\n" +
                ", instruction='" + instruction + "\n" +
                ", pharmacyName='" + pharmacyName + "\n" +
                ", status=" + status + "\n" +
                ", issueDate=" + sdf.format(issueDate) +
                ", collectionDate=" + sdf.format(collectionDate) +
                '}';
    }
}
