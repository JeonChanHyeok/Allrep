package com.example.allrep.ui.home.feed.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.WorkManager;

import com.example.allrep.R;
import com.example.allrep.userinfo.Feedinginfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.GregorianCalendar;
import java.util.HashMap;

public class Fragment_feed_feedinginfo extends Fragment {
    ListView listView;
    Feed_adapter ad;
    DatabaseReference mDBReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.feeding_helper_feedinfo,container,false);
        listView = (ListView)rootView.findViewById(R.id.feeding_info_list);
        String animalName = getArguments().getString("animalName");
        String userName = getArguments().getString("userName");
        int get_feeding_gram = getArguments().getInt("feeding_gram");
        ad = new Feed_adapter();

        mDBReference = FirebaseDatabase.getInstance().getReference();

        mDBReference.child("/Feeding_time_info/").child(userName).child(animalName).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    HashMap<String, Object> feedingInfo = (HashMap<String, Object>) postSnapshot.getValue();
                    String[] getData = {feedingInfo.get("userName").toString(), feedingInfo.get("animalName").toString(), feedingInfo.get("i").toString(),
                            feedingInfo.get("feeding_year").toString(), feedingInfo.get("feeding_month").toString(), feedingInfo.get("feeding_day").toString()};
                    Feedinginfo feedinginfo = new Feedinginfo(Integer.parseInt(getData[2]),
                            getData[0], getData[1], Integer.parseInt(getData[3]),
                            Integer.parseInt(getData[4]), Integer.parseInt(getData[5]));
                    if (feedinginfo.animalName.equals(animalName)){
                        for(int i=0 ; i < 10 ; i ++){
                            if(feedinginfo.i == i){
                                ad.mItems1.add(feedinginfo);
                                ad.str.add(Integer.toString(get_feeding_gram));
                            }
                        }
                    }
                }
                mDBReference.removeEventListener(this);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        listView.setAdapter(ad);
        return rootView;
    }
}
