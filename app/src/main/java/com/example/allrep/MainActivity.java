package com.example.allrep;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.allrep.ui.home.HomeFragment;
import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Userinfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    public static Activity activity;
    Userinfo user;
    private LoginViewModel loginViewModel;
    private AnimalViewModel animalViewModel;
    DatabaseReference mDBReference = null;
    boolean isLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = new Userinfo();
        activity = MainActivity.this;
        Intent intent = getIntent();
        user.userId = intent.getStringExtra("userId");
        user.userPs = intent.getStringExtra("userPw");
        user.share = intent.getIntExtra("share",0);
        user.alram = intent.getIntExtra("alram",0);
        isLogin = intent.getBooleanExtra("isLogin", false);

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
                        animalViewModel.addAnimal(animal);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }

}