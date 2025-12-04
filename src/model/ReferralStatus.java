package model;

public enum ReferralStatus {
    COMPLETED("Completed"),
    PENDING("Pending"),
    INPROGRESS("Inprogress"),
    NEW("New");

    private String status;

    ReferralStatus(String status){
        this.status = status;
    }

    @Override
    public String toString(){
        return "Referral Status: " + status;
    }
}
