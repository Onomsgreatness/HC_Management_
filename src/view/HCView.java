/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package view;

import controller.*;
import model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HCView extends JFrame {


    private HCController controller;

    private Runnable onCloseListener;
    private Runnable refreshPatientsListener;
    private Runnable refreshCliniciansListener;
    private Runnable refreshAppointmentsListener;
    private Runnable refreshPrescriptionsListener;

    private PatientListener addPatientListener;
    private EditPatientListener editPatientListener;
    private DeleteListener deletePatientListener;

    private ClinicianListener addClinicianListener;
    private EditClinicianListener editClinicianListener;
    private DeleteListener deleteClinicianListener;

    private AppointmentListener addAppointmentListener;
    private EditAppointmentListener editAppointmentListener;
    private UpdateStatusListener updateAppointmentStatusListener;
    private DeleteListener deleteAppointmentListener;

    private PrescriptionListener addPrescriptionListener;
    private GenerateDocumentListener generatePrescriptionDocumentListener;
    private DeleteListener deletePrescriptionListener;

    private Runnable refreshReferralsListener;
    private Runnable refreshFacilitiesListener;
    private Runnable refreshStaffListener;
    private ReferralListener addReferralListener;
    private UpdateStatusListener updateReferralStatusListener;
    private GenerateDocumentListener generateReferralDocumentListener;
    private FacilityListener addFacilityListener;
    private DeleteListener deleteFacilityListener;
    private StaffListener addStaffListener;
    private DeleteListener deleteStaffListener;

    // ===== UI =====
    private final JTabbedPane tabs = new JTabbedPane();

    private DefaultTableModel patientsTM;
    private JTable patientsTable;

    private DefaultTableModel cliniciansTM;
    private JTable cliniciansTable;

    private DefaultTableModel appointmentsTM;
    private JTable appointmentsTable;

    private DefaultTableModel prescriptionsTM;
    private JTable prescriptionsTable;

    public HCView() {
        setTitle("Healthcare System - MVC");
        setSize(1100, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        buildUI();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (onCloseListener != null) onCloseListener.run();
            }
        });
    }

    public void setController(HCController controller) {
        this.controller = controller;
    }

    // ========================= UI BUILD =========================

    private void buildUI() {
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton refreshCurrent = new JButton("Refresh Current Tab");
        refreshCurrent.addActionListener(e -> refreshCurrentTab());
        top.add(refreshCurrent);

        add(top, BorderLayout.NORTH);

        tabs.addTab("Patients", buildPatientsTab());
        tabs.addTab("Clinicians", buildCliniciansTab());
        tabs.addTab("Appointments", buildAppointmentsTab());
        tabs.addTab("Prescriptions", buildPrescriptionsTab());

        add(tabs, BorderLayout.CENTER);
    }

    private void refreshCurrentTab() {
        String t = tabs.getTitleAt(tabs.getSelectedIndex());
        switch (t) {
            case "Patients" -> { if (refreshPatientsListener != null) refreshPatientsListener.run(); }
            case "Clinicians" -> { if (refreshCliniciansListener != null) refreshCliniciansListener.run(); }
            case "Appointments" -> { if (refreshAppointmentsListener != null) refreshAppointmentsListener.run(); }
            case "Prescriptions" -> { if (refreshPrescriptionsListener != null) refreshPrescriptionsListener.run(); }
        }
    }

    // ========================= PATIENTS TAB =========================

    private JPanel buildPatientsTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        patientsTM = new DefaultTableModel(new String[]{
                "PatientID","FirstName","LastName","DoB","NHS","Gender","Contact","Email","Address","Postcode",
                "EmergencyContact_Name","EmergencyContact","RegistrationDate","GP Surgery"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        patientsTable = new JTable(patientsTM);

        panel.add(new JScrollPane(patientsTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> showAddPatientDialog());
        edit.addActionListener(e -> showEditPatientDialog());
        del.addActionListener(e -> deleteSelected(patientsTable, deletePatientListener, "patient"));

        btns.add(add); btns.add(edit); btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshPatientsTable(ArrayList<Patient> patients) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        patientsTM.setRowCount(0);
        for (Patient p : patients) {
            String dob = (p.getDoB() == null) ? "" : sdf.format(p.getDoB());
            String reg = (p.getRegistrationDate() == null) ? ""  : sdf.format(p.getRegistrationDate());
            patientsTM.addRow(new Object[]{
                    p.getPatientId(),
                    p.getFirstName(),
                    p.getLastName(),
                    dob,
                    p.getNhsNumber(),
                    p.getGender(),
                    p.getContact(),
                    p.getEmail(),
                    p.getAddress(),
                    p.getPostCode(),
                    p.getEmergencyContactName(),
                    p.getEmergencyContact(),
                    reg,
                    p.getGpSurgeryId()
            });
        }
    }

    private void showAddPatientDialog() {
        if (addPatientListener == null) { showErrorMessage("Patient add handler not set."); return; }

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
        JTextField regDate = new JTextField();
        JTextField gp = new JTextField();

        JPanel form = formGrid(
                "First Name", first,
                "Last Name", last,
                "DOB (yyyy-MM-dd)", dob,
                "NHS Number", nhs,
                "Gender", gender,
                "Contact", contact,
                "Email", email,
                "Address", address,
                "Postcode", post,
                "Emergency Name", eName,
                "Emergency Contact", eContact,
                "Registration Date", regDate,
                "GP Surgery ID", gp
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Add Patient", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            Date d = parseDate(dob.getText().trim());
            addPatientListener.onAddPatient(
                    first.getText().trim(),
                    last.getText().trim(),
                    d,
                    nhs.getText().trim(),
                    gender.getText().trim(),
                    contact.getText().trim(),
                    email.getText().trim(),
                    address.getText().trim(),
                    post.getText().trim(),
                    eName.getText().trim(),
                    eContact.getText().trim(),
                    regDate.getText().trim(),
                    gp.getText().trim()
            );
        } catch (Exception ex) {
            showErrorMessage("Invalid date. Use yyyy-MM-dd");
        }
    }

    private void showEditPatientDialog() {
        if (editPatientListener == null) { showErrorMessage("Patient edit handler not set."); return; }

        int row = patientsTable.getSelectedRow();
        if (row < 0) { showErrorMessage("Select a patient row first."); return; }

        String patientId = patientsTable.getValueAt(row, 0).toString();

        JTextField first = new JTextField(patientsTable.getValueAt(row, 1).toString());
        JTextField last = new JTextField(patientsTable.getValueAt(row, 2).toString());

        JTextField dob = new JTextField(String.valueOf(patientsTable.getValueAt(row, 3)));

        JTextField nhs = new JTextField(patientsTable.getValueAt(row, 4).toString());
        JTextField gender = new JTextField(patientsTable.getValueAt(row, 5).toString());
        JTextField contact = new JTextField(patientsTable.getValueAt(row, 6).toString());
        JTextField email = new JTextField(patientsTable.getValueAt(row, 7).toString());

        JTextField address = new JTextField(patientsTable.getValueAt(row, 8).toString());
        JTextField post = new JTextField(patientsTable.getValueAt(row, 9).toString());

        JTextField eName = new JTextField(patientsTable.getValueAt(row, 10).toString());
        JTextField eContact = new JTextField(patientsTable.getValueAt(row, 11).toString());

        //This uses read-only because registration date usually shouldn’t change
        JTextField regDate = new JTextField(patientsTable.getValueAt(row, 12).toString());
        regDate.setEditable(false);

        JTextField gp = new JTextField(patientsTable.getValueAt(row, 13).toString());

        JPanel form = formGrid(
                "Patient ID", new JLabel(patientId),
                "First Name", first,
                "Last Name", last,
                "DOB (yyyy-MM-dd)", dob,
                "NHS Number", nhs,
                "Gender", gender,
                "Contact", contact,
                "Email", email,
                "Address", address,
                "Postcode", post,
                "Emergency Name", eName,
                "Emergency Contact", eContact,
                "Registration Date", regDate,
                "GP Surgery ID", gp
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Edit Patient", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            Date d = parseDate(dob.getText().trim());

            editPatientListener.onEditPatient(
                    patientId,
                    first.getText().trim(),
                    last.getText().trim(),
                    d,
                    nhs.getText().trim(),
                    gender.getText().trim(),
                    contact.getText().trim(),
                    email.getText().trim(),
                    address.getText().trim(),
                    post.getText().trim(),
                    eName.getText().trim(),
                    eContact.getText().trim(),
                    regDate.getText().trim(),
                    gp.getText().trim()
            );
        } catch (Exception ex) {
            showErrorMessage("Invalid date. Use yyyy-MM-dd");
        }
    }


    // ========================= CLINICIANS TAB =========================

    private JPanel buildCliniciansTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        cliniciansTM = new DefaultTableModel(new String[]{
                "ClinicianID","FirstName","LastName","Title","Speciality","GMC","Contact","Email","WorkplaceID",
                "WorkplaceType", "EmploymentStatus", "StartDate"
        }, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        cliniciansTable = new JTable(cliniciansTM);

        panel.add(new JScrollPane(cliniciansTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> showAddClinicianDialog());
        edit.addActionListener(e -> showEditClinicianDialog());
        del.addActionListener(e -> deleteSelected(cliniciansTable, deleteClinicianListener, "clinician"));

        btns.add(add); btns.add(edit); btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshCliniciansTable(ArrayList<Clinician> clinicians) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        cliniciansTM.setRowCount(0);
        for (Clinician c : clinicians) {
            String start = (c.getStartDate() == null) ? "" : sdf.format(c.getStartDate());
            cliniciansTM.addRow(new Object[]{
                    c.getClinicianId(),
                    c.getFirstName(),
                    c.getLastName(),
                    c.getTitle(),
                    c.getSpeciality(),
                    c.getGmcNumber(),
                    c.getContact(),
                    c.getEmail(),
                    c.getWorkplaceId(),
                    c.getWorkplaceType(),
                    c.getEmploymentStatus(),
                    start
            });
        }
    }

    private void showAddClinicianDialog() {
        if (addClinicianListener == null) { showErrorMessage("Clinician add handler not set."); return; }

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
        JTextField start = new JTextField("yyyy-MM-dd");

        JPanel form = formGrid(
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
                "Start Date (yyyy-MM-dd)", start
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Add Clinician", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            Date startDate = parseDate(start.getText().trim());
            addClinicianListener.onAddClinician(
                    first.getText().trim(),
                    last.getText().trim(),
                    title.getText().trim(),
                    speciality.getText().trim(),
                    gmc.getText().trim(),
                    contact.getText().trim(),
                    email.getText().trim(),
                    workplaceId.getText().trim(),
                    workplaceType.getText().trim(),
                    (EmploymentStatus) emp.getSelectedItem(),
                    startDate
            );
        } catch (Exception ex) {
            showErrorMessage("Invalid date. Use yyyy-MM-dd");
        }
    }

    private void showEditClinicianDialog() {
        if (editClinicianListener == null) { showErrorMessage("Clinician edit handler not set."); return; }
        int row = cliniciansTable.getSelectedRow();
        if (row < 0) { showErrorMessage("Select a clinician row first."); return; }

        String clinicianId = cliniciansTable.getValueAt(row, 0).toString();

        JTextField first = new JTextField(cliniciansTable.getValueAt(row, 1).toString());
        JTextField last = new JTextField(cliniciansTable.getValueAt(row, 2).toString());
        JTextField title = new JTextField(cliniciansTable.getValueAt(row, 3).toString());
        JTextField speciality = new JTextField(cliniciansTable.getValueAt(row, 4).toString());
        JTextField gmc = new JTextField(cliniciansTable.getValueAt(row, 5).toString());
        JTextField contact = new JTextField(cliniciansTable.getValueAt(row, 6).toString());
        JTextField email = new JTextField(cliniciansTable.getValueAt(row, 7).toString());
        JTextField workplaceId = new JTextField(cliniciansTable.getValueAt(row, 8).toString());
        JTextField workplaceType = new JTextField(cliniciansTable.getValueAt(row, 9).toString());

        // Employment status (column 10)
        EmploymentStatus currentStatus = null;
        try {
            Object v = cliniciansTable.getValueAt(row, 10);
            if (v != null) currentStatus = EmploymentStatus.valueOf(v.toString());
        } catch (Exception ignored) {}

        JComboBox<EmploymentStatus> emp = new JComboBox<>(EmploymentStatus.values());
        if (currentStatus != null) emp.setSelectedItem(currentStatus);

        // Start date (column 11) is a String in your table (yyyy-MM-dd)
        JTextField start = new JTextField(cliniciansTable.getValueAt(row, 11).toString());

        JPanel form = formGrid(
                "Clinician ID", new JLabel(clinicianId),
                "First Name", first,
                "Last Name", last,
                "Title", title,
                "Speciality", speciality,
                "GMC Number", gmc,
                "Contact", contact,
                "Email", email,
                "Workplace ID", workplaceId,
                "Workplace Type", workplaceType,
                "Employment Status", emp,
                "Start Date (yyyy-MM-dd)", start
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Edit Clinician", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            Date startDate = parseDate(start.getText().trim());

            editClinicianListener.onEditClinician(
                    clinicianId,
                    first.getText().trim(),
                    last.getText().trim(),
                    title.getText().trim(),
                    speciality.getText().trim(),
                    gmc.getText().trim(),
                    contact.getText().trim(),
                    email.getText().trim(),
                    workplaceId.getText().trim(),
                    workplaceType.getText().trim(),
                    (EmploymentStatus) emp.getSelectedItem(),
                    startDate
            );
        } catch (Exception ex) {
            showErrorMessage("Invalid start date. Use yyyy-MM-dd");
        }
    }


    // ========================= APPOINTMENTS TAB =========================

    private JPanel buildAppointmentsTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        appointmentsTM = new DefaultTableModel(new String[]{
                "AppointmentID","PatientID","ClinicianID","FacilityID","Date","Time","Duration","Type","Status","Reason",
                "Notes","CreatedDate","LastModified"
        }, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };

        appointmentsTable = new JTable(appointmentsTM);
        panel.add(new JScrollPane(appointmentsTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton updateStatus = new JButton("Update Status");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> showAddAppointmentDialog());
        updateStatus.addActionListener(e -> showUpdateAppointmentStatusDialog());
        edit.addActionListener(e -> showEditAppointmentDialog());
        del.addActionListener(e -> deleteSelected(appointmentsTable, deleteAppointmentListener, "appointment"));

        btns.add(add); btns.add(edit); btns.add(updateStatus); btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshAppointmentsTable(ArrayList<Appointment> appts, HCModel model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        appointmentsTM.setRowCount(0);
        for (Appointment a : appts) {
            String created = (a.getCreatedDate() == null) ? "" : sdf.format(a.getCreatedDate());
            String modified = (a.getLastModified() == null) ? "" : sdf.format(a.getLastModified());
            appointmentsTM.addRow(new Object[]{
                    a.getAppointmentId(),
                    a.getPatientId(),
                    a.getClinicianID(),
                    a.getFacilityId(),
                    a.getAppointmentDate() == null ? "" : sdf.format(a.getAppointmentDate()),
                    a.getAppointmentTime(),
                    a.getDurationMinutes(),
                    a.getAppointmentType(),
                    a.getStatus() == null ? "" : a.getStatus().toCSV(),
                    a.getReason_For_Visit(),
                    a.getNotes(),
                    created,
                    modified
            });
        }
    }

    private void showAddAppointmentDialog() {
        if (addAppointmentListener == null) { showErrorMessage("Appointment add handler not set."); return; }

        JTextField patientId = new JTextField();
        JTextField clinicianId = new JTextField();
        JTextField facilityId = new JTextField();
        JTextField date = new JTextField("yyyy-MM-dd");
        JTextField time = new JTextField("HH:mm");
        JTextField duration = new JTextField("15");
        JTextField type = new JTextField("Consultation");
        JTextField reason = new JTextField();
        JTextField notes = new JTextField();
        JTextField cdate = new JTextField("yyyy-MM-dd");
        JTextField mdate = new JTextField("yyyy-MM-dd");

        JPanel form = formGrid(
                "Patient ID", patientId,
                "Clinician ID", clinicianId,
                "Facility ID", facilityId,
                "Date (yyyy-MM-dd)", date,
                "Time (HH:mm)", time,
                "Duration (minutes)", duration,
                "Type", type,
                "Reason", reason,
                "Notes", notes,
                "CreatedDate", cdate,
                "LastModified", mdate
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Add Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            Date d = parseDate(date.getText().trim());
            int dur = Integer.parseInt(duration.getText().trim());
            Date dc = parseDate(cdate.getText().trim());
            Date dm = parseDate(mdate.getText().trim());

            addAppointmentListener.onAddAppointment(
                    patientId.getText().trim(),
                    clinicianId.getText().trim(),
                    facilityId.getText().trim(),
                    d,
                    time.getText().trim(),
                    dur,
                    type.getText().trim(),
                    reason.getText().trim(),
                    notes.getText().trim(),
                    dc,
                    dm
            );
        } catch (Exception ex) {
            showErrorMessage("Invalid input (date or duration).");
        }
    }

    private void showEditAppointmentDialog() {
        if (editAppointmentListener == null) { showErrorMessage("Appointment edit handler not set."); return; }

        int row = appointmentsTable.getSelectedRow();
        if (row < 0) { showErrorMessage("Select an appointment row first."); return; }

        String appointmentId = appointmentsTable.getValueAt(row, 0).toString();

        JTextField patientId = new JTextField(appointmentsTable.getValueAt(row, 1).toString());
        JTextField clinicianId = new JTextField(appointmentsTable.getValueAt(row, 2).toString());
        JTextField facilityId = new JTextField(appointmentsTable.getValueAt(row, 3).toString());
        JTextField date = new JTextField(appointmentsTable.getValueAt(row, 4).toString());
        JTextField time = new JTextField(appointmentsTable.getValueAt(row, 5).toString());
        JTextField duration = new JTextField(appointmentsTable.getValueAt(row, 6).toString());
        JTextField type = new JTextField(appointmentsTable.getValueAt(row, 7).toString());
        JTextField reason = new JTextField(appointmentsTable.getValueAt(row, 9).toString());
        JTextField notes = new JTextField(appointmentsTable.getValueAt(row, 10).toString());

        JPanel form = formGrid(
                "Appointment ID", new JLabel(appointmentId),
                "patient ID", patientId,
                "Clinician ID", clinicianId,
                "Facility ID", facilityId,
                "Date (yyyy-MM-dd)", date,
                "Time (HH:mm)", time,
                "Duration (minutes)", duration,
                "Type", type,
                "Reason", reason,
                "Notes", notes
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Edit Appointment", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            Date d = parseDate(date.getText().trim());
            int dur = Integer.parseInt(duration.getText().trim());

            editAppointmentListener.onEditAppointment(
                    appointmentId,
                    patientId.getText().trim(),
                    clinicianId.getText().trim(),
                    facilityId.getText().trim(),
                    d,
                    time.getText().trim(),
                    dur,
                    type.getText().trim(),
                    reason.getText().trim(),
                    notes.getText().trim()
            );
        } catch (Exception ex) {
            showErrorMessage("Invalid input (date or duration).");
        }

    }

    private void showUpdateAppointmentStatusDialog() {
        if (updateAppointmentStatusListener == null) { showErrorMessage("UpdateStatus handler not set."); return; }
        int row = appointmentsTable.getSelectedRow();
        if (row < 0) { showErrorMessage("Select an appointment row first."); return; }

        String apptId = appointmentsTable.getValueAt(row, 0).toString();
        JTextField status = new JTextField("SCHEDULED"); // or CANCELLED/COMPLETED etc.

        JPanel form = formGrid("New Status", status);

        int ok = JOptionPane.showConfirmDialog(this, form, "Update Appointment Status: " + apptId, JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        updateAppointmentStatusListener.onUpdateStatus(apptId, status.getText().trim());
    }

    // ========================= PRESCRIPTIONS TAB =========================

    private JPanel buildPrescriptionsTab() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));

        prescriptionsTM = new DefaultTableModel(new String[]{
                "PrescriptionID","PatientID","ClinicianID","AppointmentID","PrescriptionDate","Medication","Dosage",
                "Frequency","Days","Quantity","Instructions","Pharmacy","Status","IssuedDate", "CollectionDate"
        }, 0) { @Override public boolean isCellEditable(int r, int c) { return false; } };

        prescriptionsTable = new JTable(prescriptionsTM);
        panel.add(new JScrollPane(prescriptionsTable), BorderLayout.CENTER);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton genDoc = new JButton("Generate Document");
        JButton del = new JButton("Delete");

        add.addActionListener(e -> showAddPrescriptionDialog());
        genDoc.addActionListener(e -> generateSelectedPrescriptionDoc());
        del.addActionListener(e -> deleteSelected(prescriptionsTable, deletePrescriptionListener, "prescription"));

        btns.add(add); btns.add(genDoc); btns.add(del);
        panel.add(btns, BorderLayout.SOUTH);

        return panel;
    }

    public void refreshPrescriptionsTable(ArrayList<Prescription> rxList, HCModel model) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        prescriptionsTM.setRowCount(0);
        for (Prescription rx : rxList) {
            String pdate = (rx.getPrescriptionDate() == null) ? "" : sdf.format(rx.getPrescriptionDate());
            prescriptionsTM.addRow(new Object[]{
                    rx.getPrescriptionId(),
                    rx.getPatientId(),
                    rx.getClinicianId(),
                    rx.getAppointmentId(),
                    pdate,
                    rx.getMedicationName(),
                    rx.getDosage(),
                    rx.getFrequency(),
                    rx.getDurationDays(),
                    rx.getQuantity(),
                    rx.getInstruction(),
                    rx.getPharmacyName(),
                    rx.getStatus(),
                    rx.getIssueDate(),
                    rx.getCollectionDate()
            });
        }
    }

    private void showAddPrescriptionDialog() {
        if (addPrescriptionListener == null) { showErrorMessage("Prescription add handler not set."); return; }

        JTextField patientId = new JTextField();
        JTextField clinicianId = new JTextField();
        JTextField appointmentId = new JTextField();
        JTextField date = new JTextField("yyyy-MM-dd");
        JTextField medication = new JTextField();
        JTextField dosage = new JTextField();
        JTextField frequency = new JTextField();
        JTextField days = new JTextField("7");
        JTextField qty = new JTextField();
        JTextField instruction = new JTextField();
        JTextField pharmacy = new JTextField();

        JPanel form = formGrid(
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
                "Pharmacy", pharmacy
        );

        int ok = JOptionPane.showConfirmDialog(this, form, "Add Prescription", JOptionPane.OK_CANCEL_OPTION);
        if (ok != JOptionPane.OK_OPTION) return;

        try {
            Date d = parseDate(date.getText().trim());
            int duration = Integer.parseInt(days.getText().trim());

            addPrescriptionListener.onAddPrescription(
                    patientId.getText().trim(),
                    clinicianId.getText().trim(),
                    appointmentId.getText().trim(),
                    d,
                    medication.getText().trim(),
                    dosage.getText().trim(),
                    frequency.getText().trim(),
                    duration,
                    qty.getText().trim(),
                    instruction.getText().trim(),
                    pharmacy.getText().trim()
            );

        } catch (Exception ex) {
            showErrorMessage("Invalid input (date or duration).");
        }
    }

    private void generateSelectedPrescriptionDoc() {
        if (generatePrescriptionDocumentListener == null) { showErrorMessage("GenerateDocument handler not set."); return; }
        int row = prescriptionsTable.getSelectedRow();
        if (row < 0) { showErrorMessage("Select a prescription row first."); return; }
        String id = prescriptionsTable.getValueAt(row, 0).toString();
        generatePrescriptionDocumentListener.onGenerateDocument(id);
    }

    // ========================= REQUIRED “IGNORE” METHODS)=========================

    public void refreshReferralsTable(ArrayList<Referral> refs, HCModel model) {}
    public void refreshFacilitiesTable(ArrayList<Facility> facilities) {}
    public void refreshStaffTable(ArrayList<Staff> staff) {}

    // ========================= COMMON HELPERS =========================

    private void deleteSelected(JTable table, DeleteListener listener, String name) {
        if (listener == null) { showErrorMessage("Delete handler not set for " + name); return; }
        int row = table.getSelectedRow();
        if (row < 0) { showErrorMessage("Select a " + name + " row first."); return; }
        String id = table.getValueAt(row, 0).toString();
        int ok = JOptionPane.showConfirmDialog(this, "Delete " + name + " " + id + "?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) listener.onDelete(id);
    }

    private JPanel formGrid(Object... pairs) {
        JPanel p = new JPanel(new GridLayout(0, 2, 8, 8));
        for (int i = 0; i < pairs.length; i += 2) {
            p.add(new JLabel(String.valueOf(pairs[i])));
            Object comp = pairs[i + 1];
            if (comp instanceof Component) p.add((Component) comp);
            else p.add(new JLabel(String.valueOf(comp)));
        }
        return p;
    }

    private Date parseDate(String yyyyMMdd) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        return sdf.parse(yyyyMMdd);
    }

    // ========================= Messages =========================

    public void showSuccessMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ========================= LISTENER SETTERS =========================

    public void setOnCloseListener(Runnable r) { this.onCloseListener = r; }

    public void setRefreshPatientsListener(Runnable r) { this.refreshPatientsListener = r; }
    public void setRefreshCliniciansListener(Runnable r) { this.refreshCliniciansListener = r; }
    public void setRefreshAppointmentsListener(Runnable r) { this.refreshAppointmentsListener = r; }
    public void setRefreshPrescriptionsListener(Runnable r) { this.refreshPrescriptionsListener = r; }

    public void setAddPatientListener(PatientListener l) { this.addPatientListener = l; }
    public void setEditPatientListener(EditPatientListener l) { this.editPatientListener = l; }
    public void setDeletePatientListener(DeleteListener l) { this.deletePatientListener = l; }

    public void setAddClinicianListener(ClinicianListener l) { this.addClinicianListener = l; }
    public void setEditClinicianListener(EditClinicianListener l) { this.editClinicianListener = l; }
    public void setDeleteClinicianListener(DeleteListener l) { this.deleteClinicianListener = l; }

    public void setAddAppointmentListener(AppointmentListener l) { this.addAppointmentListener = l; }
    public void setEditAppointmentListener(EditAppointmentListener l) { this.editAppointmentListener = l; }
    public void setUpdateAppointmentStatusListener(UpdateStatusListener l) { this.updateAppointmentStatusListener = l; }
    public void setDeleteAppointmentListener(DeleteListener l) { this.deleteAppointmentListener = l; }

    public void setAddPrescriptionListener(PrescriptionListener l) { this.addPrescriptionListener = l; }
    public void setGeneratePrescriptionDocumentListener(GenerateDocumentListener l) { this.generatePrescriptionDocumentListener = l; }
    public void setDeletePrescriptionListener(DeleteListener l) { this.deletePrescriptionListener = l; }

    // extra setters the controller calls (kept for compilation)
    public void setRefreshReferralsListener(Runnable r) { this.refreshReferralsListener = r; }
    public void setAddReferralListener(ReferralListener l) { this.addReferralListener = l; }
    public void setUpdateReferralStatusListener(UpdateStatusListener l) { this.updateReferralStatusListener = l; }
    public void setGenerateReferralDocumentListener(GenerateDocumentListener l) { this.generateReferralDocumentListener = l; }

    public void setRefreshFacilitiesListener(Runnable r) { this.refreshFacilitiesListener = r; }
    public void setAddFacilityListener(FacilityListener l) { this.addFacilityListener = l; }
    public void setDeleteFacilityListener(DeleteListener l) { this.deleteFacilityListener = l; }

    public void setRefreshStaffListener(Runnable r) { this.refreshStaffListener = r; }
    public void setAddStaffListener(StaffListener l) { this.addStaffListener = l; }
    public void setDeleteStaffListener(DeleteListener l) { this.deleteStaffListener = l; }
}
