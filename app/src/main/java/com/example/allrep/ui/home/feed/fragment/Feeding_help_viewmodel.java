package com.example.allrep.ui.home.feed.fragment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.allrep.userinfo.Animalinfo;

import java.util.List;

public class Feeding_help_viewmodel extends ViewModel {
    private final MutableLiveData<Animalinfo> animals = new MutableLiveData<Animalinfo>();

    public void addAnimal(Animalinfo animal){
        animals.setValue(animal);
    }
    public LiveData<Animalinfo> getAnimals(){
        return animals;
    }
}
