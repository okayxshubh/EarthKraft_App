package com.dit.hp.hospitalapp.Modals;

import java.io.Serializable;

// GenderPojo
public class TestsPojo implements Serializable {

    private int testId;
    private String testName;

    private String testDescription;
    private String normalrange;
    private String testprice;

   private TestsTypePojo testsTypePojo;


    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestDescription() {
        return testDescription;
    }

    public void setTestDescription(String testDescription) {
        this.testDescription = testDescription;
    }

    public String getNormalrange() {
        return normalrange;
    }

    public void setNormalrange(String normalrange) {
        this.normalrange = normalrange;
    }

    public String getTestprice() {
        return testprice;
    }

    public void setTestprice(String testprice) {
        this.testprice = testprice;
    }

    public TestsTypePojo getTestsTypePojo() {
        return testsTypePojo;
    }

    public void setTestsTypePojo(TestsTypePojo testsTypePojo) {
        this.testsTypePojo = testsTypePojo;
    }

    @Override
    public String toString() {
        return testName + " : " + "Rs. " + testprice + "/-";
    }

}
