package com.example.allrep.ui.home.feed.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.allrep.AnimalViewModel;
import com.example.allrep.R;
import com.example.allrep.ui.home.feed.Feeding_helper;
import com.example.allrep.userinfo.Animalinfo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fragment_feed_animalinfo extends Fragment {
    boolean saving = false;
    private AnimalViewModel animalViewModel;
    List<Animalinfo> animals;
    Animalinfo animal;
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

        String animalName = getArguments().getString("animalName");
        String animalJong = getArguments().getString("animalJong");
        String userName = getArguments().getString("userName");
        int animalAge = getArguments().getInt("animalAge");
        int get_feeding_day = getArguments().getInt("feeding_day");
        int get_feeding_gram = getArguments().getInt("feeding_gram");
        int get_animalWheight = getArguments().getInt("animalWheight");
        String whatEat = getArguments().getString("whatEat");

        feeding_day.setText(Integer.toString(get_feeding_day));
        feeding_gram.setText(Integer.toString(get_feeding_gram));
        animal_gram.setText(Integer.toString(get_animalWheight));
        what_feeding.setText(whatEat);

        refactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saving) {
                    saving = false;
                    refactor.setText("수정");
                    save.setEnabled(false);
                    feeding_day.setText(Integer.toString(get_feeding_day));
                    feeding_gram.setText(Integer.toString(get_feeding_gram));
                    animal_gram.setText(Integer.toString(get_animalWheight));
                    what_feeding.setText(whatEat);
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
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference mDBReference;
                mDBReference = FirebaseDatabase.getInstance().getReference();
                String get_wf = what_feeding.getText().toString();
                int get_fd = Integer.parseInt(feeding_day.getText().toString());
                int get_fg = Integer.parseInt(feeding_gram.getText().toString());
                int get_aw = Integer.parseInt(animal_gram.getText().toString());


                String imgName = userName + "/" + animalName + ".jpg";

                Animalinfo animal_temp = new Animalinfo(userName, animalName, animalAge, animalJong, imgName, get_fd, get_fg, get_aw, get_wf);
                mDBReference.child("/Animal_info/").child(userName).child(animalName).setValue(null);
                mDBReference.child("/Animal_info/").child(userName).child(animalName).child("animalName").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if (value != null) {
                            Toast.makeText(getContext(), "이미 추가된 동물 입니다!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            mDBReference.child("/Animal_info/").child(userName).child(animalName).setValue(animal_temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getContext(), "수정완료!", Toast.LENGTH_SHORT).show();
                                    getActivity().finish();
                                }
                            });
                        }
                        mDBReference.removeEventListener(this);
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
