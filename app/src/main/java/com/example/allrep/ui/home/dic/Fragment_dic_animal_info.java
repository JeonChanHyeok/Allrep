package com.example.allrep.ui.home.dic;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.allrep.R;
import com.example.allrep.ui.home.feed.GlideApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class Fragment_dic_animal_info extends Fragment {
    DatabaseReference mDBReference = null;
    ImageView mImage;
    TextView mText_small;
    TextView mText_large;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home_dic_animalinfo,container,false);
        //bundle.putString("middle_title",middle_title);
        //                    bundle.putString("small_title",small_title);
        //                    bundle.putString("animalName",animalName);

        String middle_title = getArguments().getString("middle_title");
        String small_title = getArguments().getString("small_title");
        String animalName = getArguments().getString("animalName");
        mDBReference = FirebaseDatabase.getInstance().getReference();
        TextView name = (TextView)rootView.findViewById(R.id.name);
        mImage = (ImageView) rootView.findViewById(R.id.dic_animal_img);
        mText_small = (TextView)rootView.findViewById(R.id.dic_animal_sub_explain);
        mText_large = (TextView)rootView.findViewById(R.id.dic_animal_explain);
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance("gs://allrep-f0765.appspot.com/");
        StorageReference storageRef = storage.getReference("dic_images").child(animalName + ".jpg");
        GlideApp.with(this).load(storageRef).into(mImage);


        mDBReference.child("/Dic_info/").child(middle_title).child(small_title).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    String key = d.getKey();
                    HashMap<String, Object> animal = (HashMap<String, Object>) d.getValue();
                    String[] getData = {animal.get("먹이").toString(), animal.get("생물명").toString()
                            , animal.get("습도").toString(), animal.get("적정온도").toString(), animal.get("특징").toString()
                            , animal.get("평균수명").toString() , animal.get("평균크기").toString(), animal.get("학명").toString()};
                    if(getData[1].equals(animalName)){
                        name.setText(getData[1]);
                        StringBuilder smallstr = new StringBuilder();
                        smallstr.append("\n"+"  생물명 : " + getData[1] + "\n"+"\n");
                        smallstr.append("  학명 : " + getData[7] + "\n"+"\n");
                        smallstr.append("  평균 크기 : " + getData[6] + "\n"+"\n");
                        mText_small.setText(smallstr);
                        StringBuilder bigstr = new StringBuilder();
                        bigstr.append("특징 : " + getData[4] + "\n"+"\n");
                        bigstr.append("주 먹이 : " + getData[0] + "\n"+"\n");
                        bigstr.append("적정 습도 : " + getData[2] + "\n"+"\n");
                        bigstr.append("적정 온도 : " + getData[3] + "\n"+"\n");
                        bigstr.append("평균 수명 : " + getData[5] + "\n"+"\n");
                        mText_large.setText(bigstr);
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return rootView;
    }
}
