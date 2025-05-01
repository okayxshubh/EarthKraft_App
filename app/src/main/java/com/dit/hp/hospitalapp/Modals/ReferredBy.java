package com.dit.hp.hospitalapp.Modals;

import java.io.Serializable;

public class ReferredBy implements Serializable {

    private int referredBYId;
    private String referredByName;
    private String referredByHospitalName;
    private String referredByAddress;

    public ReferredBy() {
    }

    public int getReferredBYId() {
        return referredBYId;
    }

    public void setReferredBYId(int referredBYId) {
        this.referredBYId = referredBYId;
    }

    public String getReferredByName() {
        return referredByName;
    }

    public void setReferredByName(String referredByName) {
        this.referredByName = referredByName;
    }

    public String getReferredByHospitalName() {
        return referredByHospitalName;
    }

    public void setReferredByHospitalName(String referredByHospitalName) {
        this.referredByHospitalName = referredByHospitalName;
    }

    public String getReferredByAddress() {
        return referredByAddress;
    }

    public void setReferredByAddress(String referredByAddress) {
        this.referredByAddress = referredByAddress;
    }

    @Override
    public String toString() {
        return referredByName;
    }

}
