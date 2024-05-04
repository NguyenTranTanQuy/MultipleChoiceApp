package com.example.multiplechoiceapp.activities.Hien;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.retrofit.Hien.Exam;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.models.ResultResponse;
import com.example.multiplechoiceapp.retrofit.utils.CallbackMethod;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    List<Object> dataList;
    CustomAdapterExam customAdapterExam;
    Button btnChart1;

    ListView lvDanhSachDaThi;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        getExam(username, new CallbackMethod() {
            @Override
            public void onSuccess(String information) {
                setEvent();
            }

            @Override
            public void onSuccess(String a, String b) {

            }

            @Override
            public void onFailure(String errorMessage) {

            }
        });

        Button btnChart1 = findViewById(R.id.btnChart1);
        btnChart1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChartPoint.class);
                startActivity(intent);
            }
        });
    }


    private void setEvent() {
        customAdapterExam = new CustomAdapterExam(this, R.layout.layout_exam, dataList);
        lvDanhSachDaThi.setAdapter(customAdapterExam);
    }

    private void getExam(String username, CallbackMethod callback) {
        Retrofit retrofit = RetrofitClient.getClient();
        Exam exam = retrofit.create(Exam.class);
        Call<ResultResponse> call = exam.getExam(username);

        call.enqueue(new Callback<ResultResponse>() {
            @Override
            public void onResponse(Call<ResultResponse> call, Response<ResultResponse> response) {
                ResultResponse resultResponse = response.body();
                if (resultResponse.getStatus() == 200) {
                    dataList = resultResponse.getDataList();
                    callback.onSuccess(resultResponse.getMessage());
                }
                else {
                    callback.onFailure(resultResponse.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResultResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());
            }
        });
    }

    private void setControl() {
        lvDanhSachDaThi = findViewById(R.id.lvDanhSachDaThi);
        btnChart1 = findViewById(R.id.btnChart1);
    }
}