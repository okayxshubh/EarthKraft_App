package com.dit.hp.hospitalapp.Modals;

import java.io.Serializable;

// GenderPojo
public class TestsPojo implements Serializable {
    private int testId;
    private int testCharges;
    private String testName;

    public TestsPojo() {

    }

    public TestsPojo(int testId, int testCharges, String testName) {
        this.testId = testId;
        this.testCharges = testCharges;
        this.testName = testName;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getTestCharges() {
        return testCharges;
    }

    public void setTestCharges(int testCharges) {
        this.testCharges = testCharges;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    @Override
    public String toString() {
        return testName + " : " + "Rs. " + testCharges + "/-";
    }
}
