package model;

import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Worker extends Person {
    private String employmentStatus;
    private Date startDate;

    public Worker(String firstName, String lastName, String email, String contact,
                  String employmentStatus, Date startDate) {
        super(firstName, lastName, email, contact);
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return super.toString() +  "\n"
            + "employmentStatus: " + employmentStatus +  "\n" +
                " startDate: " + sdf.format(startDate);
    }
}
