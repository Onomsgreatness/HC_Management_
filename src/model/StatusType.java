package model;

public enum StatusType {
    SCHEDULED("Scheduled"),
    CANCELLED("Cancelled");

    private String status;

    StatusType(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return "status: " + status;
    }
}
