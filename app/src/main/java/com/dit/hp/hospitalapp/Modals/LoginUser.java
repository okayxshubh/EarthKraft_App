package com.dit.hp.hospitalapp.Modals;


import java.io.Serializable;

public class LoginUser implements Serializable {

    private int userId;
    private String userName;
    private Long mobileNumber;
    private String firstName;
    private String lastName;

    private int roleId;
    private String roleName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(Long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNmae() {
        return lastName;
    }

    public void setLastNmae(String lastNmae) {
        this.lastName = lastNmae;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", firstName='" + firstName + '\'' +
                ", lastNmae='" + lastName + '\'' +
                ", roleId=" + roleId +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}

