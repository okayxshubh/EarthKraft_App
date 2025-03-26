package com.dit.hp.hospitalapp.enums;

public enum TaskType {


    LOGIN_HOSPITAL(1),


    IDK_TASK(99999)
    ;


    int value;
    private TaskType(int value) { this.value = value; }
}
