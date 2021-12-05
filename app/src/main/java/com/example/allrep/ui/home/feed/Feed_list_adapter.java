package com.example.allrep.ui.home.feed;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.allrep.R;
import com.example.allrep.userinfo.Animalinfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
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
        StorageReference stref = storage.getReference();



        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
        TextView animal_name = (TextView) convertView.findViewById(R.id.animal_name);
        TextView animal_weight = (TextView) convertView.findViewById(R.id.animal_weight);

        Animalinfo myItem = getItem(position);
        stref.child("animal_imgs").child(myItem.userName).child(myItem.animalName + ".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(iv_img);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
            }
        });
        animal_name.setText(myItem.animalName);
        animal_weight.setText(Integer.toString(myItem.animalWheight) + "g");

        return convertView;
    }


}
