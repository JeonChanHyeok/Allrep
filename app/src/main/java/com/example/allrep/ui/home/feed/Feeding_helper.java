package com.example.allrep.ui.home.feed;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.allrep.AnimalViewModel;
import com.example.allrep.LoginViewModel;
import com.example.allrep.R;
import com.example.allrep.ui.home.feed.fragment.Fragment_feed_animalinfo;
import com.example.allrep.ui.home.feed.fragment.Fragment_feed_feedinginfo;
import com.example.allrep.userinfo.Animalinfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.tabs.TabLayout;
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

public class Feeding_helper extends AppCompatActivity {
    Fragment_feed_feedinginfo fragment_feed_feedinginfo;
    Fragment_feed_animalinfo fragment_feed_animalinfo;
    private AnimalViewModel animalViewModel;
    List<Animalinfo> animals;
    Animalinfo animal;
    DatabaseReference mDBReference = null;
    boolean saving = false;
    Uri thisuri;
    ImageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeding_helper_animal);
        fragment_feed_animalinfo = new Fragment_feed_animalinfo();
        fragment_feed_feedinginfo = new Fragment_feed_feedinginfo();
        mDBReference = FirebaseDatabase.getInstance().getReference();

        animals = new ArrayList<Animalinfo>();
        Intent getintent = getIntent();
        String userName = getintent.getStringExtra("userId");
        String animalName = getintent.getStringExtra("animalName");
        int animalAge = getintent.getIntExtra("animalAge", 0);
        String animalJong = getintent.getStringExtra("animalJong");
        int feeding_day = getintent.getIntExtra("feeding_day", 0);
        int feeding_gram = getintent.getIntExtra("feeding_gram", 0);
        int animalWheight = getintent.getIntExtra("animalWheight", 0);
        String whatEat = getintent.getStringExtra("whatEat");

        Bundle bundle = new Bundle();
        bundle.putString("userName", userName);
        bundle.putString("animalName", animalName);
        bundle.putString("animalJong", animalJong);
        bundle.putString("whatEat", whatEat);
        bundle.putInt("animalAge", animalAge);
        bundle.putInt("feeding_day", feeding_day);
        bundle.putInt("feeding_gram", feeding_gram);
        bundle.putInt("animalWheight", animalWheight);
        fragment_feed_animalinfo.setArguments(bundle);
        fragment_feed_feedinginfo.setArguments(bundle);
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
        img = (ImageView)findViewById(R.id.feeding_animal_img);
        EditText animal_name = (EditText)findViewById(R.id.feeding_animal_name);
        EditText animal_jong = (EditText)findViewById(R.id.feeding_animal_jong);
        EditText animal_age = (EditText)findViewById(R.id.feeding_animal_age);
        animal_name.setText(animalName);
        animal_jong.setText(animalJong);
        animal_age.setText(Integer.toString(animalAge));

        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance("gs://allrep-f0765.appspot.com/");
        StorageReference storageRef = storage.getReference("animal_imgs").child(userName).child(animalName + ".jpg"); // 스토리지 공간을 참조해서 이미지를 가져옴
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(this.getApplicationContext()).load(storageRef).into(img); // Glide를 사용하여 이미지 로드

        refactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saving) {
                    saving = false;
                    refactor.setText("수정");
                    save.setEnabled(false);
                    animal_name.setText(animalName);
                    animal_jong.setText(animalJong);
                    animal_age.setText(Integer.toString(animalAge));
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
                    img.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent temp = new Intent(Intent.ACTION_GET_CONTENT);
                            temp.setType("image/*");
                            startActivityForResult(temp, 0);
                        }
                    });
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseStorage storage;
                StorageReference stref;
                DatabaseReference mDBReference;
                storage = FirebaseStorage.getInstance();
                stref = storage.getReference();
                mDBReference = FirebaseDatabase.getInstance().getReference();
                String get_an = animal_name.getText().toString();
                String get_aj = animal_jong.getText().toString();
                int get_aa = Integer.parseInt(animal_age.getText().toString());



                String imgName = userName + "/" + get_an + ".jpg";

                Animalinfo animal_temp = new Animalinfo(userName, get_an, get_aa, get_aj, imgName, feeding_day, feeding_gram, animalWheight, whatEat);
                StorageReference riverRdf = stref.child("animal_imgs/" + imgName);
                riverRdf.delete();
                UploadTask uploadTask = riverRdf.putFile(thisuri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        return;
                    }
                });
                mDBReference.child("/Animal_info/").child(userName).child(animalName).setValue(null);

                mDBReference.child("/Animal_info/").child(userName).child(get_an).child("animalName").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if (value != null) {
                            return;
                        } else {
                            mDBReference.child("/Animal_info/").child(userName).child(get_an).setValue(animal_temp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Feeding_helper.this, "수정완료!", Toast.LENGTH_SHORT).show();
                                    finish();
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

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0) {
            Glide.with(getApplicationContext()).load(data.getData()).into(img);
            thisuri = data.getData();
        }
    }


}
