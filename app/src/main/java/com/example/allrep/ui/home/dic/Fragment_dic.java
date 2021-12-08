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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home_dic,container,false);
        mDBReference = FirebaseDatabase.getInstance().getReference();
        mListView = (ListView) rootView.findViewById(R.id.diction_category);
        Button btn = (Button)rootView.findViewById(R.id.dic_button);
        fragment_dic_animal_info = new Fragment_dic_animal_info();
        ArrayList<String> category = new ArrayList<String>(Arrays.asList(""));
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container,fragment_dic_animal_info).commit();
                mDBReference.child("/Dic_info/").child("파충류").child("").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
        return rootView;
    }
}
