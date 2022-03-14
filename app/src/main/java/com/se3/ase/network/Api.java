package com.se3.ase.network;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.se3.ase.ProjectConstants;
import com.se3.ase.env.EnvVariables;
import com.se3.ase.network.ApiResponseHolder;
import com.se3.ase.network.OkhttpInterceptor;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Api {
    private static OkHttpClient client;
    private static String body;
    private static Context context;
    private static ApiResponseHolder apiResponseHolder;
    public static String GET(Context context,String url) {
        final String[] message = {""};
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Api.context = context;
        apiResponseHolder = new ApiResponseHolder();
        client = new OkHttpClient.Builder().addInterceptor(new OkhttpInterceptor(context)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                apiResponseHolder.setBody(e.getMessage());
                apiResponseHolder.setStatusCode(0);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                apiResponseHolder.setBody(response.body().string());
                apiResponseHolder.setStatusCode(0);
            }
        });
        return body;
    }

    public static ApiResponseHolder POST(Context context, String url, JSONObject jsonBody, Activity ac) {
        apiResponseHolder = new ApiResponseHolder();
        Api.context = context;
        client = new OkHttpClient.Builder().addInterceptor(new OkhttpInterceptor(context)).build();
        RequestBody requestBody = RequestBody.create(EnvVariables.JSONb, jsonBody.toString());
        Request request = new Request.Builder()
                .url(EnvVariables.API_BASE_URL + url)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept","application/json")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            Handler activityHandler = new Handler(context.getMainLooper());
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                            apiResponseHolder.setBody(e.getMessage());
                            apiResponseHolder.setStatusCode(1);
                            System.out.println("faliureapi");
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                apiResponseHolder.setBody(response.body().string());
                apiResponseHolder.setStatusCode(response.code());
                System.out.println("ressponseapi");
            }


        });
        return apiResponseHolder;
    }

}
