package model;

import java.text.SimpleDateFormat;
import java.util.Date;


public class  Referral {
    private String referralId;
    private String patientId;
    private String referringClinicianId;
    private String referredToClinicianId;
    private String referringFacilityId;
    private String referringToFacilityId;
    private Date referralDate;
    private String urgencyLevel;
    private String referralReason;
    private String clinicalSummary;
    private String requestedInvestigation;
    private ReferralStatus status;
    private String appointmentId;
    private String notes;
    private Date createdDate;
    private Date lastUpdated;

    public Referral(String referralId, String patientId, String referringClinicianId,
                    String referredToClinicianId, String referringFacilityId, String referringToFacilityId, Date referralDate,
                    String urgencyLevel, String referralReason, String clinicalSummary,
                    String requestedInvestigation, ReferralStatus status, String appointmentId,
                    String notes, Date createdDate, Date lastUpdated) {
        this.referralId = referralId;
        this.patientId = patientId;
        this.referringClinicianId = referringClinicianId;
        this.referredToClinicianId = referredToClinicianId;
        this.referringFacilityId = referringFacilityId;
        this.referringToFacilityId = referringToFacilityId;
        this.referralDate = referralDate;
        this.urgencyLevel = urgencyLevel;
        this.referralReason = referralReason;
        this.clinicalSummary = clinicalSummary;
        this.requestedInvestigation = requestedInvestigation;
        this.status = status;
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
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

    public String getReferringToFacilityId() {
        return referringToFacilityId;
    }

    public void setReferringToFacilityId(String referringToFacilityId) {
        this.referringToFacilityId = referringToFacilityId;
    }

    public Date getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(Date referralDate) {
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

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return referralId + "," + patientId + "," + referringClinicianId + "," + referredToClinicianId
                + "," + referringFacilityId + "," + referringToFacilityId + "," + sdf.format(referralDate) + "," + urgencyLevel + ","
                + referralReason + "," + clinicalSummary + "," + requestedInvestigation
                + "," + status + "," + appointmentId + "," + notes + "," + sdf.format(createdDate)
                + "," + sdf.format(lastUpdated);
    }

    public static Referral fromCSV(String csvLine) {
        try{
            String[] parts = csvLine.split(",");
            ReferralStatus status = ReferralStatus.valueOf(parts[11]);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            return new Referral(
                    parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], sdf.parse(parts[6]), parts[7],
                    parts[8], parts[9], parts[10], status, parts[12], parts[13], sdf.parse(parts[14]), sdf.parse(parts[15])
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "Referral{" +
                "referralId='" + referralId + "\n"+
                ", patientId=" + patientId +
                ", referringClinicianId='" + referringClinicianId + "\n"+
                ", referredToClinicianId='" + referredToClinicianId + "\n" +
                ", referringFacilityId='" + referringFacilityId + "\n" +
                ", referringToFacilityId='" + referringToFacilityId + "\n" +
                ", referralDate=" + sdf.format(referralDate) +
                ", urgencyLevel='" + urgencyLevel + "\n" +
                ", referralReason='" + referralReason + "\n" +
                ", clinicalSummary='" + clinicalSummary + "\n" +
                ", status=" + status +
                ", requestedInvestigation='" + requestedInvestigation + "\n" +
                ", appointmentId=" + appointmentId +
                ", notes='" + notes + "\n" +
                ", createdDate=" + sdf.format(createdDate) +
                ", lastUpdated=" + sdf.format(lastUpdated) +
                '}';
    }
}