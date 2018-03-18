package com.sshhww.common.bean;

/**
 * Created by limengnan on 2018/1/29.
 */
public class TaskRecordVo extends ResponseVo {

    int taskRecordId;
    String taskRecordName;

    public int getTaskRecordId() {
        return taskRecordId;
    }

    public void setTaskRecordId(int taskRecordId) {
        this.taskRecordId = taskRecordId;
    }

    public String getTaskRecordName() {
        return taskRecordName;
    }

    public void setTaskRecordName(String taskRecordName) {
        this.taskRecordName = taskRecordName;
    }

}
