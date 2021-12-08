package com.example.allrep;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.allrep.push.Constants;
import com.example.allrep.push.NotificationHelper;
import com.example.allrep.push.PreferenceHelper;
import com.example.allrep.ui.home.HomeFragment;
import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Feedinginfo;
import com.example.allrep.userinfo.Userinfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Placeholder;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.work.WorkManager;

import java.lang.reflect.Array;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


public class MainActivity extends AppCompatActivity {
    public static Activity activity;
    Userinfo user;
    private LoginViewModel loginViewModel;
    private AnimalViewModel animalViewModel;
    private FeedingViewModel feedingViewModel;
    List<String> feedList_next_day = new ArrayList<>();
    DatabaseReference mDBReference = null;
    boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences auto = getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        String userId = auto.getString("userId", null);
        String passwordNo = auto.getString("userPs", null);
        int share = auto.getInt("share", 0);
        int alram = auto.getInt("alram", 0);

        if(userId != null && passwordNo != null){
            user = new Userinfo();
            activity = MainActivity.this;
            user.userId = userId;
            user.userPs = passwordNo;
            user.share = share;
            user.alram = alram;
            isLogin = true;
        } else{
            isLogin = false;
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_bookmark, R.id.navigation_community,R.id.navigation_option)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        animalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);
        feedingViewModel = new ViewModelProvider(this).get(FeedingViewModel.class);



        if (isLogin){
            loginViewModel.logined(user);

            mDBReference = FirebaseDatabase.getInstance().getReference();
            mDBReference.child("/Animal_info/").child(user.userId).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    animalViewModel.resetAnimals();
                    for (DataSnapshot postSnapshot : snapshot.getChildren()){
                        HashMap<String, Object> animalInfo = (HashMap<String, Object>) postSnapshot.getValue();
                        String[] getData = {animalInfo.get("animalName").toString(), animalInfo.get("animalJong").toString()
                                , animalInfo.get("animalAge").toString(), animalInfo.get("animalWheight").toString()
                                , animalInfo.get("animalImg").toString(), animalInfo.get("feeding_day").toString()
                                , animalInfo.get("feeding_gram").toString(), animalInfo.get("userName").toString()
                                , animalInfo.get("whatEat").toString()};


                        Animalinfo animal = new Animalinfo(getData[7], getData[0], Integer.parseInt(getData[2])
                                                            , getData[1], getData[4], Integer.parseInt(getData[5])
                                                            , Integer.parseInt(getData[6]), Integer.parseInt(getData[3])
                                                            , getData[8]);
                        mDBReference.child("/Feeding_time_info/").child(user.userId).child(animal.animalName).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                                    HashMap<String, Object> feedingInfo = (HashMap<String, Object>) postSnapshot.getValue();
                                    String[] getData = {feedingInfo.get("userName").toString(), feedingInfo.get("animalName").toString(), feedingInfo.get("i").toString(),
                                            feedingInfo.get("feeding_year").toString(), feedingInfo.get("feeding_month").toString(), feedingInfo.get("feeding_day").toString()};

                                    Feedinginfo feedinginfo = new Feedinginfo(Integer.parseInt(getData[2]),
                                            getData[0], getData[1], Integer.parseInt(getData[3]),
                                            Integer.parseInt(getData[4]), Integer.parseInt(getData[5]));
                                    if (feedinginfo.animalName.equals(animal.animalName)){
                                        feedingViewModel.addFeeding(feedinginfo);
                                        for(int i=0 ; i < 10 ; i ++){
                                            if(feedinginfo.i == i){
                                                int today_year,today_month,today_day;
                                                GregorianCalendar toDayMan = new GregorianCalendar();
                                                today_year = toDayMan.get(toDayMan.YEAR);  //년
                                                today_month = toDayMan.get(toDayMan.MONTH)+1;//월
                                                today_day = toDayMan.get(toDayMan.DAY_OF_MONTH); // 일 int 값으로 불러오기
                                                if(feedinginfo.feeding_year == today_year && feedinginfo.feeding_month == today_month && feedinginfo.feeding_day == today_day){
                                                    feedList_next_day.add(feedinginfo.animalName);
                                                }
                                            }
                                        }
                                    }
                                }
                                initSwitchLayout(WorkManager.getInstance(getApplicationContext()),feedList_next_day);
                                mDBReference.removeEventListener(this);
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                        animalViewModel.addAnimal(animal);
                    }
                    mDBReference.removeEventListener(this);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }

    private void initSwitchLayout(final WorkManager workManager, List<String> feed_list) {
        if(feed_list.size() != 0) {
            if (user.alram != 0) {
                boolean isChannelCreated = NotificationHelper.isNotificationChannelCreated(getApplicationContext());
                if (isChannelCreated) {
                    PreferenceHelper.setBoolean(getApplicationContext(), Constants.SHARED_PREF_NOTIFICATION_KEY, true);
                    NotificationHelper.setScheduledNotification(workManager, feed_list);
                } else {
                    if (user.alram == 3) {
                        NotificationHelper.createNotificationChannel(getApplicationContext(), true, feed_list);
                    }
                    else {
                        NotificationHelper.createNotificationChannel(getApplicationContext(), false, feed_list);
                    }
                }
            } else {
                PreferenceHelper.setBoolean(getApplicationContext(), Constants.SHARED_PREF_NOTIFICATION_KEY, false);
                workManager.cancelAllWork();
            }
        }
    }

}

