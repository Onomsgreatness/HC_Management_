package model;

import model.Appointment;
import model.Patient;
import java.time.LocalDate;


public class  Referral {
    private String referralId;
    private Patient patientId;
    private String referringClinicianId;
    private String referredToClinicianId;
    private String referringFacilityId;
    private LocalDate referralDate;
    private String urgencyLevel;
    private String referralReason;
    private String clinicalSummary;
    private ReferralStatus status;
    private String requestedInvestigation;
    private Appointment appointmentId;
    private String notes;
    private LocalDate createdDate;
    private LocalDate lastUpdated;

    public Referral(String referralId, Patient patientId, String referringClinicianId,
                    String referredToClinicianId, String referringFacilityId, LocalDate referralDate,
                    String urgencyLevel, String referralReason, String clinicalSummary,
                    ReferralStatus status, String requestedInvestigation, Appointment appointmentId,
                    String notes, LocalDate createdDate, LocalDate lastUpdated) {
        this.referralId = referralId;
        this.patientId = patientId;
        this.referringClinicianId = referringClinicianId;
        this.referredToClinicianId = referredToClinicianId;
        this.referringFacilityId = referringFacilityId;
        this.referralDate = referralDate;
        this.urgencyLevel = urgencyLevel;
        this.referralReason = referralReason;
        this.clinicalSummary = clinicalSummary;
        this.status = status;
        this.requestedInvestigation = requestedInvestigation;
        this.appointmentId = appointmentId;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastUpdated = lastUpdated;
    }

    public String getReferralId() {
        return referralId;
    }

    public void setReferralId(String referralId) {
        this.referralId = referralId;
    }

    public Patient getPatientId() {
        return patientId;
    }

    public void setPatientId(Patient patientId) {
        this.patientId = patientId;
    }

    public String getReferringClinicianId() {
        return referringClinicianId;
    }

    public void setReferringClinicianId(String referringClinicianId) {
        this.referringClinicianId = referringClinicianId;
    }

    public String getReferredToClinicianId() {
        return referredToClinicianId;
    }

    public void setReferredToClinicianId(String referredToClinicianId) {
        this.referredToClinicianId = referredToClinicianId;
    }

    public String getReferringFacilityId() {
        return referringFacilityId;
    }

    public void setReferringFacilityId(String referringFacilityId) {
        this.referringFacilityId = referringFacilityId;
    }

    public LocalDate getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(LocalDate referralDate) {
        this.referralDate = referralDate;
    }

    public String getUrgencyLevel() {
        return urgencyLevel;
    }

    public void setUrgencyLevel(String urgencyLevel) {
        this.urgencyLevel = urgencyLevel;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    public String getClinicalSummary() {
        return clinicalSummary;
    }

    public void setClinicalSummary(String clinicalSummary) {
        this.clinicalSummary = clinicalSummary;
    }

    public ReferralStatus getStatus() {
        return status;
    }

    public void setStatus(ReferralStatus status) {
        this.status = status;
    }

    public String getRequestedInvestigation() {
        return requestedInvestigation;
    }

    public void setRequestedInvestigation(String requestedInvestigation) {
        this.requestedInvestigation = requestedInvestigation;
    }

    public Appointment getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Appointment appointmentId) {
        this.appointmentId = appointmentId;
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

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    @Override
    public String toString() {
        return "Referral{" +
                "referralId='" + referralId + "\n"+
                ", patientId=" + patientId +
                ", referringClinicianId='" + referringClinicianId + "\n"+
                ", referredToClinicianId='" + referredToClinicianId + "\n" +
                ", referringFacilityId='" + referringFacilityId + "\n" +
                ", referralDate=" + referralDate +
                ", urgencyLevel='" + urgencyLevel + "\n" +
                ", referralReason='" + referralReason + "\n" +
                ", clinicalSummary='" + clinicalSummary + "\n" +
                ", status=" + status +
                ", requestedInvestigation='" + requestedInvestigation + "\n" +
                ", appointmentId=" + appointmentId +
                ", notes='" + notes + "\n" +
                ", createdDate=" + createdDate +
                ", lastUpdated=" + lastUpdated +
                '}';
    }
}