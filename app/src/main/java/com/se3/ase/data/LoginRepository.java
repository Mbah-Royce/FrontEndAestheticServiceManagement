package com.se3.ase.data;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.se3.ase.ProjectConstants;
import com.se3.ase.R;
import com.se3.ase.data.model.LoggedInUser;
import com.se3.ase.env.EnvVariables;
import com.se3.ase.network.Api;
import com.se3.ase.ui.login.LoggedInUserView;
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

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    SharedPreferences sharedPreferences;

    private MutableLiveData<LoginResult> loginResult;
    OkHttpClient client;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    public LoginRepository() {
        loginResult = new MutableLiveData<>();
        client = new OkHttpClient.Builder().build();
    }

    public boolean isLoggedIn() {
        return user != null;
    }

    public void logout() {
        user = null;
//        dataSource.logout();
    }

    private void setLoggedInUser(LoggedInUser user, Context context) {
        sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE);
        this.user = user;
        SharedPreferences.Editor myEdit = sharedPreferences.edit();
        myEdit.putString("token", user.getToken());
        myEdit.putString("userid", user.getUserId());
        myEdit.putString("name", user.getDisplayName());
        myEdit.putString("email", user.getEmail());
        myEdit.commit();
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public void login(String email, String password, Context context) {
        // handle login
        try{
            JSONObject credentials = new JSONObject();
            credentials.put("email", email);
            credentials.put("password", password);
            RequestBody requestBody = RequestBody.create(EnvVariables.JSONb, credentials.toString());
            final Request request = new Request.Builder()
                    .url(EnvVariables.API_BASE_URL + "/logincontroller/login")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept","application/json")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    loginResult.postValue(new LoginResult(R.string.RESPONSE_NETWORK_ERROR));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try{
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        switch (response.code()){
                            case 200:
                                System.out.println("here is the on response");
                                String name = jsonObject.getString("first_name")+" "+jsonObject.getString("last_name");
                                setLoggedInUser(new LoggedInUser(
                                        jsonObject.getString("userid"),
                                        name,
                                        jsonObject.getString("token"),
                                        jsonObject.getString("email")),context);
                                loginResult.postValue(new LoginResult(new LoggedInUserView(name)));
                                break;
                            case 401:
                                loginResult.postValue(new LoginResult(R.string.RESPONSE_UNAUTHENTICATED));
                                break;
                            case 500:
                                loginResult.postValue(new LoginResult(R.string.RESPONSE_INTERNAL_SERVER_ERROR));
                                break;
                            case 404:
                                loginResult.postValue(new LoginResult(R.string.RESPONSE_RESOURCE_NOT_FOUND));
                                break;
                            case 422:
                                loginResult.postValue(new LoginResult(R.string.WRONG_CREDENTIALS));
                                break;
                            case 403:
                                loginResult.postValue(new LoginResult(R.string.RESPONSE_FORBIDEN));
                                break;
                            default:
                                loginResult.postValue(new LoginResult(R.string.UNEXPECTED_ERROR));
                        }
                    } catch (JSONException e) {
                        loginResult.postValue(new LoginResult(R.string.RESPONSE_BODY_ERROR));
                    }
                }
            });
        } catch (JSONException e) {
            loginResult.postValue(new LoginResult(R.string.REQUEST_BODY_ERROR));
        }
    }

    public LiveData<LoginResult> getResultResponseLiveData() {
        return loginResult;
    }
}