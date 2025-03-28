package com.dit.hp.hospitalapp.Modals;

import java.io.Serializable;
import java.util.List;

// GenderPojo
public class PatientRecord implements Serializable {

    private String recordDate;
    private String patientName;
    private String dateOfBirth;
    private String mobileNumber;
    private String gender;
    private String bloodGroup;
    private List<TestsPojo> testsList;
    private String referredBy;
    private String amountDue;
    private String paymentType;
    private String receiptNumber;

    public PatientRecord() {
    }

    // All args const.
    public PatientRecord(String recordDate, String patientName, String dateOfBirth, String mobileNumber, String gender, String bloodGroup, List<TestsPojo> testsList, String referredBy, String amountDue, String paymentType, String receiptNumber) {
        this.recordDate = recordDate;
        this.patientName = patientName;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.testsList = testsList;
        this.referredBy = referredBy;
        this.amountDue = amountDue;
        this.paymentType = paymentType;
        this.receiptNumber = receiptNumber;
    }


    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public List<TestsPojo> getTestsList() {
        return testsList;
    }

    public void setTestsList(List<TestsPojo> testsList) {
        this.testsList = testsList;
    }

    public String getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(String referredBy) {
        this.referredBy = referredBy;
    }

    public String getAmountDue() {
        return amountDue;
    }

    public void setAmountDue(String amountDue) {
        this.amountDue = amountDue;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getReceiptNumber() {
        return receiptNumber;
    }

    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }

    @Override
    public String toString() {
        return "PatientRecord{" +
                "recordDate='" + recordDate + '\'' +
                ", patientName='" + patientName + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", testsList=" + testsList +
                ", referredBy='" + referredBy + '\'' +
                ", amountDue='" + amountDue + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", receiptNumber='" + receiptNumber + '\'' +
                '}';
    }
}
