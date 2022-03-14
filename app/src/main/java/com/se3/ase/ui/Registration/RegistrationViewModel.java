package com.se3.ase.ui.Registration;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.se3.ase.R;
import com.se3.ase.data.RegistrationRepository;
import com.se3.ase.data.Result;
import com.se3.ase.data.model.LoggedInUser;
import com.se3.ase.data.model.RegisteredUser;

public class RegistrationViewModel extends ViewModel {

    private MutableLiveData<RegistrationFormState> registrationFormState = new MutableLiveData<>();
    private LiveData<RegistrationResult> registrationResult = new MutableLiveData<>();
    RegistrationRepository registrationRepository;

    LiveData<RegistrationFormState> getRegisterFormState(){
        return registrationFormState;
    }
    LiveData<RegistrationResult> getRegisterResult(){
        return registrationResult;
    }

    public void init(){
        registrationRepository = new RegistrationRepository();
        registrationResult = registrationRepository.getRegisterResult();
    }

    public void register(String firstname, String lastname, String email, String phonenumber, String password, String gender, Context context,String passConfirm){
//       formDataCheck(firstname, lastname, email, phonenumber, password, passConfirm);
        registrationDataChanged(firstname, lastname, email, phonenumber, password, passConfirm);
        registrationRepository.register(firstname, lastname, email, phonenumber, password, gender, context);
    }

    public void registrationDataChanged(String firstname, String lastname, String email, String phonenumber, String password, String passConfirm){
        formDataCheck(firstname, lastname, email, phonenumber, password, passConfirm);
    }

    public void formDataCheck(String firstname, String lastname, String email, String phonenumber, String password, String passConfirm){
        if(firstname.isEmpty()){
            registrationFormState.setValue(new RegistrationFormState(R.string.invalid_firstName,null,null,null,null,null));
        }else if(lastname.isEmpty()){
            registrationFormState.setValue(new RegistrationFormState(null,R.string.invalid_LastName,null,null,null,null));
        }else if(email.isEmpty()){
            registrationFormState.setValue(new RegistrationFormState(null,null,R.string.invalid_email,null,null,null));
        }else if(phonenumber.isEmpty()){
            registrationFormState.setValue(new RegistrationFormState(null,null,null,R.string.invalid_phoneNumber,null,null));
        }else if(password.isEmpty()){
            registrationFormState.setValue(new RegistrationFormState(null,null,null,null,R.string.invalid_password,null));
        }else if(passConfirm.isEmpty()){
            registrationFormState.setValue(new RegistrationFormState(null,null,null,null,null,R.string.invalid_pasConfirm));
        }else if(!password.equals(passConfirm)){
            registrationFormState.setValue(new RegistrationFormState(null,null,null,null,null,R.string.invalid_passMatch));
        }else{
            registrationFormState.setValue(new RegistrationFormState(true));
        }
    }

}
