package com.se3.ase.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpInterceptor extends PreferenceActivity implements Interceptor {
    SharedPreferences sharedPreferences;
    private Context context;

    public OkhttpInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        sharedPreferences = context.getSharedPreferences("MySharedPref",MODE_PRIVATE);
        Request.Builder requestBuilder = chain.request().newBuilder();
        if(sharedPreferences.contains("token")){
            String token = sharedPreferences.getString("token",null);
            requestBuilder.addHeader("Authorization", "Bearer " + token);
        }
        requestBuilder.addHeader("Accept","application/json");
        return chain.proceed(requestBuilder.build());
    }
}
