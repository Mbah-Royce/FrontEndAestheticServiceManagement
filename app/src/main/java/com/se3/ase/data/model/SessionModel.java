package com.se3.ase.data.model;

import androidx.annotation.NonNull;

public class SessionModel {
    private String startTime;
    private String endTime;
    private String name;
    private String sesssionId;

    public SessionModel(String startTime, String endTime, String name, String sesssionId) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.sesssionId = sesssionId;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getName() {
        return name;
    }

    public String getSesssionId() {
        return sesssionId;
    }

    @NonNull
    @Override
    public String toString() {
        return name+" Start Time:"+startTime+"- End Time: "+endTime;
    }
}
