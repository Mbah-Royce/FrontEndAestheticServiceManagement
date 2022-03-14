package com.se3.ase.data;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.se3.ase.ProjectConstants;
import com.se3.ase.R;
import com.se3.ase.data.model.LoggedInUser;
import com.se3.ase.data.model.RegisteredUser;
import com.se3.ase.env.EnvVariables;
import com.se3.ase.ui.Registration.RegisterUserView;
import com.se3.ase.ui.Registration.RegistrationResult;
import com.se3.ase.ui.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistrationRepository {

    SharedPreferences sharedPreferences;
    RegisteredUser registerUser;
    OkHttpClient client;
    private MutableLiveData<RegistrationResult> registrationResult;


    public RegistrationRepository() {
        registrationResult = new MutableLiveData<>();
        client = new OkHttpClient.Builder().build();
    }

    private void setRegisterUser(RegisteredUser registerUser, Context context){
        sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        this.registerUser = registerUser;
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("name", registerUser.getUsername());
        myEdit.putString("email", registerUser.getEmail());
        myEdit.putString("userid", registerUser.getUserId());
        myEdit.commit();
    }
    public void register(String firstname, String lastname, String email, String phoneNumber, String password, String gender, Context context){
        try{
            JSONObject user = new JSONObject();
            user.put("firstname", firstname);
            user.put("lastname", lastname);
            user.put("email", email);
            user.put("phone", phoneNumber);
            user.put("password", password);
            user.put("gender", gender);
            RequestBody requestBody = RequestBody.create(EnvVariables.JSONb, user.toString());
            final Request request = new Request.Builder()
                    .url(EnvVariables.API_BASE_URL + "/registrationcontroller/user/register")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept","application/json")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    registrationResult.postValue(new RegistrationResult(R.string.RESPONSE_NETWORK_ERROR));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try{
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        switch (response.code()){
                            case 201:
                                setRegisterUser(new RegisteredUser(jsonObject.getString("name"), jsonObject.getString("email"),jsonObject.getString("userid")),context);
                                registrationResult.postValue(new RegistrationResult(new RegisterUserView(jsonObject.getString("name"))));
                                break;
                            case 404:
                                registrationResult.postValue(new RegistrationResult(R.string.RESPONSE_RESOURCE_NOT_FOUND));
                                break;
                            case 401:
                                registrationResult.postValue(new RegistrationResult(R.string.RESPONSE_UNAUTHENTICATED));
                                break;
                            case 500:
                                registrationResult.postValue(new RegistrationResult(R.string.RESPONSE_INTERNAL_SERVER_ERROR));
                                break;
                            default:
                                registrationResult.postValue(new RegistrationResult(R.string.UNEXPECTED_ERROR));
                        }
                    } catch (JSONException e) {
                        registrationResult.postValue(new RegistrationResult(R.string.REQUEST_BODY_ERROR));
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public LiveData<RegistrationResult> getRegisterResult(){
        return registrationResult;
    }

}
