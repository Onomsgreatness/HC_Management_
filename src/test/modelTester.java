package test;

import model.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class modelTester {


    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("HCModel TEST HARNESS START");
        System.out.println("====================================\n");

        testModelStartsEmptyWhenLoadFilesFalse();
        testPatientCRUD_inMemory();
        testClinicianCRUD_inMemory();
        testFacilityCRUD_inMemory();
        testAppointmentCRUD_inMemory();
        testPrescriptionCRUD_inMemory();
        testReferralCreationValidation();

        System.out.println("\n====================================");
        System.out.println("HCModel TESTS COMPLETED ✅");
        System.out.println("====================================");
    }

    // ---------------- TESTS ----------------

    private static void testModelStartsEmptyWhenLoadFilesFalse() {
        header("Constructor loadFiles=false");

        HCModel model = new HCModel(false);

        assertTrue(!model.hasAnyDataLoaded(),
                "Model should be empty when loadFiles=false (hasAnyDataLoaded == false)");
    }

    /**
     * Because your HCModel.addPatient() calls savePatients() (writes to patients.csv),
     * this test uses reflection-free, safe approach:
     * - We create model (empty)
     * - We create Patient
     * - We call addPatient and then read it back
     *
     * If you don't want tests writing to real files, I can show you how to disable saving for tests.
     */
    private static void testPatientCRUD_inMemory() throws Exception {
        header("Patient CRUD");

        HCModel model = new HCModel(false);

        Patient p = new Patient(
                "PTEST",
                "John",
                "Doe",
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

        model.addPatient(p);

        Patient fetched = model.getPatient("PTEST");
        assertTrue(fetched != null, "addPatient then getPatient returns non-null");
        assertTrue("John".equals(fetched.getFirstName()), "Patient first name persisted");

        // Update
        Patient updated = new Patient(
                "PTEST",
                "Johnny",
                "Doe",
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
        model.updatePatient("PTEST", updated);

        Patient fetched2 = model.getPatient("PTEST");
        assertTrue("Johnny".equals(fetched2.getFirstName()), "updatePatient works");

        // Delete
        model.deletePatient("PTEST");
        Patient fetched3 = model.getPatient("PTEST");
        assertTrue(fetched3 == null, "deletePatient works");
    }

    private static void testClinicianCRUD_inMemory() throws Exception {
        header("Clinician CRUD");

        HCModel model = new HCModel(false);

        Clinician c = new Clinician(
                "CTEST",
                "Alice",
                "Smith",
                "Dr",
                "General Practice",
                1234567,
                "07000000000",
                "alice@nhs.uk",
                "S001",
                "Surgery",
                EmploymentStatus.FULLTIME,
                SDF.parse("2021-02-01")
        );

        model.addClinician(c);

        Clinician fetched = model.getClinician("CTEST");
        assertTrue(fetched != null, "addClinician then getClinician returns non-null");
        assertTrue("Alice".equals(fetched.getFirstName()), "Clinician first name persisted");

        Clinician updated = new Clinician(
                "CTEST",
                "Alicia",
                "Smith",
                "Dr",
                "General Practice",
                1234567,
                "07000000000",
                "alice@nhs.uk",
                "S001",
                "Surgery",
                EmploymentStatus.FULLTIME,
                SDF.parse("2021-02-01")
        );
        model.updateClinician("CTEST", updated);

        Clinician fetched2 = model.getClinician("CTEST");
        assertTrue("Alicia".equals(fetched2.getFirstName()), "updateClinician works");

        model.deleteClinician("CTEST");
        Clinician fetched3 = model.getClinician("CTEST");
        assertTrue(fetched3 == null, "deleteClinician works");
    }

    private static void testFacilityCRUD_inMemory() {
        header("Facility CRUD");

        HCModel model = new HCModel(false);

        Facility f = new Facility(
                "FTEST",
                "Test Surgery",
                "Surgery",
                "10 Clinic Road",
                "AB1 2CD",
                "01234567890",
                "surgery@example.com",
                "09:00-17:00",
                "Manager",
                "50",
                "General Practice"
        );

        model.addFacility(f);

        Facility fetched = model.getFacility("FTEST");
        assertTrue(fetched != null, "addFacility then getFacility returns non-null");
        assertTrue("Test Surgery".equals(fetched.getFacilityName()), "Facility name persisted");
    }

    private static void testAppointmentCRUD_inMemory() throws Exception {
        header("Appointment CRUD");

        HCModel model = new HCModel(false);

        Date apptDate = SDF.parse("2025-09-20");
        Date created = SDF.parse("2025-09-15");

        Appointment a = new Appointment(
                "ATEST",
                "P001",
                "C001",
                "S001",
                apptDate,
                "09:00",
                15,
                "Routine Consultation",
                StatusType.SCHEDULED,
                "Annual health check",
                "Patient due for screening",
                created,
                created
        );

        model.addAppointment(a);

        Appointment fetched = model.getAppointment("ATEST");
        assertTrue(fetched != null, "addAppointment then getAppointment returns non-null");
        assertTrue("P001".equals(fetched.getPatientId()), "Appointment patientId persisted");

        // Update
        a.setNotes("Updated notes");
        model.updateAppointment("ATEST", a);

        Appointment fetched2 = model.getAppointment("ATEST");
        assertTrue("Updated notes".equals(fetched2.getNotes()), "updateAppointment works");

        // Delete
        model.deleteAppointment("ATEST");
        Appointment fetched3 = model.getAppointment("ATEST");
        assertTrue(fetched3 == null, "deleteAppointment works");
    }

    private static void testPrescriptionCRUD_inMemory() throws Exception {
        header("Prescription CRUD");

        HCModel model = new HCModel(false);

        Date d = SDF.parse("2025-09-20");

        Prescription rx = new Prescription(
                "RXTEST",
                "P001",
                "C001",
                "A001",
                d,
                "Amoxicillin",
                "500mg",
                "3x daily",
                7,
                "21 tablets",
                "Take after meals",
                "Boots Pharmacy",
                "ISSUED",
                d,
                d
        );

        model.addPrescription(rx, false);

        Prescription fetched = model.getPrescription("RXTEST");
        assertTrue(fetched != null, "addPrescription then getPrescription returns non-null");
        assertTrue("Amoxicillin".equals(fetched.getMedicationName()), "Medication name persisted");

        // Update
        rx.setMedicationName("Ibuprofen");
        model.updatePrescription("RXTEST", rx);

        Prescription fetched2 = model.getPrescription("RXTEST");
        assertTrue("Ibuprofen".equals(fetched2.getMedicationName()), "updatePrescription works");

        // Delete
        model.deletePrescription("RXTEST");
        Prescription fetched3 = model.getPrescription("RXTEST");
        assertTrue(fetched3 == null, "deletePrescription works");
    }

    private static void testReferralCreationValidation() throws Exception {
        header("Referral Creation Validation");

        HCModel model = new HCModel(false);

        // We must seed required linked records because createReferral validates them
        Patient p = new Patient(
                "P001",
                "John",
                "Doe",
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
        model.addPatient(p);

        Clinician cFrom = new Clinician(
                "C001",
                "Alice",
                "Smith",
                "Dr",
                "General Practice",
                1234567,
                "07000000000",
                "alice@nhs.uk",
                "S001",
                "Surgery",
                EmploymentStatus.FULLTIME,
                SDF.parse("2021-02-01")
        );
        Clinician cTo = new Clinician(
                "C010",
                "Clara",
                "Ng",
                "Dr",
                "Cardiology",
                9991112,
                "07000000002",
                "clara@nhs.uk",
                "H001",
                "Hospital",
                EmploymentStatus.FULLTIME,
                SDF.parse("2019-06-01")
        );
        model.addClinician(cFrom);
        model.addClinician(cTo);

        Facility fFrom = new Facility(
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
                "General Practice"
        );
        Facility fTo = new Facility(
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
                "Cardiology"
        );
        model.addFacility(fFrom);
        model.addFacility(fTo);

        Date now = SDF.parse("2025-09-21");

        Referral referral = new Referral(
                model.generateReferralId(),
                "P001",
                "C001",
                "C010",
                "S001",
                "H001",
                now,
                "URGENT",
                "Cardiology referral",
                "Chest pain and SOB",
                "ECG + Echo",
                ReferralStatus.NEW,
                "A004",
                "Needs urgent review",
                now,
                now
        );

        boolean ok = model.createReferral(referral);
        assertTrue(ok, "createReferral returns true when linked records exist");

        assertTrue(model.getAllReferrals().size() > 0, "Referral stored in model/referralManager");
    }

    // ---------------- Helpers ----------------

    private static void header(String title) {
        System.out.println("\n--- " + title + " ---");
    }

    private static void assertTrue(boolean condition, String msg) {
        if (!condition) throw new AssertionError("❌ ASSERT FAILED: " + msg);
        System.out.println("✅ " + msg);
    }
}
