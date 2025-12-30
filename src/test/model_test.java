
 package test;

import model.*;

import java.text.SimpleDateFormat;
import java.util.Date;

public class model_test {

    private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("MODEL CLASSES TEST HARNESS STARTING");
        System.out.println("====================================\n");

        testEnums();
        testPatient();
        testFacility();
        testClinician();
        testAppointment();
        testPrescription();
        testReferral();
        testStaff();

        System.out.println("\n====================================");
        System.out.println("ALL TESTS COMPLETED");
        System.out.println("====================================");
    }

    // ---------- Helpers ----------
    private static void assertTrue(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(" ASSERT FAILED: " + message);
        }
        System.out.println("Correct " + message);
    }

    private static Date d(String yyyyMMdd) throws Exception {
        return SDF.parse(yyyyMMdd);
    }

    private static void header(String name) {
        System.out.println("\n--- " + name + " ---");
    }

    // ---------- Tests ----------

    private static void testEnums() {
        header("Enums");

        EmploymentStatus es1 = EmploymentStatus.fromCSV("Full-Time");
        EmploymentStatus es2 = EmploymentStatus.fromCSV("PARTTIME");
        assertTrue(es1 == EmploymentStatus.FULLTIME, "EmploymentStatus.fromCSV handles 'Full-Time'");
        assertTrue(es2 == EmploymentStatus.PARTTIME, "EmploymentStatus.fromCSV handles 'PARTTIME'");

        StatusType st1 = StatusType.fromCSV("Scheduled");
        StatusType st2 = StatusType.fromCSV("CANCELLED");
        assertTrue(st1 == StatusType.SCHEDULED, "StatusType.fromCSV handles 'Scheduled'");
        assertTrue(st2 == StatusType.CANCELLED, "StatusType.fromCSV handles 'CANCELLED'");

        ReferralStatus rs1 = ReferralStatus.fromCSV("In Progress");
        ReferralStatus rs2 = ReferralStatus.fromCSV("NEW");
        assertTrue(rs1 == ReferralStatus.INPROGRESS, "ReferralStatus.fromCSV handles 'In Progress'");
        assertTrue(rs2 == ReferralStatus.NEW, "ReferralStatus.fromCSV handles 'NEW'");
    }

    private static void testPatient() throws Exception {
        header("Patient");

        Patient p = new Patient(
                "P001", "John", "Doe", d("1990-05-12"), "1234567890", "Male",
                "07123456789", "john@example.com",
                "1 High Street", "AB1 2CD",
                "Jane Doe", "07999999999",
                d("2020-01-01"), "S001"
        );

        String csv = p.toCSV();
        System.out.println("toCSV: " + csv);

        Patient p2 = Patient.fromCSV(csv);
        assertTrue(p2 != null, "Patient.fromCSV returns non-null");
        assertTrue(p2.getPatientId().equals("P001"), "Patient round-trip keeps patientId");
        assertTrue(p2.getNhsNumber().equals("1234567890"), "Patient round-trip keeps NHS number");
        assertTrue(p2.getGpSurgeryId().equals("S001"), "Patient round-trip keeps gpSurgeryId");

        System.out.println("toString:\n" + p2);
    }

    private static void testFacility() {
        header("Facility");

        Facility f = new Facility(
                "S001", "City GP Surgery", "Surgery",
                "10 Clinic Road", "AB1 2CD",
                "01234567890", "surgery@example.com",
                "09:00-17:00", "Dr Manager",
                "50", "General Practice;Vaccination"
        );

        String csv = f.toCSV();
        System.out.println("toCSV: " + csv);

        Facility f2 = Facility.fromCSV(csv);
        assertTrue(f2 != null, "Facility.fromCSV returns non-null");
        assertTrue(f2.getFacilityId().equals("S001"), "Facility round-trip keeps facilityId");
        assertTrue(f2.getFacilityName().equals("City GP Surgery"), "Facility round-trip keeps facilityName");

        System.out.println("toString:\n" + f2);
    }

    private static void testClinician() throws Exception {
        header("Clinician (Specialist/GP/Nurse)");

        // Test base Clinician parsing (you might store all as Clinician in CSV)
        Clinician c = new Clinician(
                "C001", "Alice", "Smith", "Dr", "General Practice",
                1234567, "07000000000", "alice.smith@nhs.uk",
                "S001", "Surgery", EmploymentStatus.FULLTIME, d("2021-02-01")
        );

        String csv = c.toCSV();
        System.out.println("toCSV: " + csv);

        Clinician c2 = Clinician.fromCSV(csv);
        assertTrue(c2 != null, "Clinician.fromCSV returns non-null");
        assertTrue(c2.getClinicianId().equals("C001"), "Clinician round-trip keeps clinicianId");
        assertTrue(c2.getSpeciality().equals("General Practice"), "Clinician round-trip keeps speciality");

        System.out.println("toString:\n" + c2);

        // Optional: show subclasses exist
        GP gp = new GP("C002", "Bob", "Jones", "Dr", "General Practice", 7654321,
                "07000000001", "bob@nhs.uk", "S001", "Surgery", EmploymentStatus.PARTTIME, d("2022-01-10"));
        System.out.println("GP:\n" + gp);

        Specialist sp = new Specialist("C010", "Dr", "Cardiology", 9991112,
                "H001", "Hospital", "Clara", "Ng", "clara@nhs.uk", "07000000002",
                EmploymentStatus.FULLTIME, d("2019-06-01"));
        System.out.println("Specialist:\n" + sp);

        Nurse n = new Nurse("C020", "Nurse", "Practice Nurse", 0,
                "S001", "Surgery", "Dana", "Lee", "dana@nhs.uk", "07000000003",
                EmploymentStatus.FULLTIME, d("2020-03-15"), "RN");
        System.out.println("Nurse:\n" + n);
    }

    private static void testAppointment() throws Exception {
        header("Appointment");

        Appointment a = new Appointment(
                "A001", "P001", "C001", "S001",
                d("2025-09-20"), "09:00", 15,
                "Routine Consultation", StatusType.SCHEDULED,
                "Annual health check", "Patient due for routine screening",
                d("2025-09-15"), d("2025-09-15")
        );

        String csv = a.toCSV();
        System.out.println("toCSV: " + csv);

        Appointment a2 = Appointment.fromCSV(csv);
        assertTrue(a2 != null, "Appointment.fromCSV returns non-null");
        assertTrue(a2.getAppointmentId().equals("A001"), "Appointment round-trip keeps appointmentId");
        assertTrue(a2.getStatus() == StatusType.SCHEDULED, "Appointment round-trip keeps status");
        assertTrue(a2.getDurationMinutes() == 15, "Appointment round-trip keeps duration");

        System.out.println("toString:\n" + a2);

        // If your real CSV uses "Scheduled" not "SCHEDULED", you should ensure fromCSV uses StatusType.fromCSV(...)
        String csvLikeFile = "A002,P002,C009,S001,2025-09-20,10:30,30,Vaccination,Scheduled,Flu vaccination,Annual flu jab appointment,2025-09-14,2025-09-14";
        Appointment a3 = Appointment.fromCSV(csvLikeFile);
        assertTrue(a3 != null, "Appointment.fromCSV parses 'Scheduled' (title case) if using StatusType.fromCSV");
        System.out.println("Parsed file-like row:\n" + a3);
    }

    private static void testPrescription() throws Exception {
        header("Prescription");

        Prescription pr = new Prescription(
                "RX001", "P001", "C001", "A001",
                d("2025-09-20"),
                "Amoxicillin", "500mg", "3x daily",
                7, "21 tablets", "Take after food", "City Pharmacy",
                "ISSUED", d("2025-09-20"), d("2025-09-22")
        );

        String csv = pr.toCSV();
        System.out.println("toCSV: " + csv);

        Prescription pr2 = Prescription.fromCSV(csv);
        assertTrue(pr2 != null, "Prescription.fromCSV returns non-null");
        assertTrue(pr2.getPrescriptionId().equals("RX001"), "Prescription round-trip keeps prescriptionId");
        assertTrue(pr2.getMedicationName().equals("Amoxicillin"), "Prescription round-trip keeps medicationName");

        System.out.println("toString:\n" + pr2);
    }

    private static void testReferral() throws Exception {
        header("Referral");

        Referral r = new Referral(
                "R001", "P001", "C001", "C010",
                "S001", "H001",
                d("2025-09-21"),
                "URGENT",
                "Cardiology referral",
                "Patient reports chest pain and shortness of breath.",
                "ECG + Echo",
                ReferralStatus.NEW,
                "A004",
                "Needs urgent review",
                d("2025-09-21"),
                d("2025-09-21")
        );

        String csv = r.toCSV();
        System.out.println("toCSV: " + csv);

        Referral r2 = Referral.fromCSV(csv);
        assertTrue(r2 != null, "Referral.fromCSV returns non-null");
        assertTrue(r2.getReferralId().equals("R001"), "Referral round-trip keeps referralId");
        assertTrue(r2.getStatus() == ReferralStatus.NEW, "Referral round-trip keeps status");

        System.out.println("toString:\n" + r2);
    }

    private static void testStaff() throws Exception {
        header("Staff");

        Staff s = new Staff(
                "ST001", "Eve", "Walker",
                "Receptionist", "Front Desk", "S001",
                "07000000004", "eve@nhs.uk",
                EmploymentStatus.PARTTIME, d("2023-01-01"),
                "Manager A", "LOW"
        );

        String csv = s.toCSV();
        System.out.println("toCSV: " + csv);

        Staff s2 = Staff.fromCSV(csv);
        assertTrue(s2 != null, "Staff.fromCSV returns non-null");
        assertTrue(s2.getStaffId().equals("ST001"), "Staff round-trip keeps staffId");
        assertTrue(s2.getRole().equals("Receptionist"), "Staff round-trip keeps role");

        System.out.println("toString:\n" + s2);
    }
}
