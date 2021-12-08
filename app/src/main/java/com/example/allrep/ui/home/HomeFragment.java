package com.example.allrep.ui.home;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;


import com.example.allrep.LoginViewModel;
import com.example.allrep.Login_Activity;
import com.example.allrep.MainActivity;
import com.example.allrep.R;
import com.example.allrep.ui.home.dic.Fragment_dic;
import com.example.allrep.ui.home.feed.Fragment_feed;
import com.example.allrep.userinfo.Userinfo;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment{

    private LoginViewModel loginViewModel;
    Userinfo user;
    Fragment_dic fragment_dic;
    Fragment_feed fragment_feed;
    Button login_btn;
    TabLayout tabs;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        fragment_dic = new Fragment_dic();
        fragment_feed = new Fragment_feed();

        login_btn = (Button)root.findViewById(R.id.go_login);
        Intent intent = new Intent(this.getActivity(), Login_Activity.class);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login_btn.getText().toString().equals("로그인")){
                    startActivity(intent);
                    getActivity().finish();
                }else{
                    PackageManager packageManager = getActivity().getPackageManager();
                    Intent intent = packageManager.getLaunchIntentForPackage(getActivity().getPackageName());
                    ComponentName componentName = intent.getComponent();
                    Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                    SharedPreferences auto = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor autoLoginEdit = auto.edit();
                    autoLoginEdit.clear();
                    autoLoginEdit.commit();
                    startActivity(mainIntent);
                    System.exit(0);
                }

            }
        });


        Bundle bundle = new Bundle();
        bundle.putString("middle_title","");
        bundle.putString("small_title","");
        bundle.putInt("page",0);
        fragment_dic.setArguments(bundle);
        getFragmentManager().beginTransaction().add(R.id.container,fragment_dic).commit();

        tabs = (TabLayout)root.findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("사전"));
        tabs.addTab(tabs.newTab().setText("피딩도우미"));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0){
                    selected = fragment_dic;
                }else if(position == 1){
                    selected = fragment_feed;
                }
                if(!selected.isAdded()){
                    getFragmentManager().beginTransaction().replace(R.id.container,selected).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        loginViewModel.getLoginedId().observe(getViewLifecycleOwner(), new Observer<Userinfo>() {
            @Override
            public void onChanged(Userinfo s) {
                user = s;
                login_btn.setText("로그아웃");
            }
        });
    }

}