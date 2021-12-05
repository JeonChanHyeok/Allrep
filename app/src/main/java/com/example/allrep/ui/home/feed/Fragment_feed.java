package com.example.allrep.ui.home.feed;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
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
    private ListView mListView;
    ViewGroup rootView;
    Userinfo user;
    List<Animalinfo> animals;
    public Feed_list_adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_home_feed,container,false);

        mListView = (ListView) rootView.findViewById(R.id.feed_list);
        adapter = new Feed_list_adapter();
        mListView.setAdapter(adapter);
        Button button = (Button)rootView.findViewById(R.id.feed_ok);
        Button add = (Button)rootView.findViewById(R.id.add_animal);
        Intent intent = new Intent(this.getActivity(), Add_Animal.class);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("userId", user.userId);
                startActivity(intent);
            }
        });
        //for(Animalinfo i : animals){

        //}
        /*button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });*/


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
                animals = animalinfos;
                adapter.mItems.clear();
                for(Animalinfo i : animals){
                    adapter.mItems.add(i);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}

