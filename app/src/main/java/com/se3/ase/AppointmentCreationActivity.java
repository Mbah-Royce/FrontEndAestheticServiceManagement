package com.se3.ase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.se3.ase.data.model.SessionModel;
import com.se3.ase.env.EnvVariables;
import com.se3.ase.network.OkhttpInterceptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AppointmentCreationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatePickerDialog datePickerDialog;
    Button dateBtn, subAppointmentBtn;
    Spinner sessionSpinner,paymentMethodSpinner;
    JSONObject appoinmentData;
    ArrayList<SessionModel> sessions;
    String paymentMethods[] ={ "MTN Mobile Money", "Orange Mobile Money"};
    OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String catId = intent.getStringExtra("category_id");
        setContentView(R.layout.activity_appointment_creation);

        sessionSpinner = findViewById(R.id.sessionSpinner);
        paymentMethodSpinner = findViewById(R.id.paymentMethodSpinner);

        appoinmentData = new JSONObject();
        client = new OkHttpClient.Builder().addInterceptor(new OkhttpInterceptor(getApplicationContext())).build();


        sessions = new ArrayList<>();
        getCategorySessions(catId);

        ArrayAdapter<SessionModel> sessionAd = new ArrayAdapter<SessionModel>(this, android.R.layout.simple_spinner_item,sessions);
        sessionAd.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sessionSpinner.setAdapter(sessionAd);
        sessionSpinner.setOnItemSelectedListener(this);



        sessionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SessionModel sessionModel = (SessionModel) parent.getSelectedItem();
                displaySessionId(sessionModel);

//                                try {
//                    SessionModel sessionModel = (SessionModel) parent.getSelectedItem();
//                    System.out.println("this is the id"+sessionModel.getSesssionId());
//                    appoinmentData.put("sessionid",sessionModel.getSesssionId());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter paymentMethodAd = new ArrayAdapter(this, android.R.layout.simple_spinner_item,paymentMethods);
        paymentMethodAd.setDropDownViewResource(android.R.layout.simple_spinner_item);
        paymentMethodSpinner.setAdapter(paymentMethodAd);
        paymentMethodSpinner.setOnItemSelectedListener(this);


        initDatePicker();
        dateBtn = (Button) findViewById(R.id.datePickerBtn);
        subAppointmentBtn = (Button) findViewById(R.id.createAppointment);
        subAppointmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAppointmentData();
            }
        });
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v);
            }
        });

    }

    private void getCategorySessions(String catId) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data.....");
        progressDialog.show();
        Request request = new Request.Builder()
                .url(EnvVariables.API_BASE_URL + "/auth/categorycessioncontroller/category/sessions/" + catId)
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "An error occurred: can not connect to server",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String body = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if(response.code() == 200){
                            try {
                                JSONArray data = new JSONArray(body);
                                for(int i = 0; i < data.length(); i++){
                                    JSONObject object = data.getJSONObject(i);
                                    sessions.add(new SessionModel(
                                            object.getString("sessionid"),
                                            object.getString("name"),
                                            object.getString("starttime"),
                                            object.getString("endtime")
                                    ));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "An error occurred "+catId,
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
            }
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener =  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = makeDateString(dayOfMonth,month, year);
                dateBtn.setText(date);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_WEEK);

        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
    }

    private  String makeDateString(int day, int month, int year){
        return getMonthFormat(month) + "-" + day + "-" + year;
    }

    private String getMonthFormat(int month) {
        switch (month){
            case 1: return "JAN";
            case 2: return "FEB";
            case 3: return "MAR";
            case 4: return "APR";
            case 5: return "MAY";
            case 6: return "JUN";
            case 7: return "JUL";
            case 8: return "AUG";
            case 9: return "SEP";
            case 10: return "OCT";
            case 11: return "NOV";
            case 12: return "DEC";
        }
        return  "JAN";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }

    private void submitAppointmentData()  {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data.....");
        progressDialog.show();
        try{
            appoinmentData.put("appointmentdate","2029-11-12");
            appoinmentData.put("appointmentstatus","Pending");
            appoinmentData.put("sessionid","2");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(ProjectConstants.JSONb, appoinmentData.toString());
        Request request = new Request.Builder()
                .url(EnvVariables.API_BASE_URL + "/auth/appointmentcontroller/appointment")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "An error occurred: can not connect to server",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                final String body = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if(response.code() == 201){
                            AlertDialog.Builder builder = new AlertDialog.Builder(AppointmentCreationActivity.this);
                            builder.setCancelable(false);
                            builder.setTitle("Appointment Creation Notification");
                            builder.setMessage("You will a text message from the your mobile service provider to make payment and " +
                                    "you would recieve a notification for successfull booking");
                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder.show();
                        }else{
                            Toast.makeText(getApplicationContext(),
                                    "An error occurred"+response.code(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                });
            }
        });
    }

    public void getSelectSession (View v){
        SessionModel sessionModel = (SessionModel) sessionSpinner.getSelectedItem();
    }
    public void displaySessionId(SessionModel sessionModel){
        String id = sessionModel.getSesssionId();
        Toast.makeText(this,"sessionid id noew "+id,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getId() == R.id.paymentMethodSpinner){
                    try {
                        appoinmentData.put("paymentMethods", paymentMethods[position]);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                Toast.makeText(this, "clicked on payment spinner" + paymentMethods[position], Toast.LENGTH_LONG).show();
        }else if (parent.getId() == R.id.sessionSpinner){
//                try {
//                    SessionModel sessionModel = (SessionModel) parent.getSelectedItem();
//                    appoinmentData.put("sessionid",sessionModel.getSesssionId());
//                    Toast.makeText(this, "clicked on session spinner"+sessionModel.getSesssionId(),Toast.LENGTH_LONG).show();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                Toast.makeText(this, "clicked on payment spinner",Toast.LENGTH_LONG).show();
        }else{
                    Toast.makeText(this, "clicked can enter therer",Toast.LENGTH_LONG).show();
                }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}