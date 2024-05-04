package com.example.multiplechoiceapp.activities.Minh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.DTO.QuestionRequest;
import com.example.multiplechoiceapp.DTO.SelectionRequest;
import com.example.multiplechoiceapp.DTO.TopicSetRequest;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.THien.TopicSet;
import com.example.multiplechoiceapp.activities.TRIEU.NotificationActivity;
import com.example.multiplechoiceapp.activities.TRIEU.TopicSetActivity;
import com.example.multiplechoiceapp.adapters.QuestionAdapter;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.ClickListener;
import com.example.multiplechoiceapp.utils.Count;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ExperimentalBadgeUtils
public class CreateTopicSetActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private BadgeDrawable badgeDrawable;

    private QuestionAdapter questionAdapter;

    private Button buttonAddQuestion, buttonSubmitQuestion;

    private List<QuestionRequest> questions = new ArrayList<>();

    private EditText textTopicSetId,textTopicSetName,textTopicSetTime;

    private Handler handler;

    private String username ="";

    private Long topicID;

    private int tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_topic_set);
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        Intent intent = getIntent();
        topicID = intent.getLongExtra("ID_TOPIC", 0);
        initViews();

        initData();

        buttonAddQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuestionRequest questionRequest = new QuestionRequest();

                List<SelectionRequest> selectionRequests = new ArrayList<>();
                for (int i = 0; i< 4; i++){
                    SelectionRequest selectionRequest = new SelectionRequest();
                    selectionRequests.add(selectionRequest);
                }
                questionRequest.setSelection(selectionRequests);
                questions.add(questionRequest);
                questionAdapter = new QuestionAdapter(new ClickListener() {
                    @Override
                    public void onPositionClicked(int position, int status) {

                    }

                    @Override
                    public void onLongClicked(int position) {

                    }
                }, questions, getApplicationContext());
                recyclerView.setAdapter(questionAdapter);
                questionAdapter.notifyDataSetChanged();
            }
        });


        buttonSubmitQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                builder1.setMessage("Thông báo");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "Đồng ý",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RecyclerView.Adapter adapter = recyclerView.getAdapter();

                                List<QuestionRequest> questionRequests = new ArrayList<>();
                                if(adapter instanceof QuestionAdapter){
                                    QuestionAdapter questionAdapter = (QuestionAdapter) adapter;
                                    questionRequests = questionAdapter.list;
                                }

                                String name = textTopicSetName.getText().toString();
                                float duration = Float.parseFloat(textTopicSetTime.getText().toString());
                                TopicSetRequest topicSetRequest = new TopicSetRequest(name,duration,questionRequests);

                                ApiUtils.apiTopicInterface().createTopicSet(topicID,topicSetRequest).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        Toast.makeText(getApplicationContext(),"Tạo bộ đề thành công",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), TopicSet.class);
                                        startActivity(intent);
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {

                                    }
                                });
                                dialog.cancel();
                            }
                        });
                builder1.setNegativeButton(
                        "Không",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

    }

    private void initData(){
        recyclerView = (RecyclerView)findViewById(R.id.recyclerViewQuestions);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void initViews() {
        buttonAddQuestion = (Button) findViewById(R.id.buttonAddQuestion);
        buttonSubmitQuestion = (Button) findViewById(R.id.buttonSubmitQuestion);
        textTopicSetName = (EditText) findViewById(R.id.textTopicSetName);
        textTopicSetTime = (EditText) findViewById(R.id.textTopicSetTime);
        setUpToolbar();
    }

    private void setUpToolbar() {
        badgeDrawable = BadgeDrawable.create(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.notification){
            Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void repeatTask() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ApiUtils.apiUserInterface().getNotification(username).enqueue(new Callback<List<Notification>>() {
                    @Override
                    public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {

                        if(response.body().size() > Count.count){
                            BadgeUtils.attachBadgeDrawable(badgeDrawable,toolbar,R.id.notification);
                            tmp = response.body().size();
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Notification>> call, Throwable t) {
                        Log.d("data3", t.toString());
                    }
                });

                // Gọi lại hàm repeatTask() để lặp lại sau mỗi 3 giây
                repeatTask();
            }
        }, 3000); // Thực hiện sau mỗi 3 giây
    }
}