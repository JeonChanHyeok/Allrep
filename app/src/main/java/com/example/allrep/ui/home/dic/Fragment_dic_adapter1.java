package com.example.allrep.ui.home.dic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.allrep.R;
import com.example.allrep.ui.home.feed.GlideApp;
import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Dicinfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Fragment_dic_adapter1 extends BaseAdapter {
    public ArrayList<String> mItems = new ArrayList<>();




    @Override
    public int getCount(){
        return mItems.size();
    }

    @Override
    public String getItem(int position){
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
            convertView = inflater.inflate(R.layout.fragment_home_dic_text,parent,false);
        }
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance("gs://allrep-f0765.appspot.com/");




        TextView dic1 = (TextView) convertView.findViewById(R.id.dic_text);


        String myItem =  getItem(position);

        // Download directly from StorageReference using Glide
        // (See MyAppGlideModule for Loader registration)
        dic1.setText(myItem);



        return convertView;
    }
    public void addItem(String title){
        Dicinfo mItem = new Dicinfo();
        mItem.setDic_big(title);
    }

}
