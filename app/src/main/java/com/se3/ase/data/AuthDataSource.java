package com.se3.ase.data;

import android.app.Activity;
import android.content.Context;

import com.se3.ase.R;
import com.se3.ase.data.model.LoggedInUser;
import com.se3.ase.data.model.RegisteredUser;
import com.se3.ase.network.Api;
import com.se3.ase.network.ApiResponseHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class AuthDataSource {
    ApiResponseHolder apiResponseHolder;
    public Result<LoggedInUser> login(String email, String password, Context context, Activity activity) {
        apiResponseHolder = new ApiResponseHolder();
        try {
            JSONObject credentials = new JSONObject();
            credentials.put("email", email);
            credentials.put("password", password);
            apiResponseHolder = Api.POST(context,"/logincontroller/login",credentials,activity);
            System.out.println(apiResponseHolder.getBody());
            System.out.println(apiResponseHolder.getStatusCode());
            return new Result.Error(R.string.RESPONSE_UNAUTHENTICATED);
//            JSONObject jsonObject = new JSONObject(apiResponseHolder.getBody());
//            switch (apiResponseHolder.getStatusCode()){
//                case 0:
//                    return new Result.Error(R.string.RESPONSE_NETWORK_ERROR);
//                case 200:
//                    return new Result.Success<>(new LoggedInUser(
//                            jsonObject.getString("userid"),
//                            jsonObject.getString("first_name")+" "+jsonObject.getString("first_name"),
//                            jsonObject.getString("token"),
//                            jsonObject.getString("email")));
//                case 401:
//                    return new Result.Error(R.string.RESPONSE_UNAUTHENTICATED);
//                case 500:
//                    return new Result.Error(R.string.RESPONSE_INTERNAL_SERVER_ERROR);
//                case 404:
//                    return new Result.Error(R.string.RESPONSE_RESOURCE_NOT_FOUND);
//                default: return new Result.Error(R.string.UNEXPECTED_ERROR);
//            }
        } catch (Exception e) {
            return new Result.Error(R.string.REQUEST_BODY_ERROR);
        }
    }

    //write api stuff get data compare the status code and then return the classes
    public Result<RegisteredUser> register(String firstname, String lastname, String email, String phonenumber, String password, String gender, Context context) {
        apiResponseHolder = new ApiResponseHolder();
        try {
            JSONObject user = new JSONObject();
            user.put("firstname", firstname);
            user.put("lastname", lastname);
            user.put("email", email);
            user.put("phone", phonenumber);
            user.put("password", password);
            user.put("gender", gender);
            return new Result.Error(R.string.RESPONSE_UNAUTHENTICATED);
//            apiResponseHolder = Api.POST(context, "/registrationcontroller/user/register", user);
//            JSONObject jsonObject = new JSONObject(apiResponseHolder.getBody());
//            switch (apiResponseHolder.getStatusCode()) {
//                case 0:
//                    return new Result.Error(R.string.RESPONSE_NETWORK_ERROR);
//                case 201:
//                    return new Result.Success<>(new RegisteredUser(jsonObject.getString("name"), jsonObject.getString("email")));
//                case 401:
//                    return new Result.Error(R.string.RESPONSE_UNAUTHENTICATED);
//                case 500:
//                    return new Result.Error(R.string.RESPONSE_INTERNAL_SERVER_ERROR);
//                case 404:
//                    return new Result.Error(R.string.RESPONSE_RESOURCE_NOT_FOUND);
//                default:
//                    return new Result.Error(R.string.UNEXPECTED_ERROR);
            }
        catch (JSONException jsonException) {
            return new Result.Error(R.string.REQUEST_BODY_ERROR);
        }
    }
}
