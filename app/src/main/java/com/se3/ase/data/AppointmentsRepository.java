package com.se3.ase.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.se3.ase.ProjectConstants;
import com.se3.ase.R;
import com.se3.ase.ServicesItem;
import com.se3.ase.data.model.AppointmentModel;
import com.se3.ase.env.EnvVariables;
import com.se3.ase.network.OkhttpInterceptor;
import com.se3.ase.ui.appointments.AppointmentResult;
import com.se3.ase.ui.serviceDetail.ServiceDetailResult;

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

public class AppointmentsRepository {
    private OkHttpClient client;
    private MutableLiveData<AppointmentResult> appointmentResult;

    public AppointmentsRepository(Context context) {
        client = new OkHttpClient.Builder().addInterceptor(new OkhttpInterceptor(context)).build();
        appointmentResult = new MutableLiveData<>();
    }

    public void getAppointments(){
        Request request = new Request.Builder()
                .url(EnvVariables.API_BASE_URL + "/appointmentcontroller/user/appointment")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                appointmentResult.postValue(new AppointmentResult(R.string.RESPONSE_NETWORK_ERROR));
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String body = response.body().string();
                try{
                    switch (response.code()){
                        case 200:
                            JSONArray appArray = new JSONArray(body);
                            List<AppointmentModel> appointmentModelList = new ArrayList<>();
                            for (int i = 0; i < appArray.length(); i++) {
                                JSONObject object = appArray.getJSONObject(i);
                                AppointmentModel appointment = new AppointmentModel(
                                        object.getString("appointmentid"),
                                        object.getString("sessionid"),
                                        object.getString("userid"),
                                        object.getString("appointmentdate"),
                                        object.getString("appointmentstatus"),
                                        object.getString("appointmentcreateat"),
                                        object.getString("appointmentupdateat"),
                                        object.getString("sessionname"),
                                        object.getString("categoryname"),
                                        object.getString("servicename")
                                );
                                appointmentModelList.add(appointment);
                            }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public LiveData<AppointmentResult> getResultResponse(){
        return appointmentResult;
    }
}
