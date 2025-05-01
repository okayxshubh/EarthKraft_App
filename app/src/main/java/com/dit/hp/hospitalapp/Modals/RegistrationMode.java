package com.dit.hp.hospitalapp.Modals;

import java.io.Serializable;

public class RegistrationMode implements Serializable {
    private int regisrationModeId;
    private String regisrationModeName;

    public RegistrationMode() {
    }

    public int getRegisrationModeId() {
        return regisrationModeId;
    }

    public void setRegisrationModeId(int regisrationModeId) {
        this.regisrationModeId = regisrationModeId;
    }

    public String getRegisrationModeName() {
        return regisrationModeName;
    }

    public void setRegisrationModeName(String regisrationModeName) {
        this.regisrationModeName = regisrationModeName;
    }

    @Override
    public String toString() {
        return this.regisrationModeName;
    }

}
