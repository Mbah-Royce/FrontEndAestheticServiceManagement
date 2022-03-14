package com.se3.ase.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.se3.ase.ProjectConstants;
import com.se3.ase.R;
import com.se3.ase.ServicesItem;
import com.se3.ase.data.model.LoggedInUser;
import com.se3.ase.env.EnvVariables;
import com.se3.ase.network.OkhttpInterceptor;
import com.se3.ase.ui.login.LoggedInUserView;
import com.se3.ase.ui.login.LoginResult;
import com.se3.ase.ui.services.ServicesResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServicesRepository {

    private MutableLiveData<ServicesResult> serviceListingResult;
    OkHttpClient client;

    public ServicesRepository(Context context) {
        serviceListingResult = new MutableLiveData<>();
        client = new OkHttpClient.Builder().addInterceptor(new OkhttpInterceptor(context)).build();
    }

    public void getServices() {
        Request request = new Request.Builder().
                 url("http://192.168.0.101:11706/BackedTest/webresources/visitorcontroller/service")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                serviceListingResult.postValue(new ServicesResult(R.string.RESPONSE_NETWORK_ERROR));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String body = response.body().string();
                try {
                    switch (response.code()) {
                        case 200:
                            JSONArray servicesArray = new JSONArray(body);
                            List<ServicesItem> services = new ArrayList<>();
                            for (int i = 0; i < servicesArray.length(); i++) {
                                JSONObject object = servicesArray.getJSONObject(i);
                                ServicesItem service = new ServicesItem(
                                        object.getString("servicename"),
                                        object.getString("description"),
                                        object.getString("imagepath"),
                                        object.getString("serviceid")
                                );
                                services.add(service);
                            }
                            serviceListingResult.postValue(new ServicesResult(services));
                            break;
                        case 401:
                            serviceListingResult.postValue(new ServicesResult(R.string.RESPONSE_UNAUTHENTICATED));
                            break;
                        case 500:
                            serviceListingResult.postValue(new ServicesResult(R.string.RESPONSE_INTERNAL_SERVER_ERROR));
                            break;
                        case 404:
                            serviceListingResult.postValue(new ServicesResult(R.string.RESPONSE_RESOURCE_NOT_FOUND));
                            break;
                        case 422:
                            serviceListingResult.postValue(new ServicesResult(R.string.WRONG_CREDENTIALS));
                            break;
                        case 403:
                            serviceListingResult.postValue(new ServicesResult(R.string.RESPONSE_FORBIDEN));
                            break;
                        default:
                            serviceListingResult.postValue(new ServicesResult(R.string.UNEXPECTED_ERROR));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public LiveData<ServicesResult> getResultResponseLiveData() {
        return serviceListingResult;
    }

}
