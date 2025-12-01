package model;

import java.time.LocalDate;

public abstract class Worker extends Person {
    private String employmentStatus;
    private LocalDate startDate;

    public Worker(String firstName, String lastName, String email, String contact, LocalDate DoB,
                  String employmentStatus, LocalDate startDate) {
        super(firstName, lastName, email, contact, DoB);
        this.employmentStatus = employmentStatus;
        this.startDate = startDate;
    }

    public String getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(String employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return super.toString() +  "\n"
            + "employmentStatus: " + employmentStatus +  "\n" +
                " startDate: " + startDate;
    }
}
