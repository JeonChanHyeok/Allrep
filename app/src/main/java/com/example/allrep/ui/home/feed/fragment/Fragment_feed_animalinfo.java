package com.example.allrep.ui.home.feed.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allrep.R;

import java.util.ArrayList;

public class Fragment_feed_animalinfo extends Fragment {
    boolean saving = false;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.feeding_helper_animalinfo,container,false);
        Button refactor = (Button)rootView.findViewById(R.id.feeding_helper_info_refactor);
        Button save = (Button)rootView.findViewById(R.id.feeding_helper_info_save);
        EditText feeding_day = (EditText)rootView.findViewById(R.id.feeding_day);
        EditText feeding_gram = (EditText)rootView.findViewById(R.id.feeding_gram);
        EditText animal_gram = (EditText)rootView.findViewById(R.id.animal_gram);
        EditText what_feeding = (EditText)rootView.findViewById(R.id.what_feeding);
        ArrayList<String> temp = new ArrayList<>();
        temp.add(feeding_day.getText().toString());
        temp.add(feeding_gram.getText().toString());
        temp.add(animal_gram.getText().toString());
        temp.add(what_feeding.getText().toString());

        refactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saving) {
                    saving = false;
                    refactor.setText("수정");
                    save.setEnabled(false);
                    feeding_day.setText(temp.get(0));
                    feeding_gram.setText(temp.get(1));
                    animal_gram.setText(temp.get(2));
                    what_feeding.setText(temp.get(3));
                    feeding_day.setEnabled(false);
                    feeding_gram.setEnabled(false);
                    animal_gram.setEnabled(false);
                    what_feeding.setEnabled(false);
                }else{
                    saving = true;
                    refactor.setText("취소");
                    save.setEnabled(true);
                    feeding_day.setEnabled(true);
                    feeding_gram.setEnabled(true);
                    animal_gram.setEnabled(true);
                    what_feeding.setEnabled(true);
                }
            }
        });

        return rootView;
    }
}
