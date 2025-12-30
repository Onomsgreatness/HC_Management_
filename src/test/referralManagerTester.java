package test;

import model.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Tester for ReferralManager Singleton + processing + file output.
 *
 * Assumptions:
 * - ReferralManager is in package model
 * - HCModel exists but loads CSV by default (not ideal for unit test)
 *
 * To avoid relying on real CSVs, this tester uses a small "FakeHCModel" that
 * provides the needed lookup methods and a no-op saveAllData().
 */
public class referralManagerTester {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("ReferralManager TEST HARNESS START");
        System.out.println("====================================\n");

        testSingleton();
        testAddAndQueueAndProcess();
        testDuplicateAdd();

        System.out.println("\n====================================");
        System.out.println("ReferralManager TESTS COMPLETED ✅");
        System.out.println("====================================");
    }

    // ---------- Tests ----------

    private static void testSingleton() {
        header("Singleton");

        ReferralManager rm1 = ReferralManager.getInstance();
        ReferralManager rm2 = ReferralManager.getInstance();

        assertTrue(rm1 == rm2, "ReferralManager.getInstance() returns same instance");
    }

    private static void testAddAndQueueAndProcess() throws Exception {
        header("Add + Queue + Process");

        // Clean slate
        ReferralManager rm = ReferralManager.getInstance();
        rm.clearAllReferrals();

        // Ensure output files start clean (optional)
        deleteIfExists("referral_emails.txt");
        deleteIfExists("referral_audit.txt");

        // Fake model with data the email generator needs
        FakeHCModel model = new FakeHCModel();
        model.seed();

        // Create referral
        Date now = SDF.parse("2025-09-21");
        Referral r = new Referral(
                "R0001",
                "P001",
                "C001",
                "C010",
                "S001",
                "H001",
                now,
                "URGENT",
                "Cardiology referral",
                "Chest pain and shortness of breath",
                "ECG + Echo",
                ReferralStatus.NEW,
                "A004",
                "Needs urgent review",
                now,
                now
        );

        rm.addReferral(r);

        assertTrue(rm.getAllReferrals().size() == 1, "addReferral stores referral");
        assertTrue(rm.hasPendingReferrals(), "NEW referral gets queued for processing");

        rm.processNextReferral(model);

        Referral after = rm.getReferralById("R0001");
        assertTrue(after != null, "Referral still accessible after processing");
        assertTrue(after.getStatus() == ReferralStatus.COMPLETED, "Referral status becomes COMPLETED after processing");

        // Queue should now be empty (since processed)
        assertTrue(!rm.hasPendingReferrals(), "Queue empties after processing");

        // Files should be created + have content
        assertTrue(new File("referral_emails.txt").exists(), "referral_emails.txt created");
        assertTrue(new File("referral_audit.txt").exists(), "referral_audit.txt created");
        assertTrue(new File("referral_emails.txt").length() > 0, "referral_emails.txt not empty");
        assertTrue(new File("referral_audit.txt").length() > 0, "referral_audit.txt not empty");

        System.out.println("Email & audit files written ✅");
    }

    private static void testDuplicateAdd() throws Exception {
        header("Duplicate Prevention");

        ReferralManager rm = ReferralManager.getInstance();
        rm.clearAllReferrals();

        FakeHCModel model = new FakeHCModel();
        model.seed();

        Date now = SDF.parse("2025-09-21");
        Referral r = new Referral(
                "R0002",
                "P001",
                "C001",
                "C010",
                "S001",
                "H001",
                now,
                "ROUTINE",
                "Routine neuro referral",
                "Headaches",
                "MRI",
                ReferralStatus.NEW,
                "A011",
                "Routine review",
                now,
                now
        );

        rm.addReferral(r);
        rm.addReferral(r); // add same object twice

        assertTrue(rm.getAllReferrals().size() == 1, "Adding same referral twice does not duplicate in referrals list");

        // Process once
        rm.processNextReferral(model);

        Referral processed = rm.getReferralById("R0002");
        assertTrue(processed.getStatus() == ReferralStatus.COMPLETED, "Processed referral completes");

        // If status is completed, re-adding same ID should not create duplicates
        rm.addReferral(r);
        assertTrue(rm.getAllReferrals().size() == 1, "Re-adding processed referral does not duplicate");

        System.out.println("Duplicate checks passed ✅");
    }

    // ---------- FakeHCModel (minimal dependency) ----------

    /**
     * Minimal stub to satisfy ReferralManager.buildReferralEmail(...)
     * and ReferralManager.processNextReferral(...)
     */
    private static class FakeHCModel extends HCModel {
        // We do NOT want to load files in this test, but your HCModel constructor loads CSV.
        // If HCModel has no-arg constructor that loads files, this is not ideal.
        // If that happens, the test still runs, but relies on CSV existing.
        //
        // BEST PRACTICE: create a HCModel(boolean loadFiles) constructor.
        // For now, we override lookup methods to return our test objects.

        private Patient patient;
        private Clinician fromClinician;
        private Clinician toClinician;
        private Facility fromFacility;
        private Facility toFacility;

        public void seed() throws Exception {
            patient = new Patient(
                    "P001", "John", "Doe",
                    SDF.parse("1990-05-12"),
                    "1234567890",
                    "Male",
                    "07123456789",
                    "john@example.com",
                    "1 High Street",
                    "AB1 2CD",
                    "Jane Doe",
                    "07999999999",
                    SDF.parse("2020-01-01"),
                    "S001"
            );

            fromClinician = new Clinician(
                    "C001", "Alice", "Smith",
                    "Dr", "General Practice",
                    1234567,
                    "07000000000",
                    "alice@nhs.uk",
                    "S001",
                    "Surgery",
                    EmploymentStatus.FULLTIME,
                    SDF.parse("2021-02-01")
            );

            toClinician = new Clinician(
                    "C010", "Clara", "Ng",
                    "Dr", "Cardiology",
                    9991112,
                    "07000000002",
                    "clara@nhs.uk",
                    "H001",
                    "Hospital",
                    EmploymentStatus.FULLTIME,
                    SDF.parse("2019-06-01")
            );

            fromFacility = new Facility(
                    "S001",
                    "City GP Surgery",
                    "Surgery",
                    "10 Clinic Road",
                    "AB1 2CD",
                    "01234567890",
                    "surgery@example.com",
                    "09:00-17:00",
                    "Dr Manager",
                    "50",
                    "General Practice;Vaccination"
            );

            toFacility = new Facility(
                    "H001",
                    "Central Hospital",
                    "Hospital",
                    "99 Health Ave",
                    "XY9 9ZZ",
                    "02000000000",
                    "hospital@example.com",
                    "24/7",
                    "Hospital Manager",
                    "500",
                    "Cardiology;Neurology"
            );
        }

        @Override
        public Patient getPatient(String patientId) {
            if (patient != null && patient.getPatientId().equals(patientId)) return patient;
            return null;
        }

        @Override
        public Clinician getClinician(String clinicianId) {
            if (fromClinician != null && fromClinician.getClinicianId().equals(clinicianId)) return fromClinician;
            if (toClinician != null && toClinician.getClinicianId().equals(clinicianId)) return toClinician;
            return null;
        }

        @Override
        public Facility getFacility(String facilityId) {
            if (fromFacility != null && fromFacility.getFacilityId().equals(facilityId)) return fromFacility;
            if (toFacility != null && toFacility.getFacilityId().equals(facilityId)) return toFacility;
            return null;
        }

        @Override
        public void saveAllData() {
            // no-op for test
        }
    }

    // ---------- Utilities ----------

    private static void header(String name) {
        System.out.println("\n--- " + name + " ---");
    }

    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError("❌ ASSERT FAILED: " + message);
        }
        System.out.println("✅ " + message);
    }

    private static void deleteIfExists(String filename) {
        File f = new File(filename);
        if (f.exists()) {
            boolean ok = f.delete();
            if (ok) System.out.println("Deleted old " + filename);
        }
    }
}
