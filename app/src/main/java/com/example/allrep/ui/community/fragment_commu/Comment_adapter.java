package com.example.allrep.ui.community.fragment_commu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.allrep.R;
import com.example.allrep.commuinfo.Commeinfo;

import java.util.ArrayList;

public class Comment_adapter extends BaseAdapter {
    ArrayList<Commeinfo> item = new ArrayList<Commeinfo>();
    Context context;

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.fragment_community_commelist,parent,false);
        }

        TextView userid = (TextView) convertView.findViewById(R.id.comment_userid);
        TextView comment = (TextView) convertView.findViewById(R.id.comment_text);

        Commeinfo items = (Commeinfo) getItem(position);

        userid.setText(item.get(position).getUserId());
        comment.setText(item.get(position).getCommetext());

        return convertView;
    }
    public void addItem(String userid, String comment){
        Commeinfo mItem = new Commeinfo();

        mItem.setUserId(userid);
        mItem.setCommetext(comment);

        item.add(mItem);
    }
}
