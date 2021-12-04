package com.example.allrep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.allrep.ui.home.HomeFragment;
import com.example.allrep.userinfo.Userinfo;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    public static Activity activity;
    Userinfo user;
    private LoginViewModel loginViewModel;
    boolean isLogin;
    String userID;
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
        if (isLogin){
            loginViewModel.logined(user);
        }


    }
}