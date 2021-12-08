package com.example.allrep.ui.home.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.allrep.AnimalViewModel;
import com.example.allrep.FeedingViewModel;
import com.example.allrep.LoginViewModel;
import com.example.allrep.MainActivity;
import com.example.allrep.R;
import com.example.allrep.ui.home.dic.Fragment_dic;
import com.example.allrep.ui.home.feed.fragment.List_checked;
import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Feedinginfo;
import com.example.allrep.userinfo.Userinfo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Fragment_feed extends Fragment {
    Fragment_dic frd;
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
        frd = new Fragment_dic();
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
                        adapter.mItems1.get(f.position).update_feeding();
                    }
                }
                Toast.makeText(getContext(), "피딩완료!", Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("middle_title","");
                bundle.putString("small_title","");
                bundle.putInt("page",0);
                frd.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container,frd).commit();
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
                                Animalinfo temp1 = new Animalinfo();
                                Feedinginfo temp2 = new Feedinginfo();

                                for(int i = 0 ; i < adapter.mItems1.size()-1 ; i++){
                                    for(int j = 0 ; j < adapter.mItems1.size()-i-1 ; j ++){
                                        try {
                                            if(adapter.mItems2.get(j).getDate().compareTo(adapter.mItems2.get(j+1).getDate())>0){
                                                temp1 = adapter.mItems1.get(j);
                                                temp2 = adapter.mItems2.get(j);
                                                adapter.mItems1.set(j, adapter.mItems1.get(j+1));
                                                adapter.mItems2.set(j, adapter.mItems2.get(j+1));
                                                adapter.mItems1.set(j+1, temp1);
                                                adapter.mItems2.set(j+1, temp2);
                                            }
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
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

