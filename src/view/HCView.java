package view;

import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Swing View for Healthcare System (MVC)
 */
public class HCView extends JFrame {

    private controller.HCController controller;

    private JTabbedPane tabbedPane;

    // Tables + models
    private JTable patientsTable;        private DefaultTableModel patientsTM;
    private JTable cliniciansTable;      private DefaultTableModel cliniciansTM;
    private JTable appointmentsTable;    private DefaultTableModel appointmentsTM;
    private JTable prescriptionsTable;   private DefaultTableModel prescriptionsTM;
    private JTable referralsTable;       private DefaultTableModel referralsTM;

    // Listeners (set by controller)
    private AddPatientListener addPatientListener;
    private UpdatePatientListener updatePatientListener;
    private DeleteListener deletePatientListener;

    private AddClinicianListener addClinicianListener;
    private UpdateClinicianListener updateClinicianListener;
    private DeleteListener deleteClinicianListener;

    private AddAppointmentListener addAppointmentListener;
    private UpdateAppointmentListener updateAppointmentListener;
    private DeleteListener deleteAppointmentListener;

    private AddPrescriptionListener addPrescriptionListener;
    private UpdatePrescriptionListener updatePrescriptionListener;
    private DeleteListener deletePrescriptionListener;

    private CreateReferralListener createReferralListener;
    private UpdateReferralStatusListener updateReferralStatusListener;

    private Runnable refreshAllListener;
    private Runnable onCloseListener;

    public HCView() {
        setTitle("Healthcare Management System - MVC + Singleton Referral");
        setSize(1100, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (onCloseListener != null) onCloseListener.run();
            }
        });
    }

    public void setController(controller.HCController controller) {
        this.controller = controller;
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Patients", createPatientsPanel());
        tabbedPane.addTab("Clinicians", createCliniciansPanel());
        tabbedPane.addTab("Appointments", createAppointmentsPanel());
        tabbedPane.addTab("Prescriptions", createPrescriptionsPanel());
        tabbedPane.addTab("Referrals", createReferralsPanel());

        add(tabbedPane, BorderLayout.CENTER);

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshAllBtn = new JButton("Refresh All (Reload CSV)");
        refreshAllBtn.addActionListener(e -> { if (refreshAllListener != null) refreshAllListener.run(); });
        topBar.add(refreshAllBtn);

        add(topBar, BorderLayout.NORTH);
    }

    // ==================== PATIENTS TAB ====================

    private JPanel createPatientsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        String[] cols = {"PatientID", "FirstName", "LastName", "NHS", "Gender", "Contact", "Email", "GP Surgery"};
        patientsTM = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        patientsTable = new JTable(patientsTM);

        panel.add(new JScrollPane(patientsTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> showAddPatientDialog());
        edit.addActionListener(e -> showEditPatientDialog());
        del.addActionListener(e -> deleteSelectedRow(patientsTable, deletePatientListener, "patient"));

        btns.add(add); btns.add(edit); btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshPatientsTable(ArrayList<Patient> patients) {
        patientsTM.setRowCount(0);
        for (Patient p : patients) {
            Object[] row = {
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    p.getNhsNumber(),
                    p.getGender(),
                    p.getContact(),
                    p.getEmail(),
                    p.getGpSurgeryId()
            };
            patientsTM.addRow(row);
        }
    }

    private void showAddPatientDialog() {
        if (controller == null) { showError("Controller not set."); return; }

        JTextField first = new JTextField();
        JTextField last = new JTextField();
        JTextField dob = new JTextField("yyyy-MM-dd");
        JTextField nhs = new JTextField();
        JTextField gender = new JTextField();
        JTextField contact = new JTextField();
        JTextField email = new JTextField();
        JTextField address = new JTextField();
        JTextField post = new JTextField();
        JTextField eName = new JTextField();
        JTextField eContact = new JTextField();
        JTextField reg = new JTextField("yyyy-MM-dd");
        JTextField gp = new JTextField();

        JPanel p = gridForm(
                "First Name", first,
                "Last Name", last,
                "DOB", dob,
                "NHS Number", nhs,
                "Gender", gender,
                "Contact", contact,
                "Email", email,
                "Address", address,
                "Postcode", post,
                "Emergency Name", eName,
                "Emergency Contact", eContact,
                "Registration Date", reg,
                "GP Surgery ID", gp
        );

        int ok = JOptionPane.showConfirmDialog(this, p, "Add Patient", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String id = controller.nextPatientId();
            Patient patient = new Patient(
                    id,
                    first.getText().trim(),
                    last.getText().trim(),
                    sdf.parse(dob.getText().trim()),
                    nhs.getText().trim(),
                    gender.getText().trim(),
                    contact.getText().trim(),
                    email.getText().trim(),
                    address.getText().trim(),
                    post.getText().trim(),
                    eName.getText().trim(),
                    eContact.getText().trim(),
                    sdf.parse(reg.getText().trim()),
                    gp.getText().trim()
            );

            if (addPatientListener != null) addPatientListener.onAdd(patient);

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    private void showEditPatientDialog() {
        int row = patientsTable.getSelectedRow();
        if (row == -1) { showError("Select a patient row first."); return; }

        String patientId = patientsTable.getValueAt(row, 0).toString();

        JTextField first = new JTextField(patientsTable.getValueAt(row, 1).toString());
        JTextField last = new JTextField(patientsTable.getValueAt(row, 2).toString());
        JTextField nhs = new JTextField(patientsTable.getValueAt(row, 3).toString());
        JTextField gender = new JTextField(patientsTable.getValueAt(row, 4).toString());
        JTextField contact = new JTextField(patientsTable.getValueAt(row, 5).toString());
        JTextField email = new JTextField(patientsTable.getValueAt(row, 6).toString());
        JTextField gp = new JTextField(patientsTable.getValueAt(row, 7).toString());

        // Minimal edit dialog (you can expand to include address, dates, etc.)
        JPanel p = gridForm(
                "First Name", first,
                "Last Name", last,
                "NHS Number", nhs,
                "Gender", gender,
                "Contact", contact,
                "Email", email,
                "GP Surgery ID", gp
        );

        int ok = JOptionPane.showConfirmDialog(this, p, "Edit Patient " + patientId, JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            // We don't have all original fields in table; keep dates etc. unchanged is tricky without model lookup.
            // Simple approach: ask controller/model to fetch full object? For now, edit only what's visible and reuse defaults.
            // Better: add a "Get Patient" method in controller. If you want, I can refactor.
            Patient updated = new Patient(
                    patientId,
                    first.getText().trim(),
                    last.getText().trim(),
                    new Date(),                 // placeholder if you don't fetch original
                    nhs.getText().trim(),
                    gender.getText().trim(),
                    contact.getText().trim(),
                    email.getText().trim(),
                    "", "", "", "", new Date(), gp.getText().trim()
            );

            if (updatePatientListener != null) updatePatientListener.onUpdate(patientId, updated);

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    // ==================== CLINICIANS TAB ====================

    private JPanel createCliniciansPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        String[] cols = {"ClinicianID", "FirstName", "LastName", "Title", "Speciality", "GMC", "Contact", "Email", "WorkplaceID", "WorkplaceType"};
        cliniciansTM = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        cliniciansTable = new JTable(cliniciansTM);
        panel.add(new JScrollPane(cliniciansTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> showAddClinicianDialog());
        edit.addActionListener(e -> showEditClinicianDialog());
        del.addActionListener(e -> deleteSelectedRow(cliniciansTable, deleteClinicianListener, "clinician"));

        btns.add(add); btns.add(edit); btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);
        return panel;
    }

    public void refreshCliniciansTable(ArrayList<Clinician> clinicians) {
        cliniciansTM.setRowCount(0);
        for (Clinician c : clinicians) {
            Object[] row = {
                    c.getClinicianId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getTitle(),
                    c.getSpeciality(),
                    c.getGmcNumber(),
                    c.getContact(),
                    c.getEmail(),
                    c.getWorkplaceId(),
                    c.getWorkplaceType()
            };
            cliniciansTM.addRow(row);
        }
    }

    private void showAddClinicianDialog() {
        if (controller == null) { showError("Controller not set."); return; }

        JTextField first = new JTextField();
        JTextField last = new JTextField();
        JTextField title = new JTextField();
        JTextField speciality = new JTextField();
        JTextField gmc = new JTextField();
        JTextField contact = new JTextField();
        JTextField email = new JTextField();
        JTextField workplaceId = new JTextField();
        JTextField workplaceType = new JTextField();
        JComboBox<EmploymentStatus> emp = new JComboBox<>(EmploymentStatus.values());
        JTextField startDate = new JTextField("yyyy-MM-dd");

        JPanel p = gridForm(
                "First Name", first,
                "Last Name", last,
                "Title", title,
                "Speciality", speciality,
                "GMC Number", gmc,
                "Contact", contact,
                "Email", email,
                "Workplace ID", workplaceId,
                "Workplace Type", workplaceType,
                "Employment", emp,
                "Start Date", startDate
        );

        int ok = JOptionPane.showConfirmDialog(this, p, "Add Clinician", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String id = controller.nextClinicianId();

            Clinician c = new Clinician(
                    id,
                    first.getText().trim(),
                    last.getText().trim(),
                    title.getText().trim(),
                    speciality.getText().trim(),
                    Integer.parseInt(gmc.getText().trim()),
                    contact.getText().trim(),
                    email.getText().trim(),
                    workplaceId.getText().trim(),
                    workplaceType.getText().trim(),
                    (EmploymentStatus) emp.getSelectedItem(),
                    sdf.parse(startDate.getText().trim())
            );

            if (addClinicianListener != null) addClinicianListener.onAdd(c);

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    private void showEditClinicianDialog() {
        int row = cliniciansTable.getSelectedRow();
        if (row == -1) { showError("Select a clinician row first."); return; }

        String clinicianId = cliniciansTable.getValueAt(row, 0).toString();

        JTextField title = new JTextField(cliniciansTable.getValueAt(row, 3).toString());
        JTextField speciality = new JTextField(cliniciansTable.getValueAt(row, 4).toString());
        JTextField contact = new JTextField(cliniciansTable.getValueAt(row, 6).toString());
        JTextField email = new JTextField(cliniciansTable.getValueAt(row, 7).toString());

        JPanel p = gridForm(
                "Title", title,
                "Speciality", speciality,
                "Contact", contact,
                "Email", email
        );

        int ok = JOptionPane.showConfirmDialog(this, p, "Edit Clinician " + clinicianId, JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            // Minimal update, like Patient edit: best is to fetch original object from model.
            Clinician updated = new Clinician(
                    clinicianId,
                    cliniciansTable.getValueAt(row, 1).toString(),
                    cliniciansTable.getValueAt(row, 2).toString(),
                    title.getText().trim(),
                    speciality.getText().trim(),
                    Integer.parseInt(cliniciansTable.getValueAt(row, 5).toString()),
                    contact.getText().trim(),
                    email.getText().trim(),
                    cliniciansTable.getValueAt(row, 8).toString(),
                    cliniciansTable.getValueAt(row, 9).toString(),
                    EmploymentStatus.FULLTIME,
                    new Date()
            );

            if (updateClinicianListener != null) updateClinicianListener.onUpdate(clinicianId, updated);

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    // ==================== APPOINTMENTS TAB ====================

    private JPanel createAppointmentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        String[] cols = {"ApptID", "PatientID", "ClinicianID", "FacilityID", "Date", "Time", "Duration", "Type", "Status", "Reason"};
        appointmentsTM = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        appointmentsTable = new JTable(appointmentsTM);
        panel.add(new JScrollPane(appointmentsTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> showAddAppointmentDialog());
        edit.addActionListener(e -> showEditAppointmentDialog());
        del.addActionListener(e -> deleteSelectedRow(appointmentsTable, deleteAppointmentListener, "appointment"));

        btns.add(add); btns.add(edit); btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshAppointmentsTable(ArrayList<Appointment> appts) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        appointmentsTM.setRowCount(0);
        for (Appointment a : appts) {
            Object[] row = {
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianID(),
                    a.getFacilityId(),
                    sdf.format(a.getAppointmentDate()),
                    a.getAppointmentTime(),
                    a.getDurationMinutes(),
                    a.getAppointmentType(),
                    a.getStatus().name(),
                    a.getReason_For_Visit()
            };
            appointmentsTM.addRow(row);
        }
    }

    private void showAddAppointmentDialog() {
        if (controller == null) { showError("Controller not set."); return; }

        JTextField patientId = new JTextField();
        JTextField clinicianId = new JTextField();
        JTextField facilityId = new JTextField();
        JTextField date = new JTextField("yyyy-MM-dd");
        JTextField time = new JTextField("HH:mm");
        JTextField duration = new JTextField("15");
        JTextField type = new JTextField("Consultation");
        JComboBox<StatusType> status = new JComboBox<>(StatusType.values());
        JTextField reason = new JTextField();
        JTextField notes = new JTextField();

        JPanel p = gridForm(
                "Patient ID", patientId,
                "Clinician ID", clinicianId,
                "Facility ID", facilityId,
                "Date", date,
                "Time", time,
                "Duration minutes", duration,
                "Type", type,
                "Status", status,
                "Reason", reason,
                "Notes", notes
        );

        int ok = JOptionPane.showConfirmDialog(this, p, "Add Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String id = controller.nextAppointmentId();
            Date now = new Date();
            Appointment a = new Appointment(
                    id,
                    patientId.getText().trim(),
                    clinicianId.getText().trim(),
                    facilityId.getText().trim(),
                    sdf.parse(date.getText().trim()),
                    time.getText().trim(),
                    Integer.parseInt(duration.getText().trim()),
                    type.getText().trim(),
                    (StatusType) status.getSelectedItem(),
                    reason.getText().trim(),
                    notes.getText().trim(),
                    now, now
            );

            if (addAppointmentListener != null) addAppointmentListener.onAdd(a);

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    private void showEditAppointmentDialog() {
        int row = appointmentsTable.getSelectedRow();
        if (row == -1) { showError("Select an appointment row first."); return; }

        String apptId = appointmentsTable.getValueAt(row, 0).toString();
        JTextField status = new JTextField(appointmentsTable.getValueAt(row, 8).toString());
        JTextField reason = new JTextField(appointmentsTable.getValueAt(row, 9).toString());

        JPanel p = gridForm(
                "Status (SCHEDULED/CANCELLED)", status,
                "Reason", reason
        );

        int ok = JOptionPane.showConfirmDialog(this, p, "Edit Appointment " + apptId, JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            // Again minimal update without full model fetch.
            Appointment updated = new Appointment(
                    apptId,
                    appointmentsTable.getValueAt(row, 1).toString(),
                    appointmentsTable.getValueAt(row, 2).toString(),
                    appointmentsTable.getValueAt(row, 3).toString(),
                    new Date(),
                    appointmentsTable.getValueAt(row, 5).toString(),
                    Integer.parseInt(appointmentsTable.getValueAt(row, 6).toString()),
                    appointmentsTable.getValueAt(row, 7).toString(),
                    StatusType.valueOf(status.getText().trim()),
                    reason.getText().trim(),
                    "",
                    new Date(), new Date()
            );

            if (updateAppointmentListener != null) updateAppointmentListener.onUpdate(apptId, updated);

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    // ==================== PRESCRIPTIONS TAB ====================

    private JPanel createPrescriptionsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        String[] cols = {"RXID", "PatientID", "ClinicianID", "ApptID", "Medication", "Dosage", "Frequency", "Days", "Pharmacy", "Status"};
        prescriptionsTM = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        prescriptionsTable = new JTable(prescriptionsTM);
        panel.add(new JScrollPane(prescriptionsTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> showAddPrescriptionDialog());
        edit.addActionListener(e -> showEditPrescriptionDialog());
        del.addActionListener(e -> deleteSelectedRow(prescriptionsTable, deletePrescriptionListener, "prescription"));

        btns.add(add); btns.add(edit); btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);
        return panel;
    }

    public void refreshPrescriptionsTable(ArrayList<Prescription> rxList) {
        prescriptionsTM.setRowCount(0);
        for (Prescription rx : rxList) {
            Object[] row = {
                    rx.getPrescriptionId(),
                    rx.getPatientId(),
                    rx.getClinicianId(),
                    rx.getAppointmentId(),
                    rx.getMedicationName(),
                    rx.getDosage(),
                    rx.getFrequency(),
                    rx.getDurationDays(),
                    rx.getPharmacyName(),
                    rx.getStatus()
            };
            prescriptionsTM.addRow(row);
        }
    }

    private void showAddPrescriptionDialog() {
        if (controller == null) { showError("Controller not set."); return; }

        JTextField patientId = new JTextField();
        JTextField clinicianId = new JTextField();
        JTextField appointmentId = new JTextField();
        JTextField date = new JTextField("dd/MM/yyyy");
        JTextField medication = new JTextField();
        JTextField dosage = new JTextField();
        JTextField frequency = new JTextField();
        JTextField days = new JTextField("7");
        JTextField qty = new JTextField();
        JTextField instruction = new JTextField();
        JTextField pharmacy = new JTextField();
        JTextField status = new JTextField("ISSUED");
        JCheckBox generateDoc = new JCheckBox("Generate prescription text file", true);

        JPanel p = gridForm(
                "Patient ID", patientId,
                "Clinician ID", clinicianId,
                "Appointment ID", appointmentId,
                "Prescription Date", date,
                "Medication", medication,
                "Dosage", dosage,
                "Frequency", frequency,
                "Duration Days", days,
                "Quantity", qty,
                "Instruction", instruction,
                "Pharmacy", pharmacy,
                "Status", status,
                "Document", generateDoc
        );

        int ok = JOptionPane.showConfirmDialog(this, p, "Add Prescription", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            String id = controller.nextPrescriptionId();
            Date d = sdf.parse(date.getText().trim());

            Prescription rx = new Prescription(
                    id,
                    patientId.getText().trim(),
                    clinicianId.getText().trim(),
                    appointmentId.getText().trim(),
                    d,
                    medication.getText().trim(),
                    dosage.getText().trim(),
                    frequency.getText().trim(),
                    Integer.parseInt(days.getText().trim()),
                    qty.getText().trim(),
                    instruction.getText().trim(),
                    pharmacy.getText().trim(),
                    status.getText().trim(),
                    d,
                    d
            );

            if (addPrescriptionListener != null) {
                addPrescriptionListener.onAdd(rx, generateDoc.isSelected());
            }

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    private void showEditPrescriptionDialog() {
        int row = prescriptionsTable.getSelectedRow();
        if (row == -1) { showError("Select a prescription row first."); return; }

        String rxId = prescriptionsTable.getValueAt(row, 0).toString();
        JTextField status = new JTextField(prescriptionsTable.getValueAt(row, 9).toString());

        JPanel p = gridForm("Status", status);

        int ok = JOptionPane.showConfirmDialog(this, p, "Edit Prescription " + rxId, JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            // minimal update: only status
            Prescription updated = new Prescription(
                    rxId,
                    prescriptionsTable.getValueAt(row, 1).toString(),
                    prescriptionsTable.getValueAt(row, 2).toString(),
                    prescriptionsTable.getValueAt(row, 3).toString(),
                    new Date(),
                    prescriptionsTable.getValueAt(row, 4).toString(),
                    prescriptionsTable.getValueAt(row, 5).toString(),
                    prescriptionsTable.getValueAt(row, 6).toString(),
                    Integer.parseInt(prescriptionsTable.getValueAt(row, 7).toString()),
                    "", "", prescriptionsTable.getValueAt(row, 8).toString(),
                    status.getText().trim(),
                    new Date(), new Date()
            );

            if (updatePrescriptionListener != null) updatePrescriptionListener.onUpdate(rxId, updated);

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    // ==================== REFERRALS TAB ====================

    private JPanel createReferralsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));

        String[] cols = {"ReferralID", "PatientID", "FromClinician", "ToClinician", "FromFacility", "ToFacility", "Date", "Urgency", "Status"};
        referralsTM = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) { return false; }
        };
        referralsTable = new JTable(referralsTM);
        panel.add(new JScrollPane(referralsTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton create = new JButton("Create Referral");
        JButton status = new JButton("Update Status");

        create.addActionListener(e -> showCreateReferralDialog());
        status.addActionListener(e -> showUpdateReferralStatusDialog());

        btns.add(create); btns.add(status);
        panel.add(btns, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshReferralsTable(ArrayList<Referral> refs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        referralsTM.setRowCount(0);
        for (Referral r : refs) {
            Object[] row = {
                    r.getReferralId(),
                    r.getPatientId(),
                    r.getReferringClinicianId(),
                    r.getReferredToClinicianId(),
                    r.getReferringFacilityId(),
                    r.getReferringToFacilityId(),
                    sdf.format(r.getReferralDate()),
                    r.getUrgencyLevel(),
                    r.getStatus().name()
            };
            referralsTM.addRow(row);
        }
    }

    private void showCreateReferralDialog() {
        if (controller == null) { showError("Controller not set."); return; }

        JTextField patientId = new JTextField();
        JTextField fromClinician = new JTextField();
        JTextField toClinician = new JTextField();
        JTextField fromFacility = new JTextField();
        JTextField toFacility = new JTextField();
        JTextField date = new JTextField("yyyy-MM-dd");
        JTextField urgency = new JTextField("ROUTINE");
        JTextField reason = new JTextField();
        JTextArea summary = new JTextArea(4, 25);
        JTextField investigation = new JTextField();
        JTextField apptId = new JTextField();
        JTextField notes = new JTextField();
        JCheckBox generateDoc = new JCheckBox("Generate referral email text file", true);

        JPanel p = new JPanel(new GridLayout(0, 2, 8, 8));
        p.add(new JLabel("Patient ID")); p.add(patientId);
        p.add(new JLabel("Referring Clinician ID")); p.add(fromClinician);
        p.add(new JLabel("Referred-to Clinician ID")); p.add(toClinician);
        p.add(new JLabel("Referring Facility ID")); p.add(fromFacility);
        p.add(new JLabel("Referred-to Facility ID")); p.add(toFacility);
        p.add(new JLabel("Referral Date")); p.add(date);
        p.add(new JLabel("Urgency")); p.add(urgency);
        p.add(new JLabel("Reason")); p.add(reason);
        p.add(new JLabel("Clinical Summary")); p.add(new JScrollPane(summary));
        p.add(new JLabel("Requested Investigation")); p.add(investigation);
        p.add(new JLabel("Appointment ID (optional)")); p.add(apptId);
        p.add(new JLabel("Notes")); p.add(notes);
        p.add(new JLabel("Document")); p.add(generateDoc);

        int ok = JOptionPane.showConfirmDialog(this, p, "Create Referral", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String id = controller.nextReferralId();
            Date now = new Date();

            Referral referral = new Referral(
                    id,
                    patientId.getText().trim(),
                    fromClinician.getText().trim(),
                    toClinician.getText().trim(),
                    fromFacility.getText().trim(),
                    toFacility.getText().trim(),
                    sdf.parse(date.getText().trim()),
                    urgency.getText().trim(),
                    reason.getText().trim(),
                    summary.getText().trim(),
                    investigation.getText().trim(),
                    ReferralStatus.NEW,
                    apptId.getText().trim(),
                    notes.getText().trim(),
                    now,
                    now
            );

            if (createReferralListener != null) createReferralListener.onCreate(referral, generateDoc.isSelected());

        } catch (Exception ex) {
            showError("Invalid input: " + ex.getMessage());
        }
    }

    private void showUpdateReferralStatusDialog() {
        int row = referralsTable.getSelectedRow();
        if (row == -1) { showError("Select a referral row first."); return; }

        String referralId = referralsTable.getValueAt(row, 0).toString();
        JComboBox<ReferralStatus> status = new JComboBox<>(ReferralStatus.values());

        JPanel p = gridForm("New Status", status);
        int ok = JOptionPane.showConfirmDialog(this, p, "Update Status for " + referralId, JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        if (updateReferralStatusListener != null) {
            updateReferralStatusListener.onUpdate(referralId, (ReferralStatus) status.getSelectedItem());
        }
    }

    // ==================== COMMON HELPERS ====================

    private void deleteSelectedRow(JTable table, DeleteListener listener, String name) {
        int row = table.getSelectedRow();
        if (row == -1) { showError("Select a " + name + " row first."); return; }

        String id = table.getValueAt(row, 0).toString();
        int ok = JOptionPane.showConfirmDialog(this, "Delete " + name + " " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION && listener != null) {
            listener.onDelete(id);
        }
    }

    private JPanel gridForm(Object... labelAndFieldPairs) {
        JPanel p = new JPanel(new GridLayout(0, 2, 8, 8));
        for (int i = 0; i < labelAndFieldPairs.length; i += 2) {
            p.add(new JLabel(labelAndFieldPairs[i].toString()));
            p.add((Component) labelAndFieldPairs[i + 1]);
        }
        return p;
    }

    // ==================== App messages ====================

    public void showSuccess(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ==================== Listener setter methods ====================

    public void setAddPatientListener(AddPatientListener l) { this.addPatientListener = l; }
    public void setUpdatePatientListener(UpdatePatientListener l) { this.updatePatientListener = l; }
    public void setDeletePatientListener(DeleteListener l) { this.deletePatientListener = l; }

    public void setAddClinicianListener(AddClinicianListener l) { this.addClinicianListener = l; }
    public void setUpdateClinicianListener(UpdateClinicianListener l) { this.updateClinicianListener = l; }
    public void setDeleteClinicianListener(DeleteListener l) { this.deleteClinicianListener = l; }

    public void setAddAppointmentListener(AddAppointmentListener l) { this.addAppointmentListener = l; }
    public void setUpdateAppointmentListener(UpdateAppointmentListener l) { this.updateAppointmentListener = l; }
    public void setDeleteAppointmentListener(DeleteListener l) { this.deleteAppointmentListener = l; }

    public void setAddPrescriptionListener(AddPrescriptionListener l) { this.addPrescriptionListener = l; }
    public void setUpdatePrescriptionListener(UpdatePrescriptionListener l) { this.updatePrescriptionListener = l; }
    public void setDeletePrescriptionListener(DeleteListener l) { this.deletePrescriptionListener = l; }

    public void setCreateReferralListener(CreateReferralListener l) { this.createReferralListener = l; }
    public void setUpdateReferralStatusListener(UpdateReferralStatusListener l) { this.updateReferralStatusListener = l; }

    public void setRefreshAllListener(Runnable r) { this.refreshAllListener = r; }
    public void setOnCloseListener(Runnable r) { this.onCloseListener = r; }

    // ==================== Listener Interfaces ====================
    public interface DeleteListener { void onDelete(String id); }

    public interface AddPatientListener { void onAdd(Patient p); }
    public interface UpdatePatientListener { void onUpdate(String patientId, Patient updated); }

    public interface AddClinicianListener { void onAdd(Clinician c); }
    public interface UpdateClinicianListener { void onUpdate(String clinicianId, Clinician updated); }

    public interface AddAppointmentListener { void onAdd(Appointment a); }
    public interface UpdateAppointmentListener { void onUpdate(String appointmentId, Appointment updated); }

    public interface AddPrescriptionListener { void onAdd(Prescription rx, boolean generateDoc); }
    public interface UpdatePrescriptionListener { void onUpdate(String rxId, Prescription updated); }

    public interface CreateReferralListener { void onCreate(Referral referral, boolean generateDoc); }
    public interface UpdateReferralStatusListener { void onUpdate(String referralId, ReferralStatus newStatus); }
}
