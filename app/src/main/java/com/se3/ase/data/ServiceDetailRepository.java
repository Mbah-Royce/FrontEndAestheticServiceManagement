package com.se3.ase.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.se3.ase.data.model.CategoryModel;
import com.se3.ase.ProjectConstants;
import com.se3.ase.R;
import com.se3.ase.env.EnvVariables;
import com.se3.ase.network.OkhttpInterceptor;
import com.se3.ase.ui.serviceDetail.ServiceDetailResult;
import com.se3.ase.ui.serviceDetail.ServiceDetaileView;

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

public class ServiceDetailRepository {
    private MutableLiveData<ServiceDetailResult> serviceDetailResult;
    OkHttpClient client;
    private List<CategoryModel> categories;

    public ServiceDetailRepository(Context context) {
        this.serviceDetailResult = new MutableLiveData<>();
        client = new OkHttpClient.Builder().addInterceptor(new OkhttpInterceptor(context)).build();
        categories = new ArrayList<>();
    }

    public void getServiceDetail(String id) {
        Request request = new Request.Builder()
                .url(EnvVariables.API_BASE_URL + "/auth/servicescontroller/service/"+id)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                serviceDetailResult.postValue(new ServiceDetailResult(R.string.RESPONSE_NETWORK_ERROR));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String body = response.body().string();
                try {
                    switch (response.code()) {
                        case 200:
                            JSONArray dataArray = new JSONArray(body);
                            JSONObject service = dataArray.getJSONObject(0);
                            for (int i = 1; i < dataArray.length(); i++) {
                                JSONObject object = dataArray.getJSONObject(i);
                                CategoryModel category = new CategoryModel(
                                        object.getString("price"),
                                        object.getString("categoryname"),
                                        object.getString("categorydescription"),
                                        object.getString("duration"),
                                        object.getString("id")
                                );
                                categories.add(category);
                            }
                            serviceDetailResult.postValue(new ServiceDetailResult(new ServiceDetaileView(
                                    categories, service.getString("name"),
                                    service.getString("description"),
                                    service.getString("imagepath")
                            )));
                            break;
                        case 401:
                            serviceDetailResult.postValue(new ServiceDetailResult(R.string.RESPONSE_UNAUTHENTICATED));
                            break;
                        case 500:
                            serviceDetailResult.postValue(new ServiceDetailResult(R.string.RESPONSE_INTERNAL_SERVER_ERROR));
                            break;
                        case 404:
                            serviceDetailResult.postValue(new ServiceDetailResult(R.string.RESPONSE_RESOURCE_NOT_FOUND));
                            break;
                        case 422:
                            serviceDetailResult.postValue(new ServiceDetailResult(R.string.WRONG_CREDENTIALS));
                        case 403:
                            serviceDetailResult.postValue(new ServiceDetailResult(R.string.RESPONSE_FORBIDEN));
                        default:
                            serviceDetailResult.postValue(new ServiceDetailResult(R.string.UNEXPECTED_ERROR));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LiveData<ServiceDetailResult> getServiceDetailResult(){
        return this.serviceDetailResult;
    }
}



