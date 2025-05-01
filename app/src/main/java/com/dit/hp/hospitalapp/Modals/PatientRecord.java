package com.dit.hp.hospitalapp.Modals;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

// GenderPojo
public class PatientRecord implements Serializable {

    private String firstName;
    private String lastName;

    private String patientAge;
    private String patientMobile;

    private String sampleDate;
    private String receiptNumber;

    private String gender;
    private String referredBy;
    private String registrationMode;

    private List<TestsPojo> testList;


    public PatientRecord() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientMobile() {
        return patientMobile;
    }

    public void setPatientMobile(String patientMobile) {
        this.patientMobile = patientMobile;
    }

    public String getSampleDate() {
        return sampleDate;
    }

    public void setSampleDate(String sampleDate) {
        this.sampleDate = sampleDate;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getRegistrationMode() {
        return registrationMode;
    }

    public void setRegistrationMode(String registrationMode) {
        this.registrationMode = registrationMode;
    }

    public List<TestsPojo> getTestList() {
        return testList;
    }

    public void setTestList(List<TestsPojo> testList) {
        this.testList = testList;
    }


    public JSONObject getJsonToSave() {
        JSONObject json = new JSONObject();
        JSONArray testArray = new JSONArray();

        try {
            json.put("firstName", firstName);
            json.put("lastname", lastName);
            json.put("patientAge", patientAge);
            json.put("patientMobile", patientMobile);
            json.put("sampleDate", sampleDate);
            json.put("receiptNumber", receiptNumber);
            json.put("gender", gender);
            json.put("referredBy", referredBy);
            json.put("registrationMode", registrationMode);

            if (testList != null) {
                for (TestsPojo test : testList) {
                    JSONObject testObj = new JSONObject();
                    testObj.put("testName", test.getTestName());
                    testObj.put("testId", test.getTestId());
                    testArray.put(testObj);
                }
            }

            json.put("tests", testArray);

        } catch (Exception e) {
            Log.e("PatientRecord", "JsonToSave Issue: " + e.getMessage());
        }

        return json;
    }



}
