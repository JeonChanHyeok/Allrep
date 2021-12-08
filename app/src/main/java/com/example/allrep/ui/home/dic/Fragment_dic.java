package com.example.allrep.ui.home.dic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allrep.R;
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
    Fragment_dic_animal_info fragment_dic_animal_info;
    private ListView mListView;
    DatabaseReference mDBReference = null;
    Fragment_dic_adapter1 adapter;
    ArrayList<String> category;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home_dic,container,false);
        mDBReference = FirebaseDatabase.getInstance().getReference();
        adapter = new Fragment_dic_adapter1();
        Fragment_dic_adapter1 adapter = new Fragment_dic_adapter1();
        mListView = (ListView) rootView.findViewById(R.id.diction_category);
        Button btn = (Button)rootView.findViewById(R.id.dic_button);
        fragment_dic_animal_info = new Fragment_dic_animal_info();
        category = new ArrayList<String>();
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

        return rootView;
    }
}
