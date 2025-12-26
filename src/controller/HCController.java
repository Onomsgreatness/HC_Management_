package controller;

import model.*;

public class HCController {

    private HCModel model;

    public HCController() {
        this.model = new HCModel();
    }

    // ================= Patient =================

    public void addPatient(Patient patient) {
        model.addPatient(patient);
    }
}
