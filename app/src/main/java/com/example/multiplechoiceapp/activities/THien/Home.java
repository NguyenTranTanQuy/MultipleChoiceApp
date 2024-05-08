package com.example.multiplechoiceapp.activities.THien;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.Hien.MainActivity;
import com.example.multiplechoiceapp.activities.Hien.StatisticalActivity;
import com.example.multiplechoiceapp.activities.TRIEU.NotificationActivity;
import com.example.multiplechoiceapp.activities.TRIEU.TopicSetActivity;
import com.example.multiplechoiceapp.adapters.CustomAdapterTopic;
import com.example.multiplechoiceapp.fragments.NavBottom;
import com.example.multiplechoiceapp.models.Topic;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.Count;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ExperimentalBadgeUtils
public class Home extends AppCompatActivity {
    private Button btnShareTopicSetHome, btnStatisticalHome, btnCreatedTopicSetHome, btnTopicHome;
    private RecyclerView lvHome;
    private CustomAdapterTopic customAdapterTopic;
    private Long topicID = 0L;
    private float duration = 0F;

    private Toolbar toolbar;
    private BadgeDrawable badgeDrawable;
    private String username;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Bắt đầu một FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Thêm Fragment vào Activity
        NavBottom navBottom = new NavBottom(); // Khởi tạo Fragment
        fragmentTransaction.add(R.id.fragment_container, navBottom); // Thêm Fragment vào Activity
        fragmentTransaction.commit();
        setControl();
        setEvent();
        initViews();
        handler = new Handler(Looper.getMainLooper());
        repeatTask();
    }

    private void setEvent() {
        RetrofitClient retrofitClient = new RetrofitClient();
        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
        username ="thuhien123";
        TakeTopic(retrofitClient,username);
        btnTopicHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, Topic_Home.class);
                intent.putExtra("ID_TOPIC", topicID);
                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });

        btnShareTopicSetHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TopicSetActivity.class);
                startActivity(intent);
            }
        });

        btnStatisticalHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, StatisticalActivity.class);
                startActivity(intent);
            }
        });
        lvHome.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                if (e.getAction() == MotionEvent.ACTION_UP) {
                    View childView = rv.findChildViewUnder(e.getX(), e.getY());
                    if (childView != null) {
                        int position = rv.getChildAdapterPosition(childView);
                        if (position != RecyclerView.NO_POSITION) {
                            if (position < customAdapterTopic.getItemCount()) {
                                Topic tp = customAdapterTopic.getItem(position);
                                topicID = tp.getTopicCode();
                                if (tp != null) {
                                    Intent intent = new Intent(Home.this, TopicSet.class);
                                    intent.putExtra("ID_TOPIC", topicID);
                                    intent.putExtra("USERNAME", username);
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
            }
        });

    }

    private void TakeTopic(RetrofitClient retrofitClient, String username) {
        retrofitClient.getTopicHome(username, new Callback<List<Topic>>() {
            @Override
            public void onResponse(Call<List<Topic>> call, Response<List<Topic>> response) {
                if (response.isSuccessful()) {
                    List<Topic> topic = response.body();
                    lvHome.setLayoutManager(new GridLayoutManager(Home.this, 4, RecyclerView.VERTICAL, false));
                    customAdapterTopic = new CustomAdapterTopic(Home.this, R.layout.layout_list_topic_home, topic);
                    lvHome.setAdapter(customAdapterTopic);
                } else {
                    Log.e("Erro", "Lỗi Topic");

                }
            }

            @Override
            public void onFailure(Call<List<Topic>> call, Throwable t) {
                Log.e("E",t.getMessage());

            }
        });
    }

    private void setControl() {
        btnCreatedTopicSetHome = findViewById(R.id.btnCreatedTopicSetHome);
        btnShareTopicSetHome = findViewById(R.id.btnShareTopicSetHome);
        btnStatisticalHome = findViewById(R.id.btnStatisticalHome);
        lvHome = findViewById(R.id.lvHome);
        btnTopicHome = findViewById(R.id.btnTopicHome);
    }

    private void initViews() {
        setUpToolbar();
    }

    private void setUpToolbar() {
        badgeDrawable = BadgeDrawable.create(this);
        toolbar = (Toolbar) findViewById(R.id.toolbarHeader);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        BadgeUtils.attachBadgeDrawable(badgeDrawable, toolbar, R.id.notification);
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