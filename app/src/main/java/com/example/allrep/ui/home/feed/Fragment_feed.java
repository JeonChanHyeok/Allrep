package com.example.allrep.ui.home.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.allrep.AnimalViewModel;
import com.example.allrep.FeedingViewModel;
import com.example.allrep.LoginViewModel;
import com.example.allrep.R;
import com.example.allrep.ui.home.feed.fragment.List_checked;
import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Feedinginfo;
import com.example.allrep.userinfo.Userinfo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Fragment_feed extends Fragment {
    private LoginViewModel loginViewModel;
    private AnimalViewModel animalViewModel;
    private FeedingViewModel feedingViewModel;
    Feed_list_adapter adapter;
    private ListView mListView;
    ViewGroup rootView;
    Userinfo user;
    List<Animalinfo> animals;
    List<Feedinginfo> feedings;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home_feed,container,false);

        mListView = (ListView) rootView.findViewById(R.id.feed_list);
        Button button = (Button)rootView.findViewById(R.id.feed_ok);
        Button add = (Button)rootView.findViewById(R.id.add_animal);
        Intent intent = new Intent(this.getActivity(), Add_Animal.class);
        adapter = new Feed_list_adapter();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("userId", user.userId);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_animal = new Intent(getContext(), Feeding_helper.class);
                intent_animal.putExtra("userId", adapter.mItems1.get(i).userName);
                intent_animal.putExtra("animalName", adapter.mItems1.get(i).animalName);
                intent_animal.putExtra("animalAge", adapter.mItems1.get(i).animalAge);
                intent_animal.putExtra("animalJong", adapter.mItems1.get(i).animalJong);
                intent_animal.putExtra("feeding_day", adapter.mItems1.get(i).feeding_day);
                intent_animal.putExtra("feeding_gram", adapter.mItems1.get(i).feeding_gram);
                intent_animal.putExtra("animalWheight", adapter.mItems1.get(i).animalWheight);
                intent_animal.putExtra("whatEat", adapter.mItems1.get(i).whatEat);
                startActivity(intent_animal);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(List_checked f : adapter.checklist){
                    if(f.checked){
                        System.out.println(f.position + " checked");
                    }
                }
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);
        loginViewModel.getLoginedId().observe(getViewLifecycleOwner(), new Observer<Userinfo>() {
            @Override
            public void onChanged(Userinfo s) {
                user = s;
            }
        });
        animalViewModel = new ViewModelProvider(requireActivity()).get(AnimalViewModel.class);
        animalViewModel.getAnimals().observe(getViewLifecycleOwner(), new Observer<List<Animalinfo>>() {
            @Override
            public void onChanged(List<Animalinfo> animalinfos) {
                if (animalinfos != null) {
                    animals = animalinfos;
                    int is = 0;
                    for (Animalinfo i : animals) {
                        adapter.mItems1.add(i);
                        adapter.checklist.add(new List_checked(false, is));
                        is++;
                    }
                    System.out.println(adapter.mItems1.size());
                    feedingViewModel = new ViewModelProvider(requireActivity()).get(FeedingViewModel.class);
                    feedingViewModel.getFeed().observe(getViewLifecycleOwner(), new Observer<List<Feedinginfo>>() {
                        @Override
                        public void onChanged(List<Feedinginfo> feedinginfos) {
                            if(feedinginfos != null){
                                feedings = feedinginfos;
                                for(Animalinfo a : animals){
                                    for (Feedinginfo f : feedings){
                                        if(a.animalName.equals(f.animalName)){
                                            if(f.i == 1){
                                                adapter.mItems2.add(f);
                                            }
                                        }
                                    }
                                }
                                System.out.println(adapter.mItems2.size());
                                List<Animalinfo> temp_a = new ArrayList<>();
                                List<Feedinginfo> temp_f = new ArrayList<>();
                                Animalinfo temp1 = new Animalinfo();
                                Feedinginfo temp2 = new Feedinginfo();
                                for(int i = 0 ; i < adapter.mItems1.size() ; i++){
                                    temp1 = adapter.mItems1.get(i);
                                    temp2 = adapter.mItems2.get(i);
                                    for(int j = i ; j < adapter.mItems1.size() ; j ++){
                                        try {
                                            if(adapter.mItems2.get(j).getDate().compareTo(temp2.getDate())<=0){
                                                temp1 = adapter.mItems1.get(j);
                                                temp2 = adapter.mItems2.get(j);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    temp_a.add(temp1);
                                    temp_f.add(temp2);
                                }
                                mListView.setAdapter(adapter);
                            }
                        }
                    });
                }
            }
        });
    }
}

