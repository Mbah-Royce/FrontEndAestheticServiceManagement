package com.se3.ase.ui.login;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.se3.ase.R;
import com.se3.ase.data.LoginRepository;
import com.se3.ase.data.Result;
import com.se3.ase.data.model.LoggedInUser;
import com.se3.ase.network.Api;
import com.se3.ase.network.ApiResponseHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class LoginViewModel extends ViewModel {
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private LiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    Result<LoggedInUser> result;

    LiveData<LoginFormState> getLoginFormState(){
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult(){
        return loginResult;
    }

    public void init() {
        loginRepository = new LoginRepository();
        loginResult = loginRepository.getResultResponseLiveData();
    }

    public void login(String email, String password, Context context){
        loginRepository.login(email,password, context);
    }

    public void loginDataChanged(String email, String password){
        if(email.isEmpty()){
            loginFormState.setValue(new LoginFormState(R.string.invalid_email,null));
        }else if(password.isEmpty()){
            loginFormState.setValue(new LoginFormState(null,R.string.invalid_password));
        }else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }
}
