package model;

import java.time.LocalDate;

public class Appointment {
    private String appointmentId;
    private Patient patientId;
    private Clinician clinicianID;
    private Facility facilityId;
    private LocalDate appointmentDate;
    private String appointmentTime;
    private int durationMinutes;
    private String appointmentType;
    private StatusType status;
    private String reason_For_Visit;
    private String notes;
    private LocalDate createdDate;
    private LocalDate lastModified;

    public Appointment(String appointmentId, Patient patientId, Clinician clinicianID, Facility facilityId,
                       LocalDate appointmentDate, String appointmentTime, int durationMinutes, String appointmentType, StatusType status,
                       String reason_For_Visit, String notes, LocalDate createdDate, LocalDate lastModified){
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianID = clinicianID;
        this.facilityId = facilityId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.durationMinutes = durationMinutes;
        this.appointmentType = appointmentType;
        this.status = status;
        this.reason_For_Visit = reason_For_Visit;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastModified = lastModified;

    };

    public String getAppointmentId() {
      return appointmentId;
    }

    public void setAppointmentId(String id) {
        this.appointmentId = id;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public Clinician getClinicianID() {
        return clinicianID;
    }

    public void setClinicianID(Clinician clinicianID) {
        this.clinicianID = clinicianID;
    }

    public Facility getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Facility facilityId) {
        this.facilityId = facilityId;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getReason_For_Visit() {
        return reason_For_Visit;
    }

    public void setReason_For_Visit(String reason_For_Visit) {
        this.reason_For_Visit = reason_For_Visit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDate getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDate lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public String toString() {
        return "**** Appointment as follows: ****\n\n"
                + "Appointment Id: " + appointmentId + "\n"
                + "Patient Id: " + patientId + "\n"
                + "Clinician Id: " + clinicianID + "\n"
                + "Facility Id: " + facilityId;
    }
}
