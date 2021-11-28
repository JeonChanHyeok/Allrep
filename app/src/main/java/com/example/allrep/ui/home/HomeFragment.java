package com.example.allrep.ui.home;

import android.content.Intent;
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
import androidx.lifecycle.ViewModelProviders;


import com.example.allrep.Login_Activity;
import com.example.allrep.R;
import com.example.allrep.ui.home.dic.Fragment_dic;
import com.example.allrep.ui.home.feed.Fragment_feed;
import com.google.android.material.tabs.TabLayout;


public class HomeFragment extends Fragment{

    private HomeViewModel homeViewModel;
    Fragment_dic fragment_dic;
    Fragment_feed fragment_feed;
    private FragmentActivity myContext;
    boolean islogin = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        fragment_dic = new Fragment_dic();
        fragment_feed = new Fragment_feed();
        Button login_btn = (Button)root.findViewById(R.id.go_login);
        Intent intent = new Intent(this.getActivity(), Login_Activity.class);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });



        getFragmentManager().beginTransaction().add(R.id.container,fragment_dic).commit();

        TabLayout tabs = (TabLayout)root.findViewById(R.id.tabs);
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

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });


        return root;
    }

}