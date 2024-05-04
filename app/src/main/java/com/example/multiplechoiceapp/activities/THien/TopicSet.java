package com.example.multiplechoiceapp.activities.THien;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.multiplechoiceapp.DTO.Notification.Notification;
import com.example.multiplechoiceapp.R;
import com.example.multiplechoiceapp.activities.Minh.CreateTopicSetActivity;
import com.example.multiplechoiceapp.activities.TRIEU.NotificationActivity;
import com.example.multiplechoiceapp.activities.TRIEU.ShareActivity;
import com.example.multiplechoiceapp.activities.TRIEU.TopicSetActivity;
import com.example.multiplechoiceapp.adapters.CustomAdapterTopicSet;
import com.example.multiplechoiceapp.fragments.NavBottom;
import com.example.multiplechoiceapp.models.Topic_Set;
import com.example.multiplechoiceapp.retrofit.RetrofitClient;
import com.example.multiplechoiceapp.retrofit.api.ApiUtils;
import com.example.multiplechoiceapp.utils.Count;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.badge.ExperimentalBadgeUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
@ExperimentalBadgeUtils
public class TopicSet extends AppCompatActivity {

    private Button btnthi, btnfindTopicSet,btnAddTopicSet;

    private EditText txtFindTopicSet;
    private ListView lvDanhsach;
    private CustomAdapterTopicSet customAdapterTopicSet;
    private Float duration = 0F;
    private Long topicSetCode =0L;
    private String username ="";
    private Long topicID=0L;

    private Toolbar toolbar;
    private BadgeDrawable badgeDrawable;
    private FloatingActionButton fabCreateTopicSet;

    private Handler handler;
    private int tmp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topicset);

        FragmentManager fragmentManager = getSupportFragmentManager();

        // Bắt đầu một FragmentTransaction
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Thêm Fragment vào Activity
        NavBottom navBottom = new NavBottom(); // Khởi tạo Fragment
        fragmentTransaction.add(R.id.fragment_container, navBottom); // Thêm Fragment vào Activity
        fragmentTransaction.commit();
        handler = new Handler(Looper.getMainLooper());
        repeatTask();

        Intent intent = getIntent();
        if (intent != null) {
            topicID = intent.getLongExtra("ID_TOPIC",0);
            username = intent.getStringExtra("USERNAME");
        }
        setControl();
        setEvent();
        initViews();
        btnAddTopicSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CreateTopicSetActivity.class);
                intent.putExtra("ID_TOPIC", topicID);
                startActivity(intent);
            }
        });
    }

    private void setEvent() {
        RetrofitClient retrofitClient = new RetrofitClient();
        TakeTopicSet(retrofitClient);
    }

    public void TakeTopicSet(RetrofitClient retrofitClient) {
        retrofitClient.getTopicSetByTopicID(topicID, new Callback<List<Topic_Set>>() {
            @Override
            public void onResponse(Call<List<Topic_Set>> call, Response<List<Topic_Set>> response) {
                if(response.isSuccessful()){
                    List<Topic_Set> topicset = response.body();
                    for(Topic_Set tp: topicset){
                        txtFindTopicSet.setText(tp.getTopicSetName());
                        topicSetCode = tp.getTopicSetID();
                    }
                    customAdapterTopicSet = new CustomAdapterTopicSet(TopicSet.this, R.layout.layout_list_topicset, topicset);
                    lvDanhsach.setAdapter(customAdapterTopicSet);
                    customAdapterTopicSet.setOnButtonClickListener(new CustomAdapterTopicSet.OnButtonClickListener() {
                        @Override
                        public void onButtonClick(Topic_Set topicSet, int i) {
                            if(i == 1){
                                duration = topicSet.getDuration();
                                String time = String.valueOf(duration);
                                topicSetCode = topicSet.getTopicSetID();
                                Intent intent = new Intent(TopicSet.this, Exam.class);
                                intent.putExtra("ID_TOPICSET",topicSetCode);
                                intent.putExtra("USERNAME", username);
                                intent.putExtra("DURATION", duration);
                                startActivity(intent);
                            }else if (i == 2){
                                duration = topicSet.getDuration();
                                String time = String.valueOf(duration);
                                topicSetCode = topicSet.getTopicSetID();
                                Intent intent = new Intent(TopicSet.this, ExamAgain.class);
                                intent.putExtra("ID_TOPICSET", topicSetCode);
                                intent.putExtra("USERNAME",username);
                                intent.putExtra("DURATION",duration);
                                startActivity(intent);
                            } else if(i == 3) {
                                topicSetCode = topicSet.getTopicSetID();
                                Intent intent = new Intent(TopicSet.this, ShareActivity.class);
                                intent.putExtra("topicSetCode", topicSetCode);
                                intent.putExtra("topicID", topicID);
                                startActivity(intent);
                            }
                        }
                    });
                }
                else{
                    txtFindTopicSet.setText("Loi 1");

                }

            }

            @Override
            public void onFailure(Call<List<Topic_Set>> call, Throwable t) {
                txtFindTopicSet.setText("Loi 2");
                Toast.makeText(TopicSet.this,call.toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setControl(){
        txtFindTopicSet = findViewById(R.id.txtFindTopicSet);
        lvDanhsach = findViewById(R.id.lvDanhsachTopicset);
        btnAddTopicSet = findViewById(R.id.btnAddTopicSet);
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
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        BadgeUtils.attachBadgeDrawable(badgeDrawable,toolbar,R.id.notification);
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
