package com.se3.ase.ui.services;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.se3.ase.data.ServicesRepository;


public class ServicesViewModel extends ViewModel {
    private LiveData<ServicesResult> servicesResult = new MutableLiveData<>();
    ServicesRepository servicesRepository;

    public LiveData<ServicesResult> getServicesResult(){
        return servicesResult;
    }

    public void init(Context context){
        servicesRepository = new ServicesRepository(context);
        servicesResult = servicesRepository.getResultResponseLiveData();
    }

    public void getServices(){
        servicesRepository.getServices();
    }

}
