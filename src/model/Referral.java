/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


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
        List<String> fields = Arrays.asList( referralId ,patientId ,referringClinicianId , referredToClinicianId
                ,referringFacilityId,referringToFacilityId,formatDate(sdf, referralDate), urgencyLevel,
                referralReason ,clinicalSummary,requestedInvestigation, status == null ? "" : status.toCSV() ,
                appointmentId , notes , formatDate(sdf, createdDate), formatDate(sdf, lastUpdated)
        );
        return CSVHandler.toLine(fields);
    }


    public static Referral fromCSV(String csvLine) {
        try {
            List<String> parts = CSVHandler.parseLine(csvLine);
            if (parts.size() < 16) return null;

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

            ReferralStatus status = ReferralStatus.fromCSV(parts.get(11)); // use your robust parser

            return new Referral(
                    parts.get(0),
                    parts.get(1),
                    parts.get(2),
                    parts.get(3),
                    parts.get(4),
                    parts.get(5),
                    parseDate(sdf, parts.get(6)),
                    parts.get(7),
                    parts.get(8),
                    parts.get(9),
                    parts.get(10),
                    status,
                    parts.get(12),
                    parts.get(13),
                    parseDate(sdf, parts.get(14)),
                    parseDate(sdf, parts.get(15))
            );
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Date parseDate(SimpleDateFormat sdf, String value) throws Exception {
        if (value == null) return null;
        String v = value.trim();
        if (v.isEmpty()) return null;
        return sdf.parse(v);
    }

    private static String formatDate(SimpleDateFormat sdf, Date date) {
        return date == null ? "" : sdf.format(date);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "model.Referral{" +
                "referralId='" + referralId + "\n"+
                ", patientId=" + patientId +
                ", referringClinicianId='" + referringClinicianId + "\n"+
                ", referredToClinicianId='" + referredToClinicianId + "\n" +
                ", referringFacilityId='" + referringFacilityId + "\n" +
                ", referringToFacilityId='" + referringToFacilityId + "\n" +
                ", referralDate=" + formatDate(sdf, referralDate) +
                ", urgencyLevel='" + urgencyLevel + "\n" +
                ", referralReason='" + referralReason + "\n" +
                ", clinicalSummary='" + clinicalSummary + "\n" +
                ", status=" + status +
                ", requestedInvestigation='" + requestedInvestigation + "\n" +
                ", appointmentId=" + appointmentId +
                ", notes='" + notes + "\n" +
                ", createdDate=" + formatDate(sdf, createdDate) +
                ", lastUpdated=" + formatDate(sdf, lastUpdated) +
                '}';
    }
}