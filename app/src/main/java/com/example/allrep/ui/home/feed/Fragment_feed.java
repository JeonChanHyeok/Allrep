package com.example.allrep.ui.home.feed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.allrep.LoginViewModel;
import com.example.allrep.R;
import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Userinfo;

import java.util.ArrayList;
import java.util.List;

public class Fragment_feed extends Fragment {
    private LoginViewModel loginViewModel;
    private AnimalViewModel animalViewModel;
    Feed_list_adapter adapter;
    private ListView mListView;
    ViewGroup rootView;
    Userinfo user;
    List<Animalinfo> animals;
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
        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        mListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent_animal = new Intent(getContext(), Feeding_helper.class);
                intent_animal.putExtra("userId", adapter.mItems.get(i).userName);
                intent_animal.putExtra("animalName", adapter.mItems.get(i).animalName);
                intent_animal.putExtra("animalAge", adapter.mItems.get(i).animalAge);
                intent_animal.putExtra("animalJong", adapter.mItems.get(i).animalJong);
                intent_animal.putExtra("feeding_day", adapter.mItems.get(i).feeding_day);
                intent_animal.putExtra("feeding_gram", adapter.mItems.get(i).feeding_gram);
                intent_animal.putExtra("animalWheight", adapter.mItems.get(i).animalWheight);
                intent_animal.putExtra("whatEat", adapter.mItems.get(i).whatEat);
                startActivity(intent_animal);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                    for (Animalinfo i : animals) {
                        adapter.mItems.add(i);
                    }
                    mListView.setAdapter(adapter);
                }
            }
        });
    }
}

