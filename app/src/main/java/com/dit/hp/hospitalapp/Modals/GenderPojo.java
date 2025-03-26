package com.dit.hp.hospitalapp.Modals;

import java.io.Serializable;

// GenderPojo
public class GenderPojo implements Serializable {
    private int genderId;
    private String genderName;

    public GenderPojo() {

    }

    public GenderPojo(int genderId, String genderName) {
        this.genderId = genderId;
        this.genderName = genderName;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    @Override
    public String toString() {
        return genderName;
    }
}
