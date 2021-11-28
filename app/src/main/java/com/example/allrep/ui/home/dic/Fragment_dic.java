package com.example.allrep.ui.home.dic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allrep.R;


public class Fragment_dic extends Fragment {
    Fragment_dic_animal_info fragment_dic_animal_info;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home_dic,container,false);
        Button btn = (Button)rootView.findViewById(R.id.dic_button);
        fragment_dic_animal_info = new Fragment_dic_animal_info();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.container,fragment_dic_animal_info).commit();
            }
        });
        return rootView;
    }
}
