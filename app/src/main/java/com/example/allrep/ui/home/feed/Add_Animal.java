package com.example.allrep.ui.home.feed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.allrep.R;
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

import java.io.File;


public class Add_Animal extends AppCompatActivity {
    String id;
    FirebaseStorage storage;
    StorageReference stref;
    DatabaseReference mDBReference;
    ImageView animal_img;
    Uri uri;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeding_helper_animal_add);
        Intent intent = getIntent();
        id = intent.getStringExtra("userId");

        storage = FirebaseStorage.getInstance();
        stref = storage.getReference();
        mDBReference = FirebaseDatabase.getInstance().getReference();

        animal_img = (ImageView)findViewById(R.id.add_animal_img);
        animal_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent temp = new Intent(Intent.ACTION_GET_CONTENT);
                temp.setType("image/*");
                startActivityForResult(temp,0);

            }
        });
        EditText animal_name = (EditText)findViewById(R.id.add_feeding_animal_name);
        EditText animal_jong = (EditText)findViewById(R.id.add_feeding_animal_jong);
        EditText animal_age = (EditText)findViewById(R.id.add_feeding_animal_age);
        EditText animal_feeding_day = (EditText)findViewById(R.id.add_feeding_day);
        EditText animal_gram = (EditText)findViewById(R.id.add_animal_gram);
        EditText animal_feeding_gram = (EditText)findViewById(R.id.add_feeding_gram);
        EditText animal_what_eat = (EditText)findViewById(R.id.add_what_feeding);
        Button add = (Button)findViewById(R.id.add_feeding_helper_save);









        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String get_an = animal_name.getText().toString();
                String get_aj = animal_jong.getText().toString();
                int get_aa = Integer.parseInt(animal_age.getText().toString());
                int get_afd = Integer.parseInt(animal_feeding_day.getText().toString());
                int get_ag = Integer.parseInt(animal_gram.getText().toString());
                int get_afg = Integer.parseInt(animal_feeding_gram.getText().toString());
                String get_awe = animal_what_eat.getText().toString();


                String imgName = id + "/" + get_an + ".jpg";

                Animalinfo animal = new Animalinfo(id, get_an, get_aa, get_aj, imgName,get_afd, get_afg, get_ag, get_awe);
                StorageReference riverRdf = stref.child("animal_imgs/"+imgName);
                UploadTask uploadTask = riverRdf.putFile(uri);
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        return;
                    }
                });
                mDBReference.child("/Animal_info/").child(id).child(get_an).child("animalName").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String value = snapshot.getValue(String.class);
                        if(value != null){
                            Toast.makeText(Add_Animal.this, "이미 추가된 동물 입니다!", Toast.LENGTH_SHORT).show();
                        }else{
                            mDBReference.child("/Animal_info/").child(id).child(get_an).setValue(animal).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Add_Animal.this, "추가완료!", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
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
        if(requestCode == 0){
            Glide.with(getApplicationContext()).load(data.getData()).into(animal_img);
            uri = data.getData();
        }
    }
}
