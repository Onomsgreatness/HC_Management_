/**
 * Author: Onome Abuku <oa22aed@herts.ac.uk>
 *     ID: 21092431
 *     References: Dr. John Kanyaru, BookShop Example.
 */

package model;

import java.util.Date;
import java.text.SimpleDateFormat;

public abstract class Worker extends Person {
    private EmploymentStatus employmentStatus;
    private Date startDate;

    public Worker(String firstName, String lastName, String email, String contact,
                  EmploymentStatus employmentStatus, Date startDate) {
        super(firstName, lastName, email, contact);
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
    }

    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    private static String formatDate(SimpleDateFormat sdf, Date date) {
        return date == null ? "" : sdf.format(date);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return super.toString() + "\n"
                + "employmentStatus: " + (employmentStatus == null ? "" : employmentStatus.toString()) + "\n"
                + "startDate: " + formatDate(sdf, startDate);
    }
}
