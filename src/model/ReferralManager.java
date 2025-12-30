package model;

import CSV.CSVHandler;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class ReferralManager {

    private static ReferralManager instance;

    // All referrals in the system (used for UI tables + saving)
    private final ArrayList<Referral> referrals = new ArrayList<>();

    // FIFO queue for processing
    private final ArrayList<Referral> referralQueue = new ArrayList<>();

    // Track processed referrals (prevents duplicates / reprocessing)
    private final HashMap<String, Referral> processedReferrals = new HashMap<>();

    private int nextReferralId = 1;

    private final String referralEmailOutFile = "referral_emails.txt";
    private final String referralAuditFile = "referral_audit.txt";

    private ReferralManager() {}

    public static ReferralManager getInstance() {
        if (instance == null) {
            instance = new ReferralManager();
        }
        return instance;
    }

    // ---------------- ID Generation ----------------
    public String generateNextReferralId() {
        return "R" + String.format("%04d", nextReferralId++);
    }

    // ---------------- Add / Store ----------------
    public void addReferral(Referral referral) {
        if (referral == null) return;

        // Prevent duplicates in referrals list
        if (getReferralById(referral.getReferralId()) != null) return;

        referrals.add(referral);
        syncNextReferralId(referral.getReferralId());

        // Auto-queue if needs processing
        if (referral.getStatus() == ReferralStatus.NEW || referral.getStatus() == ReferralStatus.PENDING) {
            enqueueReferral(referral);
        }
    }

    private void syncNextReferralId(String referralId) {
        if (referralId == null || !referralId.startsWith("R")) return;

        String numPart = referralId.substring(1).trim();
        if (numPart.isEmpty()) return;

        for (int i = 0; i < numPart.length(); i++) {
            if (!Character.isDigit(numPart.charAt(i))) return;
        }

        try {
            int value = Integer.parseInt(numPart);
            if (value >= nextReferralId) nextReferralId = value + 1;
        } catch (NumberFormatException ignored) {}
    }

    // ---------------- Queue ----------------
    public void enqueueReferral(Referral referral) {
        if (referral == null) return;

        // already processed -> do not queue
        if (processedReferrals.containsKey(referral.getReferralId())) return;

        // avoid queue duplicates
        for (int i = 0; i < referralQueue.size(); i++) {
            if (referralQueue.get(i).getReferralId().equals(referral.getReferralId())) {
                return;
            }
        }

        referralQueue.add(referral);
    }

    public boolean hasPendingReferrals() {
        return !referralQueue.isEmpty();
    }

    // ---------------- Query ----------------
    public ArrayList<Referral> getAllReferrals() {
        return new ArrayList<>(referrals);
    }

    public ArrayList<Referral> getPendingReferrals() {
        ArrayList<Referral> pending = new ArrayList<>();
        for (int i = 0; i < referrals.size(); i++) {
            ReferralStatus st = referrals.get(i).getStatus();
            if (st == ReferralStatus.NEW || st == ReferralStatus.PENDING || st == ReferralStatus.INPROGRESS) {
                pending.add(referrals.get(i));
            }
        }
        return pending;
    }

    public ArrayList<Referral> getReferralsByStatus(ReferralStatus status) {
        ArrayList<Referral> result = new ArrayList<>();
        for (int i = 0; i < referrals.size(); i++) {
            if (referrals.get(i).getStatus() == status) {
                result.add(referrals.get(i));
            }
        }
        return result;
    }

    public ArrayList<Referral> getReferralsByPatient(String patientId) {
        ArrayList<Referral> result = new ArrayList<>();
        for (int i = 0; i < referrals.size(); i++) {
            if (referrals.get(i).getPatientId().equals(patientId)) {
                result.add(referrals.get(i));
            }
        }
        return result;
    }

    public Referral getReferralById(String referralId) {
        for (int i = 0; i < referrals.size(); i++) {
            if (referrals.get(i).getReferralId().equals(referralId)) {
                return referrals.get(i);
            }
        }
        return null;
    }

    public void updateReferralStatus(String referralId, ReferralStatus newStatus) {
        Referral r = getReferralById(referralId);
        if (r == null) return;

        r.setStatus(newStatus);
        r.setLastUpdated(new Date());

        if (newStatus == ReferralStatus.NEW || newStatus == ReferralStatus.PENDING) {
            enqueueReferral(r);
        }
    }

    public void clearAllReferrals() {
        referrals.clear();
        referralQueue.clear();
        processedReferrals.clear();
        nextReferralId = 1;
    }

    // ---------------- Processing ----------------
    public void processNextReferral(HCModel model) {
        if (model == null) return;
        if (referralQueue.isEmpty()) return;

        Referral r = referralQueue.remove(0);
        if (r == null) return;

        // already processed -> ignore
        if (processedReferrals.containsKey(r.getReferralId())) return;

        r.setStatus(ReferralStatus.INPROGRESS);
        r.setLastUpdated(new Date());

        String emailContent = buildReferralEmail(model, r);

        appendText(referralEmailOutFile, emailContent + "\n");
        appendText(referralAuditFile, auditLine("EMAIL_GENERATED", r) + "\n");

        r.setStatus(ReferralStatus.COMPLETED);
        r.setLastUpdated(new Date());

        processedReferrals.put(r.getReferralId(), r);

        // Persist model data (including referrals.csv)
        model.saveAllData();

        appendText(referralAuditFile, auditLine("REFERRAL_COMPLETED", r) + "\n");
    }

    // ---------------- Helpers ----------------
    private String buildReferralEmail(HCModel model, Referral r) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Patient p = model.getPatient(r.getPatientId());
        Clinician from = model.getClinician(r.getReferringClinicianId());
        Clinician to = model.getClinician(r.getReferredToClinicianId());
        Facility fromF = model.getFacility(r.getReferringFacilityId());
        Facility toF = model.getFacility(r.getReferringToFacilityId());

        StringBuilder sb = new StringBuilder();
        sb.append("=== REFERRAL EMAIL (SIMULATED) ===\n");
        sb.append("Referral ID: ").append(r.getReferralId()).append("\n");
        sb.append("Date: ").append(sdf.format(r.getReferralDate())).append("\n");
        sb.append("Urgency: ").append(r.getUrgencyLevel()).append("\n\n");

        sb.append("Patient: ")
                .append(p != null ? p.getFirstName() + " " + p.getLastName() : r.getPatientId())
                .append("\n");

        sb.append("From Clinician: ")
                .append(from != null ? from.getTitle() + " " + from.getFirstName() + " " + from.getLastName() : r.getReferringClinicianId())
                .append("\n");

        sb.append("To Clinician: ")
                .append(to != null ? to.getTitle() + " " + to.getFirstName() + " " + to.getLastName() : r.getReferredToClinicianId())
                .append("\n");

        sb.append("From Facility: ")
                .append(fromF != null ? fromF.getFacilityName() : r.getReferringFacilityId())
                .append("\n");

        sb.append("To Facility: ")
                .append(toF != null ? toF.getFacilityName() : r.getReferringToFacilityId())
                .append("\n\n");

        sb.append("Reason: ").append(r.getReferralReason()).append("\n");
        sb.append("Clinical Summary: ").append(r.getClinicalSummary()).append("\n");
        sb.append("Requested Investigation: ").append(r.getRequestedInvestigation()).append("\n");
        sb.append("Appointment ID: ").append(r.getAppointmentId()).append("\n");
        sb.append("Notes: ").append(r.getNotes()).append("\n");
        sb.append("=================================\n");

        return sb.toString();
    }

    private String auditLine(String action, Referral r) {
        return new Date() + " | " + action +
                " | referralId=" + r.getReferralId() +
                " | patientId=" + r.getPatientId();
    }

    private void appendText(String filename, String content) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(content);
        } catch (IOException e) {
            System.err.println("Failed to append to file: " + filename);
            e.printStackTrace();
        }
    }
}
