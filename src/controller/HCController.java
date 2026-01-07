/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package controller;

import model.*;
import view.HCView;

import java.util.ArrayList;
import java.util.Date;

public class HCController {
    private HCModel model;
    private HCView view;

    public HCController(HCModel model, HCView view) {
        this.model = model;
        this.view = view;

        initializeView();
        setupEventListeners();
    }

    private void initializeView() {
        view.refreshPatientsTable(model.getAllPatients());
        view.refreshCliniciansTable(model.getAllClinicians());
        view.refreshAppointmentsTable(model.getAllAppointments(), model);
        view.refreshPrescriptionsTable(model.getAllPrescriptions(), model);
        view.refreshReferralsTable(model.getAllReferrals(), model);
        view.refreshFacilitiesTable(model.getAllFacilities());
        view.refreshStaffTable(model.getAllStaff());
    }

    private void setupEventListeners() {
        // Patient listeners
        view.setRefreshPatientsListener(new Runnable() {
            public void run() {
                handleRefreshPatients();
            }
        });

        view.setAddPatientListener(new PatientListener() {
            public void onAddPatient(String firstName, String lastName, Date dob, String nhsNumber,
                                     String gender, String contact, String email, String address,
                                     String postCode, String emergencyContactName, String emergencyContact,
                                     String registrationDate, String gpSurgeryId) {
                handleAddPatient(firstName, lastName, dob, nhsNumber, gender, contact, email,
                        address, postCode, emergencyContactName, emergencyContact, registrationDate, gpSurgeryId);
            }
        });

        view.setEditPatientListener(new EditPatientListener() {
            public void onEditPatient(String patientId, String firstName, String lastName, Date dob,
                                      String nhsNumber, String gender, String contact, String email,
                                      String address, String postCode, String emergencyContactName,
                                      String emergencyContact, String registrationDate, String gpSurgeryId) {
                handleEditPatient(patientId, firstName, lastName, dob, nhsNumber, gender, contact,
                        email, address, postCode, emergencyContactName, emergencyContact, registrationDate,gpSurgeryId);
            }
        });

        view.setDeletePatientListener(new DeleteListener() {
            public void onDelete(String id) {
                handleDeletePatient(id);
            }
        });

        // Clinician listeners
        view.setRefreshCliniciansListener(new Runnable() {
            public void run() {
                handleRefreshClinicians();
            }
        });

        view.setAddClinicianListener(new ClinicianListener() {
            public void onAddClinician(String firstName, String lastName, String title, String speciality,
                                       String gmcNumber, String contact, String email, String workplaceId,
                                       String workplaceType, EmploymentStatus employmentStatus, Date startDate) {
                handleAddClinician(firstName, lastName, title, speciality, gmcNumber, contact, email,
                        workplaceId, workplaceType, employmentStatus, startDate);
            }
        });

        view.setEditClinicianListener(new EditClinicianListener() {
            public void onEditClinician(String clinicianId, String firstName, String lastName, String title,
                                        String speciality, String gmcNumber, String contact, String email,
                                        String workplaceId, String workplaceType,
                                        EmploymentStatus employmentStatus, Date startDate) {
                    handleEditClinician(clinicianId, firstName, lastName, title, speciality, gmcNumber,
                            contact, email, workplaceId, workplaceType, employmentStatus, startDate);
            }
        });

        view.setDeleteClinicianListener(new DeleteListener() {
            public void onDelete(String id) {
                handleDeleteClinician(id);
            }
        });

        // Appointment listeners
        view.setRefreshAppointmentsListener(new Runnable() {
            public void run() {
                handleRefreshAppointments();
            }
        });

        view.setAddAppointmentListener(new AppointmentListener() {
            public void onAddAppointment(String patientId, String clinicianId, String facilityId,
                                         Date appointmentDate, String appointmentTime, int durationMinutes,
                                         String appointmentType, String reasonForVisit, String notes,
                                         Date createdDate, Date lastModified) {
                handleAddAppointment(patientId, clinicianId, facilityId, appointmentDate, appointmentTime,
                        durationMinutes, appointmentType, reasonForVisit, notes, createdDate, lastModified);
            }
        });

        view.setUpdateAppointmentStatusListener(new UpdateStatusListener() {
            public void onUpdateStatus(String id, String status) {
                handleUpdateAppointmentStatus(id, status);
            }
        });

        view.setDeleteAppointmentListener(new DeleteListener() {
            public void onDelete(String id) {
                handleDeleteAppointment(id);
            }
        });

        // Prescription listeners
        view.setRefreshPrescriptionsListener(new Runnable() {
            public void run() {
                handleRefreshPrescriptions();
            }
        });

        view.setAddPrescriptionListener(new PrescriptionListener() {
            public void onAddPrescription(String patientId, String clinicianId, String appointmentId,
                                          Date prescriptionDate, String medicationName, String dosage,
                                          String frequency, int durationDays, String quantity,
                                          String instruction, String pharmacyName) {
                handleAddPrescription(patientId, clinicianId, appointmentId, prescriptionDate,
                        medicationName, dosage, frequency, durationDays, quantity,
                        instruction, pharmacyName);
            }
        });

        view.setGeneratePrescriptionDocumentListener(new GenerateDocumentListener() {
            public void onGenerateDocument(String id) {
                handleGeneratePrescriptionDocument(id);
            }
        });

        view.setDeletePrescriptionListener(new DeleteListener() {
            public void onDelete(String id) {
                handleDeletePrescription(id);
            }
        });

        // Referral listeners
        view.setRefreshReferralsListener(new Runnable() {
            public void run() {
                handleRefreshReferrals();
            }
        });

        view.setAddReferralListener(new ReferralListener() {
            public void onAddReferral(String patientId, String referringClinicianId, String referredToClinicianId,
                                      String referringFacilityId, String referringToFacilityId, Date referralDate,
                                      String urgencyLevel, String referralReason, String clinicalSummary,
                                      String requestedInvestigation, String appointmentId, String notes) {
                handleAddReferral(patientId, referringClinicianId, referredToClinicianId, referringFacilityId,
                        referringToFacilityId, referralDate, urgencyLevel, referralReason,
                        clinicalSummary, requestedInvestigation, appointmentId, notes);
            }
        });

        view.setUpdateReferralStatusListener(new UpdateStatusListener() {
            public void onUpdateStatus(String id, String status) {
                handleUpdateReferralStatus(id, status);
            }
        });

        view.setGenerateReferralDocumentListener(new GenerateDocumentListener() {
            public void onGenerateDocument(String id) {
                handleGenerateReferralDocument(id);
            }
        });

        // Facility listeners
        view.setRefreshFacilitiesListener(new Runnable() {
            public void run() {
                handleRefreshFacilities();
            }
        });

        view.setAddFacilityListener(new FacilityListener() {
            public void onAddFacility(String facilityName, String facilityType, String address, String postCode,
                                      String contact, String email, String openingHours, String managerName,
                                      String capacity, String specialitiesOffered) {
                handleAddFacility(facilityName, facilityType, address, postCode, contact, email,
                        openingHours, managerName, capacity, specialitiesOffered);
            }
        });

        view.setDeleteFacilityListener(new DeleteListener() {
            public void onDelete(String id) {
                handleDeleteFacility(id);
            }
        });

        // Staff listeners
        view.setRefreshStaffListener(new Runnable() {
            public void run() {
                handleRefreshStaff();
            }
        });

        view.setAddStaffListener(new StaffListener() {
            public void onAddStaff(String firstName, String lastName, String role, String department,
                                   String facilityId, String contact, String email, EmploymentStatus employmentStatus,
                                   Date startDate, String lineManager, String accessLevel) {
                handleAddStaff(firstName, lastName, role, department, facilityId, contact, email,
                        employmentStatus, startDate, lineManager, accessLevel);
            }
        });

        view.setDeleteStaffListener(new DeleteListener() {
            public void onDelete(String id) {
                handleDeleteStaff(id);
            }
        });

        // Close listener
        view.setOnCloseListener(new Runnable() {
            public void run() {
                handleSaveData();
            }
        });
    }

    // ========== Patient Handlers ==========

    private void handleRefreshPatients() {
        view.refreshPatientsTable(model.getAllPatients());
    }

    private void handleAddPatient(String firstName, String lastName, Date dob, String nhsNumber,
                                  String gender, String contact, String email, String address,
                                  String postCode, String emergencyContactName, String emergencyContact,
                                  String registrationDate, String gpSurgeryId) {
        String patientId = model.generatePatientId();
        Date reg = new Date();

        Patient patient = new Patient(patientId, firstName, lastName, dob, nhsNumber, gender,
                contact, email, address, postCode, emergencyContactName,
                emergencyContact, reg, gpSurgeryId);
        model.addPatient(patient);
        view.showSuccessMessage("Patient added successfully!");
        handleRefreshPatients();
    }

    private void handleEditPatient(String patientId, String firstName, String lastName, Date dob,
                                   String nhsNumber, String gender, String contact, String email,
                                   String address, String postCode, String emergencyContactName,
                                   String emergencyContact, String registrationDate, String gpSurgeryId) {
        Patient patient = model.getPatient(patientId);
        if (patient != null) {
            Date reg = patient.getRegistrationDate();
            Patient updatedPatient = new Patient(patientId, firstName, lastName, dob, nhsNumber, gender,
                    contact, email, address, postCode, emergencyContactName,
                    emergencyContact, reg, gpSurgeryId);
            model.updatePatient(updatedPatient);
            view.showSuccessMessage("Patient updated successfully!");
            handleRefreshPatients();
        }
    }

    private void handleDeletePatient(String patientId) {
        model.deletePatient(patientId);
        view.showSuccessMessage("Patient deleted successfully!");
        handleRefreshPatients();
    }

    // ========== Clinician Handlers ==========

    private void handleRefreshClinicians() {
        view.refreshCliniciansTable(model.getAllClinicians());
    }

    private void handleAddClinician(String firstName, String lastName, String title,
                                    String speciality,
                                    String gmcNumber, String contact, String email, String workplaceId,
                                    String workplaceType, EmploymentStatus employmentStatus, Date startDate) {
        String clinicianId = model.generateClinicianId();

        Clinician clinician = new Clinician(clinicianId, firstName, lastName, title, speciality,
                gmcNumber, contact, email, workplaceId, workplaceType,
                employmentStatus, startDate);
        model.addClinician(clinician);
        view.showSuccessMessage("Clinician added successfully!");
        handleRefreshClinicians();
    }

    private void handleEditClinician(String clinicianId, String firstName, String lastName, String title,
                                     String speciality,
                                     String gmcNumber, String contact, String email, String workplaceId,
                                     String workplaceType, EmploymentStatus employmentStatus, Date startDate) {
        Clinician clinician = model.getClinician(clinicianId);
        if (clinician != null) {
            Date start = clinician.getStartDate();
            Clinician updatedClinician = new Clinician(clinicianId, firstName, lastName, title, speciality,
                    gmcNumber, contact, email, workplaceId, workplaceType,
                    employmentStatus, startDate);
            model.updateClinician(updatedClinician);
            view.showSuccessMessage("Clinician updated successfully!");
            handleRefreshClinicians();
        }
    }

    private void handleDeleteClinician(String clinicianId) {
        model.deleteClinician(clinicianId);
        view.showSuccessMessage("Clinician deleted successfully!");
        handleRefreshClinicians();
    }

    // ========== Appointment Handlers ==========

    private void handleRefreshAppointments() {
        view.refreshAppointmentsTable(model.getAllAppointments(), model);
    }

    private void handleAddAppointment(String patientId, String clinicianId, String facilityId,
                                      Date appointmentDate, String appointmentTime, int durationMinutes,
                                      String appointmentType, String reasonForVisit, String notes,
                                      Date cDate, Date lmDate) {
        String appointmentId = model.generateAppointmentId();
        Date createdDate = new Date();
        Date lastModified = new Date();

        Appointment appointment = new Appointment(appointmentId, patientId, clinicianId, facilityId,
                appointmentDate, appointmentTime, durationMinutes,
                appointmentType, StatusType.SCHEDULED, reasonForVisit,
                notes, createdDate, lastModified);
        model.addAppointment(appointment);
        view.showSuccessMessage("Appointment added successfully!");
        handleRefreshAppointments();
    }

    private void handleUpdateAppointmentStatus(String appointmentId, String status) {
        Appointment appointment = model.getAppointment(appointmentId);
        if (appointment != null) {
            StatusType statusType = StatusType.fromCSV(status);
            appointment.setStatus(statusType);
            appointment.setLastModified(new Date());
            model.updateAppointment(appointment);
            view.showSuccessMessage("Appointment status updated!");
            handleRefreshAppointments();
        }
    }

    private void handleDeleteAppointment(String appointmentId) {
        model.deleteAppointment(appointmentId);
        view.showSuccessMessage("Appointment deleted successfully!");
        handleRefreshAppointments();
    }

    // ========== Prescription Handlers ==========

    private void handleRefreshPrescriptions() {
        view.refreshPrescriptionsTable(model.getAllPrescriptions(), model);
    }

    private void handleAddPrescription(String patientId, String clinicianId, String appointmentId,
                                       Date prescriptionDate, String medicationName, String dosage,
                                       String frequency, int durationDays, String quantity,
                                       String instruction, String pharmacyName) {
        String prescriptionId = model.generatePrescriptionId();
        Date issueDate = new Date();

        Prescription prescription = new Prescription(prescriptionId, patientId, clinicianId, appointmentId,
                prescriptionDate, medicationName, dosage, frequency,
                durationDays, quantity, instruction, pharmacyName,
                "Issued", issueDate, null);
        model.addPrescription(prescription);
        view.showSuccessMessage("Prescription added successfully!");
        handleRefreshPrescriptions();
    }

    private void handleGeneratePrescriptionDocument(String prescriptionId) {
        Prescription prescription = model.getPrescription(prescriptionId);
        if (prescription != null) {
            PrescriptionDocumentWriter.write(prescription);
            view.showSuccessMessage("Prescription document generated successfully!\nSaved to: output/prescriptions/");
        }
    }

    private void handleDeletePrescription(String prescriptionId) {
        model.deletePrescription(prescriptionId);
        view.showSuccessMessage("Prescription deleted successfully!");
        handleRefreshPrescriptions();
    }

    // ========== Referral Handlers ==========

    private void handleRefreshReferrals() {
        view.refreshReferralsTable(model.getAllReferrals(), model);
    }

    private void handleAddReferral(String patientId, String referringClinicianId, String referredToClinicianId,
                                   String referringFacilityId, String referringToFacilityId, Date referralDate,
                                   String urgencyLevel, String referralReason, String clinicalSummary,
                                   String requestedInvestigation, String appointmentId, String notes) {
        ReferralManager rm = model.getReferralManager();
        String referralId = rm.generateReferralId();
        Date createdDate = new Date();
        Date lastUpdated = new Date();

        Referral referral = new Referral(referralId, patientId, referringClinicianId, referredToClinicianId,
                referringFacilityId, referringToFacilityId, referralDate, urgencyLevel,
                referralReason, clinicalSummary, requestedInvestigation,
                ReferralStatus.NEW, appointmentId, notes, createdDate, lastUpdated);
        rm.addReferral(referral);
        view.showSuccessMessage("Referral added successfully!");
        handleRefreshReferrals();
    }

    private void handleUpdateReferralStatus(String referralId, String status) {
        ReferralManager rm = model.getReferralManager();
        ReferralStatus referralStatus = ReferralStatus.fromCSV(status);
        rm.updateReferralStatus(referralId, referralStatus);
        view.showSuccessMessage("Referral status updated!");
        handleRefreshReferrals();
    }

    private void handleGenerateReferralDocument(String referralId) {
        ReferralManager rm = model.getReferralManager();
        Referral referral = rm.getReferralById(referralId);
        if (referral != null) {
            rm.writeReferralEmailText(referral);
            view.showSuccessMessage("Referral document generated successfully!\nSaved to: output/referrals/");
        }
    }

    // ========== Facility Handlers ==========

    private void handleRefreshFacilities() {
        view.refreshFacilitiesTable(model.getAllFacilities());
    }

    private void handleAddFacility(String facilityName, String facilityType, String address, String postCode,
                                   String contact, String email, String openingHours, String managerName,
                                   String capacity, String specialitiesOffered) {
        String facilityId = model.generateFacilityId();

        Facility facility = new Facility(facilityId, facilityName, facilityType, address, postCode,
                contact, email, openingHours, managerName, capacity,
                specialitiesOffered);
        model.addFacility(facility);
        view.showSuccessMessage("Facility added successfully!");
        handleRefreshFacilities();
    }

    private void handleDeleteFacility(String facilityId) {
        model.deleteFacility(facilityId);
        view.showSuccessMessage("Facility deleted successfully!");
        handleRefreshFacilities();
    }

    // ========== Staff Handlers ==========

    private void handleRefreshStaff() {
        view.refreshStaffTable(model.getAllStaff());
    }

    private void handleAddStaff(String firstName, String lastName, String role, String department,
                                String facilityId, String contact, String email, EmploymentStatus employmentStatus,
                                Date startDate, String lineManager, String accessLevel) {
        String staffId = model.generateStaffId();

        Staff staff = new Staff(staffId, firstName, lastName, role, department, facilityId,
                contact, email, employmentStatus, startDate, lineManager, accessLevel);
        model.addStaff(staff);
        view.showSuccessMessage("Staff member added successfully!");
        handleRefreshStaff();
    }

    private void handleDeleteStaff(String staffId) {
        model.deleteStaff(staffId);
        view.showSuccessMessage("Staff member deleted successfully!");
        handleRefreshStaff();
    }

    // ========== Data Access Methods ==========

    public ArrayList<Patient> getAllPatients() {
        return model.getAllPatients();
    }

    public ArrayList<Clinician> getAllClinicians() {
        return model.getAllClinicians();
    }

    public ArrayList<Facility> getAllFacilities() {
        return model.getAllFacilities();
    }

    public ArrayList<Appointment> getAllAppointments() {
        return model.getAllAppointments();
    }

    public Patient getPatient(String patientId) {
        return model.getPatient(patientId);
    }

    public Clinician getClinician(String clinicianId) {
        return model.getClinician(clinicianId);
    }

    public Facility getFacility(String facilityId) {
        return model.getFacility(facilityId);
    }

    // ========== Save Data ==========

    private void handleSaveData() {
        model.saveAllData();
    }
}
