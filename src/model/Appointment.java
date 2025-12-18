package model;


import java.util.Date;
import java.text.SimpleDateFormat;


public class Appointment {
    private String appointmentId;
    private String patientId;
    private String clinicianID;
    private String facilityId;
    private Date appointmentDate;
    private String appointmentTime;
    private int durationMinutes;
    private String appointmentType;
    private StatusType status;
    private String reason_For_Visit;
    private String notes;
    private Date createdDate;
    private Date lastModified;

    public Appointment(String appointmentId, String patientId, String clinicianID, String facilityId,
                       Date appointmentDate, String appointmentTime, int durationMinutes, String appointmentType, StatusType status,
                       String reason_For_Visit, String notes, Date createdDate, Date lastModified){
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.clinicianID = clinicianID;
        this.facilityId = facilityId;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.durationMinutes = durationMinutes;
        this.appointmentType = appointmentType;
        this.status = status;
        this.reason_For_Visit = reason_For_Visit;
        this.notes = notes;
        this.createdDate = createdDate;
        this.lastModified = lastModified;

    };

    public String getAppointmentId() {
      return appointmentId;
    }

    public void setAppointmentId(String id) {
        this.appointmentId = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getClinicianID() {
        return clinicianID;
    }

    public void setClinicianID(String clinicianID) {
        this.clinicianID = clinicianID;
    }

    public String getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(String facilityId) {
        this.facilityId = facilityId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(String appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public String getReason_For_Visit() {
        return reason_For_Visit;
    }

    public void setReason_For_Visit(String reason_For_Visit) {
        this.reason_For_Visit = reason_For_Visit;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String toCSV() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return appointmentId + "," + patientId + "," + clinicianID + "," + facilityId + "," +
        sdf.format(appointmentDate) + "," + appointmentTime + "," + durationMinutes + "," + status.name() + "," +
                status + "," + reason_For_Visit + "," + notes + "," + sdf.format(createdDate) + "," + sdf.format(lastModified);
    }

    public static Appointment fromCSV(String csvLine){
       try{
            String[] parts = csvLine.split(",");
            StatusType status = StatusType.valueOf(parts[8].trim());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return new Appointment(parts[0], parts[1], parts[2], parts[3], sdf.parse(parts[4]),
                    parts[5], Integer.parseInt(parts[6]), parts[7],status, parts[9], parts[10],
                    sdf.parse(parts[11]), sdf.parse(parts[12]));
       } catch (Exception e) {
           e.printStackTrace();
           return null;
       }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return "**** Appointment: ****\n"
                + "Appointment Id: " + appointmentId + "\n"
                + "Patient Id: " + patientId + "\n"
                + "Clinician Id: " + clinicianID + "\n"
                + "Facility Id: " + facilityId + "\n"
                + "Appointment Date: " + sdf.format(appointmentDate) + "\n"
                + "Appointment Time: " + appointmentTime + "\n"
                + "Duration Minutes: " + durationMinutes + "\n"
                + "Appointment Type: " + appointmentType + "\n"
                + "Status: " + status + "\n"
                + "Reason For Visit: " + reason_For_Visit + "\n"
                + "Notes: " + notes + "\n"
                + "Created Date: " + sdf.format(createdDate) + "\n"
                + "Last Modified: " + sdf.format(lastModified) + "\n";
    }
}
