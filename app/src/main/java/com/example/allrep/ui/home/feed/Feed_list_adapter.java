package com.example.allrep.ui.home.feed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.annotation.GlideModule;
import com.example.allrep.R;
import com.example.allrep.userinfo.Animalinfo;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;

public class Feed_list_adapter extends BaseAdapter {
    public ArrayList<Animalinfo> mItems = new ArrayList<>();

    @Override
    public int getCount(){
        return mItems.size();
    }

    @Override
    public Animalinfo getItem(int position){
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_homg_feed_list,parent,false);
        }
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance("gs://allrep-f0765.appspot.com/");



        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
        TextView animal_name = (TextView) convertView.findViewById(R.id.animal_name);
        TextView animal_nextfeedingday = (TextView) convertView.findViewById(R.id.animal_nextfeedingday);
        CheckBox chbox = (CheckBox) convertView.findViewById(R.id.check);
        chbox.setFocusable(false);
        Animalinfo myItem = getItem(position);
        StorageReference storageRef = storage.getReference("animal_imgs").child(myItem.userName).child(myItem.animalName + ".jpg"); // 스토리지 공간을 참조해서 이미지를 가져옴
        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        GlideApp.with(convertView).load(storageRef).into(iv_img); // Glide를 사용하여 이미지 로드
        animal_name.setText(myItem.animalName);
        animal_nextfeedingday.setText(Integer.toString(myItem.animalWheight) + " g");

        return convertView;
    }


}
