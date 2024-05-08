package com.example.multiplechoiceapp.activities.THien;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.QUY.ForgotPassword;
import com.example.multiplechoiceapp.activities.QUY.Login;
import com.example.multiplechoiceapp.retrofit.AuthenticationService;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class Account extends AppCompatActivity {
    private Button btnLogOutAccount,btnChangePasswordAccount,btnConductAccount, btnInformation;

    private String username ="";
    private TextView txtNameUserAccount;
    private Long topicID = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setControl();
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        Intent intent = getIntent();
        setEvent();
    }
    private void setEvent() {
        getUser(username, new CallbackMethod() {
            @Override
            public void onSuccess(String information) {
            }

            @Override
            public void onSuccess(String a, String b) {
            }

            @Override
            public void onFailure(String errorMessage) {
            }
        });
        btnConductAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, Contruct.class);
                intent.putExtra("USERNAME",username);
                v.getContext().startActivity(intent);
            }
        });
        btnChangePasswordAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, ForgotPassword.class);
                v.getContext().startActivity(intent);
            }
        });
        btnLogOutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, Login.class);
                v.getContext().startActivity(intent);
            }
        });
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Account.this, AccountUser.class);
                intent.putExtra("USERNAME",username);
                v.getContext().startActivity(intent);
            }
        });
    }
    private void getUser(String username, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        AuthenticationService authenticationService = retrofit.create(AuthenticationService.class);
        Call<ResultResponse> call = authenticationService.getUser(username);
        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (resultResponse != null && resultResponse.getStatus() == 200) {
                    String dataString = resultResponse.getData().toString();
                    dataString = dataString.substring(0, dataString.lastIndexOf("}"));
                    String[] parts = dataString.split(", ");
                    int i =1;
                    for (String part : parts) {
                        String[] keyValue = part.split("=");

                        if (keyValue.length == 2) {
                            String key = keyValue[0];
                            String value = keyValue[1];
                            if (i == 3) {
                                txtNameUserAccount.setText(value);
                            }
                            i ++;
                        }
                    }

                }
                else {
                    Log.e("UnSucc", response.message());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                Log.e("onFailure",t.getMessage());
            }
        });

    }


    private void setControl() {
        txtNameUserAccount = findViewById(R.id.txtNameUserAccount);
        btnConductAccount = findViewById(R.id.btnConductAccount);
        btnChangePasswordAccount = findViewById(R.id.btnChangePasswordAccount);
        btnLogOutAccount = findViewById(R.id.btnLogOutAccount);
        btnInformation = findViewById(R.id.btnInformation);
    }

}