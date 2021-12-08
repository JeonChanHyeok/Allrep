package com.example.allrep.ui.home.feed.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allrep.R;
import com.example.allrep.ui.home.feed.GlideApp;
import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Feedinginfo;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Feed_adapter extends BaseAdapter {
    public ArrayList<Feedinginfo> mItems1 = new ArrayList<>();
    public ArrayList<String> str = new ArrayList<>();

    @Override
    public int getCount(){
        return mItems1.size();
    }

    @Override
    public Integer getItem(int position){
        return position;
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
            convertView = inflater.inflate(R.layout.feeding_helper_feedinfo_item,parent,false);
        }
        FirebaseStorage storage;
        storage = FirebaseStorage.getInstance("gs://allrep-f0765.appspot.com/");



        TextView feeding_date = (TextView) convertView.findViewById(R.id.text_left);
        TextView feeding_gram = (TextView) convertView.findViewById(R.id.text_right);
        Feedinginfo myItem1 = mItems1.get(getItem(position));
        feeding_date.setText(myItem1.getDatetoString());
        feeding_gram.setText(str.get(getItem(position)));

        return convertView;
    }
}
