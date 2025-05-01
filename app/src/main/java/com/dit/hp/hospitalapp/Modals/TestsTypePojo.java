package com.dit.hp.hospitalapp.Modals;

import java.io.Serializable;

// GenderPojo
public class TestsTypePojo implements Serializable {
    private int testTypeId;
    private String testTypeName;

    public TestsTypePojo() {
    }

    public int getTestTypeId() {
        return testTypeId;
    }

    public void setTestTypeId(int testTypeId) {
        this.testTypeId = testTypeId;
    }

    public String getTestTypeName() {
        return testTypeName;
    }

    public void setTestTypeName(String testTypeName) {
        this.testTypeName = testTypeName;
    }
}
