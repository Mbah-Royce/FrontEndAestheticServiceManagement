package com.se3.ase.ui.serviceDetail;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.se3.ase.data.ServiceDetailRepository;


public class ServiceDataViewModel extends ViewModel {
    private LiveData<ServiceDetailResult> servicesDetailResult = new MutableLiveData<>();
    ServiceDetailRepository serviceDetailRepository;

    LiveData<ServiceDetailResult> getServicesDetailResult(){
        return servicesDetailResult;
    }
    public void init(Context context){
        serviceDetailRepository = new ServiceDetailRepository(context);
        servicesDetailResult = serviceDetailRepository.getServiceDetailResult();
    }

    public void getServiceDetails(String serviceId){
        serviceDetailRepository.getServiceDetail(serviceId);
    }
}
