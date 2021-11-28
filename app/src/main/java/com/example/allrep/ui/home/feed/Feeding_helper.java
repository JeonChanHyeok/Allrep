package com.example.allrep.ui.home.feed;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.allrep.R;
import com.example.allrep.ui.home.feed.fragment.Fragment_feed_animalinfo;
import com.example.allrep.ui.home.feed.fragment.Fragment_feed_feedinginfo;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class Feeding_helper extends AppCompatActivity {
    Fragment_feed_feedinginfo fragment_feed_feedinginfo;
    Fragment_feed_animalinfo fragment_feed_animalinfo;
    boolean saving = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeding_helper_animal);
        fragment_feed_animalinfo = new Fragment_feed_animalinfo();
        fragment_feed_feedinginfo = new Fragment_feed_feedinginfo();
        getSupportFragmentManager().beginTransaction().replace(R.id.feeding_container,fragment_feed_animalinfo).commit();

        TabLayout tabs = (TabLayout)findViewById(R.id.feeding_tabs);
        tabs.addTab(tabs.newTab().setText("상세정보"));
        tabs.addTab(tabs.newTab().setText("피딩기록"));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Fragment selected = null;
                if(position == 0){
                    selected = fragment_feed_animalinfo;
                }else if(position == 1){
                    selected = fragment_feed_feedinginfo;
                }
                if(!selected.isAdded()){
                    getSupportFragmentManager().beginTransaction().replace(R.id.feeding_container,selected).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        Button refactor = (Button)findViewById(R.id.feeding_helper_refactor);
        Button save = (Button)findViewById(R.id.feeding_helper_save);
        EditText animal_name = (EditText)findViewById(R.id.feeding_animal_name);
        EditText animal_jong = (EditText)findViewById(R.id.feeding_animal_jong);
        EditText animal_age = (EditText)findViewById(R.id.feeding_animal_age);

        ArrayList<String> temp = new ArrayList<>();
        temp.add(animal_name.getText().toString());
        temp.add(animal_jong.getText().toString());
        temp.add(animal_age.getText().toString());


        refactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saving) {
                    saving = false;
                    refactor.setText("수정");
                    save.setEnabled(false);
                    animal_name.setText(temp.get(0));
                    animal_jong.setText(temp.get(1));
                    animal_age.setText(temp.get(2));
                    animal_name.setEnabled(false);
                    animal_jong.setEnabled(false);
                    animal_age.setEnabled(false);
                }else{
                    saving = true;
                    refactor.setText("취소");
                    save.setEnabled(true);
                    animal_name.setEnabled(true);
                    animal_jong.setEnabled(true);
                    animal_age.setEnabled(true);
                }
            }
        });
    }
}
