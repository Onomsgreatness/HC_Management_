package controller;

import model.ReferralStatus;

import java.util.Date;

public interface EditReferralListener {
    void onEditReferral (
            String referralId, String patientId, String referringClinicianId,
            String referredToClinicianId, String referringFacilityId, String referringToFacilityId,
            Date referralDate, String urgencyLevel, String referralReason, String clinicalSummary,
            String requestedInvestigation, String appointmentId, String notes
    );
}
