package com.dit.hp.hospitalapp.interfaces;


import com.dit.hp.hospitalapp.Modals.ResponsePojoGet;
import com.dit.hp.hospitalapp.enums.TaskType;

/**
 * @author Kush.Dhawan
 * @project HPePass
 * @Time 03, 05 , 2020
 */
public interface AsyncTaskListenerObject {
    void onTaskCompleted(ResponsePojoGet result, TaskType taskType) throws Exception;
}
