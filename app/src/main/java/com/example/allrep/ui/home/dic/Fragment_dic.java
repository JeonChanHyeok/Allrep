package com.example.allrep.ui.home.dic;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allrep.R;
import com.example.allrep.ui.home.feed.Feeding_helper;
import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Dicinfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Fragment_dic extends Fragment {
    Fragment_dic fragment_dic;
    private ListView mListView;
    DatabaseReference mDBReference = null;
    Fragment_dic_adapter1 adapter;
    ArrayList<String> category;
    String middle_title;
    String small_title;
    int page;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home_dic,container,false);
        mDBReference = FirebaseDatabase.getInstance().getReference();
        adapter = new Fragment_dic_adapter1();
        Fragment_dic_adapter1 adapter = new Fragment_dic_adapter1();
        mListView = (ListView) rootView.findViewById(R.id.diction_category);
        Button btn = (Button)rootView.findViewById(R.id.dic_button);
        fragment_dic = new Fragment_dic();
        category = new ArrayList<String>();
        page = 0;
        page = getArguments().getInt("page");
        middle_title = getArguments().getString("middle_title");
        small_title = getArguments().getString("small_title");

        if(page == 0 ){
            mDBReference.child("/Dic_info/").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        category.add(d.getKey());
                    }
                    for(int i = 0; i<category.size();i++){
                        adapter.mItems.add(category.get(i));
                    }
                    mListView.setAdapter(adapter);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if (page == 1){
            mDBReference.child("/Dic_info/").child(middle_title).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        category.add(d.getKey());
                    }
                    for(int i = 0; i<category.size();i++){
                        adapter.mItems.add(category.get(i));
                    }
                    mListView.setAdapter(adapter);




                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        else if (page == 2){
            mDBReference.child("/Dic_info/").child(middle_title).child(small_title).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot d : snapshot.getChildren()) {
                        category.add(d.getKey());
                    }
                    for(int i = 0; i<category.size();i++){
                        adapter.mItems.add(category.get(i));
                    }
                    mListView.setAdapter(adapter);




                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(page==0){
                   middle_title = adapter.mItems.get(i);
                   Bundle bundle = new Bundle();
                   bundle.putString("middle_title",middle_title);
                   bundle.putString("small_title","");
                   bundle.putInt("page",1);
                   fragment_dic.setArguments(bundle);
                   getFragmentManager().beginTransaction().replace(R.id.container,fragment_dic).commit();
                }
                else if(page ==1){
                    small_title = adapter.mItems.get(i);
                    Bundle bundle = new Bundle();
                    bundle.putString("middle_title",middle_title);
                    bundle.putString("small_title",small_title);
                    bundle.putInt("page",2);
                    fragment_dic.setArguments(bundle);
                    getFragmentManager().beginTransaction().replace(R.id.container,fragment_dic).commit();

                }
            }
        });

        return rootView;
    }
}
