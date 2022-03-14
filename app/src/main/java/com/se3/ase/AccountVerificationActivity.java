package com.se3.ase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.se3.ase.databinding.ActivityAccountVerificationBinding;
import com.se3.ase.env.EnvVariables;
import com.se3.ase.network.OkhttpInterceptor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AccountVerificationActivity extends AppCompatActivity {
    private ActivityAccountVerificationBinding binding;
    EditText codeEditText;
    SharedPreferences sharedPreferences;
    private OkHttpClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAccountVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        codeEditText = binding.code;
        Button verifyButton = binding.btnVerify;
        ProgressBar progressBar = binding.pgloading;

        //
        sharedPreferences = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        if(sharedPreferences.contains("userid")){
            String userId = sharedPreferences.getString("userid",null);
            verifyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(CheckAllFields() && sharedPreferences.contains("userid")){
                        progressBar.setVisibility(v.VISIBLE);
                        JSONObject code = new JSONObject();
                        try {
                            String email = sharedPreferences.getString("email",null);
                            code.put("optCode", codeEditText.getText().toString().trim());
                            client = new OkHttpClient.Builder().addInterceptor(new OkhttpInterceptor(AccountVerificationActivity.this)).build();
                            RequestBody requestBody = RequestBody.create(ProjectConstants.JSONb, code.toString());
                            Request request = new Request.Builder()
                                    .url(EnvVariables.API_BASE_URL + "/user/VerifyOtpController/verify/" + userId)
                                    .post(requestBody)
                                    .build();
                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setVisibility(v.GONE);
                                            Toast.makeText(getApplicationContext(), "An error Occurred can not connect to server", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressBar.setVisibility(v.GONE);
                                            int statusCode = response.code();
                                            if(statusCode == 200){
                                                String jsonData = null;
                                                try {
                                                    jsonData = response.body().string();
                                                    JSONObject Jobject = new JSONObject(jsonData);
                                                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                                                    myEdit.putString("token", Jobject.getString("token"));
                                                    myEdit.putString("userid", Jobject.getString("userid"));
                                                    myEdit.putString("first_name", Jobject.getString("first_name"));
                                                    myEdit.putString("last_name", Jobject.getString("last_name"));
                                                    myEdit.putString("email", Jobject.getString("email"));
                                                    myEdit.commit();
                                                    startActivity(new Intent(getApplicationContext(), AuthHomeActivity.class));
                                                    finish();
                                                } catch (IOException | JSONException e) {
                                                    e.printStackTrace();
                                                }
                                                Toast.makeText(getApplicationContext(),
                                                        "Account Verification Successful",
                                                        Toast.LENGTH_LONG)
                                                        .show();

                                            }else if (statusCode == 400){
                                                Toast.makeText(getApplicationContext(), "Wrong Code", Toast.LENGTH_LONG).show();
                                                codeEditText.setText("");
                                            }else {
                                                Toast.makeText(getApplicationContext(), userId+"An error occurred pleaes report problem"+statusCode, Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
        else{
            verifyButton.setEnabled(false);
        }

    }

    public boolean CheckAllFields() {
        boolean error = true;
        if (codeEditText.getText().toString().trim().length() == 0) {
            codeEditText.setError("This field is required");
            error = false;
        }
        return error;
    }
}