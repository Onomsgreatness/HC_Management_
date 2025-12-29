//package test;
//
//import model.HCModel;
//import controller.HCController;
//import model.Referral;
//import model.ReferralStatus;
//import model.HCModel;
//
//import java.util.ArrayList;
//import java.util.Date;
//
//public class model_test {
//    public static void main(String[] args) throws Exception{
//
//        HCController controller = new HCController();
//
//        // 1) Confirm data loaded
//        System.out.println("Patients loaded: " + controller.getAllPatients().size());
//        System.out.println("Clinicians loaded: " + controller.getAllClinicians().size());
//        System.out.println("Facilities loaded: " + controller.getAllFacilities().size());
//        System.out.println("Appointments loaded: " + controller.getAllAppointments().size());
//        System.out.println("Prescriptions loaded: " + controller.getAllPrescriptions().size());
//        System.out.println("Referrals loaded: " + controller.getAllReferrals().size());
//
//        // 2) Create a referral using existing IDs from your CSV
//        // Use IDs that actually exist in your data:
//        // Patient: P001
//        // Referring Clinician (GP): C001
//        // Referred Clinician (Specialist): C005 (Cardiology)
//        // Referring Facility (GP surgery): S001
//        // Referred Facility (Hospital): H001
//        Referral referral = new Referral(
//                "R999",              // referralId (temporary; see note below)
//                "P001",
//                "C001",
//                "C005",
//                "S001",
//                "H001",
//                new Date(),          // referralDate
//                "URGENT",
//                "Cardiology referral",
//                "Patient reports chest pain + shortness of breath.",
//                "ECG + Echo",
//                ReferralStatus.NEW,
//                "A001",
//                "Please triage urgently.",
//                new Date(),          // createdDate
//                new Date()           // lastUpdated
//        );
//
//        boolean created = controller.createReferral(referral);
//        System.out.println("Referral created? " + created);
//        controller.shutdown();
//
//        // 3) Show the latest referrals
//        ArrayList<Referral> referrals = controller.getAllReferrals();
//        System.out.println("Referrals after creation: " + referrals.size());
//
//        // Optional: update status if you want to test lifecycle
//        if (!referrals.isEmpty()) {
//            String lastId = referrals.get(referrals.size() - 1).getReferralId();
//            controller.updateReferralStatus(lastId, ReferralStatus.PENDING);
//            System.out.println("Updated referral status to PENDING for: " + lastId);
//        }
//
//        // 4) Save everything (good practice)
//        controller.shutdown();
//
//        System.out.println("Done.");
//    }
//}
