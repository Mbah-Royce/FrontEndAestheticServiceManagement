package com.se3.ase.ui.appointments;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.se3.ase.data.AppointmentsRepository;

public class AppointmentViewModel extends ViewModel {
    private LiveData<AppointmentResult> appointmentResult = new MutableLiveData<>();
    AppointmentsRepository appointmentsRepository;

    LiveData<AppointmentResult> getAppointmentResult()
    {
        return appointmentResult;
    }
    public void init(Context context){
        appointmentsRepository = new AppointmentsRepository(context);
        appointmentResult = appointmentsRepository.getResultResponse();
    }

    public  void getAppointments(){
        appointmentsRepository.getResultResponse();
    }
}
