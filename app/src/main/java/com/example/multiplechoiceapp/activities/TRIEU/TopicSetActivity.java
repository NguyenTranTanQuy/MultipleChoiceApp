package com.example.multiplechoiceapp.activities.TRIEU;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.R;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


import android.Manifest;

import com.example.multiplechoiceapp.adapters.TopicSetAdapter;
import com.example.multiplechoiceapp.models.Topic_Set;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.Count;
import com.example.multiplechoiceapp.utils.RecyclerViewMargin;


import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;


@ExperimentalBadgeUtils
public class TopicSetActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TopicSetAdapter topicAdapter;
    private Toolbar toolbar;
    private BadgeDrawable badgeDrawable;

    private SearchView searchView;

    private Handler handler;

    private String TAG = "NotificationApp";

    private static final int NOTIFICATION_ID = 1;

    private FloatingActionButton fabCreateTopicSet;

    private int tmp;

    private String username;

    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean o) {
            if (o) {
                Toast.makeText(TopicSetActivity.this, "Post notification permission granted!", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_set);

        SharedPreferences sharedPreferences = getSharedPreferences("SaveAccount", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");
//        FirebaseApp.initializeApp(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(TopicSetActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                activityResultLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
        
//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(task -> {
//                    if (!task.isSuccessful()) {
//                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
//                        return;
//                    }
//                    String token = task.getResult();
//                    Log.i(TAG, token.toString());
//                });


//        fabCreateTopicSet = findViewById(R.id.fabCreateTopicSet);
//        fabCreateTopicSet.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(TopicSetActivity.this, CreateTopicSetActivity.class);
//                startActivity(intent);
//            }
//        });

        initViews();
        initData();
        setUpSearchView();
//        handler = new Handler(Looper.getMainLooper());
//        repeatTask();
    }

    private void initData(){

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiUtils.apiTopicInterface().getTopicSetsOfShareTopic(username).enqueue(new Callback<List<Topic_Set>>() {
            @Override
            public void onResponse(Call<List<Topic_Set>> call, Response<List<Topic_Set>> response) {
                RecyclerViewMargin decoration = new RecyclerViewMargin(20, response.body().size());
                recyclerView.addItemDecoration(decoration);
                recyclerView.setHasFixedSize(true);
                topicAdapter = new TopicSetAdapter(response.body(),getApplicationContext());
                recyclerView.setAdapter(topicAdapter);
                topicAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Topic_Set>> call, Throwable t) {
                Log.d("data3", t.toString());
            }
        });
    }

    private void initViews() {
        setUpToolbar();
    }

    private void setUpSearchView(){
        this.searchView = (SearchView) findViewById(R.id.searchViewTopic);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                topicAdapter.getFilter().filter(newText);
                return false;
            }
        });
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
            Count.count = tmp;
            Intent intent = new Intent(getApplicationContext(), ShareActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

//    private void repeatTask() {
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ApiUtils.apiUserInterface().getNotification("a").enqueue(new Callback<List<Notification>>() {
//                    @Override
//                    public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
//
//                        if(response.body().size() > Count.count){
//                            BadgeUtils.attachBadgeDrawable(badgeDrawable,toolbar,R.id.notification);
//                            tmp = response.body().size();
//                        }
//                    }
//                    @Override
//                    public void onFailure(Call<List<Notification>> call, Throwable t) {
//                        Log.d("data3", t.toString());
//                    }
//                });
//
//                // Gọi lại hàm repeatTask() để lặp lại sau mỗi 3 giây
//                repeatTask();
//            }
//        }, 3000); // Thực hiện sau mỗi 3 giây
//    }
}