package com.dit.hp.hospitalapp.Modals;

import java.io.Serializable;

// GenderPojo
public class BloodGroupPojo implements Serializable {
    private int bloodGroupId;
    private String bloodGroupName;


    public BloodGroupPojo() {

    }

    public BloodGroupPojo(int bloodGroupId, String bloodGroupName) {
        this.bloodGroupId = bloodGroupId;
        this.bloodGroupName = bloodGroupName;
    }

    public int getBloodGroupId() {
        return bloodGroupId;
    }

    public void setBloodGroupId(int bloodGroupId) {
        this.bloodGroupId = bloodGroupId;
    }

    public String getBloodGroupName() {
        return bloodGroupName;
    }

    public void setBloodGroupName(String bloodGroupName) {
        this.bloodGroupName = bloodGroupName;
    }

    @Override
    public String toString() {
        return bloodGroupName;
    }

}
