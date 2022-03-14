package com.se3.ase.ui.dashboard;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.se3.ase.network.Api;

public class DashboardViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue(Api.GET("http://192.168.0.100:11706/CasaDemo/webresources/usercontroller/casauser"));
        mText.setValue("dfdf");
    }

    public LiveData<String> getText() {
        return mText;
    }
}