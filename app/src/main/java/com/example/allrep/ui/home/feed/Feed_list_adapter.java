package com.example.allrep.ui.home.feed;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.allrep.R;
import com.example.allrep.userinfo.Animalinfo;

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

        //ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img);
        TextView animal_name = (TextView) convertView.findViewById(R.id.animal_name);
        TextView animal_weight = (TextView) convertView.findViewById(R.id.animal_weight);

        Animalinfo myItem = getItem(position);

        //iv_img.setImageDrawable(myItem.animalImg);
        animal_name.setText(myItem.animalName);
        //animal_weight.setText(myItem.animalWheight);

        return convertView;
    }


}
