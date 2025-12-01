package model;


import java.time.LocalDate;

public class Prescription {
    private String prescriptionId;
    private Patient patientId;
    private Clinician clinicianId;
    private Appointment appointmentId;
    private LocalDate prescriptionDate;
    private String medicationName;
    private String dosage;
    private String frequency;
    private int durationDays;
    private String quantity;
    private String instruction;
    private String pharmacyName;
    private StatusType status;
    private LocalDate issueDate;
    private LocalDate collectionDate;

    public Prescription(String prescriptionId, Patient patientId, Clinician clinicianId, Appointment appointmentId,
                        LocalDate prescriptionDate, String medicationName, String dosage, String frequency,
                        int durationDays, String quantity, String instruction, String pharmacyName, StatusType status,
                        LocalDate issueDate, LocalDate collectionDate) {
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

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Clinician getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(Clinician clinicianId) {
        this.clinicianId = clinicianId;
    }

    public Appointment getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Appointment appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LocalDate getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(LocalDate prescriptionDate) {
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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(LocalDate collectionDate) {
        this.collectionDate = collectionDate;
    }

    @Override
    public String toString() {
        return "Prescription{" +
                "prescriptionId='" + prescriptionId + "\n" +
                ", patientId=" + patientId +
                ", clinicianId=" + clinicianId +
                ", appointmentId=" + appointmentId +
                ", prescriptionDate=" + prescriptionDate +
                ", medicationName='" + medicationName + "\n"+
                ", dosage='" + dosage + "\n" +
                ", frequency='" + frequency + "\n" +
                ", durationDays=" + durationDays +
                ", quantity='" + quantity + "\n" +
                ", instruction='" + instruction + "\n" +
                ", pharmacyName='" + pharmacyName + "\n" +
                ", status=" + status + "\n" +
                ", issueDate=" + issueDate +
                ", collectionDate=" + collectionDate +
                '}';
    }
}
