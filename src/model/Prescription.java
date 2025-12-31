/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import CSV.CSVHandler;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


public class Prescription {
    private String prescriptionId;
    private String patientId;
    private String clinicianId;
    private String appointmentId;
    private Date prescriptionDate;
    private String medicationName;
    private String dosage;
    private String frequency;
    private int durationDays;
    private String quantity;
    private String instruction;
    private String pharmacyName;
    private String status;
    private Date issueDate;
    private Date collectionDate;

    public Prescription(String prescriptionId, String patientId, String clinicianId, String appointmentId,
                        Date prescriptionDate, String medicationName, String dosage, String frequency,
                        int durationDays, String quantity, String instruction, String pharmacyName, String status,
                        Date issueDate, Date collectionDate) {
        this.prescriptionId = prescriptionId;
        this.patientId = patientId;
        this.clinicianId = clinicianId;
        this.appointmentId = appointmentId;
        this.prescriptionDate = prescriptionDate;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.frequency = frequency;
        this.durationDays = durationDays;
        this.quantity = quantity;
        this.instruction = instruction;
        this.pharmacyName = pharmacyName;
        this.status = status;
        this.issueDate = issueDate;
        this.collectionDate = collectionDate;
    }

    public String getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(String prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getClinicianId() {
        return clinicianId;
    }

    public void setClinicianId(String clinicianId) {
        this.clinicianId = clinicianId;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getPrescriptionDate() {
        return prescriptionDate;
    }

    public void setPrescriptionDate(Date prescriptionDate) {
        this.prescriptionDate = prescriptionDate;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public int getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(int durationDays) {
        this.durationDays = durationDays;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(String pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    public Date getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(Date collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String toCSV(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<String> fields = Arrays.asList( prescriptionId,patientId,clinicianId ,appointmentId ,
                formatDate(sdf, prescriptionDate),medicationName ,dosage, frequency,
                String.valueOf(durationDays),quantity ,instruction ,pharmacyName,
                status, formatDate(sdf, issueDate), formatDate(sdf,collectionDate)
        );
        return CSVHandler.toLine(fields);
    }

    public static Prescription fromCSV(String csvLine){
        try{
            List<String> parts = CSVHandler.parseLine(csvLine);
            if (parts.size() < 15) return null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return new Prescription(parts.get(0), parts.get(1), parts.get(2), parts.get(3), parseDate(sdf, parts.get(4)),
                    parts.get(5), parts.get(6), parts.get(7), Integer.parseInt(parts.get(8)), parts.get(9),
                    parts.get(10), parts.get(11), parts.get(12), parseDate(sdf, parts.get(13)), parseDate(sdf, parts.get(14)));
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    private static Date parseDate(SimpleDateFormat sdf, String value) throws Exception {
        if (value == null) return null;
        String v = value.trim();
        if (v.isEmpty()) return null;
        return sdf.parse(v);
    }

    private static String formatDate(SimpleDateFormat sdf, Date date) {
        return date == null ? "" : sdf.format(date);
    }


    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "model.Prescription{" +
                "prescriptionId='" + prescriptionId + "\n" +
                ", patientId=" + patientId +
                ", clinicianId=" + clinicianId +
                ", appointmentId=" + appointmentId +
                ", prescriptionDate=" + formatDate(sdf, prescriptionDate) +
                ", medicationName='" + medicationName + "\n"+
                ", dosage='" + dosage + "\n" +
                ", frequency='" + frequency + "\n" +
                ", durationDays=" + durationDays +
                ", quantity='" + quantity + "\n" +
                ", instruction='" + instruction + "\n" +
                ", pharmacyName='" + pharmacyName + "\n" +
                ", status=" + status + "\n" +
                ", issueDate=" + formatDate(sdf, issueDate) +
                ", collectionDate=" + formatDate(sdf, collectionDate) +
                '}';
    }
}
