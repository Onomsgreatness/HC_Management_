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

        String referralId = "R" + nextReferralId++;

        Referral referral = new Referral(
                referralId, patientId, referringClinicianId,
                referredToClinicianId, referringFacilityId, referringToFacilityId, new Date(),
                urgencyLevel, referralReason, clinicalSummary,
                requestedInvestigation, ReferralStatus.NEW, appointmentId,
                notes, new Date(), new Date()
        );

        referrals.add(referral);
        return referral;
    }

    public void addReferral(Referral referral) {
        referrals.add(referral);
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

    public void clearAlReferrals() {
        referrals.clear();
        nextReferralId = 1;
    }
}
