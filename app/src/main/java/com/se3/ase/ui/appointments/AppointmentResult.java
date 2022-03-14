package com.se3.ase.ui.appointments;

import com.se3.ase.data.model.AppointmentModel;

import java.util.List;

public class AppointmentResult {
    private Integer error;
    private List<AppointmentModel> success;

    public AppointmentResult(Integer error){
        this.error = error;
    }

    public AppointmentResult(List<AppointmentModel> success){
        this.success = success;
    }

    public Integer getError() {
        return error;
    }

    public List<AppointmentModel> getSuccess() {
        return success;
    }
}
