/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReferralManager {
    private static ReferralManager instance;

    private final ArrayList<Referral> referrals;
    private int nextReferralNumber;

    private static final String REFERRALS_FILE = "referrals.csv";
    private static final String REFERRAL_EMAIL_OUT_DIR = "output/referrals";

    private ReferralManager() {
        referrals = new ArrayList<>();
        nextReferralNumber = 1;
    }

    public static ReferralManager getInstance() {
        if (instance == null) instance = new ReferralManager();
        return instance;
    }

    public void loadReferrals() {
        referrals.clear();
        ArrayList<String> lines = CSVHandler.readLines(REFERRALS_FILE);

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (i == 0 && looksLikeHeader(line, "referral")) continue;

            Referral r = Referral.fromCSV(line);
            if (r != null) {
                referrals.add(r);
                updateNextId(r.getReferralId());
            }
        }
    }

    public void saveReferrals() {
        ArrayList<String> lines = new ArrayList<>();
        for (Referral r : referrals) lines.add(r.toCSV());
        CSVHandler.writeLines(REFERRALS_FILE, lines);
    }

    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<>(referrals);
    }

    public Referral getReferralById(String referralId) {
        if (referralId == null) return null;
        for (Referral r : referrals) {
            if (referralId.equals(r.getReferralId())) return r;
        }
        return null;
    }

    public String generateReferralId() {
        return String.format("R%04d", nextReferralNumber++);
    }

    public void addReferral(Referral referral) {
        if (referral == null) return;

        if (referral.getReferralId() == null || referral.getReferralId().trim().isEmpty()) {
            referral.setReferralId(generateReferralId());
        } else {
            updateNextId(referral.getReferralId());
        }

        referrals.add(referral);
        saveReferrals();
    }

    public void updateReferralStatus(String referralId, ReferralStatus newStatus) {
        Referral r = getReferralById(referralId);
        if (r != null) {
            r.setStatus(newStatus);
            r.setLastUpdated(new Date());
            saveReferrals();
        }
    }

    public void writeReferralEmailText(Referral referral) {
        if (referral == null) return;

        File dir = new File(REFERRAL_EMAIL_OUT_DIR);
        if (!dir.exists()) dir.mkdirs();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String filename = REFERRAL_EMAIL_OUT_DIR + "/" + referral.getReferralId() + "_referral.txt";

        ArrayList<String> lines = new ArrayList<>();
        lines.add("=== REFERRAL EMAIL CONTENT (SIMULATED) ===");
        lines.add("Referral ID: " + referral.getReferralId());
        lines.add("Date: " + (referral.getReferralDate() == null ? "" : sdf.format(referral.getReferralDate())));
        lines.add("");
        lines.add("Patient ID: " + referral.getPatientId());
        lines.add("Appointment ID: " + safe(referral.getAppointmentId()));
        lines.add("");
        lines.add("From Clinician ID: " + safe(referral.getReferringClinicianId()));
        lines.add("To Clinician ID: " + safe(referral.getReferredToClinicianId()));
        lines.add("From Facility ID: " + safe(referral.getReferringFacilityId()));
        lines.add("To Facility ID: " + safe(referral.getReferringToFacilityId()));
        lines.add("");
        lines.add("Urgency: " + safe(referral.getUrgencyLevel()));
        lines.add("Reason: " + safe(referral.getReferralReason()));
        lines.add("");
        lines.add("Clinical Summary:");
        lines.add(safe(referral.getClinicalSummary()));
        lines.add("");
        lines.add("Requested Investigation:");
        lines.add(safe(referral.getRequestedInvestigation()));
        lines.add("");
        lines.add("Notes:");
        lines.add(safe(referral.getNotes()));
        lines.add("");
        lines.add("Status: " + (referral.getStatus() == null ? "" : referral.getStatus().toCSV()));
        lines.add("Created: " + (referral.getCreatedDate() == null ? "" : sdf.format(referral.getCreatedDate())));
        lines.add("Last Updated: " + (referral.getLastUpdated() == null ? "" : sdf.format(referral.getLastUpdated())));

        CSVHandler.writeLines(filename, lines);
    }

    private void updateNextId(String referralId) {
        if (referralId == null) return;
        String digits = referralId.replaceAll("\\D+", "");
        if (digits.isEmpty()) return;

        try {
            int num = Integer.parseInt(digits);
            if (num >= nextReferralNumber) nextReferralNumber = num + 1;
        } catch (NumberFormatException ignored) {}
    }

    private boolean looksLikeHeader(String line, String token) {
        if (line == null) return false;
        String l = line.trim().toLowerCase();
        return l.contains(token.toLowerCase());
    }

    private String safe(String s) {
        return s == null ? "" : s;
    }
}
