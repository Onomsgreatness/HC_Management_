package controller;

import model.Referral;
import model.ReferralStatus;

import java.util.ArrayList;
import java.util.Date;

public class ReferralManager {
    private static ReferralManager instance;
    private ArrayList<Referral> referrals;
    private int nextReferralId;

    private ReferralManager() {
        referrals = new ArrayList<>();
        nextReferralId = 1;
    }

    //Singleton access function
    public static ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    public Referral createReferral(
            String patientId, String referringClinicianId, String referredToClinicianId, String referringFacilityId,
            String referringToFacilityId, String urgencyLevel, String referralReason,
            String clinicalSummary, String requestedInvestigation, String appointmentId, String notes) {

        String referralId = generateNextReferralId();
        Date updateDate = new Date();

        Referral referral = new Referral(
                referralId, patientId, referringClinicianId,
                referredToClinicianId, referringFacilityId, referringToFacilityId, updateDate,
                urgencyLevel, referralReason, clinicalSummary,
                requestedInvestigation, ReferralStatus.NEW, appointmentId,
                notes, updateDate, updateDate
        );

        referrals.add(referral);
        return referral;
    }

    public void addReferral(Referral referral) {
        if (referral == null) return;
        referrals.add(referral);

        // Example IDs: R001, R12, R0007 etc.
        String id = referral.getReferralId();
        if (id != null && id.startsWith("R")) {
            String numPart = id.substring(1).trim();

            boolean allDigits = true;
            for (int i = 0; i < numPart.length(); i++) {
                if (!Character.isDigit(numPart.charAt(i))) {
                    allDigits = false;
                    break;
                }
            }

            if (allDigits && !numPart.isEmpty()) {
                int value = Integer.parseInt(numPart);
                if (value >= nextReferralId) {
                    nextReferralId = value + 1;
                }
            }
        }
    }

    public ArrayList<Referral> getReferralsByStatus(ReferralStatus status) {
        ArrayList<Referral> result = new ArrayList<>();
        for (Referral r : referrals) {
            if (r.getStatus() == status) {
                result.add(r);
            }
        }
        return result;
    }

    public void updateReferralStatus(String referralId, ReferralStatus newStatus) {
        for (Referral r : referrals) {
            if (r.getReferralId().equals(referralId)) {
                r.setStatus(newStatus);
                r.setLastUpdated(new Date());
                break;
            }
        }
    }

    public ArrayList<Referral> getReferralsByPatient(String patientId) {
        ArrayList<Referral> results = new ArrayList<>();
        for (int r = 0; r < referrals.size(); r++) {
            if (referrals.get(r).getPatientId().equals(patientId)) {
                results.add(referrals.get(r));
            }
        }
        return results;
    }

    public Referral getReferralById(String referralId) {
        for (Referral r : referrals) {
            if (r.getReferralId().equals(referralId)) {
                return r;
            }
        }
        return null;
    }

    public void clearAllReferrals() {
        referrals.clear();
        nextReferralId = 1;
    }

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<Referral>(referrals);
    }

    public ArrayList<Referral> getPendingReferrals() {
        ArrayList<Referral> pending = new ArrayList<>();
        for (int i = 0; i < referrals.size(); i++) {
            if (referrals.get(i).getStatus() == ReferralStatus.PENDING) {
                pending.add(referrals.get(i));
            }
        }
        return pending;
    }

    public String generateNextReferralId() {
        String id = "R" + String.format("%03d", nextReferralId);
        nextReferralId++;
        return id;
    }

//Contents of output text file (referrals and prescription content ) [10 points]??
}
