package com.example.allrep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.allrep.userinfo.Feedinginfo;

import java.util.ArrayList;
import java.util.List;

public class FeedingViewModel extends ViewModel {
    List<Feedinginfo> feeding_list = new ArrayList<Feedinginfo>();
    private final MutableLiveData<List<Feedinginfo>> feedingtimes = new MutableLiveData<List<Feedinginfo>>();

    public void addFeeding(Feedinginfo info){
        feeding_list.add(info);
        feedingtimes.setValue(feeding_list);
    }

    public LiveData<List<Feedinginfo>> getFeed(){ return feedingtimes; }
    public void resetFeeding(){
        feeding_list.clear();
        feedingtimes.setValue(null);
    };

}
