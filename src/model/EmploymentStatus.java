package model;

public enum EmploymentStatus {
    FULLTIME("Full-Time"),
    PARTTIME("Part-Time");

    private String status;

    EmploymentStatus (String status) { this.status = status; }

    public String getStatus(){
        return status;
    }

    @Override
    public String toString(){
        return "Employment Status: " + status;
    }

}
