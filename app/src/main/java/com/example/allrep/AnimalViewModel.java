package com.example.allrep;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.allrep.userinfo.Animalinfo;
import com.example.allrep.userinfo.Userinfo;

import java.util.ArrayList;
import java.util.List;

public class AnimalViewModel extends ViewModel {
    private final List<MutableLiveData<Animalinfo>> animals = new ArrayList<MutableLiveData<Animalinfo>>();

    public void addAnimal(Animalinfo info){
        MutableLiveData<Animalinfo> temp = new MutableLiveData<Animalinfo>();
        temp.setValue(info);
        animals.add(temp);
    }

    public List<MutableLiveData<Animalinfo>> getAnimals(){
        return animals;
    }
}
