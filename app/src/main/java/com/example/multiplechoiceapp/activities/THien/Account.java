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


public class Account extends AppCompatActivity {
    private Button btnLogOutAccount,btnChangePasswordAccount,btnConductAccount, btnInformation;

    private String username ="";
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

    private void setControl() {
        btnConductAccount = findViewById(R.id.btnConductAccount);
        btnChangePasswordAccount = findViewById(R.id.btnChangePasswordAccount);
        btnLogOutAccount = findViewById(R.id.btnLogOutAccount);
        btnInformation = findViewById(R.id.btnInformation);
    }

}